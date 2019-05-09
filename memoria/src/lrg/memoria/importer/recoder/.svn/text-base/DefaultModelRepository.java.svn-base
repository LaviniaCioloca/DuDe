package lrg.memoria.importer.recoder;

import lrg.memoria.core.*;
import lrg.memoria.core.AnnotationProperty;
import lrg.memoria.core.Class;
import lrg.memoria.core.Package;
import lrg.memoria.core.factories.BodyFactory;
import recoder.abstraction.*;
import recoder.abstraction.Method;
import recoder.abstraction.Type;
import recoder.bytecode.ClassFile;
import recoder.bytecode.ConstructorInfo;
import recoder.bytecode.FieldInfo;
import recoder.bytecode.MethodInfo;
import recoder.java.declaration.AnnotationDeclaration;
import recoder.java.declaration.AnnotationUseSpecification;
import recoder.java.reference.*;
import recoder.parser.TokenMgrError;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.lang.System;

/**
 * This implementation uses the associationCoupling between the full getName of the lrg.insider.lrg.insider.core.MMModel element
 * and the element itself.
 */
public class DefaultModelRepository implements ModelRepository {

    //the unique instance of the class
    private static DefaultModelRepository dmr;

    private HashMap fileMap;
    private HashMap classMap;
    private HashMap arraysMap;
    private HashMap packageMap;
    private HashMap namespacesMap;
    private HashMap methodMap;
    private HashMap primitiveMap;
    private HashMap variableMap;
    private HashMap accessMap;
    private HashMap callMap;
    
    private HashMap annotationMap;
    private lrg.memoria.core.Annotation currentAnnotation;
    private lrg.memoria.core.AnnotationInstance currentAnnotationInstance;
    
    private lrg.memoria.core.File currentFile;
    private lrg.memoria.core.System system;
    private lrg.memoria.core.Class currentMMMClass;
    private lrg.memoria.core.Method currentMMMMethod;
    private lrg.memoria.core.Package currentMMMPackage;
    private lrg.memoria.core.Body currentMMMBody;
    private lrg.memoria.core.CodeStripe currentMMMCodeStripe;

    protected DefaultModelRepository(String systemName) {
        Integer id = ModelElementsRepository.addNewModelElementsRepository();
        system = new lrg.memoria.core.System(systemName);
        system.setSystemId(id);
        fileMap = new HashMap();
        classMap = new HashMap();
        arraysMap = new HashMap();
        packageMap = new HashMap();
        packageMap.put(lrg.memoria.core.Package.ANONYMOUS_PACKAGE_NAME, lrg.memoria.core.Package.getAnonymousPackage());
        packageMap.put(lrg.memoria.core.Package.UNKNOWN_PACKAGE_NAME, lrg.memoria.core.Package.getUnknownPackage());
        namespacesMap = new HashMap();
        methodMap = new HashMap();
        primitiveMap = new HashMap();
        variableMap = new HashMap();
        accessMap = new HashMap();
        callMap = new HashMap();
        annotationMap = new HashMap();
    }

    public static DefaultModelRepository getModelRepository(String repositoryName) {
        if (dmr == null) {
            BodyFactory.cleanUp();
            dmr = new DefaultModelRepository(repositoryName);
        }
        return dmr;
    }

    static void cleanUp() {
        if (dmr != null) {
            dmr.fileMap.clear();
            dmr.classMap.clear();
            dmr.arraysMap.clear();
            dmr.packageMap.clear();
            dmr.namespacesMap.clear();
            dmr.methodMap.clear();
            dmr.primitiveMap.clear();
            dmr.variableMap.clear();
            dmr.accessMap.clear();
            dmr.callMap.clear();
            dmr.annotationMap.clear();
        }
        dmr = null;
        BodyFactory.cleanUp();
        java.lang.System.gc();
    }


    public lrg.memoria.core.Class addClass(Object key, String className) {
        if (key instanceof TypeReference)
            return addClassFromTypeReference((TypeReference) key, className);
        else
            return addClassFromClassType((ClassType) key, className);
    }

