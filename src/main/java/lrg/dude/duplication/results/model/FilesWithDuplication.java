package lrg.dude.duplication.results.model;

import java.util.List;

public class FilesWithDuplication {
    private String duplicationFragment;
    private Integer duplicationTotalLOC;
    private Integer duplicationActualLOC;
    private Integer duplicationCount;
    private List<String> filesWithDuplicationFragmentPresent;

    public String getDuplicationFragment() {
        return duplicationFragment;
    }

    public void setDuplicationFragment(final String duplicationFragment) {
        this.duplicationFragment = duplicationFragment;
    }

    public Integer getDuplicationTotalLOC() {
        return duplicationTotalLOC;
    }

    public void setDuplicationTotalLOC(final Integer duplicationTotalLOC) {
        this.duplicationTotalLOC = duplicationTotalLOC;
    }

    public Integer getDuplicationActualLOC() {
        return duplicationActualLOC;
    }

    public void setDuplicationActualLOC(final Integer duplicationActualLOC) {
        this.duplicationActualLOC = duplicationActualLOC;
    }

    public Integer getDuplicationCount() {
        return duplicationCount;
    }

    public void setDuplicationCount(final Integer duplicationCount) {
        this.duplicationCount = duplicationCount;
    }

    public List<String> getFilesWithDuplicationFragmentPresent() {
        return filesWithDuplicationFragmentPresent;
    }

    public void setFilesWithDuplicationFragmentPresent(final List<String> filesWithDuplicationFragmentPresent) {
        this.filesWithDuplicationFragmentPresent = filesWithDuplicationFragmentPresent;
    }
}
