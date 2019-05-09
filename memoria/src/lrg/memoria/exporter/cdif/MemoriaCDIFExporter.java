package lrg.memoria.exporter.cdif;

import lrg.memoria.core.*;
import lrg.memoria.core.Class;
import lrg.memoria.core.Package;
import lrg.memoria.core.System;
import lrg.memoria.importer.recoder.JavaModelLoader;
import lrg.memoria.utils.ConfigFileReader;
import lrg.memoria.utils.Logger;
import lrg.memoria.utils.MEMORIABreadthIterator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Iterator;

public class MemoriaCDIFExporter extends ModelVisitor {

    public static final String nullElement = "_NULL_";

    private System system;
    private PrintStream os;
    private long counter;

    public MemoriaCDIFExporter(System sys) {
        system = sys;
        counter = ModelElementsRepository.getCurrentModelElementsRepository().getElementCount();
    }

    public void exportToStream(PrintStream os) {
        this.os = os;
        printCDIFHeader();
        Iterator it = new MEMORIABreadthIterator(system);
        for (; it.hasNext();)
            ((ModelElement) it.next()).accept(this);
        printCDIFFooter();
    }

    private void printCDIFHeader() {
        os.println("CDIF, SYNTAX \"SYNTAX.1\" \"02.00.00\", ENCODING \"ENCODING.1\" \"01.05.04\"");
        os.println("(:HEADER");
        os.println("\t(:SUMMARY ");
        os.println("\t\t(ExporterName  \"MEMORIA\")");
        os.println("\t\t(ExporterVersion  \"1.00\")");
        Calendar cld = Calendar.getInstance();
        os.println("\t\t(ExporterDate  \"" + cld.get(Calendar.YEAR) + "//" + cld.get(Calendar.MONTH) + "//" + cld.get(Calendar.DAY_OF_MONTH) + "\")");
        os.println("\t\t(ExporterTime  \"" + cld.get(Calendar.HOUR) + "//" + cld.get(Calendar.MINUTE) + "//" + cld.get(Calendar.SECOND) + "\")");
        os.println("\t\t(PublisherName  \"Unknown\")");
        os.println("\t)");
        os.println(")");

        os.println("(:META-MODEL");
        os.println("\t(:SUBJECTAREAREFERENCE Foundation");
        os.println("\t\t(:VERSIONNUMBER \"01.00\") ");
        os.println("\t)");
        os.println("\t(:SUBJECTAREAREFERENCE MEEMORIA");
        os.println("\t\t(:VERSIONNUMBER \"1.0\")");
        os.println("\t)");
        os.println(")");

        os.println("(:MODEL");
    }

    private void printCDIFFooter() {
        os.println(")");
    }

    public void visitPackage(Package p) {
        os.println("(Package " + p.getElementID());
        os.println("\t(getName \"" + p.getName() + "\")");
        os.println("\t(statute " + p.getStatute() + " )");
        os.println(")");
        os.println();
    }

    public void visitNamespace(Namespace n) {
        os.println("(Namespace " + n.getElementID());
        os.println("\t(getName \"" + n.getName() + "\")");
        os.println("\t(statute " + n.getStatute() + " )");
        os.println(")");
        os.println();
    }

