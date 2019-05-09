package lrg.memoria.hismo.utils;

import lrg.memoria.hismo.core.*;

import java.util.ArrayList;
import java.util.Iterator;

public class HismoWrapper {
    protected SystemHistory currentSystemHistory;

    public HismoWrapper(ArrayList historyNamesList, ArrayList pathList, ArrayList cacheList) {
        HismoLoader hismoBuilder = new HismoLoader(historyNamesList, pathList, cacheList, "testdata", null, HismoLoader.CPP_SOURCES);
        currentSystemHistory = hismoBuilder.getSystemHistory();
    }

    public HismoWrapper(SystemHistory systemHistory) {
        currentSystemHistory = systemHistory;
    }

    public SystemHistory getSystemHistory() {
        return currentSystemHistory;
    }

    public PackageHistoryGroup getPackageHistories() {
        return currentSystemHistory.getPackageHistories();
    }

    public NamespaceHistoryGroup getNamespaceHistories() {
        return currentSystemHistory.getNamespaceHistories();
    }

    public ClassHistoryGroup getClassHistories() {
        return currentSystemHistory.getClassHistories();
    }

    public PackageHistory getPackageHistoryNamed(String name) {
        PackageHistoryGroup packageHistories = getPackageHistories();
        for (Iterator it = packageHistories.getHistoriesCollection().iterator(); it.hasNext();) {
            PackageHistory packHist = (PackageHistory) it.next();
            if (name.equals(packHist.getFullName()))
                return packHist;
        }
        return null;
    }

    public NamespaceHistory getNamespaceHistoryNamed(String name) {
        NamespaceHistoryGroup namespaceHistories = getNamespaceHistories();
        for (Iterator<AbstractHistory> it = namespaceHistories.getHistoriesCollection().iterator(); it.hasNext();) {
            NamespaceHistory namespaceHistory = (NamespaceHistory) it.next();
            if (name.equals(namespaceHistory.getFullName()))
                return namespaceHistory;
        }
        return null;
    }

    public GlobalFunctionHistory getGlobalFunctionHistoryNamed(String fullName) {
        String temp = fullName.substring(0, fullName.indexOf("("));
        String namespaceName = temp.substring(0, temp.lastIndexOf("."));
        NamespaceHistory namespaceHistory = getNamespaceHistoryNamed(namespaceName);
        if (namespaceHistory == null)
            return null;
        GlobalFunctionHistoryGroup globalFunctionHistories = namespaceHistory.getGlobalFunctionHistories();
        return (GlobalFunctionHistory) globalFunctionHistories.get(fullName);
    }

    public GlobalVariableHistory getGlobalVariableHistoryNamed(String fullName) {
        String namespaceName = fullName.substring(0, fullName.lastIndexOf("."));
        NamespaceHistory namespaceHistory = getNamespaceHistoryNamed(namespaceName);
        if (namespaceHistory == null)
            return null;
        GlobalVariableHistoryGroup globalVariableHistories = namespaceHistory.getGlobalVariableHistories();
        return (GlobalVariableHistory) globalVariableHistories.get(fullName);
    }

    public ClassHistory getClassHistoryNamed(String fullName) {
        String namespaceName = fullName.substring(0, fullName.lastIndexOf("."));
        NamespaceHistory namespaceHistory = getNamespaceHistoryNamed(namespaceName);
        if (namespaceHistory == null)
            return null;
        ClassHistoryGroup classHistories = namespaceHistory.getClassHistories();
        return (ClassHistory) classHistories.get(fullName);
    }

    public MethodHistory getMethodHistoryNamed(String fullName) {
        String methodName = fullName.substring(0, fullName.indexOf("("));
        String fullClassName = methodName.substring(0, methodName.lastIndexOf("."));
        ClassHistory classHistory = getClassHistoryNamed(fullClassName);
        if (classHistory == null)
            return null;
        MethodHistoryGroup methodHistories = classHistory.getMethodHistories();
        return (MethodHistory) methodHistories.get(fullName);
    }

    public AttributeHistory getAttributeHistoryNamed(String fullName) {
        String fullClassName = fullName.substring(0, fullName.lastIndexOf("."));
        ClassHistory classHistory = getClassHistoryNamed(fullClassName);
        if (classHistory == null)
            return null;
        AttributeHistoryGroup attributeHistories = classHistory.getAttributeHistories();
        return (AttributeHistory) attributeHistories.get(fullName);
    }
}
