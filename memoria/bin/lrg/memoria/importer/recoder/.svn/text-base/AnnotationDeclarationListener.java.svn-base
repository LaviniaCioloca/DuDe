package lrg.memoria.importer.recoder;

import java.util.Stack;

import lrg.memoria.core.Annotation;
import lrg.memoria.core.AnnotationProperty;
import lrg.memoria.core.Class;
import lrg.memoria.core.Location;
import recoder.abstraction.ClassType;
import recoder.java.ProgramElement;
import recoder.java.declaration.AnnotationDeclaration;

public class AnnotationDeclarationListener implements Listener{
	
	static{
		ModelConstructor.addFactory("lrg.memoria.importer.recoder.AnnotationDeclarationListener", new Factory());
	}
	
	private static Listener listener;
	
	static class Factory implements IFactory{

		public void cleanUp() {
			listener = null;			
		}

		public Listener getListener() {
			if(listener != null)
				return listener;
			else
				return (listener = new AnnotationDeclarationListener());
		}
		
	}
	
	private Stack<Annotation> oldAnnotations = new Stack<Annotation>();
	
	private AnnotationDeclarationListener(){
	}
	
	public void enterModelComponent(ProgramElement pe){
		AnnotationDeclaration ad = (AnnotationDeclaration) pe;
		ModelRepository mr = DefaultModelRepository.getModelRepository(null);
		//this line is added because annotations are types too, so they
		//should be in the repository (as classes and common interfaces are)
		Class cls = mr.addClass(ad, ad.getName());
		Annotation mmma = mr.addAnnotation(ad, ad.getFullName());
		//At this point we should either just added the new annotation,
		//either the annotation was added earlier because it was used before it's
		// declaration. In both cases, we only have to update its location.
		Location loc = new Location(mr.getCurrentFile());
		loc.setStartLine(ad.getStartPosition().getLine());
		loc.setStartChar(ad.getStartPosition().getColumn());
		loc.setEndLine(ad.getEndPosition().getLine());
		loc.setEndChar(ad.getEndPosition().getColumn());			
		mmma.setLocation(loc);
		for (recoder.abstraction.Method f : ad.getMethods()) {
			AnnotationProperty ap = null;
			for (AnnotationProperty apr: mmma.getAnnotationProperties()){
				if(apr.getName() == f.getName())
				{
					ap = apr;
					break;
				}
			}
			Location location = new Location(mr.getCurrentFile());
			location.setStartLine(((recoder.java.declaration.MethodDeclaration)f).getFirstElement().getStartPosition().getLine());
			location.setStartChar(((recoder.java.declaration.MethodDeclaration)f).getFirstElement().getStartPosition().getColumn());
			location.setEndLine(((recoder.java.declaration.MethodDeclaration)f).getEndPosition().getLine());
			location.setEndChar(((recoder.java.declaration.MethodDeclaration)f).getEndPosition().getColumn());
			if (ap != null)
				ap.setLocation(location);
		}
		if(cls != null)
		{
			cls.setStatute(mmma.getStatute());
			cls.setLocation(mmma.getLocation());
			//as a type, annotations are marked as interfaces (because 
			//they are a "special" type of interface)
			cls.setInterface();
		}
		oldAnnotations.push(mr.getCurrentAnnotation());
		mr.setCurrentAnnotation(mmma);
	}

	public void leaveModelComponent(ProgramElement pe) {
		ModelRepository mr = DefaultModelRepository.getModelRepository(null);
		mr.setCurrentAnnotation(oldAnnotations.pop());
	}
}
