package lrg.memoria.hismo.core;

import lrg.memoria.core.Body;
import lrg.memoria.core.Function;
import lrg.memoria.core.LocalVariable;

import java.util.ArrayList;

public abstract class FunctionHistory extends AbstractHistory {

    LocalVariableHistoryGroup localVariableHistories;

    public FunctionHistory() {
        initializeInnerHistories();
    }

    public FunctionHistory(VersionsList versions) {
        super(versions);
    }

    public LocalVariableHistoryGroup getLocalVariableHistories() {
        return localVariableHistories;
    }

    protected void initializeInnerHistories() {
        localVariableHistories = new LocalVariableHistoryGroup();
    }

    protected void updateInnerHistories(AbstractVersion version) {
        Body body = ((Function) version).getBody();
        ArrayList<LocalVariable> currentLocalVariables = body.getLocalVarList();
        LocalVariableVersion currentLocalVariableVersion;
        String currentLocalVariableName;
        AbstractHistory currentHistory;
        for (LocalVariable lv : currentLocalVariables) {
            currentLocalVariableVersion = new LocalVariableVersion(version.versionName(), lv);
            currentLocalVariableName = lv.getFullName();
            currentHistory = localVariableHistories.get(currentLocalVariableName);
            if (currentHistory == null) {
                currentHistory = new LocalVariableHistory(this);
                currentHistory.addVersion(currentLocalVariableVersion);
                localVariableHistories.put(currentHistory);
            } else
                currentHistory.addVersion(currentLocalVariableVersion);
        }
    }
}
