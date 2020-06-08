package lrg.dude.duplication.utils;

import lrg.dude.duplication.model.StringList;

public class DuplicationUtil {

    public static StringList cleanCode(StringList bruteText, boolean considerComments) {
        CleaningDecorator commonCleaner = new WhiteSpacesCleaner(new ImportsCleaner(new NoiseCleaner(null)));
        CleaningDecorator cleaner;
        if (!considerComments) {
            cleaner = new CommentsCleaner(commonCleaner);
        } else {
            cleaner = commonCleaner;
        }
        return cleaner.clean(bruteText);
    }
}
