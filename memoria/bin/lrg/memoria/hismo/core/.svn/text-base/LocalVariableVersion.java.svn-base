package lrg.memoria.hismo.core;

import lrg.memoria.core.LocalVariable;

public class LocalVariableVersion extends LocalVariable implements AbstractVersion {
    private String versionName;

    public LocalVariableVersion(String versionName, LocalVariable lv) {
        super(lv);
        this.versionName = versionName;
    }

    public String versionName() {
        return versionName;
    }

    public int compareTo(Object ver2) {
        return versionName.compareTo(((AbstractVersion)ver2).versionName());
    }
}
