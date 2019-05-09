package lrg.memoria.importer.recoder;

import lrg.memoria.core.LocalVariable;
import lrg.memoria.core.Location;
import lrg.memoria.core.Parameter;
import recoder.java.ProgramElement;
import recoder.java.declaration.*;
import recoder.java.reference.TypeReference;
import recoder.java.statement.Catch;

public class VariableSpecificationListener implements Listener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.VariableSpecificationListener", new Factory());
    }

    private VariableSpecificationListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new VariableSpecificationListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);
        VariableSpecification vs = (VariableSpecification) pe;
        VariableDeclaration vd = vs.getParent();
        if (vd instanceof ParameterDeclaration) {
            lrg.memoria.core.Variable mmmp;
            if (vd.getASTParent() instanceof Catch) {
                mmmp = mr.addLocalVar(vs, vs.getName(),mr.getCurrentStripe());
                mr.getCurrentStripe().addLocalVar((LocalVariable) mmmp);
                ((LocalVariable) mmmp).setExParam();
                mmmp.setLocation(mr.getCurrentStripe().getRelPosOf(getLocation(mr, vs)));
            } else {
                mmmp = mr.addParameter(vs, vs.getName());
                lrg.memoria.core.Function scope = mr.getCurrentMethod();
                ((Parameter) mmmp).setScope(scope);
                if (scope != null)
                    scope.addParameter((Parameter) mmmp);
                mmmp.setLocation(getLocation(mr, vs));
            }
            mmmp.setStatute(lrg.memoria.core.Statute.NORMAL);

            TypeReference tr = vs.getParent().getTypeReference();
            int arrayDimensions = vs.getDimensions() + ReferenceConverter.getArrayDimension(tr);
            lrg.memoria.core.Type baseType = ReferenceConverter.getTypeFromTypeReference(tr);
            if (arrayDimensions > 0)
                mmmp.setType(mr.getArrayType(baseType, arrayDimensions));
            else
                mmmp.setType(baseType);
            if (vs.isFinal())
                mmmp.setFinal();
            
            //if this variable is annotated, add the current annotation instance
            //to its annotation collection and reset the current annotation instance
            if(mr.getCurrentAnnotationInstance()!=null){
            	mmmp.addAnnotationInstance(mr.getCurrentAnnotationInstance());
            	mr.getCurrentAnnotationInstance().setAnnotatedElement(mmmp);
            	mr.setCurrentAnnotationInstance(null);
            }
            
        } else if (vd instanceof LocalVariableDeclaration) {
            lrg.memoria.core.LocalVariable mmmlv = mr.addLocalVar(vs, vs.getName(),mr.getCurrentStripe());
            mr.getCurrentStripe().addLocalVar(mmmlv);
            //setLocalVarScope(mr, mmmlv);
            mmmlv.setStatute(lrg.memoria.core.Statute.NORMAL);
            mmmlv.setLocation(mmmlv.getStripe().getRelPosOf(getLocation(mr, vs)));

            TypeReference tr = vs.getParent().getTypeReference();
            int arrayDim = ReferenceConverter.getArrayDimension(tr);
            lrg.memoria.core.Type baseType = ReferenceConverter.getTypeFromTypeReference(tr);
            if (arrayDim > 0)
                mmmlv.setType(mr.getArrayType(baseType, arrayDim));
            else
                mmmlv.setType(baseType);
            if (vs.isFinal())
                mmmlv.setFinal();
            ProgramElement grandParent = vd.getASTParent().getASTParent();
            if (!(grandParent instanceof MethodDeclaration || grandParent instanceof ClassInitializer))
                mmmlv.setBlock();
            
            //if this variable is annotated, add the current annotation instance
            //to its annotation collection and reset the current annotation instance
            if(mr.getCurrentAnnotationInstance()!=null){
            	mmmlv.addAnnotationInstance(mr.getCurrentAnnotationInstance());
            	mr.getCurrentAnnotationInstance().setAnnotatedElement(mmmlv);
            	mr.setCurrentAnnotationInstance(null);
            }
            
        }
    }

    private Location getLocation(ModelRepository mr, VariableSpecification vs) {
        Location loc = new Location(mr.getCurrentFile());
        loc.setStartLine(vs.getFirstElement().getStartPosition().getLine());
        loc.setStartChar(vs.getFirstElement().getStartPosition().getColumn());
        loc.setEndLine(vs.getLastElement().getEndPosition().getLine());
        loc.setEndChar(vs.getLastElement().getEndPosition().getColumn());
        return loc;
    }

    public void leaveModelComponent(ProgramElement pe) {
    }
}
