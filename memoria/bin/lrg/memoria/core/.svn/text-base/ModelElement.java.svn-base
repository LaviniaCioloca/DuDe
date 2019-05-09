//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;

import lrg.common.abstractions.entities.AbstractEntity;
import lrg.common.abstractions.managers.EntityTypeManager;

import java.io.Serializable;

/**
 * The common interface for the lrg.insider.lrg.insider.core elements.
 */
public abstract class ModelElement extends AbstractEntity implements Serializable, ModelElementRoot {
    protected transient boolean restored = false;
    private Long elementID;

    public ModelElement() {
        elementID = ModelElementsRepository.getCurrentModelElementsRepository().addElement(this);
    }

    public void setElementID(Long elementID) {
        this.elementID = elementID;
    }

    public Long getElementID() {
        return elementID;
    }

    /**
     * Used after deserialization
     */
    boolean restore() {
        if (restored == true)
            return false;
        else
            return (restored = true);
    }

    //private Hashtable propertiesMap = new Hashtable();
}
