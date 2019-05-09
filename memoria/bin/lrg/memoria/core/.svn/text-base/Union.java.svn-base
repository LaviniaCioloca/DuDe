package lrg.memoria.core;

public class Union extends DataAbstraction {

    public Union(String name) {
        super(name);
    }

    public void accept(ModelVisitor mv) {
        mv.visitUnion(this);
    }

    public boolean isUnion() {
        return true;
    }

    public String toString() {
        return "Union: \n \tname: " + getName();
    }
}
