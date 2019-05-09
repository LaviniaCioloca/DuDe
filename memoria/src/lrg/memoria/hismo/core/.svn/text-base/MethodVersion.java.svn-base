package lrg.memoria.hismo.core;

import lrg.memoria.core.Method;

public class MethodVersion extends Method implements AbstractVersion {

    private String versionName;

    public MethodVersion(String versionName, Method meth) {
        super(meth);
        this.versionName = versionName;
    }

    public String versionName() {
        return versionName;
    }

    public int compareTo(Object ver2) {
        return versionName.compareTo(((AbstractVersion) ver2).versionName());
    }
}
