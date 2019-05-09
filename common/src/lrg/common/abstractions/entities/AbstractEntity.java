package lrg.common.abstractions.entities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import lrg.common.abstractions.managers.EntityTypeManager;
import lrg.common.abstractions.plugins.tools.AbstractEntityTool;


public class AbstractEntity implements AbstractEntityInterface {
    protected EntityType theType;
    protected HashMap groupDictionary;
    protected HashMap propertyDictionary;
    private Hashtable annotationsMap = new Hashtable();

    public AbstractEntity(EntityType theEntityType) {
        theType = theEntityType;
    }

    public AbstractEntity() {
        this(EntityTypeManager.getEntityTypeForName("abstract entity"));
    }

    public void setEntityType(EntityType entityType) {
        theType = entityType;
    }

    public EntityType getEntityType() {
        return theType;
    }

    public String getName() {
        return "NONAME(AbstractEntity)";
    }

    public String toString() {
        return getName();
    }

    public void removeProperty(String aDescriptor) {
        if (propertyDictionary == null) return;
        propertyDictionary.remove(aDescriptor);
    }

    public void addProperty(String aDescriptor, ResultEntity aPropertyResult) {
        if (propertyDictionary == null)
            propertyDictionary = new HashMap();
        propertyDictionary.put(aDescriptor, aPropertyResult);
    }

    public void addGroup(String aDescriptor, GroupEntity aPropertyResult) {
        if (groupDictionary == null)
            groupDictionary = new HashMap();
        groupDictionary.put(aDescriptor, aPropertyResult);
        aPropertyResult.setLock();
    }

    public ResultEntity getProperty(String propertyDescriptor) {
        if (propertyDictionary == null) propertyDictionary = new HashMap();
        ResultEntity computedProperty;
        computedProperty = (ResultEntity) propertyDictionary.get(propertyDescriptor);
        if (computedProperty != null) return computedProperty;

        if (theType == null) return null;
        computedProperty = theType.computeProperty(propertyDescriptor, this);

        // property was not computed so try to interpret the propertyDescriptor
        // as indicating to an aggredated property
        if (computedProperty == null)
            computedProperty = getAggregatedProperty(propertyDescriptor);

        // if successfully computed add the property result to the cache.
        if (computedProperty != null)
            propertyDictionary.put(propertyDescriptor, computedProperty);
        else
        // property was not computed so try to interpret the propertyDescriptor
        // as indicating to the property of a subpart ==> the Result contains a Group
            computedProperty = getPropertyGroupFromSubparts(propertyDescriptor);

        return computedProperty;
    }


    private ResultEntity getPropertyGroupFromSubparts(String propertyDescriptor) {
        ArrayList resultCollection = new ArrayList(), tmp;
        GroupEntity subTypeGroup;
        ResultEntity aResult;
        EntityType subType;

        Iterator it = EntityTypeManager.getAllSubtypesForName(theType.getName()).iterator();
        if (it.hasNext() == false) return null;

        while (it.hasNext()) {
            subType = (EntityType) it.next();
            // get predefined group of components for subType
            subTypeGroup = theType.buildGroup(subType.getName() + " group", this);
            // try to build the group from the tools of the subType
            if (subTypeGroup == null) continue;
            aResult = subTypeGroup.getProperty(propertyDescriptor);
            if (aResult != null) {
                tmp = aResult.getValueAsCollection();
                if (tmp != null)
                    resultCollection.addAll(tmp);
                else
                    resultCollection.add(aResult.getValue());
            }
        }
        return (resultCollection.size() != 0 ? new ResultEntity(resultCollection) : null);
    }

    private ResultEntity getAggregatedProperty(String propertyDescriptor) {
        String[] parts = propertyDescriptor.split("_");

        if (parts.length < 2) return null;

        ResultEntity groupOfResults = getPropertyGroupFromSubparts(parts[1]);
        if (groupOfResults == null) return null;

        return groupOfResults.aggregate(parts[0]);
    }

