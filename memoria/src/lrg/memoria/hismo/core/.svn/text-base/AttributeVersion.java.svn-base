package lrg.memoria.hismo.core;

import lrg.memoria.core.Attribute;

public class AttributeVersion extends Attribute implements AbstractVersion {
    private String versionName;

    public AttributeVersion(String versionName, Attribute attr) {
        super(attr);
        this.versionName = versionName;
    }

    public String versionName() {
        return versionName;
    }

    public int compareTo(Object ver2) {
        return versionName.compareTo(((AbstractVersion)ver2).versionName());
    }
}
