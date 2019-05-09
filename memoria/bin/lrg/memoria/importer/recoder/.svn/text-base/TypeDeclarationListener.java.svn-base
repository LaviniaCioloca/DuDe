package lrg.memoria.importer.recoder;

import lrg.memoria.core.*;
import lrg.memoria.core.Class;
import recoder.java.NonTerminalProgramElement;
import recoder.java.ProgramElement;
import recoder.java.declaration.TypeDeclaration;
import recoder.java.expression.operator.New;
import recoder.java.reference.TypeReference;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

public abstract class TypeDeclarationListener implements Listener {
    private Stack<Class> containingClassesStack = new Stack<Class>();
    private Stack<CodeStripe> oldStripes = new Stack<CodeStripe>();

    protected TypeDeclarationListener() {
    }

    public void enterModelComponent(ProgramElement pe) {
        ModelRepository mr=DefaultModelRepository.getModelRepository(null);
        oldStripes.push(mr.getCurrentStripe());
        mr.setCurrentStripe(null);
    }

    protected DataAbstraction buildClassFromDeclaration(TypeDeclaration td) {
        DefaultModelRepository mr = DefaultModelRepository.getModelRepository(null);
        Class mmmc;
        Class containingClass = mr.getCurrentClass();
        containingClassesStack.push(containingClass);
        NonTerminalProgramElement parent = td.getParent();
        if (parent instanceof New) {                                //we have an anonymous class declaration
            TypeReference tr = ((New) parent).getTypeReference();
            String name = String.valueOf(getNextAnonymousClass(containingClass.getContainedClasses()));
            mmmc = mr.addClass(td, name);
            lrg.memoria.core.Class ancestor = ReferenceConverter.getClassType(tr);
            mmmc.addAncestor(ancestor);
            ancestor.addDescendant(mmmc);
            InheritanceRelation rel = new InheritanceRelation(mmmc,ancestor,(byte)0);
            mmmc.addRelationAsDescendent(rel);
            ancestor.addRelationAsAncestor(rel);
        } else {
            StringBuffer name = new StringBuffer();
            name.append(td.getName());
            mmmc = mr.addClass(td, name.toString());
        }
        Location loc = new Location(mr.getCurrentFile());
        loc.setStartLine(td.getFirstElement().getStartPosition().getLine());
        loc.setStartChar(td.getFirstElement().getStartPosition().getColumn()); 
        loc.setEndLine(td.getLastElement().getEndPosition().getLine());
        loc.setEndChar(td.getLastElement().getEndPosition().getColumn());

        if (mr.getCurrentStripe() != null) {
            mmmc.setLocation(mr.getCurrentStripe().getRelPosOf(loc));
        } else mmmc.setLocation(loc);


        if (td.isFinal())
            mmmc.setFinal();
        if (td.isAbstract())
            mmmc.setAbstract();
        if (td.isStatic())
            mmmc.setStatic();
        if (td.isInterface())
            mmmc.setInterface();
        mmmc.setAccessMode(RecoderToMemojConverter.convertAccessMode(td));
        mmmc.setStatute(lrg.memoria.core.Statute.NORMAL);
        //Body currentBody = mr.getCurrentBody();
        if (mr.getCurrentStripe() != null) {
            mmmc.setScope(mr.getCurrentStripe());
            mr.getCurrentStripe().addScopedElement(mmmc);
        } else if (containingClass != null) {
            mmmc.setScope(containingClass);
            containingClass.addScopedElement(mmmc);
            //mr.getCurrentPackage().addType(mmmc);
        }// else {
        // Namespace nsp = mr.convertPackageToNamespace(mr.getCurrentPackage());
        //   mmmc.setScope(nsp);
        // nsp.addType(mmmc);
        //}
        mr.setCurrentClass(mmmc);
        return mmmc;
    }

    public void leaveModelComponent(ProgramElement pe) {
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);
        DataAbstraction currentClass = mr.getCurrentClass();
        ArrayList innerClasses = currentClass.getContainedClasses();
        Hashtable names = new Hashtable();
        for (int i = 0; i < innerClasses.size(); i++) {
            lrg.memoria.core.Class curClass = (lrg.memoria.core.Class) innerClasses.get(i);
            String name = curClass.getName();
            Integer index = (Integer) names.get(name);
            if (index == null) {
                names.put(name, new Integer(0));
            } else if (index.equals(new Integer(0))) {
                for (int j = 0; j < innerClasses.size(); j++) {
                    lrg.memoria.core.Class cls = (lrg.memoria.core.Class) innerClasses.get(j);
                    if (cls.getName().equals(name)) {
                        cls.setName(1 + name);
                        break;
                    }
                }
                curClass.setName(2 + name);
                names.put(name, new Integer(2));
            } else {
                int newIndex = index.intValue() + 1;
                names.put(name, new Integer(newIndex));
                curClass.setName(newIndex + name);
            }
        }
        mr.setCurrentClass(containingClassesStack.pop());
        mr.setCurrentStripe(oldStripes.pop());
    }

    private int getNextAnonymousClass(ArrayList containedClasses) {
        int num = 1;
        DataAbstraction currentClass;
        for (int i = 0; i < containedClasses.size(); i++) {
            currentClass = (DataAbstraction) containedClasses.get(i);
            String name = currentClass.getName();
            try {
                Integer.parseInt(name);
                num++;
            } catch (NumberFormatException e) {
            }
        }
        return num;
    }
}
