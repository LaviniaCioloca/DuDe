package lrg.common.abstractions.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import lrg.common.abstractions.managers.EntityTypeManager;
import lrg.common.abstractions.plugins.AbstractPlugin;
import lrg.common.abstractions.plugins.details.AbstractDetail;
import lrg.common.abstractions.plugins.filters.FilteringRule;
import lrg.common.abstractions.plugins.groups.GroupBuilder;
import lrg.common.abstractions.plugins.groups.GroupEntityBuilder;
import lrg.common.abstractions.plugins.operators.AggregationOperator;
import lrg.common.abstractions.plugins.operators.FilteringOperator;
import lrg.common.abstractions.plugins.properties.PropertyComputer;
import lrg.common.abstractions.plugins.tools.AbstractEntityTool;
import lrg.common.abstractions.plugins.tools.AbstractGroupTool;
import lrg.common.abstractions.plugins.visualization.AbstractVisualization;


public class EntityType implements Serializable {
    private String name;
    private String supertypeName;

    private HashMap propertyComputerDictionary;
    private HashMap groupBuilderDictionary;
    private HashMap filteringRuleDictionary;
    private HashMap filteringOperatorDictionary;
    private HashMap toolsDictionary;
    private HashMap visualizationsDictionary;
    private HashMap detailsDictionary;


    public EntityType(String name, String supertypeName) {
        this.name = name;
        this.supertypeName = supertypeName;

        groupBuilderDictionary = new HashMap();
        propertyComputerDictionary = new HashMap();
        filteringRuleDictionary = new HashMap();
        filteringOperatorDictionary = new HashMap();
        toolsDictionary = new HashMap();
        visualizationsDictionary = new HashMap();
        detailsDictionary = new HashMap();
    }

    public EntityType(String name) {
        this(name, "");
    }

    public String getName() {
        return name;
    }


    public String getSupertypeName() {
        return supertypeName;
    }



    public void attach(AbstractPlugin someCommand) {
        if ((someCommand instanceof GroupBuilder) || (someCommand instanceof GroupEntityBuilder))
            groupBuilderDictionary.put(someCommand.getDescriptorObject().getName(), someCommand);
        else if (someCommand instanceof PropertyComputer) {
            propertyComputerDictionary.put(someCommand.getDescriptorObject().getName(), someCommand);
            if (someCommand instanceof FilteringRule)
                filteringRuleDictionary.put(someCommand.getDescriptorObject().getName(), someCommand);
        } else if ((someCommand instanceof AbstractGroupTool) || (someCommand instanceof AbstractEntityTool))
            toolsDictionary.put(someCommand.getDescriptorObject().getName(), someCommand);
        else if (someCommand instanceof AggregationOperator)
            filteringRuleDictionary.put(someCommand.getDescriptorObject().getName(), someCommand);
        else if (someCommand instanceof FilteringOperator) {
            filteringOperatorDictionary.put(someCommand.getDescriptorObject().getName(), someCommand);
        } else if (someCommand instanceof AbstractDetail) {
            detailsDictionary.put(someCommand.getDescriptorObject().getName(), someCommand);
        } else if (someCommand instanceof AbstractVisualization) {
            visualizationsDictionary.put(someCommand.getDescriptorObject().getName(), someCommand);
        }
    }


    public void unAttach(FilteringRule aRule) {
        filteringRuleDictionary.remove(aRule.getDescriptorObject().getName());
    }


    /**
     * The methods returns a filtering command that can be than applied on an entity.
     *
     * @param filterDescriptor
     * @return The reason why the methods does not return a FilteringRule object
     *         <<<<<<< EntityType.java
     *         <p/>
     *         is because we use the same dictionary to keep both filtering lrg.insider.hiddens
     *         <p/>
     *         =======
     *         is because we use the same dictionary to keep both filtering lrg.insider.plugins
     *         >>>>>>> 1.4
     *         that are subclassed from FilteringRule and filtering operators (e.g. HigherThan)
     *         subclassed from FilteringOperator
     */

    public AbstractPlugin findFilteringRule(String filterDescriptor) {
        return (AbstractPlugin) filteringRuleDictionary.get(filterDescriptor);
    }


    public AbstractEntityTool findEntityTool(String toolDescriptor) {
        return (AbstractEntityTool) toolsDictionary.get(toolDescriptor);
    }

    public AbstractVisualization findVisualizations(String descriptor) {
        return (AbstractVisualization) visualizationsDictionary.get(descriptor);
    }

    public AbstractDetail findDetails(String descriptor) {
        return (AbstractDetail) detailsDictionary.get(descriptor);
    }

    public AbstractGroupTool findGroupTool(String toolDescriptor) {
        return (AbstractGroupTool) toolsDictionary.get(toolDescriptor);
    }


