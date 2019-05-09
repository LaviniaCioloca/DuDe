package lrg.common.metamodel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.managers.CacheManager;
import lrg.common.abstractions.managers.EntityTypeManager;
import lrg.common.abstractions.plugins.AbstractPlugin;

public abstract class ModelBuilder {
    protected HashMap<String, AbstractEntityInterface> addressMap;
	protected String cachePath;


    public ModelBuilder() {
        addressMap = new HashMap<String, AbstractEntityInterface>();
    }

    public abstract void buildModel() throws Exception;

    public abstract void cleanModel();

    public HashMap<String, AbstractEntityInterface> getAddressMap() {
        return addressMap;
    }

    protected abstract void registerEntityTypes();

    protected void createEntityTypes() {
        if (EntityTypeManager.loadFromCache()) {
            java.lang.System.out.println("Load EntityTypes from Cache");
        } else {
            registerEntityTypes();
            attachOutsiders();
        }
        attachPlugins();
    }

    private void attachPlugins() {
        if (CacheManager.getDynamicETCache().exists()) {
            try {
                ObjectInputStream serin = CacheManager.readDynamicETStream();
                AbstractPlugin aPlugin;

                while (true) {
                    aPlugin = (AbstractPlugin) serin.readObject();
                    java.lang.System.out.println("SERIAL: " + aPlugin.getDescriptorObject().getName());
                    EntityTypeManager.attach(aPlugin);
                }

            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
                java.lang.System.out.println("AICI:" + e.toString());
            }
        }
    }

    private void attachOutsiders() {
        Loader loader = new Loader("classes");
        Iterator it = loader.getNames().iterator();
        int counter = 0;

        System.out.print("Loading the plugins...\n");
        while (it.hasNext()) {
            AbstractPlugin someCommand = loader.buildFrom((String) it.next());
            if (someCommand != null) {
                counter++;
                EntityTypeManager.attach(someCommand);
            }
        }
        System.out.println(counter + " plugins successfully loaded!\n");
    }
}