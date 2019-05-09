package lrg.memoria.importer.recoder;

import recoder.java.*;
import recoder.java.declaration.*;
import recoder.java.declaration.modifier.*;
import recoder.java.expression.ArrayInitializer;
import recoder.java.expression.Assignment;
import recoder.java.expression.Operator;
import recoder.java.expression.ParenthesizedExpression;
import recoder.java.expression.literal.*;
import recoder.java.expression.operator.*;
import recoder.java.reference.*;
import recoder.java.statement.*;
import recoder.list.generic.ASTList;


import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

public class CodeStripeSourcePrinter extends SourceVisitor {
    private static CodeStripeSourcePrinter instance;

    public static CodeStripeSourcePrinter instance() {
        if (instance == null) instance = new CodeStripeSourcePrinter(null);
        instance.reset();
        return instance;
    }


    private static char[] BLANKS = new char[128];
    private static char[] FEEDS = new char[8];

    static {
        for (int i = 0; i < FEEDS.length; i++) {
            FEEDS[i] = '\n';
        }
        for (int i = 0; i < BLANKS.length; i++) {
            BLANKS[i] = ' ';
        }
    }

    private Writer out;
    private int line, column;

    public String getSource(SourceElement e) {
        SourceElement.Position st, ed;

        st = e.getStartPosition();
        ed = e.getEndPosition();

        e.accept(this);
        String r = out.toString();

        int sl = st.getLine();
        while (sl > 1) {
            r = r.substring(r.indexOf("\n") + "\n".length());
            sl--;
        }

        int el = ed.getLine() - st.getLine() - (r + "\n").split("\n").length;
        while (el > 0) {
            r += "\n";
            el--;
        }

        return r;
    }

    public void reset() {
        out = new StringWriter();
        line = 1;
        column = 1;
    }

    protected CodeStripeSourcePrinter(Writer out) {
        this.out = out;
    }

    public void setWriter(Writer out) {
        this.out = out;
    }

    public Writer getWriter() {
        return out;
    }

