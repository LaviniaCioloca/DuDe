package lrg.memoria.core;

/**
 * This class defines a namespace.
 */
public class Namespace extends Package implements Scope {
    /**
     * The namespace which has no getName.
     * <par>
     * Java - anonymous packages
     * C++  - global entities which are not defined in any namespace
     */
    public static final String ANONYMOUS_NAMESPACE_NAME = "_ANONYMOUS_NAMESPACE_";
    public static Namespace getAnonymousNamespace() {        
        return ModelElementsRepository.getCurrentModelElementsRepository().getAnonymousNamespace();
    }

    /**
     * The namespace which is visible anywhere.
     * <p>
     * Java and C++ - contains primitive types.
     */
    public static final String GLOBAL_NAMESPACE_NAME = "_GLOBAL_NAMESPACE_";
    public static Namespace getGlobalNamespace() {
        return ModelElementsRepository.getCurrentModelElementsRepository().getGlobalNamespace();
    }

    /**
     * This is used for the namespaces of failed dependencies.
     */
    public static final String UNKNOWN_NAMESPACE_NAME = "_UNKNOWN_NAMESPACE_";
    public static Namespace getUnknownNamespace() {
        return ModelElementsRepository.getCurrentModelElementsRepository().getUnknownNamespace();
    }


    /*-----------------------------------------------------------------------*/


    /**
     * Adds a namespace with a specified getName to the system.
     */
    public Namespace(String name) {
        super(name);
    }

    /**
     * Adds a namespace equivalent to a package.
     */
    public Namespace(String name, int statute) {
        super(name);
        setStatute(statute);
    }

    /**
     * Adds a namespace equivalent to a package.
     */
    public Namespace(Namespace nsp) {
        super(nsp);
    }

    /**
     * The scope of a namespace is the namespace itself.      
     */
    public Scope getScope() {
        return this;
    }

    public void addScopedElement(Scopable element) {
        if (element instanceof GlobalFunction)
            addGlobalFunction((GlobalFunction)element);
        if (element instanceof GlobalVariable)
            addGlobalVariable((GlobalVariable)element);
        if (element instanceof Type)
            addType((Type)element);
    }

    /**
     * Accepts a visitor.
     */
    public void accept(ModelVisitor v) {
        v.visitNamespace(this);
    }

    public String toString() {
        StringBuffer sir = new StringBuffer("\tNamespace: ");
        sir.append(name);
        sir.append("\n\t - contained types:\n");
        for (Type type : getAllTypesList())
            sir.append(type);
        sir.append("\n\t - contained global variables:\n");
        for (Variable var : getGlobalVariablesList())
            sir.append(var);
        sir.append("\n\t - contained global functions:\n");
        for (Function func : getGlobalFunctionsList())
            sir.append(func);
        return new String(sir);
    }
}
