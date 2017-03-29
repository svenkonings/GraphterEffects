package graafvis;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class EdgeLabelGenTest extends GrammarTest {

    private static final List<String> LINES = Arrays.asList
            (
                    "edge labels: %s.",
                    "edge labels: %s, %s."
            );

    public static final List<String> VALID_SAMPLES = new ArrayList<>();
    static {
        // Add line 0
        for (String sample : LabelTest.VALID_SAMPLES) {
            VALID_SAMPLES.add(String.format(LINES.get(0), sample));
        }
        // Add line 1
        for (String sample : LabelTest.VALID_SAMPLES) {
            VALID_SAMPLES.add(String.format(LINES.get(0), sample, sample));
        }
    }

    public static final List<String> INVALID_SAMPLES = Arrays.asList
            (
                    "",
                    "edge labels:.",
                    "edge labels: \"wolf\"",
                    "edge labels: wolf.",
                    "edge labels: \"Wolf\" as _id."
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
        return parser.edge_label_gen();
    }

    @Override
    protected String getRuleName() {
        return "edge label gen";
    }

}
