package segregation;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SegregationThreadOption {
    private static final String TYPE_SEPARATOR = "::";
    private static final String ENUM_SEPARATOR = "&&";

    private Path path;
    private Path resultPath;
    private List<String> predicateList;

    private String distinct;
    private String sorting;


    public SegregationThreadOption(String path, String arg) {
        this.path = Paths.get(path);

        String[] typeSplit = arg.split(TYPE_SEPARATOR);

        if(typeSplit.length > 0) {
            predicateList = new ArrayList<>();
            predicateList.addAll(Arrays.asList(typeSplit[0].split(ENUM_SEPARATOR)));
        }

        if(typeSplit.length > 1) {
            resultPath = Path.of(typeSplit[1]);
        }

        if(typeSplit.length > 2) {
            distinct = typeSplit[2];
        }

        if(typeSplit.length > 3) {
            sorting = typeSplit[3];
        }
    }

    public List<String> getPredicateList() {
        return predicateList;
    }


    public Path getPath() {
        return path;
    }

    public String getDistinct() {
        return distinct;
    }

    public String getSorting() {
        return sorting;
    }

    public Path getResultPath() {
        return resultPath;
    }
}
