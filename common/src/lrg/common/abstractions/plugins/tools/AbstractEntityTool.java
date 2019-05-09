package lrg.common.abstractions.plugins.tools;

import java.util.ArrayList;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.plugins.AbstractPlugin;
import lrg.common.abstractions.plugins.Descriptor;

public abstract class AbstractEntityTool extends AbstractPlugin {
    public AbstractEntityTool(String name, String description, String entity) {
        super(name, description, entity);
    }

    public AbstractEntityTool(String name, String description, String[] entities) {
        super(new Descriptor(name,description,entities));
    }

    public abstract void run(AbstractEntityInterface anEntity, Object theToolParameters);

    public abstract String getToolName();

    public ArrayList<String> getParameterList() {
        return new ArrayList<String>();
    }

    public ArrayList<String> getParameterExplanations() {
        return new ArrayList<String>();
    }

    public ArrayList<String> getParameterInitialValue() {
        return new ArrayList<String>();
    }
}

