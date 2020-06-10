package lrg.dude.duplication.results.model;

import java.util.Set;

public class StatisticResults {
    private Integer numberOfFilesAnalysed;
    private Integer numberOfDuplicatedCodeFragments;
    private Integer numberOfFilesContainingDuplicateFragments;
    private Set<String> filesWithDuplicateFragments;
    private Double percentageOfFilesAnalysedThatHaveDuplicateFragments;
    private DuplicationFragmentJSONExportModel duplicationFragmentWithMostLOC;
    private DuplicationFragmentJSONExportModel duplicationFragmentPresentInMostFiles;

    public Integer getNumberOfFilesAnalysed() {
        return numberOfFilesAnalysed;
    }

    public void setNumberOfFilesAnalysed(final Integer numberOfFilesAnalysed) {
        this.numberOfFilesAnalysed = numberOfFilesAnalysed;
    }

    public Integer getNumberOfDuplicatedCodeFragments() {
        return numberOfDuplicatedCodeFragments;
    }

    public void setNumberOfDuplicatedCodeFragments(final Integer numberOfDuplicatedCodeFragments) {
        this.numberOfDuplicatedCodeFragments = numberOfDuplicatedCodeFragments;
    }

    public Integer getNumberOfFilesContainingDuplicateFragments() {
        return numberOfFilesContainingDuplicateFragments;
    }

    public void setNumberOfFilesContainingDuplicateFragments(final Integer numberOfFilesContainingDuplicateFragments) {
        this.numberOfFilesContainingDuplicateFragments = numberOfFilesContainingDuplicateFragments;
    }

    public Set<String> getFilesWithDuplicateFragments() {
        return filesWithDuplicateFragments;
    }

    public void setFilesWithDuplicateFragments(final Set<String> filesWithDuplicateFragments) {
        this.filesWithDuplicateFragments = filesWithDuplicateFragments;
    }

    public Double getPercentageOfFilesAnalysedThatHaveDuplicateFragments() {
        return percentageOfFilesAnalysedThatHaveDuplicateFragments;
    }

    public void setPercentageOfFilesAnalysedThatHaveDuplicateFragments(final Double percentageOfFilesAnalysedThatHaveDuplicateFragments) {
        this.percentageOfFilesAnalysedThatHaveDuplicateFragments = percentageOfFilesAnalysedThatHaveDuplicateFragments;
    }

    public DuplicationFragmentJSONExportModel getDuplicationFragmentWithMostLOC() {
        return duplicationFragmentWithMostLOC;
    }

    public void setDuplicationFragmentWithMostLOC(final DuplicationFragmentJSONExportModel duplicationFragmentWithMostLOC) {
        this.duplicationFragmentWithMostLOC = duplicationFragmentWithMostLOC;
    }

    public DuplicationFragmentJSONExportModel getDuplicationFragmentPresentInMostFiles() {
        return duplicationFragmentPresentInMostFiles;
    }

    public void setDuplicationFragmentPresentInMostFiles(final DuplicationFragmentJSONExportModel duplicationFragmentPresentInMostFiles) {
        this.duplicationFragmentPresentInMostFiles = duplicationFragmentPresentInMostFiles;
    }
}
