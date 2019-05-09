package lrg.common.abstractions.plugins.operators;

import java.util.ArrayList;

import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.plugins.AbstractPlugin;
import lrg.common.abstractions.plugins.Descriptor;

public abstract class AggregationOperator extends AbstractPlugin {
    public AggregationOperator(String name) {
        super(new Descriptor(name, "numerical"));
    }

    public AggregationOperator(String name, String typeName) {
        super(new Descriptor(name, typeName));
    }

    public abstract ResultEntity aggregate(ArrayList resultGroup);

}
