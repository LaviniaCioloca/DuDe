package lrg.memoria.core;

public class Annotation extends ExplicitlyDefinedType implements Scope{

	private ModelElementList<AnnotationProperty> annotationProperties;
	
	/**
	 * creates a new Annotation with the specified name
	 */
	public Annotation(String name) {
		super(name);
		annotationProperties = new ModelElementList<AnnotationProperty>();
	}

	protected Annotation(Annotation a){
		super(a);
		annotationProperties = a.annotationProperties;
		//annotations = a.annotations;
	}

	public void accept(ModelVisitor v) {
		v.visitAnnotation(this);
	}
	
	public void setName(String newName){
		name = newName;
	}

	/**
	 * adds the annotation properties
	 */
	public void addScopedElement(Scopable element) {
		if(element instanceof AnnotationProperty)
			addAnnotationProperty((AnnotationProperty)element);	
	}

	/**
	 * returns the elements (annotation properties) in the 
	 * annotation's scope
	 */
	public ModelElementList getScopedElements() {
		return annotationProperties;
	}
	
	/**
	 * adds a new annotation property
	 */
	public void addAnnotationProperty(AnnotationProperty ap){
		if(annotationProperties == null)
			annotationProperties = new ModelElementList<AnnotationProperty>();
		annotationProperties.add(ap);
	}

	/**
	 * returns the list with the annotation properties
	 */
	public ModelElementList<AnnotationProperty> getAnnotationProperties() {
		return annotationProperties;
	}

	/**
	 * sets the list of annotation properties to this annotation 
	 */
	public void setAnnotationProperties(ModelElementList<AnnotationProperty> properties) {
		this.annotationProperties = properties;
	}

	public String toString(){
		StringBuffer myStr = new StringBuffer("\t\tAnnotation: ");
		myStr.append(getFullName()).append("\n\t\t - statute: ");
		myStr.append(Statute.convertToString(getStatute()));
		myStr.append("\n\t\t - location: ");
		if(getLocation() != null)
			myStr.append(getLocation());
		myStr.append("\n\t\t - scope: ");
        if (getScope() != null)
        	myStr.append(getScope().getName());
        if(annotationProperties != null){
        	myStr.append("\n\t\t - properties: ");
        	for(AnnotationProperty ap : annotationProperties){
        		myStr.append(ap);
        	}
        }
        if(annotations != null){
        	myStr.append("\n\t\t - meta-annotations: ");
        	for(AnnotationInstance ai : annotations){
        		myStr.append("\n\t\t\t -" + ai.getAnnotation().getFullName());
        		for(int i=0;i<ai.getPropertyValuePairs().size();i++){
        			myStr.append("\n\t\t\t\t");
        			myStr.append(ai.getPropertyValuePairs().get(i).getAp().getName());
        			myStr.append(" = ").append(ai.getPropertyValuePairs().get(i).getValue());
        		}
        	}
        }
		return new String(myStr);				
	}
	
	boolean restore() {
        if (super.restore()) {
            if (annotationProperties != null)
                annotationProperties.restore();
            if (annotations != null)
            	annotations.restore();
            return true;
        }
        return false;
    }

}
