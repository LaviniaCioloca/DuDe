package lrg.common.abstractions.entities;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import lrg.common.abstractions.managers.EntityTypeManager;
import lrg.common.abstractions.plugins.filters.FilteringRule;
import lrg.common.abstractions.plugins.tools.AbstractGroupTool;
import lrg.common.metamodel.MetaModel;


public class GroupEntity extends AbstractEntity {

    private ArrayList elements;
    private String groupName;
    protected boolean unModifyable = false;

    protected void setLock() {
        unModifyable = true;
    }

    public GroupEntity(String name, EntityType entityType) {
        super(entityType);
        elements = new ArrayList();
        groupName = name;
        MetaModel.instance().addGroupToAddressMap(this);
    }

    public GroupEntity(String name, ArrayList theEntities) {
        super(EntityTypeManager.getEntityTypeForName("group"));
        groupName = name;
        elements = theEntities;
        MetaModel aModel = MetaModel.instance();
        if (aModel != null) aModel.addGroupToAddressMap(this);
    }

    public String toString() {
        String tmp = new String("[");
        Iterator it = elements.iterator();
        while (it.hasNext()) {
            tmp += ((AbstractEntityInterface) it.next()).getName() + " ";
        }
        return tmp + "]";
    }

    public String getName() {
        return groupName;
    }

    void setName(String name) {
        groupName = name;
    }

    /**
     * @return
     */
    public EntityType getEntityTypeOfElements() {
        Iterator it = elements.iterator();
        while (it.hasNext()) {
            AbstractEntityInterface tmp = (AbstractEntityInterface) it.next();
            if (tmp == null) continue;
         /*   if((tmp instanceof lrg.memoria.core.Class) && 
               (tmp.getEntityType().getName().compareTo("abstract entity") == 0)) 
            	  System.out.println("!!!!" + tmp.getName());
            	  
            if(tmp.getEntityType().getName().compareTo("abstract entity") == 0) { 
            	System.out.println(">>> " + tmp.getName() + "   " + tmp.getEntityType().getName());continue;}
          */
            return tmp.getEntityType();
        }
        
        return null;
    }


    public ResultEntity getProperty(String propertyDescriptor) {
        Iterator it = getElements().iterator();
        ArrayList resultCollection = new ArrayList();

        if (propertyDescriptor.compareTo("Address") == 0)
            return
                    super.getProperty(propertyDescriptor);

        ResultEntity aResult;
        while (it.hasNext()) {
            aResult = ((AbstractEntity) it.next()).getProperty(propertyDescriptor);
            if (aResult == null) continue;
            if (aResult.isCollectionResult())
                resultCollection.addAll(aResult.getValueAsCollection());
            else
                resultCollection.add(aResult);
        }

        return (resultCollection.size() != 0 ? new ResultEntity(resultCollection) : new ResultEntity(0));
    }

    /**
     * This methods redefines the methods in AbstractEntity. It builds a group of entities
     * by calling the getGroup() methods on all the elements of the current group and
     * than putting together in the same result group all the entities from all the
     * partial tools
     *
     * @param groupDescriptor - the identifier of the group to be built
     */
    public GroupEntity getGroup(String groupDescriptor) {
        Iterator it = elements.iterator();

        GroupEntity resultGroup = new GroupEntity(groupDescriptor + " of (" + getName() + ")", new ArrayList());
        while (it.hasNext()) {
            GroupEntity aGroupEntity = ((AbstractEntityInterface) it.next()).getGroup(groupDescriptor);
            if (aGroupEntity == null) continue;
            resultGroup.addAll(aGroupEntity);
        }
        return resultGroup;
    }

