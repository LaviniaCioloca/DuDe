package lrg.memoria.core;

public class TypedefDecorator extends ExplicitlyDefinedType implements TypeDecorator {
    private Type decoratedType;

    public TypedefDecorator(Type decorated, String name) {
        super(name);
        decoratedType = decorated;
    }

    public String getFullName() {
        return getScope().getFullName() + "." + getName();
    }

    public String getName() {
        return name;
    }

    public Type getDecoratedType() {
        return decoratedType;
    }

    public Type getRootType() {
        if (decoratedType instanceof TypeDecorator)
            return ((TypeDecorator)decoratedType).getRootType();
        else
            return decoratedType;
    }

    public boolean isTypedefAlias() {
        return true;
    }

    public boolean isPointer() {
        return false;
    }

    public boolean isArray() {
        return false;
    }

    public boolean isReference() {
        return false;
    }

    public void accept(ModelVisitor mv) {
        mv.visitTypedefDecorator(this);
    }
}

