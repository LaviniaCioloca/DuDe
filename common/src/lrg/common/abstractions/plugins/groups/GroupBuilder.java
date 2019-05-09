package lrg.common.abstractions.plugins.groups;

import java.util.ArrayList;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.plugins.AbstractPlugin;
import lrg.common.abstractions.plugins.Descriptor;

public abstract class GroupBuilder extends AbstractPlugin {
    public GroupBuilder(String name, String description, String entityType) {
        super(name, description, entityType);
    }


    public GroupBuilder(String name, String description, String[] entityTypes) {
        super(new Descriptor(name, description, entityTypes));
    }

    public abstract ArrayList buildGroup(AbstractEntityInterface anEntity);

    public GroupEntity buildGroupEntity(AbstractEntityInterface anEntity) {
        String aName = getDescriptorObject().getName() + " for " + anEntity.getName();
        return new GroupEntity(aName, buildGroup(anEntity));
    }

}


