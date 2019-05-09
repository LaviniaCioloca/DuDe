package lrg.memoria.importer.recoder;

import recoder.java.ProgramElement;

public class ReturnListener implements Listener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.ReturnListener", new Factory());
    }

    private static Listener listener;

    private ReturnListener() {
    }

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new ReturnListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
        mer.addExit();
    }

    public void leaveModelComponent(ProgramElement pe) {
    }
}
