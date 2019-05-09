package lrg.memoria.hismo.core;

import lrg.memoria.core.GlobalFunction;

public class GlobalFunctionVersion extends GlobalFunction implements AbstractVersion {
    private String versionName;

    public GlobalFunctionVersion(String versionName, GlobalFunction gf) {
        super(gf);
        this.versionName = versionName;
    }

    public String versionName() {
        return versionName;
    }

    public int compareTo(Object ver2) {
        return versionName.compareTo(((AbstractVersion)ver2).versionName());
    }
}
