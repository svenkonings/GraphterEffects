import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class TermTest extends GrammarTest{

    private static final List<String> TUPLE = Arrays.asList
            (
                    "(%s,%s)",
                    "(%s, (%s, %s))",
                    "((%s, (%s, %s)))",
                    "((%s,%s),((%s,%s)))"
            );

    public static final List<String> VALID_SAMPLES = new ArrayList<>();
    static {
        List<String> GROUND_TERMS = GroundTermTest.VALID_SAMPLES;
        List<String> VARIABLES = VariableTest.VALID_SAMPLES;
        List<String> options = new ArrayList<>();
        options.addAll(GROUND_TERMS);
        options.addAll(VARIABLES);

        VALID_SAMPLES.addAll(GROUND_TERMS);
        VALID_SAMPLES.addAll(VARIABLES);
        // Add tuple 0
        for (String sample : options) {
            VALID_SAMPLES.add(String.format(TUPLE.get(0), sample, sample));
        }
        // Add tuple 1
        for (String sample : options) {
            VALID_SAMPLES.add(String.format(TUPLE.get(1), sample, sample, sample));
        }
        // Add tuple 2
        for (String sample : options) {
            VALID_SAMPLES.add(String.format(TUPLE.get(2), sample, sample, sample));
        }
        // Add tuple 3
        for (String sample : options) {
            VALID_SAMPLES.add(String.format(TUPLE.get(3), sample, sample, sample, sample));
        }
    }

    public static final List<String> INVALID_SAMPLES = Arrays.asList
            (
                    "()",
                    "",
                    "(())",
                    "((),(1))",
                    "("
            );

    @Override
    protected List<String> getValidSamples() {
        return VALID_SAMPLES;
    }

    @Override
    protected List<String> getInvalidSamples() {
        return INVALID_SAMPLES;
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
