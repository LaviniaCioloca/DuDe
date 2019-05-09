package lrg.memoria.core;

public class PointerDecorator extends ImplicitTypeDecorator {

    public PointerDecorator(Type decorated) {
        super(decorated);
    }

    public String getFullName() {
        return getScope().getFullName() + "." + decoratedType.getName() + "*";
    }

    public String getName() {
        return decoratedType.getName() + "*";
    }

    public boolean isPointer() {
        return true;
    }

    public void accept(ModelVisitor mv) {
        mv.visitPointerDecorator(this);
    }
}
