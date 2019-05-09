package lrg.memoria.importer.recoder.recoder;

import recoder.CrossReferenceServiceConfiguration;
import recoder.service.ChangeHistoryEvent;
import recoder.service.DefaultCrossReferenceSourceInfo;


/**
 * Added to manage Failed Dependencies.
 */
public class MeMoJCDefaultCrossReferenceSourceInfo extends DefaultCrossReferenceSourceInfo {

    public MeMoJCDefaultCrossReferenceSourceInfo(CrossReferenceServiceConfiguration crsc) {
        super(crsc);
    }

    public void modelChanged(ChangeHistoryEvent changes) {
        try {
            super.modelChanged(changes);
        } catch (Exception e) {
            System.out.println(e);
//            e.printStackTrace();
//            System.out.println("Failed Dependency ERROR: The exception was caused by: " + changes.toString());
        }
        catch (Throwable e) {
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Strange Character: " + changes.toString());
        }
    }
}
