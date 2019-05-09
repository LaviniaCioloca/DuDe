package lrg.memoria.importer.mcc.loader;

import lrg.memoria.core.Body;
import lrg.memoria.core.Call;
import lrg.memoria.core.Function;

public class DefaultCallVisitor extends DefaultVisitorRoot implements CallVisitor {
    private int callId;
    private Body scope;
    private int count;
    private Function calledFunction;

    public void setId(Integer id) {
        callId = id.intValue();
    }

    public void setBodyId(String bodyId) {
        scope = null;
        if (!bodyId.equals(ERROR))
            scope = Loader.getInstance().getBody(new Integer(bodyId));
        if (scope == null)
            scope = Body.getUnkonwnBody();
        //assert scope != null : "DefaultCallVisitor ERROR: could not find the body for the call with id = "  + callId;
    }

    public void setFuncId(String functId) {
        calledFunction = null;
        if (!functId.equals(ERROR))
            calledFunction = Loader.getInstance().getFunction(new Integer(functId));
        if (calledFunction == null)
            calledFunction = Function.getUnknownFunction();
        //assert calledFunction != null : "DefaultCallVisitor ERROR: could not find the called function with id=" + functId + " for the call with id = "  + callId;
    }

    public void setCounter(Integer counter) {
        count = counter.intValue();
    }

    public void addCall() {
        Call currentCall = new Call(calledFunction, scope);
        currentCall.setCount(count);
        scope.addCall(currentCall);
        calledFunction.addCall(currentCall);
    }
}
