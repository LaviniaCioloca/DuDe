//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;

import java.io.FileWriter;
import java.io.IOException;


/**
 * This class contains the elements that are specific for an attribute.
 */
public class Attribute extends Variable {
    private int accessMode = AccessMode.PACKAGE;
    private boolean isStatic = false;

    protected Attribute(Attribute attr) {
        super(attr);
        accessMode = attr.accessMode;
        isStatic = attr.isStatic;
    }

    public Attribute(String name) {
        super(name);
        scope = Class.getUnknownClass();
    }

    /**
     * Creates a new Attribute having a specified getName, type, scope and line.
     * Line represents the line in the source where the current attribute is defined.<br>
     * In this case the access specifier of the attribute is by default censidered to be PACKAGE.
     */
    public Attribute(String name, Type dataType, DataAbstraction scope) {
        super(name, dataType);
        this.scope = scope;
    }

    /**
     * Creates a new Attribute having a specified getName, type, scope, access mode and line.<br>
     * accMode is the access specifier of the attribute (i.e. PUBLIC, PROTECTED, PRIVATE)<br>
     * line represents the line in the source where the current attribute is defined.
     */
    public Attribute(String name, Type dataType, DataAbstraction scope, int accMode) {
        this(name, dataType, scope);
        accessMode = accMode;
    }

    /**
     * This method returns the class where the attribute was defined in.
     */
    public DataAbstraction getScope() {
        return (DataAbstraction)scope;
    }

    /**
     * Sets this variable to be static.
     */
    public void setStatic() {
            isStatic = true;
        }

    /**
     * This property indicates if the variable is static or not.
     * A static attribute belongs to the class side and not to the
     * instances of the class.
     */
    public boolean isStatic() {
        return isStatic;
    }
    
    /**
     * Sets the access mode of the attribute.
     * Access modes are defined in the class AccessMode.
     */
    public void setAccessMode(int accMode) {
        accessMode = accMode;
    }

    public int getAccessMode() {
        return accessMode;
    }

    /**
     * This method returns true if the attribute is declared public.
     */
    public boolean isPublic() {
        return (accessMode == AccessMode.PUBLIC);
    }

    /**
     * This method returns true if the attribute is declared private.
     */
    public boolean isPrivate() {
        return (accessMode == AccessMode.PRIVATE);
    }

    /**
     * This method returns true if the attribute is declared protected.
     */
    public boolean isProtected() {
        return (accessMode == AccessMode.PROTECTED);
    }

    /**
     * This method returns true if the attribute is declared package.
     */
    public boolean isPackage() {
        return (accessMode == AccessMode.PACKAGE);
    }

    public void accept(ModelVisitor v) {
        v.visitAttribute(this);
    }

    public void writeXML(FileWriter output) throws IOException {
    }

    public String toString() {
        int tmp;

        StringBuffer myStr = new StringBuffer("\t\t\tAttribute: ");
        myStr.append(getName()).append("(").append(scope.getFullName()).append(")");
        myStr.append("\n\t\t\t - type: ").append(getType().getName());
        if (isArray())
            myStr.append("[]");
        myStr.append("\n\t\t\t - scope: ").append(scope.getName());
        myStr.append("\n\t\t\t - location: ").append(getLocation());
        myStr.append("\n\t\t\t - access mode: ");
        if (isPublic())
            myStr.append("public");
        if (isProtected())
            myStr.append("protected");
        if (isPrivate())
            myStr.append("private");
        if (isPackage())
            myStr.append("package");
        myStr.append("\n\t\t\t - flags: ");
        if (isFinal())
            myStr.append("final, ");
        if (isStatic())
            myStr.append("static.");
        else if (isFinal()) {
            tmp = myStr.length();
            myStr.delete(tmp - 2, tmp);
            myStr.append(".");
        }
        if(annotations != null){
        	myStr.append("\n\t\t\t - annotations: ");
        	for(AnnotationInstance ai : annotations){
        		myStr.append("\n\t\t\t  -" + ai.getAnnotation().getFullName());
        		for(int i=0;i<ai.getPropertyValuePairs().size();i++){
        			myStr.append("\n\t\t\t\t");
        			myStr.append(ai.getPropertyValuePairs().get(i).getAp().getName());
        			myStr.append(" = ").append(ai.getPropertyValuePairs().get(i).getValue());
        		}
        	}
        }
        myStr.append("\n");
        return new String(myStr);
    }
}
