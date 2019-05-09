//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;

import java.util.HashMap;

/**
 * This class defines a package.
 */
public class Package extends NamedModelElement {

    /**
     * The entity which belong to the root directory of the project are put here.
     */
    public static final String ANONYMOUS_PACKAGE_NAME = "_ANONYMOUS_PACKAGE_";    
    public static Package getAnonymousPackage() {
            return ModelElementsRepository.getCurrentModelElementsRepository().getAnonymousPackage();
    }

    /**
     * This is used for the namespaces of failed dependencies.
     */
    public static final String UNKNOWN_PACKAGE_NAME = "_UNKNOWN_PACKAGE_";
    public static Package getUnknownPackage() {
        return ModelElementsRepository.getCurrentModelElementsRepository().getUnknownPackage();
    }


    /*-----------------------------------------------------------------*/

    private ModelElementList<Type> typesList;
    private ModelElementList<GlobalVariable> globalVariablesList;
    private ModelElementList<GlobalFunction> globalFunctionsList;
    private ModelElementList<Annotation> annotationsList;
    
    public void addAnnotation(Annotation a){
    	if(annotationsList == null)
    		annotationsList = new ModelElementList<Annotation>();
    	annotationsList.add(a);
    }
    
    private System system;
    private Subsystem subsystem;
    
    /**
     * Adds a package with a specified getName to the system.
     */
    public Package(String name) {
        super(name);
        typesList = new ModelElementList<Type>();
        globalVariablesList = new ModelElementList<GlobalVariable>();
        globalFunctionsList = new ModelElementList<GlobalFunction>();
        annotationsList = new ModelElementList<Annotation>();
    }

    protected Package(Package pack) {
        super(pack);
        typesList = pack.typesList;
        globalVariablesList = pack.globalVariablesList;
        globalFunctionsList = pack.globalFunctionsList;
        annotationsList = pack.annotationsList;
    }

    /**
     * Adds a type to the current package.
     */
    public void addType(Type type) { 
    	for(Type aType : typesList) if(type.getName().compareTo(aType.getName()) == 0) return;
        typesList.add(type);
    }

    /**
     * Get list of types contained in this package. All types that physically resede within this package
     * will be returned no matter in which scope they are.
     */
    public ModelElementList<Type> getAllTypesList() {
        return typesList;
    }

    /**
     * Set the list of types of the current package.
     */
    public void setAllTypesList(ModelElementList<Type> types) {
        typesList = types;
    }

    /**
     * Get list of types contained in this package.
     * Only the types which are not inner are returned.
     */
    
    public ModelElementList<Type> getTypesList() {
        ModelElementList<Type> temp = new ModelElementList<Type>();
        HashMap<String, Type> types = new HashMap<String, Type>();
        
        for(Type current : typesList) 
            if ((current.getScope() instanceof Namespace) && (types.get(current.getFullName()) == null)) { 
                temp.add(current);
                types.put(current.getFullName(), current);
            }
        return temp;
    }
/*    
    public ModelElementList<Type> getTypesList() {
        ModelElementList<Type> temp = new ModelElementList<Type>();
        for(Type current : typesList) {
            if (current.getScope() instanceof Namespace)
                temp.add(current);
        }
        return temp;
    }
*/
    /**
     * Adds a global variable to the current package.
     */
    public void addGlobalVariable(GlobalVariable var) {
        globalVariablesList.add(var);
    }

    /**
     * Set the global variables list of the current package.
     */
    public void setGlobalVariables(ModelElementList<GlobalVariable> globalVariables) {
        globalVariablesList = globalVariables;
    }

    /**
     * Get the list of variables contained in this package.
     */
    public ModelElementList<GlobalVariable> getGlobalVariablesList() {
        return globalVariablesList;
    }

    public ModelElementList<Annotation> getAnnotationsList() {
        return annotationsList;
    }

    /**
     * Get the list of functions contained in this package.
     */
    public ModelElementList<GlobalFunction> getGlobalFunctionsList() {
        return globalFunctionsList;
    }

    /**
     * Add a new global function to the package.
     */
    public void addGlobalFunction(GlobalFunction func) {
        globalFunctionsList.add(func);
    }

    /**
     * Set the global functions list of the current package.
     */
    public void setGlobalFunctions(ModelElementList<GlobalFunction> globalFunctions) {
        globalFunctionsList = globalFunctions;
    }

    public void setSystem(System s) {
        system = s;
    }

    /**
     * Returns the system to which the package belongs.
     */
    public System getSystem() {
        return system;
    }

    /**
     * Returns the list of abstract data types from the current package.
     */
    public ModelElementList<DataAbstraction> getAbstractDataTypes() {
        ModelElementList<DataAbstraction> onlyAbstractDataTypes = new ModelElementList<DataAbstraction>();
        for(int i = 0; i < typesList.size(); i++)
            if(typesList.get(i) instanceof DataAbstraction)
                onlyAbstractDataTypes.add((DataAbstraction)typesList.get(i));
        return onlyAbstractDataTypes;
    }

    public ModelElementList getScopedElements() {
        ModelElementList scopedElements = new ModelElementList();
        scopedElements.addAll(typesList);
        scopedElements.addAll(globalFunctionsList);
        scopedElements.addAll(globalVariablesList);
        scopedElements.addAll(annotationsList);
        return scopedElements;
    }

    public void accept(ModelVisitor v) {
        v.visitPackage(this);
    }

    public String toString() {
        StringBuffer sir = new StringBuffer("\tPackage: ");
        sir.append(name);
        sir.append("\n\t - contained typesList:\n");
        for (Type type : typesList)
            sir.append(type);
        sir.append("\n\t - contained globalVariablesList:\n");
        for (Variable var : globalVariablesList)
            sir.append(var);
        sir.append("\n\t - contained annotationsList:\n");
        for (Annotation an : annotationsList)
            sir.append(an);
        if (annotations != null){
        	sir.append("\n\t - annotated with annotations: ");        	
        	for(AnnotationInstance ai : annotations){
        		sir.append("\n\t\t -" + ai.getAnnotation().getFullName());
        		for(int i=0;i<ai.getPropertyValuePairs().size();i++){
        			sir.append("\n\t\t\t");
        			sir.append(ai.getPropertyValuePairs().get(i).getAp().getName());
        			sir.append(" = ").append(ai.getPropertyValuePairs().get(i).getValue());
        		}
        	}
        }
        return new String(sir);
    }

    /*------------------------------------------------------------------------------------*/

    boolean restore() {
        if (super.restore()) {
            typesList.restore();
            globalVariablesList.restore();
            globalFunctionsList.restore();
            if (annotations != null)
            	annotations.restore();
            if (annotationsList != null)
            	annotationsList.restore();
            if (bugs != null)
            	bugs.restore();
            return true;
        }
        return false;
    }

	public Subsystem getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(Subsystem subsystem) {
		this.subsystem = subsystem;
	}
}
