package lrg.memoria.hismo.utils;

import lrg.memoria.hismo.core.AbstractVersion;

/**
 * Created by IntelliJ IDEA.
 * User: ratiud
 * Date: Mar 1, 2004
 * Time: 7:22:54 PM
 * To change this template use Options | File Templates.
 */
public interface AbstractChangeProperty {

    public int getChangeValue(AbstractVersion previousVersion, AbstractVersion currentVersion);

}
