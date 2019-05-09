package lrg.common.abstractions.entities;


import java.text.DecimalFormat;
import java.util.ArrayList;

import lrg.common.abstractions.managers.EntityTypeManager;
import lrg.common.abstractions.plugins.operators.AggregationOperator;
import lrg.common.abstractions.plugins.operators.FilteringOperator;
import lrg.common.abstractions.plugins.operators.FilteringOperatorWithThresholds;


public class ResultEntity extends AbstractEntity {
    public ResultEntity(Object aValue) {
        super(EntityTypeManager.getEntityTypeForName("entity"));
        theValue = aValue;
    }

    public ResultEntity(String aValue) {
        super(EntityTypeManager.getEntityTypeForName("string"));
        theValue = aValue;
    }

    public ResultEntity(double aValue) {
        super(EntityTypeManager.getEntityTypeForName("numerical"));
        theValue = new Double(aValue);
    }


    public ResultEntity(boolean aValue) {
        super(EntityTypeManager.getEntityTypeForName("boolean"));
        theValue = new Boolean(aValue);
    }

    public boolean applyFilter(String filteringOperator, Object threshold) {
        FilteringOperator theOperator = (FilteringOperator) theType.findFilteringOperator(filteringOperator);
        if (theOperator == null) return false;

        if (theOperator instanceof FilteringOperatorWithThresholds)
            return ((FilteringOperatorWithThresholds) theOperator).apply(this, threshold);

        return theOperator.apply(this);
    }

    public Object getValue() {
        return theValue;
    }

    public String toString() {
        DecimalFormat twoDecimals = new DecimalFormat("#0.00");
        if(theValue instanceof Double) {
            String sValue = twoDecimals.format(theValue);
            if(sValue.endsWith(",00") || sValue.endsWith(".00")) return ""+((Double)theValue).intValue();
            else return sValue;
        }
        return ""+theValue;
    }

    public int compareTo(ResultEntity aResultEntity) {
        if (aResultEntity == null) return 1;
        if ((theValue instanceof String) && (aResultEntity.theValue instanceof String)) {
            String s1 = (String) theValue;
            String s2 = (String) aResultEntity.theValue;
            return s1.toLowerCase().compareTo(s2.toLowerCase());
        }

        if ((theValue instanceof ArrayList) && (aResultEntity.theValue instanceof ArrayList)) {
            String s1 = ((ArrayList) theValue).get(0).toString();
            String s2 = ((ArrayList) aResultEntity.theValue).get(0).toString() ;
            return s1.toLowerCase().compareTo(s2.toLowerCase());
        }
        
        if ((theValue instanceof Double) && (aResultEntity.theValue instanceof Double)) {
            double d1 = ((Double) theValue).doubleValue();
            double d2 = ((Double) aResultEntity.theValue).doubleValue();
            if (d1 < d2) return -1;
            if (d1 > d2) return 1;
            return 0;
        }

        if ((theValue instanceof Boolean) && (aResultEntity.theValue instanceof Boolean)) {
            boolean b1 = ((Boolean) theValue).booleanValue();
            boolean b2 = ((Boolean) aResultEntity.theValue).booleanValue();
            if ((b1 && b2) || ((!b1) && (!b2))) return 0;
            if (b1 && (!b2)) return -1;

            return 1;
        }


        
        return compareTo(new ResultEntity(theValue.toString()));
    }

    public boolean isCollectionResult() {
        return theValue instanceof ArrayList;
    }

    public ArrayList getValueAsCollection() {
        if (isCollectionResult()) return (ArrayList) theValue;
        return null;
    }

    public ResultEntity aggregate(String aggregatorName) {
        ArrayList groupOfResults = getValueAsCollection();
        if (groupOfResults == null) return this;

        if (groupOfResults.isEmpty()) return new ResultEntity(0);


        if ((groupOfResults.get(0) instanceof ResultEntity) == false) return null;
        if (aggregatorName.compareTo("size") == 0) return new ResultEntity(groupOfResults.size());

        ResultEntity firstResult = (ResultEntity) groupOfResults.get(0);
        EntityType typeOfElements = firstResult.getEntityType();
        if (typeOfElements == null) return null;

        AggregationOperator anAggregator = (AggregationOperator) typeOfElements.findFilteringRule(aggregatorName);
        if (anAggregator == null) return null;

        return anAggregator.aggregate(groupOfResults);
    }


    private Object theValue;
} 