    private lrg.memoria.core.Class addClassFromClassType(ClassType clst, String className) {
        // UTypeReference.getUTypeReferenceFor(clst);
        lrg.memoria.core.Class mmmc = (lrg.memoria.core.Class) classMap.get(clst);
        if (mmmc == null) {
            mmmc = new lrg.memoria.core.Class(className);
            classMap.put(clst, mmmc);
            lrg.memoria.core.Package pack;
            recoder.abstraction.Package tempPack = clst.getPackage();
            if (tempPack != null) {
                String packName;
                packName = tempPack.getFullName();
                if (packName.equals("")) {
                    packName = lrg.memoria.core.Package.ANONYMOUS_PACKAGE_NAME;
                }
                pack = addPackage(tempPack, packName);
                pack.addType(mmmc);
                mmmc.setPackage(pack);
                //if (clst.getContainingClassType() == null) {
                lrg.memoria.core.Namespace nsp = convertPackageToNamespace(pack);
                nsp.addType(mmmc);
                mmmc.setScope(nsp);
                //}
                if (clst instanceof ClassFile) {
                    ClassFile ct = (ClassFile) clst;
                    mmmc.setStatute(lrg.memoria.core.Statute.LIBRARY);
                    if (ct.isAbstract())
                        mmmc.setAbstract();
                    if (ct.isFinal())
                        mmmc.setFinal();
                    if (ct.isStatic())
                        mmmc.setStatic();
                    if (ct.isInterface())
                        mmmc.setInterface();
                    mmmc.setAccessMode(RecoderToMemojConverter.convertAccessMode(ct));
                    List<ClassType> ctl = ct.getSupertypes();
                    for (int i = 0; i < ctl.size(); i++) {
                        ClassType clsType = ctl.get(i);
                        DataAbstraction mcls = ReferenceConverter.getMemoriaClass(clsType);
                        if (clsType.isInterface())
                            mmmc.addInterface(mcls);
                        else
                            mmmc.addAncestor(mcls);
                        mcls.addDescendant(mmmc);
                    }
                }
            }
        }
        return mmmc;
    }

    /**
     * This is called in the case of Failed Dependencies.
     */
    private lrg.memoria.core.Class addClassFromTypeReference(TypeReference tr, String className) {
        PackageReference pr = tr.getPackageReference();
        lrg.memoria.core.Class mmmc = null;
        String packFullName = "";
        if (pr != null) {
            do {
                packFullName = pr.getName() + "." + packFullName;
                pr = (PackageReference) pr.getReferencePrefix();
            } while (pr != null);
            packFullName = packFullName.substring(0, packFullName.length() - 1);
            mmmc = (lrg.memoria.core.Class) classMap.get(packFullName + "." + className);
            if (mmmc == null) {
                mmmc = (lrg.memoria.core.Class) classMap.get(className);
                if (mmmc == null) {
                    mmmc = new lrg.memoria.core.Class(className);
                    classMap.put(className, mmmc);
                }
                setPackageForClassObtainedFromTypeReference(mmmc, packFullName);
                classMap.put(packFullName + "." + className, mmmc);
            }
        } else { // the package reference is null
            mmmc = (lrg.memoria.core.Class) classMap.get(className);
            if (mmmc == null) {
                mmmc = new lrg.memoria.core.Class(className);
                classMap.put(className, mmmc);
                setPackageForClassObtainedFromTypeReference(mmmc, "");
            }
        }
        return mmmc;
    }

    private void setPackageForClassObtainedFromTypeReference(DataAbstraction mmmc, String packFullName) {
        try {
            lrg.memoria.core.Package pack;
            if (!packFullName.equals(""))
                pack = addPackage(null, packFullName);
            else
                pack = lrg.memoria.core.Package.getUnknownPackage();
            lrg.memoria.core.Namespace nsp = convertPackageToNamespace(pack);
            pack.addType(mmmc);
            nsp.addType(mmmc);
            mmmc.setPackage(pack);
            mmmc.setScope(nsp);
        } catch (IndexOutOfBoundsException ex) {
            system.addFailedDepElement(mmmc);
        }
    }


