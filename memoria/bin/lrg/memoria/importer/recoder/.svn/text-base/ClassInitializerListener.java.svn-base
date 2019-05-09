package lrg.memoria.importer.recoder;

import lrg.memoria.core.Location;
import recoder.java.ProgramElement;
import recoder.java.declaration.ClassInitializer;

import java.util.Stack;

public class ClassInitializerListener implements Listener {

    private Stack oldBodies;

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.ClassInitializerListener", new Factory());
    }

    private ClassInitializerListener() {
        oldBodies = new Stack();
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener == null)
                return (listener = new ClassInitializerListener());
            else
                return listener;
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
        mer.resetAll();
        ClassInitializer ci = (ClassInitializer) pe;
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);
        lrg.memoria.core.Class mmmc = mr.getCurrentClass();

        lrg.memoria.core.InitializerBody ib = new lrg.memoria.core.InitializerBody(mmmc);
        if (ci.isStatic())
            ib.setStatic();
        mmmc.addInitializer(ib);


        MemoriaPrettyPrinter mpp = MemoriaPrettyPrinter.getMemoriaPrettyPrinter();
        ib.setSourceCode(mpp.getSource(ci));

        oldBodies.push(mr.getCurrentBody());
        mr.setCurrentBody(ib);
    }

    public void leaveModelComponent(ProgramElement pe) {
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);
        /*
        restoreStripe(mr,pe,
                ((ClassInitializer)pe).getStatementAt(((ClassInitializer)pe).getStatementCount()-1));
        */
        mr.setCurrentBody((lrg.memoria.core.Body) oldBodies.pop());
    }
}
