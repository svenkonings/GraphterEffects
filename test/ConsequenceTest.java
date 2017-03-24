import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class ConsequenceTest extends GrammarTest {

    private static final List<String> CONSEQUENCES = Arrays.asList
            (
                    "%s",
                    "%s , %s",
                    "%s , %s, %s"
            );

    public static final List<String> VALID_SAMPLES = new ArrayList<>();
    static {
        List<String> literals = LiteralTest.VALID_SAMPLES;
        // Add consequence 0
        for (String sample : literals) {
            VALID_SAMPLES.add(String.format(CONSEQUENCES.get(0), sample));
        }
        // Add consequence 1
        for (String sample : literals) {
            VALID_SAMPLES.add(String.format(CONSEQUENCES.get(0), sample, sample));
        }
        // Add consequence 2
        for (String sample : literals) {
            VALID_SAMPLES.add(String.format(CONSEQUENCES.get(0), sample, sample, sample));
        }
    }

    public static final List<String> INVALID_SAMPLES = Arrays.asList
            (
                    "",
                    "p(X) and p(Y)",
                    "p(X) or q(x)",
                    "P(X)"
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
        return parser.consequence();
    }

    @Override
    protected String getRuleName() {
        return "consequence";
    }
}
