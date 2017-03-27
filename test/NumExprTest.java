import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class NumExprTest extends GrammarTest {

    private static final List<String> NUM_EXPR = Arrays.asList
            (
                    "%s %s %s",
                    "(%s %s %s)",
                    "%s %s (%s %s %s)",
                    "(%s %s %s) %s %s"
            );

    public static final List<String> VALID_SAMPLES = new ArrayList<>();
    static {
        List<String> vals = new ArrayList<>();
        vals.addAll(VALID_NUMBER_SAMPLES);
        vals.addAll(VariableTest.VALID_SAMPLES);

        // Add expr 0
        for (String sample : vals) {
            VALID_SAMPLES.add(String.format(NUM_EXPR.get(0), sample, VALID_NUM_OP_SAMPLES.get(0), sample));
        }
        for (String operator : VALID_NUM_OP_SAMPLES) {
            VALID_SAMPLES.add(String.format(NUM_EXPR.get(0), vals.get(0), operator, vals.get(0)));
        }
        // Add expr 1
        for (String sample : vals) {
            VALID_SAMPLES.add(String.format(NUM_EXPR.get(1), sample, VALID_NUM_OP_SAMPLES.get(0), sample));
        }
        for (String operator : VALID_NUM_OP_SAMPLES) {
            VALID_SAMPLES.add(String.format(NUM_EXPR.get(1), vals.get(0), operator, vals.get(0)));
        }
        // Add expr 2
        for (String sample : vals) {
            VALID_SAMPLES.add(String.format(NUM_EXPR.get(2), sample, VALID_NUM_OP_SAMPLES.get(0), sample, VALID_NUM_OP_SAMPLES.get(0), sample));
        }
        for (String operator : VALID_NUM_OP_SAMPLES) {
            VALID_SAMPLES.add(String.format(NUM_EXPR.get(2), vals.get(0), operator, vals.get(0), operator, vals.get(0)));
        }
        // Add expr 3
        for (String sample : vals) {
            VALID_SAMPLES.add(String.format(NUM_EXPR.get(3), sample, VALID_NUM_OP_SAMPLES.get(0), sample, VALID_NUM_OP_SAMPLES.get(0), sample));
        }
        for (String operator : VALID_NUM_OP_SAMPLES) {
            VALID_SAMPLES.add(String.format(NUM_EXPR.get(3), vals.get(0), operator, vals.get(0), operator, vals.get(0)));
        }
    }

    public static final List<String> INVALID_SAMPLES = Arrays.asList
            (
                    "()",
                    ")X + 1(",
                    "_ + 1",
                    "1 1",
                    "1 ==",
                    "== 1",
                    "A # B",
                    ""
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
        return parser.num_expr();
    }

    @Override
    protected String getRuleName() {
        return "num expr";
    }
}
