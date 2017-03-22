import org.antlr.v4.runtime.ParserRuleContext;

/**
 *
 */
public class PredicateTest extends GrammarTest {

    private static final String[] VALID_SAMPLES = new String[]
            {
                    "predicate",
                    "p",
                    "p_q",
                    "p1",
                    "p_",
                    "p1_"
            };
    private static final String[] INVALID_SAMPLES = new String[]
            {
                    "Predicate",
                    "1p",
                    "",
                    "_predicate",
                    "!@#predicate"
            };

    @Override
    protected String[] getValidSamples() {
        return VALID_SAMPLES;
    }

    @Override
    protected String[] getInvalidSamples() {
        return INVALID_SAMPLES;
    }

    @Override
    protected ParserRuleContext parse(GraafvisParser parser) {
        return parser.predicate();
    }

    @Override
    protected String getRuleName() {
        return "predicate";
    }

}
