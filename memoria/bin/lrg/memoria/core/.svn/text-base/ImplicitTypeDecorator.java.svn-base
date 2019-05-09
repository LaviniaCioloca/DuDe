package lrg.memoria.core;

/**
 * The root class for the type decorator hierarchy.
 * Every type can be decorated with: array decorators, reference decorators,
 * pointer decorators, typedef decorators.
 */
abstract public class ImplicitTypeDecorator extends NamedModelElement implements TypeDecorator {
    protected Type decoratedType;

    public ImplicitTypeDecorator(Type decorated) {
        super(decorated.getName());
        decoratedType = decorated;
    }

    public ImplicitTypeDecorator(Type decorated, String name) {
        super(name);
        decoratedType = decorated;
    }

    public Type getRootType() {
        if (decoratedType instanceof TypeDecorator)
            return ((TypeDecorator)decoratedType).getRootType();
        else
            return decoratedType;
    }

    public Type getDecoratedType() {
        return decoratedType;
    }

    public Scope getScope() {
        return decoratedType.getScope();
    }

    public boolean isPointer() {
        if (decoratedType instanceof TypeDecorator)
            return ((TypeDecorator)decoratedType).isPointer();
        return false;
    }

    public boolean isArray() {
        if (decoratedType instanceof TypeDecorator)
            return ((TypeDecorator)decoratedType).isArray();
        return false;
    }

    public boolean isTypedefAlias() {
        if (decoratedType instanceof TypeDecorator)
            return ((TypeDecorator)decoratedType).isTypedefAlias();
        return false;
    }

    public boolean isReference() {
        if (decoratedType instanceof TypeDecorator)
            return ((TypeDecorator)decoratedType).isReference();
        return false;
    }    
}
