package lrg.memoria.importer.recoder;

import recoder.java.ProgramElement;
import recoder.java.statement.Try;
import lrg.memoria.core.CodeStripe;

public class TryListener extends  StripeContainerListener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.TryListener", new Factory());
    }

    private TryListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new TryListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
        mer.updateNestingLevel(1);

        ModelRepository mr=DefaultModelRepository.getModelRepository(null);
        CodeStripe cs=new CodeStripe(mr.getCurrentStripe());
        cs.setAccess(CodeStripe.NONSTATIC_STRIPE);
        cs.setSignature(CodeStripe.TRY);
        setActiveStripe(cs,mr,pe,((Try)pe).getStatementAt(0),
                ((Try)pe).getStatementAt(((Try)pe).getStatementCount()-1));
    }

    public void leaveModelComponent(ProgramElement pe) {
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
        mer.updateNestingLevel(-1);
        restoreStripe(DefaultModelRepository.getModelRepository(null));
    }
}