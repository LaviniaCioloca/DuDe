//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;

/**
 * A model element which has getName.
 */
public abstract class NamedModelElement extends ModelElement {
    protected String name;
    private int statute = Statute.FAILEDDEP;
	protected ModelElementList<AnnotationInstance> annotations;
	protected ModelElementList<Bug> bugs;

    public NamedModelElement(String name) {
        this.name = name;
        annotations = new ModelElementList<AnnotationInstance>();
        bugs = new ModelElementList<Bug>();
    }

    protected NamedModelElement(NamedModelElement namedElement) {
        name = namedElement.name;
        statute = namedElement.statute;
        annotations = namedElement.annotations;
        bugs = namedElement.bugs;

    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return getName();
    }

    /**
     * This method sets the statute of the current named model element.
     * In this model, we distinguish between  three categories of model elements, that may occur
     * in a project. These categories are:
     * "Normal" model elements -- i.e. classes defined  in the project
     * "Library" model elements -- i.e. model elements that are referenced within the project,
     * for which we don't have the source-code, but only the byte-code.  A reference to
     * such a model element may occur as (the example is given for types):
     * + the type in a  variable declaration
     * + the return-type of a method
     * + the getName of a ancestor class, or an interface
     * "FailedDependency" model elements -- i.e. model elements that are referenced within the project,
     * but for which neither the source-code, nor the byte-code could be found during parsing.
     * <P>
     * REMARK:
     * "FailedDependency" model elements are logged to a file for possible further analysis, as
     * they might bias the results of the source-code analysis.
     * <P>
     * The statutes are defined in the class Statute. (i.e. Statute.NORMAL for normal model elements).
     */
    public void setStatute(int statute) {
        this.statute = statute;
    }

    /**
     * See the documentation from setStatute(int)
     */
    public int getStatute() {
        return statute;
    }

	public void addAnnotationInstance(AnnotationInstance a) {
		if(annotations == null)
			annotations = new ModelElementList<AnnotationInstance>();
		annotations.add(a);
	}

	public void addBug(Bug bug) {
		if(bugs == null)
			bugs = new ModelElementList<Bug>();
		bugs.add(bug);
	}

	
	public ModelElementList<AnnotationInstance> getAnnotations() {
		return annotations;
	}

	public ModelElementList<Bug> getBugs() {
		return bugs;
	}

	
	public void setAnnotations(ModelElementList<AnnotationInstance> annotations) {
		this.annotations = annotations;
	}
	
	
}
