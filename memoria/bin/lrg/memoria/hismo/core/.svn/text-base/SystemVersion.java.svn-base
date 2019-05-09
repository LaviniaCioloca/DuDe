package lrg.memoria.hismo.core;

import lrg.memoria.core.System;

public class SystemVersion extends System implements AbstractVersion {

    private String versionName;

    public SystemVersion(String versionName, System system) {
        super(system);
        this.versionName = versionName;
    }

    public String versionName() {
        return versionName;
    }

    public int compareTo(Object ver2) {
        return versionName.compareTo(((AbstractVersion) ver2).versionName());
    }
}
