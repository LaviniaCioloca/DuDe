package lrg.dude.duplication.results.model;

import java.util.List;

public class DuplicationFragment {
    private String duplicationFragment;
    private Integer duplicationCount;
    private List<String> filesWithDuplicationFragmentPresent;

    public DuplicationFragment(final String duplicationFragment, final Integer duplicationCount, final List<String> filesWithDuplicationFragmentPresent) {
        this.duplicationFragment = duplicationFragment;
        this.duplicationCount = duplicationCount;
        this.filesWithDuplicationFragmentPresent = filesWithDuplicationFragmentPresent;
    }

    public String getDuplicationFragment() {
        return duplicationFragment;
    }

    public void setDuplicationFragment(final String duplicationFragment) {
        this.duplicationFragment = duplicationFragment;
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
