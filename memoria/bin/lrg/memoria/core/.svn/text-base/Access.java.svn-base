//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * The class contains the information about a variable access.
 * Each access is kept as a different object.
 */
public class Access extends ModelElement {
    private final Variable accessedVariable;
    private final CodeStripe accessingStripe;
    private Body scope;
    private int numberOfAccesses;
    private ArrayList<Location> readInstances;
    private ArrayList<Location> writeInstances;

    public static final int READ = 1;
    public static final int WRITE = 2;
    
    /**
     * Type of access: read or write
     * Be advised that if there are multiple accesses to the same variable, only one access is recorded and
     * if these accesses are mixed (reads and writes), the value of m_type will be WRITE. This doesn't mean
     * reads to that variable are not present. If however numberOfAccesses == 1 and m_type == WRITE, than it means that
     * there was exactly one WRITE access.
     */

    /**
     * Constructs a new Access object.
     * <br>
     * what - is the accessed variable
     * <br>
     * scope - is the method where does the access occur.
     * @deprecated - accesses no longer have bodies as their scope
     */
    public Access(Variable what, Body scope) {
        accessedVariable = what;
        this.scope = scope;
        numberOfAccesses = 0;
        readInstances = new ArrayList<Location>();
        writeInstances = new ArrayList<Location>();
        accessingStripe = null;
    }

    /**
     * Constructs a new Access object.
     * <br>
     * what - is the accessed variable
     * <br>
     * scope - is the method where does the access occur.
     */
    public Access(Variable what, CodeStripe fromWhere) {
        accessedVariable = what;
        numberOfAccesses = 0;
        readInstances = new ArrayList<Location>();
        writeInstances = new ArrayList<Location>();
        accessingStripe = fromWhere;
    }

    /**
     * Returns the type of this access
     *
     * @return - type code (1 for READ, 2 for WRITE)
     */
    public int getType() {
        if (writeInstances.size() > 0)
            return WRITE;
        else
            return READ;
    }

    public int getCount() {
        return numberOfAccesses;
    }

    /**
     * Do not call in conjuction with addInstance
     */
    public void setCount(int count) {
        numberOfAccesses = count;
    }

    public void addInstance(Location loc, int type) {
        if (type == READ)
            readInstances.add(loc);
        else
            writeInstances.add(loc);
        numberOfAccesses++;
    }

    public ArrayList<Location> getInstanceList() {
        ArrayList<Location> tmp = new ArrayList<Location>(readInstances);
        tmp.addAll(writeInstances);
        return tmp;
    }

    public ArrayList<Location> getReadInstanceList() {
        return readInstances;
    }

    public ArrayList<Location> getWriteInstanceList() {
        return writeInstances;
    }

    /**
     * This method returns a reference to the variable-object that is accessed.
     */
    public Variable getVariable() {
        return accessedVariable;
    }

    /**
     * This method returns where does the access occur.
     * @deprecated - use getBody() or getStripe()
     */
    public Body getScope() {
        if (accessingStripe==null) return scope;
        else return accessingStripe.getParentBody();
    }

    public Body getBody() {
        return accessingStripe.getParentBody();
    }

    public CodeStripe getStripe() {
        return accessingStripe;
    }


    ArrayList<Location> extractReadInstancesWithin(Location range) {
        return Location.extractLocationsWithin(range,readInstances);
    }

    ArrayList<Location> extractWriteInstancesWithin(Location range) {
        return Location.extractLocationsWithin(range,writeInstances);
    }

    public void accept(ModelVisitor v) {
        v.visitAccess(this);
    }

    public void writeXML(FileWriter output) throws IOException {
    }

    public String toString() {
        StringBuffer myStr = new StringBuffer();

        if ((accessedVariable instanceof Attribute) && (accessedVariable.getStatute() != lrg.memoria.core.Statute.FAILEDDEP))
            myStr.append(((Attribute) accessedVariable).getScope().getFullName()).append(".");
        myStr.append(accessedVariable.getName());
        myStr.append("(").append(numberOfAccesses).append(",");
        if (writeInstances.size() > 0)
            myStr.append("WRITE");
        else
            myStr.append("READ");
        myStr.append(")");
        return new String(myStr);
    }
}
