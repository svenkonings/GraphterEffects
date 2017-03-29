import graafvis.GraafvisParser;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class ClauseTest extends GrammarTest {

    private static final List<String> CLAUSES = Arrays.asList
            (
                    "%s.",
                    "%s->%s.",
                    "%s,%s->%s,%s.",
                    "%s or %s -> %s,%s.",
                    "not (%s and %s) -> %s."
            );

    public static final List<String> VALID_SAMPLES = new ArrayList<>();
    static {
        List<String> antecedents = AntecedentTest.VALID_SAMPLES;
        List<String> consequences = ConsequenceTest.VALID_SAMPLES;
        // Add clause 0
        for (String sample : consequences) {
            VALID_SAMPLES.add(String.format(CLAUSES.get(0), sample));
        }
        // Add clause 1
        for (String sample : consequences) {
            VALID_SAMPLES.add(String.format(CLAUSES.get(1), antecedents.get(0), sample));
        }
        for (String sample : antecedents) {
            VALID_SAMPLES.add(String.format(CLAUSES.get(1), sample, consequences.get(0)));
        }
        // Add clause 2
        for (String sample : consequences) {
            VALID_SAMPLES.add(String.format(CLAUSES.get(2), antecedents.get(0), antecedents.get(0), sample, sample));
        }
        for (String sample : antecedents) {
            VALID_SAMPLES.add(String.format(CLAUSES.get(2), sample, sample, consequences.get(0), consequences.get(0)));
        }
        // Add clause 3
        for (String sample : consequences) {
            VALID_SAMPLES.add(String.format(CLAUSES.get(3), antecedents.get(0), antecedents.get(0), sample, sample));
        }
        for (String sample : antecedents) {
            VALID_SAMPLES.add(String.format(CLAUSES.get(3), sample, sample, consequences.get(0), consequences.get(0)));
        }
        // Add clause 4
        for (String sample : consequences) {
            VALID_SAMPLES.add(String.format(CLAUSES.get(4), antecedents.get(0), antecedents.get(0), sample));
        }
        for (String sample : antecedents) {
            VALID_SAMPLES.add(String.format(CLAUSES.get(4), sample, sample, consequences.get(0)));
        }
    }

    public static final List<String> INVALID_SAMPLES = Arrays.asList
            (
                    "",
                    "",
                    "->p(x).",
                    "p(x)->.",
                    "p(x) p(z).",
                    "p(x), p(x) -> q(x) or p(x).",
                    "X(p)."
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
        return parser.clause();
    }

    @Override
    protected String getRuleName() {
        return "clause";
    }
}
