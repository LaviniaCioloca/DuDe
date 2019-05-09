package lrg.memoria.hismo.core;

import lrg.memoria.core.Namespace;

public class NamespaceVersion extends Namespace implements AbstractVersion {

    private String versionName;

    public NamespaceVersion(String versionName, Namespace nsp) {
        super(nsp);
        this.versionName = versionName;
    }

    public String versionName() {
        return versionName;
    }

    public int compareTo(Object ver2) {
        return versionName.compareTo(((AbstractVersion)ver2).versionName());
    }

}
