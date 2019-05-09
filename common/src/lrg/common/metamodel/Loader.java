package lrg.common.metamodel;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import lrg.common.abstractions.plugins.AbstractPlugin;

public class Loader {
    public Loader(String outsiderPath) {
        this.outsiderPath = outsiderPath;

        outsiderNames = new ArrayList();

        //collectOutsiderNamesFromPath(outsiderPath);
        collectOutsiderNamesFromPath(outsiderPath, "");
    }

    public ArrayList getNames() {
        return outsiderNames;
    }

    public AbstractPlugin buildFrom(String propertyClassName, String directory)
    {
        if(propertyClassName.contains(directory))
        {
            return buildFrom(propertyClassName);
        }
        return null;
    }
    
    public AbstractPlugin buildFrom(String propertyClassName) {
        if (propertyClassName.contains("plugins")) {
            File file = new File(outsiderPath);
            try {
                URL url = file.toURL();
                URL urls[] = new URL[]{url};
                ClassLoader classLoader = new URLClassLoader(urls);
                Class c = classLoader.loadClass(propertyClassName);
                AbstractPlugin aPlugin = (AbstractPlugin) c.newInstance();
                // System.out.println(propertyClassName + " plugin was loaded!");
                return aPlugin;
            } catch (Exception e) {
                //System.out.println("WARNING: " + propertyClassName + " is not derived from AbstractPlugin or does not have a no-arg constructor. (SKIPPING)");
                // System.err.println("ERROR: " + propertyClassName +" plugin was not loaded successfully !");
                // System.out.println(e);
                // e.printStackTrace();
                return null;
            } catch (Error e) {
                return null;
            }
        } else
            return null;
    }

    private void collectOutsiderNamesFromPath(String path, String pathToAdd) {
        File thisPath = new File(path);
        File[] files = thisPath.listFiles();

        if (files == null) return;
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().endsWith(".class") && files[i].isFile())
                outsiderNames.add(pathToAdd + files[i].getName().substring(0, files[i].getName().lastIndexOf(".")));

            if (files[i].isDirectory())
                collectOutsiderNamesFromPath(path + File.separator + files[i].getName(), pathToAdd + files[i].getName() + ".");
        }
    }

    private ArrayList outsiderNames;
    private String outsiderPath;
}