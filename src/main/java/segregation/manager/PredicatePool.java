package segregation.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class PredicatePool {
    private static final Predicate<Integer> EVEN = x -> x % 2 == 0;
    private static final Predicate<Integer> ODD = x -> x % 2 != 0;
    private static final Predicate<Integer> POSITIVE = x -> x >= 0;
    private static final Predicate<Integer> NEGATIVE = x -> x < 0;

    private static final Map<String, Predicate<Integer>> predicateMap;

    static {
        predicateMap = new HashMap<>();

        predicateMap.put("even", EVEN);
        predicateMap.put("odd", ODD);
        predicateMap.put("positive", POSITIVE);
        predicateMap.put("negative", NEGATIVE);
    }

    public static Predicate<Integer> getPredicateByKey(String key) {
        return predicateMap.getOrDefault(key, (x) -> true);
    }
}
