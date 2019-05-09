//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;

/**
 * A generic visitor of the lrg.insider.lrg.insider.core nodes.
 */
public abstract class ModelVisitor {

	/**
     * Visits the specified annotation.
     * The default implementation doesn't do anything.
     */
    public void visitAnnotation(Annotation a) {
    }
	

    public void visitAnnotationInstance(AnnotationInstance a) {    	
    }


    public void visitAnnotationProperty(AnnotationProperty a) {    	
    }

    /**
     * Visits an inheritance relation.
     * The default implementation doesn't do anything.
     */

    public void visitSubsystem(Subsystem s) {
    }

    public void visitInheritanceRelation(InheritanceRelation r) {
    }

    /**
     * Visits the system.
     * The default implementation doesn't do anything.
     */
    public void visitSystem(System s) {
    }

    /**
     * Visits the specified file.
     * The default implementation doesn't do anything.
     */
    public void visitFile(File f) {
    }

    /**
     * Visits the specified package.
     * The default implementation doesn't do anything.
     */
    public void visitPackage(Package p) {
    }

    public void visitComponent(Component c) {
        
    }
    /**
     * Visits the specified namespace.
     * The default implementation doesn't do anything.
     */
    public void visitNamespace(Namespace p) {
    }

    /**
     * Visits the specified class.
     * The default implementation doesn't do anything.
     */
    public void visitClass(Class c) {
    }

    /**
     * Visits the specified primitive.
     * The default implementation doesn't do anything.
     */
    public void visitPrimitive(Primitive p) {
    }

    /**
     * Visits the specified union.
     * The default implementation doesn't do anything.
     */
    public void visitUnion(Union u) {
    }

    /**
     * Visits the specified template parameter.
     * The default implementation doesn't do anything.
     */
    public void visitTemplateParameter(TemplateParameterType u) {
    }

    /**
     * Visits the specified pointer decorator type.
     * The default implementation doesn't do anything.
     */
    public void visitPointerDecorator(PointerDecorator pd) {
    }

    /**
     * Visits the specified array decorator type.
     * The default implementation doesn't do anything.
     */
    public void visitArrayDecorator(ArrayDecorator ad) {
    }

    /**
     * Visits the specified reference decorator type.
     * The default implementation doesn't do anything.
     */
    public void visitReferenceDecorator(ReferenceDecorator rd) {
    }

    /**
     * Visits the specified typedef decorator type.
     * The default implementation doesn't do anything.
     */
    public void visitTypedefDecorator(TypedefDecorator td) {
    }

    /**
     * Visits the specified function pointer type.
     * The default implementation doesn't do anything.
     */
    public void visitFunctionPointer(FunctionPointer fp) {
    }

    /**
     * Visits the specified function pointer function.
     * The default implementation doesn't do anything.
     */
    public void visitPointerToFunction(PointerToFunction ptf) {
    }

    /**
     * Visits the specified global variable.
     * The default implementation doesn't do anything.
     */
    public void visitGlobalVar(GlobalVariable v) {
    }


    /**
     * Visits the specified attribute.
     * The default implementation doesn't do anything.
     */
    public void visitAttribute(Attribute a) {
    }

    /**
     * Visits the specified parameter.
     * The default implementation doesn't do anything.
     */
    public void visitParameter(Parameter p) {
    }

    /**
     * Visits the specified local variable.
     * The default implementation doesn't do anything.
     */
    public void visitLocalVar(LocalVariable l) {
    }

    /**
     * Visits the specified method.
     * The default implementation doesn't do anything.
     */
    public void visitMethod(Method m) {
    }

    /**
     * Visits the specified global function.
     * The default implementation doesn't do anything.
     */
    public void visitGlobalFunction(GlobalFunction m) {
    }

    /**
     * Visits the specified function body.
     * The default implementation doesn't do anything.
     */
    public void visitFunctionBody(FunctionBody m) {
    }

    /**
     * Visits the specified initializer body.
     * The default implementation doesn't do anything.
     */
    public void visitInitializerBody(InitializerBody i) {
    }

    /**
     * Visits the specified initializer CodeStripe.
     * The default implementation doesn't do anything.
     */
    public void visitCodeStripe(CodeStripe cs) {
    }

    /**
     * Visits the specified call.
     * The default implementation doesn't do anything.
     */
    public void visitCall(Call c) {
    }

    /**
     * Visits the specified access.
     * The default implementation doesn't do anything.
     */
    public void visitAccess(Access a) {
    }

    public void visitTemplateInstance(TemplateInstance templateInstanceDecorator) {
    }


	public void visitBug(Bug bug) {		
	}
}
