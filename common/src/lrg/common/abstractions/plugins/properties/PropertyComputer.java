package lrg.common.abstractions.plugins.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.plugins.AbstractPlugin;
import lrg.common.abstractions.plugins.Descriptor;
import lrg.common.abstractions.plugins.groups.GroupBuilder;
import lrg.common.abstractions.plugins.groups.GroupEntityBuilder;

public class PropertyComputer extends AbstractPlugin {
    public PropertyComputer(String name, String longName, String entityType, String resultEntityTypeName) {
        super(name, longName, entityType);
        this.resultEntityTypeName = resultEntityTypeName;
    }

    public PropertyComputer(String name, String longName, String[] entityTypes, String resultEntityTypeName) {
        super(new Descriptor(name, longName, entityTypes));
        this.resultEntityTypeName = resultEntityTypeName;
    }

    public ResultEntity compute(AbstractEntityInterface anEntity) {
        double theResult = 0.0;
        int crt = 0;
        Collection<GroupBuilder> allGroups = usedGroups.values();

        for (GroupBuilder crtGRoup : allGroups) {
            if (isDistinctList.get(crt++))
                theResult += getGroup(crtGRoup.getDescriptorObject().getName(), anEntity).distinct().size();
            else
                theResult += getGroup(crtGRoup.getDescriptorObject().getName(), anEntity).size();
        }

        return new ResultEntity(theResult);
    }

    public String getResultEntityTypeName() {
        return resultEntityTypeName;
    }


    protected void basedOnGroup(GroupBuilder aGroupBuilder) {
        usedGroups.put(aGroupBuilder.getDescriptorObject().getName(), aGroupBuilder);
        isDistinctList.add(new Boolean(false));
    }

    protected void basedOnDistinctGroup(GroupBuilder aGroupBuilder) {
        usedGroups.put(aGroupBuilder.getDescriptorObject().getName(), aGroupBuilder);
        isDistinctList.add(new Boolean(true));
    }


    public ArrayList getListOfGroupNames() {
        return (ArrayList) usedGroups.keySet();
    }

    public GroupEntity getGroup(String groupName, AbstractEntityInterface anEntity) {
        GroupBuilder groupBuilder = (GroupBuilder) usedGroups.get(groupName);
        if (groupBuilder instanceof GroupEntityBuilder)
            return ((GroupEntityBuilder) groupBuilder).buildGroupEntity(anEntity);

        return groupBuilder.buildGroupEntity(anEntity);
    }

    public int sizeOf(String groupName, AbstractEntityInterface anEntity) {
        return getGroup(groupName, anEntity).size();
    }

    private String resultEntityTypeName;
    private HashMap usedGroups = new HashMap();
    private ArrayList<Boolean> isDistinctList = new ArrayList();
}


