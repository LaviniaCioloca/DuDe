package lrg.memoria.hismo.core;

public class LocalVariableHistory extends VariableHistory {
    private FunctionHistory functionHistory;

    public LocalVariableHistory(FunctionHistory fh) {
        functionHistory = fh;
        initializeInnerHistories();
    }

    public LocalVariableHistory(VersionsList versions) {
        super(versions);
    }

    protected void initializeInnerHistories() {}

    protected void updateInnerHistories(AbstractVersion version) {}
}
