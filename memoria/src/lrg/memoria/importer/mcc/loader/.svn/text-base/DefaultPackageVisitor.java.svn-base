package lrg.memoria.importer.mcc.loader;

import lrg.memoria.core.Statute;

public class DefaultPackageVisitor implements PackageVisitor {
    private Integer id;
    private String name;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        String temp = name.replaceAll("/", ".").replaceAll("\\\\", ".");
        while (temp.startsWith("."))
            temp = temp.substring(1, temp.length());
        this.name = temp;
    }

    public void addPackage() {
        lrg.memoria.core.Package p = new lrg.memoria.core.Package(name);
        p.setStatute(Statute.NORMAL);
        Loader.getInstance().addPackage(id, p);
    }
}
