package lrg.common.abstractions.plugins.operators;

import lrg.common.abstractions.entities.ResultEntity;

public abstract class FilteringOperatorWithThresholds extends FilteringOperator
{
    public FilteringOperatorWithThresholds(String name, String entityTypeName)
    {
        super(name, entityTypeName);
    }

    public abstract boolean apply(ResultEntity theResult, Object threshold);

    public boolean apply(ResultEntity theResult)
    {
        return false;
    }
}
