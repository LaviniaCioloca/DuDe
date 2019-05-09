package lrg.common.abstractions.plugins;

import java.io.Serializable;

public class Descriptor  implements Serializable
{
    private String name;   // name visibile by user from UI
    private String info;  // A little bit of info
    private String[] entityTypeName;


    public Descriptor(String name, String info, String entityTypeName)
    {
        this.name = name;
        this.info = info;
        this.entityTypeName = new String[1];
        this.entityTypeName[0] = entityTypeName;
    }

    public Descriptor(String name, String info, String[] listOfEntityTypeNames)
    {
        this.name = name;
        this.info = info;
        this.entityTypeName = listOfEntityTypeNames;
    }

    public Descriptor(String name, String entity)
    {
        this(name, "", entity);
    }


    public Descriptor(String name, String[] listOfEntityTypeNames)
    {
        this(name, "", listOfEntityTypeNames);
    }

    public String getName()
    {
        return name;
    }

    public String getInfo()
    {
        return info;
    }


    public String getEntityTypeName()
    {
        return entityTypeName[0];
    }

    public String[] getAllEntityTypeNames() {
        return entityTypeName;
    }

    public void setName(String newName)
    {
        name = newName;
    }
}

