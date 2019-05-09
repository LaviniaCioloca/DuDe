package lrg.memoria.core;

public class GlobalVariable extends Variable {
    private Package currentPackage;
    private Variable refersTo = null;
    private boolean isStatic = false;

    /**
     * Constructs a new GlobalVariable with a specified getName
     */
    public GlobalVariable(String name) {
        super(name);
        setScope(Namespace.getUnknownNamespace());
        setPackage(Package.getUnknownPackage());
    }

    /**
     * Udes for hismo.
     */
    public GlobalVariable(GlobalVariable gf) {
        super(gf);
        currentPackage = gf.currentPackage;
        refersTo = gf.refersTo;
        isStatic = gf.isStatic;
    }

    /**
     * This method returns the scope of the definition of this global variable.
     * This scope is the namespace in which the variable was declared or the
     * unnamed namespace if the variable was declared as "static" (only for C).
     */
    public Namespace getScope() {
        return (Namespace)scope;
    }

    public Package getPackage() {
        return currentPackage;
    }

    public void setPackage(Package packageScope) {
        this.currentPackage = packageScope;
    }

    /**
     * Sets this variable to be static.
     */
     public void setStatic() {
            isStatic = true;
        }

    /**
     * This property indicates if the global variable is static or not.
     * If the variable is static its scope is the fiel where it is defined.
     */
    public boolean isStatic() {
        return isStatic;
    }    

    public void setRefersTo(Variable refersTo) {
        this.refersTo = refersTo;
    }

    public Variable getRefersTo() {
        return refersTo;
    }

    public void accept(ModelVisitor mv) {
        mv.visitGlobalVar(this);
    }
}
