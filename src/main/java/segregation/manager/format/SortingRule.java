package segregation.manager.format;

import java.util.Comparator;
import java.util.List;

public interface SortingRule {
    List<Integer> sort(List<Integer> integers);


    default boolean sorted(List<Integer> integers) {
        for(int i = 1; i < integers.size(); i++) {
            if(getComparator().compare(integers.get(i - 1), integers.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    Comparator<Integer> getComparator();
}