    protected static String encodeUnicodeChars(String str) {
        int len = str.length();
        StringBuffer buf = new StringBuffer(len + 4);
        for (int i = 0; i < len; i += 1) {
            char c = str.charAt(i);
            if (c >= 0x0100) {
                if (c < 0x1000) {
                    buf.append("\\u0" + Integer.toString(c, 16));
                } else {
                    buf.append("\\u" + Integer.toString(c, 16));
                }
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }

    protected void print(int c) {
        if (laterText != null) {
            //throw new IllegalArgumentException("Missing later text. Printing will be corrupted!");
            //System.err.println("Later text missed que. Printing might be corrupted! ("+laterText+"~"+(char)c+")");
            String later = laterText;
            laterText = null;
            print(later);
        }
        if (c == '\n') {
            column = 1;
            line += 1;
        } else {
            column += 1;
        }
        try {
            out.write(c);
        } catch (IOException ioe) {
            throw new PrettyPrintingException(ioe);
        }
    }

    protected void print(String str) {
        if (str.length() == 0) return;
        if (laterText != null) {
            // this case may happen when some Element that has a location is skipped
            // eg. method should have parameter but they may be ommited: void xxx()
            // or initializer of for may be omitted etc. for(;;)
            // this is because none of (, ;, ) know their location in the source

            //throw new IllegalArgumentException("Missing later text. Printing will be corrupted!");
            //System.err.println("Later text missed que. Printing might be corrupted! ("+laterText+"~"+str+")");
            String later = laterText;
            laterText = null;
            print(later);
        }
        int i = str.lastIndexOf('\n');
        if (i >= 0) {
            column = str.length() - i + 1 + 1;
            do {
                line += 1;
                i = str.lastIndexOf('\n', i - 1);
            } while (i >= 0);
        } else {
            column += str.length();
        }

        try {
            out.write(str);
        } catch (IOException ioe) {
            throw new PrettyPrintingException(ioe);
        }
    }


    private void print(char[] cbuf, int off, int len) {
        for (int i = off; i < (off + len); i++) {
            if (cbuf[i] == '\n') {
                line += 1;
                column = 1;
            } else
                column++;
        }

        try {
            out.write(cbuf, off, len);
        } catch (IOException ioe) {
            throw new PrettyPrintingException(ioe);
        }
    }

    private void printIndentation(int lf, int blanks) {
        if (lf > 0) {
            do {
                int n = Math.min(lf, FEEDS.length);
                print(FEEDS, 0, n);
                lf -= n;
            } while (lf > 0);
        }
        while (blanks > 0) {
            int n = Math.min(blanks, BLANKS.length);
            print(BLANKS, 0, n);
            blanks -= n;
        }
    }

    private String laterText = null;

    private void latePrint(String s) {
        if (laterText == null)
            laterText = s;
        else
            laterText += s;
    }

    private void advanceTo(int line, int col) {
        int il = line - this.line, ic = 0, laterLen = 0;
        String later = "";
        if (laterText != null) {
            later = laterText;
            laterLen = later.length();
            laterText = null;
        }

        if (il == 0) { // print on same line
            ic = (col > this.column) ? col - this.column : 0;
            if (ic >= laterLen)
                ic -= laterLen;
            else {
                // since recoder's _char per line_ count ends at 255 (0xFF) this means
                // that the locations provided are no longer reliable :(
                if (this.column>=255) {
                    // no checking!
                } else {
                    // this is a case of missuse of late printing
                    // or comment positioning and attaching bugs in recoder :(
                    //throw new IllegalArgumentException(
                    //        "No space to print text scheduled for late printing ("+this.line+","+this.column+").");
                    System.out.println(
                        "Warning (CodeStripeSourcePrinter:215): Source reconstruction may be corrupted: current_file("+this.line+","+this.column+").");
                }
            }


            printIndentation(il, ic);
            print(later);
        } else { // print on different lines
            ic = col - 1;
            if (ic >= laterLen) { // print on same line with marker
                ic -= laterLen;
                printIndentation(il, ic);
                print(later);
            } else { // print on previous line to marker (cause of no spcae on same line)
                printIndentation(il - 1, 0);
                print(later);
                if (il > 0) printIndentation(1, ic);
            }
        }
    }

    private void advanceTo(SourceElement.Position p) {
        advanceTo(p.getLine(), p.getColumn());
    }

    private void printPreCommentsOf(ProgramElement x) {
        int s = (x.getComments() != null) ? x.getComments().size() : 0;
        for (int i = 0; i < s; i++) {
            Comment c = x.getComments().get(i);
            if (c.isPrefixed()) {
                c.accept(this);
            }
        }
    }

    private void printPostCommentsOf(ProgramElement x) {
        int s = (x.getComments() != null) ? x.getComments().size() : 0;
        for (int i = 0; i < s; i++) {
            Comment c = x.getComments().get(i);
            if (!c.isPrefixed()) {
                c.accept(this);
            }
        }
    }

    private void printElement(ProgramElement x) {
        x.accept(this);
    }

    private void printElements(ASTList<ProgramElement> list, String separationSymbol) {
        int s = list.size();
        if (s == 0) return;

        printElement(list.get(0));
        for (int i = 1; i < s; i++) {
            print(separationSymbol);
            printElement(list.get(i));
        }
    }

    private void printElements(List<ProgramElement> list, String separationSymbol) {
        int s = list.size();
        if (s == 0) return;

        printElement(list.get(0));
        for (int i = 1; i < s; i++) {
            print(separationSymbol);
            printElement(list.get(i));
        }
    }


    public void visitIdentifier(Identifier x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print(x.getText());
        printPostCommentsOf(x);
    }

    public void visitIntLiteral(IntLiteral x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print(x.getValue());
        printPostCommentsOf(x);
    }

    public void visitBooleanLiteral(BooleanLiteral x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print(x.getValue() ? "true" : "false");
        printPostCommentsOf(x);
    }

    public void visitStringLiteral(StringLiteral x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print(encodeUnicodeChars(x.getValue()));
        printPostCommentsOf(x);
    }

    public void visitNullLiteral(NullLiteral x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("null");
        printPostCommentsOf(x);
    }

    public void visitCharLiteral(CharLiteral x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print(encodeUnicodeChars(x.getValue()));
        printPostCommentsOf(x);
    }

    public void visitDoubleLiteral(DoubleLiteral x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print(x.getValue());
        printPostCommentsOf(x);
    }

    public void visitLongLiteral(LongLiteral x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print(x.getValue());
        printPostCommentsOf(x);
    }

    public void visitFloatLiteral(FloatLiteral x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print(x.getValue());
        printPostCommentsOf(x);
    }

    public void visitTypeReference(TypeReference x) {
        printPreCommentsOf(x);
        if (x.getReferencePrefix() != null) {
            printElement(x.getReferencePrefix());
            print('.');
        }
        if (x.getIdentifier() != null) {
            printElement(x.getIdentifier());
        }
        for (int i = 0; i < x.getDimensions(); i += 1) {
            print("[]");
        }
        printPostCommentsOf(x);
    }

    public void visitPackageReference(PackageReference x) {
        printPreCommentsOf(x);
        if (x.getReferencePrefix() != null) {
            printElement(x.getReferencePrefix());
            print('.');
        }
        if (x.getIdentifier() != null) {
            printElement(x.getIdentifier());
        }
        printPostCommentsOf(x);
    }

    public void visitArrayReference(ArrayReference x) {
        printPreCommentsOf(x);
        if (x.getReferencePrefix() != null) {
            printElement(x.getReferencePrefix());
        }
        if (x.getDimensionExpressions() != null) {
            int s = x.getDimensionExpressions().size();
            for (int i = 0; i < s; i += 1) {
                print('[');
                printElement(x.getDimensionExpressions().get(i));
                print(']');
            }
        }
        printPostCommentsOf(x);
    }

    public void visitFieldReference(FieldReference x) {
        printPreCommentsOf(x);
        if (x.getReferencePrefix() != null) {
            printElement(x.getReferencePrefix());
            print('.');
        }
        if (x.getIdentifier() != null) {
            printElement(x.getIdentifier());
        }
        printPostCommentsOf(x);
    }

    public void visitVariableReference(VariableReference x) {
        printPreCommentsOf(x);
        if (x.getIdentifier() != null) {
            printElement(x.getIdentifier());
        }
        printPostCommentsOf(x);
    }

    public void visitMetaClassReference(MetaClassReference x) {
        printPreCommentsOf(x);
        if (x.getTypeReference() != null) {
            printElement(x.getTypeReference());
            print('.');
        }
        advanceTo(x.getStartPosition());
        print("class");
        printPostCommentsOf(x);
    }

    public void visitMethodReference(MethodReference x) {
        printPreCommentsOf(x);
        if (x.getReferencePrefix() != null) {
            printElement(x.getReferencePrefix());
            print('.');
        }
        if (x.getIdentifier() != null) {
            printElement(x.getIdentifier());
        }
        latePrint("(");
        if (x.getArguments() != null) {
            printElements((ASTList)x.getArguments(), ",");
        }
        printPostCommentsOf(x);
        print(')');
        if (x.getStatementContainer() != null) {
            advanceTo(x.getEndPosition());
            print(';');
        }
    }

    public void visitSuperConstructorReference(SuperConstructorReference x) {
        printPreCommentsOf(x);
        if (x.getReferencePrefix() != null) {
            printElement(x.getReferencePrefix());
            print('.');
        }
        advanceTo(x.getStartPosition());
        print("super");
        latePrint("(");
        if (x.getArguments() != null) {
            printElements((ASTList)x.getArguments(), ",");
        }
        printPostCommentsOf(x);
        print(')');
        advanceTo(x.getEndPosition());
        print(';');
    }

    public void visitThisConstructorReference(ThisConstructorReference x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("this");
        latePrint("(");
        if (x.getArguments() != null) {
            printElements((ASTList)x.getArguments(), ",");
        }
        printPostCommentsOf(x);
        print(')');
        advanceTo(x.getEndPosition());
        print(';');
    }

    public void visitSuperReference(SuperReference x) {
        printPreCommentsOf(x);
        if (x.getReferencePrefix() != null) {
            printElement(x.getReferencePrefix());
            print('.');
        }
        advanceTo(x.getStartPosition());
        print("super");
        printPostCommentsOf(x);
    }

    public void visitThisReference(ThisReference x) {
        printPreCommentsOf(x);
        if (x.getReferencePrefix() != null) {
            printElement(x.getReferencePrefix());
            print('.');
        }
        advanceTo(x.getStartPosition());
        print("this");
        printPostCommentsOf(x);
    }

    /*
    public void visitArrayLengthReference(ArrayLengthReference x) {
        printPreCommentsOf(x);
        if (x.getReferencePrefix() != null) {
            printElement(x.getReferencePrefix());
            print('.');
        }
        advanceTo(x.getStartPosition());
        print("length");
        printPostCommentsOf(x);
    }
*/

    public void visitCompilationUnit(CompilationUnit x) {
        printPreCommentsOf(x);
        if (x.getPackageSpecification() != null) {
            printElement(x.getPackageSpecification());
        }
        if (x.getImports() != null) {
            printElements((ASTList)x.getImports(), "\n");
        }
        if (x.getDeclarations() != null) {
            printElements((ASTList)x.getDeclarations(), "");
        }
        printPostCommentsOf(x);
    }

    public void visitImport(Import x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("import ");
        printElement(x.getReference());
        if (x.isMultiImport()) {
            print(".*");
        }
        printPostCommentsOf(x);
        advanceTo(x.getEndPosition());
        print(';');
    }

    public void visitPackageSpecification(PackageSpecification x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("package ");
        printElement(x.getPackageReference());
        printPostCommentsOf(x);
        advanceTo(x.getEndPosition());
        print(';');
    }

    public void visitClassDeclaration(ClassDeclaration x) {
        printPreCommentsOf(x);
        if (x.getModifiers() != null) {
            printElements((List)x.getModifiers(), " ");
            print(' ');
        }
        if (x.getIdentifier() != null) {
            advanceTo(x.getStartPosition());
            print("class");
            printElement(x.getIdentifier());
        }
        if (x.getExtendedTypes() != null) {
            print(' ');
            printElement(x.getExtendedTypes());
        }
        if (x.getImplementedTypes() != null) {
            print(' ');
            printElement(x.getImplementedTypes());
        }

        print('{');
        if (x.getMembers() != null && !x.getMembers().isEmpty()) {
            printElements((ASTList)x.getMembers(), "");
        }
        printPostCommentsOf(x);
        advanceTo(x.getEndPosition());
        print('}');
    }

    public void visitInterfaceDeclaration(InterfaceDeclaration x) {
        printPreCommentsOf(x);
        if (x.getModifiers() != null) {
            printElements((List)x.getModifiers(), " ");
            print(' ');
        }
        if (x.getIdentifier() != null) {
            advanceTo(x.getStartPosition());
            print("interface");
            printElement(x.getIdentifier());
        }
        if (x.getExtendedTypes() != null) {
            print(' ');
            printElement(x.getExtendedTypes());
        }

        print("{");
        if (x.getMembers() != null && !x.getMembers().isEmpty()) {
            printElements((ASTList)x.getMembers(), "");
        }
        printPostCommentsOf(x);
        advanceTo(x.getEndPosition());
        print('}');
    }

    public void visitFieldDeclaration(FieldDeclaration x) {
        printPreCommentsOf(x);
        if (x.getModifiers() != null) {
            printElements((List)x.getModifiers(), " ");
            print(' ');
        }
        printElement(x.getTypeReference());
        print(' ');

        if (x.getVariables() != null) {
            printElements((List)x.getVariables(), ",");
        }
        printPostCommentsOf(x);
        advanceTo(x.getEndPosition());
        print(';');
    }

    public void visitLocalVariableDeclaration(LocalVariableDeclaration x) {
        printPreCommentsOf(x);
        if (x.getModifiers() != null) {
            printElements((List)x.getModifiers(), " ");
            print(' ');
        }
        printElement(x.getTypeReference());
        print(' ');

        if (x.getVariables() != null) {
            printElements((List)x.getVariables(), ",");
        }
        printPostCommentsOf(x);
        if (!(x.getStatementContainer() instanceof LoopStatement)) {
            advanceTo(x.getEndPosition());
            print(';');
        }
    }

    protected void visitVariableDeclaration(VariableDeclaration x) {
        printPreCommentsOf(x);
        if (x.getModifiers() != null) {
            printElements((List)x.getModifiers(), " ");
            print(' ');
        }
        printElement(x.getTypeReference());
        print(' ');

        if (x.getVariables() != null) {
            printElements((List)x.getVariables(), ",");
        }
        printPostCommentsOf(x);
    }

    public void visitExtends(Extends x) {
        printPreCommentsOf(x);
        if (x.getSupertypes() != null) {
            advanceTo(x.getStartPosition());
            print("extends ");
            printElements((ASTList)x.getSupertypes(), ",");
        }
        printPostCommentsOf(x);
    }

    public void visitImplements(Implements x) {
        printPreCommentsOf(x);
        if (x.getSupertypes() != null) {
            advanceTo(x.getStartPosition());
            print("implements ");
            printElements((ASTList)x.getSupertypes(), ",");
        }
        printPostCommentsOf(x);
    }

    public void visitThrows(Throws x) {
        printPreCommentsOf(x);
        if (x.getExceptions() != null) {
            advanceTo(x.getStartPosition());
            print("throws ");
            printElements((ASTList)x.getExceptions(), ",");
        }
        printPostCommentsOf(x);
    }

    public void visitMethodDeclaration(MethodDeclaration x) {
        printPreCommentsOf(x);
        if (x.getModifiers() != null) {
            printElements((List)x.getModifiers(), " ");
            print(' ');
        }
        if (x.getTypeReference() != null) {
            printElement(x.getTypeReference());
            print(' ');
        }
        //advanceTo(x.getStartPosition());
        printElement(x.getIdentifier());
        latePrint("(");
        if (x.getParameters() != null) {
            printElements((ASTList)x.getParameters(), ",");
        }
        print(')');
        if (x.getThrown() != null) {
            printElement(x.getThrown());
        }
        if (x.getBody() != null) {
            printElement(x.getBody());
            printPostCommentsOf(x);
        } else {
            printPostCommentsOf(x);
            advanceTo(x.getEndPosition());
            print(';');
        }
    }

    public void visitClassInitializer(ClassInitializer x) {
        printPreCommentsOf(x);
        if (x.getModifiers() != null) {
            printElements((List)x.getModifiers(), " ");
        }
        if (x.getBody() != null) {
            printElement(x.getBody());
        }
        printPostCommentsOf(x);
    }

    public void visitVariableSpecification(VariableSpecification x) {
        printElement(x.getIdentifier());
        for (int i = 0; i < x.getDimensions(); i += 1) {
            print("[]");
        }
        if (x.getInitializer() != null) {
            print("=");
            printElement(x.getInitializer());
        }
        printPostCommentsOf(x);
    }

    public void visitArrayInitializer(ArrayInitializer x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print('{');
        if (x.getArguments() != null) {
            printElements((ASTList)x.getArguments(), ",");
        }
        printPostCommentsOf(x);
        advanceTo(x.getEndPosition());
        print('}');
    }

    public void visitNewArray(NewArray x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("new");
        printElement(x.getTypeReference());
        int i = 0;
        if (x.getArguments() != null) {
            for (; i < x.getArguments().size(); i += 1) {
                print('[');
                printElement(x.getArguments().get(i));
                print(']');
            }
        }
        for (; i < x.getDimensions(); i += 1) {
            print("[]");
        }
        if (x.getArrayInitializer() != null) {
            printElement(x.getArrayInitializer());
        }
        printPostCommentsOf(x);
    }

    public void visitNew(New x) {
        if (x.getReferencePrefix() != null) {
            printElement(x.getReferencePrefix());
            print('.');
        }
        advanceTo(x.getStartPosition());
        print("new");
        printElement(x.getTypeReference());
        latePrint("(");
        if (x.getArguments() != null) {
            printElements((ASTList)x.getArguments(), ",");
        }
        print(')');
        if (x.getClassDeclaration() != null) {
            printElement(x.getClassDeclaration());
        }
        printPostCommentsOf(x);
        if (x.getStatementContainer() != null) {
            advanceTo(x.getEndPosition());
            print(';');
        }
    }


    public void visitStatementBlock(StatementBlock x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print('{');
        if (x.getBody() != null && x.getBody().size() > 0) {
            printElements((ASTList)x.getBody(), "");
        }
        printPostCommentsOf(x);
        advanceTo(x.getEndPosition());
        print('}');
    }

    public void visitBreak(Break x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("break");
        if (x.getIdentifier() != null) {
            print(" ");
            printElement(x.getIdentifier());
        }
        printPostCommentsOf(x);
        advanceTo(x.getEndPosition());
        print(';');
    }

    public void visitContinue(Continue x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("continue");
        if (x.getIdentifier() != null) {
            print(" ");
            printElement(x.getIdentifier());
        }
        printPostCommentsOf(x);
        advanceTo(x.getEndPosition());
        print(';');
    }

    public void visitLabeledStatement(LabeledStatement x) {
        printPreCommentsOf(x);
        if (x.getIdentifier() != null) {
            printElement(x.getIdentifier());
            print(':');
        }
        if (x.getBody() != null) {
            printElement(x.getBody());
        }
        printPostCommentsOf(x);
    }


    public void visitReturn(Return x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("return");
        if (x.getExpression() != null) {
            print(" ");
            printElement(x.getExpression());
        }
        printPostCommentsOf(x);
        advanceTo(x.getEndPosition());
        print(';');
    }

    public void visitThrow(Throw x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("throw");
        if (x.getExpression() != null) {
            print(" ");
            printElement(x.getExpression());
        }
        printPostCommentsOf(x);
        advanceTo(x.getEndPosition());
        print(';');
    }

    public void visitDo(Do x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("do ");
        if (x.getBody() == null || x.getBody() instanceof EmptyStatement) {
            print(';');
        } else {
            printElement(x.getBody());
        }
        latePrint("while(");
        if (x.getGuard() != null) {
            printElement(x.getGuard());
        }
        printPostCommentsOf(x);
        print(')');
        advanceTo(x.getEndPosition());
        print(';');
    }

    public void visitFor(For x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("for");
        latePrint("(");

        if (x.getInitializers() != null) {
            printElements((ASTList)x.getInitializers(), ",");
        }
        print(';');
        if (x.getGuard() != null) {
            printElement(x.getGuard());
        }
        print(';');
        if (x.getUpdates() != null) {
            printElements((ASTList)x.getUpdates(), ",");
        }
        print(')');
        if (x.getBody() == null || x.getBody() instanceof EmptyStatement) {
            printPostCommentsOf(x);

            advanceTo(x.getEndPosition());
            print(';');
        } else {
            printElement(x.getBody());
            printPostCommentsOf(x);
        }
    }

    public void visitWhile(While x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("while");
        latePrint("(");
        if (x.getGuard() != null) {
            printElement(x.getGuard());
        }
        print(')');
        if (x.getBody() == null || x.getBody() instanceof EmptyStatement) {
            printPostCommentsOf(x);

            advanceTo(x.getEndPosition());
            print(';');
        } else {
            printElement(x.getBody());
            printPostCommentsOf(x);
        }
    }

    public void visitAssert(Assert x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("assert");
        if (x.getCondition() != null) {
            print(' ');
            printElement(x.getCondition());
        }
        if (x.getMessage() != null) {
            print(":");
            printElement(x.getMessage());
        }
        printPostCommentsOf(x);

        advanceTo(x.getEndPosition());
        print(';');
    }

    public void visitIf(If x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("if");
        latePrint("(");
        if (x.getExpression() != null)
            printElement(x.getExpression());
        print(')');
        if (x.getThen() != null)
            printElement(x.getThen());
        if (x.getElse() != null)
            printElement(x.getElse());
        printPostCommentsOf(x);
    }

    public void visitThen(Then x) {
        printPreCommentsOf(x);
        if (x.getBody() != null) {
            printElement(x.getBody());
        }
        printPostCommentsOf(x);
    }

    public void visitElse(Else x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("else ");
        if (x.getBody() != null) {
            printElement(x.getBody());
        }
        printPostCommentsOf(x);
    }

    public void visitSwitch(Switch x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("switch");
        latePrint("(");
        if (x.getExpression() != null)
            printElement(x.getExpression());
        print("){");
        if (x.getBranchList() != null && x.getBranchCount() > 0)
            printElements((ASTList)x.getBranchList(), "");

        printPostCommentsOf(x);
        advanceTo(x.getEndPosition());
        print('}');
    }

    public void visitCase(Case x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("case");
        if (x.getExpression() != null) {
            print(' ');
            printElement(x.getExpression());
        }
        print(':');
        if (x.getBody() != null && x.getBody().size() > 0)
            printElements((ASTList)x.getBody(), "");
        printPostCommentsOf(x);
    }

    public void visitDefault(Default x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("default:");
        if (x.getBody() != null && x.getBody().size() > 0)
            printElements((ASTList)x.getBody(), "");
        printPostCommentsOf(x);
    }

    public void visitTry(Try x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("try ");
        if (x.getBody() != null)
            printElement(x.getBody());
        if (x.getBranchList() != null)
            printElements((ASTList)x.getBranchList(), "");
        printPostCommentsOf(x);
    }

    public void visitCatch(Catch x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("catch");
        latePrint("(");
        if (x.getParameterDeclaration() != null)
            printElement(x.getParameterDeclaration());
        print(')');
        if (x.getBody() != null)
            printElement(x.getBody());
        printPostCommentsOf(x);
    }

    public void visitFinally(Finally x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("finally ");
        if (x.getBody() != null)
            printElement(x.getBody());
        printPostCommentsOf(x);
    }


    public void visitSynchronizedBlock(SynchronizedBlock x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("synchronized");

        if (x.getExpression() != null) {
            latePrint("(");
            printElement(x.getExpression());
            print(')');
        } else
            print(' ');

        if (x.getBody() != null) {
            printElement(x.getBody());
        }
        printPostCommentsOf(x);
    }

    public void visitEmptyStatement(EmptyStatement x) {
        printPreCommentsOf(x);
        advanceTo(x.getEndPosition());
        printPostCommentsOf(x);
        print(';');
    }

    public void visitInstanceof(Instanceof x) {
        if (x.getArguments() != null) {
            printElement(x.getArguments().get(0));
        }
        //advanceTo(x.getStartPosition()); // instanceof's location seems broken
        print(" instanceof");
        if (x.getTypeReference() != null) {
            printElement(x.getTypeReference());
        }
        printPostCommentsOf(x);
    }

    public void visitTypeCast(TypeCast x) {
        advanceTo(x.getStartPosition());
        print('(');
        if (x.getTypeReference() != null) {
            printElement(x.getTypeReference());
        }
        print(')');
        if (x.getArguments() != null) {
            printElement(x.getArguments().get(0));
        }
        printPostCommentsOf(x);
    }

    public void visitUncollatedReferenceQualifier(UncollatedReferenceQualifier x) {
        printPreCommentsOf(x);
        if (x.getReferencePrefix() != null) {
            printElement(x.getReferencePrefix());
            print('.');
        }
        if (x.getIdentifier() != null) {
            // advanceTo(x.getStartPosition());
            // printElement(x.getIdentifier());
            // URQ Location seems broken StartLocation = EndLocation
            // so we improvise :)
            latePrint(x.getIdentifier().getText());
        }
        printPostCommentsOf(x);
    }


    private void printBinaryOp(Operator x, String symbol) {
        printPreCommentsOf(x);
        printElement(x.getArguments().get(0));
        advanceTo(x.getStartPosition());
        print(symbol);
        printElement(x.getArguments().get(1));
        printPostCommentsOf(x);
    }

    private void printAssignement(Assignment x, String symbol) {
        printPreCommentsOf(x);
        printElement(x.getArguments().get(0));
        advanceTo(x.getStartPosition());
        print(symbol);
        printElement(x.getArguments().get(1));
        if (x.getStatementContainer() != null) {
            advanceTo(x.getEndPosition());
            print(';');
        }
        printPostCommentsOf(x);
    }

    private void printPrefixUnaryOp(Operator x, String symbol) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print(symbol);
        printElement(x.getArguments().get(0));
    }

    private void printPostfixUnaryOp(Operator x, String symbol) {
        printPreCommentsOf(x);
        printElement(x.getArguments().get(0));
        advanceTo(x.getStartPosition());
        print(symbol);
    }

    public void visitBinaryAndAssignment(BinaryAndAssignment x) {
        printAssignement(x, "&=");
    }

    public void visitBinaryOrAssignment(BinaryOrAssignment x) {
        printAssignement(x, "|=");
    }

    public void visitBinaryXOrAssignment(BinaryXOrAssignment x) {
        printAssignement(x, "^=");
    }

    public void visitCopyAssignment(CopyAssignment x) {
        printAssignement(x, "=");
    }

    public void visitDivideAssignment(DivideAssignment x) {
        printAssignement(x, "/=");
    }

    public void visitMinusAssignment(MinusAssignment x) {
        printAssignement(x, "-=");
    }

    public void visitModuloAssignment(ModuloAssignment x) {
        printAssignement(x, "%=");
    }

    public void visitPlusAssignment(PlusAssignment x) {
        printAssignement(x, "+=");
    }

    public void visitShiftLeftAssignment(ShiftLeftAssignment x) {
        printAssignement(x, "<<=");
    }

    public void visitShiftRightAssignment(ShiftRightAssignment x) {
        printAssignement(x, ">>=");
    }

    public void visitTimesAssignment(TimesAssignment x) {
        printAssignement(x, "*=");
    }

    public void visitUnsignedShiftRightAssignment(UnsignedShiftRightAssignment x) {
        printAssignement(x, ">>>=");
    }

    public void visitBinaryAnd(BinaryAnd x) {
        printBinaryOp(x, "&");
    }

    public void visitBinaryNot(BinaryNot x) {
        // Binary as in bits not as in 2 operands!
        //printBinaryOp(x,"~");
        printPrefixUnaryOp(x, "~");
        printPostCommentsOf(x);
    }

    public void visitBinaryOr(BinaryOr x) {
        printBinaryOp(x, "|");
    }

    public void visitBinaryXOr(BinaryXOr x) {
        printBinaryOp(x, "^");
    }

    public void visitDivide(Divide x) {
        printBinaryOp(x, "/");
    }

    public void visitEquals(Equals x) {
        printBinaryOp(x, "==");
    }

    public void visitGreaterOrEquals(GreaterOrEquals x) {
        printBinaryOp(x, ">=");
    }

    public void visitGreaterThan(GreaterThan x) {
        printBinaryOp(x, ">");
    }

    public void visitLessOrEquals(LessOrEquals x) {
        printBinaryOp(x, "<=");
    }

    public void visitLessThan(LessThan x) {
        printBinaryOp(x, "<");
    }

    public void visitNotEquals(NotEquals x) {
        printBinaryOp(x, "!=");
    }

    public void visitLogicalAnd(LogicalAnd x) {
        printBinaryOp(x, "&&");
    }

    public void visitLogicalOr(LogicalOr x) {
        printBinaryOp(x, "||");
    }

    public void visitMinus(Minus x) {
        printBinaryOp(x, "-");
    }

    public void visitModulo(Modulo x) {
        printBinaryOp(x, "%");
    }

    public void visitPlus(Plus x) {
        printBinaryOp(x, "+");
    }

    public void visitShiftLeft(ShiftLeft x) {
        printBinaryOp(x, "<<");
    }

    public void visitShiftRight(ShiftRight x) {
        printBinaryOp(x, ">>");
    }

    public void visitTimes(Times x) {
        printBinaryOp(x, "*");
    }

    public void visitUnsignedShiftRight(UnsignedShiftRight x) {
        printBinaryOp(x, ">>>");
    }


    public void visitPostDecrement(PostDecrement x) {
        printPostfixUnaryOp(x, "--");
        printPostCommentsOf(x);
        if (x.getStatementContainer() != null) {
            advanceTo(x.getEndPosition());
            print(';');
        }
    }

    public void visitPostIncrement(PostIncrement x) {
        printPostfixUnaryOp(x, "++");
        printPostCommentsOf(x);
        if (x.getStatementContainer() != null) {
            advanceTo(x.getEndPosition());
            print(';');
        }
    }

    public void visitPreDecrement(PreDecrement x) {
        printPrefixUnaryOp(x, "--");
        printPostCommentsOf(x);
        if (x.getStatementContainer() != null) {
            advanceTo(x.getEndPosition());
            print(';');
        }
    }

    public void visitPreIncrement(PreIncrement x) {
        printPrefixUnaryOp(x, "++");
        printPostCommentsOf(x);
        if (x.getStatementContainer() != null) {
            advanceTo(x.getEndPosition());
            print(';');
        }
    }

    public void visitLogicalNot(LogicalNot x) {
        printPrefixUnaryOp(x, "!");
        printPostCommentsOf(x);
    }

    public void visitNegative(Negative x) {
        printPrefixUnaryOp(x, "-");
        printPostCommentsOf(x);
    }

    public void visitPositive(Positive x) {
        printPrefixUnaryOp(x, "+");
        printPostCommentsOf(x);
    }

    public void visitConditional(Conditional x) {
        printPreCommentsOf(x);
        if (x.getArguments() != null) {
            printElement(x.getArguments().get(0));
            print("?");
            printElement(x.getArguments().get(1));
            print(":");
            printElement(x.getArguments().get(2));
        }
        printPostCommentsOf(x);
    }

    public void visitParenthesizedExpression(ParenthesizedExpression x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print('(');
        if (x.getArguments() != null) {
            printElement(x.getArguments().get(0));
        }
        printPostCommentsOf(x);
        print(')');
    }


    public void visitAbstract(Abstract x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("abstract");
        printPostCommentsOf(x);
    }

    public void visitFinal(Final x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("final");
        printPostCommentsOf(x);
    }

    public void visitNative(Native x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("native");
        printPostCommentsOf(x);
    }

    public void visitPrivate(Private x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("private");
        printPostCommentsOf(x);
    }

    public void visitProtected(Protected x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("protected");
        printPostCommentsOf(x);
    }

    public void visitPublic(Public x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("public");
        printPostCommentsOf(x);
    }

    public void visitStatic(Static x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("static");
        printPostCommentsOf(x);
    }

    public void visitStrictFp(StrictFp x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("strictfp");
        printPostCommentsOf(x);
    }

    public void visitSynchronized(Synchronized x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("synchronized");
        printPostCommentsOf(x);
    }

    public void visitTransient(Transient x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("transient");
        printPostCommentsOf(x);
    }

    public void visitVolatile(Volatile x) {
        printPreCommentsOf(x);
        advanceTo(x.getStartPosition());
        print("volatile");
        printPostCommentsOf(x);
    }

    public void visitComment(Comment x) {
        advanceTo(x.getStartPosition());
        print(x.getText());
    }

    public void visitSingleLineComment(SingleLineComment x) {
        visitComment(x);
    }

    public void visitDocComment(DocComment x) {
        visitComment(x);
    }


}
