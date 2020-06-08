package lrg.dude.duplication.utils;

import lrg.dude.duplication.model.StringList;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ImportsCleaner extends CleaningDecorator {
    public ImportsCleaner(CleaningDecorator next) {
        super(next);
    }

    protected StringList specificClean(StringList text) {
        final ArrayList<String> linesWithoutImportStatements = new ArrayList<>(text.getList()
                                                                       .stream()
                                                                       .filter(line -> !line.contains("import") && !line.contains("using"))
                                                                       .collect(Collectors.toList()));

        return new StringList(linesWithoutImportStatements);
    }

}
