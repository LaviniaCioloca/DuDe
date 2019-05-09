package lrg.memoria.importer.recoder;

import recoder.java.CompilationUnit;
import recoder.java.ProgramElement;

public class CompilationUnitListener implements Listener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.CompilationUnitListener", new Factory());
    }

    private static Listener listener;

    private CompilationUnitListener() {
    }

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new CompilationUnitListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);
        mr.setCurrentFile(mr.addFile(pe, ((CompilationUnit) pe).getName()));
        mr.setCurrentPackage(lrg.memoria.core.Package.getAnonymousPackage());
    }

    public void leaveModelComponent(ProgramElement pe) {
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);
        mr.setCurrentFile(lrg.memoria.core.File.getUnknownFile());
    }
}
