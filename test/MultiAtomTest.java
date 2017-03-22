import org.antlr.v4.runtime.ParserRuleContext;

/**
 *
 */
public class MultiAtomTest extends GrammarTest {

    public static final String[] VALID_SAMPLES = new String[]{};
    public static final String[] INVALID_SAMPLES = new String[]{};

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
        return parser.multi_atom();
    }

    @Override
    protected String getRuleName() {
        return "multi_atom";
    }


}
