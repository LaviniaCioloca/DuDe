package lrg.memoria.core;

public class ReferenceDecorator extends ImplicitTypeDecorator {

    public ReferenceDecorator(Type decorated) {
        super(decorated);
    }

    public String getFullName() {
        return getScope().getFullName() + "." + decoratedType.getName() + "&";
    }

    public String getName() {
        return decoratedType.getName() + "&";
    }

    public boolean isReference() {
        return true;
    }

    public void accept(ModelVisitor mv) {
        mv.visitReferenceDecorator(this);
    }
}


