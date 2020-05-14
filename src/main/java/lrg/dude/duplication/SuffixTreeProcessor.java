package lrg.dude.duplication;

import org.ardverk.collection.AdaptedPatriciaTrie;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class SuffixTreeProcessor extends Processor {
    public static final String SPLIT_STRING = "\r\n|\r|\n";
    public static double time;
    private ArrayList<Observer> observers = new ArrayList<Observer>();
    private Entity[] entities;
    private Entity[] entitiesWithoutCleanup;
    private IMethodEntity referenceEntity;
    private int noOfRefLines;
    private MatrixLineList matrixLines;
    private VirtualColumnMatrix coolMatrix;
    private DuplicationList duplicates;
    private StringCompareStrategy compareStrategy;
    private Parameters params = new Parameters(0, 1, 2, false);
    //statistical data
    private long numberOfRawLines = 0;
    private long numberOfDots = 0;  //in half of the matrix (non-redundant)
    private long numberOfDuplicatedLines = 0;

    /**
     * Constructor for dead mode (starting with a path)
     *
     * @param path The path where to start searching for files
     */
    public SuffixTreeProcessor(String path, StringCompareStrategy compareStrategy) {
        this.compareStrategy = compareStrategy;
        long start = System.currentTimeMillis();
        DirectoryReader cititorDirector = new DirectoryReader(path);
        ArrayList<File> files = cititorDirector.getFilesRecursive();

        System.err.println("FILES: " + files.size());
        if (files != null) {
            ArrayList<Entity> allFiles = new ArrayList<Entity>();
            for (int i = 0; i < files.size(); i++) {
                File currentFile = files.get(i);
                // this check is needed to filter only source files, else it will throw an exception
                if (isSourceFile(currentFile)) {
                    //TODO: aici am problema daca e cale relativa
                    String shortName = currentFile.getAbsolutePath().substring(path.length() + 1);
                    allFiles.add(new SourceFile(currentFile, shortName));
                }
            }
            entities = allFiles.toArray(new Entity[allFiles.size()]);
        } else {
            entities = new SourceFile[0];
        }

        entitiesWithoutCleanup = new Entity[entities.length];

        for (int i = 0; i < entities.length; ++i) {
            final SourceFile entity = new SourceFile();
            entity.setFileName(entities[i].getName());

            final StringList stringList = new StringList();
            stringList.addAll(entities[i].getCode());
            entity.setCode(stringList);

            entitiesWithoutCleanup[i] = entity;
        }
        long stop = System.currentTimeMillis();
        System.out.print("\nDUDEE: Got " + entities.length + " files in: ");
        System.out.println(TimeMeasurer.convertTimeToString(stop - start) + "\n");
    }

    /**
     * Constructor for the alive mode
     *
     * @param methods Entities (methods from MeMoJ / MeMoRIA)
     */
    public SuffixTreeProcessor(Entity[] methods, StringCompareStrategy compareStrategy) {
        this.compareStrategy = compareStrategy;
        entities = methods;
        referenceEntity = null;
    }

    public SuffixTreeProcessor(Entity[] methods) {
        this(methods, new IdenticalCompareStrategy());
    }

    public SuffixTreeProcessor(Entity[] methods, IMethodEntity reference) {
        this(methods, new IdenticalCompareStrategy());
        referenceEntity = reference;
    }

    private boolean isSourceFile(File currentFile) {
        String absolutePath = currentFile.getAbsolutePath();

        for (String extension : DuDe.fileExtensions) {
            if (absolutePath.endsWith(extension) && isAcceptable(absolutePath)) {
                return true;
            }
        }

        return false;
    }

    private boolean isAcceptable(String absolutePath) {
        return true;
    }

    public void run() {
        time = 0.0;
        long startTime = System.currentTimeMillis();
        if (referenceEntity != null) {
            rearrangeEntities();
        }
        createNewMatrixLines();    /*cleans code*/
        /*if(referenceEntity == null)*/
        clusteredSearchWithSuffixTries();
        //else clusteredSearchWithReferenceEntity();

        numberOfDuplicatedLines = matrixLines.countDuplicatedLines();
        notifyObservers();
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println("Computed duplications in: " + (currentTimeMillis - startTime) + " ms");
        time = ((double) (currentTimeMillis - startTime)) / 1000.0;
    }

    /**
     * Cleans the code of a single entity (method body, file)
     *
     * @param entity The entity to work on
     * @return an array of "clean" code (no whitespaces etc.)
     */
    private MatrixLineList entityToNewMatrixLines(Entity entity, int startPos) {
        StringList code = entity.getCode();
        numberOfRawLines += code.size();
        try {
            code = cleanCode(code);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        /*create matrix lines*/
        int matrixPos = startPos;
        MatrixLineList matrixLines = new MatrixLineList();
        for (int i = 0; i < code.size(); i++) {
            if (code.get(i).length() > 0) {
                matrixLines.add(new MatrixLine(code.get(i), entity, i + 1, matrixPos++));
            }
        }
        return matrixLines;
    }

    /**
     * Having the entities (method bodies, files etc.) the method will create
     * an array of Strings with "clean" code (without the code that should be ignored).
     *
     * @return array of "clean" code
     */
    public MatrixLineList createNewMatrixLines() {
        long start = System.currentTimeMillis();
        matrixLines = new MatrixLineList();
        int noOfMatrixLinesBefore, noOfMatrixLinesAfter;
        for (int i = 0; i < entities.length; i++) {
            noOfMatrixLinesBefore = matrixLines.size();

            if (entities[i] == null) {
                System.out.println("entities[" + i + "] is null");
            }

            if (entities[i].getCode().size() < params.getMinLength()) {
                System.out.println(entities[i].getName() + " ignored");
                continue;
            }
            matrixLines.addAll(entityToNewMatrixLines(entities[i], noOfMatrixLinesBefore));
            noOfMatrixLinesAfter = matrixLines.size();
            entities[i].setNoOfRelevantLines(noOfMatrixLinesAfter - noOfMatrixLinesBefore);
            setRelevantLinesForReferenceEntity(entities[i]);
        }
        long stop = System.currentTimeMillis();
        System.out.print("\nDUDE: Got " + matrixLines.size() + " lines of clean code in: ");
        System.out.println(TimeMeasurer.convertTimeToString(stop - start) + "\n");
        return matrixLines;
    }

    private void clusteredSearchWithSuffixTries() {
        int startingMatrixColumn;
        int noOfEntities = entities.length;
        noOfRefLines = 0;

        duplicates = new DuplicationList();
        System.out.println("NO OF ENTITIES (SuffixTreeProcessor.clusteredSearchWithSuffixTries): " + noOfEntities);
        AdaptedPatriciaTrie trie = new AdaptedPatriciaTrie();
        startingMatrixColumn = 0;
        int totalNoOfRows = 0;

        for (int j = 0; j < noOfEntities; j++) {

            //add lines to trie and create dot-matrix
            int noOfColumns = entities[j].getNoOfRelevantLines();

            if (referenceEntity != null) {
                if (j == 0) {
                    noOfRefLines = noOfColumns;
                }
            }
            coolMatrix = new VirtualColumnMatrix(noOfColumns, startingMatrixColumn);

            createMatrixCells(totalNoOfRows, noOfColumns, trie);
            totalNoOfRows += noOfColumns;
            //search dot-matrix for duplicates
            if (referenceEntity != null) {
                //if reference entity, search dot-matrix against the reference only
                System.err.println("Search Ref #" + j);
                searchRefDuplicates(startingMatrixColumn, noOfColumns, noOfRefLines);
            } else {
                //if no reference entity, search dot-matrix against all previous entities
                System.err.println("Search #" + j);
                // System.out.println("ColMatrix: " + coolMatrix.getList());
                searchDuplicates(startingMatrixColumn, noOfColumns);
            }
            //removeAll does also the free
            // coolMatrix.removeAll();
            coolMatrix = null;
            startingMatrixColumn += noOfColumns;
        }

        System.out.println("PAT. NO OF Duplicates: " + duplicates.size());
        System.out.println("PAT. NO OF Duplicate Dots: " + numberOfDots);
    }

    /**
     * Starting from the matrix lines ("clean" code), it compares the lines
     * to establish the matrix.
     *
     * @param startingMatrixRow
     * @param rows
     */
    private void createMatrixCells(int startingMatrixColumn, int columns, AdaptedPatriciaTrie trie) {
        int endMatrixColumn = startingMatrixColumn + columns;
        for (int j = startingMatrixColumn; j < endMatrixColumn; j++) {  //aici am corectat in loc de j = 0!! RADU
            MatrixLineList ml = new MatrixLineList();
            ml.add(matrixLines.get(j));

            // System.out.println(j + " MatrixLine code: " + matrixLines.get(j).getCode());

            MatrixLineList newList = trie.put(matrixLines.get(j).getCode(), ml);

            /*
                newList is not null only if in the trie the matrixLine code was encountered at least once (the line is a
                duplicate). The trie will return a MatrixLineList which contains a list with the other entities in which
                that line of code was found and its line number
            */
            if (null != newList) {
                // System.out.println(">>> For that line newList with size: " + newList.size() + " is: " + newList
                // .getList());
                for (int i = 0; i < newList.size(); i++) {
                    if ((referenceEntity != null) &&
                        (j >= noOfRefLines && newList.get(i).getMatrixIndex() >= noOfRefLines)) {
                        continue;
                    }
                    /*
                        Add at position j the matrixIndexes of all the lines that are duplicate to this one:
                        matrixLines.get(j).getCode()

                        For example if the line of code at line 60 consists of the string: "model.readData()"
                        and if this line was encountered at lines 30 and 35 then at index 60 in the matrix we will have:
                        {30=false, 35=false}

                     */
                    // System.out.println("Will put at coordinate: " + j + " value: " + newList.get(i).getMatrixIndex
                    // ());
                    coolMatrix.set(newList.get(i).getMatrixIndex(), j, Boolean.valueOf(false));
                    numberOfDots++;
                }
            }
        }
    }

    private void rearrangeEntities() {
        int referenceIndex = 0;
        if (referenceEntity == null) {
            return;
        }
        IMethodEntity firstEntity = (IMethodEntity) entities[0];
        if (firstEntity.getMethod() == referenceEntity.getMethod()) {
            return;
        }

        for (referenceIndex = 1; ((IMethodEntity) entities[referenceIndex]).getMethod() != referenceEntity.getMethod(); referenceIndex++) {
        }

        if (referenceIndex >= entities.length) {
            System.out.println("ERROR");
            return;
        }

        entities[referenceIndex] = firstEntity;
        entities[0] = referenceEntity;
    }

    /**
     * Once established the duplicate lines, this method will try to group
     * the lines in Duplication entities (fragments of duplicated code)
     *
     * @param startingMatrixRow
     * @param rows
     */
    private void searchDuplicates(int startingMatrixColumn, int columns) {
        Duplication newDup;
        Iterator iterator;
        //for every cell above the diagonal
        int endMatrixColumn = startingMatrixColumn + columns;
        for (int i = startingMatrixColumn; i < endMatrixColumn; i++) {
            iterator = coolMatrix.iterator(i);  //returneaza urmatorul index (Integer)
            while (iterator.hasNext()) {
                int j = ((Integer) iterator.next()).intValue();
                //if there is a duplicate [i,j] and it hasn't been used in a previous duplication
                if (coolMatrix.get(j, i) != null &&
                    !coolMatrix.get(j, i).booleanValue()
                    &&
                    (newDup = traceDuplication(j, i)) != null
                ) {
                    duplicates.add(newDup);
                }
            }
        }
    }

    private void searchRefDuplicates(int startingMatrixColumn, int columns, int refRows) {
        Duplication newDup;
        Iterator iterator;
        //for every cell above the diagonal
        int endMatrixColumn = startingMatrixColumn + columns;
        for (int i = startingMatrixColumn; i < endMatrixColumn; i++) {
            iterator = coolMatrix.iterator(i);  //returneaza urmatorul index (Integer)
            while (iterator.hasNext()) {
                int j = ((Integer) iterator.next()).intValue();
                //if there is a duplicate [i,j] and it hasn't been used in a previous duplication
                if (j < refRows &&
                    coolMatrix.get(j, i) != null &&
                    !coolMatrix.get(j, i).booleanValue()
                    &&
                    (newDup = traceDuplication(j, i)) != null
                ) {
                    duplicates.add(newDup);
                }
            }
        }
    }

    /**
     * Checks if the cell can be taken as part of the current duplication.
     * Checks out if the coordinate (reference.X+dx,reference.Y+dy) is within the matrix boundaries,
     * if it is a part of the same 2 entities as the current coordinate,
     * and if it is a duplication
     *
     * @param reference Current coordinate
     * @param dx        the x bias
     * @param dy        the y bias
     * @return true if it is a valid coordinate, and false if not
     */
    private boolean validCoordinate(Coordinate reference, int dx, int dy) {
        int oldX = reference.getX();
        int oldY = reference.getY();
        int newX = oldX + dx;
        int newY = oldY + dy;
        /*not used*/
        return newX < matrixLines.size() && /*still within the matrix*/
               newY < matrixLines.size() &&
                /*still within the same entity*/
               matrixLines.get(newX).getEntity() == matrixLines.get(oldX).getEntity() &&
               matrixLines.get(newY).getEntity() == matrixLines.get(oldY).getEntity() &&
               (coolMatrix.get(newX, newY)) != null && /*is duplicate*/
               coolMatrix.get(newX, newY).booleanValue() == false;
    }

    /**
     * Tries to find a valid coordinate to be added to the current Duplication
     *
     * @param start The last coordinate of the current Duplication
     * @return The next valid coordinate or null if none found
     */
    private Coordinate getNextCoordinate(Coordinate start, int currentExactSize) {
        if (validCoordinate(start, 1, 1)) {
            return new Coordinate(start.getX() + 1, start.getY() + 1);
        }
        if (currentExactSize < params.getMinExactChunk()) {
            return null;
        }
        for (int i = 1; i <= params.getMaxLineBias(); i++) {
            if (validCoordinate(start, 1, 1 + i)) {
                return new Coordinate(start.getX() + 1, start.getY() + 1 + i);
            }
        }
        for (int i = 1; i <= params.getMaxLineBias(); i++) {
            if (validCoordinate(start, 1 + i, 1)) {
                return new Coordinate(start.getX() + 1 + i, start.getY() + 1);
            }
        }
        for (int i = 1; i <= params.getMaxLineBias(); i++) {
            if (validCoordinate(start, 1 + i, 1 + i)) {
                return new Coordinate(start.getX() + 1 + i, start.getY() + 1 + i);
            }
        }
        return null;
    }

    /**
     * Starting from a duplicate cell in the matrix, this method will
     * try to follow some pattern (diagonal) to group duplicate code lines
     * into duplicate code fragments
     *
     * @param rowNo Row number of the cell where the pattern tracing starts
     * @param colNo Column number of the cell where the pattern tracing starts
     * @return Duplication entity or null if the Duplication is too short to be accepted.
     */
    private Duplication traceDuplication(int rowNo, int colNo) {
        CoordinateList coordinates = new CoordinateList();
        Coordinate start = new Coordinate(rowNo, colNo);
        Coordinate end = start;
        Coordinate current = start;
        coordinates.add(current);
        int currentExactChunkSize = 1;     //first duplication line is always an Exact
        while ((current = getNextCoordinate(current, currentExactChunkSize)) != null) {
            Coordinate previous = coordinates.get(coordinates.size() - 1);
			/*check that the duplication is not within the same entity,
            and the end of the referenceCode has not reached the start of the dupCode*/
            if (current.getX() < start.getY()) {
                coordinates.add(current);
            } else {
                continue;
            }
            int dx = current.getX() - previous.getX();
            int dy = current.getY() - previous.getY();
            if (dx == 1 && dy == 1) {
                currentExactChunkSize++;
            } else {
                currentExactChunkSize = 1;
            }
        }
        if (currentExactChunkSize < params.getMinExactChunk()) {
            //remove coordinates representing the last exact chunk
            for (int i = 0; i < currentExactChunkSize; i++) {
                int index = coordinates.size() - 1;
                coordinates.remove(index);
            }
        }
        if (coordinates.size() > 0) {
            end = coordinates.get(coordinates.size() - 1);
            //length considered the number of lines that form the duplication chain
            int lengthX = end.getX() - start.getX() + 1;
            int lengthY = end.getY() - start.getY() + 1;
            int length = lengthX <= lengthY ? lengthX : lengthY;
            if (length >= params.getMinLength()) {
                return makeDuplication(coordinates, length);
            }
        }
        return null;
    }

    /**
     * Makes a duplication entity starting from a list of coordinates
     *
     * @param coordinates
     * @return new Duplication entity
     */
    private Duplication makeDuplication(CoordinateList coordinates, int length) {
        Duplication newDuplication;
        String signature = extractSignature(coordinates);
        DuplicationType type = extractType(signature);
        markCoordinates(coordinates);
        /*create duplication*/
        Coordinate start = coordinates.get(0);
        Coordinate end = coordinates.get(coordinates.size() - 1);

        MatrixLine referenceStart = matrixLines.get(start.getX());
        MatrixLine referenceEnd = matrixLines.get(end.getX());

        final List<String> referenceCleanedLinesOfCode = new ArrayList<>();
        for (int i = start.getX(); i <= end.getX(); ++i) {
            referenceCleanedLinesOfCode.add(matrixLines.get(i).getCode());
        }

        final Optional<Entity> referenceEntity = Arrays.stream(entitiesWithoutCleanup)
                                                       .filter(entity -> entity.getName().equals(referenceStart.getEntity().getName()))
                                                       .findFirst();

        final List<String> referenceLinesOfCode = new ArrayList<>();
        for (int i = referenceStart.getRealIndex(); i < referenceEnd.getRealIndex(); ++i) {
            referenceLinesOfCode.add(referenceEntity.get().getCode().get(i - 1));
        }

        // System.out.println("Reference Entity Lines of Code: " + referenceLinesOfCode);

        MatrixLine duplicateStart = matrixLines.get(start.getY());
        MatrixLine duplicateEnd = matrixLines.get(end.getY());

        final List<String> duplicateCleanedLinesOfCode = new ArrayList<>();
        for (int i = start.getY(); i <= end.getY(); ++i) {
            duplicateCleanedLinesOfCode.add(matrixLines.get(i).getCode());
        }

        final Optional<Entity> duplicateEntity = Arrays.stream(entitiesWithoutCleanup)
                                                       .filter(entity -> entity.getName().equals(duplicateStart.getEntity().getName()))
                                                       .findFirst();
        final List<String> duplicateLinesOfCode = new ArrayList<>();
        for (int i = duplicateStart.getRealIndex(); i < duplicateEnd.getRealIndex(); ++i) {
            duplicateLinesOfCode.add(duplicateEntity.get().getCode().get(i - 1));
        }

        // System.out.println("Duplicate Entity Lines of Code: " + duplicateLinesOfCode);

        CodeFragment referenceCode = new CodeFragment(referenceStart.getEntity(),
                                                      referenceStart.getRealIndex(),
                                                      referenceEnd.getRealIndex(),
                                                      referenceCleanedLinesOfCode,
                                                      referenceLinesOfCode);
        CodeFragment duplicateCode = new CodeFragment(duplicateStart.getEntity(),
                                                      duplicateStart.getRealIndex(),
                                                      duplicateEnd.getRealIndex(),
                                                      duplicateCleanedLinesOfCode,
                                                      duplicateLinesOfCode);
        newDuplication = new Duplication(referenceCode, duplicateCode, type, signature, length);
        return newDuplication;
    }

    /**
     * Marks the used coordinates in the matrix
     *
     * @param coordinates The coordinates list
     */
    private void markCoordinates(CoordinateList coordinates) {
        int size = coordinates.size();
        for (int i = 0; i < size; i++) {
            Coordinate current = coordinates.get(i);
            coolMatrix.set(current.getX(), current.getY(), new Boolean(true));
            //set the matrixLines involved as duplicated (for the statistics)
            matrixLines.get(current.getX()).setDuplicated();
            matrixLines.get(current.getY()).setDuplicated();
        }
    }

    /**
     * Extracts the duplication type, from a given signature
     *
     * @param signature Duplication's signature
     * @return Duplication type
     */
    private DuplicationType extractType(String signature) {
        StringBuffer buffer = new StringBuffer(signature);
        int iModified = buffer.indexOf("M");
        int iDelete = buffer.indexOf("D");
        int iInsert = buffer.indexOf("I");
        if (iModified < 0 && iDelete < 0 && iInsert < 0) {
            return DuplicationType.EXACT;
        }
        if (iModified > -1 && iDelete < 0 && iInsert < 0) {
            return DuplicationType.MODIFIED;
        }
        if (iModified < 0 && iDelete > -1 && iInsert < 0) {
            return DuplicationType.DELETE;
        }
        if (iModified < 0 && iDelete < 0 && iInsert > -1) {
            return DuplicationType.INSERT;
        }
        return DuplicationType.COMPOSED;
    }

    /**
     * Extracts a duplicate signature starting from a list of Coordinates
     *
     * @param coordinates The list
     * @return The duplication signature
     */
    private String extractSignature(CoordinateList coordinates) {
        StringBuffer signature = new StringBuffer();
        int exactChunkSize = 1; /*duplicates always start from an exact line duplication*/
        char separator = '.';
        for (int i = 1; i < coordinates.size(); i++) {
            Coordinate current = coordinates.get(i);
            Coordinate previous = coordinates.get(i - 1);

            int xBias = current.getX() - previous.getX() - 1;
            int yBias = current.getY() - previous.getY() - 1;

            if (xBias == yBias && xBias == 0) {     /*exact*/
                exactChunkSize++;
            } else {       /*delete, insert or modified*/
                signature.append("E" + exactChunkSize);
                exactChunkSize = 1;

                if (xBias == yBias && xBias > 0) {   /*modified*/
                    signature.append(separator + "M" + xBias + separator);
                } else if (xBias > 0) {     /*delete*/
                    signature.append(separator + "D" + xBias + separator);
                } else if (yBias > 0) {
                    signature.append(separator + "I" + yBias + separator);
                }
            }
        }
        signature.append("E" + exactChunkSize);
        return signature.toString();
    }

    /**
     * Having the entities (method bodies, files etc.) the method will create
     * an array of Strings with "clean" code (without the code that should be ignored).
     *
     * @return array of "clean" code
     */
    public MatrixLineList createMatrixLines() {
        long start = System.currentTimeMillis();
        matrixLines = new MatrixLineList();
        int noOfMatrixLinesBefore, noOfMatrixLinesAfter;
        for (int i = 0; i < entities.length; i++) {
            noOfMatrixLinesBefore = matrixLines.size();
            if (entities[i] == null) {
                System.out.println("null");
            }
            matrixLines.addAll(entityToMatrixLines(entities[i]));
            noOfMatrixLinesAfter = matrixLines.size();
            entities[i].setNoOfRelevantLines(noOfMatrixLinesAfter - noOfMatrixLinesBefore);
            setRelevantLinesForReferenceEntity(entities[i]);
        }
        long stop = System.currentTimeMillis();
        System.out.print("\nDUDE: Got " + matrixLines.size() + " lines of clean code in: ");
        System.out.println(TimeMeasurer.convertTimeToString(stop - start) + "\n");
        return matrixLines;
    }

    private void setRelevantLinesForReferenceEntity(Entity entity) {
        if (referenceEntity == null) {
            return;
        }
        IMethodEntity reference = referenceEntity;
        IMethodEntity crtEntity = (IMethodEntity) entity;

        if (reference.getMethod() == crtEntity.getMethod()) {
            reference.setNoOfRelevantLines(entity.getNoOfRelevantLines());
        }

    }

    /**
     * Filters the source code fragment
     *
     * @param bruteText Code unfiltered
     * @return clean code
     */
    private StringList cleanCode(StringList bruteText) {
        return DuplicationUtil.cleanCode(bruteText, params.isConsiderComments());
    }

    public int computeLinesOfCleanCode(String codeFragment) {
        StringList stringList = new StringList(codeFragment.split(SPLIT_STRING));
        StringList cleanCode = DuplicationUtil.cleanCode(stringList, false);
        int length = 0;
        for (int i = 0; i < cleanCode.size(); i++) {
            if (cleanCode.get(i).isEmpty()) {
                continue;
            }
            length++;
        }
        return length;
    }

    /**
     * Cleans the code of a single entity (method body, file)
     *
     * @param entity The entity to work on
     * @return an array of "clean" code (no whitespaces etc.)
     */
    private MatrixLineList entityToMatrixLines(Entity entity) {
        StringList code = entity.getCode();
        numberOfRawLines += code.size();

        try {
            code = cleanCode(code);
        } catch (Throwable e) {
            e.printStackTrace();
        }


        /*create matrix lines*/
        MatrixLineList matrixLines = new MatrixLineList();
        for (int i = 0; i < code.size(); i++) {
            if (code.get(i).length() > 0) {
                matrixLines.add(new MatrixLine(code.get(i), entity, i + 1));
            }
        }
        return matrixLines;
    }

    /**
     * ***********************************
     * Statistical data retrievers
     * *************************************
     */

    public long getNumberOfRawLines() {
        return numberOfRawLines;
    }

    public long getNumberOfCleanLines() {
        if (matrixLines != null) {
            return matrixLines.size();
        } else {
            return -1;
        }
    }

    public int getNumberOfEntities() {
        if (entities != null) {
            return entities.length;
        } else {
            return -1;
        }
    }

    public long getNumberOfDots() {
        return numberOfDots;
    }

    public Duplication[] getSearchResults() {
        return duplicates.toArray();
    }

    public long getNumberOfDuplicatedLines() {
        return numberOfDuplicatedLines;
    }

    /**
     * Pentru eventuale date statistice
     *
     * @return
     */
    public int getMatrixLinesLength() {
        return matrixLines.size();
    }

    public Entity[] testGetEntities() {
        return entities;
    }

    //Observer interface
    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        Iterator<Observer> iterator = observers.iterator();
        while (iterator.hasNext()) {
            iterator.next().getDuplication(this);
        }
    }

    public void setParams(Parameters params) {
        this.params = params;
    }
}
