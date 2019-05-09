//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;



/**
 * This class keeps the information about the primitve types of the
 * language which are used in the projects. 
 */
public class Primitive extends NamedModelElement implements Type {
    private final Namespace scope = Namespace.getGlobalNamespace();

    /**
     * Creates a primitive type with a specified getName.
     */
    public Primitive(String name) {
        super(name);
        setStatute(Statute.LIBRARY);
    }

    public String getFullName() {
        return scope.getName() + "." + name;
    }

    public Scope getScope() {
        return scope;
    }

    public void accept(ModelVisitor v) {
        v.visitPrimitive(this);
    }
}
