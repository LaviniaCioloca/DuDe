package lrg.memoria.hismo.core;


public class AttributeHistoryGroup extends AbstractHistoryGroup {

    public AbstractHistory createHistory(VersionsList versionsList) {
        return new AttributeHistory(versionsList);
    }

    public AbstractHistoryGroup createHistoryGroup() {
        return new AttributeHistoryGroup();
    }
}