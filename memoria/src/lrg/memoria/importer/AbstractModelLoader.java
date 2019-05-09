package lrg.memoria.importer;

import lrg.common.utils.ProgressObserver;
import lrg.memoria.core.Namespace;
import lrg.memoria.core.Package;
import lrg.memoria.core.System;

import java.io.File;

public abstract class AbstractModelLoader extends CacheModelLoader {
    protected ProgressObserver loadingProgressObserver;

    protected AbstractModelLoader(ProgressObserver observer) {
        loadingProgressObserver = observer;
    }

    protected void resolveParticularNamespacesAndPackages() {
        Package anonymousPackage = Package.getAnonymousPackage();
        Package unknownPackage = Package.getUnknownPackage();
        anonymousPackage.setSystem(system);
        unknownPackage.setSystem(system);
        system.addPackage(unknownPackage);
        system.addPackage(anonymousPackage);
        Namespace anonymousNamespace = Namespace.getAnonymousNamespace();
        Namespace globalNamespace = Namespace.getGlobalNamespace();
        Namespace unknownNamespace = Namespace.getUnknownNamespace();
        anonymousNamespace.setSystem(system);
        system.addNamespace(anonymousNamespace);
        globalNamespace.setSystem(system);
        system.addNamespace(globalNamespace);
        unknownNamespace.setSystem(system);
        system.addNamespace(unknownNamespace);
    }

    public System loadModel(String sourcePathList, String cachePath) throws Exception {
        return loadModelFromCacheOrFromSources(cachePath, sourcePathList);
    }

    protected System loadModelFromCacheOrFromSources(String cachePath, String sourcePathList) throws Exception {
        File serialized = getCacheFile(sourcePathList, cachePath);
        if (!serialized.exists()) {
            java.lang.System.out.println("Loading the model from source files ... ");
            java.lang.System.out.println("The source path list is: " + sourcePathList);
            loadModelFromSources(sourcePathList);
            resolveParticularNamespacesAndPackages();
            System.serializeToFile(serialized, system);
        } else {
            loadModelFromCache(serialized);
        }
        return system;
    }

    protected abstract void loadModelFromSources(String pathList) throws Exception;

    private File getCacheFile(String sourcePathList, String cachePath) {
        File serialized;
        if (cachePath == null) {
            String noPathSeparator = sourcePathList.replaceAll(File.pathSeparator, "__");
            String normalized = noPathSeparator.replace(File.separator.charAt(0), '_');
            normalized = normalized.replace(':', '#');
            File f = new File("temp" + File.separator + "cache" + File.separator + sourcePathList.hashCode());
            f.mkdirs();
            serialized = new File(f.toString() + File.separator + "cached_model_from_" + normalized + ".dat");
        } else {
            serialized = new File(cachePath);
        }
        return serialized;
    }
}
