package lrg.memoria.importer.recoder;

import lrg.memoria.core.Location;
import recoder.java.ProgramElement;
import recoder.java.reference.MethodReference;

public class MethodReferenceListener implements Listener {
    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.MethodReferenceListener", new Factory());
    }

    private static Listener listener;

    private MethodReferenceListener() {
    }

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new MethodReferenceListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    /*!
     * \todo The method references are not considered unless they are in a body. Otherwise the call
     *       is ignored.
     */
    public void enterModelComponent(ProgramElement pe) {
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);
        /*
        lrg.memoria.core.Body mmmb = mr.getCurrentBody();
        if (mmmb != null) {
            MethodReference metr = (MethodReference) pe;
            lrg.memoria.core.Method mmmm = ReferenceConverter.getMethod(metr);
            lrg.memoria.core.Call call = mr.addCall(mmmm, mmmm, mmmb);
            Location loc = new Location(mr.getCurrentFile());
            loc.setStartLine(metr.getStartPosition().getLine());
            loc.setStartChar(metr.getStartPosition().getColumn());
            loc.setEndLine(metr.getEndPosition().getLine());
            loc.setEndChar(metr.getEndPosition().getColumn());
            call.addInstance(loc);
        }
        */

        lrg.memoria.core.CodeStripe cs=mr.getCurrentStripe();
        if (cs!=null) {
            MethodReference metr = (MethodReference) pe;
            lrg.memoria.core.Method mmmm = ReferenceConverter.getMethod(metr);
            Location loc = new Location(mr.getCurrentFile());
            lrg.memoria.core.Call call = mr.addCall(mmmm, mmmm, cs);
            loc.setStartLine(metr.getFirstElement().getStartPosition().getLine());
            loc.setStartChar(metr.getFirstElement().getStartPosition().getColumn());
            loc.setEndLine(metr.getLastElement().getEndPosition().getLine());
            loc.setEndChar(metr.getLastElement().getEndPosition().getColumn());
            call.addInstance(cs.getRelPosOf(loc));
        }

    }

    public void leaveModelComponent(ProgramElement pe) {
    }
}
