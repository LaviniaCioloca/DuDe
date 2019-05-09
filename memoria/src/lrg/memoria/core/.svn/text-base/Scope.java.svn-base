package lrg.memoria.core;

import lrg.common.abstractions.entities.AbstractEntityInterface;

/**
 * This interface defines a scope. Each scope has a short and an unique getName and a
 * scope that contains it. The scope of a namespace is the namespace itself. 
 */
public interface Scope extends Scopable, AbstractEntityInterface {
    public String getName();
    public String getFullName();
    public void addScopedElement(Scopable element);
    public ModelElementList getScopedElements();
}
