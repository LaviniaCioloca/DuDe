package lrg.memoria.hismo.core;

import lrg.memoria.core.GlobalVariable;

public class GlobalVariableVersion extends GlobalVariable implements AbstractVersion {
    private String versionName;

    public GlobalVariableVersion(String versionName, GlobalVariable gv) {
        super(gv);
        this.versionName = versionName;
    }

    public String versionName() {
        return versionName;
    }

    public int compareTo(Object ver2) {
        return versionName.compareTo(((AbstractVersion)ver2).versionName());
    }
}

