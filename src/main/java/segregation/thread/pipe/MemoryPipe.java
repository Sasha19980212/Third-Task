package segregation.thread.pipe;

import segregation.manager.format.DistinctRule;
import segregation.manager.format.SortingRule;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class MemoryPipe {
    private static final int MAX_MEMORY_BUFFER_LIMIT = 0xFFFFFF;
    private static final String TXT_FORMAT = ".txt";

    private List<Integer> pipe;
    private List<Path> buffer;

    private DistinctRule distinctRule;
    private SortingRule sortingRule;
    
    public MemoryPipe(DistinctRule distinctRule, SortingRule sortingRule) {
        pipe = new LinkedList<>();
        buffer = new ArrayList<>();

        this.distinctRule = distinctRule;
        this.sortingRule = sortingRule;
    }

    public void addInt(int num) {
        pipe.add(num);

        if(pipe.size() >= MAX_MEMORY_BUFFER_LIMIT) {
            flush();
        }
    }

    public void flush() {
        pipe = sortingRule.sort(pipe);

        if(!sortingRule.sorted(pipe)) {
            pipe = distinctRule.distinctSort(pipe);
        }

        String buff = "tmp_" + UUID.randomUUID().toString() + TXT_FORMAT;
        Path path = Path.of(buff);
        buffer.add(path);

        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(path, Charset.defaultCharset())) {
            pipe.forEach((x) -> {
                try {
                    bufferedWriter.write(x.toString() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        pipe.clear();
    }

    public void removeTmpFiles() {
        buffer.forEach((x) -> {
            if(Files.exists(x)) {
                try {
                    Files.delete(x);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadToFile(Path path) {
        try(StreamPipe streamPipe = new StreamPipe(buffer, sortingRule);
            BufferedWriter bufferedWriter = Files.newBufferedWriter(path, Charset.defaultCharset())) {
            while (streamPipe.ready()) {
                Integer integer = streamPipe.next();

                if(!distinctRule.isDistinct(integer)) {
                    bufferedWriter.write(integer + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
