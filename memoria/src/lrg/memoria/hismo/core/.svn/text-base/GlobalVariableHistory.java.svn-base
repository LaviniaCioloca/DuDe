package lrg.memoria.hismo.core;

public class GlobalVariableHistory extends VariableHistory {
    private NamespaceHistory namespaceHistory;

    public GlobalVariableHistory(NamespaceHistory nsh) {
        namespaceHistory = nsh;
        initializeInnerHistories();
    }

    public GlobalVariableHistory(VersionsList versions) {
        super(versions);
    }

    public NamespaceHistory getNamespaceHistory() {
            return namespaceHistory;
    }

    protected void initializeInnerHistories() {}

    protected void updateInnerHistories(AbstractVersion version) {}
}
