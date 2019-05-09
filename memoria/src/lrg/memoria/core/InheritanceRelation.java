package lrg.memoria.core;

public class InheritanceRelation extends ModelElement {

    private ModelElementList<lrg.memoria.core.Class> entities  = new ModelElementList<lrg.memoria.core.Class>();
    private byte type;

    public InheritanceRelation(lrg.memoria.core.Class subClass, lrg.memoria.core.Class superClass, byte type) {
        entities.add(superClass);
        entities.add(subClass);
        this.type = type;
    }

    public void accept(ModelVisitor v) {
        v.visitInheritanceRelation(this);
    }

    public Class getSuperClass() {
        return entities.get(0);
    }

    public Class getSubClass() {
        return entities.get(1);
    }

    public boolean isPublic() {
        return type == 0;
    }

    public boolean isPrivate() {
        return type == 1;
    }

    public boolean isProtected() {
        return type == 2;
    }

    boolean restore() {
        if (super.restore()) {
            entities.restore();
            return true;
        }
        return false;
    }

}