    /**
     * The methods looks in the propertyDictionary for a property named as
     * the first parameter and if it finds it it, it computes the result
     * of that property (using the adequate PropertyComputer object) on the
     * entity passed as the second parameter to the methods
     *
     * @param propertyDescriptor - the name of the property to be computed
     * @param anEntity
     * @return May return null is the property was not found in the dictionary
     */

    public ResultEntity computeProperty(String propertyDescriptor, AbstractEntityInterface anEntity) {
        PropertyComputer aPropertyComputer = (PropertyComputer) propertyComputerDictionary.get(propertyDescriptor);
        if (aPropertyComputer == null) return null;      // TODO introduce exception instead of null

        return aPropertyComputer.compute(anEntity);
    }


    public FilteringOperator findFilteringOperator(String filteringOperator) {
        return (FilteringOperator) filteringOperatorDictionary.get(filteringOperator);
    }


    public PropertyComputer findPropertyComputer(String propertyComputerName) {
        return (PropertyComputer) propertyComputerDictionary.get(propertyComputerName);
    }

    /**
     * This methods looks in its dictionary of groupBuilders for one matching the groupDescriptor
     * and if it finds a builder it applies it for the entity passed as a parameter.
     * The result is a GroupEntity.
     *
     * @param groupDescriptor - the name of the group to be built
     * @param anEntity        - the entity for which the group is built
     * @return May return null is the group was not found in the dictionary
     */

    public GroupEntity buildGroup(String groupDescriptor, AbstractEntityInterface anEntity) {
        GroupBuilder aGroupBuilder = (GroupBuilder) groupBuilderDictionary.get(groupDescriptor);
        if (aGroupBuilder == null) return null; // TODO introduce exception instead of null

        ArrayList resultGroup = aGroupBuilder.buildGroup(anEntity);
        if (resultGroup == null) return null;
        return new GroupEntity(groupDescriptor, resultGroup);
    }


    /**
     * The methods gathers *recursively* the names of all the tools that can be
     * built for this EntityType.
     *
     * @return A Set of String objects
     */

    public ArrayList nameAllGroupBuilders() {
        Set namesOfAllGroupBuilders = new HashSet();
        namesOfAllGroupBuilders.addAll(groupBuilderDictionary.keySet());

        Iterator it = EntityTypeManager.getAllSubtypesForName(name).iterator();
        EntityType crt;
        while (it.hasNext()) {
            crt = (EntityType) it.next();
            namesOfAllGroupBuilders.addAll(crt.nameAllGroupBuilders());
        }
        return sortKeys(namesOfAllGroupBuilders);
    }


    /**
     * The methods returns a set with all the names of all FilteringRules stored in the
     * filteringRuleDictionary. It does NOT work recursively!
     *
     * @return A Set of String objects
     */

    public ArrayList nameAllFilteringRules() {
        return sortKeys(filteringRuleDictionary.keySet());
    }


    public ArrayList nameAllTools() {
        return sortKeys(toolsDictionary.keySet());
    }

    public ArrayList nameAllVisualizations() {
        return sortKeys(visualizationsDictionary.keySet());
    }

    public ArrayList nameAllDetails() {
        return sortKeys(detailsDictionary.keySet());
    }

    /**
     * The methods returns a set with all the names of all PropertyComputers stored in the
     * propertyComputerDictionary. It does NOT work recursively!
     *
     * @return A Set of String objects
     */
    public ArrayList nameAllPropertyComputers() {
        return sortKeys(propertyComputerDictionary.keySet());
    }


    public ArrayList nameAllFilteringOperators() {
        return sortKeys(filteringOperatorDictionary.keySet());
    }


    private ArrayList sortKeys(Set setToSort) {
        ArrayList list = new ArrayList(setToSort);
        Collections.sort(list);
        return list;
    }
    
    public ArrayList nameAllTools(HashSet<String> acceptedPlugins) {
    	ArrayList group = sortKeys(toolsDictionary.keySet());
        group.retainAll(acceptedPlugins);
        return group;
    }

    

    public ArrayList nameAllVisualizations(HashSet<String> acceptedPlugins) {
    	ArrayList group = sortKeys(visualizationsDictionary.keySet());
        group.retainAll(acceptedPlugins);
        return group;
    }
    

    public ArrayList nameAllDetails(HashSet<String> acceptedPlugins) {
    	ArrayList group = sortKeys(detailsDictionary.keySet());
        group.retainAll(acceptedPlugins);
        return group;
    }
    
    
    public ArrayList nameAllPropertyComputers(HashSet<String> acceptedPlugins) {
    	ArrayList group = sortKeys(propertyComputerDictionary.keySet());
        group.retainAll(acceptedPlugins);
        return group;
    }

    public ArrayList<String> nameAllGroupBuilders(HashSet<String> acceptedPlugins) {
        ArrayList group = nameAllGroupBuilders();
        group.retainAll(acceptedPlugins);
        return group;
    }

}