package lrg.memoria.hismo.core;

import lrg.memoria.core.Class;

/**
 * Created by IntelliJ IDEA.
 * User: ratiud
 * Date: Jan 27, 2004
 * Time: 6:52:17 PM
 * To change this template use Options | File Templates.
 */
public class ClassVersion extends Class implements AbstractVersion {

    private String versionName;

    public ClassVersion(String versionName, Class cls) {
        super(cls);
        this.versionName = versionName;
    }

    public String versionName() {
        return versionName;
    }

    public int compareTo(Object ver2) {
        return versionName.compareTo(((AbstractVersion) ver2).versionName());
    }
}
