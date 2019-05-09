package lrg.common.abstractions.managers;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import lrg.common.abstractions.entities.EntityType;
import lrg.common.abstractions.plugins.AbstractPlugin;
import lrg.common.abstractions.plugins.filters.FilteringRule;

public class EntityTypeManager {
    private static HashMap entityTypes = new HashMap();

    private static EntityType findEntityType(String entityTypeName) {
        return (EntityType) entityTypes.get(entityTypeName);
    }

    public static ArrayList getAllPropertiesForName(String entityTypeName) {
        return findEntityType(entityTypeName).nameAllPropertyComputers();
    }

    public static ArrayList getAllGroupsForName(String entityTypeName) {
        return findEntityType(entityTypeName).nameAllGroupBuilders();
    }

    public static ArrayList getAllFiltersForName(String entityTypeName) {
        return findEntityType(entityTypeName).nameAllFilteringRules();
    }

    public static EntityType getEntityTypeForName(String entityTypeName) {
        EntityType theEntityType = findEntityType(entityTypeName);
        if (theEntityType == null) {
            theEntityType = new EntityType(entityTypeName);
            entityTypes.put(entityTypeName, theEntityType);
        }
        return theEntityType;
    }

    public static Collection getAllTypes() {
        return entityTypes.values();
    }

    public static ArrayList getAllSubtypesForName(String entityTypeName) {
        ArrayList subTypes = new ArrayList();

        Iterator it = entityTypes.values().iterator();
        EntityType entityType;
        while (it.hasNext()) {
            entityType = (EntityType) it.next();
            if (entityType.getSupertypeName().compareTo(entityTypeName) == 0)
                subTypes.add(entityType);
        }
        return subTypes;
    }

    public static void registerEntityType(String entityTypeName, String supertypeName) {
        EntityType newEntityType = new EntityType(entityTypeName, supertypeName);
        entityTypes.put(entityTypeName, newEntityType);
    }

    public static void attach(AbstractPlugin someCommand) {
        String[] entityTypeNames = someCommand.getDescriptorObject().getAllEntityTypeNames();
        EntityType anEntityType;

        for (int i = 0; i < entityTypeNames.length; i++) {
            anEntityType = getEntityTypeForName(entityTypeNames[i]);
            anEntityType.attach(someCommand);
        }
    }

    public static void attach(AbstractPlugin someCommand, String entityTypeName) {
        EntityType anEntityType = getEntityTypeForName(entityTypeName);
        anEntityType.attach(someCommand);
    }

    public static void unAttach(FilteringRule aRule) {
        EntityType anEntityType = getEntityTypeForName(aRule.getDescriptorObject().getEntityTypeName());
        anEntityType.unAttach(aRule);
    }

    public static boolean loadFromCache() {
        if (CacheManager.getStaticETCache().exists() == false) return false;
        try {
            ObjectInputStream serin = CacheManager.readStaticETStream();
            entityTypes = (HashMap) serin.readObject();
            serin.close();
            return true;
        } catch (IOException e) {
            java.lang.System.err.println("ERROR: Unable to load from cache !");
            return false;
        } catch (ClassNotFoundException e) {
            java.lang.System.err.println("ERROR: Unable to load from cache !");
            return false;
        }
    }

    public static void writeStaticEntityTypesToCache() {
        try {
            ObjectOutputStream serout = CacheManager.writeStaticETStream();
            serout.writeObject(entityTypes);
            serout.close();
        } catch (IOException e) {
            java.lang.System.err.println("ERROR: Unable to write to cache !");
            e.printStackTrace();
        }
    }
}