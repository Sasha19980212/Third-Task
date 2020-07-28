package generator;

import generator.interfaces.NumberGenerationRule;
import generator.interfaces.NumberSequenceGenerator;

import java.io.IOException;
import java.io.Writer;

public class SimpleSequenceGenerator implements NumberSequenceGenerator  {

    private NumberGenerationRule numberGenerationRule;

    public SimpleSequenceGenerator(NumberGenerationRule numberGenerationRule) {
        this.numberGenerationRule = numberGenerationRule;
    }

    public void generateSequence(Writer numberWriter, long count) throws IOException {
        long counter = 0;

        while(counter++ < count) {
            numberWriter.write(numberGenerationRule.next() + "\n");
        }
    }
}
