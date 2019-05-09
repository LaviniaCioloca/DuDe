package lrg.memoria.hismo.core;


public class AttributeHistory extends VariableHistory {
    private ClassHistory classHistory;

    public AttributeHistory(ClassHistory ch) {
        classHistory = ch;
    }

    public AttributeHistory(VersionsList versions) {
        super(versions);
    }

    public ClassHistory getClassHistory() {
        return classHistory;
    }

    protected void initializeInnerHistories() {
    }

    protected void updateInnerHistories(AbstractVersion version) {
        //do nothing
    }
}
