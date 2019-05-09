package lrg.memoria.core;

public class GlobalFunction extends Function {
    private Package packageScope = Package.getUnknownPackage();
    protected boolean isStatic = false;

    public GlobalFunction(String name) {
        super(name);
        setScope(Namespace.getUnknownNamespace());
        setFunctionBody((FunctionBody)Body.getUnkonwnBody());
    }

    public GlobalFunction(GlobalFunction gf) {
        super(gf);
        packageScope = gf.packageScope;
        isStatic = gf.isStatic;
    }

    public Package getPackage() {
            return packageScope;
        }

    public void setPackage(Package packageScope) {
            this.packageScope = packageScope;
        }

    public void accept(ModelVisitor mv) {
        mv.visitGlobalFunction(this);
    }

    /**
     * Sets this global function to be static. A static function is local
     * to the file within it is defined.
     */
    public void setStatic() {
        isStatic = true;
    }

    /**
     * Indicates if the method is static. A static function is local
     * to the file within it is defined. 
     */
    public boolean isStatic() {
        return isStatic;
    }
}
