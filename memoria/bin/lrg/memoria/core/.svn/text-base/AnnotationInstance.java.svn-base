package lrg.memoria.core;

public class AnnotationInstance extends ModelElement{
	private NamedModelElement annotatedElement; 
	private Annotation a;
	private ModelElementList<AnnotationPropertyValuePair> apv;
	
	public AnnotationInstance(Annotation a) {
		this.a = a;
		apv = new ModelElementList<AnnotationPropertyValuePair>();
	}

	/**
	 * @return the reference to the annotation that this annotation instance refers to
	 */
	public Annotation getAnnotation() {
		return a;
	}

	/**
	 * sets the annotation referred by this annotation instance
	 * @param a
	 */
	public void setAnnotation(Annotation a) {
		this.a = a;
	}

	public String getName() {
		return a.getName();
	}
	
	public String getFullName() {
		return a.getFullName();
	}
	
	/**
	 * @return the list of annotation property references and associated values
	 */
	public ModelElementList<AnnotationPropertyValuePair> getPropertyValuePairs() {
		return apv;
	}

	public void setApv(ModelElementList<AnnotationPropertyValuePair> apv) {
		this.apv = apv;
	}
	
	/**
	 * adds a new annotation property together with its value (as a string)
	 * to this annotation instance
	 * @param ap - the annotation property reference
	 * @param value - the string representing its value
	 */
	public void addAnotationProperyValuePair(AnnotationProperty ap, String value){
		AnnotationPropertyValuePair apvp = new AnnotationPropertyValuePair();
		apvp.setAp(ap);
		apvp.setValue(value);
		if(apv == null)
			apv = new ModelElementList<AnnotationPropertyValuePair>();
		apv.add(apvp);
	}

	public void accept(ModelVisitor v) {
		v.visitAnnotationInstance(this);
	}
	
	boolean restore() {
        if (super.restore()) {
            if (apv != null)
            	apv.restore();
            return true;
        }
        return false;
    }

	public void setAnnotatedElement(NamedModelElement annotatedElement) {
		this.annotatedElement = annotatedElement;
	}

	public NamedModelElement getAnnotatedElement() {
		return annotatedElement;
	}
	
}
