package lrg.memoria.hismo.core;

public class GlobalFunctionHistoryGroup extends AbstractHistoryGroup {

    public AbstractHistory createHistory(VersionsList versionsList) {
        return new GlobalFunctionHistory(versionsList);
    }

    public AbstractHistoryGroup createHistoryGroup() {
        return new GlobalFunctionHistoryGroup();
    }
}
