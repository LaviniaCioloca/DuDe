//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Iterator;

import lrg.common.abstractions.managers.EntityTypeManager;

/*
 * A list of ModelElements.
 */
public class ModelElementList<T extends ModelElementRoot> extends ArrayList<T> implements Externalizable {
    private ArrayList idList;
    private boolean restored = false;

    public void writeExternal(ObjectOutput out) throws IOException {
        idList = new ArrayList();
        Iterator it = this.iterator();
        while (it.hasNext()) {
            idList.add(((ModelElement) it.next()).getElementID());
        }
        out.writeObject(idList);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        idList = (ArrayList) in.readObject();
    }

    boolean restore() {
        if (restored)
            return false;
        clear();
        Iterator it = idList.iterator();
        ModelElementsRepository mer = ModelElementsRepository.getCurrentModelElementsRepository();
        while (it.hasNext()) {            
            T elem = (T)mer.byElementID((Long) it.next());
            if (elem == null) {
                int i = 4;
            }
            ((ModelElement)elem).restore();
            add(elem);
        }
        return true;
    }
}
