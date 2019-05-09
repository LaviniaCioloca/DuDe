package lrg.memoria.hismo.core;

import lrg.memoria.core.Class;
import lrg.memoria.core.*;

public class NamespaceHistory extends AbstractHistory {

    ClassHistoryGroup classHistories;
    GlobalFunctionHistoryGroup globalFunctionHistories;
    GlobalVariableHistoryGroup globalVariableHistories;

    public NamespaceHistory() {
        initializeInnerHistories();
    }

    public NamespaceHistory(VersionsList versions) {
        super(versions);
    }

    protected void initializeInnerHistories() {
        classHistories = new ClassHistoryGroup();
        globalFunctionHistories = new GlobalFunctionHistoryGroup();
        globalVariableHistories = new GlobalVariableHistoryGroup();
    }

    public ClassHistoryGroup getClassHistories() {
        return classHistories;
    }

    public GlobalFunctionHistoryGroup getGlobalFunctionHistories() {
        return globalFunctionHistories;
    }

    public GlobalVariableHistoryGroup getGlobalVariableHistories() {
        return globalVariableHistories;
    }

    protected void updateInnerHistories(AbstractVersion version) {
        updateTypesHistories((NamespaceVersion) version);
        updateGlobalFunctionHistories((NamespaceVersion) version);
        updateGlobalVariableHistories((NamespaceVersion) version);
    }

    private void updateGlobalFunctionHistories(NamespaceVersion version) {
        ModelElementList<GlobalFunction> currentGlobalFunctions = version.getGlobalFunctionsList();
        GlobalFunctionVersion currentGlobalFunctionVersion;
        String currentGlobalFunctionName;
        AbstractHistory currentHistory;
        for (GlobalFunction currentGlobalFunction : currentGlobalFunctions) {
            currentGlobalFunctionVersion = new GlobalFunctionVersion(version.versionName(), currentGlobalFunction);
            currentGlobalFunctionName = currentGlobalFunction.getFullName();
            currentHistory = globalFunctionHistories.get(currentGlobalFunctionName);
            if (currentHistory == null) {
                currentHistory = new GlobalFunctionHistory(this);
                currentHistory.addVersion(currentGlobalFunctionVersion);
                globalFunctionHistories.put(currentHistory);
            } else
                currentHistory.addVersion(currentGlobalFunctionVersion);
        }
    }

    private void updateGlobalVariableHistories(NamespaceVersion version) {
        ModelElementList<GlobalVariable> currentGlobalVariables = version.getGlobalVariablesList();
        GlobalVariableVersion currentGlobalVariableVersion;
        String currentGlobalVariableName;
        AbstractHistory currentHistory;
        for (GlobalVariable currentGlobalVariable : currentGlobalVariables) {
            currentGlobalVariableVersion = new GlobalVariableVersion(version.versionName(), currentGlobalVariable);
            currentGlobalVariableName = currentGlobalVariable.getFullName();
            currentHistory = globalVariableHistories.get(currentGlobalVariableName);
            if (currentHistory == null) {
                currentHistory = new GlobalVariableHistory(this);
                currentHistory.addVersion(currentGlobalVariableVersion);
                globalVariableHistories.put(currentHistory);
            } else
                currentHistory.addVersion(currentGlobalVariableVersion);
        }
    }

    private void updateTypesHistories(NamespaceVersion version) {
        ModelElementList<Type> currentTypes = version.getAllTypesList();
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
                    currentHistory = new ClassHistory(this);
                    currentHistory.addVersion(currentClassVersion);
                    classHistories.put(currentHistory);
                } else
                    currentHistory.addVersion(currentClassVersion);
            }
        }
    }

}
