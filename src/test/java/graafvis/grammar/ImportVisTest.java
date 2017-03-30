package graafvis.grammar;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class ImportVisTest extends GrammarTest {

    private static final List<String> LINES = Arrays.asList
            (
                    "consult %s."
            );

    public static final List<String> VALID_SAMPLES = new ArrayList<>();
    static {
        // Add line 0
        for (String sample : VALID_STRING_SAMPLES) {
            VALID_SAMPLES.add(String.format(LINES.get(0), sample));
        }
    }

    public static final List<String> INVALID_SAMPLES = Arrays.asList
            (
                    "consult wolf.",
                    "consult \"wolf\"",
                    "consult."
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
        return parser.import_vis();
    }

    @Override
    protected String getRuleName() {
        return "import";
    }

}
