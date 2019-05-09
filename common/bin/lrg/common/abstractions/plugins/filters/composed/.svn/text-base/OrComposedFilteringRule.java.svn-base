package lrg.common.abstractions.plugins.filters.composed;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.plugins.Descriptor;
import lrg.common.abstractions.plugins.filters.FilteringRule;

public class OrComposedFilteringRule extends FilteringRule {
    private FilteringRule firstFilter, secondFilter;


    public OrComposedFilteringRule(FilteringRule oneFilter, FilteringRule otherFilter) {
        super(new Descriptor("(" + oneFilter.getDescriptorObject().getName() + " or " + otherFilter.getDescriptorObject().getName() + ")",
                oneFilter.getUnionofEntityTypeNames(otherFilter)));

        /*
          (oneFilter.getDescriptorObject().getEntityTypeName().compareTo("") == 0) ?
            otherFilter.getDescriptorObject().getEntityTypeName() :
            oneFilter.getDescriptorObject().getEntityTypeName()));
          */


        firstFilter = oneFilter;
        secondFilter = otherFilter;
    }

    public boolean applyFilter(AbstractEntityInterface anEntity) {
        if ((firstFilter == null) || (secondFilter == null)) return false;
        return (firstFilter.applyFilter(anEntity) || secondFilter.applyFilter(anEntity));
    }
}
