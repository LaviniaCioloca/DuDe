package lrg.memoria.importer;

public class ImporterTools {

    public static String getFileName(String fullFileName) {
        int index = fullFileName.lastIndexOf("\\");
        if (index < 0)
            index = fullFileName.lastIndexOf("/");
        if (index < 0)
            return fullFileName;
        return fullFileName.substring(index + 1, fullFileName.length());
    }

    public static String getPathName(String fullFileName) {
        int index = fullFileName.lastIndexOf("\\");
        if (index < 0)
            index = fullFileName.lastIndexOf("/");
        if (index < 0)
            return ".";
        return fullFileName.substring(0, index);
    }

}
