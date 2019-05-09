//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;

/**
 * This class models a method object, as it results by its declaration.
 */
public class Method extends Function {
    public static final int CONSTRUCTOR = 1;
    public static final int ACCESSOR = 2;
    public static final int OTHER = 3;

    public static final String UNKNOWN_METHOD_NAME = "unknown_method";

    public static Method getUnknownMethod() {
        return ModelElementsRepository.getCurrentModelElementsRepository().getUnknownMethod();
    }

    /*----------------------------------------------------------------*/

    private ModelElementList<Class> throwsExceptionsList;
    protected boolean isAbstract = false;
    protected boolean isVirtual = true;
    protected boolean isStatic = false;
    private int kindOf = OTHER;
    private int accessMode = AccessMode.PACKAGE;
    
    /**
     * Constructs a method with a specified getName, scope and line where this method was defined at.
     */
    public Method(String name) {
        super(name);
        throwsExceptionsList = new ModelElementList<Class>();
        setScope(Class.getUnknownClass());
    }

    public Method(Method method) {
        super(method);
        location = method.location;
        returnType = method.returnType;
        body = method.body;
        throwsExceptionsList = method.throwsExceptionsList;        
        isAbstract = method.isAbstract;
        isVirtual = method.isVirtual;
        isStatic = method.isStatic;
        kindOf = method.kindOf;
        accessMode = method.accessMode;
    }

    public void setAccessMode(int accMode) {
        accessMode = accMode;
    }

    public int getAccessMode() {
        return accessMode;
    }

    /**
     * Sets this method to be abstract. Abstract methods do not neccesarily
     * have a method-body.
     */
    public void setAbstract() {
        isAbstract = true;
    }

    /**
     * Sets this method to be an accessor method.
     * By an "accessor method" we understand a method that has the exclusive
     * task of seting or retrieving a particular attribute of the class, which
     * is not visible from the outside, due to encapsulation rules.
     * <P>
     * This implementation is based on a simple, and therefore vulnerable, naming
     * convention, which states that the getName of an accessor-method must be prefixed
     * by "set" or "get"  string.
     */
    public void setAccessor() {
        kindOf = ACCESSOR;
    }

    /**
     * Adds an exception, which the method throws, to the list of exceptions.
     */
    public void addException(Class e) {
        throwsExceptionsList.add(e);
    }

    /**
     * Returnes the list of the classes of exceptions that the method throws.
     */
    public ModelElementList<Class> getExceptionList() {
        return throwsExceptionsList;
    }

    public void setKindOf(int k) {
        kindOf = k;
    }

    public int getKindOf() {
        return kindOf;
    }

    public boolean isVirtual() {
        return isVirtual;
    }

    public void setVirtual() {
        isVirtual = true;
    }

    /**
     * Sets this method to be final, i.e. the method cannot be overriden.
     */
    public void setFinal() {
        isVirtual = false;
    }

    /**
     * Indicates if the method is final, i.e. the method cannot be overriden.
     */
    public boolean isFinal() {
        return !isVirtual;
    }

    /**
     * Indicates if the method is a constructor.
     */
    public boolean isConstructor() {
        return (kindOf == CONSTRUCTOR);
    }

    /**
     * The current method is a constructor.
     */
    public void setConstructor() {
        kindOf = CONSTRUCTOR;
        returnType = null;
    }

    /**
     * Indicates if the method is a accessor method.
     * By an "accessor method" we understand a method that has the exclusive
     * task of seting or retrieving a particular attribute of the class, which
     * is not visible from the outside, due to encapsulation rules.
     * <P>
     * This implementation is based on a simple, and therefore vulnerable, naming
     * convention, which states that the getName of an accessor-method must be prefixed
     * by "set" or "get"  string.
     */
    public boolean isAccessor() {
        return (kindOf == ACCESSOR);
    }