    /**
     * This methods applies the filter specified by the parameter on a group of entities.
     * Applying the filter happens as follows:
     * (A) if the filter is defined for exactly the EntityType of the group's elements, the filter is simply applied.
     * (B) if the filter is defined for another EntityType, the methods tries to build a group of (sub)elements
     * having the same EntityType as the one required by the FilteringRule. If the group can be built the filter
     * is applied on that group of subelements. If by applying the filter, at least an element is kept than the
     * element of the "this" GroupEntity has passed the filter, and is thus included in the result group.
     *
     * @param theFilter - WARNING: If the FilteringRule is to be applied on the SUBELEMENTS of the group than
     *                  the filter (parameter) must be created using this constructor: FilteringRule(String propertyName, String filteringOperator, double threshold, String entityType)
     * @return
     */
    public GroupEntity applyFilter(FilteringRule theFilter) {
        String nameOfEntityTypeToFilter = theFilter.getDescriptorObject().getEntityTypeName();
        GroupEntity filteredEntities = new GroupEntity(theFilter.createNameForFilteredGroup(this), theType);

        Iterator it = elements.iterator();
        if (it.hasNext() == false) return filteredEntities;

        if (shouldApplyFilterOnSubelemnts(theFilter))
            return applyFilterOnSubelements(nameOfEntityTypeToFilter, theFilter);

        while (it.hasNext()) {
            AbstractEntity crtEntity = (AbstractEntity) it.next();
            if (theFilter.applyFilter(crtEntity) == true) filteredEntities.add(crtEntity);
        }

        return filteredEntities;
    }

    private boolean shouldApplyFilterOnSubelemnts(FilteringRule theFilter) {
        String[] nameOfEntityTypeToFilter = theFilter.getDescriptorObject().getAllEntityTypeNames();
        for (int i = 0; i < nameOfEntityTypeToFilter.length; i++)
            if (getEntityTypeOfElements().getName().compareTo(nameOfEntityTypeToFilter[i]) == 0) return false;

        return true;
    }


    /**
     * This methods applies the filter identified by the String paramter only if the
     * filter can be found in the filteringRulesDictionary of the element's EntityType.
     * So, it DOES NOT apply filters recursively on the subelements.
     *
     * @param filterDescriptor
     * @return
     */
    public GroupEntity applyFilter(String filterDescriptor) {
        EntityType elementType = getEntityTypeOfElements();
        if (elementType == null) return this;

        FilteringRule theFilter = (FilteringRule) elementType.findFilteringRule(filterDescriptor);
        if (theFilter == null) return this;
        return applyFilter(theFilter);
    }

    public void runTool(String toolName, Object parameters) {
        EntityType elemType = getEntityTypeOfElements();
        if (elemType == null) return;
        AbstractGroupTool theTool = elemType.findGroupTool(toolName);
        if (theTool == null) return;
        theTool.run(this, parameters);
    }

    public AbstractEntity belongsTo(String scopeIdentifier) {
        GroupEntity groupOfScopes = new GroupEntity("scopes of " + groupName, new ArrayList());
        Iterator it = elements.iterator();
        AbstractEntity crtEntity, crtScope;
        while (it.hasNext()) {
            crtEntity = (AbstractEntity) it.next();
            if (crtEntity != null) {
                crtScope = crtEntity.belongsTo(scopeIdentifier);
                if (crtScope != null) groupOfScopes.add(crtScope);
            }
        }
        return groupOfScopes;
    }


    public ArrayList getElements() {
        return elements;
    }

    public AbstractEntityInterface getElementAt(int index) {
        return (AbstractEntityInterface) elements.get(index);
    }

    public int size() {
        return elements.size();
    }

    public Iterator iterator() {
        return elements.iterator();
    }

    public void add(AbstractEntityInterface anEntity) {
        if(unModifyable) {
            throw new RuntimeException("YOUR ANALYSIS (OR ONE YOU DEPEND ON) CONTAINS AN ERROR - YOU ARE TRYING TO MODIFY A CACHED MODEL GROUP - USE UNION NOT ADD");
        }
        elements.add(anEntity);
    }

    public void addAll(GroupEntity aGroup) {
        if(unModifyable) {
            throw new RuntimeException("YOUR ANALYSIS (OR ONE YOU DEPEND ON) CONTAINS AN ERROR - YOU ARE TRYING TO MODIFY A CACHED MODEL GROUP - USE UNION NOT ADD");
        }
        elements.addAll(aGroup.elements);
    }

    public void addAllDistinct(GroupEntity aGroup) {
        Iterator it = aGroup.getElements().iterator();
        AbstractEntity crtEntity;
        while (it.hasNext()) {
            crtEntity = (AbstractEntity) it.next();
            if (isInGroup(crtEntity) == false) add(crtEntity);
        }
    }

