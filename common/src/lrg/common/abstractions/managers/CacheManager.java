package lrg.common.abstractions.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CacheManager {
    private static String staticEntityTypes = ".cache\\staticEntityTypes.cache";
    private static String dynamicEntityTypes = ".cache\\dynamicEntityTypes.cache";
    private static ObjectOutputStream serout = null;

    private CacheManager() {}

    public static ObjectInputStream readStaticETStream() throws IOException {
        return new ObjectInputStream(new FileInputStream(staticEntityTypes));
    }

    public static ObjectInputStream readDynamicETStream() throws IOException {
        return new ObjectInputStream(new FileInputStream(dynamicEntityTypes));
    }

    public static ObjectOutputStream writeStaticETStream() throws IOException {
         return new ObjectOutputStream(new FileOutputStream(staticEntityTypes));
    }

    public static void writeDynamicETStream(Object anObject) {
        try {
            serout = new ObjectOutputStream(new FileOutputStream(dynamicEntityTypes, true));
            serout.writeObject(anObject);
            serout.close();
        } catch (IOException e) {
            java.lang.System.err.println("ERROR: Unable to write to cache !");
            e.printStackTrace();
        }
    }

/*
    public static void deleteStaticETCache() {
        new File(staticEntityTypes).delete();
    }

    public static void deleteDynamicETCache() {
        new File(dynamicEntityTypes).delete();
    }

    public static boolean existsStaticETCache() {
        return new File(staticEntityTypes).exists();
    }
*/
    public static File getStaticETCache() {
        return new File(staticEntityTypes);
    }

    public static File getDynamicETCache() {
        return new File(dynamicEntityTypes);
    }

}
