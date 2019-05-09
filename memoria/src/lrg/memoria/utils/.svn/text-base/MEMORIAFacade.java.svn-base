package lrg.memoria.utils;

import lrg.memoria.core.*;
import lrg.memoria.core.Class;
import lrg.memoria.core.Package;

import java.util.ArrayList;

public class MEMORIAFacade {
    protected lrg.memoria.core.System currentSystem;

    protected MEMORIAFacade() {

    }

    public MEMORIAFacade(lrg.memoria.core.System aSystem) {
        currentSystem = aSystem;
    }

    public MEMORIAFacade(String pathList) {
        try {
            java.lang.System.out.println("Loading the model from: " + pathList);
            lrg.memoria.importer.recoder.JavaModelLoader model = new lrg.memoria.importer.recoder.JavaModelLoader(pathList, null, null);
            currentSystem = model.getSystem();
        } catch (Exception e) {
            java.lang.System.err.println(e);
            e.printStackTrace();
        }
    }

    public lrg.memoria.core.System getSystem() {
        return currentSystem;
    }

    public lrg.memoria.core.Package getPackageNamed(String packageName) {
        lrg.memoria.core.Package currentPackage;
        ArrayList packageList = currentSystem.getPackages();
        for (int i = 0; i < packageList.size(); i++) {
            currentPackage = (Package) packageList.get(i);
            if (currentPackage.getName().equals(packageName))
                return currentPackage;
        }
        return null;
    }

    public lrg.memoria.core.Namespace getNamespaceNamed(String namespaceName) {
        Namespace currentNamespace;
        ArrayList namespacesList = currentSystem.getNamespaces();
        for (int i = 0; i < namespacesList.size(); i++) {
            currentNamespace = (Namespace) namespacesList.get(i);
            if (currentNamespace.getName().equals(namespaceName))
                return currentNamespace;
        }
        return null;
    }

    public lrg.memoria.core.UnnamedNamespace getUnnamedNamespaceFromFileNamed(String fileName) {
        ModelElementList<UnnamedNamespace> unspList = currentSystem.getUnnamedNamespaces();
        for (UnnamedNamespace currentNsp : unspList) {
            if (currentNsp.getFullName().equals(fileName))
                return currentNsp;
        }
        return null;
    }

    private Namespace searchNamespaceFromName(String name) {
        int index = name.lastIndexOf(".");
        if (index < 0)
            return null;
        String nspName = name.substring(0, index);
        Namespace currentNamespace = getNamespaceNamed(nspName);
        if (currentNamespace != null)
            return currentNamespace;
        else
            return searchNamespaceFromName(nspName);
    }

    public Class getClassNamed(String classFullName) {
        return (Class) getTypeNamed(classFullName);
    }

    public Type getTypeNamed(String typeFullName) {
        Namespace currentNamespace = searchNamespaceFromName(typeFullName);
        if (currentNamespace == null)
            return null;
        ArrayList classList = currentNamespace.getAllTypesList();
        Type currentType;
        for (int i = 0; i < classList.size(); i++) {
            currentType = (Type) classList.get(i);
            if (currentType.getFullName().equals(typeFullName))
                return currentType;
        }
        return null;
    }

    /**
     * @deprecated - use getFunctionNamed instead
     */
    public Method getMethodNamed(String methodFullName) {
        return (Method) getFunctionNamed(methodFullName);
    }

    public Function getFunctionNamed(String functionFullName) {
        int index = functionFullName.lastIndexOf("(");
        String temp = functionFullName.substring(0, index);
        int index1 = temp.lastIndexOf(".");
        String fullScopeName = temp.substring(0, index1);
        DataAbstraction currentClass = (DataAbstraction) getTypeNamed(fullScopeName);
        ArrayList functionList;
        if (currentClass != null)
            functionList = currentClass.getMethodList();
        else {
            if (isFileName(fullScopeName)) {
                UnnamedNamespace tmp = getUnnamedNamespaceFromFileNamed(fullScopeName);
                if (tmp != null)
                    functionList = tmp.getGlobalFunctionsList();
                else
                    return null;
            } else {
                Namespace nsp = getNamespaceNamed(fullScopeName);
                if (nsp != null)
                    functionList = nsp.getGlobalFunctionsList();
                else
                    return null;
            }
        }
        Function currentFunction;
        for (int i = 0; i < functionList.size(); i++) {
            currentFunction = (Function) functionList.get(i);
            if (currentFunction.getFullName().equals(functionFullName))
                return currentFunction;
        }
        return null;
    }

    public Variable getVariableNamed(String variableFullName) {
        Variable var;
        if (variableFullName.lastIndexOf(")") > 0)
            var = getLocalVariableNamed(variableFullName);
        else {
            var = getGlobalVariableNamed(variableFullName);
            Variable refersTo = ((GlobalVariable) var).getRefersTo();
            if (refersTo != null)
                var = refersTo;
        }
        return var;
    }

    private GlobalVariable getGlobalVariableNamed(String variableFullName) {
        int index = variableFullName.lastIndexOf(".");
        String variableName = variableFullName.substring(index + 1);
        String scopeName = variableFullName.substring(0, index);
        ArrayList globalVarList;
        if (isFileName(scopeName)) {
            UnnamedNamespace unsp = getUnnamedNamespaceFromFileNamed(scopeName);
            if (unsp == null)
                return null;
            globalVarList = unsp.getGlobalVariablesList();
        } else {
            Namespace currentNamespace = getNamespaceNamed(scopeName);
            if (currentNamespace == null)
                return null;
            globalVarList = currentNamespace.getGlobalVariablesList();
        }
        GlobalVariable currentGlobalVar;
        for (int i = 0; i < globalVarList.size(); i++) {
            currentGlobalVar = (GlobalVariable) globalVarList.get(i);
            if (currentGlobalVar.getName().equals(variableName))
                return currentGlobalVar;
        }
        return null;
    }

    private boolean isFileName(String scopeName) {
        return scopeName.endsWith(".cc") || scopeName.endsWith(".c") || scopeName.endsWith(".cpp.");
    }

    private Variable getLocalVariableNamed(String variableFullName) {
        int index = variableFullName.lastIndexOf(")");
        String variableName = variableFullName.substring(index + 2);
        String functionFullName = variableFullName.substring(0, index + 1);
        Function currentMethod = getFunctionNamed(functionFullName);
        if (currentMethod == null)
            return null;

        ModelElementList<Parameter> parameterList = currentMethod.getParameterList();
        for (Parameter currentParameter : parameterList) {
            if (currentParameter.getName().equals(variableName))
                return currentParameter;
        }

        Body currentBody = currentMethod.getBody();
        if (currentBody == null)
            return null;

        ModelElementList<LocalVariable> localVarList = currentBody.getLocalVarList();
        for (LocalVariable currentLocalVar : localVarList) {
            if (currentLocalVar.getName().equals(variableName))
                return currentLocalVar;
        }
        return null;
    }

    public Attribute getAttributeNamed(String attributeFullName) {
        Attribute att;
        int index = attributeFullName.lastIndexOf(".");
        String fullClassName = attributeFullName.substring(0, index);
        String attName = attributeFullName.substring(index + 1, attributeFullName.length());
        DataAbstraction currentClass = getClassNamed(fullClassName);
        if (currentClass == null)
            return null;
        ArrayList attributeList = currentClass.getAttributeList();
        for (int i = 0; i < attributeList.size(); i++) {
            att = (Attribute) attributeList.get(i);
            if (att.getName().equals(attName))
                return att;
        }
        return null;
    }
}
