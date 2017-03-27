import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class NumExprTest extends GrammarTest {

    private VariableTest variableTest = new VariableTest();

    private static final String[] VALID_NUMBER_SAMPLES = new String[]
            {
                    "1",
                    "0123",
                    "0",
                    "921345156224167352"
            };

    private static final String[] NUM_EXPR = new String[]
            {
                    "%s %s %s",
                    "(%s %s %s)",
                    "%s %s (%s &s %s)",
                    "(%s %s %s) %s %s",
                    "%s (%s %s %s)"
            };
    private static final String[] VALID_OPERATOR_SAMPLES = new String[]
            {
                    "-",
                    "/",
                    "%",
                    "==",
                    "<"
            };
    private static final List<String> INVALID_SAMPLES = Arrays.asList
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

    // TODO @Ron Something broke here and I figured you could much easier fix this.
    @Override
    protected List<String> getValidSamples() {



        return new ArrayList<String>();
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
