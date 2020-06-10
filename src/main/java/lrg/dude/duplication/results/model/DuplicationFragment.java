package lrg.dude.duplication.results.model;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "DuplicationFragment{" +
               "duplicationFragment='" + duplicationFragment + '\'' +
               ", duplicationTotalLOC=" + duplicationTotalLOC +
               ", duplicationActualLOC=" + duplicationActualLOC +
               '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DuplicationFragment that = (DuplicationFragment) o;
        return duplicationFragment.equals(that.duplicationFragment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duplicationFragment);
    }
}
