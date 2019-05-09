package lrg.memoria.importer;

import lrg.memoria.core.System;
import lrg.memoria.utils.ModelLighter;

import java.io.File;

public class CacheModelLoader {
    protected System system;

    public lrg.memoria.core.System getSystem() {
        return system;
    }

    public System loadModel(String cacheFileName) {
        return loadModelFromCache(new File(cacheFileName));
    }

    protected System loadModelFromCache(java.io.File cacheFile) {
        java.lang.System.out.println("Loading the model from cache: " + cacheFile.toString());
        system = System.loadFromFile(cacheFile);
        return system;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            java.lang.System.out.println("Usage: java CacheModelLoader cacheFileName");
        }
        CacheModelLoader cml = new CacheModelLoader();
        System sys = cml.loadModel(args[0]);
        ModelLighter ml = new ModelLighter(sys);
        ml.lightenModel(2);
        System.serializeToFile(new File("/home/ratiud/tmp/rnim/rnim_2.dat"), sys);
        System.unloadSystemFromMemory(sys);
        sys = null;
        cml.loadModel("/home/ratiud/tmp/rnim/rnim_2.dat");
    }
}
