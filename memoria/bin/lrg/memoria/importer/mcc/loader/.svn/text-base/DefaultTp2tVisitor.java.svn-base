package lrg.memoria.importer.mcc.loader;

import lrg.memoria.core.TemplateParameterType;
import lrg.memoria.core.Type;

public class DefaultTp2tVisitor extends DefaultVisitorRoot implements Tp2tVisitor {
    private Integer id;
    private Loader loaderInstance = Loader.getInstance();
    private TemplateParameterType templateParameterType;
    private Type instantiationType;

    public void setId(String id) {
        this.id = new Integer(id);
    }

    public void setTemplateParamID(String tpi) {
        if (!tpi.equals(UNKNOWN) && !tpi.equals(ERROR))
            templateParameterType = (TemplateParameterType) loaderInstance.getType(new Integer(tpi));
        if (templateParameterType == null)
            templateParameterType = TemplateParameterType.getUnknownTemplateParameterType();
        //assert templateParameterType != null : "DefaultTp2tVisitor ERROR: could not find the template parameter for integer: " + tpi;
    }

    public void setInstantiationTypeID(String iti) {
        if (!iti.equals(ERROR))
            instantiationType = loaderInstance.getType(new Integer(iti));
        if (instantiationType == null)
            instantiationType = lrg.memoria.core.Class.getUnknownClass();
        //assert instantiationType != null : "DefaultTp2tVisitor ERROR: could not find the instantiation type for integer: " + iti;
    }

    public void addInstantiation() {
        templateParameterType.addInstantiationType(instantiationType);
        Loader.getInstance().addTemplateParameterToType(id, instantiationType);
    }
}
