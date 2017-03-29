package graafvis.grammar;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class GroundTermTest extends GrammarTest {

    public static final List<String> VALID_SAMPLES = new ArrayList<>();
    static {
        VALID_SAMPLES.addAll(VALID_STRING_SAMPLES);
        VALID_SAMPLES.addAll(VALID_NUMBER_SAMPLES);
        VALID_SAMPLES.addAll(VALID_ID_SAMPLES);
    }

    public static final List<String> INVALID_SAMPLES = Arrays.asList
            (
                    "",
                    "A",
                    "A_name",
                    "@#$",
                    "_a"
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
        return parser.ground_term();
    }

    @Override
    protected String getRuleName() {
        return "ground term";
    }
}
