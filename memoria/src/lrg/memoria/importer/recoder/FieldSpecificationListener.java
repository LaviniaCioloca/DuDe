package lrg.memoria.importer.recoder;

import lrg.memoria.core.ArrayDecorator;
import lrg.memoria.core.DataAbstraction;
import lrg.memoria.core.Location;
import recoder.java.ProgramElement;
import recoder.java.declaration.FieldSpecification;
import recoder.java.reference.TypeReference;

public class FieldSpecificationListener implements Listener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.FieldSpecificationListener", new Factory());
    }

    private FieldSpecificationListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new FieldSpecificationListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);
        FieldSpecification fs = (FieldSpecification) pe;
        DataAbstraction mmmScope = mr.getCurrentClass();
        lrg.memoria.core.Attribute mmma = mr.addAttribute(fs, fs.getName());
        mmma.setStatute(lrg.memoria.core.Statute.NORMAL);
        TypeReference tr = fs.getParent().getTypeReference();
        int arrayDim = ReferenceConverter.getArrayDimension(tr);
        lrg.memoria.core.Type baseType = ReferenceConverter.getTypeFromTypeReference(tr);
        if (arrayDim > 0) {
            ArrayDecorator ad = mr.getArrayType(baseType, arrayDim);
            mmma.setType(ad);
        } else
            mmma.setType(baseType);
        Location loc = new Location(mr.getCurrentFile());
        loc.setStartLine(fs.getFirstElement().getStartPosition().getLine());
        loc.setStartChar(fs.getFirstElement().getStartPosition().getColumn());
        loc.setEndLine(fs.getLastElement().getEndPosition().getLine());
        loc.setEndChar(fs.getLastElement().getEndPosition().getColumn());
        mmma.setLocation(loc);
        mmma.setScope(mmmScope);
        mmmScope.addAttribute(mmma);
        mmma.setAccessMode(RecoderToMemojConverter.convertAccessMode(fs));
        if (fs.isStatic())
            mmma.setStatic();
        if (fs.isFinal())
            mmma.setFinal();
        
        //if this variable is annotated, add the current annotation instance
        //to its annotation collection and reset the current annotation instance
        if(mr.getCurrentAnnotationInstance()!=null){
        	mmma.addAnnotationInstance(mr.getCurrentAnnotationInstance());
        	mr.getCurrentAnnotationInstance().setAnnotatedElement(mmma);
        	mr.setCurrentAnnotationInstance(null);
        }
        
    }

    public void leaveModelComponent(ProgramElement pe) {
    }
}
