package lrg.memoria.importer.recoder;

import recoder.java.ProgramElement;
import recoder.java.declaration.InterfaceDeclaration;

public class InterfaceDeclarationListener extends TypeDeclarationListener {
    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.InterfaceDeclarationListener", new Factory());
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new InterfaceDeclarationListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    private InterfaceDeclarationListener() {
    }

    public void enterModelComponent(ProgramElement pe) {
        InterfaceDeclaration td = (InterfaceDeclaration) pe;
        buildClassFromDeclaration(td);
        super.enterModelComponent(pe);
    }
}
