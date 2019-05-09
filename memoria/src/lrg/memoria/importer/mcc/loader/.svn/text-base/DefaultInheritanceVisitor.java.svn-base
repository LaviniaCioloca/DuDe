package lrg.memoria.importer.mcc.loader;

import lrg.memoria.core.*;
import lrg.memoria.core.Class;

public class DefaultInheritanceVisitor implements InheritanceVisitor {

    private DataAbstraction ancestor;
    private DataAbstraction descendant;
    private String inhAttribute;
    private int depth;
    
    public void setId(Integer id) {

    }

    public void setDescendentId(String descendentId) {
       try {
        int dID = Integer.parseInt(descendentId);
        descendant = (DataAbstraction) Loader.getInstance().getType(new Integer(dID));
        }
        catch(NumberFormatException e) {
            descendant = Class.getUnknownClass();
        }
    }

    public void setParentId(String parentId) {
        try {
            int pID = Integer.parseInt(parentId);
            Type tmp = Loader.getInstance().getType(new Integer(pID));

            if (tmp instanceof TypedefDecorator) {
                while (!(tmp instanceof Class))
                    tmp = ((TypedefDecorator) tmp).getDecoratedType();
            }

            ancestor = (DataAbstraction) tmp;
            if (ancestor == null)
                ancestor = Class.getUnknownClass();

            if (tmp != null && tmp instanceof Class)
                ancestor = (DataAbstraction) tmp;
            else
                ancestor = Class.getUnknownClass();

        } catch (NumberFormatException e) {
            ancestor = Class.getUnknownClass();
        }

    }

    public void setAttribute(String attribute) {
        inhAttribute = attribute;
    }

    public void setDepth(Integer d) {
        depth = d.intValue();
    }

    public void addInh() {
        if (depth == 1) {
            descendant.addAncestor(ancestor);
            ancestor.addDescendant(descendant);
            byte type;
            if(inhAttribute.equals("public")) type = 0;
            else if(inhAttribute.equals("protected")) type = 2;
            else type = 1;
            InheritanceRelation rel = new InheritanceRelation((Class)descendant,(Class)ancestor,type);
            ancestor.addRelationAsAncestor(rel);
            descendant.addRelationAsDescendent(rel);
        }
    }
}
