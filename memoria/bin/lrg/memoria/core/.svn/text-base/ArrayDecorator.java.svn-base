package lrg.memoria.core;

/**
 * This is the array decorator.
 * For more details @see ImplicitTypeDecorator
 */
public class ArrayDecorator extends ImplicitTypeDecorator {

    public ArrayDecorator(Type decorated) {
        super(decorated);
    }

    public String getFullName() {
        return getScope().getFullName() + "." + decoratedType.getName() + "[]";
    }

    public String getName() {
        return decoratedType.getName() + "[]";
    }

    public boolean isArray() {
        return true;
    }

    public void accept(ModelVisitor mv) {
        mv.visitArrayDecorator(this);
    }
}
