package lrg.memoria.importer.mcc.loader;

import lrg.memoria.core.TemplateInstance;
import lrg.memoria.core.Type;

public class DefaultChainVisitor extends DefaultVisitorRoot implements ChainVisitor {
    private Integer tid;
    private TemplateInstance templateInstance;
    private boolean errorOccured;

    public void setId(Integer id) {
        errorOccured = false;
    }

    public void setT2TRelationId(String relationId) {
        if (relationId.equals(ERROR)) {
            errorOccured = true;
            return;
        }
        tid = new Integer(relationId);
    }

    public void setTemplateInstanceId(String templateInstanceId) {
        if (templateInstanceId.equals(ERROR)) {
            errorOccured = true;
            return;
        }
        if (Loader.getInstance().getType(new Integer(templateInstanceId)) instanceof TemplateInstance)
            templateInstance = (TemplateInstance) Loader.getInstance().getType(new Integer(templateInstanceId));
        else {
            errorOccured = true;
        }
    }

    public void addChain() {
        if (!errorOccured) {
            Type ti = Loader.getInstance().getTemplateParameterType(tid);
            templateInstance.addTypeInstantiation(ti);
        }
    }
}
