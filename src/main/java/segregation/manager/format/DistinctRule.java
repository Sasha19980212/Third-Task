package segregation.manager.format;

import java.util.List;

public interface DistinctRule {
    boolean isDistinct(Integer distinct);
    List<Integer> distinctSort(List<Integer> integers);
}
