package lrg.memoria.core;

public class TemplateInstance extends NamedModelElement implements Type {
    private GenericClass templateType;
    private ModelElementList<Type> typeInstantiations;

    public TemplateInstance(GenericClass template, String name) {
        super(name);
        templateType = template;
        typeInstantiations = new ModelElementList<Type>();
    }

    public String getFullName() {
        return getScope().getFullName() + "." + getName();
    }

    public String getName() {
        return name;
    }

    public GenericClass getTemplateType() {
        return templateType;
    }

    public void addTypeInstantiation(Type t) {
        typeInstantiations.add(t);
    }

    public ModelElementList<Type> getTypeInstantiations() {
        return typeInstantiations;
    }

    public Scope getScope() {
        return templateType.getScope();
    }

    public void accept(ModelVisitor mv) {
        mv.visitTemplateInstance(this);
    }

    boolean restore() {
        if (super.restore()) {
            typeInstantiations.restore();
            return true;
        }
        return false;
    }
}
