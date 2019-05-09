//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;

import lrg.common.abstractions.entities.AbstractEntityInterface;

/**
 * This interface is a used as a marker for types.
 * <P>
 * The two classes that implement it are:
 * "Primitive" (stays for the predefined primitive types of the language)
 * "Function
 */
public interface Type extends Scopable, ModelElementRoot, AbstractEntityInterface {
    /**
     * This method returns the getName of the type.
     */
    public String getName();

    /**
     * This method returns the getName that uniquely identifies the type in the system.
     */
    public String getFullName();    
}

