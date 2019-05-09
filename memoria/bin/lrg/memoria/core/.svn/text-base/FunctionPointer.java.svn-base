package lrg.memoria.core;

/**
 * The type side of a function pointer definition.
 */
public class FunctionPointer extends NamedModelElement implements Type, Scope {         
    private Scope scope = Namespace.getUnknownNamespace();
    private PointerToFunction functionSide;

    public FunctionPointer(String name) {
        super(name);
    }

    public String getFullName() {
        return scope.getFullName() + "." + getName();    
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void addScopedElement(Scopable element) {
        if (element instanceof PointerToFunction)
            setFunctionSide((PointerToFunction)element);
    }

    public ModelElementList getScopedElements() {
        ModelElementList scopedElements = new ModelElementList();
        scopedElements.add(functionSide);
        return scopedElements;
    }

    public void setFunctionSide(PointerToFunction pointerToFunction) {
        functionSide = pointerToFunction;
    }

    public PointerToFunction getFunctionSide() {
        return functionSide;
    }

    public void accept(ModelVisitor mv) {
        mv.visitFunctionPointer(this);
    }
}