    public GroupEntity getGroup(String groupIdentifier) {
        if (groupDictionary == null) groupDictionary = new HashMap();

        GroupEntity aGroup = (GroupEntity) groupDictionary.get(groupIdentifier);

        if (aGroup != null) return aGroup;

        if (theType == null) return new GroupEntity(groupIdentifier, new ArrayList());

        aGroup = theType.buildGroup(groupIdentifier, this);

        if (aGroup == null) {  // this means that the entity asks its subparts to build the group
            Iterator it = EntityTypeManager.getAllSubtypesForName(theType.getName()).iterator();
            EntityType subType;
            GroupEntity subTypeGroup;
            while (it.hasNext()) {
                subType = (EntityType) it.next();
                // get predefined group of components for subType
                subTypeGroup = theType.buildGroup(subType.getName() + " group", this);
                // try to build the group from the tools of the subType
                if (subTypeGroup != null) {
                    GroupEntity groupFromSubtype = subTypeGroup.getGroup(groupIdentifier);
                    if ((groupFromSubtype != null) && (groupFromSubtype.size() > 0)) {
                        if (aGroup == null)
                            aGroup = groupFromSubtype;
                        else
                            aGroup.addAll(groupFromSubtype);
                    }
                }
            }
        }

        if (aGroup != null) {
            aGroup.setName(composeGroupName(groupIdentifier));
            groupDictionary.put(groupIdentifier, aGroup);
            aGroup.setLock();
            return aGroup;
        }

        return new GroupEntity(groupIdentifier, new ArrayList());
    }
    /*
    public GroupEntity getGroup(String groupIdentifier) {
        if (groupDictionary == null) groupDictionary = new HashMap();

        GroupEntity aGroup = (GroupEntity) groupDictionary.get(groupIdentifier);

        if (aGroup != null) return aGroup;

        if (theType == null) return new GroupEntity(groupIdentifier, new ArrayList());

        aGroup = theType.buildGroup(groupIdentifier, this);

        if (aGroup == null) {  // this means that the entity asks its subparts to build the group
            Iterator it = EntityTypeManager.getAllSubtypesForName(theType.getName()).iterator();
            EntityType subType;
            GroupEntity subTypeGroup;
            while (it.hasNext()) {
                subType = (EntityType) it.next();
                // get predefined group of components for subType
                subTypeGroup = theType.buildGroup(subType.getName() + " group", this);
                // try to build the group from the tools of the subType
                if (subTypeGroup != null) {
                    GroupEntity tmp = subTypeGroup.getGroup(groupIdentifier);
                    if (tmp != null && tmp.size() > 0) {
                        aGroup = tmp;
                        break;
                    }
                }
            }
        }

        if (aGroup != null) {
            aGroup.setName(composeGroupName(groupIdentifier));
            groupDictionary.put(groupIdentifier, aGroup);
            return aGroup;
        }

        return new GroupEntity(groupIdentifier, new ArrayList());
    }
    */

    public GroupEntity uses(String groupIdentifier) {
        return getGroup(groupIdentifier);
    }

    public GroupEntity isUsed(String groupIdentifier) {
        return getGroup(groupIdentifier);
    }

    public GroupEntity contains(String groupIdentifier) {
        return getGroup(groupIdentifier);
    }

    public AbstractEntity belongsTo(String scopeIdentifier) {
        AbstractEntity theScope, currentEntity = this;

        do {
            theScope = currentEntity.getProperty("scope");
            if (theScope == null) return null;
            Object anObject = ((ResultEntity) theScope).getValue();
            if (anObject instanceof AbstractEntity == false) return null;
            theScope = (AbstractEntity) anObject;
            if (theScope == null) return null;
            if (theScope.getEntityType().getName().compareTo(scopeIdentifier) == 0) return theScope;
            currentEntity = theScope;
        } while (true);
    }

    public AbstractEntityTool getTool(String toolIdentifier) {
        return getEntityType().findEntityTool(toolIdentifier);
    }

    /**
     * Adds a new property to the current list of properties of this element.
     */
    public void putAnnotation(String addnotationName, Object addnotation) {
        annotationsMap.put(addnotationName, addnotation);
    }

    /**
     * Retrieves a property given its getName.
     */
    public Object getAnnotation(String addnotationName) {
        return annotationsMap.get(addnotationName);
    }

    /**
     * Removes a property given its name. Behaves just as Hashtable.remove()
     */
    public Object removeAnnotation(String annotationName) {
        return annotationsMap.remove(annotationName);
    }

    public String annotationsToString() {
        String result = "";
        Iterator it = annotationsMap.keySet().iterator();
        while (it.hasNext()) result += it.next() + " ";

        return result;
    }


    private String composeGroupName(String groupIdentifier) {
        return groupIdentifier + " of " + this.getEntityType().getName() + " " + this.getName();
    }
}