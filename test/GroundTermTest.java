import org.antlr.v4.runtime.ParserRuleContext;

/**
 *
 */
public class GroundTermTest extends GrammarTest {

    public static final String[] VALID_STRING_SAMPLES = new String[]
            {
                    "\"\"",
                    "\"a\"",
                    "\"123\"",
                    "\"abc\"",
                    "\"a1b\"",
                    "\"a%^&#\"",
                    "\"a_b\""
            };

    public static final String[] VALID_NUMBER_SAMPLES = new String[]
            {
                    "1",
                    "0123",
                    "0",
                    "921345156224167352"
            };

    public static final String[] VALID_NAME_LO_SAMPLES = new String[]
            {
                    "a",
                    "a_b",
                    "aAa",
                    "a123",
                    "a_"
            };

    public static final String[] INVALID_SAMPLES = new String[]
            {
                    "",
                    "A",
                    "A_name",
                    "@#$",
                    "_a",
            };

    @Override
    protected String[] getValidSamples() {
        String[] result = new String[VALID_STRING_SAMPLES.length + VALID_NUMBER_SAMPLES.length + VALID_NAME_LO_SAMPLES.length];
        System.arraycopy(VALID_STRING_SAMPLES, 0, result, 0, VALID_STRING_SAMPLES.length);
        System.arraycopy(VALID_NUMBER_SAMPLES, 0, result, VALID_STRING_SAMPLES.length, VALID_NUMBER_SAMPLES.length);
        System.arraycopy(VALID_NAME_LO_SAMPLES, 0, result, VALID_STRING_SAMPLES.length + VALID_NUMBER_SAMPLES.length, VALID_NAME_LO_SAMPLES.length);
        return result;
    }

    @Override
    protected String[] getInvalidSamples() {
        return INVALID_SAMPLES;
    }

    @Override
    protected ParserRuleContext parse(GraafvisParser parser) {
        return parser.ground_term();
    }
}