    /**
     * The key is the fully quallified getName of the class.
     */
    public lrg.memoria.core.Class getClass(Object key) {
        return (lrg.memoria.core.Class) classMap.get(key);
    }

    /**
     * We considered that the key is a String representing the getName of the package.
     */
    public lrg.memoria.core.Package addPackage(Object key, String packageName) {
        lrg.memoria.core.Package pack;
        if (key == null) {
            //failed dep
            pack = (lrg.memoria.core.Package) packageMap.get(packageName);
            if (pack == null) {
                pack = new lrg.memoria.core.Package(packageName);
                packageMap.put(packageName, pack);
                pack.setSystem(system);
                system.addPackage(pack);
            }
        } else {
            pack = (lrg.memoria.core.Package) packageMap.get(packageName);
            if (pack == null) {
                pack = new lrg.memoria.core.Package(packageName);
                if (pack.getStatute() == Statute.FAILEDDEP)
                    pack.setStatute(lrg.memoria.core.Statute.LIBRARY);
                packageMap.put(packageName, pack);
                system.addPackage(pack);
                pack.setSystem(system);
            }
        }
        return pack;
    }

    /**
     * We considered that the key is a String representing the getName of the package.
     */
    public lrg.memoria.core.Package getPackage(Object key) {
        return (lrg.memoria.core.Package) packageMap.get(key);
    }

    /**
     * As a key we expect a MethodDeclarationListener object.
     */
    public lrg.memoria.core.Method addMethod(Object key, String methodName) {
        Object element = methodMap.get(key);
        if (element == null) {
            element = new lrg.memoria.core.Method(methodName);
            methodMap.put(key, element);
            lrg.memoria.core.Method met = (lrg.memoria.core.Method) element;
            if ((key instanceof MethodInfo) || (key instanceof DefaultConstructor)) {
                DataAbstraction cls = ReferenceConverter.getMemoriaClass(((Method) key).getContainingClassType());
                met.setScope(cls);
                cls.addMethod(met);
                met.setStatute(lrg.memoria.core.Statute.LIBRARY);

                List<Type> signature = ((Method) key).getSignature();
                Type currentType;
                for (int i = 0; i < signature.size(); i++) {
                    currentType = signature.get(i);
                    lrg.memoria.core.Type currentParamType;
                    currentParamType = ReferenceConverter.getType(currentType);
                    met.addParameter(new Parameter("par" + i + 1, currentParamType, met));
                }

                met.setAccessMode(RecoderToMemojConverter.convertAccessMode((Method) key));
                if (key instanceof DefaultConstructor) {
                    met.setConstructor();
                    met.setFunctionBody((FunctionBody) BodyFactory.getInstance().produceBody(met));
                } else if (!(key instanceof ConstructorInfo)) {
                    MethodInfo metInf = (MethodInfo) key;
                    met.setReturnType(ReferenceConverter.getType(metInf.getReturnType()));
                    if (metInf.isStatic())
                        met.setStatic();
                    if (metInf.isFinal())
                        met.setFinal();
                    if (metInf.isAbstract())
                        met.setAbstract();
                }
            } else if (key instanceof MethodReference || null==((ProgramModelElement)key).getProgramModelInfo() ) {
                system.addFailedDepElement(met);
                Class.getUnknownClass().addMethod(met);
                met.setScope(Class.getUnknownClass());
                met.setReturnType(Class.getUnknownClass());
                //met.setFunctionBody(Body.getUnkonwnBody());
            }
        }
        return (lrg.memoria.core.Method) element;
    }

    public ArrayDecorator getArrayType(lrg.memoria.core.Type type, int dim) {
        String fullName = type.getFullName();
        ArrayDecorator decorator = null;
        lrg.memoria.core.Type decorated = type;
        int i;
        for (i = 0; i < dim; i++) {
            fullName += "[]";
            decorator = (lrg.memoria.core.ArrayDecorator) arraysMap.get(fullName);
            if (decorator == null) {
                decorator = new ArrayDecorator(decorated);
                arraysMap.put(fullName, decorator);
                decorated.getScope().addScopedElement(decorator);
            }
            decorated = decorator;
        }
        return decorator;
    }


