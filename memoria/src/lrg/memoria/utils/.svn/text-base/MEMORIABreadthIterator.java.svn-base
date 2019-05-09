package lrg.memoria.utils;

import lrg.memoria.core.*;
import lrg.memoria.core.Class;
import lrg.memoria.core.Package;
import lrg.memoria.core.System;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MEMORIABreadthIterator implements Iterator {
    private System currentSystem;
    private Iterator packagesIterator;
//    private Iterator namespacesIterator;
    private Iterator typesIterator;
    private Iterator attributesIterator;
    private Iterator globalFunctionsIterator, methodsIterator;
    private Iterator initializerBodiesIterator, bodiesIterator;
    private Iterator parametersIterator;
    private Iterator globalVariablesIterator, localVariablesIterator;
    private Iterator accessesIterator;
    private Iterator callsIterator;
    private Iterator annotationIterator, annotationInstanceIterator;

    private ModelElementList typesList;
    private ModelElementList globalFunctionsList, methodsList, attributesList, initializerBodiesList, bodiesList;
    private ModelElementList parametersList, globalVariablesList, localVariablesList;
    private ModelElementList accessesList, callsList;
    private ModelElementList annotationList, annotationInstanceList;

    
    public MEMORIABreadthIterator(System s) {
        currentSystem = s;
        packagesIterator = currentSystem.getPackages().iterator();
//        namespacesIterator = currentSystem.getNamespaces().iterator();
        typesList = new ModelElementList();
        typesIterator = typesList.iterator();
        methodsList = new ModelElementList();
        methodsIterator = methodsList.iterator();
        globalFunctionsList = new ModelElementList();
        globalFunctionsIterator = globalFunctionsList.iterator();
        bodiesList = new ModelElementList();
        bodiesIterator = bodiesList.iterator();
        attributesList = new ModelElementList();
        attributesIterator = attributesList.iterator();
        initializerBodiesList = new ModelElementList();
        initializerBodiesIterator = initializerBodiesList.iterator();
        parametersList = new ModelElementList();
        parametersIterator = parametersList.iterator();
        localVariablesList = new ModelElementList();
        localVariablesIterator = localVariablesList.iterator();
        globalVariablesList = new ModelElementList();
        globalVariablesIterator = globalVariablesList.iterator();
        accessesList = new ModelElementList();
        accessesIterator = accessesList.iterator();
        callsList = new ModelElementList();
        callsIterator = callsList.iterator();
        
        annotationList = new ModelElementList();
        annotationIterator = annotationList.iterator();
        annotationInstanceList = new ModelElementList();
        annotationInstanceIterator = annotationInstanceList.iterator();
    }

    public boolean hasNext() {
        return packagesIterator.hasNext()  ||
//        		namespacesIterator.hasNext() ||
                typesIterator.hasNext() || globalVariablesIterator.hasNext() ||
                globalFunctionsIterator.hasNext() || initializerBodiesIterator.hasNext() ||
                attributesIterator.hasNext() || methodsIterator.hasNext() ||
                parametersIterator.hasNext() || bodiesIterator.hasNext() || localVariablesIterator.hasNext() ||
                accessesIterator.hasNext() || callsIterator.hasNext() ||
                annotationIterator.hasNext() || annotationInstanceIterator.hasNext();
    }

    public ModelElement next() {
        if (packagesIterator.hasNext())
            return nextPackage();
//        if (namespacesIterator.hasNext())
//            return nextNamespace();
        if (annotationIterator.hasNext())
            return nextAnnotation();
        if (typesIterator.hasNext())
            return nextType();
        
        if (globalVariablesIterator.hasNext())
            return nextGlobalVariable();
        if (globalFunctionsIterator.hasNext())
            return nextGlobalFunction();
        if (initializerBodiesIterator.hasNext())
            return nextInitializerBody();
        if (attributesIterator.hasNext())
            return nextAttribute();
        if (methodsIterator.hasNext())
            return nextMethod();
        if (parametersIterator.hasNext())
            return nextParameter();
        if (bodiesIterator.hasNext())
            return nextBody();
        if (localVariablesIterator.hasNext())
            return nextLocalVar();

        if (annotationInstanceIterator.hasNext())
            return nextAnnotationInstance();
        
        if (accessesIterator.hasNext())
            return nextAccess();
        if (callsIterator.hasNext())
            return nextCall();
        throw new NoSuchElementException();
    }

    private ModelElement nextAnnotationInstance() {
		return (ModelElement) annotationInstanceIterator.next();
    }

	private ModelElement nextAnnotation() {
		return (ModelElement) annotationIterator.next();
	}

	public void remove() {
        throw new UnsupportedOperationException();
    }

    private Package nextPackage() {
        Package pack = (Package) packagesIterator.next();
        typesList.addAll(pack.getAllTypesList());
        globalFunctionsList.addAll(pack.getGlobalFunctionsList());
        globalVariablesList.addAll(pack.getGlobalVariablesList());        
        annotationList.addAll(pack.getAnnotationsList());
        if (!packagesIterator.hasNext()) {
            typesList.addAll(Namespace.getGlobalNamespace().getTypesList());
            typesIterator = typesList.iterator();            
            annotationIterator = annotationList.iterator();
            globalVariablesIterator = globalVariablesList.iterator();
            globalFunctionsIterator = globalFunctionsList.iterator();
        }
        return pack;
    }

/*    
    private Namespace nextNamespace() {
        Namespace nsp = (Namespace) namespacesIterator.next();
        addImplicitlyDefinedTypes(nsp);
        globalFunctionsList.addAll(nsp.getGlobalFunctionsList());
        globalVariablesList.addAll(nsp.getGlobalVariablesList());
        if (!namespacesIterator.hasNext()) {
            typesIterator = typesList.iterator();
            globalVariablesIterator = globalVariablesList.iterator();
            globalFunctionsIterator = globalFunctionsList.iterator();
        }
        return nsp;
    }

    private void addImplicitlyDefinedTypes(Scope scope) {
        ModelElementList elementsInScope = scope.getScopedElements();
        for (Object me : elementsInScope) {
            if (me instanceof TypeDecorator || me instanceof TemplateInstance)
                typesList.add(me);
            if (me instanceof Scope)
                addImplicitlyDefinedTypes((Scope) me);
        }
    }
*/
    private ModelElement nextType() {
        Type currentType = (Type) typesIterator.next();
        if (currentType instanceof DataAbstraction) {
            DataAbstraction currentDataAbstraction = (DataAbstraction) currentType;
            methodsList.addAll(currentDataAbstraction.getMethodList());
            attributesList.addAll(currentDataAbstraction.getAttributeList());
            annotationInstanceList.addAll(currentDataAbstraction.getAnnotations());
            if (currentDataAbstraction instanceof Class)
                initializerBodiesList.addAll(((Class) currentDataAbstraction).getInitializerList());
        }
        if (currentType instanceof FunctionPointer)
            methodsList.add(((FunctionPointer) currentType).getFunctionSide());
        if (!typesIterator.hasNext()) {
            attributesIterator = attributesList.iterator();
            methodsIterator = methodsList.iterator();
            initializerBodiesIterator = initializerBodiesList.iterator();
            annotationInstanceIterator = annotationInstanceList.iterator();
        }
        return (ModelElement) currentType;
    }

    private GlobalVariable nextGlobalVariable() {
        return (GlobalVariable) globalVariablesIterator.next();
    }

    private GlobalFunction nextGlobalFunction() {
        GlobalFunction gf = (GlobalFunction) globalFunctionsIterator.next();
        parametersList.addAll(gf.getParameterList());
        Body body = gf.getBody();
        if (body != null)
            bodiesList.add(body);
        return gf;
    }

    private InitializerBody nextInitializerBody() {
        InitializerBody ib = (InitializerBody) initializerBodiesIterator.next();
        localVariablesList.addAll(ib.getLocalVarList());
        accessesList.addAll(ib.getAccessList());
        callsList.addAll(ib.getCallList());
        return ib;
    }

    private Attribute nextAttribute() {
        return (Attribute) attributesIterator.next();
    }

    private Function nextMethod() {
        Function method = (Function) methodsIterator.next();
        parametersList.addAll(method.getParameterList());
        annotationInstanceList.addAll(method.getAnnotations());
        Body body = method.getBody();
        if (body != null)
            bodiesList.add(body);
        if (!methodsIterator.hasNext()) {
            parametersIterator = parametersList.iterator();
            bodiesIterator = bodiesList.iterator();
            annotationInstanceIterator = annotationInstanceList.iterator();
        }
        return method;
    }

    private Parameter nextParameter() {
        return (Parameter) parametersIterator.next();
    }

    private Body nextBody() {
        Body body = (Body) bodiesIterator.next();
        localVariablesList.addAll(body.getLocalVarList());
        accessesList.addAll(body.getAccessList());
        callsList.addAll(body.getCallList());
        if (!bodiesIterator.hasNext()) {
            localVariablesIterator = localVariablesList.iterator();
            accessesIterator = accessesList.iterator();
            callsIterator = callsList.iterator();
        }
        return body;
    }

    private LocalVariable nextLocalVar() {
        LocalVariable lv = (LocalVariable) localVariablesIterator.next();
        return lv;
    }

    private Access nextAccess() {
        return (Access) accessesIterator.next();
    }

    private Call nextCall() {
        Call call = (Call) callsIterator.next();
        return call;
    }
}
