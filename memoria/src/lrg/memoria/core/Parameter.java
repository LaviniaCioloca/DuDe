//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;




/**
 * This class models parameters.
 */
public class Parameter extends Variable {

    public Parameter(String name) {
        super(name);
        setScope(Function.getUnknownFunction());
    }

    /**
     * Creates a new Parameter with a specified getName, type and scope.
     */
    public Parameter(String name, Type dataType, Function scope) {
        super(name, dataType);
        this.scope = scope;
    }

    /**
     * This method returns the scope of this parameter.
     * (i.e. the function where it was defined)
     */
    public Function getScope() {
        return (Function)scope;
    }

    public void accept(ModelVisitor v) {
        v.visitParameter(this);
    }
}
