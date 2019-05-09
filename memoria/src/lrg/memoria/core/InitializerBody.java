//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;




/**
 * This class models an inittialezer block.
 * Only classes with the "Normal" state can have an initializer block.
 */
public class InitializerBody extends Body {
    private boolean isStatic = false;

    /**
     * Constructs an initializer-body object with a specified scope.
     */
    public InitializerBody(DataAbstraction scope) {
        super(scope);
    }
   
    /**
     * This method sets the initialization block to static.
     */
    public void setStatic() {
        isStatic = true;
    }

    /**
     * This method indicates if the initialization block is static.
     */
    public boolean isStatic() {
        return isStatic;
    }

    public DataAbstraction getScope() {
        return (DataAbstraction)super.getScope();
    }

    public void accept(ModelVisitor v) {
        v.visitInitializerBody(this);
    }

    public String toString() {
        StringBuffer myStr = new StringBuffer("\n\t\t\t InitializerBody: ");

        myStr.append("\n\t\t\t\t - flags: ");
        if (isStatic()) myStr.append("static.");
        myStr.append(super.toString());
        return new String(myStr);
    }
}
