package lrg.memoria.importer.mcc.loader;

import lrg.memoria.core.*;
import lrg.memoria.core.Class;
import lrg.memoria.core.Package;

import java.lang.System;

public class DefaultFunctionVisitor extends DefaultVisitorRoot implements FunctionVisitor {
    private Integer id;
    private String name;
    private String kind;
    private String signature;
    private Type returnType;
    private Type typeScope;
    private Namespace namespaceScope;
    private int accessMode;
    private boolean isStatic;
    private boolean isVirtual;
    private boolean isConstructor;
    private boolean isGlobalFunction;
    private FunctionBody functionBody;

    private int statute=Statute.FAILEDDEP;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setReturnType(String returnType) {
        if ((returnType.indexOf(NO_ONE) >= 0)) {
            isConstructor = true;
            return;
        }
        isConstructor = false;
        if (!(returnType.indexOf(ERROR) >= 0))
            this.returnType = Loader.getInstance().getType(new Integer(returnType));
        if (this.returnType == null)
            this.returnType = Class.getUnknownClass();
        //assert returnType != null : "DefaultFunctionVisitor ERROR: could not find the return type for function:" + getName;
    }

    public void setScopeId(String scopeId) {
        typeScope = null;
        if (!(scopeId.indexOf(ERROR) >= 0) && !(scopeId.indexOf(NO_ONE) >= 0))
            typeScope = (Type) Loader.getInstance().getType(new Integer(scopeId));
        if (typeScope == null)
            typeScope = Class.getUnknownClass();
        //assert typeScope != null : "DefaultFunctionVisitor ERROR: could not find the scope type for function:" + getName;
    }

    public void setNamespaceId(String nId) {
        if (nId.equals(NULL)) {
            isGlobalFunction = false;
            return;
        }
        isGlobalFunction = true;
        if (nId.equals(NO_ONE)) {
            namespaceScope = Namespace.getAnonymousNamespace();
            return;
        }
        namespaceScope = null;
        if (!nId.equals(ERROR))
            namespaceScope = Loader.getInstance().getNamespace(new Integer(nId));
        if (namespaceScope == null)
            namespaceScope = Namespace.getUnknownNamespace();
        //assert namespaceScope != null : "DefaultFunctionVisitor ERROR: could not find the namespace scope for function:" + getName;
    }

    public void setAccess(String access) {
        if (access.equals(NULL))
            return;
        if (access.equals("public"))
            accessMode = AccessMode.PUBLIC;
        if (access.equals("private"))
            accessMode = AccessMode.PRIVATE;
        if (access.equals("protected"))
            accessMode = AccessMode.PROTECTED;
    }

    public void setIsStatic(String isSt) {
        if (isSt.indexOf("1") >= 0)
            isStatic = true;
        else
            isStatic = false;
    }

    public void setIsVirtual(String isVt) {
        if (isVt.indexOf("1") >= 0)
            isVirtual = true;
        else
            isVirtual = false;
    }

    public void setBodyId(String bId) {
        if (bId.equals(ERROR) || bId.equals(INIT_NULL_BODY) ||
                bId.equals(ONLY_DECLARED) || bId.equals(NULL))
            functionBody = null;
        else
            functionBody = Loader.getInstance().getBody(new Integer(bId));
    }

    public void addFunction() {
        Function func = null;
        if (kind.equals("pointer-to-function"))
            func = addPointerToFunction();
        else if (isConstructor)
            func = addConstructor();
        else if (isGlobalFunction)
            func = addGlobalFunction();
        else
            func = addMethod();
        if (!isConstructor)
            func.setReturnType(returnType);
        if (functionBody != null) {
            func.setFunctionBody(functionBody);
            func.setLocation(functionBody.getLocation());
            functionBody.setScope(func);
        }
        Loader.getInstance().addFunction(id, func);
    }

    private Function addPointerToFunction() {
        PointerToFunction fp = new PointerToFunction(name);
        fp.setScope((FunctionPointer) typeScope);
        return fp;
    }

    private Method addMethod() {
        Method meth = new Method(name);
        setStatute();
        meth.setStatute(statute);
        meth.setAccessMode(accessMode);
        if (typeScope instanceof TypedefDecorator) typeScope = ((TypedefDecorator) typeScope).getDecoratedType();
        meth.setScope((DataAbstraction) typeScope);
        ((DataAbstraction) typeScope).addMethod(meth);
        if (isVirtual)
            meth.setVirtual();
        if (isStatic)
            meth.setStatic();
        return meth;

    }

    private Function addGlobalFunction() {
        GlobalFunction gf = new GlobalFunction(name);
        setStatute();
        gf.setStatute(statute);
        if (functionBody != null) {
            Package currentPackage = Loader.getInstance().getPackageForBody(functionBody);
            gf.setPackage(currentPackage);
            currentPackage.addGlobalFunction(gf);
        }
        if (isStatic) {
            File temp;
            if (functionBody != null)
                temp = functionBody.getLocation().getFile();
            else
                temp = File.getUnknownFile();
            UnnamedNamespace unsp = Loader.getInstance().getUnnamedNamespace(temp.getFullName());
            gf.setScope(unsp);
            gf.setStatute(unsp.getStatute());
            unsp.addGlobalFunction(gf);
            gf.setStatic();
        } else {
            gf.setScope(namespaceScope);
            namespaceScope.addGlobalFunction(gf);
        }
        return gf;
    }

    private Function addConstructor() {
        Method cons = addMethod();
        cons.setKindOf(Method.CONSTRUCTOR);
        return cons;
    }

    private void setStatute() {
        if(typeScope!=null) {
            if(typeScope!=Class.getUnknownClass()) {
                if (typeScope instanceof lrg.memoria.core.Class)
                    statute = ((DataAbstraction)typeScope).getStatute();
                if (typeScope instanceof lrg.memoria.core.Union)
                    statute = ((lrg.memoria.core.Union)typeScope).getStatute();
                }
            else
                if(namespaceScope!=null)
                    statute = namespaceScope.getStatute();
        }
    }
}
