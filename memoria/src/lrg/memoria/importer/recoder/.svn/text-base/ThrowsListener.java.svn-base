package lrg.memoria.importer.recoder;

import recoder.java.ProgramElement;
import recoder.java.reference.TypeReference;
import recoder.java.declaration.Throws;
import recoder.list.generic.ASTList;

public class ThrowsListener implements Listener {
    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.ThrowsListener", new Factory());
    }

    private ThrowsListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener == null)
                return (listener = new ThrowsListener());
            else
                return listener;
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        Throws t = (Throws) pe;
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);
        lrg.memoria.core.Method mmmm = mr.getCurrentMethod();

        ASTList<TypeReference> trl = t.getExceptions();
        for (int i = 0; i < trl.size(); i++)
            mmmm.addException(ReferenceConverter.getClassType(trl.get(i)));
    }

    public void leaveModelComponent(ProgramElement pe) {
    }
}
