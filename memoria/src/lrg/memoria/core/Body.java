//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;


/**
 * This class contains the information about a body as it results from its
 * implementation. Only methods and classes with the "Normal" state  will
 * have a body object.
 * <P>
 * The class also contains the implementation of different procedural metrics.
 * We put only the atomic metrics. The rest of the metrics should be computable
 * based on these metrics.
 */
public abstract class Body extends ModelElement implements Scopable, Scope {
    private String sourceCode; 
    private Scope scope;
    private CodeStripe mainStripe=null;

    private int numberOfStatements = 0; // also in CodeStripe
    private int numberOfLines = 0; // also in CodeStripe
    private int numberOfComments = 0; // also in CodeStripe
    private int numberOfDecisions = 0; // also in CodeStripe
    private int numberOfLoops = 0; // also in CodeStripe
    private int numberOfExits = 0; // also in CodeStripe
    private int numberOfExceptions; // also in CodeStripe
    private int maxNestingLevel = 0; // also in CodeStripe
    private int cyclomaticNumber = 0; // also in CodeStripe

    public static Body getUnkonwnBody() {
        return ModelElementsRepository.getCurrentModelElementsRepository().getUnknownBody();
    }

    public Body(Scope scope) {
        this.scope = scope;
    }

    private void checkMainStripe() {
        if (mainStripe==null) {
            if  (((NamedModelElement)this.getScope()).getStatute()==Statute.NORMAL)
                java.lang.System.err.println("*** Warning: You might be loosing Information! (Body.java:41)");
            mainStripe=new CodeStripe(this);
        }
    }

    /**
     * @deprecated
     */
    public void addScopedElement(Scopable element) {
        checkMainStripe();
        mainStripe.addScopedElement(element);
    }

