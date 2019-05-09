package lrg.memoria.importer.recoder;

import recoder.java.ProgramElement;
import recoder.java.statement.Do;
import lrg.memoria.core.CodeStripe;

public class DoListener extends StripeContainerListener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.DoListener", new Factory());
    }

    private DoListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new DoListener());
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
        cs.setSignature(CodeStripe.DO);
        setActiveStripe(cs,mr,pe,((Do)pe).getBody(),((Do)pe).getBody());
    }

    public void leaveModelComponent(ProgramElement pe) {
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
        mer.updateNestingLevel(-1);
        restoreStripe(DefaultModelRepository.getModelRepository(null));
    }
}

