//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;

/**
 * This class models the elements that build a class. Each class in the model will be an instance of the "Class" class.
 */
public class Class extends DataAbstraction {

    public static final String UNKNOWN_CLASS_NAME = "_UNKNOWN_CLASS_";
    public static DataAbstraction getUnknownClass() {
       return ModelElementsRepository.getCurrentModelElementsRepository().getUnknownClass();
    }

    public static void setHierarchyRootClass(DataAbstraction cls) {
        ModelElementsRepository.getCurrentModelElementsRepository().setHierarchyRootClass(cls);
    }
    public static DataAbstraction getHierarchyRootClass() {
        return ModelElementsRepository.getCurrentModelElementsRepository().getHierarchyRootClass();
    }

    /*---------------------------------------------------------------*/

    private ModelElementList<InitializerBody> initializers;
    private boolean isStructure = false, isFinal = false;
    private boolean isAbstract = false, isInterface = false, isStatic = false;
    private int accessMode = AccessMode.PACKAGE;
    
    /**
     * Creates a new class with a specified getName.
     */
    public Class(String name) {
        super(name);
    }

    
    protected Class(Class oldClass) {
        super(oldClass);
        initializers = oldClass.initializers;
        isFinal = oldClass.isFinal;
        isAbstract = oldClass.isAbstract;
        isInterface = oldClass.isInterface;
        isStatic = oldClass.isStatic;
        accessMode = oldClass.accessMode;
        isStructure = oldClass.isStructure;
        //annotations = oldClass.annotations;
    }

    public void setName(String newName) {
        name = newName;
    }

    /**
     * This property must be set if a class in final (i.e. can't be extended).
     */
    public void setFinal() {
        isFinal = true;
    }

    /**
     * This property must be set if the class is abstract.
     */
    public void setAbstract() {
        isAbstract = true;
    }

    /**
     * This method distinguishes between classes and interfaces.
     * The parameter must be "true" for an interface-object and "false" for a class-object.
     */
    public void setInterface() {
        isInterface = true;
    }

    public void setStatic() {
        isStatic = true;
    }

    /**
     * Sets the access mode. The access modes are defined in the class AccessMode. (AccessMode.PUBLIC - for public methods)
     */
    public void setAccessMode(int accMode) {
        accessMode = accMode;
    }

    public int getAccessMode() {
        return accessMode;
    }

    public void setStructure() {
        isStructure = true;
    }

    /**
     * This property indicates if a class is a isStructure.
     */
    public boolean isStructure() {
        return isStructure;
    }


    /**
     * This property indicates if a class in final (i.e. can't be extended) or not.
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * Indicates if the class is abstract.
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    /**
     * This property distinguishes between classes and interfaces, by returning "true"
     * for an interface-object and "false" for a class-object.
     */
    public boolean isInterface() {
        return isInterface;
    }

    public boolean isStatic() {
        return isStatic;
    }

    /**
     * This method returns true if the class is part of the interface of the package where
     * it was defined. (i.e. if it was declared "public" within the package)
     */
    public boolean isPublic() {
        return (accessMode == AccessMode.PUBLIC);
    }

    /**
     * This method returns true if the class is private.
     */
    public boolean isPrivate() {
        return (accessMode == AccessMode.PRIVATE);
    }

    /**
     * This method returns true if the class is protected.
     */
    public boolean isProtected() {
        return (accessMode == AccessMode.PROTECTED);
    }

    /**
     * This method returns true if the class is package.
     */
    public boolean isPackage() {
        return (accessMode == AccessMode.PACKAGE);
    }

    /**
     * Adds a new interface to this class.
     */
    public void addInterface(DataAbstraction interf) {
        if (interf != null)
            getAncestorsList().add(interf);
    }

    /**
     * Adds a new initializer to this class.
     */
    public void addInitializer(InitializerBody initializer) {
        if (initializers == null)
            initializers = new ModelElementList<InitializerBody>();
        initializers.add(initializer);
    }

    /**
     * Sets the list of initializers of this class.
     */
    public void setInitializersList(ModelElementList<InitializerBody> il) {
        initializers = il;
    }

