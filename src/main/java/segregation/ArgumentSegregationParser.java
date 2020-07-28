package segregation;

import java.util.ArrayList;
import java.util.List;

public class ArgumentSegregationParser {
    private String generatedDataPath;
    private long count;
    private List<SegregationThreadOption> options;

    public ArgumentSegregationParser(String[] args) {
        generatedDataPath = args[0];
        count = Long.parseLong(args[1]);

        options = new ArrayList<>();

        for(int i = 2; i < args.length; i++) {
            options.add(new SegregationThreadOption(generatedDataPath, args[i]));
        }
    }

    public String getGeneratedDataPath() {
        return generatedDataPath;
    }

    public long getCount() {
        return count;
    }

    public List<SegregationThreadOption> getOptions() {
        return options;
    }
}
