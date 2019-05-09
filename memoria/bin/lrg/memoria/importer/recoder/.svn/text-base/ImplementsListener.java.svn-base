package lrg.memoria.importer.recoder;

import lrg.memoria.core.DataAbstraction;

public class ImplementsListener extends InheritanceSpecificationListener {
    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.ImplementsListener", new Factory());
    }

    private ImplementsListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new ImplementsListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    protected void setInheritance(lrg.memoria.core.Class curClass, DataAbstraction superType) {
        curClass.addInterface(superType);
    }
}