    public void addScopedElement(Scopable element) {
        if (element instanceof InitializerBody)
            addInitializer((InitializerBody)element);
        super.addScopedElement(element);
    }

    public ModelElementList getScopedElements() {
        ModelElementList scopedElements = super.getScopedElements();
        if (initializers != null)
            scopedElements.addAll(initializers);
        return scopedElements;
    }

    /**
     * This method returns all the initializer-blocks of this class.
     */
    public ModelElementList<InitializerBody> getInitializerList() {
        if (initializers == null)
            initializers = new ModelElementList<InitializerBody>();
        return initializers;
    }

    public void accept(ModelVisitor v) {
        v.visitClass(this);
    }

    public String toString() {
        int i, tmp;
        StringBuffer myStr = new StringBuffer("\t\t");
        if (isInterface())
            myStr.append("Interface: ");
        else
            myStr.append("Class: ");
        myStr.append(getFullName()).append("\n\t\t - statute: ");
        myStr.append(Statute.convertToString(getStatute()));
        myStr.append("\n\t\t - location: ").append(getLocation());
        if (getStatute() != Statute.FAILEDDEP) {
            myStr.append("\n\t\t - access mode: ");
            if (isPublic())
                myStr.append("public");
            if (isProtected())
                myStr.append("protected");
            if (isPrivate())
                myStr.append("private");
            if (isPackage())
                myStr.append("package");
            myStr.append("\n\t\t - flags: ");
            if (isFinal())
                myStr.append("final, ");
            if (isAbstract())
                myStr.append("abstract.");
            else if (isFinal()) {
                tmp = myStr.length();
                myStr.delete(tmp - 2, tmp);
                myStr.append(".");
            }
            myStr.append("\n\t\t - package: ").append(getPackage().getName());
            myStr.append("\n\t\t - scope: ");
            if (getScope() != null)
                myStr.append(getScope().getName());
            myStr.append("\n\t\t - supertypes: ");
            i = 0;
            for (DataAbstraction currentSupertype : getAncestorsList()) {
                myStr.append(currentSupertype.getName());
                myStr.append(",");
                i++;
            }
            if (i > 0) {
                tmp = myStr.length();
                myStr.delete(tmp - 2, tmp).append(".");
            }
            myStr.append("\n\t\t - descendants: ");
            i = 0;
            for (DataAbstraction currentDescendant : getDescendants()) {
                myStr.append(currentDescendant.getName()).append(", ");
                i++;
            }
            if (i > 0) {
                tmp = myStr.length();
                myStr.delete(tmp - 2, tmp).append(".");
            }
            if (initializers != null) {
                myStr.append("\n\t\t - initializers: ");
                for (i = 0; i < initializers.size(); i++)
                    myStr.append(initializers.get(i));
            }
            myStr.append("\n\t\t - attributes:\n");
            for (i = 0; i < attributes.size(); i++)
                myStr.append((Attribute) attributes.get(i));
            myStr.append("\t\t - methods:\n");
            for (Method currentMethod : methods)
                myStr.append(currentMethod);
            if(annotations != null){
            	myStr.append("\n\t\t\t - annotations: ");
            	for(AnnotationInstance ai : annotations){
            		myStr.append("\n\t\t\t  - " + ai.getAnnotation().getFullName());
            		for(i=0;i<ai.getPropertyValuePairs().size();i++){
            			myStr.append("\n\t\t\t\t");
            			myStr.append(ai.getPropertyValuePairs().get(i).getAp().getName());
            			myStr.append(" = ").append(ai.getPropertyValuePairs().get(i).getValue());
            		}
            	}
            }
        } else
            myStr.append("\n");
        return new String(myStr);
    }

    /*----------------------------------------------------------------------------------------*/

    boolean restore() {
        if (super.restore()) {
            if (initializers != null)
                initializers.restore();
            if (annotations != null)
            	annotations.restore();
            if (bugs != null)
            	bugs.restore();

            return true;
        }
        return false;
    }

}
