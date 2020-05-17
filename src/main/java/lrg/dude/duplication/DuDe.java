package lrg.dude.duplication;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

public class DuDe {
    public static final String PROJECT_FOLDER = "project.folder=";
    public static final String MIN_EXACT_CHUNK = "min.chunk=";
    public static final String MAX_LINEBIAS = "max.linebias=";
    public static final String MIN_LENGTH = "min.length=";
    public static final String FILE_EXTENSIONS = "file.extensions=";
    public static final String CONSIDER_COMMENTS = "consider.comments=";
    public static final String CONSIDER_TEST_FILES = "consider.test.files=";

    public static String projectFolder = null;
    public static int minDuplicationLength = 20;
    public static int minExactChunk = 5;
    public static int maxLineBias = 3;
    public static ArrayList<String> fileExtensions = new ArrayList<>();
    public static HashMap<String, List<Duplication>> resultsMap = new HashMap<>();
    public static boolean considerComments = true;
    public static boolean considerTestFiles = true;

    private static ArrayList<String> initFileExtensions(String listOfFileExtensions) {
        return new ArrayList<>(Arrays.asList(Pattern.compile(",").split(listOfFileExtensions, 0)));
    }

    private static void init(String filename) {
        try {
            for (String line : Files.readAllLines(Paths.get(filename))) {
                if (line.startsWith(PROJECT_FOLDER)) {
                    projectFolder = line.substring(PROJECT_FOLDER.length());
                }
                if (line.startsWith(MIN_EXACT_CHUNK)) {
                    minExactChunk = Integer.valueOf(line.substring(MIN_EXACT_CHUNK.length()));
                }
                if (line.startsWith(MAX_LINEBIAS)) {
                    maxLineBias = Integer.valueOf(line.substring(MAX_LINEBIAS.length()));
                }
                if (line.startsWith(MIN_LENGTH)) {
                    minDuplicationLength = Integer.valueOf(line.substring(MIN_LENGTH.length()));
                }
                if (line.startsWith(FILE_EXTENSIONS)) {
                    fileExtensions = initFileExtensions(line.substring(FILE_EXTENSIONS.length()));
                }
                if (line.startsWith(CONSIDER_COMMENTS)) {
                    considerComments = Boolean.valueOf(line.substring(CONSIDER_COMMENTS.length()));
                }
                if (line.startsWith(CONSIDER_TEST_FILES)) {
                    considerComments = Boolean.valueOf(line.substring(CONSIDER_TEST_FILES.length()));
                }
            }
        } catch (IOException e) {
            System.out.println("No configuration file");
        }

        if (projectFolder == null) {
            System.out.println("No project folder specified");
            System.exit(-1);
        }
        if (fileExtensions.size() == 0) {
            fileExtensions.add(".java");
            fileExtensions.add(".c");
            fileExtensions.add(".cc");
            fileExtensions.add(".cpp");
            fileExtensions.add(".h");
            fileExtensions.add(".hh");
            fileExtensions.add(".hpp");
            fileExtensions.add(".cs");
            fileExtensions.add(".sql");
        }

        System.out.println(projectFolder);
        System.out.println(String.join(",", fileExtensions));
        System.out.println(minDuplicationLength);

    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            init("config.txt");
        } else {
            init(args[0]);
        }

        Processor processor = new SuffixTreeProcessor(projectFolder, new IdenticalCompareStrategy());

        Parameters params = new Parameters(minDuplicationLength, maxLineBias, minExactChunk, considerComments, considerTestFiles);
        processor.setParams(params);

        processor.run();

        Duplication[] results = processor.getSearchResults();

        if (results.length == 0) {
            System.out.println("No duplication results");
            System.exit(-1);
        }

        ArrayList<ChronosImportJson> jsonObjects = new ArrayList<ChronosImportJson>();

        for (int index = 0; index < results.length; index++) {
            String referenceFile = results[index].getReferenceCode().getEntityName();
            String duplicatedFile = results[index].getDuplicateCode().getEntityName();

            List<Duplication> duplicationForReferenceFile = resultsMap.get(referenceFile);
            if (duplicationForReferenceFile == null) {
                duplicationForReferenceFile = new ArrayList<Duplication>();
            }
            duplicationForReferenceFile.add(results[index]);
            resultsMap.put(referenceFile, duplicationForReferenceFile);

            if (results[index].isSelfDuplication()) {
                continue;
            }

            List<Duplication> duplicationForSecondaryFile = resultsMap.get(duplicatedFile);
            if (duplicationForSecondaryFile == null) {
                duplicationForSecondaryFile = new ArrayList<Duplication>();
            }
            duplicationForSecondaryFile.add(results[index]);
            resultsMap.put(duplicatedFile, duplicationForSecondaryFile);
        }

        for (String filename : resultsMap.keySet()) {
            jsonObjects.addAll(exportDuplication(filename, resultsMap.get(filename)));
        }

        exportJson(jsonObjects);
    }

    private static List<ChronosImportJson> exportDuplication(String filename, List<Duplication> duplicationsForFile) {
        int duplication_lines = 0;

        HashSet<String> duplicatedFiles = new HashSet<String>();
        List<ChronosImportJson> result = new ArrayList<ChronosImportJson>();

        final List<String> duplicatedFileNames = new ArrayList<>();
        final List<String> duplicatedCodeFragments = new ArrayList<>();

        if (filename.contains("CurrencyCloud.")) {
            System.out.println(filename);
        }
        for (Duplication crtDup : duplicationsForFile) {
            duplication_lines += crtDup.realLength();

            if (filename.compareTo(crtDup.getReferenceCode().getEntityName()) == 0) {
                duplicatedFileNames.add(crtDup.getDuplicateCode().getEntityName());
                duplicatedFiles.add(crtDup.getDuplicateCode().getEntityName());
                duplicatedCodeFragments.add(crtDup.getDuplicateCode().toString());
                if (filename.contains("CurrencyCloud.")) {
                    System.out.println("\t >>>" + crtDup.getDuplicateCode().getEntityName());
                }
            } else {
                duplicatedFiles.add(crtDup.getReferenceCode().getEntityName());
                duplicatedFileNames.add(crtDup.getReferenceCode().getEntityName());
                duplicatedCodeFragments.add(crtDup.getReferenceCode().toString());
            }
        }

        // final String duplicatedFileNamesString = String.join(",", duplicatedFileNames);

        result.add(new ChronosImportJson(filename, "duplicated_lines", "duplication", duplication_lines, duplicatedCodeFragments));
        result.add(new ChronosImportJson(filename, "duplicated_files", "duplication", duplicatedFiles.size(), duplicatedFileNames));

        return result;
    }

    public static void exportJson(List<ChronosImportJson> jsonObjects) throws IOException {
        PrintWriter out = new PrintWriter("dude.json");
        out.println(new Gson().toJson(jsonObjects));
        out.close();

        out = new PrintWriter("dude.csv");

        for (String file : resultsMap.keySet()) {
            List<Duplication> duplications = resultsMap.get(file);
            for (Duplication crtDup : duplications) {
                if (crtDup.isSelfDuplication() == false) {
                    out.println(crtDup.getReferenceCode().getEntityName() + "," + crtDup.getDuplicateCode().getEntityName() + "," + crtDup.copiedLength());
                }
            }
        }

        out.close();
    }

}
