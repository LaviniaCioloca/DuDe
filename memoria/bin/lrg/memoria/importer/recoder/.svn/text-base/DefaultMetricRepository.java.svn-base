package lrg.memoria.importer.recoder;

import lrg.memoria.core.CodeStripe;

public class DefaultMetricRepository implements MetricsRepository {

    private int statements;
    private int lines;
    private int commentsNumber;
    private int decisions;
    private int loops;
    private int exits;
    private int exceptions;
    private int currentNestingLevel;
    private int maxNestingLevel;
    private int cyclomaticNumber;

    /**
     * The one and only instance of this class.
     */
    private static DefaultMetricRepository instance;

    static void cleanUp() {
        instance = null;
    }

    private DefaultMetricRepository() {
        resetAll();
    }

    public static MetricsRepository getMetricRepository() {
        if (instance == null) {
            instance = new DefaultMetricRepository();
        }
        return instance;
    }

    public void resetAll() {
        statements = 1;
        lines = 0;
        commentsNumber = 0;
        decisions = 0;
        loops = 0;
        exits = 0;
        exceptions = 0;
        currentNestingLevel = 0;
        maxNestingLevel = 0;
        cyclomaticNumber = 1;
    }

    public void addComments(int n) {
        commentsNumber += n;
        CodeStripe cs=DefaultModelRepository.getModelRepository(null).getCurrentStripe();
        if (cs!=null) cs.addAtomicCommentLines(n);
    }

    public int getCommentsNumber() {
        return commentsNumber;
    }

    public void addDecision() {
        // stripes don't need updating cause I can recognize this case
        decisions++;
        cyclomaticNumber++;
    }

    public int getDecisions() {
        return decisions;
    }

    public void addLoop() {
        // stripes don't need updating cause I can recognize this case
        loops += 1;
        cyclomaticNumber += 1;
        decisions++;
    }

    public int getLoops() {
        return loops;
    }

    public void addException() {
        exceptions += 1;
    }

    public int getExceptions() {
        return exceptions;
    }

    public void updateNestingLevel(int n) {
        currentNestingLevel += n;
        if (currentNestingLevel > maxNestingLevel)
            maxNestingLevel = currentNestingLevel;
    }

    public int getMaxNestingLevel() {
        return maxNestingLevel;
    }

    public int getCyclomatic() {
        return cyclomaticNumber;
    }

    public void addLogicalAnd() {
        cyclomaticNumber++;
        CodeStripe cs=DefaultModelRepository.getModelRepository(null).getCurrentStripe();
        if (cs!=null) cs.addAtomicCyclo(1);
    }

    public void addLogicalOr() {
        cyclomaticNumber++;
        CodeStripe cs=DefaultModelRepository.getModelRepository(null).getCurrentStripe();
        if (cs!=null) cs.addAtomicCyclo(1);
    }

    public void addStatements(int n) {
        statements += n;
    }

    public int getNumberOfStatements() {
        return statements;
    }

    public void addExit() {
        exits++;
    }

    public int getNumberOfExits() {
        return exits;
    }
}
