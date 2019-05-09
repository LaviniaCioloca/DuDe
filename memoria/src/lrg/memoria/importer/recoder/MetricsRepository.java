package lrg.memoria.importer.recoder;

/**
 * Compute the basic metrics.
 */
public interface MetricsRepository {

    /**
     * Resets the current values of the metrics and other internal variables.
     */
    void resetAll();

    /**
     * Increases the number of comments with n.
     */
    void addComments(int n);

    /**
     * Retrieves the number of comments.
     */
    int getCommentsNumber();

    /**
     * Increases the decisions number with a unit.
     */
    void addDecision();

    /**
     * Retreives the current decisions number.
     */
    int getDecisions();

    /**
     * Increases the loops number with a unit.
     */
    void addLoop();

    /**
     * Retreives the current loops number.
     */
    int getLoops();

    /**
     * Increases the exception number with a unit.
     */
    void addException();

    /**
     * Retreives the current exceptions number.
     */
    int getExceptions();

    /**
     * Updates the current nesting level with a unit.
     * The valid values of n are -1 and 1.
     */
    void updateNestingLevel(int n);

    /**
     * Retreives the maximum nesting level.
     */
    int getMaxNestingLevel();


    /**
     * Retreives the current cyclomatic. Being a derived metric, we don't have addCyclomatic().
     * It is calculated transparently for the user.
     */
    int getCyclomatic();

    /**
     * Increases the number of logical and with a unit.
     */
    void addLogicalAnd();

    /**
     * Increases the number of logical or with a unit.
     */
    void addLogicalOr();

    /**
     * Increases the number of statements with n.
     */
    void addStatements(int n);

    /**
     * Retrives the number of statements.
     */
    int getNumberOfStatements();

    /**
     * Increases the number of exits with 1
     */
    void addExit();

    /**
     * Retrives the number of exits.
     */
    int getNumberOfExits();
}
