package lrg.memoria.importer.mcc.loader;

import lrg.memoria.core.*;
import lrg.memoria.core.Class;
import lrg.memoria.core.Package;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultVariableVisitor extends DefaultVisitorRoot implements VariableVisitor {

    private Integer varId;
    private Location location;
    private String varName;
    private int accessMode;
    private boolean isStatic;
    private boolean isConst;
    private Type type;
    private String kindOf;
    private DataAbstraction typeScope;
    private Package packageScope;
    private Namespace namespaceScope;
    private Integer bodyScopeId;
    private Integer refersToVariable;
    private HashMap<Integer, GlobalVariable> refersToMap = new HashMap<Integer, GlobalVariable>();
    private File currentFile;

    public void setId(Integer id) {
        varId = id;
        accessMode = 0;
    }

    public void setFileName(String fileFullName) {
        if (!fileFullName.equals(NULL)) {
            currentFile = Loader.getInstance().getFileByName(fileFullName);
            location = new Location(currentFile);
        } else
            location = new Location(File.getUnknownFile());
    }

    public void setDeclarationLine(Integer declarationLine) {
        location.setStartLine(declarationLine.intValue());
    }

    public void setVariableName(String variableName) {
        if (variableName.indexOf(NO_NAME) >= 0)
            varName = "";
        else
            varName = variableName;
    }

    public void setKindOf(String kindOf) {
        this.kindOf = kindOf;
    }

    public void setTypeId(String tId) {
        if (tId.indexOf(ERROR) >= 0 || tId.indexOf(NO_ONE) >= 0)
            type = Class.getUnknownClass();
        else {
            int temp = Integer.parseInt(tId);
            type = Loader.getInstance().getType(new Integer(temp));
            if (type == null)
                type = Class.getUnknownClass();
        }
        //assert type != null : "Error: Could not find the type with ID=" + tId + " for variable named: " + varName + " !";
    }

    public void setAccess(String access) {
        if (access.indexOf("public") >= 0)
            accessMode = AccessMode.PUBLIC;
        if (access.indexOf("private") >= 0)
            accessMode = AccessMode.PRIVATE;
        if (access.indexOf("protected") >= 0)
            accessMode = AccessMode.PROTECTED;
    }

    public void setClassId(String cId) {
        if (cId.indexOf(ERROR) >= 0 || cId.indexOf(NULL) >= 0)
            typeScope = Class.getUnknownClass();
        else {
            int temp = Integer.parseInt(cId);
            typeScope = (DataAbstraction) Loader.getInstance().getType(new Integer(temp));
        }
    }

    public void setBodyId(String bId) {
        if (bId.indexOf(ERROR) >= 0 || bId.indexOf(NULL) >= 0)
            bodyScopeId = new Integer(-1);
        else {
            bodyScopeId = new Integer(bId);
        }
    }

    public void setPackageId(String pId) {
        if (pId.indexOf(NULL) < 0) {
            int temp = Integer.parseInt(pId);
            packageScope = Loader.getInstance().getPackage(new Integer(temp));
        }
        if (packageScope == null)
            packageScope = Package.getUnknownPackage();
    }

    public void setNamespaceId(String namespaceId) {
        if (namespaceId.indexOf(NO_ONE) >= 0) {
            namespaceScope = Namespace.getAnonymousNamespace();
            return;
        }
        if (namespaceId.indexOf(ERROR) < 0 && namespaceId.indexOf(NULL) < 0) {
            int temp = Integer.parseInt(namespaceId);
            namespaceScope = Loader.getInstance().getNamespace(new Integer(temp));
        }
        if (namespaceScope == null)
            namespaceScope = Namespace.getUnknownNamespace();
    }

    public void setIsStatic(Integer isSt) {
        if (isSt.intValue() == 1)
            isStatic = true;
        else
            isStatic = false;
    }

    public void setIsConst(Integer isCt) {
        if (isCt.intValue() == 1)
            isConst = true;
        else
            isConst = false;
    }

    public void setRefersTo(String refersTo) {
        if (refersTo.indexOf(ERROR) >= 0 || refersTo.indexOf(NULL) >= 0 ||
                refersTo.indexOf(NOT_INIT) >= 0 || refersTo.indexOf(NO_ONE) >= 0)
            refersToVariable = new Integer(0);
        else {
            refersToVariable = new Integer(refersTo);
        }

    }

    public void addVariable() {
        Variable var = null;
        if (kindOf.equals("global"))
            var = addGlobalVariable();
        if (kindOf.equals("attribute"))
            var = addAttribute();
        if (kindOf.equals("parameter"))
            var = addParameter();
        if (kindOf.equals("local"))
            var = addLocalVariable();
        var.setLocation(location);
        var.setType(type);
        if (isConst)
            var.setFinal();
        if (refersToVariable.intValue() > 0) {
            assert var instanceof GlobalVariable : "ERROR: The variable that refers an extern variable should be global !";
            if (var instanceof GlobalVariable)
                refersToMap.put(refersToVariable, (GlobalVariable) var);
        }
        var.setStatute(Statute.NORMAL);
        Loader.getInstance().addVariable(varId, var);
    }

    public void variablesEOF() {
        Set<Map.Entry<Integer, GlobalVariable>> keys = refersToMap.entrySet();
        GlobalVariable var, refersTo;
        Integer refersToID;
        for (Map.Entry<Integer, lrg.memoria.core.GlobalVariable> key : keys) {
            refersToID = key.getKey();
            var = key.getValue();
            refersTo = (GlobalVariable) Loader.getInstance().getVariable(refersToID);
            var.setRefersTo(refersTo);
        }
    }

    private Variable addGlobalVariable() {
        GlobalVariable gv = new GlobalVariable(varName);
        if (isStatic) {
            UnnamedNamespace unsp = Loader.getInstance().getUnnamedNamespace(currentFile.getFullName());
            gv.setScope(unsp);
            unsp.addGlobalVariable(gv);
        } else {
            gv.setScope(namespaceScope);
            namespaceScope.addGlobalVariable(gv);
        }
        gv.setPackage(packageScope);
        packageScope.addGlobalVariable(gv);
        if (isStatic)
            gv.setStatic();
        return gv;
    }

    private Variable addLocalVariable() {
        LocalVariable lv = new LocalVariable(varName);
        FunctionBody fb = Loader.getInstance().getBody(bodyScopeId);
        if ((lv == null) || (fb == null)) return lv;

        fb.addLocalVar(lv);
        lv.setScope(fb.getCodeStripe());
        if (isStatic)
            lv.setStatic();
        return lv;
    }

    private Variable addAttribute() {
        Attribute attr = new Attribute(varName);
        attr.setScope(typeScope);
        typeScope.addAttribute(attr);
        assert accessMode != 0 : "Error: could not identify the access mode for attribute *" + attr.getFullName() + "*";
        attr.setAccessMode(accessMode);
        if (isStatic)
            attr.setStatic();
        return attr;
    }

    private Parameter addParameter() {
        Parameter par = new Parameter(varName);
        FunctionBody fb = Loader.getInstance().getBody(bodyScopeId);
        if (fb == null) return par;
        Function func = (Function) fb.getScope();
        if (func == null) return par;
        func.addParameter(par);
        par.setScope(func);
        return par;
    }
}
