package lrg.memoria.core;

public class GenericClass extends Class {
    private ModelElementList<TemplateParameterType> templateParameters;
    private ModelElementList<TemplateInstance> templateInstances;

    /**
     * Creates a new class with a specified getName.
     */
    public GenericClass(String name) {
        super(name);
        templateParameters = new ModelElementList<TemplateParameterType>();
        templateInstances = new ModelElementList<TemplateInstance>();
    }

    protected GenericClass(GenericClass oldClass) {
        super(oldClass);
        templateParameters = oldClass.templateParameters;
        templateInstances = new ModelElementList<TemplateInstance>();
    }

    public ModelElementList<TemplateParameterType> getTemplateParameters() {
        return templateParameters;
    }

    public void addTemplateParameters(TemplateParameterType tpt) {
        templateParameters.add(tpt);
    }

    public void addTemplateInstance(TemplateInstance ti) {
        templateInstances.add(ti);
    }

    public ModelElementList<TemplateInstance> getTemplateInstances() {
        return templateInstances;
    }

    public String toString() {
        int i, tmp;
        StringBuffer myStr = new StringBuffer("\t\t");
        myStr.append("GenericClass: ");
        myStr.append(getFullName());
        myStr.append("\n\t\t - location: ").append(getLocation());
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
        myStr.append("\n\t\t - attributes:\n");
        for (i = 0; i < attributes.size(); i++)
            myStr.append((Attribute) attributes.get(i));
        myStr.append("\t\t - methods:\n");
        for (Method currentMethod : methods)
            myStr.append(currentMethod);
        return new String(myStr);
    }

    boolean restore() {
        if (super.restore()) {
            templateInstances.restore();
            templateParameters.restore();                        
            return true;
        }
        return false;
    }
}