    /**
     * @deprecated
     */
    public ModelElementList getScopedElements() {
        ModelElementList scoped=new ModelElementList();
        scoped.addAll(getLocalVarList());
        scoped.addAll(getInnerTypesList());
        return scoped;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public String getName() {
        return scope.getName();
    }

    public String getFullName() {
        return scope.getFullName();
    }

    /**
     * Adds a local variable declared in this body.
     * @deprecated
     */
    public void addLocalVar(LocalVariable var) {
        checkMainStripe();
        mainStripe.addLocalVar(var);
    }

    /**
     * Adds an inner type declared in this body.
     * @deprecated
     */
    public void addInnerType(Type type) {
        checkMainStripe();
        mainStripe.addInnerType(type);
    }

    /**
     * Adds a call-object, representing a call that occur in the implementation of that body.
     * @deprecated - please do not add calls to bodies anymore, add to CodeStripes
     */
    public void addCall(Call c) {
        checkMainStripe();
        mainStripe.addCall(c);
    }

    /**
     * Adds an access-object, representing a variable access that
     * occur in the implementation of that body.
     * @deprecated - add accesses to code stripes not bodies
     */
    public void addAccess(Access a) {
        checkMainStripe();
        mainStripe.addAccess(a);
    }

    /**
     * The list of local variables declared in this body.
     * @deprecated
     */
    public ModelElementList<LocalVariable> getLocalVarList() {
        checkMainStripe();
        return mainStripe.flattenLocalVariables();
    }

    /**
     * The list of call-objects, representing the calls that occur in the implementation of that body.
     * @deprecated - access call objects via CodeStripe
     */
    public ModelElementList<Call> getCallList() {
        checkMainStripe();
        return mainStripe.flattenCalls();
    }

    /**
     * The list of accesed-objects, representing the variable accesses that
     * occur in the implementation of that body.
     * @deprecated - get access objects from code stripes
     */
    public ModelElementList<Access> getAccessList() {
        checkMainStripe();
        return mainStripe.flattenAccesses();
    }

    /**
     * @deprecated
     */
    public ModelElementList<Type> getInnerTypesList() {
        checkMainStripe();
        return mainStripe.flattenInnerTypes();
    }

    /**
     * Sets the number of statements of a method.
     * Following statements are counted:
     * - Control statements.
     * - Statements followed by ";"
     * @deprecated
     */
    public void setNumberOfStatements(int n) {
        numberOfStatements = n;
    }

    /**
     * Sets the total number of lines in a body.
     * @deprecated
     */
    public void setNumberOfLines(int n) {
        numberOfLines = n;
    }

    /**
     * Sets the number of commented lines in a body.
     * @deprecated
     */
    public void setNumberOfComments(int n) {
        numberOfComments = n;
    }

    /**
     * Sets the number of statements with a decision (if, switch).
     * @deprecated
     */
    public void setNumberOfDecisions(int n) {
        numberOfDecisions = n;
    }

    /**
     * Sets the number of loop statements (pre- and post- tested loops).
     * @deprecated
     */
    public void setNumberOfLoops(int n) {
        numberOfLoops = n;
    }

    /**
     * Sets the number of statements associated with an explicit exit from this body. (return, exit)
     * @deprecated
     */
    public void setNumberOfExits(int n) {
        numberOfExits = n;
    }

    /**
     * Sets the number of "catch" blocks in a body.
     * Concerning "numberofExceptions" we have to consider also about
     * the possibility to count the number of "try-catch" blocks in a function.
     * @deprecated
     */
    public void setNumberOfExceptions(int n) {
        numberOfExceptions = n;
    }

    /**
     * Sets the McCabe cyclomatic complexity number of the function.
     * <P>
     * Obs:
     * <br>
     * We know that compared to the rest of the metrics defined for
     * this class this one is not atomic, but because it is important
     * for us, and because its definition still has to be studied we
     * put it here.
     * @deprecated
     */
    public void setCyclomaticNumber(int n) {
        cyclomaticNumber = n;
    }

    /**
     * Sets the maximum nesting level for control structures in the body.
     * @deprecated
     */
    public void setMaxNestingLevel(int n) {
        maxNestingLevel = n;
    }

    /**
     * Returns the number of statements of a method.
     * Following statements are counted:
     * - Control statements.
     * - Statements followed by ";"
     * <br>NOT IMPLEMENTED YET.
     * @deprecated
     */
    public int getNumberOfStatements() {
        return numberOfStatements;
    }

    /**
     * Returns the total number of lines in a body.
     * <br>NOT IMPLEMENTED YET.
     * @deprecated
     */
    public int getNumberOfLines() {
        return numberOfLines;
    }

    /**
     * Returns the number of commented lines in a body.
     * <br>NOT IMPLEMENTED YET.
     * @deprecated
     */
    public int getNumberOfComments() {
        return numberOfComments;
    }

    /**
     * Is the number of "catch" blocks in a body.
     * Concerning "numberofExceptions" we have to consider also about
     * the possibility to count the number of "try-catch" blocks in a function.
     * @deprecated
     */
    public int getNumberOfExceptions() {
        return numberOfExceptions;
    }

    /**
     * Is the McCabe cyclomatic complexity number of the function.
     * <P>
     * Obs:
     * <br>
     * We know that compared to the rest of the metrics defined for
     * this class this one is not atomic, but because it is important
     * for us, and because its definition still has to be studied we
     * put it here.
     * @deprecated
     */
    public int getCyclomaticNumber() {
        return cyclomaticNumber;
    }

    /**
     * Is the number of statements with a decision (if, switch).
     * @deprecated
     */
    public int getNumberOfDecisions() {
        return numberOfDecisions;
    }

    /**
     * Is the number of loop statements (pre- and post- tested loops).
     * @deprecated
     */
    public int getNumberOfLoops() {
        return numberOfLoops;
    }

    /**
     * Is the number of statements associated with an explicit exit from this body.(return, exit)
     * <br>NOT IMPLEMENTED YET.
     * @deprecated
     */
    public int getNumberOfExits() {
        return numberOfExits;
    }

    /**
     * Is the maximum nesting level for control structures in the body".
     * <br>NOT IMPLEMENTED YET.
     * @deprecated
     */
    public int getMaxNestingLevel() {
        return maxNestingLevel;
    }

    public abstract void accept(ModelVisitor v);

    public String getSourceCode() {
        checkMainStripe();
        return mainStripe.getSourceCode();
        //return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public CodeStripe getCodeStripe() {
        return mainStripe;
    }

    public void setCodeStripe(CodeStripe stripe) {
        mainStripe=stripe;
    }

    public String toString() {
        int i, tmp;
        StringBuffer myStr = new StringBuffer();
        checkMainStripe();

        if (mainStripe.flattenLocalVariables().size()>0) {
            myStr.append("\n\t\t\t\t - local variables: ");
            for (i = 0; i < mainStripe.flattenLocalVariables().size(); i++) {
                LocalVariable var;
                var = (LocalVariable) mainStripe.flattenLocalVariables().get(i);
                myStr.append(var.getName()).append("(" + var.getType().getName());
                myStr.append(")").append(", ");
            }
            if (i > 0) {
                tmp = myStr.length();
                myStr.delete(tmp - 2, tmp).append(".");
            }
        }
        myStr.append("\n\t\t\t\t - accesses: ");
        for (i = 0; i < mainStripe.flattenAccesses().size(); i++)
            myStr.append((Access) mainStripe.flattenAccesses().get(i)).append(", ");
        if (i > 0) {
            tmp = myStr.length();
            myStr.delete(tmp - 2, tmp).append(".");
        }
        myStr.append("\n\t\t\t\t - calls: ");
        for (i = 0; i < mainStripe.flattenCalls().size(); i++)
            myStr.append((Call) mainStripe.flattenCalls().get(i)).append(", ");
        if (i > 0) {
            tmp = myStr.length();
            myStr.delete(tmp - 2, tmp).append(".");
        }
        myStr.append("\n\t\t\t\t - metrics:");
        myStr.append("\n\t\t\t\t\t - Cyclomatic number: " + cyclomaticNumber);
        myStr.append("\n\t\t\t\t\t - Number of loops: " + numberOfLoops);
        myStr.append("\n\t\t\t\t\t - Number of exceptions: " + numberOfExceptions);
        myStr.append("\n\t\t\t\t\t - Number of decisions: " + numberOfDecisions);
        myStr.append("\n\t\t\t\t\t - Number of lines: " + numberOfLines);
        myStr.append("\n\t\t\t\t\t - Number of comments: " + numberOfComments);
        myStr.append("\n\t\t\t\t\t - Number of statements: " + numberOfStatements);
        myStr.append("\n\t\t\t\t\t - Number of exits: " + numberOfExits);
        myStr.append("\n\t\t\t\t\t - Max nesting level: " + maxNestingLevel);
        return new String(myStr);
    }

    /**
     * @deprecated
     */
    public Location getLocation() {
        checkMainStripe();
        return mainStripe.getLocation();
    }

    /**
     * @deprecated
     */
    public void setLocation(Location l) {
        checkMainStripe();
        mainStripe.setLocation(l);
    }

    /*-------------------------------------------------------------*/

    boolean restore() {
        if (super.restore()) {
            if (mainStripe!=null)
                mainStripe.restore();
            return true;
        }
        return false;
    }

}