    public void visitClass(Class c) {
        os.println("(Class " + c.getElementID());
        os.println("\t(getName \"" + c.getName() + "\")");
        os.println("\t(uniqueName \"" + c.getFullName() + "\")");
        os.println("\t(belongsToPackage \"" + c.getPackage().getFullName() + "\")");
        Scope temp = c.getScope();
        while (!(temp instanceof Namespace))
            temp = temp.getScope();
        os.println("\t(belongsToNamespace \"" + ((Namespace) temp).getFullName() + "\")");
        if (c.getScope() instanceof DataAbstraction)
            os.println("\t(belongsToClass \"" + c.getScope().getFullName() + "\")");
        if (c.getScope() instanceof Body)
            os.println("\t(belongsToBody \"" + ((Body) c.getScope()).getElementID() + "\")");
        os.println("\t(file_name \"" + c.getLocation().getFile().getFullName() + "\")");
        os.println("\t(start_line " + c.getLocation().getStartLine() + " )");
        os.println("\t(start_char " + c.getLocation().getStartChar() + " )");
        os.println("\t(end_line " + c.getLocation().getEndLine() + " )");
        os.println("\t(end_char " + c.getLocation().getEndChar() + " )");
        os.println("\t(isAbstract -" + new Boolean(c.isAbstract()).toString().toUpperCase() + "- )");
        os.println("\t(isFinal -" + new Boolean(c.isFinal()).toString().toUpperCase() + "- )");
        os.println("\t(isStatic -" + new Boolean(c.isStatic()).toString().toUpperCase() + "- )");
        os.println("\t(isInterface -" + new Boolean(c.isInterface()).toString().toUpperCase() + "- )");
        os.println("\t(statute " + c.getStatute() + " )");
        os.println("\t(access_mode " + c.getAccessMode() + " )");
        os.println(")");
        os.println();

        os.println("(InheritanceDefinition " + counter++);
        os.println("\t(subclass \"" + c.getFullName() + "\")");
        if (c.getFirstAncestor() != null)
            os.println("\t(superclass \"" + c.getFirstAncestor().getFullName() + "\")");
        else
            os.println("\t(superclass \"" + nullElement + "\")");
        os.println(")");
        os.println();

        for (Iterator it = c.getInterfaces().iterator(); it.hasNext();) {
            os.println("(ImplementsDefinition " + counter++);
            os.println("\t(subclass \"" + c.getFullName() + "\")");
            os.println("\t(interface \"" + ((DataAbstraction) it.next()).getFullName() + "\")");
            os.println(")");
            os.println();
        }
        os.println();
    }

    public void visitArrayDecorator(ArrayDecorator ad) {
        os.println("(ArrayDecorator " + ad.getElementID());
        os.println("\t(getName \"" + ad.getName() + "\")");
        os.println("\t(uniqueName \"" + ad.getFullName() + "\")");
        os.println("\t(decoratedType \"" + ad.getDecoratedType().getFullName() + "\")");
        os.println(")");
        os.println();
    }

    public void visitPrimitive(Primitive p) {
        os.println("(PrimitiveType " + p.getElementID());
        os.println("\t(getName \"" + p.getName() + "\")");
        os.println("\t(uniqueName \"" + p.getFullName() + "\")");
        os.println("\t(belongsToNamespace \"" + p.getScope().getFullName() + "\")");
        os.println(")");
        os.println();
    }

    public void visitMethod(Method m) {
        os.println("(Method " + m.getElementID());
        os.println("\t(getName \"" + m.getName() + "\")");
        String fullName = m.getFullName();
        os.println("\t(uniqueName \"" + fullName + "\")");
        //int index = getFullName.substring(0, getFullName.indexOf("(")).lastIndexOf(".");
        //os.println("\t(signature \"" + getFullName.substring(index + 1) + "\")");
        Location loc = m.getLocation();
        printLocation(loc);
        os.println("\t(belongsTo \"" + m.getScope().getFullName() + "\")");
        if (m.getReturnType() != null)
            os.println("\t(returnType \"" + m.getReturnType().getFullName() + "\")");
        else
            os.println("\t(returnType \"" + nullElement + "\")");
        os.println("\t(access_mode " + m.getAccessMode() + " )");
        os.println("\t(isAbstract -" + new Boolean(m.isAbstract()).toString().toUpperCase() + "- )");
        os.println("\t(isFinal -" + new Boolean(m.isFinal()).toString().toUpperCase() + "- )");
        os.println("\t(isStatic -" + new Boolean(m.isStatic()).toString().toUpperCase() + "- )");
        os.println("\t(statute " + m.getStatute() + " )");
        os.println("\t(kindOf " + m.getKindOf() + " )");
        os.println(")");
        os.println();

        for (Iterator it = m.getExceptionList().iterator(); it.hasNext();) {
            os.println("(ThrowsException " + counter++);
            os.println("\t(method \"" + m.getFullName() + "\")");
            os.println("\t(exception_name \"" + ((DataAbstraction) it.next()).getFullName() + "\")");
            os.println(")");
            os.println();
        }
    }