    /**
     * As a key we expect a MethodDeclarationListener object.
     */
    public lrg.memoria.core.Function getMethod(Object key) {
        return (lrg.memoria.core.Function) methodMap.get(key);
    }

    public lrg.memoria.core.Primitive addPrimitiveType(Object key, String typeName) {
        Object element = primitiveMap.get(key);
        if (element == null) {
            element = new lrg.memoria.core.Primitive(typeName);
            Namespace.getGlobalNamespace().addType((lrg.memoria.core.Type) element);
            primitiveMap.put(key, element);
        }
        return (lrg.memoria.core.Primitive) element;
    }

    public lrg.memoria.core.Primitive getPrimitiveType(Object key) {
        return (lrg.memoria.core.Primitive) primitiveMap.get(key);
    }

    public lrg.memoria.core.LocalVariable addLocalVar(Object key, String varName, CodeStripe scope) {
        Object element = variableMap.get(key);
        if (element == null) {
            if (scope == null)
                throw new IllegalArgumentException("You must specify a valid scope for a LocalVariable!");
            if (key instanceof VariableReference || null==((ProgramModelElement)key).getProgramModelInfo() ) {
                element = new lrg.memoria.core.LocalVariable(varName, scope);
                system.addFailedDepElement((lrg.memoria.core.LocalVariable) element);
            } else
                element = new lrg.memoria.core.LocalVariable(varName, scope);
            variableMap.put(key, element);
        }
        return (lrg.memoria.core.LocalVariable) element;
    }

    public lrg.memoria.core.LocalVariable getLocalVar(Object key) {
        return (lrg.memoria.core.LocalVariable) variableMap.get(key);
    }

    public lrg.memoria.core.Parameter addParameter(Object key, String varName) {
        Object element = variableMap.get(key);
        if (element == null) {
            element = new lrg.memoria.core.Parameter(varName);
            variableMap.put(key, element);
        }
        return (lrg.memoria.core.Parameter) element;
    }

    public lrg.memoria.core.Parameter getParameter(Object key) {
        return (lrg.memoria.core.Parameter) variableMap.get(key);
    }

    public lrg.memoria.core.Attribute addAttribute(Object key, String varName) {
        Object element = variableMap.get(key);
        if (element == null) {
            element = new lrg.memoria.core.Attribute(varName);
            variableMap.put(key, element);
            lrg.memoria.core.Attribute attr = (lrg.memoria.core.Attribute) element;
            if (key instanceof FieldInfo) {
                FieldInfo fld = (FieldInfo) key;
                DataAbstraction cls = ReferenceConverter.getMemoriaClass(fld.getContainingClassType());
                attr.setScope(cls);
                cls.addAttribute(attr);
                attr.setType(ReferenceConverter.getType(fld.getType()));

                if (fld.getType() instanceof ArrayType) {
                    attr.setType(getArrayType(attr.getType(), ReferenceConverter.getArrayDimension(fld.getType())));
                }
                attr.setStatute(lrg.memoria.core.Statute.LIBRARY);
                attr.setAccessMode(RecoderToMemojConverter.convertAccessMode(fld));
                if (fld.isStatic())
                    attr.setStatic();
                if (fld.isFinal())
                    attr.setFinal();
            } else if (key instanceof FieldReference || null==((ProgramModelElement)key).getProgramModelInfo() ) {
                system.addFailedDepElement(attr);
                attr.setScope(Class.getUnknownClass());
                Class.getUnknownClass().addAttribute(attr);
                attr.setType(Class.getUnknownClass());
                //attr.setAccessMode(RecoderToMemojConverter.convertAccessMode(fld));

            }
        }
        return (lrg.memoria.core.Attribute) element;
    }

    public lrg.memoria.core.Attribute getAttribute(Object key) {
        return (lrg.memoria.core.Attribute) variableMap.get(key);
    }

