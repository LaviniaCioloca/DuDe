package lrg.memoria.hismo.core;


public class MethodHistory extends FunctionHistory {

    private ClassHistory classHistory;

    public MethodHistory(ClassHistory ch) {
        classHistory = ch;
    }

    public MethodHistory(VersionsList versions) {
        super(versions);
    }

    public ClassHistory getClassHistory() {
        return classHistory;
    }

    /*protected void initializeInnerHistories() {
        super.initializeInnerHistories();
    }

    protected void updateInnerHistories(AbstractVersion version) {
        super.updateInnerHistories(version);        
    } */
}