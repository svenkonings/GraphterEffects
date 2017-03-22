import org.antlr.v4.runtime.ParserRuleContext;
import java.util.Random;

/**
 *
 */
public class TermTest extends GrammarTest {

    private GroundTermTest groundTermTest = new GroundTermTest();
    private VariableTest variableTest = new VariableTest();

    private static final String[] TUPLES = new String[]{
            "(%s,%s)",
            "(%s, (%s, %s))",
            "((%s, (%s, %s)))",
            "((%s,%s),((%s,%s)))"
    };
    public static final String[] INVALID_SAMPLES = new String[]
            {
                    "()",
                    "",
                    "(())",
                    "((),(1))",
                    "("
            };

    @Override
    protected String[] getValidSamples() {
        int tupleTests = 2;
        int tuples = tupleTests * TUPLES.length;
        String[] groundTermSamples = groundTermTest.getValidSamples();
        String[] variableSamples = variableTest.getValidSamples();
        String[] samples = new String[groundTermSamples.length + variableSamples.length + tuples];
        System.arraycopy(groundTermSamples, 0,samples, 0, groundTermSamples.length);
        System.arraycopy(variableSamples, 0, samples, groundTermSamples.length, variableSamples.length);
        int offset = groundTermSamples.length + variableSamples.length;
        Random random = new Random();

        int j = 0;
        for (int test = 0; test < tupleTests; test++, j++) {
            int t1 = random.nextInt(offset);
            int t2 = random.nextInt(offset);


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


}
