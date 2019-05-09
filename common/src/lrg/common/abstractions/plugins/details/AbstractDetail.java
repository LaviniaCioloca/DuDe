package lrg.common.abstractions.plugins.details;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.plugins.AbstractPlugin;
import lrg.common.abstractions.plugins.Descriptor;

public abstract class AbstractDetail extends AbstractPlugin {
    public AbstractDetail(String name, String longName, String entity) {
        super(name, longName, entity);
    }

    public AbstractDetail(String name, String longName, String[] entityTypes) {
        super(new Descriptor(name, longName, entityTypes));
    }

    public abstract ResultEntity compute(AbstractEntityInterface anEntity);
}
