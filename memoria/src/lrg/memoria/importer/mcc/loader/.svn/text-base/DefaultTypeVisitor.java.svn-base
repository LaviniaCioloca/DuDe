package lrg.memoria.importer.mcc.loader;

import lrg.memoria.core.*;
import lrg.memoria.core.Class;
import lrg.memoria.core.Package;

import java.util.ArrayList;
import java.util.Stack;

public class DefaultTypeVisitor extends DefaultVisitorRoot implements TypeVisitor {
    private Integer id;
    private String name;
    private Location location;
    private String kind;
    private Package currentPackage;
    private Namespace currentNamespace;
    private boolean isAbstract;
    private boolean isInterface;
    private boolean isGeneric;
    private int scopeId;
    private int decoratedTypeID;
    private static Stack lazyTypeScopesStack = new Stack();
    private static Stack lazyFuncsScopesStack = new Stack();

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLocation(String fileFullName, String startPosition, String stopPosition) {
        if (fileFullName.compareTo(NULL) == 0)
            location = Location.getUnknownLocation();
        else {
            File file = Loader.getInstance().getFileByName(fileFullName);
            location = new Location(file);
            location.setStartLine(new Integer(startPosition).intValue());
            if (stopPosition.compareTo(NULL) != 0)
                location.setEndLine(new Integer(stopPosition).intValue());
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setPackageId(String packageId) {
        currentPackage = null;
        if (!packageId.equals(NULL)) {
            currentPackage = Loader.getInstance().getPackage(new Integer(packageId));
        }
        if (currentPackage == null)
            currentPackage = Package.getUnknownPackage();
        //assert currentPackage != null : "DefaultTypeVisitor ERROR: Unable to find the package for type: " + getName;
    };

    public void setNamespaceId(String namespaceId) {
        currentNamespace = null;
        if (namespaceId.equals(NO_ONE)) {
            currentNamespace = Namespace.getAnonymousNamespace();
            return;
        }
        //namespaceId = NULL => the type is neither a struct/union/class nor a typedef
        if (namespaceId.equals(NULL) && kind.indexOf("library") >= 0)
            currentNamespace = Namespace.getAnonymousNamespace();
        if (!namespaceId.equals(ERROR) && !namespaceId.equals(NULL))
            currentNamespace = Loader.getInstance().getNamespace(new Integer(namespaceId));
        if (currentNamespace == null)
            currentNamespace = Namespace.getUnknownNamespace();
        //assert currentNamespace != null : "DefaultTypeVisitor ERROR: Unable to find the namespace for type: " + getName;
    }

    public void setIsAbstract(String isAbstract) {
        if (isAbstract.compareTo("1") == 0)
            this.isAbstract = true;
        else
            this.isAbstract = false;
    }

    public void setIsInterface(String isInterface) {
        if (isInterface.compareTo("1") == 0)
            this.isInterface = true;
        else
            this.isInterface = false;
    }

    public void setIsGeneric(String isGeneric) {
        if (isGeneric.equals("1"))
            this.isGeneric = true;
        else
            this.isGeneric = false;
    }

    public void setScopeId(String scopeId) {
        if (!scopeId.equals(NULL) && !scopeId.equals(NO_ONE) && !scopeId.equals(ERROR))
            this.scopeId = Integer.parseInt(scopeId);
        else
            this.scopeId = -1;
    }

    public void setDecoratedType(String decoratedType) {
        if (!decoratedType.equals(NULL) && !decoratedType.equals(NO_ONE) && !decoratedType.equals(NOT_INIT))
            decoratedTypeID = Integer.parseInt(decoratedType);
        else
            decoratedTypeID = -1;
    }

    public void addType() {
        Type currentType = null;
        if (kind.compareTo("primitive") == 0)
            currentType = (Primitive) addPrimitiveType();
        if (kind.indexOf("class") >= 0) {
            currentType = addClassType();
            ((DataAbstraction) currentType).setStatute(Statute.NORMAL);
        }
        if (kind.indexOf("struct") >= 0) {
            currentType = addClassType();
            ((Class) currentType).setStructure();
            ((DataAbstraction) currentType).setStatute(Statute.NORMAL);
        }
        if (kind.indexOf("union") >= 0)
            currentType = addUnionType();
        if (kind.compareTo("template-par") == 0)
            currentType = addTemplateParType();
        if (kind.compareTo("template-instance") == 0) {
            currentType = addTemplateInstance();
            if(currentType == null) return;
            ((TemplateInstance) currentType).setStatute(Statute.NORMAL);
        }
        if (kind.compareTo("library-type") == 0) {
            currentType = addClassType();
            ((ExplicitlyDefinedType) currentType).setStatute(Statute.LIBRARY);
        }
        if (kind.indexOf("decorator") > 0)
            currentType = addDecorator();
        if (kind.compareTo("pointer-to-function") == 0) {
            currentType = addFunctionPointer();
        }
        if (currentType == null) return;
        Loader.getInstance().addType(id, currentType);
        if (scopeId > 0) {
            if (kind.indexOf("in-func") < 0) {
                ArrayList pair = new ArrayList();
                pair.add(currentType);
                pair.add(new Integer(scopeId));
                lazyTypeScopesStack.push(pair);
            } else {
                Body body = Loader.getInstance().getBody(new Integer(scopeId));
                assert body != null : "DefaultTypeVisitor ERROR: could not find the body scope with ID=" + scopeId + " for type: " + currentType.getName();
                ((ExplicitlyDefinedType) currentType).setScope(body);
                body.addScopedElement(currentType);
            }
        }
    }

    private TemplateInstance addTemplateInstance() throws ClassCastException {
        TemplateInstance ti = null;
        if (decoratedTypeID > 0) {
            Type t = Loader.getInstance().getType(new Integer(decoratedTypeID));
            if (t instanceof GenericClass) {
                GenericClass generic = (GenericClass) t;
                ti = new TemplateInstance(generic, name);
                generic.addTemplateInstance(ti);
                generic.getScope().addScopedElement(ti);
            }
            else
                java.lang.System.out.println("ERROR " + name + "is not a generic Class and has template-instances");
        }
        return ti;
    }

    public void typesEOF() {
        Loader lo = Loader.getInstance();
        Scope nsp;
        for (; !lazyTypeScopesStack.empty();) {
            ArrayList pair = (ArrayList) lazyTypeScopesStack.pop();
            if (pair.get(0) instanceof ExplicitlyDefinedType) {
                ExplicitlyDefinedType scoped = (ExplicitlyDefinedType) pair.get(0);
                Integer scopeId = (Integer) pair.get(1);
                Scope scope = (Scope) lo.getType(scopeId);
                assert scope != null : "DefaultTypesVisitor ERROR: scope is null for: " + scoped.getFullName();
                scoped.setScope(scope);
                scope.addScopedElement(scoped);
                nsp = scope.getScope();
                while (!(nsp instanceof Namespace))
                    nsp = nsp.getScope();
                ((Namespace) nsp).addType(scoped);
                if (scoped instanceof TemplateParameterType) {
                    ((GenericClass) scope).addTemplateParameters((TemplateParameterType) scoped);
                }
            }
        }
        lo.setLazyFuncsScopesStack(lazyFuncsScopesStack);
        lazyTypeScopesStack.clear();
    }

    private Type addDecorator() {
        TypeDecorator td = null;
        Type decoratedType;
        if (decoratedTypeID > 0)
            decoratedType = Loader.getInstance().getType(new Integer(decoratedTypeID));
        else
            decoratedType = Class.getUnknownClass();

        if (decoratedType == null) return null;

        if (kind.indexOf("ptr") >= 0)
            td = new PointerDecorator(decoratedType);
        if (kind.indexOf("array") >= 0)
            td = new ArrayDecorator(decoratedType);
        if (kind.indexOf("ref") >= 0)
            td = new ReferenceDecorator(decoratedType);
        if (kind.indexOf("typedef") >= 0) {
            td = new TypedefDecorator(decoratedType, name);
            setScopeAndLocation((ExplicitlyDefinedType) td);
        } else {
            decoratedType.getScope().addScopedElement(td);
        }
        return td;
    }

    private Type addPrimitiveType() {
        Primitive pr = new Primitive(name);
        pr.setStatute(Statute.LIBRARY);
        Namespace ansp = Namespace.getGlobalNamespace();
        ansp.addType(pr);
        return pr;
    }

    private Type addFunctionPointer() {
        FunctionPointer fp = new FunctionPointer(name);
        fp.setStatute(Statute.NORMAL);
        Namespace ansp = Namespace.getAnonymousNamespace();
        fp.setScope(Namespace.getAnonymousNamespace());
        ansp.addType(fp);
        return fp;
    }

    private TemplateParameterType addTemplateParType() {
        TemplateParameterType tpt = new TemplateParameterType(name);
        setScopeAndLocation(tpt);
        return tpt;
    }

    private DataAbstraction addClassType() {
        Class cls;
        if (isGeneric)
            cls = new GenericClass(name);
        else
            cls = new Class(name);
        setScopeAndLocation(cls);
        if (isAbstract)
            cls.setAbstract();
        if (isInterface)
            cls.setAbstract();
        return cls;
    }

    private void setScopeAndLocation(ExplicitlyDefinedType st) {
        st.setLocation(location);
        st.setPackage(currentPackage);
        currentPackage.addType(st);
        st.setScope(currentNamespace);
        currentNamespace.addType(st);
    }

    private Union addUnionType() {
        Union ut = new Union(name);
        setScopeAndLocation(ut);
        ut.setStatute(Statute.NORMAL);
        return ut;
    }
}
