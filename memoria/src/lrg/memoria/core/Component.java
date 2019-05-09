package lrg.memoria.core;

/**
 * Created by IntelliJ IDEA.
 * User: marinescu
 * Date: 22.11.2006
 * Time: 11:10:50
 * To change this template use File | Settings | File Templates.
 */
public class Component extends NamedModelElement implements Scope {
    private ModelElementList<Class> containedClasses;
    private lrg.memoria.core.System theSystem;
    public Component(String name, lrg.memoria.core.System theScope) {
        super(name);
        containedClasses = new ModelElementList<Class>();
        theSystem = theScope;
    }

    public void accept(ModelVisitor v) {
        v.visitComponent(this);
    }

    public void addScopedElement(Scopable element) {
        if(element instanceof Class == false) return;
        containedClasses.add((Class)element);
    }
    
    public void removeScopedElement(Scopable element) {
        if(element instanceof Class == false) return;
        containedClasses.remove((DataAbstraction)element);
    }

    public ModelElementList getScopedElements() {
        return containedClasses;
    }

    public Scope getScope() {
        return this;
    }

    public System getSystem() {
        return theSystem;
    }
}
