package lrg.memoria.hismo.utils;

import lrg.memoria.core.Statute;
import lrg.memoria.hismo.core.AbstractVersion;
import lrg.memoria.hismo.core.ClassVersion;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: ratiud
 * Date: Mar 1, 2004
 * Time: 8:00:53 PM
 * To change this template use Options | File Templates.
 */
public class NumberOfMethodsChange implements AbstractChangeProperty {

    public int getChangeValue(AbstractVersion previousVersion, AbstractVersion currentVersion) {
        int nom1, nom2;

        ClassVersion v1 = (ClassVersion) previousVersion;
        ArrayList methodsList1 = v1.getMethodList();
        nom1 = methodsList1.size();

        ClassVersion v2 = (ClassVersion) currentVersion;
        ArrayList methodsList2 = v2.getMethodList();
        nom2 = methodsList2.size();

        if (v1.getStatute() == Statute.LIBRARY)
            return 0;

        if (nom1 == nom2)
            return 0;
        else
            return 1;
    }

}
