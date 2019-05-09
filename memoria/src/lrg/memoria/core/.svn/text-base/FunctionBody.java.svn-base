//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;




/**
 * This class contains the information about a function as it results from
 * its implementation. Only functions with the "Normal" state will have a
 * function-body object.
 */
public class FunctionBody extends Body {

    /**
     * Constructs a function-body object with a specified scope.
     */
    public FunctionBody(Method scope) {
        super(scope);
    }

    /**
     * Constructs a function-body object within an unspecified scope.
     */
    public FunctionBody() {
        super(Method.getUnknownMethod());
    }

    public Function getScope() {
        return (Function)super.getScope();
    }

    public void accept(ModelVisitor v) {
        v.visitFunctionBody(this);
    }

    public String toString() {
        StringBuffer myStr = new StringBuffer("\n\t\t\t - function body:");
        myStr.append(super.toString());
        return new String(myStr);
    }
}
