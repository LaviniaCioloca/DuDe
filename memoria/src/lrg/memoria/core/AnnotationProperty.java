package lrg.memoria.core;

public class AnnotationProperty extends NamedModelElement implements Scopable{

	private Type type;
	private Scope scope;
	private Location location = Location.getUnknownLocation();

	/**
	 * creates a new annotation property with the specified name
	 */
	public AnnotationProperty(String name) {
		super(name);
	}
	
	protected AnnotationProperty(AnnotationProperty ap){
		super(ap);
		type = ap.type;
		scope = ap.scope;
		location = ap.location;
		//annotations = ap.annotations;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public Scope getScope() {
		return scope;
	}

	public String toString(){
		StringBuffer myStr = new StringBuffer("\n\t\t\t - Annotation Property: ");
		myStr.append(getName());
		myStr.append("\n\t\t\t  - type: ");
		if(getType()!=null)
			myStr.append(getType().getFullName());
		else
			myStr.append("unknown");
		myStr.append("\n\t\t\t  - statute: ");
		myStr.append(Statute.convertToString(getStatute()));
		myStr.append("\n\t\t\t  - location: ");
		if(getLocation() != null)
			myStr.append(getLocation());
		myStr.append("\n\t\t\t  - scope: ");
        if (getScope() != null)
        	myStr.append(getScope().getName());
        if(annotations != null){
        	myStr.append("\n\t\t\t  - annotations: ");
        	for(AnnotationInstance ai : annotations){
        		myStr.append("\n\t\t\t\t - " + ai.getAnnotation().getFullName());
        		for(int i=0;i<ai.getPropertyValuePairs().size();i++){
        			myStr.append("\n\t\t\t\t\t");
        			myStr.append(ai.getPropertyValuePairs().get(i).getAp().getName());
        			myStr.append(" = ").append(ai.getPropertyValuePairs().get(i).getValue());
        		}
        	}
        }
		return new String(myStr);
	}
	
	public void accept(ModelVisitor v) {
		v.visitAnnotationProperty(this);
	}
	
	boolean restore() {
        if (super.restore()) {            
            if(annotations != null)
            	annotations.restore();
            return true;
        }
        return false;
    }

}
