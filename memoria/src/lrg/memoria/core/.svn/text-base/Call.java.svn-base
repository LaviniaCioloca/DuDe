//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class implements a call. Each different call is a different object.
 */

public class Call extends ModelElement {
    private final Function callee;
    private final CodeStripe sourceStripe;
    private Body scope;
    private int count;
    private ArrayList<Location> callInstances;

    /**
     * Creates a call object.
     * This have a specified function (i.e. the function which is called) and the scope (i.e. where does the call occur)
     * @deprecated - The scope is no longer a body but a CodeStripe
     */
    public Call(Function what, Body scope) {
        callee = what;
        this.scope = scope;
        count = 0;
        callInstances = new ArrayList<Location>();
        sourceStripe=null;
    }

    public Call(Function what, CodeStripe source) {
        callee = what;
        this.sourceStripe = source;
        callInstances = new ArrayList<Location>();
        count = 0;
    }

    public int getCount() {
        return count;
    }

    /**
     * Do not call in conjuction with addInstance
     */
    public void setCount(int count) {
        this.count = count;
    }

    public void addInstance(Location loc) {
        callInstances.add(loc);
        count++;
    }

    public ArrayList<Location> getInstanceList() {
        return callInstances;
    }

    /**
     * This method returnes a reference to the the called method-object.
     * @deprecated - use getFunction instead
     */
    public Method getMethod() {
        return (Method)callee;
    }

    /**
     * This method returnes a reference to the the called function object.
     */
    public Function getFunction() {
        return callee;
    }

    /**
     * This method returnes the BODY where does the call occur.
     * @deprecated - use getBody() or getStripe()
     */
    public Body getScope() {
        if (sourceStripe==null) return scope;
        else return sourceStripe.getParentBody();
    }


    public Body getBody() {
        return sourceStripe.getParentBody();
    }

    public CodeStripe getStripe() {
        return sourceStripe;
    }

    ArrayList<Location> extractInstancesWithin(Location range) {
        return Location.extractLocationsWithin(range,callInstances);
    }

    public void accept(ModelVisitor mv) {
        mv.visitCall(this);
    }

    public String toString() {
        StringBuffer myStr = new StringBuffer();

        if (callee.getStatute() != lrg.memoria.core.Statute.FAILEDDEP)
            myStr.append(callee.getScope().getFullName()).append(".");
        myStr.append(callee.getName());
        myStr.append("(").append(count).append(")");

        return new String(myStr);
    }
}