    private void printLocation(Location loc) {
        if (loc != null) {
            os.println("\t(file_name \"" + loc.getFile().getFullName() + "\")");
            os.println("\t(start_line " + loc.getStartLine() + " )");
            os.println("\t(start_char " + loc.getStartChar() + " )");
            os.println("\t(end_line " + loc.getEndLine() + " )");
            os.println("\t(end_char " + loc.getEndChar() + " )");
        } else {
            os.println("\t(file_name \"" + "library" + "\")");
            os.println("\t(start_line " + -1 + " )");
            os.println("\t(start_char " + -1 + " )");
            os.println("\t(end_line " + -1 + " )");
            os.println("\t(end_char " + -1 + " )");
        }
    }

    public void visitFunctionBody(FunctionBody mb) {
        os.println("(FunctionBody " + mb.getElementID());
        os.println("\t(belongsTo \"" + mb.getScope().getFullName() + "\")");
        os.println("\t(LOC " + mb.getNumberOfLines() + ")");
        os.println("\t(CYCLO " + mb.getCyclomaticNumber() + ")");
        os.println("\t(NOS " + mb.getNumberOfStatements() + ")");
        os.println("\t(NODec " + mb.getNumberOfDecisions() + ")");
        os.println("\t(NOCmt " + mb.getNumberOfComments() + ")");
        os.println("\t(NOExc " + mb.getNumberOfExceptions() + ")");
        os.println("\t(NOExits " + mb.getNumberOfExits() + ")");
        os.println("\t(NOL " + mb.getNumberOfLoops() + ")");
        os.println(")");
        os.println();
    }

    public void visitInitializerBody(InitializerBody ib) {
        os.println("(InitializerBody " + ib.getElementID());
        os.println("\t(belongsTo \"" + ib.getScope().getFullName() + "\")");
        os.println("\t(LOC " + ib.getNumberOfLines() + ")");
        os.println("\t(CYCLO" + ib.getCyclomaticNumber() + ")");
        os.println("\t(NOS" + ib.getNumberOfStatements() + ")");
        os.println("\t(NODec" + ib.getNumberOfDecisions() + ")");
        os.println("\t(NOCmt" + ib.getNumberOfComments() + ")");
        os.println("\t(NOExc" + ib.getNumberOfExceptions() + ")");
        os.println("\t(NOExits" + ib.getNumberOfExits() + ")");
        os.println("\t(NOL" + ib.getNumberOfLoops() + ")");
        os.println(")");
        os.println();

    }

    public void visitAttribute(Attribute a) {
        os.println("(Attribute " + a.getElementID());
        printVariableCharacteristics(a);
        os.println("\t(belongsTo \"" + a.getScope().getFullName() + "\")");
        os.println("\t(access_mode " + a.getAccessMode() + " )");
        os.println(")");
        os.println();
    }

    public void visitParameter(Parameter p) {
        os.println("(FormalParameter " + p.getElementID());
        printVariableCharacteristics(p);
        os.println("\t(belongsTo \"" + p.getScope().getFullName() + "\")");
        os.println(")");
        os.println();
    }

    public void visitLocalVar(LocalVariable lv) {
        os.println("(LocalVariable " + lv.getElementID());
        printVariableCharacteristics(lv);
        os.println("\t(belongsTo \"" + lv.getScope().getElementID() + "\")");
        os.println("\t(isBlock \"" + new Boolean(lv.isBlock()).toString().toUpperCase() + "\")");
        os.println(")");
        os.println();
    }

