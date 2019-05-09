package lrg.common.abstractions.plugins;

import java.io.Serializable;

public class AbstractPlugin  implements Serializable
{
    private Descriptor theDescriptor;

    public AbstractPlugin(Descriptor aDescriptor)
    {
        theDescriptor = aDescriptor;
    }

    public AbstractPlugin(String name, String description, String entity)
    {
        this(new Descriptor(name, description, entity));
    }

    public AbstractPlugin(String name)
    {
        this(new Descriptor(name, "", ""));
    }

    public Descriptor getDescriptorObject()
    {
        return theDescriptor;
    }
}
