import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class LabelTest extends GrammarTest{

    public static final List<String> VALID_SAMPLES = Arrays.asList
            (
                    "\"Wolf\" as wolf",
                    "\"wolf\""
            );

    public static final List<String> INVALID_SAMPLES = Arrays.asList
            (
                    "",
                    "wolf",
                    "wolf as wolf",
                    "as wolf",
                    "\"Wolf\" as"
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
        return parser.label();
    }

    @Override
    protected String getRuleName() {
        return "label";
    }

}
