package lrg.common.abstractions.plugins.filters;


import java.util.ArrayList;
import java.util.HashSet;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.plugins.Descriptor;
import lrg.common.abstractions.plugins.properties.PropertyComputer;


public class FilteringRule extends PropertyComputer {

    protected String propertyDescriptor;
    protected String filteringOperator;

    Object threshold;


    public FilteringRule(Descriptor theDescriptor) {
        super(theDescriptor.getName(), theDescriptor.getInfo(), theDescriptor.getAllEntityTypeNames(), "boolean");
    }


    public FilteringRule(String propertyName, String filteringOperator, String appliesToEntityTypeName) {
        super(appliesToEntityTypeName + ": " + propertyName + " " + filteringOperator, "", appliesToEntityTypeName, "boolean");
        initializeMembers(propertyName, filteringOperator, null);
    }


    public FilteringRule(String propertyName, String filteringOperator, String appliesToEntityTypeName, Object threshold) {

        super(appliesToEntityTypeName + ": " + propertyName + " " + filteringOperator + " " + threshold, "", appliesToEntityTypeName, "boolean");

        initializeMembers(propertyName, filteringOperator, threshold);

    }


    public FilteringRule(String propertyName, String filteringOperator, String appliesToEntityTypeName, double threshold) {

        super(appliesToEntityTypeName + ": " + propertyName + " " + filteringOperator + " " + threshold, "", appliesToEntityTypeName, "boolean");

        initializeMembers(propertyName, filteringOperator, new Double(threshold));

    }


    public String createNameForFilteredGroup(GroupEntity groupEntity) {

        return getDescriptorObject().getName() + " filter on (" + groupEntity.getName() + ")";

    }


    public ResultEntity compute(AbstractEntityInterface anEntity) {
        return new ResultEntity(applyFilter(anEntity));
    }

    public boolean applyFilter(AbstractEntityInterface anEntity) {
    	if(anEntity == null) return true;
        ResultEntity theResult = anEntity.getProperty(propertyDescriptor);
        if (theResult == null) return true;
        return theResult.applyFilter(filteringOperator, threshold);
    }


    public String toString() {
        if (this.getDescriptorObject().getName().indexOf("<html>") == -1)
            return this.getDescriptorObject().getName();
        else {
            return "<html>" + this.getDescriptorObject().getName();
        }
    }


    private void initializeMembers(String propertyName, String filteringOperator, Object threshold) {

        this.propertyDescriptor = propertyName;

        this.filteringOperator = filteringOperator;

        this.threshold = threshold;

    }

    public String[] getIntersectionofEntityTypeNames(FilteringRule otherRule) {
        String[] thisETNames = this.getDescriptorObject().getAllEntityTypeNames();
        String[] otherETNames = this.getDescriptorObject().getAllEntityTypeNames();
        ArrayList<String> thisETNamesArray = new ArrayList<String>();
        ArrayList<String> otherETNamesArray = new ArrayList<String>();

        for (int i = 0; i < thisETNames.length; i++) thisETNamesArray.add(thisETNames[i]);
        for (int i = 0; i < otherETNames.length; i++) otherETNamesArray.add(otherETNames[i]);

        thisETNamesArray.retainAll(otherETNamesArray);

        if (thisETNamesArray.size() == 0) return new String[]{""};
        return thisETNamesArray.toArray(new String[thisETNamesArray.size()]);
    }

    public String[] getUnionofEntityTypeNames(FilteringRule otherRule) {
        String[] thisETNames = this.getDescriptorObject().getAllEntityTypeNames();
        String[] otherETNames = this.getDescriptorObject().getAllEntityTypeNames();
        HashSet<String> allDistinctETNames = new HashSet<String>();

        for (int i = 0; i < thisETNames.length; i++) allDistinctETNames.add(thisETNames[i]);
        for (int i = 0; i < otherETNames.length; i++) allDistinctETNames.add(otherETNames[i]);

        if (allDistinctETNames.size() == 0) return new String[]{""};
        return allDistinctETNames.toArray(new String[allDistinctETNames.size()]);
    }

}