    /**
     * Indicates if the method is abstract. Abstract methods do not neccesarily
     * have a method-body.
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    /**
     * Indicates if the method is public.
     */
    public boolean isPublic() {
        return (accessMode == AccessMode.PUBLIC);
    }

    /**
     * Indicates if the method is private.
     */
    public boolean isPrivate() {
        return (accessMode == AccessMode.PRIVATE);
    }

    /**
     * Indicates if the method is package.
     */
    public boolean isPackage() {
        return (accessMode == AccessMode.PACKAGE);
    }

    /**
     * Indicates if the method is protected.
     */
    public boolean isProtected() {
        return (accessMode == AccessMode.PROTECTED);
    }

    public void accept(ModelVisitor v) {
        v.visitMethod(this);
    }

    public String toString() {
        int tmp, i;
        StringBuffer myStr = new StringBuffer("\t\t\t");

        if (isConstructor())
            myStr.append("Constructor: ");
        else
            myStr.append("Method: ");
        myStr.append(name).append("(").append(getScope().getFullName()).append(")");
        myStr.append("\n\t\t\t - location: ").append(location);
        myStr.append("\n\t\t\t - statute: ");
        myStr.append(Statute.convertToString(getStatute()));
        if (getStatute() != Statute.FAILEDDEP) {
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
            int count = 0;
            if (isFinal()) {
                count++;
                myStr.append("final, ");
            }
            if (isAbstract()) {
                count++;
                myStr.append("abstract, ");
            }
            if (isStatic())
                myStr.append("static.");
            else if (count > 0) {
                tmp = myStr.length();
                myStr.delete(tmp - 2, tmp).append(".");
            }
            myStr.append("\n\t\t\t - exceptions: ");
            for (i = 0; i < throwsExceptionsList.size(); i++) {
                DataAbstraction e = (DataAbstraction) throwsExceptionsList.get(i);
                myStr.append(e.getName() + ", ");
            }
            if (i > 0) {
                tmp = myStr.length();
                myStr.delete(tmp - 2, tmp).append(".");
            }
            myStr.append("\n\t\t\t - scope: ");
            myStr.append(getScope().getName());
            myStr.append("\n\t\t\t - parameters: ");
            for (i = 0; i < getParameterList().size(); i++) {
                Parameter var;
                var = (Parameter) getParameterList().get(i);
                myStr.append(var.getName()).append("(" + var.getType().getName());
                if (var.getType() instanceof ArrayDecorator) myStr.append("[]");
                myStr.append(")").append(", ");
            }
            if (i > 0) {
                tmp = myStr.length();
                myStr.delete(tmp - 2, tmp).append(".");
            }
            if (!isConstructor()) {
                myStr.append("\n\t\t\t - returns: ");
                myStr.append(returnType.getName());
            }
            if (getStatute() == Statute.NORMAL && !isAbstract() && !((DataAbstraction)getScope()).isInterface()) myStr.append(body);
        }
        if(annotations != null){
        	myStr.append("\n\t\t\t - annotations: ");
        	for(AnnotationInstance ai : annotations){
        		myStr.append("\n\t\t\t  -" + ai.getAnnotation().getFullName());
        		for(i=0;i<ai.getPropertyValuePairs().size();i++){
        			myStr.append("\n\t\t\t\t");
        			myStr.append(ai.getPropertyValuePairs().get(i).getAp().getName());
        			myStr.append(" = ").append(ai.getPropertyValuePairs().get(i).getValue());
        		}
        	}
        }
        myStr.append("\n");
        return new String(myStr);
    }

    /**
     * Sets this method to be static. A static method belongs to the class
     * and not to instances of that class.
     */
    public void setStatic() {
        isStatic = true;
    }

    /**
     * Indicates if the method is static. A static method belongs to the class
     * and not to instances of that class.
     */
    public boolean isStatic() {
        return isStatic;
    }

    boolean restore() {
        if (super.restore()) {
            throwsExceptionsList.restore();            
            return true;
        }
        return false;
    }
}
