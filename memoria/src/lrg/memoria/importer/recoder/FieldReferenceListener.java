package lrg.memoria.importer.recoder;

import lrg.memoria.core.Location;
import recoder.java.ProgramElement;
import recoder.java.Reference;
import recoder.java.expression.Assignment;
import recoder.java.reference.FieldReference;

public class FieldReferenceListener implements Listener {
    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.FieldReferenceListener", new Factory());
    }

    private static Listener listener;

    private FieldReferenceListener() {
    }

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new FieldReferenceListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    /*!
     *  \todo   Problems with accesses from outside of all bodies. To which body do the following access belong ?
     *          class Cls {
     *             public static ExampleClass ec = new ExampleClass(System.out);
     *          }
     *          Current solution:
     *          if mr.currentBody == null then ignore the access
     */
    public void enterModelComponent(ProgramElement pe) {
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);
        /*
        lrg.memoria.core.Body mmmb = mr.getCurrentBody();
        if (mmmb != null) {
            FieldReference fr = (FieldReference) pe;
            lrg.memoria.core.Variable mmmv = ReferenceConverter.getField(fr);
            lrg.memoria.core.Access access = mr.addAccess(mmmv, mmmv, mmmb);
            Location loc = new Location(mr.getCurrentFile());
            loc.setStartLine(fr.getStartPosition().getLine());
            loc.setStartChar(fr.getStartPosition().getColumn());
            loc.setEndLine(fr.getEndPosition().getLine());
            loc.setEndChar(fr.getEndPosition().getColumn());
            Reference ref = fr;
            while (ref.getASTParent() instanceof Reference)
                ref = (Reference) ref.getASTParent();
            if (ref.getASTParent() instanceof Assignment && ref.getASTParent().getChildAt(0) == ref) { 
                    access.addInstance(loc, lrg.memoria.core.Access.WRITE);
            } else
                    access.addInstance(loc, lrg.memoria.core.Access.READ);
        } */

        lrg.memoria.core.CodeStripe cs = mr.getCurrentStripe();
        if (cs != null) {
            FieldReference fr = (FieldReference) pe;
            lrg.memoria.core.Variable mmmv = ReferenceConverter.getField(fr);
            lrg.memoria.core.Access access = mr.addAccess(mmmv, mmmv, cs);

            Location loc = new Location(mr.getCurrentFile());
            loc.setStartLine(fr.getFirstElement().getStartPosition().getLine());
            loc.setStartChar(fr.getFirstElement().getStartPosition().getColumn());
            loc.setEndLine(fr.getLastElement().getEndPosition().getLine());
            loc.setEndChar(fr.getLastElement().getEndPosition().getColumn());

            Reference ref = fr;
            while (ref.getASTParent() instanceof Reference)
                ref = (Reference) ref.getASTParent();
            if (ref.getASTParent() instanceof Assignment && ref.getASTParent().getChildAt(0) == ref) {
                    access.addInstance(cs.getRelPosOf(loc), lrg.memoria.core.Access.WRITE);
            } else
                    access.addInstance(cs.getRelPosOf(loc), lrg.memoria.core.Access.READ);
        }
    }

    public void leaveModelComponent(ProgramElement pe) {
    }
}
