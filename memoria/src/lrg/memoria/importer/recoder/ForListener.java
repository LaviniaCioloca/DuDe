package lrg.memoria.importer.recoder;

import recoder.java.ProgramElement;
import recoder.java.statement.For;
import lrg.memoria.core.CodeStripe;

public class ForListener extends StripeContainerListener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.ForListener", new Factory());
    }

    private ForListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new ForListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }


    public void enterModelComponent(ProgramElement pe) {
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
        mer.addLoop();
        mer.addStatements(1);
        mer.updateNestingLevel(1);

        ModelRepository mr=DefaultModelRepository.getModelRepository(null);
        CodeStripe cs=new CodeStripe(mr.getCurrentStripe());
        cs.setAccess(CodeStripe.NONSTATIC_STRIPE);
        cs.setSignature(CodeStripe.FOR);
        setActiveStripe(cs,mr,pe,((For)pe).getBody(),((For)pe).getBody());
    }

    public void leaveModelComponent(ProgramElement pe) {
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();

        restoreStripe(DefaultModelRepository.getModelRepository(null));
        mer.updateNestingLevel(-1);
    }
}
