package lrg.memoria.importer.recoder;

import lrg.memoria.core.DataAbstraction;

public class ExtendsListener extends InheritanceSpecificationListener {
    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.ExtendsListener", new Factory());
    }

    private ExtendsListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new ExtendsListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    protected void setInheritance(lrg.memoria.core.Class curClass, DataAbstraction superType) {
        if (curClass.isInterface())
            curClass.addInterface(superType);
        else
            curClass.addAncestor(superType);
    }
}
