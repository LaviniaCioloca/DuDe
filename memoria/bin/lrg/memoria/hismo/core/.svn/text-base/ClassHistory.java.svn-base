package lrg.memoria.hismo.core;

import lrg.memoria.core.Attribute;
import lrg.memoria.core.Method;

import java.util.ArrayList;

public class ClassHistory extends AbstractHistory {

    MethodHistoryGroup methodHistories;
    AttributeHistoryGroup attributeHistories;
    NamespaceHistory namespaceHistory;

    public ClassHistory() {
        initializeInnerHistories();
    }

    public ClassHistory(NamespaceHistory nsh) {
        namespaceHistory = nsh;
        initializeInnerHistories();
    }

    public ClassHistory(VersionsList versions) {
        super(versions);
    }

    protected void initializeInnerHistories() {
        methodHistories = new MethodHistoryGroup();
        attributeHistories = new AttributeHistoryGroup();
    }

    public NamespaceHistory getNamespaceHistory() {
        return namespaceHistory;
    }

    public MethodHistoryGroup getMethodHistories() {
        return methodHistories;
    }

    public AttributeHistoryGroup getAttributeHistories() {
        return attributeHistories;
    }

    protected void updateInnerHistories(AbstractVersion version) {
        updateMethodHistories(version);
        updateAttributeHistories(version);
    }

    private void updateMethodHistories(AbstractVersion version) {
        ArrayList currentMethods = ((ClassVersion) version).getMethodList();
        Method currentMethod;
        MethodVersion currentMethodVersion;
        String currentMethodName;
        AbstractHistory currentHistory;
        for (int i = 0; i < currentMethods.size(); i++) {
            currentMethod = (Method) currentMethods.get(i);
            currentMethodVersion = new MethodVersion(version.versionName(), currentMethod);
            currentMethodName = currentMethod.getFullName();
            currentHistory = methodHistories.get(currentMethodName);
            if (currentHistory == null) {
                currentHistory = new MethodHistory(this);
                currentHistory.addVersion(currentMethodVersion);
                methodHistories.put(currentHistory);
            } else
                currentHistory.addVersion(currentMethodVersion);
        }
    }

    private void updateAttributeHistories(AbstractVersion version) {
        ArrayList currentAttributes = ((ClassVersion) version).getAttributeList();
        Attribute currentAttribute;
        AttributeVersion currentAttributeVersion;
        String currentAttributeName;
        AbstractHistory currentHistory;
        for (int i = 0; i < currentAttributes.size(); i++) {
            currentAttribute = (Attribute) currentAttributes.get(i);
            currentAttributeVersion = new AttributeVersion(version.versionName(), currentAttribute);
            currentAttributeName = currentAttribute.getFullName();
            currentHistory = attributeHistories.get(currentAttributeName);
            if (currentHistory == null) {
                currentHistory = new AttributeHistory(this);
                currentHistory.addVersion(currentAttributeVersion);
                attributeHistories.put(currentHistory);
            } else
                currentHistory.addVersion(currentAttributeVersion);
        }
    }
}
