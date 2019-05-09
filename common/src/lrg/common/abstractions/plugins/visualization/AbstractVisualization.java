package lrg.common.abstractions.plugins.visualization;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.plugins.AbstractPlugin;
import lrg.common.abstractions.plugins.Descriptor;

public abstract class AbstractVisualization extends AbstractPlugin {

    public AbstractVisualization(String name, String description, String entity) {
        super(name,description,entity);
    }

    public AbstractVisualization(String name, String description, String[] entities) {
        super(new Descriptor(name,description,entities));
    }

    public abstract void view(AbstractEntityInterface entity);
    
}
