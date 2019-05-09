//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;


import lrg.memoria.utils.ModelLighter;

import java.io.*;

/**
 * This class is the "root" of the model. The main components of a system are the list of
 * namespaces and the list of packages.
 */
public class System extends NamedModelElement {
    private ModelElementList<Package> packages;
    private ModelElementList<Subsystem> subsystems;
    private ModelElementList<Namespace> namespaces;
    private ModelElementList<ModelElement> failedDepElements;
    private transient ModelElementList<Package> cachedNormalPackagesList;
    private transient ModelElementList<Namespace> cachedNormalNamespacesList;
    private transient Integer systemId;
    private int loadingLevel = ModelLighter.ENTIRE_MODEL;
    public String programmingLanguage = "Java";  

    public void setName(String newName) {
        name = newName;
    }

    protected System(System aSystem) {
        super(aSystem);
        packages = aSystem.packages;
        subsystems = aSystem.subsystems;
        namespaces = aSystem.namespaces;
        failedDepElements = aSystem.failedDepElements;
        systemId = aSystem.systemId;
    }

    /**
     * Construct an empty model with a given getName.
     */
    public System(String name) {
        super(name);
        packages = new ModelElementList<Package>();
        subsystems = new ModelElementList<Subsystem>();        
        namespaces = new ModelElementList<Namespace>();
        failedDepElements = new ModelElementList<ModelElement>();
        setStatute(Statute.NORMAL);
    }

    public void addSubsystem(Subsystem subsys) {
        if (!subsystems.contains(subsys)) {
        	subsystems.add(subsys);
        }
    }
    
    /**
     * Adds a package to the list of the packages contained in the current system.
     */

    public void addPackage(Package pack) {
        if (!packages.contains(pack)) {
            packages.add(pack);
            pack.setSystem(this);
        }
    }

    /**
     * Adds a namespace to the list of the namespaces contained in the current system.
     */
    public void addNamespace(Namespace namespace) {
        if (!namespaces.contains(namespace)) {
            namespaces.add(namespace);
            namespace.setSystem(this);
        }
    }

    /**
     * Adds a failed dep element.
     */
    public void addFailedDepElement(ModelElement elem) {
        failedDepElements.add(elem);
    }

    /**
     * Returns the list of failed dependencies.
     */
    public ModelElementList<ModelElement> getFailedDepElementList() {
        return failedDepElements;
    }

    /**
     * Sets the list of failed dependencies.
     */
    public void setFailedDepElementList(ModelElementList<ModelElement> fdl) {
        failedDepElements = fdl;
    }


    public ModelElementList<Subsystem> getSubsystems() {
        return subsystems;
    }

    public ModelElementList<Package> getPackages() {
        return packages;
    }

    public ModelElementList<Package> getNormalPackages() {
        if (cachedNormalPackagesList == null) {
            cachedNormalPackagesList = new ModelElementList<Package>();
            for (Package current : packages) {
                if (current.getStatute() == Statute.NORMAL)
                    cachedNormalPackagesList.add(current);
            }
        }
        return cachedNormalPackagesList;
    }

    public ModelElementList<Namespace> getNamespaces() {
        return namespaces;
    }

    public ModelElementList<Namespace> getNormalNamespaces() {
        if (cachedNormalNamespacesList == null) {
            cachedNormalNamespacesList = new ModelElementList<Namespace>();
            for (Namespace current : namespaces) {
                if (current.getStatute() == Statute.NORMAL)
                    cachedNormalNamespacesList.add(current);
            }
        }
        return cachedNormalNamespacesList;
    }

    public ModelElementList<UnnamedNamespace> getUnnamedNamespaces() {
        ModelElementList<UnnamedNamespace> unspl = new ModelElementList<UnnamedNamespace>();
        for (Namespace current : namespaces) {
            if (current instanceof UnnamedNamespace)
                unspl.add((UnnamedNamespace) current);
        }
        return unspl;
    }

    /**
     * Returns the unique ID of the system that identifies it when we are working
     * with multiple versions.
     */
    public Integer getSystemId() {
        return systemId;
    }

    /**
     * Sets the unique ID of the system that identifies it when we are working with
     * multiple versions.
     */
    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public void setLoadingLevel(int level) {
        loadingLevel = level;
    }

    public int getLoadingLevel() {
        return loadingLevel;
    }

    public void accept(ModelVisitor v) {
        v.visitSystem(this);
    }

    public boolean restore() {
        if (super.restore()) {
            packages.restore();
            namespaces.restore();
            failedDepElements.restore();
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuffer st = new StringBuffer("System:\n");
        for (Namespace current : namespaces)
            st.append(current);
        return new String(st);
    }

    public static void unloadSystemFromMemory(System currentSystem) {
        ModelElementsRepository.deleteModelElementsRepository(currentSystem.getSystemId());
    }

    public static void serializeToFile(java.io.File serialized, System system) {
        try {
            if (serialized.getParentFile() != null)
                serialized.getParentFile().mkdirs();
            serialized.createNewFile();
            ObjectOutputStream serout = new ObjectOutputStream(new FileOutputStream(serialized));
            ModelElementsRepository.setCurrentModelElementsRepository(system.getSystemId());
            serout.writeObject(ModelElementsRepository.getCurrentModelElementsRepository());
            serout.close();
        } catch (IOException e) {
            java.lang.System.err.println("ERROR: Unable to create the cache !");
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    public static System loadFromFile(java.io.File serialized) {
        System currentSystem;
        try {
            ObjectInputStream serin = new ObjectInputStream(new FileInputStream(serialized));
            ModelElementsRepository mer = (ModelElementsRepository) serin.readObject();
            int id = ModelElementsRepository.addNewModelElementsRepository(mer);
            serin.close();
            currentSystem = (System) ModelElementsRepository.getCurrentModelElementsRepository().byElementID(new Long(0));
            currentSystem.setSystemId(new Integer(id));
            currentSystem.restore();
            return currentSystem;
        } catch (IOException e) {
            java.lang.System.err.println("ERROR: Unable to load from cache !");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            java.lang.System.err.println("ERROR: Unable to load from cache !");
            e.printStackTrace();
        }
        return null;
    }
}
