package lrg.memoria.hismo.utils;

import lrg.memoria.hismo.core.AbstractHistory;
import lrg.memoria.hismo.core.AbstractHistoryGroup;
import lrg.memoria.hismo.core.AbstractVersion;
import lrg.memoria.hismo.core.VersionsList;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: ratiud
 * Date: Jan 31, 2004
 * Time: 11:29:20 AM
 * To change this template use Options | File Templates.
 */
public class EntitiesThatChangeTogether {

    private int minimumNumberOfVersions;
    private int minimumNumberOfEntities;
    private AbstractChangeProperty changeProperty;

    public EntitiesThatChangeTogether(int minimumNumberOfVersions, int minimumNumberOfEntities, AbstractChangeProperty changeProperty) {
        this.minimumNumberOfVersions = minimumNumberOfVersions;
        this.minimumNumberOfEntities = minimumNumberOfEntities;
        this.changeProperty = changeProperty;
    }

    public Set compute(AbstractHistoryGroup historyGroup) {
        Hashtable matrix = computeInitialMatrix(historyGroup);
        Hashtable newMatrix = new Hashtable();
        for (int i = 1; i < minimumNumberOfEntities; i++) {
            newMatrix = computeNextMatrix(matrix, i);
            matrix = newMatrix;
        }
        return matrix.keySet();
    }

    private Hashtable computeInitialMatrix(AbstractHistoryGroup historyGroup) {
        Hashtable initialMatrix = new Hashtable();
        Collection histories = historyGroup.getHistoriesCollection();
        VersionsList versions = historyGroup.getAllVersions();
        for (Iterator it = histories.iterator(); it.hasNext();) {
            AbstractHistory currentHistory = (AbstractHistory) it.next();
            Vector vectorOfChanges = new Vector();
            AbstractVersion currentVersion, previousVersion = null;
            int num = 0;
            for (Iterator vit = versions.iterator(); vit.hasNext();) {
                currentVersion = (AbstractVersion) vit.next();
                if (previousVersion != null) {
                    if (currentHistory.getVersions().contains(previousVersion) &&
                            currentHistory.getVersions().contains(currentVersion)) {
                        int val = changeProperty.getChangeValue(currentHistory.getVersionForName(previousVersion.versionName()),
                                currentHistory.getVersionForName(currentVersion.versionName()));
                        if (val != 0)
                            num++;
                        vectorOfChanges.add(new Integer(val));
                    } else {
                        vectorOfChanges.add(new Integer(0));
                    }
                }
                previousVersion = currentVersion;
            }
            if (num > 0) {
                AbstractHistoryGroup ahg = historyGroup.createHistoryGroup();
                ahg.put(currentHistory);
                initialMatrix.put(ahg, vectorOfChanges);
            }
        }
        return initialMatrix;
    }

    private Hashtable computeNextMatrix(Hashtable currentMatrix, int iteration) {
        Hashtable newMatrix = new Hashtable();
        Set entrySet1 = currentMatrix.entrySet();
        for (Iterator it1 = entrySet1.iterator(); it1.hasNext();) {
            Map.Entry ent1 = (Map.Entry) it1.next();
            AbstractHistoryGroup ahg1 = (AbstractHistoryGroup) ent1.getKey();
            Vector vectorOfChanges1 = (Vector) ent1.getValue();
            Set entrySet2 = currentMatrix.entrySet();
            for (Iterator it2 = entrySet2.iterator(); it2.hasNext();) {
                Map.Entry ent2 = (Map.Entry) it2.next();
                AbstractHistoryGroup ahg2 = (AbstractHistoryGroup) ent2.getKey();
                Vector vectorOfChanges2 = (Vector) ent2.getValue();
                if (!ahg1.equals(ahg2)) {
                    Vector vectorOfChangesResult = new Vector();
                    int count = 0;
                    for (int i = 0; i < vectorOfChanges1.size(); i++) {
                        Integer el1 = ((Integer) vectorOfChanges1.elementAt(i));
                        Integer el2 = ((Integer) vectorOfChanges2.elementAt(i));
                        if (el1.intValue() == 1 && el2.intValue() == 1) {
                            vectorOfChangesResult.add(new Integer(1));
                            count++;
                        } else
                            vectorOfChangesResult.add(new Integer(0));
                    }
                    if (count >= minimumNumberOfVersions) {
                        AbstractHistoryGroup ahgResult = ahg2.createNewHistoryGroupByAdding(ahg1);
                        if (ahgResult.getHistoriesCollection().size() > iteration)
                            newMatrix.put(ahgResult, vectorOfChangesResult);
                    }
                }
            }
        }
        return optimize(newMatrix);
    }

    private Hashtable optimize(Hashtable matrix) {
        Hashtable optimizedMatrix = new Hashtable();
        Set entrySet1 = matrix.entrySet();
        Object[] array1 = entrySet1.toArray();
        for (int i = 0; i < entrySet1.size(); i++) {
            Map.Entry ent1 = (Map.Entry) array1[i];
            AbstractHistoryGroup ahg1 = (AbstractHistoryGroup) ent1.getKey();
            Vector vectorOfChanges1 = (Vector) ent1.getValue();
            boolean bool = false;
            for (int j = i + 1; j < entrySet1.size(); j++) {
                Map.Entry ent2 = (Map.Entry) array1[j];
                AbstractHistoryGroup ahg2 = (AbstractHistoryGroup) ent2.getKey();
                Vector vectorOfChanges2 = (Vector) ent2.getValue();
                if (ahg2.includes(ahg1)) {
                    int sameChangesNum = 0;
                    for (int k = 0; k < vectorOfChanges1.size(); k++)
                        if (!vectorOfChanges1.get(k).equals(vectorOfChanges2.get(k)))
                            sameChangesNum++;
                    if (sameChangesNum == vectorOfChanges1.size())
                        bool = true;
                }
            }
            if (!bool)
                optimizedMatrix.put(ahg1, vectorOfChanges1);
        }
        return optimizedMatrix;
    }
}
