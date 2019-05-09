package lrg.memoria.importer.recoder;

import recoder.java.ProgramElement;
import recoder.java.declaration.InheritanceSpecification;
import recoder.java.reference.TypeReference;
import recoder.list.generic.ASTList;
import lrg.memoria.core.DataAbstraction;
import lrg.memoria.core.InheritanceRelation;

public abstract class InheritanceSpecificationListener implements Listener {

    public void enterModelComponent(ProgramElement pe) {
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);
        InheritanceSpecification e = (InheritanceSpecification) pe;

        ASTList<TypeReference> trl = e.getSupertypes();
        for (int i = 0; i < trl.size(); i++) {
            TypeReference tr = trl.get(i);
            lrg.memoria.core.Class curClass = mr.getCurrentClass();
            lrg.memoria.core.Class mmmc = ReferenceConverter.getClassType(tr);
            setInheritance(curClass, mmmc);
            mmmc.addDescendant(curClass);
            InheritanceRelation rel = new InheritanceRelation(curClass,mmmc,(byte)0);
            curClass.addRelationAsDescendent(rel);
            mmmc.addRelationAsAncestor(rel);            
        }
    }

    protected abstract void setInheritance(lrg.memoria.core.Class curClass, DataAbstraction superType);

    public void leaveModelComponent(ProgramElement pe) {
    }
}
