package lrg.dude.duplication;

public class Parameters {
    private int minLength;
    private int maxLineBias;
    private int minExactChunk;
    private boolean considerComments;
    private boolean considerTestFiles;

    public Parameters(int minLength, int maxLineBias, int minExactChunk, boolean considerComments,
                      boolean considerTestFiles) {
        this.minLength = minLength;
        this.maxLineBias = maxLineBias;
        this.minExactChunk = minExactChunk;
        this.considerComments = considerComments;
        this.considerTestFiles = considerTestFiles;
    }

    public int getMinLength() {
        return minLength;
    }

    public int getMaxLineBias() {
        return maxLineBias;
    }

    public int getMinExactChunk() {
        return minExactChunk;
    }

    public boolean isConsiderComments() {
        return considerComments;
    }

    public boolean isConsiderTestFiles() {
        return considerTestFiles;
    }
}
