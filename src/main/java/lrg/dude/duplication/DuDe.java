package lrg.dude.duplication;

import com.google.gson.Gson;
import lrg.dude.duplication.model.Duplication;
import lrg.dude.duplication.model.Parameters;
import lrg.dude.duplication.processor.Processor;
import lrg.dude.duplication.processor.SuffixTreeProcessor;
import lrg.dude.duplication.results.model.ChronosImportJson;
import lrg.dude.duplication.results.model.DuplicationFragment;
import lrg.dude.duplication.results.model.DuplicationFragmentJSONExportModel;
import lrg.dude.duplication.results.model.StatisticResults;
import lrg.dude.duplication.strategies.IdenticalCompareStrategy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    private static final Map<DuplicationFragment, Set<String>> duplicationFragmentsInFiles = new HashMap<>();
    private static DuplicationFragmentJSONExportModel duplicationFragmentWithMostLOC =
            new DuplicationFragmentJSONExportModel();
    private static DuplicationFragmentJSONExportModel duplicationFragmentPresentInMostFiles =
            new DuplicationFragmentJSONExportModel();

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

        // System.out.println("Will perform DuDe search analysis in folder: " + projectFolder);
        System.out.println("File extensions considered for analysis: " + String.join(", ", fileExtensions));
        System.out.println("The minimum duplication length is: " + minDuplicationLength + " LOC (lines of code)");
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            init("config.txt");
        } else {
            init(args[0]);
        }

        // System.out.println("\n--- DuDe Analysis started ---\n");
        Processor processor = new SuffixTreeProcessor(projectFolder, new IdenticalCompareStrategy());

        Parameters params = new Parameters(minDuplicationLength, maxLineBias, minExactChunk, considerComments, considerTestFiles);
        processor.setParams(params);

        processor.run();

        Duplication[] results = processor.getSearchResults();

        if (results.length == 0) {
            System.out.println("No duplication results");
            System.exit(0);
        }

        ArrayList<ChronosImportJson> jsonObjects = new ArrayList<>();

        for (int index = 0; index < results.length; index++) {
            String referenceFile = results[index].getReferenceCode().getEntityName();
            String duplicatedFile = results[index].getDuplicateCode().getEntityName();

            List<Duplication> duplicationForReferenceFile = resultsMap.get(referenceFile);
            if (duplicationForReferenceFile == null) {
                duplicationForReferenceFile = new ArrayList<>();
            }
            duplicationForReferenceFile.add(results[index]);
            resultsMap.put(referenceFile, duplicationForReferenceFile);

            if (results[index].isSelfDuplication()) {
                continue;
            }

            List<Duplication> duplicationForSecondaryFile = resultsMap.get(duplicatedFile);
            if (duplicationForSecondaryFile == null) {
                duplicationForSecondaryFile = new ArrayList<>();
            }
            duplicationForSecondaryFile.add(results[index]);
            resultsMap.put(duplicatedFile, duplicationForSecondaryFile);
        }

        for (String filename : resultsMap.keySet()) {
            jsonObjects.addAll(exportDuplication(filename, resultsMap.get(filename)));
        }

        final List<DuplicationFragmentJSONExportModel> duplicationFragments = new ArrayList<>();
        duplicationFragmentsInFiles.entrySet().forEach(entry -> {
            final DuplicationFragmentJSONExportModel filesWithDuplication = new DuplicationFragmentJSONExportModel();
            filesWithDuplication.setDuplicationFragment(entry.getKey().getDuplicationFragment());
            filesWithDuplication.setDuplicationTotalLOC(entry.getKey().getDuplicationTotalLOC());
            filesWithDuplication.setDuplicationActualLOC(entry.getKey().getDuplicationActualLOC());
            filesWithDuplication.setFilesHavingThisDuplicationFragmentCount(entry.getValue().size());
            filesWithDuplication.setFilesHavingThisDuplicationFragment(entry.getValue());

            duplicationFragments.add(filesWithDuplication);
        });

        final DuplicationFragment duplicationWithMostLOC = duplicationFragmentsInFiles.keySet()
                                                                                      .stream()
                                                                                      .max(Comparator.comparing(DuplicationFragment::getDuplicationActualLOC))
                                                                                      .get();

        final DuplicationFragment duplicationPresentInMostFiles = duplicationFragmentsInFiles.entrySet()
                                                                                             .stream()
                                                                                             .max(Map.Entry.comparingByValue(Comparator.comparingInt(Set::size)))
                                                                                             .get()
                                                                                             .getKey();

        populateDuplicationFragmentsForJSONExport(duplicationWithMostLOC,
                                                  duplicationFragmentsInFiles.get(duplicationWithMostLOC),
                                                  duplicationPresentInMostFiles,
                                                  duplicationFragmentsInFiles.get(duplicationPresentInMostFiles));

        PrintWriter out = new PrintWriter("dude-duplicationFragments.json");
        out.println(new Gson().toJson(duplicationFragments));
        out.close();

        exportJson(jsonObjects);

        exportStatisticResults(processor);
    }

    private static void populateDuplicationFragmentsForJSONExport(final DuplicationFragment duplicationWithMostLOC,
                                                                  final Set<String> filesThatHaveTheDuplicationWithMostLOC,
                                                                  final DuplicationFragment duplicationPresentInMostFiles,
                                                                  final Set<String> filesThatHaveTheDuplicationPresentInMostFiles) {
        duplicationFragmentWithMostLOC.setDuplicationFragment(duplicationWithMostLOC.getDuplicationFragment());
        duplicationFragmentWithMostLOC.setDuplicationTotalLOC(duplicationWithMostLOC.getDuplicationTotalLOC());
        duplicationFragmentWithMostLOC.setDuplicationActualLOC(duplicationWithMostLOC.getDuplicationActualLOC());
        duplicationFragmentWithMostLOC.setFilesHavingThisDuplicationFragmentCount(filesThatHaveTheDuplicationWithMostLOC.size());
        duplicationFragmentWithMostLOC.setFilesHavingThisDuplicationFragment(filesThatHaveTheDuplicationWithMostLOC);

        duplicationFragmentPresentInMostFiles.setDuplicationFragment(duplicationPresentInMostFiles.getDuplicationFragment());
        duplicationFragmentPresentInMostFiles.setDuplicationTotalLOC(duplicationPresentInMostFiles.getDuplicationTotalLOC());
        duplicationFragmentPresentInMostFiles.setDuplicationActualLOC(duplicationPresentInMostFiles.getDuplicationActualLOC());
        duplicationFragmentPresentInMostFiles.setFilesHavingThisDuplicationFragmentCount(filesThatHaveTheDuplicationPresentInMostFiles.size());
        duplicationFragmentPresentInMostFiles.setFilesHavingThisDuplicationFragment(filesThatHaveTheDuplicationPresentInMostFiles);
    }

    private static List<ChronosImportJson> exportDuplication(String filename, List<Duplication> duplicationsForFile) {
        int duplication_lines = 0;

        HashSet<String> duplicatedFiles = new HashSet<>();
        List<ChronosImportJson> result = new ArrayList<>();

        final List<String> duplicatedFileNames = new ArrayList<>();
        final List<DuplicationFragment> duplicatedCodeFragments = new ArrayList<>();

        if (filename.contains("CurrencyCloud.")) {
            System.out.println(filename);
        }
        for (Duplication crtDup : duplicationsForFile) {
            duplication_lines += crtDup.realLength();

            if (filename.compareTo(crtDup.getReferenceCode().getEntityName()) == 0) {
                duplicatedFileNames.add(crtDup.getDuplicateCode().getEntityName());
                duplicatedFiles.add(crtDup.getDuplicateCode().getEntityName());

                final DuplicationFragment duplicationFragment =
                        new DuplicationFragment(crtDup.getDuplicateCode().toString(),
                                                crtDup.getDuplicateCode().getLinesOfCode().size(),
                                                crtDup.getDuplicateCode().getLinesOfCleanedCode().size());
                duplicatedCodeFragments.add(duplicationFragment);

                if (filename.contains("CurrencyCloud.")) {
                    System.out.println("\t >>>" + crtDup.getDuplicateCode().getEntityName());
                }
            } else {
                duplicatedFiles.add(crtDup.getReferenceCode().getEntityName());
                duplicatedFileNames.add(crtDup.getReferenceCode().getEntityName());

                final DuplicationFragment duplicationFragment =
                        new DuplicationFragment(crtDup.getReferenceCode().toString(),
                                                crtDup.getReferenceCode().getLinesOfCode().size(),
                                                crtDup.getReferenceCode().getLinesOfCleanedCode().size());
                duplicatedCodeFragments.add(duplicationFragment);
            }
        }

        for (int i = 0; i < duplicatedCodeFragments.size(); ++i) {
            if (duplicationFragmentsInFiles.get(duplicatedCodeFragments.get(i)) == null) {
                final Set<String> fileThatHasDuplicationFragment = new HashSet<>();
                fileThatHasDuplicationFragment.add(filename);
                fileThatHasDuplicationFragment.add(duplicatedFileNames.get(i));

                duplicationFragmentsInFiles.put(duplicatedCodeFragments.get(i), fileThatHasDuplicationFragment);
            } else {
                final Set<String> fileThatHasDuplicationFragment =
                        duplicationFragmentsInFiles.get(duplicatedCodeFragments.get(i));
                fileThatHasDuplicationFragment.add(duplicatedFileNames.get(i));
            }
        }

        result.add(new ChronosImportJson(filename, "duplicated_lines", "duplication", duplication_lines,
                                         duplicatedCodeFragments.stream().map(DuplicationFragment::getDuplicationFragment).collect(Collectors.toList())));
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

    public static void exportStatisticResults(final Processor processor) throws FileNotFoundException {
        final StatisticResults statisticResults = new StatisticResults();
        statisticResults.setNumberOfFilesAnalysed(processor.getNumberOfEntities());
        statisticResults.setNumberOfDuplicatedCodeFragments(duplicationFragmentsInFiles.size());

        final Set<String> filesWithDuplicateFragments = new HashSet<>();
        duplicationFragmentsInFiles.entrySet().forEach(entry -> {
            entry.getValue().stream().forEach(file -> filesWithDuplicateFragments.add(file));
        });

        statisticResults.setFilesWithDuplicateFragments(filesWithDuplicateFragments);
        statisticResults.setNumberOfFilesContainingDuplicateFragments(filesWithDuplicateFragments.size());

        final double percentageOfFilesAnalysedThatHaveDuplicateFragments = calculate2DecimalPlacesPercentageOfDuplication(filesWithDuplicateFragments.size(),
                                                                                                                          processor.getNumberOfEntities());
        statisticResults.setPercentageOfFilesAnalysedThatHaveDuplicateFragments(percentageOfFilesAnalysedThatHaveDuplicateFragments);

        statisticResults.setDuplicationFragmentWithMostLOC(duplicationFragmentWithMostLOC);
        statisticResults.setDuplicationFragmentPresentInMostFiles(duplicationFragmentPresentInMostFiles);

        final PrintWriter out = new PrintWriter("dude-StatisticResults.json");
        out.println(new Gson().toJson(statisticResults));
        out.close();
    }

    private static double calculate2DecimalPlacesPercentageOfDuplication(final int filesWithDuplicateFragments,
                                                                         final int totalNumberOfFiles) {
        return Math.floor((double) filesWithDuplicateFragments * 100 / (double) totalNumberOfFiles * 100) / 100;
    }

}
