import org.antlr.v4.runtime.ParserRuleContext;

/**
 *
 */
public class TermTest extends GrammarTest {

    private GroundTermTest groundTermTest = new GroundTermTest();
    private VariableTest variableTest = new VariableTest();

    private static final String[] TUPLES = new String[]
            {
                "(%s,%s)",
                "(%s, (%s, %s))",
                "((%s, (%s, %s)))",
                "((%s,%s),((%s,%s)))"
            };
    private static final String[] INVALID_SAMPLES = new String[]
            {
                    "()",
                    "",
                    "(())",
                    "((),(1))",
                    "("
            };

    @Override
    protected String[] getValidSamples() {
        String[] groundTermSamples = groundTermTest.getValidSamples();
        String[] variableSamples = variableTest.getValidSamples();
        int validTermAmount = groundTermSamples.length + variableSamples.length;
        int tupleCount = 0;
        // Tuple 0
        tupleCount += validTermAmount * 2;
        // Tuple 1
        tupleCount += validTermAmount * 3;
        // Tuple 2
        tupleCount += validTermAmount * 3;
        // Tuple 3
        tupleCount += validTermAmount * 4;
        // Create array to store possible terms
        String[] samples = new String[groundTermSamples.length + variableSamples.length + tupleCount];
        // Add ground terms and variables
        System.arraycopy(groundTermSamples, 0,samples, 0, groundTermSamples.length);
        System.arraycopy(variableSamples, 0, samples, groundTermSamples.length, variableSamples.length);
        // Add tuple examples
        int samplePointer = validTermAmount;
        // Tuple 0
        for (int i = 0; i < validTermAmount; i++, samplePointer++) {
            samples[samplePointer] = String.format(TUPLES[0], samples[i], samples[0]);
        }
        for (int i = 0; i < validTermAmount; i++, samplePointer++) {
            samples[samplePointer] = String.format(TUPLES[0], samples[0], samples[i]);
        }
        // Tuple 1
        for (int i = 0; i < validTermAmount; i++, samplePointer++) {
            samples[samplePointer] = String.format(TUPLES[1], samples[i], samples[0], samples[0]);
        }
        for (int i = 0; i < validTermAmount; i++, samplePointer++) {
            samples[samplePointer] = String.format(TUPLES[1], samples[0], samples[i], samples[0]);
        }
        for (int i = 0; i < validTermAmount; i++, samplePointer++) {
            samples[samplePointer] = String.format(TUPLES[1], samples[0], samples[0], samples[i]);
        }
        // Tuple 2
        for (int i = 0; i < validTermAmount; i++, samplePointer++) {
            samples[samplePointer] = String.format(TUPLES[2], samples[i], samples[0], samples[0]);
        }
        for (int i = 0; i < validTermAmount; i++, samplePointer++) {
            samples[samplePointer] = String.format(TUPLES[2], samples[0], samples[i], samples[0]);
        }
        for (int i = 0; i < validTermAmount; i++, samplePointer++) {
            samples[samplePointer] = String.format(TUPLES[2], samples[0], samples[0], samples[i]);
        }
        // Tuple 3
        for (int i = 0; i < validTermAmount; i++, samplePointer++) {
            samples[samplePointer] = String.format(TUPLES[3], samples[i], samples[0], samples[0], samples[0]);
        }
        for (int i = 0; i < validTermAmount; i++, samplePointer++) {
            samples[samplePointer] = String.format(TUPLES[3], samples[0], samples[i], samples[0], samples[0]);
        }
        for (int i = 0; i < validTermAmount; i++, samplePointer++) {
            samples[samplePointer] = String.format(TUPLES[3], samples[0], samples[0], samples[i], samples[0]);
        }
        for (int i = 0; i < validTermAmount; i++, samplePointer++) {
            samples[samplePointer] = String.format(TUPLES[3], samples[0], samples[0], samples[0], samples[i]);
        }
        return samples;
    }

    @Override
    protected String[] getInvalidSamples() {
        return INVALID_SAMPLES;
    }

    @Override
    public void test() {
        groundTermTest.test();
        variableTest.test();
        super.test();
    }

    @Override
    protected ParserRuleContext parse(GraafvisParser parser) {
        return parser.term();
    }

    @Override
    protected String getRuleName() {
        return "term";
    }


}
