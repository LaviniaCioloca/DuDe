package lrg.memoria.importer.recoder;

import recoder.java.ProgramElement;
import recoder.java.statement.Default;
import lrg.memoria.core.CodeStripe;

public class DefaultListener extends StripeContainerListener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.DefaultListener", new DefaultListener.Factory());
    }

    private DefaultListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new DefaultListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        ModelRepository mr=DefaultModelRepository.getModelRepository(null);
        CodeStripe cs=new CodeStripe(mr.getCurrentStripe());
        cs.setAccess(CodeStripe.STATIC_SCOPE);
        cs.setSignature(CodeStripe.DEFAULT);

        Default d=(Default)pe;
        cs.addAtomicStatements(d.getStatementCount());

        if (d.getStatementCount()>0) {
            setActiveStripe(cs,mr,pe,d.getStatementAt(0),d.getStatementAt(d.getStatementCount()-1));
        } else {
            cs.setAccess(CodeStripe.STATIC_STRIPE);
            setActiveStripe(cs,mr,pe,null,null);
        }

    }

    public void leaveModelComponent(ProgramElement pe) {
            restoreStripe(DefaultModelRepository.getModelRepository(null));
    }

}
