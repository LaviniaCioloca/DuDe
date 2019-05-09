package lrg.memoria.hismo.core;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class VersionsList {

    private TreeSet<AbstractVersion> list;

    public VersionsList() {
        list = new TreeSet<AbstractVersion>();
    }

    public boolean equals(VersionsList aVersionList) {
        return list.equals(aVersionList.list);
    }

    public void add(AbstractVersion version) {
        list.add(version);
    }

    public AbstractVersion firstVersion() {
        return (AbstractVersion) list.first();
    }

    public AbstractVersion lastVersion() {
        return (AbstractVersion) list.last();
    }

    public SortedSet getVersionNames() {
        SortedSet names = new TreeSet();
        for (Iterator it = list.iterator(); it.hasNext();)
            names.add(((AbstractVersion) it.next()).versionName());
        return names;
    }

    public Iterator<AbstractVersion> iterator() {
        return list.iterator();
    }

    public int size() {
        return list.size();
    }

    public void addAll(VersionsList versions) {
        list.addAll(versions.list);
    }

    public boolean contains(AbstractVersion version) {
        return list.contains(version);
    }
}
