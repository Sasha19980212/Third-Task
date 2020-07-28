package generator;

import generator.interfaces.NumberGenerationRule;

import java.util.Random;

public class SimpleSequenceRule implements NumberGenerationRule {

    private static final int UPPER_BOUND = 10000;
    private final Random random;

    public SimpleSequenceRule() {
        random = new Random();
    }

    public SimpleSequenceRule(int seed) {
        random = new Random(seed);
    }


    @Override
    public int next() {
        return random.nextInt(UPPER_BOUND + UPPER_BOUND) - UPPER_BOUND;
    }
}
