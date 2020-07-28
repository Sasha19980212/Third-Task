package segregation.manager.format;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormatPool {

    private static final Map<String, SortingRule> sortingMap;

    static {
        sortingMap = new HashMap<>();

        sortingMap.put("asc", new AscSorting());
        sortingMap.put("desc", new DescSorting());
    }

    public static DistinctRule getDistinctRuleByKey(String key) {
        if("distinct".equals(key)) {
            return new DistinctRuleImpl();
        }
        return new DistinctRule() {
            @Override
            public boolean isDistinct(Integer distinct) {
                return false;
            }

            @Override
            public List<Integer> distinctSort(List<Integer> integers) {
                return integers;
            }
        };
    }

    public static SortingRule getSortingRuleByKey(String key) {
        return sortingMap.getOrDefault(key, new SortingRule() {
            @Override
            public List<Integer> sort(List<Integer> integers) {
                return integers;
            }

            @Override
            public Comparator<Integer> getComparator() {
                return null;
            }

            @Override
            public boolean sorted(List<Integer> integers) {
                return false;
            }
        });
    }
}
