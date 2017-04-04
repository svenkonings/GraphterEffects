package graafvis.grammar;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class TermTest extends GrammarTest{

    private static final List<String> LIST = Arrays.asList
            (
                    "[%s,%s]",
                    "[%s, [%s, %s]]",
                    "[[%s, [%s, %s]]]",
                    "[[%s,%s],[[%s,%s]]]",
                    "[%s|%s]",
                    "[%s,%s|%s,%s]",
                    "[%s, [%s|%s]]",
                    "[]"
            );

    public static final List<String> VALID_SAMPLES = new ArrayList<>();
    static {
        List<String> VARIABLES = VariableTest.VALID_SAMPLES;
        List<String> options = new ArrayList<>();
        options.addAll(VARIABLES);

        VALID_SAMPLES.addAll(VALID_STRING_SAMPLES);
        VALID_SAMPLES.addAll(VALID_NUMBER_SAMPLES);
        VALID_SAMPLES.addAll(VALID_ID_SAMPLES);
        VALID_SAMPLES.addAll(VARIABLES);
        // Add list 0
        for (String sample : options) {
            VALID_SAMPLES.add(String.format(LIST.get(0), sample, sample));
        }
        // Add list 1
        for (String sample : options) {
            VALID_SAMPLES.add(String.format(LIST.get(1), sample, sample, sample));
        }
        // Add list 2
        for (String sample : options) {
            VALID_SAMPLES.add(String.format(LIST.get(2), sample, sample, sample));
        }
        // Add list 3
        for (String sample : options) {
            VALID_SAMPLES.add(String.format(LIST.get(3), sample, sample, sample, sample));
        }
        // Add list 4
        for (String sample : options) {
            VALID_SAMPLES.add(String.format(LIST.get(4), sample, sample));
        }
        // Add list 5
        for (String sample : options) {
            VALID_SAMPLES.add(String.format(LIST.get(5), sample, sample, sample, sample));
        }
        // Add list 6
        for (String sample : options) {
            VALID_SAMPLES.add(String.format(LIST.get(6), sample, sample, sample));
        }
        // Add list 7
        VALID_SAMPLES.add(LIST.get(7));
    }

    public static final List<String> INVALID_SAMPLES = Arrays.asList
            (
                    "",
                    "[[]",
                    "[[,[1]]",
                    "[",
                    "[|]",
                    "[|1]",
                    "[1|]",
                    "[1|1|1]"
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
        return parser.term();
    }

    @Override
    protected String getRuleName() {
        return "term";
    }

}
