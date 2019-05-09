package lrg.memoria.importer.recoder.recoder;

/**
 * Created by IntelliJ IDEA.
 * User: ratiud
 * Date: Mar 10, 2004
 * Time: 8:01:22 PM
 * To change this template use Options | File Templates.
 */

import lrg.memoria.importer.recoder.recoder.io.MemoriaDefaultSourceFileRepository;
import recoder.CrossReferenceServiceConfiguration;
import recoder.io.SourceFileRepository;
import recoder.service.SourceInfo;

/**
 * Added to manage Failed Dependencies.
 */
public class MeMoJCCrossReferenceServiceConfiguration extends CrossReferenceServiceConfiguration {

    protected SourceInfo makeSourceInfo() {
        return new MeMoJCDefaultCrossReferenceSourceInfo(this);
    }

    protected SourceFileRepository makeSourceFileRepository() {
        return new MemoriaDefaultSourceFileRepository(this);
    }
}
