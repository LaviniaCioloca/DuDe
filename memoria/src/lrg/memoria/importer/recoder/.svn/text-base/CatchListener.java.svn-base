package lrg.memoria.importer.recoder;

import recoder.java.ProgramElement;
import recoder.java.statement.Catch;
import lrg.memoria.core.CodeStripe;

public class CatchListener extends StripeContainerListener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.CatchListener", new Factory());
    }

    private CatchListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new CatchListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
        mer.addException();
        mer.updateNestingLevel(1);

        ModelRepository mr=DefaultModelRepository.getModelRepository(null);
        CodeStripe cs=new CodeStripe(mr.getCurrentStripe());
        cs.setAccess(CodeStripe.STATIC_SCOPE);
        cs.setSignature(CodeStripe.CATCH);
        setActiveStripe(cs,mr,pe,((Catch)pe).getBody(),((Catch)pe).getBody());
    }

    public void leaveModelComponent(ProgramElement pe) {
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
        mer.updateNestingLevel(-1);
        restoreStripe(DefaultModelRepository.getModelRepository(null));
    }
}

