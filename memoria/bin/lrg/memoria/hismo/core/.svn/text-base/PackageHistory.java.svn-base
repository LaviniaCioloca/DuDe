package lrg.memoria.hismo.core;

import lrg.memoria.core.Class;
import lrg.memoria.core.ModelElementList;
import lrg.memoria.core.Type;

public class PackageHistory extends AbstractHistory {

    ClassHistoryGroup classHistories;

    public PackageHistory() {
        initializeInnerHistories();
    }

    public PackageHistory(VersionsList versions) {
        super(versions);
    }

    protected void initializeInnerHistories() {
        classHistories = new ClassHistoryGroup();
    }

    public ClassHistoryGroup getClassHistories() {
        return classHistories;
    }

    protected void updateInnerHistories(AbstractVersion version) {
        ModelElementList<Type> currentTypes = ((PackageVersion) version).getAllTypesList();
        ClassVersion currentClassVersion;
        String currentClassName;
        AbstractHistory currentHistory;
        for (Type currentType : currentTypes) {
            if (currentType instanceof Class) {
                Class currentClass = (Class) currentType;
                currentClassVersion = new ClassVersion(version.versionName(), currentClass);
                currentClassName = currentClass.getFullName();
                currentHistory = classHistories.get(currentClassName);
                if (currentHistory == null) {
                    currentHistory = new ClassHistory();
                    currentHistory.addVersion(currentClassVersion);
                    classHistories.put(currentHistory);
                } else
                    currentHistory.addVersion(currentClassVersion);
            }
        }
    }
}
