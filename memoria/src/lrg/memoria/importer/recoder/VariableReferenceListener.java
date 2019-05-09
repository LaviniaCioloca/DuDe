package lrg.memoria.importer.recoder;

import lrg.memoria.core.Location;
import recoder.java.ProgramElement;
import recoder.java.Reference;
import recoder.java.expression.Assignment;
import recoder.java.reference.VariableReference;

public class VariableReferenceListener implements Listener {
    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.VariableReferenceListener", new Factory());
    }

    private static Listener listener;

    private VariableReferenceListener() {
    }

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new VariableReferenceListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        VariableReference vr = (VariableReference) pe;
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);
        /*
        lrg.memoria.core.Body mmmb = mr.getCurrentBody();
        lrg.memoria.core.Variable mmmv = ReferenceConverter.getVariable(vr);
        lrg.memoria.core.Access access = mr.addAccess(mmmv, mmmv, mmmb);
        */
        lrg.memoria.core.CodeStripe cs = mr.getCurrentStripe();
        try {
        if (cs!=null) { // ignore initialization of members of anonymous classes 
            lrg.memoria.core.Variable mmmv = ReferenceConverter.getVariable(vr);
            lrg.memoria.core.Access access = mr.addAccess(mmmv, mmmv, cs);

            Location loc = new Location(mr.getCurrentFile());
            loc.setStartLine(vr.getFirstElement().getStartPosition().getLine());
            loc.setStartChar(vr.getFirstElement().getStartPosition().getColumn());
            loc.setEndLine(vr.getLastElement().getEndPosition().getLine());
            loc.setEndChar(vr.getLastElement().getEndPosition().getColumn());
            Reference ref = vr;
            while (ref.getASTParent() instanceof Reference)
                ref = (Reference) ref.getASTParent();
            if (ref.getASTParent() instanceof Assignment && ref.getASTParent().getChildAt(0) == ref)
                access.addInstance(cs.getRelPosOf(loc), lrg.memoria.core.Access.WRITE);
            else
                access.addInstance(cs.getRelPosOf(loc), lrg.memoria.core.Access.READ);
        }
        } catch(IllegalArgumentException e) { System.err.println(e); }    
    }

    public void leaveModelComponent(ProgramElement pe) {
    }
}
