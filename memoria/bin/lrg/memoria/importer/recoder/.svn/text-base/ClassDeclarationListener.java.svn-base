package lrg.memoria.importer.recoder;

import lrg.memoria.core.DataAbstraction;
import recoder.java.ProgramElement;
import recoder.java.declaration.ClassDeclaration;
import recoder.java.declaration.Extends;

public class ClassDeclarationListener extends TypeDeclarationListener {
    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.ClassDeclarationListener", new ClassDeclarationListener.Factory());
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new ClassDeclarationListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    private ClassDeclarationListener() {
    }

    public void enterModelComponent(ProgramElement pe) {
        ClassDeclaration td = (ClassDeclaration) pe;
        DataAbstraction mmmc = buildClassFromDeclaration(td);
        Extends ext = td.getExtendedTypes();
        if (ext == null)
            mmmc.addAncestor(lrg.memoria.core.Class.getHierarchyRootClass());
        super.enterModelComponent(pe);
    }
}
