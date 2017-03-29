package graafvis;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class VariableTest extends GrammarTest {

    public static final List<String> VALID_SAMPLES = Arrays.asList
            (
                    "A",
                    "ABC",
                    "Aa",
                    "A_bc",
                    "A_",
                    "A1"
            );

    public static final List<String> INVALID_SAMPLES = Arrays.asList
            (
                    "",
                    "aA",
                    "_A",
                    "#$%",
                    "a",
                    "1A",
                    "1"
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
        return parser.variable();
    }

    @Override
    protected String getRuleName() {
        return "variable";
    }

}
