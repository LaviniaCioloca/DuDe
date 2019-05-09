package lrg.memoria.importer.mcc.loader;

import lrg.memoria.core.Statute;

public class DefaultNamespaceVisitor implements NamespaceVisitor{
    private Integer id;
    private String name;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addNamespace() {
        lrg.memoria.core.Namespace n = new lrg.memoria.core.Namespace(name);
        n.setStatute(Statute.NORMAL);
        Loader.getInstance().addNamespace(id, n);
    }
}