    /**
     * @deprecated
     */
    public lrg.memoria.core.Access addAccess(Object key, lrg.memoria.core.Variable var, lrg.memoria.core.Body body) {
        Object element = accessMap.get(key);
        if (element == null) {
            element = new lrg.memoria.core.Access(var, body);
            accessMap.put(key, element);
            body.addAccess((lrg.memoria.core.Access) element);
            var.addAccess((lrg.memoria.core.Access) element);
        }
        return (lrg.memoria.core.Access) element;
    }

    public lrg.memoria.core.Access addAccess(Object key, lrg.memoria.core.Variable var, lrg.memoria.core.CodeStripe stripe) {
        Object element = accessMap.get(key);
        if (element == null) {
            element = new lrg.memoria.core.Access(var, stripe);
            accessMap.put(key, element);
            stripe.addAccess((lrg.memoria.core.Access) element);
            var.addAccess((lrg.memoria.core.Access) element);
        }
        return (lrg.memoria.core.Access) element;
    }

    public lrg.memoria.core.Access getAccess(Object key) {
        return (lrg.memoria.core.Access) accessMap.get(key);
    }

    /**
     * @deprecated
     */
    public lrg.memoria.core.Call addCall(Object key, lrg.memoria.core.Method met, lrg.memoria.core.Body body) {
        Object element = callMap.get(key);
        if (element == null) {
            element = new lrg.memoria.core.Call(met, body);
            callMap.put(key, element);
            body.addCall((lrg.memoria.core.Call) element);
            met.addCall((lrg.memoria.core.Call) element);
        }
        return (lrg.memoria.core.Call) element;
    }

    public lrg.memoria.core.Call addCall(Object key, lrg.memoria.core.Method met, lrg.memoria.core.CodeStripe stripe) {
        Object element = callMap.get(key);
        if (element == null) {
            element = new lrg.memoria.core.Call(met, stripe);
            callMap.put(key, element);
            stripe.addCall((lrg.memoria.core.Call) element);
            met.addCall((lrg.memoria.core.Call) element);
        }
        return (lrg.memoria.core.Call) element;
    }

    public lrg.memoria.core.Call getCall(Object key) {
        return (lrg.memoria.core.Call) callMap.get(key);
    }

    public lrg.memoria.core.Class getCurrentClass() {
        return currentMMMClass;
    }

    public void setCurrentClass(lrg.memoria.core.Class mmmc) {
        currentMMMClass = mmmc;
    }

    public lrg.memoria.core.Method getCurrentMethod() {
        return currentMMMMethod;
    }

    public void setCurrentMethod(lrg.memoria.core.Method mmmm) {
        currentMMMMethod = mmmm;
    }

    public lrg.memoria.core.Package getCurrentPackage() {
        return currentMMMPackage;
    }

    public void setCurrentPackage(lrg.memoria.core.Package mmmp) {
        currentMMMPackage = mmmp;
    }

    public lrg.memoria.core.Body getCurrentBody() {
        return currentMMMBody;
    }

    public void setCurrentBody(lrg.memoria.core.Body body) {
        // accessMap.clear();
        // callMap.clear();
        currentMMMBody = body;
    }

    public lrg.memoria.core.CodeStripe getCurrentStripe() {
        return currentMMMCodeStripe;
    }

    public void setCurrentStripe(lrg.memoria.core.CodeStripe mmmcs) {
        accessMap.clear();
        callMap.clear();
        currentMMMCodeStripe = mmmcs;
        if (mmmcs != null) {
            for (Call c : mmmcs.getCallList()) callMap.put(c.getFunction(), c);
            for (Access a : mmmcs.getAccessList()) accessMap.put(a.getVariable(), a);
        }
    }

    public Namespace convertPackageToNamespace(lrg.memoria.core.Package pack) {
        Namespace nsp = (Namespace) namespacesMap.get(pack);
        if (nsp == null) {
            if (pack == Package.getUnknownPackage()) {
                nsp = Namespace.getUnknownNamespace();
            } else {
                nsp = new Namespace(pack.getName(), pack.getStatute());
                nsp.setSystem(system);
                system.addNamespace(nsp);
            }
            namespacesMap.put(pack, nsp);

        }
        return nsp;
    }

    public lrg.memoria.core.System getSystem() {
        return system;
    }

