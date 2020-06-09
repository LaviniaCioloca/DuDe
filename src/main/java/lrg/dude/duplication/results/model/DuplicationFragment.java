package lrg.dude.duplication.results.model;

public class DuplicationFragment {
    private String duplicationFragment;
    private Integer duplicationTotalLOC;
    private Integer duplicationActualLOC;

    public DuplicationFragment(final String duplicationFragment, final Integer duplicationTotalLOC, final Integer duplicationActualLOC) {
        this.duplicationFragment = duplicationFragment;
        this.duplicationTotalLOC = duplicationTotalLOC;
        this.duplicationActualLOC = duplicationActualLOC;
    }

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
}
