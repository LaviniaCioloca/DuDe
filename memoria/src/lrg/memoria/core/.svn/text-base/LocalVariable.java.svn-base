//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;


/**
 * This class models local variables.
 */
public class LocalVariable extends Variable {
    private boolean hasBlockScope = false, isStatic = false;
    private boolean isExceptionParameter = false;

    /**
     * Constructs a new LocalVariable with a specified getName
     *
     * @deprecated
     */
    public LocalVariable(String name) {
        super(name);
        setScope(Body.getUnkonwnBody().getCodeStripe());
    }

    public LocalVariable(String name, CodeStripe scope) {
        super(name);
        setScope(scope);
    }

    /**
     * Constructs a LocalVariable from another LocalVariable.
     */
    public LocalVariable(LocalVariable lv) {
        super(lv);
        hasBlockScope = lv.hasBlockScope;
        isStatic = lv.isStatic;
        isExceptionParameter = lv.isExceptionParameter;
    }


    /**
     * This method returns the scope of the definition of this variables.
     * This scope is the body of a method or the body of an initialization block.
     *
     * @deprecated - will no longer return bodies but CodeStripes
     */
    public Body getScope() {
        return ((CodeStripe) scope).getParentBody();
    }

    public void setScope(Scope scope) {
        // this.scope = ((FunctionBody) scope).getCodeStripe();
        this.scope = (CodeStripe) scope;
    }

    public CodeStripe getStripe() {
        return ((CodeStripe) scope);
    }


    /**
     * This method returnes true if the variable is defined inside a block.
     */
    public boolean isBlock() {
        return hasBlockScope;
    }

    public void setBlock() {
        hasBlockScope = true;
    }

    /**
     * Sets this variable to be static.
     */
    public void setStatic() {
        isStatic = true;
    }

    /**
     * This property indicates if the variable is static or not.
     * If a local variable is static then it expands its lifetime between
     * consecutive activations of the scope where it is defined.
     */
    public boolean isStatic() {
        return isStatic;
    }

    /**
     * This method returnes true if the variale is an exception parameter, that is a parameter to a catch.
     */
    public boolean isExParam() {
        return isExceptionParameter;
    }

    public void setExParam() {
        isExceptionParameter = true;
    }


    public void accept(ModelVisitor v) {
        v.visitLocalVar(this);
    }
}

