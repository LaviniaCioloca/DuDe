package lrg.memoria.core;

import java.util.Date;

public class Bug extends ModelElement {
	private String bugIdentifier="";  // should describe the bug uniquely
	private String status=""; //inclus de: IS si SF
	private Date creationDate; //inclus de: IS si SF
	private Date closeDate; //inclus de: SF||la fel ca si creationDate pentru IS
	private String subComponent=""; //inclus de: IS || "" pentru SF
	private String bugDescription="";	//inclus de: IS si SF
	private ModelElementList<NamedModelElement> referredModelElements;
	

	public ModelElementList<NamedModelElement> getReferredModelElements() {
		return referredModelElements;
	}

	public Bug(String id, String status, Date startDate, Date closeDate, String subcomponent, String bugDetails) {
		this();
		this.bugIdentifier = id;
		this.status=status;
		this.creationDate=startDate;
		this.closeDate=closeDate;
		this.subComponent=subComponent;
		this.bugDescription = bugDetails;
	}
	
	public Bug() {
		referredModelElements = new ModelElementList<NamedModelElement>();
	}

	public String getName() {
		return bugIdentifier;
	}
	
	public String getDescription() {
		return bugDescription;
	}

	public void accept(ModelVisitor v) {
        v.visitBug(this);		
	}
	
	public void add(NamedModelElement modelElementReferredInBug) {
		referredModelElements.add(modelElementReferredInBug);
		modelElementReferredInBug.addBug(this);
	}

}
