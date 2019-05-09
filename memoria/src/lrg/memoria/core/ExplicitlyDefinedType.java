package lrg.memoria.core;

public abstract class ExplicitlyDefinedType extends NamedModelElement implements Type {
    private Location location = Location.getUnknownLocation();
    private Package containingPackage = Package.getUnknownPackage();
    private Scope scope = Namespace.getUnknownNamespace();

    protected ExplicitlyDefinedType(ExplicitlyDefinedType st) {
        super(st);
        containingPackage = st.containingPackage;
        scope = st.scope;
        location = st.location;
    }

    public ExplicitlyDefinedType(String name) {
        super(name);
    }

    /**
     * Set location (i.e. the getName of the file and the position where it was defined) and line (i.e. the line in its file -- only for NORMAL types otherwise this parameter must be 0).
     */
    public void setLocation(Location loc) {
        location = loc;
    }

    /**
     * Get the Location where this type is defined in.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set the package to which this type belongs.
     */
    public void setPackage(Package pack) {
        containingPackage = pack;
    }

    /**
     * Get the package object to which the type directly belongs
     * (i.e. the package where the type was defined in).
     */
    public Package getPackage() {
        return containingPackage;
    }

    /**
     * If the type is an inner type then this method must receive a reference to the type
     * directly encloses the given type.
     * <P>
     * e.g., Java allowes anonymous inner classes to  be defined directly in a method.
     * In this case, the scope is given by the function body of that method.
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }
    
    public Scope getScope() {
        if (scope instanceof CodeStripe)
            return ((CodeStripe)scope).getParentBody();
        else return scope;
    }

    /**
     * Get the full getName of this class (i.e. namespace_name.className).
     */
    public String getFullName() {
        if (scope instanceof Namespace)
            return scope.getFullName() + "." + name;
        else
            if (scope instanceof CodeStripe) {
                Body scp=((CodeStripe)scope).getParentBody();
                if (scp instanceof FunctionBody)
                    return scp.getScope().getScope().getFullName() + "$" + name;
                else
                    if (scp instanceof InitializerBody)
                        return scp.getScope().getFullName() + "$" + name;
            }
        return scope.getFullName() + "$" + name;
    }

    /**
     * Returns the getName of this class (i.e. outerClassName$innerClassName).
     */
    public String getName() {
        return name;
    }

    public ModelElementList<ExplicitlyDefinedType> getContainedClasses() {
        ModelElementList<ExplicitlyDefinedType> containedClasses = new ModelElementList<ExplicitlyDefinedType>();
        ModelElementList<Type> typesList = getPackage().getAllTypesList();
        for (Type currentType : typesList) {
            if (currentType instanceof Class) {
                Scope scope = ((DataAbstraction)currentType).getScope();
                while (!(scope instanceof Namespace)) {
                    if (scope == this)
                        containedClasses.add((DataAbstraction)currentType);
                    scope = scope.getScope();
                }
            }
        }
        return containedClasses;
    }
}
