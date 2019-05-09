package lrg.memoria.importer.recoder;

import recoder.java.ProgramElement;
import recoder.java.statement.Case;
import lrg.memoria.core.CodeStripe;

public class CaseListener extends StripeContainerListener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.CaseListener", new Factory());
    }

    private CaseListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new CaseListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
        recoder.java.statement.Case currentCase = (recoder.java.statement.Case)pe;
        if (!currentCase.getBody().isEmpty()) {
            mer.updateNestingLevel(1);
            mer.addDecision();
        }

        ModelRepository mr=DefaultModelRepository.getModelRepository(null);
        CodeStripe cs=new CodeStripe(mr.getCurrentStripe());
        cs.setAccess(CodeStripe.STATIC_SCOPE);
        cs.setSignature(CodeStripe.CASE);
        cs.addAtomicStatements(currentCase.getStatementCount());
        Case cas=(Case)pe;
        if (cas.getStatementCount()>0) {
            setActiveStripe(cs,mr,pe,cas.getStatementAt(0),cas.getStatementAt(cas.getStatementCount()-1));
        } else {
            cs.setAccess(CodeStripe.STATIC_STRIPE);
            setActiveStripe(cs,mr,pe,null,null);
        }
    }

    public void leaveModelComponent(ProgramElement pe) {
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
        mer.updateNestingLevel(-1);

        restoreStripe(DefaultModelRepository.getModelRepository(null));
    }
}