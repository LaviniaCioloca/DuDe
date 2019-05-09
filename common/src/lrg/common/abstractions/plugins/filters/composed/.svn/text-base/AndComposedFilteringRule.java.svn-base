package lrg.common.abstractions.plugins.filters.composed;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.plugins.Descriptor;
import lrg.common.abstractions.plugins.filters.FilteringRule;

public class AndComposedFilteringRule extends FilteringRule {
    private FilteringRule firstFilter, secondFilter;

    public AndComposedFilteringRule(FilteringRule oneFilter, FilteringRule otherFilter) {
        super(new Descriptor("(" + oneFilter.getDescriptorObject().getName() + " and " + otherFilter.getDescriptorObject().getName() + ")",
                oneFilter.getIntersectionofEntityTypeNames(otherFilter)));

        firstFilter = oneFilter;
        secondFilter = otherFilter;
    }

    public boolean applyFilter(AbstractEntityInterface anEntity) {
        if ((firstFilter == null) || (secondFilter == null)) return false;
        return (firstFilter.applyFilter(anEntity) && secondFilter.applyFilter(anEntity));
    }
}
