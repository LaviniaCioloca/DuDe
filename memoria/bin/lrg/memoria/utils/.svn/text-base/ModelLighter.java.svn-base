package lrg.memoria.utils;

import lrg.memoria.core.*;
import lrg.memoria.core.Class;
import lrg.memoria.core.Package;
import lrg.memoria.core.System;
import lrg.memoria.importer.recoder.JavaModelLoader;

import java.io.File;
import java.util.HashMap;

public class ModelLighter extends ModelVisitor {
    public static final int ENTIRE_MODEL = 0;
    public static final int ONLY_TYPES = 1;
    public static final int UP_TO_METHOD_LEVEL = 2;

    private System currentSystem;
    private int level;

    public ModelLighter(System sys) {
        currentSystem = sys;
    }

    public void lightenModel(int level) {
        this.level = level;
        MEMORIABreadthIterator mbi = new MEMORIABreadthIterator(currentSystem);
        cleanFailedDepandencies();
        while (mbi.hasNext())
            mbi.next().accept(this);

        if (level != ENTIRE_MODEL) {
            ModelElementsRepository mer = ModelElementsRepository.getCurrentModelElementsRepository();
            HashMap<Long, ModelElement> newElements = new HashMap<Long, ModelElement>();
            cleanModelElementsRepository(mer, newElements);
            mer.setModelElements(newElements);
        }
    }

    private void cleanFailedDepandencies() {
        ModelElementList<ModelElement> newFDL = new ModelElementList<ModelElement>();
        for (ModelElement me : currentSystem.getFailedDepElementList()) {
            if (entityShouldRemain(me))
                newFDL.add(me);
        }
        currentSystem.setFailedDepElementList(newFDL);
    }

    private void cleanModelElementsRepository(ModelElementsRepository mer, HashMap newElements) {
        HashMap<Long, ModelElement> oldElements = mer.getModelElements();
        long elementsCount = mer.getElementCount();
        long currentID = 0;
        ModelElement current;
        for (long i = 0; i < elementsCount; i++) {
            current = oldElements.get(new Long(i));
            if (entityShouldRemain(current)) {
                current.setElementID(new Long(currentID));
                newElements.put(new Long(currentID), current);
                currentID++;
            }
        }
    }

    private boolean entityShouldRemain(ModelElement current) {
        if (current instanceof Type || current instanceof Package || current instanceof System)
            return true;
        if (level == ONLY_TYPES)
            return false;
        if (level == UP_TO_METHOD_LEVEL)
            if (current instanceof Function || current instanceof Variable)
                return true;
        return false;
    }

    public void visitNamespace(Namespace n) {
        visitPackage(n);
    }

    public void visitPackage(Package p) {
        if (level == ONLY_TYPES) {
            p.setGlobalFunctions(new ModelElementList<GlobalFunction>());
            p.setGlobalVariables(new ModelElementList<GlobalVariable>());
            ModelElementList<Type> types = p.getAllTypesList();
            ModelElementList<Type> nonInnerTypes = new ModelElementList<Type>();
            for (Type t : types) {
                if ((t.getScope() instanceof Namespace) || (t.getScope() instanceof DataAbstraction))
                    nonInnerTypes.add(t);
            }
            p.setAllTypesList(nonInnerTypes);
        }
    }

    public void visitClass(Class c) {
        if (level == ONLY_TYPES) {
            c.setAttributes(new ModelElementList<Attribute>());
            c.setMethods(new ModelElementList<Method>());
        }
        c.setInitializersList(new ModelElementList<InitializerBody>());
    }

    public void visitUnion(Union u) {
        if (level == ONLY_TYPES) {
            u.setAttributes(new ModelElementList<Attribute>());
            u.setMethods(new ModelElementList<Method>());
        }
    }

    public void visitMethod(Method m) {
        visitFunction(m);
    }

    public void visitGlobalFunction(GlobalFunction gf) {
        visitFunction(gf);
    }

    private void visitFunction(Function f) {
        if (level == UP_TO_METHOD_LEVEL) {
            f.setCallList(new ModelElementList<Call>());
            f.setFunctionBody((FunctionBody) Body.getUnkonwnBody());
        }
    }

    public void visitAttribute(Attribute a) {
        visitVariable(a);
    }

    public void visitGlobalVariable(GlobalVariable gv) {
        visitVariable(gv);
    }

    public void visitParameter(Parameter par) {
        visitVariable(par);
    }

    public void visitLocalVar(LocalVariable lv) {
        visitVariable(lv);
    }

    private void visitVariable(Variable v) {
        if (level == UP_TO_METHOD_LEVEL) {
            v.setAccessesList(new ModelElementList<Access>());
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            java.lang.System.out.println("Usage: java ModelLighter java_sources_path_list cache_path loading_level");
        }

        int loadingLevel = Integer.parseInt(args[2]);

        System sys = new JavaModelLoader(args[0], null, null).getSystem();

        java.lang.System.out.println("Making the model lighter ...");

        ModelLighter currentLighter = new ModelLighter(sys);

        if (loadingLevel != ModelLighter.ENTIRE_MODEL) {
            currentLighter.lightenModel(loadingLevel);
            sys.setLoadingLevel(loadingLevel);
        }

        java.lang.System.out.println("Saving the light model into cache ...");

        java.lang.System.gc();
        System.serializeToFile(new File(args[1]), sys);

        /*int i = 4;
        while(1 < 2) {
            if (i == 2)
                java.lang.System.out.println(sys);
        } */
    }

}
