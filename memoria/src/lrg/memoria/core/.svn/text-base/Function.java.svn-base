package lrg.memoria.core;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Function extends NamedModelElement implements Scopable, Scope {
    public static final String unknownFunctionName = "unknown_function";

    public static Function getUnknownFunction() {
        return ModelElementsRepository.getCurrentModelElementsRepository().getUnknownFunction();
    }

    /*-----------------------------------------------------------------*/

    protected Location location = Location.getUnknownLocation();
    protected ModelElementList<Parameter> parametersList;
    protected Type returnType = Class.getUnknownClass();
    protected FunctionBody body;
    protected ModelElementList<Call> callsList;
    private  Scope scope;
    private Package thePackage; 
    
    public Function(String name) {
        super(name);
    }

    protected Function(Function func) {
        super(func);
        location = func.location;
        scope = func.scope;
        returnType = func.returnType;
        body = func.body;
        parametersList = func.parametersList;
        callsList = func.callsList;
        //annotations = func.annotations;
    }

    public String getFullName() {
        String fullName = scope.getFullName() + "." + name + "(";
        int j = 0;
        if(ModelElementsRepository.getCurrentModelElementsRepository().getUnknownFunction() != this &&
           ModelElementsRepository.getCurrentModelElementsRepository().getUnknownMethod() !=this) {            if (parametersList != null) {
                for (Parameter param : parametersList) {
                    if (j > 0)
                        fullName += ",";
                    fullName += param.getType().getFullName();
                    j++;
                }
            }
        }
        fullName += ")";
        return fullName;
    }

    public void setLocation(Location loc) {
        location = loc;
    }

    public Location getLocation() {
        return location;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public boolean isEqualSignature(Function otherFunction) {
        if (getName().compareTo(otherFunction.getName()) != 0) return false;

        ModelElementList<Parameter> thisParameters = getParameterList();
        ModelElementList<Parameter> othersParameters = otherFunction.getParameterList();

        if (thisParameters.size() != othersParameters.size()) return false;

        ArrayList paramTypes = new ArrayList();
        ArrayList paramTypesOfOtherMethod = new ArrayList();

        for (Iterator it = othersParameters.iterator(); it.hasNext();
             paramTypes.add(((Parameter) it.next()).getType()))
            ;
        for (Iterator it = othersParameters.iterator(); it.hasNext();
             paramTypesOfOtherMethod.add(((Parameter) it.next()).getType()))
            ;

        return paramTypes.containsAll(paramTypesOfOtherMethod);
    }


    public void addScopedElement(Scopable element) {
        if (element instanceof Parameter)
            addParameter((Parameter) element);
        if (element instanceof Body)
            setFunctionBody((FunctionBody) element);
    }

    public ModelElementList getScopedElements() {
        ModelElementList scopedElements = new ModelElementList();
        scopedElements.add(body);
        return scopedElements;
    }

    /**
     * Sets a reference to the method-body object. A method object having
     * the "Normal" statute  must have a valid reference to an "Method-body"
     * object, if the method is not abstract (or is not the declaration
     * contained in a interface).
     * <P>
     * If the method has the "BrokenLibraryLimitations" or "FailedDependency" statute the
     * reference to the method-body will be set to NULL.
     */
    public void setFunctionBody(FunctionBody body) {
        this.body = body;
    }

    /**
     * Adds a parameter to this method.
     */
    public void addParameter(Parameter p) {
        if (parametersList == null)
            parametersList = new ModelElementList<Parameter>();
        parametersList.add(p);
    }

    /**
     * Returns the list of parameters of the method.
     */
    public ModelElementList<Parameter> getParameterList() {
        if (parametersList == null)
            parametersList = new ModelElementList<Parameter>();
        return parametersList;
    }

    /**
     * Returnes the line where this method was defined at.
     */
    public int getLine() {
        return location.getStartLine();
    }

    /**
     * This method sets the type returned by the current method.
     */
    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    /**
     * This method returns the type returned by this method.
     */
    public Type getReturnType() {
        return returnType;
    }

    /**
     * Returns a reference to the method-body object. A method object having
     * the "Normal" statute  must have a valid reference to an "Method-body"
     * object, if the method is not abstract (or is not the declaration
     * contained in a interface).
     * <P>
     * If the method has the "BrokenLibraryLimitations" or "FailedDependency" statute the
     * reference to the method-body will be set to NULL.
     */
    public FunctionBody getBody() {
        if (body == null)
            body = (FunctionBody) Body.getUnkonwnBody();
        return body;
    }

    public void addCall(Call c) {
        if (callsList == null)
            callsList = new ModelElementList<Call>();
        callsList.add(c);
    }

    public ModelElementList<Call> getCallList() {
        if (callsList == null)
            callsList = new ModelElementList<Call>();
        return callsList;
    }

    public void setCallList(ModelElementList<Call> calls) {
        callsList = calls;
    }

    boolean restore() {
        if (super.restore()) {
            if (callsList != null)
                callsList.restore();
            if (parametersList != null)
                parametersList.restore();
            if (body != null)
                body.restore();
            if(annotations != null)
            	annotations.restore();
            if(bugs != null)
            	bugs.restore();
            return true;
        }
        return false;
    }

	public void setPackage(Package thePackage) {
		this.thePackage = thePackage;
	}

	public Package getPackage() {
		return thePackage;
	}

}
