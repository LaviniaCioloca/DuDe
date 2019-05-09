package lrg.memoria.hismo.core;

public class PackageVersion extends lrg.memoria.core.Package implements AbstractVersion {

    private String versionName;

    public PackageVersion(String versionName, lrg.memoria.core.Package pack) {
        super(pack);
        this.versionName = versionName;
    }

    public String versionName() {
        return versionName;
    }

    public int compareTo(Object ver2) {
        return versionName.compareTo(((AbstractVersion)ver2).versionName());
    }
}
