package lrg.memoria.hismo.core;

import lrg.common.utils.ProgressObserver;
import lrg.memoria.importer.CacheModelLoader;
import lrg.memoria.importer.mcc.TablesLoader;
import lrg.memoria.importer.recoder.JavaModelLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HismoLoader {
    public static int JAVA_SOURCES = 1;
    public static int CPP_SOURCES = 2;
    public static int CACHE = 3;

    private SystemHistory systemHistory;
    protected ProgressObserver loadingProgressObserver;
    private CacheModelLoader model;
    private int kindOfSources;
    private String name;

    public HismoLoader(File sourceDirs, File cacheDirs, String name, ProgressObserver po, int kindOfSources) throws IOException {
        this.name = name;
        this.loadingProgressObserver = po;
        this.kindOfSources = kindOfSources;
        if (!sourceDirs.isDirectory() || (cacheDirs != null && !cacheDirs.isDirectory())) {
            System.out.println("Source dir or Cache dir is not a directory !");
            return;
        } else {
            File versions[] = sourceDirs.listFiles();
            File caches[] = cacheDirs.listFiles();
            ArrayList nameList = new ArrayList();
            ArrayList pathList = new ArrayList();
            ArrayList cacheList = new ArrayList();
            for (int i = 0; i < versions.length; i++) {
                nameList.add(versions[i].getCanonicalPath());
                pathList.add(versions[i].getAbsolutePath());
                if (i > caches.length || caches[i] == null)
                    cacheList.add(versions[i].getCanonicalPath() + ".dat");
                else
                    cacheList.add(caches[i].getAbsolutePath());
            }
            loadHismo(nameList, pathList, cacheList);
        }
    }

    public HismoLoader(File cacheDirs, String name, ProgressObserver po) throws IOException {
        this.name = name;
        this.loadingProgressObserver = po;
        this.kindOfSources = CACHE;
        if (!cacheDirs.isDirectory()) {
            System.out.println("cacheDir is not a directory !");
            return;
        } else {
            File caches[] = cacheDirs.listFiles();
            ArrayList nameList = new ArrayList();
            ArrayList cacheList = new ArrayList();
            for (int i = 0; i < caches.length; i++) {
                nameList.add(caches[i].getName().substring(0, caches[i].getName().length() - 4));
                cacheList.add(caches[i].getAbsolutePath());
            }
            loadHismoFromCache(cacheList, nameList);
        }
    }

    public HismoLoader(ArrayList nameList, ArrayList pathList, ArrayList cachesList, String name, ProgressObserver po, int kindOfSources) {
        loadingProgressObserver = po;
        this.kindOfSources = kindOfSources;
        this.name = name;
        loadHismo(pathList, cachesList, nameList);
    }

    private void loadHismoFromCache(ArrayList cachesList, ArrayList nameList) {
        int versionsNumber = cachesList.size();
        if (loadingProgressObserver != null)
            loadingProgressObserver.setMaxValue(versionsNumber);
        try {
            systemHistory = new SystemHistory(name);
            SystemVersion systemVersion;

            for (int i = 0; i < versionsNumber; i++) {
                String currentCachePath = (String) cachesList.get(i);
                model = new CacheModelLoader();
                model.loadModel(currentCachePath);

                String versionName = (String) nameList.get(i);

                systemVersion = new SystemVersion(versionName, model.getSystem());
                systemHistory.addVersion(systemVersion);

                if (loadingProgressObserver != null)
                    loadingProgressObserver.increment();
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    private void loadHismo(ArrayList pathList, ArrayList cachesList, ArrayList nameList) {
        int versionsNumber = pathList.size();
        if (loadingProgressObserver != null)
            loadingProgressObserver.setMaxValue(versionsNumber);
        try {
            systemHistory = new SystemHistory(name);
            SystemVersion systemVersion;

            for (int i = 0; i < versionsNumber; i++) {
                String currentSourcePath = (String) pathList.get(i);
                String sourceFullPath = new File(currentSourcePath).getAbsolutePath();
                String currentCachePath = (String) cachesList.get(i);
                String cacheFullPath;
                if (currentCachePath != null)
                    cacheFullPath = new File(currentCachePath).getAbsolutePath();
                else
                    cacheFullPath = null;
                System.out.println("Loading the model from source path: " + sourceFullPath + " or from cache: " + cacheFullPath);
                if (kindOfSources == JAVA_SOURCES)
                    model = new JavaModelLoader(sourceFullPath, cacheFullPath, null);
                if (kindOfSources == CPP_SOURCES)
                    model = new TablesLoader(null, sourceFullPath, cacheFullPath);

                String versionName = (String) nameList.get(i);

                systemVersion = new SystemVersion(versionName, model.getSystem());
                systemHistory.addVersion(systemVersion);

                if (loadingProgressObserver != null)
                    loadingProgressObserver.increment();
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    public SystemHistory getSystemHistory() {
        return systemHistory;
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: javac HismoLoader caches_dir_path");
        }

        HismoLoader hl = new HismoLoader(new File(args[0]), "try", null);

    }
}
