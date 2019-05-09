package lrg.memoria.core;

/**
 * This class models data abstraction.
 */
abstract public class DataAbstraction extends ExplicitlyDefinedType implements Scope {
    protected ModelElementList<Attribute> attributes;
    protected ModelElementList<Method> methods;
    protected ModelElementList<DataAbstraction> supertypes;
    protected ModelElementList<InheritanceRelation> relationAsSuperType;
    protected ModelElementList<DataAbstraction> descendants;
    protected ModelElementList<InheritanceRelation> relationAsSubType;
    protected ModelElementList<Type> innerTypes;
	public DataAbstraction(String name) {
        super(name);
        attributes = new ModelElementList<Attribute>();
        methods = new ModelElementList<Method>();
    }

    protected DataAbstraction(DataAbstraction oldType) {
        super(oldType);
        innerTypes = oldType.innerTypes;
        attributes = oldType.attributes;
        methods = oldType.methods;
        descendants = oldType.descendants;
        supertypes = oldType.supertypes;
        relationAsSuperType = oldType.relationAsSuperType;
        relationAsSubType = oldType.relationAsSubType;
    }

    /**
     * Adds a new attribute to this abstract data type.
     */
    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    /**
     * Sets the attributes list of this abstract data type.
     */
    public void setAttributes(ModelElementList<Attribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * Adds a new method to this abstract data type.
     */
    public void addMethod(Method method) {
        methods.add(method);
    }

    /**
     * Sets the methods list of this abstract data type.
     */
    public void setMethods(ModelElementList<Method> methods) {
        this.methods = methods;
    }

    /**
     * Adds a new type which is defined in the scope of this abstract data type.
     */
    public void addInnerType(Type type) {
        if (innerTypes == null)
            innerTypes = new ModelElementList<Type>();
        innerTypes.add(type);
    }

    /**
     * Returns the atttributes of this abstract data type.
     */
    public ModelElementList<Attribute> getAttributeList() {
        return attributes;
    }

    /**
     * Returns the methods of this abstract data type.
     */
    public ModelElementList<Method> getMethodList() {
        return methods;
    }

    public boolean isInterface() {
        return false;
    }

    public boolean isUnion() {
        return false;
    }

    public boolean isStructure() {
        return false;
    }

    public void addScopedElement(Scopable element) {
        if (element instanceof Attribute)
            addAttribute((Attribute)element);
        if (element instanceof Method)
            addMethod((Method)element);
        if (element instanceof Type)
            addInnerType((Type)element);
    }

    public ModelElementList getScopedElements() {
        ModelElementList scopedElements = new ModelElementList();
        scopedElements.addAll(attributes);
        scopedElements.addAll(methods);
        if (innerTypes != null)
            scopedElements.addAll(innerTypes);
        return scopedElements;
    }

    /**
     * Add an ancestor to this class.
     */
    public void addAncestor(DataAbstraction ancestor) {
        if (supertypes == null)
            supertypes = new ModelElementList<DataAbstraction>();
        if (ancestor != null)
            supertypes.add(ancestor);
    }

    /**
     * Returns the first ancestor of this class.
     */
    public DataAbstraction getFirstAncestor() {
        if (supertypes == null)
            return null;
        if (supertypes.size() > 0)
            return supertypes.get(0);
        else
            return null;
    }

    /**
     * Returns the ancestor list of this class.
     */
    public ModelElementList<DataAbstraction> getAncestorsList() {
        if (supertypes == null)
            supertypes = new ModelElementList<DataAbstraction>();
        return supertypes;
    }

    /**
     * Adds a new descendant to this class.
     */
    public void addDescendant(DataAbstraction descendant) {
        if (descendants == null)
            descendants = new ModelElementList<DataAbstraction>();
        descendants.add(descendant);
    }

    public void addRelationAsDescendent(InheritanceRelation rel) {
        if (relationAsSubType == null) {
            relationAsSubType = new ModelElementList<InheritanceRelation>();
        }
        relationAsSubType.add(rel);
    }

    public void addRelationAsAncestor(InheritanceRelation rel) {
        if (relationAsSuperType == null) {
            relationAsSuperType = new ModelElementList<InheritanceRelation>();
        }
        relationAsSuperType.add(rel);
    }

    public ModelElementList<InheritanceRelation> getRelationAsSubClass() {
        if (relationAsSubType == null) {
            relationAsSubType = new ModelElementList<InheritanceRelation>();
        }
        return relationAsSubType;
    }

    public ModelElementList<InheritanceRelation> getRelationAsSuperClass() {
        if (relationAsSuperType == null) {
            relationAsSuperType = new ModelElementList<InheritanceRelation>();
        }
        return relationAsSuperType;
    }

    /**
     * Returns the list of interfaces that this data abstraction directly implements.
     */
    public ModelElementList<Class> getInterfaces() {
        ModelElementList<Class> interfacesList = new ModelElementList<Class>();
        for (DataAbstraction currentDataAbstraction : getAncestorsList()) {
            if (currentDataAbstraction instanceof Class &&
                    ((DataAbstraction)currentDataAbstraction).isInterface())
                interfacesList.add((Class)currentDataAbstraction);
        }
        return interfacesList;
    }

    /**
     * Returns the list of the data abstractions direclty derived from this data abstraction.
     */
    public ModelElementList<DataAbstraction> getDescendants() {
        if (descendants == null)
            descendants = new ModelElementList<DataAbstraction>();
        return descendants;
    }

    boolean restore() {
        if (super.restore()) {
            attributes.restore();
            if (descendants != null)
                descendants.restore();
            if (supertypes != null)
                supertypes.restore();
            if (relationAsSuperType != null)
                relationAsSuperType.restore();
            if (relationAsSubType != null)
                relationAsSubType.restore();
            methods.restore();
            if (innerTypes != null)
                innerTypes.restore();
            return true;
        }
        return false;
    }
}
