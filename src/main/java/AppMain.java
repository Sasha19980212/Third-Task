import generator.SimpleSequenceGenerator;
import generator.SimpleSequenceRule;
import generator.interfaces.NumberSequenceGenerator;
import segregation.ArgumentSegregationParser;
import segregation.SegregationThreadOption;
import segregation.manager.format.FormatPool;
import segregation.manager.PredicatePool;
import segregation.thread.SegregationThread;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AppMain {

    private static final Function<SegregationThreadOption, SegregationThread> OPTION_MAPPER =
            (x) -> {
                SegregationThread.ThreadBuilder builder = SegregationThread.threadBuilder();
                return builder
                        .setPredicateList(x
                                .getPredicateList()
                                .stream()
                                .map(PredicatePool::getPredicateByKey)
                                .collect(Collectors.toList()))
                        .setPath(x.getPath())
                        .setMemoryPipe(FormatPool.getDistinctRuleByKey(x.getDistinct()), FormatPool.getSortingRuleByKey(x.getSorting()))
                        .setResultPath(x.getResultPath())
                        .build();
            };

    public static void main(String[] args) {
        ArgumentSegregationParser argumentSegregationParser = new ArgumentSegregationParser(args);
        NumberSequenceGenerator nsg = new SimpleSequenceGenerator(new SimpleSequenceRule());
        List<SegregationThread> segregationThreadList = null;

        //Generate Random
        if(!Files.exists(Paths.get(argumentSegregationParser.getGeneratedDataPath()))) {
            try (BufferedWriter numberWriter =
                         Files.newBufferedWriter(Paths.get(argumentSegregationParser.getGeneratedDataPath()),
                                 Charset.defaultCharset())) {
                nsg.generateSequence(numberWriter, argumentSegregationParser.getCount());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        segregationThreadList =
                argumentSegregationParser
                        .getOptions()
                        .stream()
                        .map(OPTION_MAPPER)
                        .collect(Collectors.toList());

        segregationThreadList.forEach(Thread::start);

        for (SegregationThread segregationThread : segregationThreadList) {
            try {
                segregationThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
