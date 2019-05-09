package lrg.memoria.importer.recoder;

import recoder.java.ProgramElement;

/**
 * Created by IntelliJ IDEA.
 * User: ratiud
 * Date: Mar 16, 2004
 * Time: 6:25:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogicalOrListener implements Listener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.LogicalOrListener", new LogicalOrListener.Factory());
    }

    private LogicalOrListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new LogicalOrListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        MetricsRepository mr = DefaultMetricRepository.getMetricRepository();
        mr.addLogicalOr();
    }

    public void leaveModelComponent(ProgramElement pe) {
        //void
    }
}
