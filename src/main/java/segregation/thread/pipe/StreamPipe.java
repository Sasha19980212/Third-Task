package segregation.thread.pipe;

import segregation.manager.format.SortingRule;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class StreamPipe implements AutoCloseable {

    private List<BufferedReader> bufferedReaders;
    private Map<BufferedReader, Queue<Integer>> numbers;
    private SortingRule sortingRule;

    public StreamPipe(List<Path> paths, SortingRule sortingRule) throws IOException {
        bufferedReaders = new ArrayList<>();
        numbers = new HashMap<>();
        this.sortingRule = sortingRule;

        for (Path path: paths) {
            BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.defaultCharset());
            bufferedReaders.add(bufferedReader);
            numbers.put(bufferedReader, new ArrayDeque<>());
        }
    }

    public boolean ready() throws IOException {
        for (Map.Entry<BufferedReader, Queue<Integer>> entry: numbers.entrySet()) {
            if(entry.getKey().ready() || !entry.getValue().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public int next() throws IOException {
        queueFill();

        BufferedReader current = null;
        for (Map.Entry<BufferedReader, Queue<Integer>> entry: numbers.entrySet()) {
            if(current == null || sortingRule.getComparator()
                    .compare(numbers.get(current).peek(), entry.getValue().peek()) > 0) {
                current = entry.getKey();
            }
        }

        return numbers.get(current).poll();
    }

    public void queueFill() throws IOException {
        List<BufferedReader> remove = new ArrayList<>();

        for (Map.Entry<BufferedReader, Queue<Integer>> entry: numbers.entrySet()) {
            if(entry.getValue().isEmpty()) {
                if (!entry.getKey().ready()) {
                    remove.add(entry.getKey());
                } else {
                    entry.getValue().add(Integer.parseInt(entry.getKey().readLine()));
                }
            }
        }

        remove.forEach((x) -> numbers.remove(x));
    }

    @Override
    public void close() throws Exception {
        for (BufferedReader br: bufferedReaders) {
            br.close();
        }
    }
}
