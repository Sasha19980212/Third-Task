package segregation.manager.format;

import java.util.List;
import java.util.stream.Collectors;

public class DistinctRuleImpl implements DistinctRule {

    private Integer prev = null;

    @Override
    public boolean isDistinct(Integer distinct) {
        if(prev == null) {
            prev = distinct;
            return false;
        }
        boolean result = prev.equals(distinct);
        prev = distinct;
        return result;
    }

    @Override
    public List<Integer> distinctSort(List<Integer> integers) {
        return integers.stream().sorted(Integer::compareTo).collect(Collectors.toList());
    }
}
