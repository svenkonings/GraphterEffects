import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class LiteralTest extends GrammarTest {

    public static final List<String> VALID_SAMPLES = new ArrayList<>();
    static {
        VALID_SAMPLES.addAll(AtomTest.VALID_SAMPLES);
        VALID_SAMPLES.addAll(MultiAtomTest.VALID_SAMPLES);
        VALID_SAMPLES.addAll(BoolExprTest.VALID_SAMPLES);
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
