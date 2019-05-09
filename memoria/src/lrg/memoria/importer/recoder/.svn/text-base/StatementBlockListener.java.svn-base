package lrg.memoria.importer.recoder;

import lrg.memoria.core.CodeStripe;
import recoder.java.NonTerminalProgramElement;
import recoder.java.ProgramElement;
import recoder.java.StatementBlock;
import recoder.java.declaration.ClassInitializer;
import recoder.java.declaration.MethodDeclaration;

public class StatementBlockListener extends StripeContainerListener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.StatementBlockListener", new Factory());
    }

    private StatementBlockListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new StatementBlockListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        StatementBlock sb = (StatementBlock) pe;
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();

        mer.addStatements(sb.getStatementCount());

        ModelRepository mr = DefaultModelRepository.getModelRepository(null);

        CodeStripe cs;
        if (mr.getCurrentStripe() == null) {
            cs = new CodeStripe(mr.getCurrentBody());
            mr.getCurrentBody().setCodeStripe(cs);
            cs.addAtomicCyclo(1);
            cs.addAtomicStatements(1);
            cs.setAccess(CodeStripe.STATIC_SCOPE);
        } else {
            cs = new CodeStripe(mr.getCurrentStripe());
            cs.setAccess(CodeStripe.NONSTATIC_STRIPE);
        }

        cs.setSignature(CodeStripe.STATEMENT_BLOCK);
        cs.addAtomicStatements(sb.getStatementCount());

        CodeStripe old = mr.getCurrentStripe(); // importat to get this b4 we set the current one

        if (sb.getStatementCount() > 0) {
            setActiveStripe(cs, mr, pe, sb.getStatementAt(0), sb.getStatementAt(sb.getStatementCount() - 1));
        } else {
            cs.setAccess(CodeStripe.STATIC_CONTENT);
            setActiveStripe(cs, mr, pe, null, null);
        }

        if (old == null) {
            // MemoriaPrettyPrinter mpp = MemoriaPrettyPrinter.getMemoriaPrettyPrinter();
            CodeStripeSourcePrinter mpp = CodeStripeSourcePrinter.instance();


            String src = mpp.getSource(sb);

            try {
                cs.setSourceCode(src);
            } catch (IllegalArgumentException e) {
                if (sb.getStatementCount() > 0) throw e;
            }
        }
    }

    public void leaveModelComponent(ProgramElement pe) {
        NonTerminalProgramElement parent = pe.getASTParent();
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);
        lrg.memoria.core.Method mmm = mr.getCurrentMethod();
        if ((parent instanceof ClassInitializer || (parent instanceof MethodDeclaration) && !mmm.isAbstract())) {
            lrg.memoria.core.Body mmmb = mr.getCurrentBody();
            MetricsRepository mer = DefaultMetricRepository.getMetricRepository();

            mmmb.setNumberOfStatements(mer.getNumberOfStatements());

            StatementBlock sb = (StatementBlock) pe;
            int spos = sb.getStartPosition().getLine();
            int epos = sb.getEndPosition().getLine();

            mmmb.setNumberOfLines(epos - spos + 1);
            mmmb.setNumberOfComments(mer.getCommentsNumber());
            mmmb.setNumberOfDecisions(mer.getDecisions());
            mmmb.setNumberOfLoops(mer.getLoops());
            mmmb.setNumberOfExits(mer.getNumberOfExits());
            mmmb.setNumberOfExceptions(mer.getExceptions());
            mmmb.setMaxNestingLevel(mer.getMaxNestingLevel());
            mmmb.setCyclomaticNumber(mer.getCyclomatic());
        }

        StatementBlock sb = (StatementBlock) pe;
        restoreStripe(mr);

    }
}


