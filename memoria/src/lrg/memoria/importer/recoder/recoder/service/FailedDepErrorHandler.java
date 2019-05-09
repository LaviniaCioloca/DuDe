package lrg.memoria.importer.recoder.recoder.service;

import recoder.java.ProgramElement;
import recoder.service.DefaultErrorHandler;

import java.util.EventObject;

public class FailedDepErrorHandler extends DefaultErrorHandler {
    public FailedDepErrorHandler() {
        super(10000000);
    }

    protected boolean isReferingUnavailableCode(ProgramElement e) {
        return true;
    }

    public void modelUpdated(EventObject e) {
    }
}