    private void printVariableCharacteristics(Variable var) {
        os.println("\t(getName \"" + var.getName() + "\")");
        printVariableFullName(var);
        os.println("\t(type \"" + var.getType().getFullName() + "\")");
        os.println("\t(isFinal -" + new Boolean(var.isFinal()).toString().toUpperCase() + "- )");
        //os.println("\t(isStatic -" + new Boolean(var.isStatic()).toString().toUpperCase() + "- )");
        os.println("\t(statute " + var.getStatute() + " )");
        Location loc = var.getLocation();
        printLocation(loc);
    }

    private void printVariableFullName(Variable var) {
        if (var instanceof Parameter)
            os.println("\t(uniqueName \"" + ((Parameter) var).getScope().getFullName() + "." + var.getName() + "\")");
        if (var instanceof LocalVariable) {
            Body scope = ((LocalVariable) var).getScope();
            if (scope instanceof FunctionBody)
                os.println("\t(uniqueName \"" + ((FunctionBody) scope).getScope().getFullName() + "." + var.getName() + var.getLocation().getStartLine() + "\")");
            else
                os.println("\t(uniqueName \"" + ((InitializerBody) scope).getScope().getFullName() + "." + var.getName() + var.getLocation().getStartLine() + "\")");
        }
        if (var instanceof Attribute)
            os.println("\t(uniqueName \"" + ((Attribute) var).getScope().getFullName() + "." + var.getName() + "\")");
    }

    public void visitAccess(Access a) {
        os.println("(Access " + a.getElementID());
        Variable accessedVariable = a.getVariable();
        printVariableFullName(accessedVariable);
        Body body = a.getScope();
        os.println("\t(accessedIn " + body.getElementID() + ")");
        os.println("\t(accesses_number " + a.getCount() + ")");
        os.println(")");
        os.println();
    }

    public void visitCall(Call c) {
        os.println("(Call " + c.getElementID());
        os.println("\t(uniqueName \"" + c.getFunction().getFullName() + "\")");
        os.println("\t(invokedIn " + c.getScope().getElementID() + ")");
        os.println("\t(invocations_number " + c.getCount() + ")");
        os.println(")");
        os.println();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            java.lang.System.out.println("Usage: java MemoriaCDIFExporter config_file");
            java.lang.System.exit(1);
        }
        try {
            ConfigFileReader cfr = new ConfigFileReader(args[0]);
            String sources, cache, libraries, cdif_file, error_file;
            sources = cfr.readLine();
            while (sources != null) {
                cache = cfr.readLine();
                libraries = cfr.readLine();
                cdif_file = cfr.readLine();
                error_file = cfr.readLine();
                File err = new File(error_file);
                err.createNewFile();
                Logger errorLogger = new Logger(new FileOutputStream(err));
                java.lang.System.setOut(errorLogger);
                java.lang.System.setErr(errorLogger);
                makeOneCDIF(sources, cache, libraries, cdif_file);
                errorLogger.close();
                sources = cfr.readLine();
            }
            cfr.close();
        } catch (Exception e) {
            e.printStackTrace();
            java.lang.System.out.println(e);
        }
    }

    private static void makeOneCDIF(String source_path, String cache_path, String additional_library_path, String cdif_file_name) {
        try {
            lrg.memoria.importer.recoder.JavaModelLoader model = new JavaModelLoader(source_path, cache_path, additional_library_path, null);
            lrg.memoria.core.System mySystem;
            mySystem = model.getSystem();
            MemoriaCDIFExporter exporter = new MemoriaCDIFExporter(mySystem);
            try {
                java.io.File file = new java.io.File(cdif_file_name);
                java.lang.System.out.println("Writing the CDIF file for the path: " + source_path);
                exporter.exportToStream(new PrintStream(new FileOutputStream(file)));
            } catch (Exception e) {
                e.printStackTrace();
                java.lang.System.out.println(e);
            }

        } catch (Exception pex) {
            java.lang.System.out.println("Error while parsing the sources !!!\nTable generation aborted !!!");
            java.lang.System.exit(6);
        }
    }
}