    public boolean isInGroup(AbstractEntityInterface entityToFind) {
        return intersect(entityToFind).size() > 0;
    }

    public GroupEntity union(AbstractEntity anElement) {
        ArrayList unionElements = new ArrayList(elements);

        unionElements.add(anElement);

        return new GroupEntity(groupName + " or " + anElement.getName(), unionElements);
    }

    public GroupEntity union(GroupEntity otherGroup) {
        ArrayList unionElements = new ArrayList(elements);

        unionElements.addAll(otherGroup.elements);

        return new GroupEntity(groupName + " or " + otherGroup.getName(), unionElements);
    }

    public GroupEntity intersect(GroupEntity otherGroup) {
        ArrayList intersectionElements = new ArrayList(elements);
        intersectionElements.retainAll(otherGroup.getElements());
        return new GroupEntity(groupName + " and " + otherGroup.getName(), intersectionElements);
    }

    public GroupEntity intersect(AbstractEntityInterface otherEntity) {
        GroupEntity tmp = new GroupEntity(otherEntity.getName(), new ArrayList());
        tmp.add(otherEntity);

        return intersect(tmp);
    }

    public GroupEntity exclude(AbstractEntity anEntity) {
        GroupEntity tmp = new GroupEntity(anEntity.getName(), new ArrayList());
        tmp.add(anEntity);
        return exclude(tmp);
    }

    public GroupEntity exclude(GroupEntity otherGroup) {
        ArrayList intersectionElements = new ArrayList(elements);

        intersectionElements.removeAll(otherGroup.getElements());

        return new GroupEntity(groupName + " without " + otherGroup.getName(), intersectionElements);
    }

    public GroupEntity cartesian(GroupEntity otherGroup) {
        ArrayList cartesianGroup = new ArrayList();

        if ((getEntityTypeOfElements() == null) || (otherGroup.getEntityTypeOfElements() == null))
            return new GroupEntity("group", cartesianGroup);

        String entityTypeName = getEntityTypeOfElements().getName() + "-" +
                otherGroup.getEntityTypeOfElements().getName();

        EntityType theEntityType = EntityTypeManager.getEntityTypeForName(entityTypeName);


        AbstractEntity firstEntity, secondEntity;
        GroupEntity currentPair;

        for (Iterator firstIt = elements.iterator(); firstIt.hasNext();) {
            firstEntity = (AbstractEntity) firstIt.next();
            for (Iterator secondIt = otherGroup.iterator(); secondIt.hasNext();) {
                secondEntity = (AbstractEntity) secondIt.next();
                currentPair = new GroupEntity("group", theEntityType);
                currentPair.add(firstEntity);
                currentPair.add(secondEntity);
                cartesianGroup.add(currentPair);
            }
        }

        return new GroupEntity("cartesian of " + getName() + " and " + otherGroup.getName(), cartesianGroup);
    }

    public GroupEntity distinct() {
        HashSet distinctElements = new HashSet(elements);
        return new GroupEntity("distinct " + groupName, new ArrayList(distinctElements));
    }


    private GroupEntity applyFilterOnSubelements(String nameOfEntityTypeToFilter, FilteringRule theFilter) {
        Iterator it = elements.iterator();
        GroupEntity filteredEntities;
        GroupEntity subGroup;
        EntityType groupType = EntityTypeManager.getEntityTypeForName(nameOfEntityTypeToFilter);

        filteredEntities = new GroupEntity(theFilter.createNameForFilteredGroup(this), groupType);
        while (it.hasNext()) {
            AbstractEntity crtEntity = (AbstractEntity) it.next();
            subGroup = crtEntity.getGroup(nameOfEntityTypeToFilter + " group");
            if (subGroup.applyFilter(theFilter).size() > 0)
                filteredEntities.add(crtEntity);
        }
        return filteredEntities;
    }

    public void putAnnotation(String id, Object annotation) {
        Iterator it = elements.iterator();

        while (it.hasNext()) ((AbstractEntity) it.next()).putAnnotation(id, annotation);
    }


}