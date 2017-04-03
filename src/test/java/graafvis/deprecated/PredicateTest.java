package graafvis.deprecated;

import graafvis.grammar.GraafvisParser;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class PredicateTest extends GrammarTest {

    public static final List<String> VALID_SAMPLES = Arrays.asList
            (
                    "predicate",
                    "p",
                    "p_q",
                    "p1",
                    "p_",
                    "p1_",
                    "pP"
            );

    public static final List<String> INVALID_SAMPLES = Arrays.asList
            (
                    "Predicate",
                    "1p",
                    "",
                    "_predicate",
                    "!@#predicate"
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
        return parser.predicate();
    }

    @Override
    protected String getRuleName() {
        return "predicate";
    }

}
