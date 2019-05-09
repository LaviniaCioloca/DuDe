package lrg.memoria.importer.recoder;

import lrg.memoria.core.Annotation;
import lrg.memoria.core.AnnotationInstance;
import lrg.memoria.core.AnnotationProperty;
import lrg.memoria.core.DataAbstraction;
import lrg.memoria.core.ModelElement;
import lrg.memoria.core.Package;
import recoder.abstraction.ProgramModelElement;
import recoder.java.Expression;
import recoder.java.NonTerminalProgramElement;
import recoder.java.PackageSpecification;
import recoder.java.ProgramElement;
import recoder.java.declaration.AnnotationDeclaration;
import recoder.java.declaration.AnnotationPropertyDeclaration;
import recoder.java.declaration.AnnotationUseSpecification;
import recoder.java.declaration.ClassDeclaration;
import recoder.java.declaration.EnumConstantDeclaration;
import recoder.java.declaration.FieldDeclaration;
import recoder.java.declaration.InterfaceDeclaration;
import recoder.java.declaration.LocalVariableDeclaration;
import recoder.java.declaration.MethodDeclaration;
import recoder.java.declaration.ParameterDeclaration;
import recoder.java.expression.ElementValueArrayInitializer;
import recoder.java.expression.Literal;
import recoder.java.reference.TypeReference;
import recoder.java.reference.VariableReference;

public class AnnotationUseSpecificationListener implements Listener{

	static{
		ModelConstructor.addFactory("lrg.memoria.importer.recoder.AnnotationUseSpecificationListener", new Factory());
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
				return (listener = new AnnotationUseSpecificationListener());
		}
		
	}
	
	private AnnotationUseSpecificationListener(){
	}
	
	private String getValueFromExpression(Expression e){
		String result = "";
		if(e instanceof Literal){
			Literal l = (Literal)e;
			result += l.getEquivalentJavaType().toString();
		}
		else if (e instanceof VariableReference){
			VariableReference vr = (VariableReference)e;
			result += vr.getName();
		}
		else if (e instanceof ElementValueArrayInitializer){
			ElementValueArrayInitializer ev = (ElementValueArrayInitializer)e;
//			result += "{";
			for (Expression ex : ev.getElementValues())
			{
				String value = getValueFromExpression(ex);
				result += isNumber(value) ? value : "'"+ value +"'"; 
				result+=" ";
			}
			int endIndex = result.length()-1;
			result = result.substring(0, endIndex);
//			result += "}";
		}
		else{
			result += "_UNKNOWN";
		}
		return result;
	}
	
	private boolean isNumber(String value) {
		try  { Double.valueOf(value); } catch(NumberFormatException e) { return false; }
		try  { Integer.valueOf(value); } catch(NumberFormatException e) { return false; }
				
		return true;
	}

	public void enterModelComponent(ProgramElement pe) {
		ModelRepository mr = DefaultModelRepository.getModelRepository(null);
		AnnotationUseSpecification aus = (AnnotationUseSpecification)pe;
        	
		Annotation a = mr.getAnnotation(ReferenceConverter.getTypeFromTypeReference(aus.getTypeReference()).getFullName());
		if(a==null){
			//non-user defined annotation or 
			//user defined annotation whose declaration will be parsed later		
			a = mr.addAnnotation(aus, ReferenceConverter.getTypeFromTypeReference(aus.getTypeReference()).getFullName());
		}
		
		AnnotationInstance ai = new AnnotationInstance(a);
		if(aus.getElementValuePairs()!=null){
			for(int i=0;i<aus.getElementValuePairs().size();i++){
				String value;
				try{
					value = getValueFromExpression(aus.getElementValuePairs().get(i).getElementValue());
				}
				catch (Exception e) {
					value = "_UNKNOWN";
					System.out.println("Exception coming from reading annotation property values");
				}
				//if the annotation has only one property, its name might be omitted
				//when the annotation is used, so apr.getName() will return the property's 
				//name and aus.getElementValuePairs().get(i).getElementName() will return
				//"(default value)"
				if(a.getAnnotationProperties().size() == 1){
					AnnotationProperty apr = a.getAnnotationProperties().get(0);
					ai.addAnotationProperyValuePair(apr, value);
				}
				else {
					for (AnnotationProperty apr: a.getAnnotationProperties()){
						if(apr.getName()==aus.getElementValuePairs().get(i).getElementName()){
							ai.addAnotationProperyValuePair(apr, value);
						}
					}
				}
			}
		}
		
		NonTerminalProgramElement parent = aus.getASTParent();
		
		if(!(parent instanceof AnnotationDeclaration) && (parent instanceof ClassDeclaration || parent instanceof InterfaceDeclaration)){
			DataAbstraction c = mr.getCurrentClass();
			if(ai!=null && c!=null) {
				c.addAnnotationInstance(ai);
				ai.setAnnotatedElement(c);
			}
		}
		//we exclude annotation properties (they are annotated later)
		else if (parent instanceof MethodDeclaration && !(parent instanceof AnnotationPropertyDeclaration)){
			lrg.memoria.core.Method m = mr.getCurrentMethod();
			if(ai!=null && m!=null) {
				m.addAnnotationInstance(ai);
				ai.setAnnotatedElement(m);
			}
		}
		else if (parent instanceof AnnotationDeclaration){
			lrg.memoria.core.Annotation an = mr.getCurrentAnnotation();
			if(ai!=null && an!=null) {
				an.addAnnotationInstance(ai); //add a meta-annotation
				ai.setAnnotatedElement(an);
			}
		}
		//we exclude enumerated values (in our model)
		else if (!(parent instanceof EnumConstantDeclaration) && (parent instanceof FieldDeclaration || parent instanceof ParameterDeclaration || parent instanceof LocalVariableDeclaration)){			
			mr.setCurrentAnnotationInstance(ai);
			//here we set the currentAnnotationInstance in DefaulModelRepository
			//and later, in Field/Variable Listener, it will be used to annotate 
			//the field/variable/parameter
		}
		else if (parent instanceof AnnotationPropertyDeclaration){
			AnnotationPropertyDeclaration apd = (AnnotationPropertyDeclaration)parent;
			NonTerminalProgramElement grandparent = parent.getASTParent();
			AnnotationDeclaration ad = (AnnotationDeclaration)grandparent;
			Annotation ano = mr.getAnnotation(ad.getFullName());
			for (AnnotationProperty apr: ano.getAnnotationProperties()){
				if(apr.getName()==apd.getName()){
					if(ai!=null) {
						apr.addAnnotationInstance(ai);
						ai.setAnnotatedElement(apr);
					}
				}
			}
		}
		else if (parent instanceof PackageSpecification){
			Package p = mr.getCurrentPackage();
			if (ai != null && p != null) {
				p.addAnnotationInstance(ai);
				ai.setAnnotatedElement(p);
			}
		}
	}

	public void leaveModelComponent(ProgramElement pe) {
		// TODO Auto-generated method stub
	}

}
