package graafvis.grammar;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class LiteralTest extends GrammarTest {

    private static final List<String> EQ_EXPR = Arrays.asList
            (
                    "%s %s %s"
            );

    public static final List<String> VALID_SAMPLES = new ArrayList<>();
    static {
        VALID_SAMPLES.addAll(AtomTest.VALID_SAMPLES);
        VALID_SAMPLES.addAll(MultiAtomTest.VALID_SAMPLES);
        // Add eq expr
        for (String sample : NumExprTest.VALID_SAMPLES) {
            VALID_SAMPLES.add(String.format(EQ_EXPR.get(0), sample, VALID_EQ_OP_SAMPLES.get(0), sample));
        }
        for (String sample : VALID_EQ_OP_SAMPLES) {
            VALID_SAMPLES.add(String.format(EQ_EXPR.get(0), NumExprTest.VALID_SAMPLES.get(0), sample, NumExprTest.VALID_SAMPLES.get(0)));
        }
    }

    public static final List<String> INVALID_SAMPLES = Arrays.asList
            (
                    "",
                    "_",
                    "test",
                    "_(A)"
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
        return parser.literal();
    }

    @Override
    protected String getRuleName() {
        return "literal";
    }

}
