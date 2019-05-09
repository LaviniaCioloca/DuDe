package lrg.memoria.importer.recoder;

import recoder.java.ProgramElement;
import lrg.memoria.core.CodeStripe;

public class LogicalAndListener implements Listener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.LogicalAndListener", new Factory());
    }

    private LogicalAndListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new LogicalAndListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        MetricsRepository mr = DefaultMetricRepository.getMetricRepository();
        mr.addLogicalAnd();
    }

    public void leaveModelComponent(ProgramElement pe) {
        //void
    }
}
