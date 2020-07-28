package segregation.manager.format;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DescSorting implements SortingRule {

    private Comparator<Integer> comparator;

    public DescSorting() {
        comparator = (x, y) -> (-1) * x.compareTo(y);
    }

    @Override
    public List<Integer> sort(List<Integer> integers) {
        return integers.stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public Comparator<Integer> getComparator() {
        return comparator;
    }
}
