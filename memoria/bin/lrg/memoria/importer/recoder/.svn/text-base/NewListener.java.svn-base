package lrg.memoria.importer.recoder;

import lrg.memoria.core.*;
import recoder.java.ProgramElement;
import recoder.java.expression.operator.New;

public class NewListener extends StripeContainerListener {
    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.NewListener", new Factory());
    }

    private NewListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener == null)
                return (listener = new NewListener());
            else
                return listener;
        }

        public void cleanUp() {
            listener = null;
        }
    }

    /*!
     *  \todo   Problems with the initialization of attributes. To which body do the following calls belong ?
     *          class Cls {
     *             ArrayList al = new ArrayList();
     *             ArrayList al1 = new ArrayList(new ArrayList());
     *          }
     *          Current solution:
     *          if mr.currentBody == null then ignore the call
     */
    public void enterModelComponent(ProgramElement pe) {
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);

        lrg.memoria.core.CodeStripe cs=mr.getCurrentStripe();
        if (cs!=null) {
            New cr = (New) pe;
            lrg.memoria.core.Method met = ReferenceConverter.getConstructor(cr);
            met.setConstructor();
            if (((DataAbstraction) met.getScope()).getStatute() == Statute.LIBRARY)
                met.setStatute(Statute.LIBRARY);
            Call call = mr.addCall(met, met, cs);
            Location loc = new Location(mr.getCurrentFile());
            loc.setStartLine(cr.getStartPosition().getLine());
            loc.setStartChar(cr.getStartPosition().getColumn());
            loc.setEndLine(cr.getEndPosition().getLine());
            loc.setEndChar(cr.getEndPosition().getColumn());
            call.addInstance(cs.getRelPosOf(loc));

            // create the new Stripe
            cs=new CodeStripe(mr.getCurrentStripe());
            cs.setAccess(CodeStripe.STATIC_CONTENT);
            cs.setSignature(CodeStripe.NEW);
            setActiveStripe(cs,mr,pe,null,null); // since the content is static
            // we don't care where it starts or where it ends
        }

    }

    public void leaveModelComponent(ProgramElement pe) {
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);

        if (mr.getCurrentStripe() != null)
            restoreStripe(DefaultModelRepository.getModelRepository(null));
    }
}
