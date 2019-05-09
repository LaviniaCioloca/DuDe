package lrg.common.abstractions.plugins.operators;

import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.plugins.AbstractPlugin;
import lrg.common.abstractions.plugins.Descriptor;

public abstract class FilteringOperator extends AbstractPlugin {
    public FilteringOperator(String name, String entityTypeName) {
        super(new Descriptor(name, entityTypeName));
    }

    public abstract boolean apply(ResultEntity theResult);
}
