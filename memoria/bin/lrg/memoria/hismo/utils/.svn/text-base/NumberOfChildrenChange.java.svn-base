package lrg.memoria.hismo.utils;

import lrg.memoria.core.DataAbstraction;
import lrg.memoria.core.Statute;
import lrg.memoria.hismo.core.AbstractVersion;
import lrg.memoria.hismo.core.ClassVersion;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: ratiud
 * Date: Mar 1, 2004
 * Time: 7:24:56 PM
 * To change this template use Options | File Templates.
 */
public class NumberOfChildrenChange implements AbstractChangeProperty {

    public int getChangeValue(AbstractVersion previousVersion, AbstractVersion currentVersion) {
        int noc1 = 0, noc2 = 0;

        ClassVersion v1 = (ClassVersion)previousVersion;
        ArrayList descendants1 = v1.getDescendants();
        for (int i = 0; i < descendants1.size(); i++)
            if (((DataAbstraction)descendants1.get(i)).getStatute() == Statute.NORMAL)
                noc1++;

        ClassVersion v2 = (ClassVersion)currentVersion;
        ArrayList descendants2 = v2.getDescendants();
        for (int i = 0; i < descendants2.size(); i++)
            if (((DataAbstraction)descendants2.get(i)).getStatute() == Statute.NORMAL)
                noc2++;

        if (noc1 == noc2)
            return 0;
        else
            return 1;
    }

}
