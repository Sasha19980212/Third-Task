package segregation.thread;

import segregation.manager.format.DistinctRule;
import segregation.thread.pipe.MemoryPipe;
import segregation.manager.format.SortingRule;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SegregationThread extends Thread {

    private List<Predicate<Integer>> predicates;
    private MemoryPipe memoryPipe;
    private Path path;
    private Path resultPath;

    private SegregationThread() {
        predicates = new ArrayList<>();
    }

    public void run() {
        super.run();
        int buffer = 0;

        try(BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            while (bufferedReader.ready()) {
                buffer = Integer.parseInt(bufferedReader.readLine());

                if(predicateCheck(buffer)) {
                    memoryPipe.addInt(buffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        memoryPipe.flush();
        memoryPipe.loadToFile(resultPath);
        memoryPipe.removeTmpFiles();
    }

    private boolean predicateCheck(int num) {
        for (Predicate<Integer> predicate: predicates) {
            if(!predicate.test(num)) {
                return false;
            }
        }
        return true;
    }

    public static ThreadBuilder threadBuilder() {
        return new SegregationThread().new ThreadBuilder();
    }

    public class ThreadBuilder {

        private ThreadBuilder() {

        }

        public ThreadBuilder setPredicateList(List<Predicate<Integer>> list) {
            SegregationThread.this.predicates.addAll(list);
            return this;
        }

        public ThreadBuilder setPath(Path path) {
            SegregationThread.this.path = path;
            return this;
        }

        public ThreadBuilder setMemoryPipe(DistinctRule distinctRule, SortingRule sortingRule) {
            SegregationThread.this.memoryPipe = new MemoryPipe(distinctRule, sortingRule);
            return this;
        }

        public ThreadBuilder setResultPath(Path resultPath) {
            SegregationThread.this.resultPath = resultPath;
            return this;
        }

        public SegregationThread build() {
            return SegregationThread.this;
        }
    }
}
