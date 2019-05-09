package lrg.memoria.importer.recoder;

import recoder.CrossReferenceServiceConfiguration;
import recoder.bytecode.ClassFile;
import recoder.abstraction.ClassTypeContainer;
import recoder.java.Import;
import recoder.java.ProgramElement;
import recoder.java.reference.PackageReference;
import recoder.java.reference.ReferencePrefix;
import recoder.java.reference.TypeReference;

import java.util.Hashtable;
import java.util.List;

public class ImportListener implements Listener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.ImportListener", new ImportListener.Factory());
    }

    private ImportListener() {
        getAllLibraryPackages();
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new ImportListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    private Hashtable name2package;

    public void enterModelComponent(ProgramElement pe) {
        Import imp = (Import) pe;
        ReferencePrefix tri = imp.getReference();
        if (tri instanceof TypeReference)
            tri = ((TypeReference) tri).getPackageReference();
        if (tri == null)
            return;
        String packName;
        if (tri instanceof PackageReference) //added for incomplete code
            packName = ((PackageReference) tri).getName();
        else
            return;
        PackageReference prefix = (PackageReference) ((PackageReference) tri).getReferencePrefix();
        while (prefix != null) {
            packName = prefix.getName() + "." + packName;
            prefix = (PackageReference) prefix.getReferencePrefix();
        }
        CrossReferenceServiceConfiguration crsc = JavaModelLoader.getCrossReferenceServiceConfiguration();
        if (name2package.get(packName) == null) {
            DefaultModelRepository dmr = DefaultModelRepository.getModelRepository(null);
            dmr.addPackage(null, packName);
        }
    }

    public void leaveModelComponent(ProgramElement pe) {
    }

    private void getAllLibraryPackages() {
        name2package = new Hashtable();
        CrossReferenceServiceConfiguration crsc = JavaModelLoader.getCrossReferenceServiceConfiguration();
        List<ClassFile> cf = crsc.getClassFileRepository().getKnownClassFiles();
        for (int i = cf.size() - 1; i >= 0; i -= 1) {
            ClassTypeContainer ctc = cf.get(i).getContainer();
            if (ctc instanceof recoder.abstraction.Package) {
                name2package.put(ctc.getFullName(), ctc);
            }
        }
    }
}


