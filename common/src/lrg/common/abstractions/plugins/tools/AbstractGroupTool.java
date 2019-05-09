package lrg.common.abstractions.plugins.tools;

import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.plugins.AbstractPlugin;
import lrg.common.abstractions.plugins.Descriptor;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 12.05.2004
 * Time: 11:20:34
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractGroupTool extends AbstractPlugin
{
    public AbstractGroupTool(String name, String description, String entity)
    {
        super(name, description, entity);
    }
    
    public AbstractGroupTool(String name, String description, String[] entities) {
        super(new Descriptor(name,description,entities));
    }


    public abstract void run(GroupEntity aGroupEntity, Object theToolParameters);
}
