package lrg.memoria.core;

public class TemplateParameterType extends DataAbstraction {
    public static String UNKNOWN_TPT_NAME = "unknown_tpt";
    public static TemplateParameterType getUnknownTemplateParameterType() {
        return ModelElementsRepository.getCurrentModelElementsRepository().getUnknownTemplateParameterType();
    }

    /*------------------------------------------------------*/

    private ModelElementList<Type> instantiationTypes;

    public TemplateParameterType(String name) {
        super(name);
        instantiationTypes = new ModelElementList<Type>();
    }

    public void addInstantiationType(Type it) {
        instantiationTypes.add(it);
    }

    public ModelElementList<Type> getInstantiationTypes() {
        return instantiationTypes;
    }

    public void accept(ModelVisitor mv) {
        mv.visitTemplateParameter(this);
    }

    public String toString() {
        return "TemplateParameterType \n\tname: " + name;
    }

    boolean restore() {
        if (super.restore()) {
            instantiationTypes.restore();            
            return true;
        }
        return false;
    }
}
