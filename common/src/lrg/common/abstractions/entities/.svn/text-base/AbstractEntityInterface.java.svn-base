package lrg.common.abstractions.entities;

import lrg.common.abstractions.plugins.tools.AbstractEntityTool;

public interface AbstractEntityInterface {
    String getName();

    EntityType getEntityType();

    void setEntityType(EntityType entityType);

    ResultEntity getProperty(String propertyDescriptor);

    void addProperty(String aDescriptor, ResultEntity aPropertyResult);

    GroupEntity getGroup(String groupIdentifier);

    void addGroup(String aDescriptor, GroupEntity aPropertyResult);

    AbstractEntityTool getTool(String toolIdentifier);

    GroupEntity uses(String groupIdentifier);

    GroupEntity isUsed(String groupIdentifier);

    GroupEntity contains(String groupIdentifier);

    AbstractEntity belongsTo(String scopeIdentifier);

    void putAnnotation(String addnotationName, Object addnotation);
    Object getAnnotation(String addnotationName);
}
