package lrg.common.abstractions.plugins.filters.composed;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.plugins.Descriptor;
import lrg.common.abstractions.plugins.filters.FilteringRule;

public class NotComposedFilteringRule extends FilteringRule {
    private FilteringRule firstFilter;

    public NotComposedFilteringRule(FilteringRule oneFilter) {
        super(new Descriptor("(not " + oneFilter.getDescriptorObject().getName() + ")",
                (oneFilter.getDescriptorObject().getEntityTypeName())));

        firstFilter = oneFilter;
    }

    public boolean applyFilter(AbstractEntityInterface anEntity) {
        if (firstFilter == null) return false;
        return (!(firstFilter.applyFilter(anEntity)));
    }
}

