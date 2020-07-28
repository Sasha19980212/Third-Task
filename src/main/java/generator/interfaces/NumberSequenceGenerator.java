package generator.interfaces;

import java.io.IOException;
import java.io.Writer;

public interface NumberSequenceGenerator {
    void generateSequence(Writer numberWriter, long count) throws IOException;
}
