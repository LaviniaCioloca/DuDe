package lrg.memoria.importer.mcc.loader;

import lrg.memoria.core.Access;
import lrg.memoria.core.Body;
import lrg.memoria.core.Variable;

public class DefaultAccessVisitor extends DefaultVisitorRoot implements AccessVisitor {
    private int id;
    private int count;
    private Body currentBody;
    private Variable currentVariable;

    public void setId(Integer id) {
        this.id = id.intValue();
    }

    public void setBodyId(String bodyId) {
        currentBody = null;
        if (!bodyId.equals(ERROR))
            currentBody = Loader.getInstance().getBody(new Integer(bodyId));
        if (currentBody == null)
            currentBody = Body.getUnkonwnBody();
        //assert currentBody != null : "DefaultAccessVisitor ERROR: could not find the body for access with id = "  + id;
    }

    public void setVarId(String varId) {
        currentVariable = null;
        if (!varId.equals(ERROR))
            currentVariable = Loader.getInstance().getVariable(new Integer(varId));
        if (currentVariable == null)
            currentVariable = Variable.getUnknownVariable();
        //assert currentVariable != null : "DefaultAccessVisitor ERROR: could not find the accessed variable for access with id = "  + id;
    }

    public void setCounter(Integer counter) {
        count = counter.intValue();
    }

    public void addAccess() {
        Access currentAccess = new Access(currentVariable, currentBody);
        currentAccess.setCount(count);
        currentVariable.addAccess(currentAccess);
        currentBody.addAccess(currentAccess);
    }
}
