package lrg.memoria.importer.recoder;

import recoder.java.PackageSpecification;
import recoder.java.ProgramElement;

public class PackageSpecificationListener implements Listener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.PackageSpecificationListener", new Factory());
    }

    private static Listener listener;

    private PackageSpecificationListener() {
    }

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new PackageSpecificationListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        lrg.memoria.core.Package mmmp = ReferenceConverter.getPackage(((PackageSpecification) pe).getPackageReference());
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);
        mmmp.setStatute(lrg.memoria.core.Statute.NORMAL);
        mr.setCurrentPackage(mmmp);
    }

    public void leaveModelComponent(ProgramElement pe) {
    }
}
