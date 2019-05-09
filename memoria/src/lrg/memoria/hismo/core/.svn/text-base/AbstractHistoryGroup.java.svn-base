package lrg.memoria.hismo.core;

import lrg.memoria.hismo.utils.AbstractSelectionCondition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

public abstract class AbstractHistoryGroup {

    protected Hashtable<String, AbstractHistory> histories;

    public AbstractHistoryGroup() {
        histories = new Hashtable<String, AbstractHistory>();
    }

    public void put(AbstractHistory history) {
        histories.put(history.getFullName(), history);
    }

    public void addAll(AbstractHistoryGroup aHistoryGroup) {
        AbstractHistory aHistory;
        Collection histories = aHistoryGroup.histories.values();
        Collection currentHistories = this.histories.values();
        for (Iterator it = histories.iterator(); it.hasNext();) {
            aHistory = (AbstractHistory) it.next();
            if (!currentHistories.contains(aHistory))
                this.histories.put(aHistory.getFullName(), aHistory);
        }
    }

    public AbstractHistoryGroup createNewHistoryGroupByAdding(AbstractHistoryGroup aHistoryGroup) {
        AbstractHistoryGroup newGroup = aHistoryGroup.createHistoryGroup();
        newGroup.addAll(this);
        newGroup.addAll(aHistoryGroup);
        return newGroup;
    }

    public int size() {
        return histories.size();
    }

    public Collection<AbstractHistory> getHistoriesCollection() {
        return histories.values();
    }

    public ArrayList<AbstractHistory> getHistoriesArrayList() {
        return new ArrayList(histories.values());
    }

    public boolean equals(AbstractHistoryGroup aHistoryGroup) {
        return histories.values().containsAll(aHistoryGroup.histories.values()) &&
                aHistoryGroup.histories.values().containsAll(histories.values());
    }

    public boolean includes(AbstractHistoryGroup aHistoryGroup) {
        return histories.values().containsAll(aHistoryGroup.histories.values());
    }

    public AbstractHistory get(String fullName) {
        return (AbstractHistory) histories.get(fullName);
    }

    public VersionsList getAllVersions() {
        VersionsList allVersions = new VersionsList();
        VersionsList currentVersions = new VersionsList();
        AbstractHistory currentHistory;
        for (Iterator it = histories.values().iterator(); it.hasNext();) {
            currentHistory = (AbstractHistory) it.next();
            currentVersions = currentHistory.getVersions();
            allVersions.addAll(currentVersions);
        }
        return allVersions;
    }

    public AbstractHistoryGroup selectGroupMatchingCondition(AbstractSelectionCondition cond) {
        AbstractHistoryGroup currentGroup = createHistoryGroup();
        AbstractHistory currentHistory;
        for (Iterator it = histories.values().iterator(); it.hasNext();) {
            currentHistory = (AbstractHistory) it.next();
            if (cond.isSatisfiedBy(currentHistory))
                currentGroup.put(currentHistory);
        }
        return currentGroup;
    }

    public AbstractHistory selectFirstHistoryMatchingCondition(AbstractSelectionCondition cond) {
        AbstractHistory currentHistory;
        for (Iterator it = histories.values().iterator(); it.hasNext();) {
            currentHistory = (AbstractHistory) it.next();
            if (cond.isSatisfiedBy(currentHistory))
                return currentHistory;
        }
        return null;
    }

    public AbstractHistoryGroup selectHistoriesBornInVersion(String name) {
        class BornInVersionSelectionCondition implements AbstractSelectionCondition {
            String version;

            public BornInVersionSelectionCondition(String versionName) {
                version = versionName;
            }

            public boolean isSatisfiedBy(AbstractHistory history) {
                AbstractVersion firstVersion = history.getFirstVersion();
                if (firstVersion != null && firstVersion.versionName().equals(version))
                    return true;
                return false;
            }
        }
        return selectGroupMatchingCondition(new BornInVersionSelectionCondition(name));
    }

    public AbstractHistoryGroup selectHistoriesBornAfterVersion(String name) {
        class BornAfterVersionSelectionCondition implements AbstractSelectionCondition {
            String version;

            public BornAfterVersionSelectionCondition(String versionName) {
                version = versionName;
            }

            public boolean isSatisfiedBy(AbstractHistory history) {
                AbstractVersion firstVersion = history.getFirstVersion();
                if (firstVersion != null && firstVersion.versionName().compareTo(version) >= 0)
                    return true;
                return false;
            }
        }
        return selectGroupMatchingCondition(new BornAfterVersionSelectionCondition(name));
    }

    public AbstractHistoryGroup selectHistoriesBornBeforeVersion(String name) {
        class BornBeforeVersionSelectionCondition implements AbstractSelectionCondition {
            String version;

            public BornBeforeVersionSelectionCondition(String versionName) {
                version = versionName;
            }

            public boolean isSatisfiedBy(AbstractHistory history) {
                AbstractVersion firstVersion = history.getFirstVersion();
                if (firstVersion != null && firstVersion.versionName().compareTo(version) < 0)
                    return true;
                return false;
            }
        }
        return selectGroupMatchingCondition(new BornBeforeVersionSelectionCondition(name));
    }

    public AbstractHistoryGroup selectSubHistoryForVersions(ArrayList versionNames) {
        AbstractHistoryGroup currentGroup = createHistoryGroup();
        AbstractHistory currentHistory, subHistory;
        VersionsList newVersionsList;
        AbstractVersion currentVersion;
        for (Iterator it = histories.values().iterator(); it.hasNext();) {
            currentHistory = (AbstractHistory) it.next();
            newVersionsList = new VersionsList();
            for (Iterator vit = currentHistory.getVersionIterator(); vit.hasNext();) {
                currentVersion = (AbstractVersion) vit.next();
                if (versionNames.contains(currentVersion.versionName())) {
                    newVersionsList.add(currentVersion);
                }
            }
            if (newVersionsList.size() > 0) {
                subHistory = createHistory(newVersionsList);
                currentGroup.put(subHistory);
            }
        }
        return currentGroup;
    }

    public abstract AbstractHistory createHistory(VersionsList abstractVersionsList);

    public abstract AbstractHistoryGroup createHistoryGroup();
}