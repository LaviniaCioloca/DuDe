package lrg.memoria.importer.recoder;

import recoder.java.ProgramElement;
import recoder.java.statement.Else;
import lrg.memoria.core.CodeStripe;

public class ElseListener extends StripeContainerListener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.ElseListener", new Factory());
    }

    private ElseListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new ElseListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
        mer.addStatements(1);

        ModelRepository mr=DefaultModelRepository.getModelRepository(null);
        CodeStripe cs=new CodeStripe(mr.getCurrentStripe());
        cs.setAccess(CodeStripe.STATIC_SCOPE);
        cs.setSignature(CodeStripe.ELSE);
        setActiveStripe(cs,mr,pe,((Else)pe).getBody(),((Else)pe).getBody());
    }

    public void leaveModelComponent(ProgramElement pe) {
        restoreStripe(DefaultModelRepository.getModelRepository(null));
    }
}
