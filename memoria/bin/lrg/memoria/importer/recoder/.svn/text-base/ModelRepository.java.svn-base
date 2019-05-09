package lrg.memoria.importer.recoder;

import lrg.memoria.core.Type;
import lrg.memoria.core.Method;
import lrg.memoria.core.CodeStripe;

/**
 * Stores and retrieves the lrg.memoria.lrg.insider.lrg.insider.core components.
 */
public interface ModelRepository {

    /**
     * Adds a new lrg.memoria.lrg.insider.lrg.insider.core.Class with the given key and getName to the repository.
     * The method returns the new class.
     * If the class already exists the function will return its reference.
     */
    lrg.memoria.core.Class addClass(Object key, String className);

    /**
     * Retrieves a lrg.memoria.lrg.insider.lrg.insider.core.Class from the repository.
     * If the key doesn't have a corresponding class then the null pointer will be returned.
     */
    lrg.memoria.core.Class getClass(Object key);

    /**
     * Adds a new lrg.memoria.lrg.insider.lrg.insider.core.Package with the given key and getName to the repository.
     * The method returns the new package.
     * If the package already exists the function will return its reference.
     */
    lrg.memoria.core.Package addPackage(Object key, String packageName);

    /**
     * Retrieves a lrg.memoria.lrg.insider.lrg.insider.core.Package from the repository.
     * If the key doesn't have a corresponding package then the null pointer will be returned.
     */
    lrg.memoria.core.Package getPackage(Object key);

    /**
     * Adds a new lrg.memoria.lrg.insider.lrg.insider.core.Method with the given key and getName to the repository.
     * The method returns the new lrg.insider.lrg.insider.core.MMModel.Method.
     * If the 'method' already exists the function will return its reference.
     */
    lrg.memoria.core.Method addMethod(Object key, String methodName);

    /**
     * Retrieves a lrg.memoria.lrg.insider.lrg.insider.core.Method from the repository.
     * If the key doesn't have a corresponding 'method' then the null pointer will be returned.
     */
    lrg.memoria.core.Function getMethod(Object key);

    lrg.memoria.core.Primitive addPrimitiveType(Object key, String typeName);

    lrg.memoria.core.Primitive getPrimitiveType(Object key);

    lrg.memoria.core.ArrayDecorator getArrayType(Type type, int dim);

    lrg.memoria.core.LocalVariable addLocalVar(Object key, String varName, CodeStripe scope);

    lrg.memoria.core.LocalVariable getLocalVar(Object key);

    lrg.memoria.core.Parameter addParameter(Object key, String varName);

    lrg.memoria.core.Parameter getParameter(Object key);

    lrg.memoria.core.Attribute addAttribute(Object key, String varName);

    lrg.memoria.core.Attribute getAttribute(Object key);

    /**
     * @deprecated - use addAccess(..., CodeStripe)
     */
    lrg.memoria.core.Access addAccess(Object key, lrg.memoria.core.Variable var, lrg.memoria.core.Body body);
    
    lrg.memoria.core.Access addAccess(Object key, lrg.memoria.core.Variable var, lrg.memoria.core.CodeStripe stripe);

    lrg.memoria.core.Access getAccess(Object key);

    /**
     * @deprecated - use addCall(..., CodeStripe)
     */
    lrg.memoria.core.Call addCall(Object key, lrg.memoria.core.Method met, lrg.memoria.core.Body body);

    lrg.memoria.core.Call addCall(Object key, lrg.memoria.core.Method met, lrg.memoria.core.CodeStripe stripe);

    lrg.memoria.core.Call getCall(Object key);

    lrg.memoria.core.File addFile(Object key, String name);

    lrg.memoria.core.File getFile(Object key);

    lrg.memoria.core.File getCurrentFile();

    void setCurrentFile(lrg.memoria.core.File file);

    /**
     * Returns the class that is parsed.
     */
    lrg.memoria.core.Class getCurrentClass();

    /**
     * Sets the current class.
     */
    void setCurrentClass(lrg.memoria.core.Class mmmc);

    /**
     * Returns the method that is parsed.
     */
    lrg.memoria.core.Method getCurrentMethod();

    /**
     * Sets the current method.
     */
    void setCurrentMethod(lrg.memoria.core.Method mmmm);

    /**
     * Returns the package that is parsed.
     */
    lrg.memoria.core.Package getCurrentPackage();

    /**
     * Sets the current package.
     */
    void setCurrentPackage(lrg.memoria.core.Package mmmp);

    lrg.memoria.core.Body getCurrentBody();

    void setCurrentBody(lrg.memoria.core.Body body);


    lrg.memoria.core.CodeStripe getCurrentStripe();

    void setCurrentStripe(lrg.memoria.core.CodeStripe mmmcs);


    lrg.memoria.core.System getSystem();
    
    /**
     * @return the last annotation instance (meaning the last annotation
     * that was used in the source code to annotate something)
     */
    public lrg.memoria.core.AnnotationInstance getCurrentAnnotationInstance();
    
    /**
     * sets the current annotation instance (the annotation that is
     * currently used in code to annotate something)
     * @param a
     */
    public void setCurrentAnnotationInstance(lrg.memoria.core.AnnotationInstance a);
    
    /**
     * @return the annotation that is parsed
     */    
    public lrg.memoria.core.Annotation getCurrentAnnotation();

    /**
     * sets the current annotation
     * @param currentAnnotation
     */
	public void setCurrentAnnotation(lrg.memoria.core.Annotation currentAnnotation);
	
	/**
	 * Retrieves an lrg.memoria.core.Annotation from the repository.
     * If the key doesn't have a corresponding annotation then 
     * the null pointer will be returned.
	 * @param key - the full name of the annotation
	 */
	public lrg.memoria.core.Annotation getAnnotation(String key);
	
	/**
	 * adds a new annotation to the repository annotations map
	 * @param anot - the AnnotationDeclaration or AnnotationUseSpecification
	 * @param annotationFullName
	 * @return if the annotation already exists, a reference to it 
	 * will be returned; otherwise, a new annotation will be added 
	 * to the annotations map, and the reference will be returned
	 */
	public lrg.memoria.core.Annotation addAnnotation(Object anot, String annotationFullName);
    
}
