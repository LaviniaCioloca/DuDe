package lrg.common.abstractions.plugins.groups;

import java.util.ArrayList;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;


public abstract class GroupEntityBuilder extends GroupBuilder {
    public GroupEntityBuilder(String name, String description, String entityType) {
        super(name, description, entityType);
    }


    public GroupEntityBuilder(String name, String description, String[] entityTypes) {
        super(name, description, entityTypes);
    }

    public ArrayList buildGroup(AbstractEntityInterface anEntity) {
        return buildGroupEntity(anEntity).getElements();
    }

    public abstract GroupEntity buildGroupEntity(AbstractEntityInterface anEntity);
}