    public String toString() {
        String myStr = "";
        Collection values = packageMap.values();
        lrg.memoria.core.Package actPack;
        Iterator valIterator = values.iterator();
        if (valIterator != null) {
            do {
                actPack = (lrg.memoria.core.Package) valIterator.next();
                myStr += actPack.toString();
            } while (valIterator.hasNext());
        }
        return myStr;
    }

    /* (non-Javadoc)
     * @see lrg.memoria.importer.recoder.ModelRepository#addFile(java.lang.Object, java.lang.String)
     */
    public File addFile(Object key, String name) {
        lrg.memoria.core.File element = (lrg.memoria.core.File) fileMap.get(key);
        if (element == null) {
            java.io.File temp = new java.io.File(name);
            if (temp.getParent() == null)
                element = new File("", temp.getName());
            else
                element = new File(temp.getParent(), temp.getName());
            //system.addFile(element);
            fileMap.put(key, element);
        }
        return element;
    }

    /* (non-Javadoc)
     * @see lrg.memoria.importer.recoder.ModelRepository#getFile(java.lang.Object)
     */
    public File getFile(Object key) {
        return (lrg.memoria.core.File) fileMap.get(key);
    }

    /* (non-Javadoc)
     * @see lrg.memoria.importer.recoder.ModelRepository#getCurrentFile()
     */
    public File getCurrentFile() {
        return currentFile;
    }

    /* (non-Javadoc)
     * @see lrg.memoria.importer.recoder.ModelRepository#setCurrentFile(lrg.memoria.lrg.insider.lrg.insider.core.File)
     */
    public void setCurrentFile(File file) {
        currentFile = file;
    }

    /**
     * @return the annotation that is parsed
     */    
	public lrg.memoria.core.Annotation getCurrentAnnotation() {
		return currentAnnotation;
	}

	/**
     * sets the current annotation
     * @param currentAnnotation
     */
	public void setCurrentAnnotation(lrg.memoria.core.Annotation currentAnnotation) {
		this.currentAnnotation = currentAnnotation;
	}
	
	/**
	 * Retrieves an lrg.memoria.core.Annotation from the repository.
     * If the key doesn't have a corresponding annotation then 
     * the null pointer will be returned.
	 * @param key - the full name of the annotation
	 */
	public lrg.memoria.core.Annotation getAnnotation(String key){
		return (lrg.memoria.core.Annotation) annotationMap.get(key);
	}
	
