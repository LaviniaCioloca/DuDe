package lrg.memoria.hismo.core;

import lrg.common.abstractions.entities.AbstractEntity;
import lrg.memoria.core.NamedModelElement;
import lrg.memoria.core.Statute;

import java.util.Iterator;

public abstract class AbstractHistory extends AbstractEntity {

    protected VersionsList versions;

    public AbstractHistory() {
        versions = new VersionsList();
    }

    public AbstractHistory(VersionsList versions) {
        this.versions = new VersionsList();
        initializeInnerHistories();
        for (Iterator it = versions.iterator(); it.hasNext();)
            addVersion((AbstractVersion) it.next());
    }

    public int getNumberOfVersions() {
        return versions.size();
    }

    public Iterator getVersionIterator() {
        return versions.iterator();
    }

    public AbstractVersion getVersionForName(String name) {
        AbstractVersion currentVersion;
        for (Iterator it = getVersions().iterator(); it.hasNext();) {
            currentVersion = (AbstractVersion) it.next();
            if (currentVersion.versionName().equals(name))
                return currentVersion;
        }
        return null;
    }

    public AbstractVersion getFirstVersion() {
        return versions.firstVersion();
    }

    public AbstractVersion getLastVersion() {
        return versions.lastVersion();
    }

    public VersionsList getVersions() {
        return versions;
    }

    public boolean equals(AbstractHistory aHistory) {
        return (getFullName().equals(aHistory.getFullName())) &&
                versions.equals(aHistory.versions);

    }

    public String getName() {
        AbstractVersion firstVersion = (AbstractVersion) versions.firstVersion();
        return firstVersion.getName();
    }

    public String getFullName() {
        AbstractVersion firstVersion = (AbstractVersion) versions.firstVersion();
        return firstVersion.getFullName();
    }

    public void addVersion(AbstractVersion version) {
        versions.add(version);
        updateInnerHistories(version);
    }

    /**
     * @return true only if all versions belong to the library
     */
    public boolean isLibrary() {
        for (Iterator<AbstractVersion> it = getVersions().iterator(); it.hasNext();) {
            if (((NamedModelElement) it.next()).getStatute() == Statute.NORMAL)
                return false;
        }
        return true;
    }

    protected abstract void initializeInnerHistories();

    protected abstract void updateInnerHistories(AbstractVersion version);
}
