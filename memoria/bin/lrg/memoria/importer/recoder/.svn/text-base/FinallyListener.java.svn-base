package lrg.memoria.importer.recoder;

import recoder.java.ProgramElement;
import recoder.java.statement.Finally;
import lrg.memoria.core.CodeStripe;

public class FinallyListener extends StripeContainerListener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.FinallyListener", new FinallyListener.Factory());
    }

    private FinallyListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new FinallyListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        ModelRepository mr=DefaultModelRepository.getModelRepository(null);
        CodeStripe cs=new CodeStripe(mr.getCurrentStripe());
        cs.setAccess(CodeStripe.STATIC_SCOPE);
        cs.setSignature(CodeStripe.FINALLY);
        setActiveStripe(cs,mr,pe,((Finally)pe).getBody(),((Finally)pe).getBody());
    }

    public void leaveModelComponent(ProgramElement pe) {
        restoreStripe(DefaultModelRepository.getModelRepository(null));
    }
}