	/**
	 * adds a new annotation to the repository annotations map
	 * @param anot - the AnnotationDeclaration or AnnotationUseSpecification 
	 * @param annotationFullName
	 * @return if the annotation already exists, a reference to it 
	 * will be returned; otherwise, a new annotation will be added 
	 * to the annotations map, and the reference will be returned
	 */
	public lrg.memoria.core.Annotation addAnnotation(Object anot, String annotationFullName){
		String annotationName;
		if(annotationFullName.contains("."))
			annotationName = annotationFullName.substring(annotationFullName.lastIndexOf('.')+1);
		else
			annotationName = annotationFullName;
		lrg.memoria.core.Annotation mmma = (lrg.memoria.core.Annotation)annotationMap.get(annotationFullName);
		if(mmma == null) 
		{
			mmma = new lrg.memoria.core.Annotation(annotationName);
			annotationMap.put(annotationFullName, mmma);
			if (anot instanceof AnnotationUseSpecification)
			{
				AnnotationUseSpecification aus = (AnnotationUseSpecification)anot;
				//if an annotation is used before its declaration is parsed
				//we have two possibilities: 1. either the annotation is user-defined
				//and then we add it to the repository from its declaration right now,
				//and later, when the file with its actual declaration is parsed, we 
				//only update its location (field that we can't find in advance); 
				//2. either the annotation is NOT user-defined [it might be a
				//system annotation (like for ex. Override) or a system meta-annotation 
				//(like for ex. Target) or it might be an annotation DEFINED IN A JAR]; 
				//in this case, we add the annotation from its class file
				TypeReference tr = aus.getTypeReference();
				Type ct = null;
				try {
					ct = ReferenceConverter.getSrcInfo().getType(tr);
				} catch (Exception e) { //added for Failed Dependencies
					System.out.println(e);
					e.printStackTrace();
					System.out.println("Failed Dependency Error; Exception occured while getting the type\"" + tr.getName() + "\" from TypeReference");
				} catch (TokenMgrError e) {
					java.lang.System.out.println(e);
					e.printStackTrace();
				}
				if (ct != null)
				{
					ClassType clst = (ClassType) ct;
					lrg.memoria.core.Package pack;
					recoder.abstraction.Package tempPack = clst.getPackage();
					if (tempPack != null) {
						String packName;
						packName = tempPack.getFullName();
						if (packName.equals("")) {
							packName = lrg.memoria.core.Package.ANONYMOUS_PACKAGE_NAME;
						}
						pack = addPackage(tempPack, packName);
						pack.addAnnotation(mmma);
						mmma.setPackage(pack);
						lrg.memoria.core.Namespace nsp = convertPackageToNamespace(pack);
						mmma.setScope(nsp);
						//this is the case of an annotation defined in a .class file
						//(for ex. a system annotation or one defined in a JAR)
						if (clst instanceof ClassFile) {
							ClassFile cf = (ClassFile) clst;
							mmma.setStatute(lrg.memoria.core.Statute.LIBRARY);
							mmma.getAnnotationProperties().clear();
							for (recoder.abstraction.Method f : cf.getMethods()) 
							{
								AnnotationProperty ap = new AnnotationProperty(f.getName());
								ap.setScope(mmma);
								ap.setStatute(Statute.LIBRARY);
								ap.setType(ReferenceConverter.getType(f.getReturnType()));				
								mmma.addAnnotationProperty(ap);
							}
						}
						//this is the case when we use an user-defined annotation before the 
						//file where it is defined is parsed (so we add now all the information
						//available - everything except its location);
						//OBS: - we added it's scope and package above
						//     - the location will be updated later, when the file with the
						//       actual declaration will be parsed
						else if (clst instanceof AnnotationDeclaration) {
							addAnnotationDetailsFromItsDeclaration(clst, mmma);
						}		
					}
				}
			}
			//this is the case when we add an annotation from its declaration
			//(when we actually parse the file with its declaration)
			else if (anot instanceof AnnotationDeclaration)
			{
				lrg.memoria.core.Package pack;
				recoder.abstraction.Package tempPack = ((AnnotationDeclaration)anot).getPackage();
				if (tempPack != null) {
					String packName;
					packName = tempPack.getFullName();
					if (packName.equals("")) {
						packName = lrg.memoria.core.Package.ANONYMOUS_PACKAGE_NAME;
					}
					pack = addPackage(tempPack, packName);
					pack.addAnnotation(mmma);
					mmma.setPackage(pack);
					lrg.memoria.core.Namespace nsp = convertPackageToNamespace(pack);
					mmma.setScope(nsp);
				}
				addAnnotationDetailsFromItsDeclaration(anot, mmma);
			}
		}
		return mmma;		
	}

	private void addAnnotationDetailsFromItsDeclaration(Object anot, lrg.memoria.core.Annotation mmma) 
	{
		AnnotationDeclaration ad = (AnnotationDeclaration)anot;
		mmma.setStatute(Statute.NORMAL);
		mmma.getAnnotationProperties().clear();
		for (recoder.abstraction.Method f : ad.getMethods()) {
			AnnotationProperty ap = new AnnotationProperty(f.getName());
			ap.setScope(mmma);
			ap.setStatute(Statute.NORMAL);
			ap.setType(ReferenceConverter.getType(f.getReturnType()));				
			mmma.addAnnotationProperty(ap);
		}
	}

	/**
     * @return the last annotation instance (meaning the last annotation
     * that was used in the source code to annotate something)
     */
	public AnnotationInstance getCurrentAnnotationInstance() {
		return currentAnnotationInstance;
	}

    /**
     * sets the current annotation instance (the annotation that is
     * currently used in code to annotate something)
     * @param a
     */
	public void setCurrentAnnotationInstance(AnnotationInstance a) {
		currentAnnotationInstance = a;
	}

}
