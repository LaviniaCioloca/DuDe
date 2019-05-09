//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;

/**
 * The class stores the common characteristics of all types of variables.
 */
public abstract class Variable extends NamedModelElement implements Scopable {

    public static String UNKNOWN_VARIABLE_NAME = "unknown_variable";
    public static Variable getUnknownVariable() {
        return ModelElementsRepository.getCurrentModelElementsRepository().getUnknownVariable();
    }

    /*---------------------------------------------------------------*/

    protected Type type;
    protected Scope scope;
    private Location location = Location.getUnknownLocation();
    private ModelElementList<Access> accessesList;
    private boolean isFinal = false;
    
    protected Variable(String name) {
        this(name, Class.getUnknownClass());
    }

    protected Variable(Variable var) {
        super(var);
        type = var.type;
        accessesList = var.accessesList;
        location = var.location;
        scope = var.scope;
        isFinal = var.isFinal;
        //annotations = var.annotations;
    }

    protected Variable(String name, Type dataType) {
        super(name);
        type = dataType;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public String getFullName() {
        return scope.getFullName() + "." + getName();
    }

    /**
     * Returns the type of the variable.
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets this variable to be final.
     * This indicates if the variable is declared "final",
     * i.e. if the variable is a ... constant :)
     */
    public void setFinal() {
        isFinal = true;
    }

    /**
     * Adds a new access to this variable.
     */
    public void addAccess(Access access) {
        if (accessesList == null)
            accessesList = new ModelElementList<Access>();
        accessesList.add(access);
    }

    /**
     * Returns the list of accesses to this variable.
     */
    public ModelElementList<Access> getAccessList() {
        if (accessesList == null)
            accessesList = new ModelElementList<Access>();
        return accessesList;
    }

    public void setAccessesList(ModelElementList<Access> accesses) {
        accessesList = accesses;        
    }

    /**
     * This property indicates if the variable is of a primitive type
     * or if it is the instance of a class.
     */
    public boolean hasPrimitiveType() {
        return (type instanceof Primitive);
    }

    /**
     * This property indicates if the variable is declared "final",
     * i.e. if the variable is a ... constant :)
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * In this model we do not treat array types as a distinct kind of type
     * (e.g. String and String[] are considered to be the same).
     * Yet, based on this property we can make the distinction between a
     * variable which was declared
     * <P>
     * Thus, considering following declarations :
     * <PRE>
     * String s1;
     * String[] s2;
     * Both will have the dataType field set to "String",
     * but the "isArray" property will return "false" for s1 and "true" for s2;
     * </PRE>
     * @deprecated - use getType().getFullName() instead
     */
    public boolean isArray() {
        return (getType() instanceof ArrayDecorator);
    }

    public abstract void accept(ModelVisitor v);

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    boolean restore() {
        if (super.restore()) {
            if (accessesList != null)
                accessesList.restore();
            if (annotations != null)
            	annotations.restore();
            return true;
        }
        return false;
    }

    public String toString() {
            int tmp;
            StringBuffer myStr = new StringBuffer("\t\t\t Variable: ");

            myStr.append(name).append("\n\t\t\t - type: ").append(getType().getName());
            myStr.append("\n\t\t\t - location: ").append(getLocation());
            myStr.append("\n\t\t\t - flags: ");
            if (isFinal())
                myStr.append("final, ");
            else if (isFinal()) {
                tmp = myStr.length();
                myStr.delete(tmp - 2, tmp);
            }
            if(annotations!=null){
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
