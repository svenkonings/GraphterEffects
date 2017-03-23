import org.antlr.v4.runtime.ParserRuleContext;

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
    private static final String[] INVALID_SAMPLES = new String[]
            {
                    "()",
                    ")X + 1(",
                    "_ + 1",
                    "1 1",
                    "1 ==",
                    "== 1",
                    "A # B",
                    ""
            };


    @Override
    protected String[] getValidSamples() {



        return new String[0];
    }

    @Override
    protected String[] getInvalidSamples() {
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
