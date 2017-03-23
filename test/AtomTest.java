import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class AtomTest extends GrammarTest {

    private static final List<String> ATOMS = Arrays.asList
            (
                    "%s(%s)",
                    "%s(%s,%s)",
                    "%s(%s,%s,%s)",
                    "%s()"
            );

    public static final List<String> VALID_SAMPLES = new ArrayList<>();
    static {
        List<String> terms = TermTest.VALID_SAMPLES;
        List<String> predicates = PredicateTest.VALID_SAMPLES;
        // Add atom 0
        for (String predicate : predicates) {
            VALID_SAMPLES.add(String.format(ATOMS.get(0), predicate, terms.get(0)));
        }
        for (String term : terms) {
            VALID_SAMPLES.add(String.format(ATOMS.get(0), predicates.get(0), term));
        }
        // Add atom 1
        for (String predicate : predicates) {
            VALID_SAMPLES.add(String.format(ATOMS.get(1), predicate, terms.get(0), terms.get(0)));
        }
        for (String term : terms) {
            VALID_SAMPLES.add(String.format(ATOMS.get(1), predicates.get(0), term, term));
        }
        // Add atom 2
        for (String predicate : predicates) {
            VALID_SAMPLES.add(String.format(ATOMS.get(2), predicate, terms.get(0), terms.get(0), terms.get(0)));
        }
        for (String term : terms) {
            VALID_SAMPLES.add(String.format(ATOMS.get(2), predicates.get(0), term, term, term));
        }
        // Add atom 3
        for (String predicate : predicates) {
            VALID_SAMPLES.add(String.format(ATOMS.get(3), predicate));
        }
    }

    public static final List<String> INVALID_SAMPLES = Arrays.asList
            (
                    "test",
                    "p)(",
                    "P(x)",
                    "_p()",
                    "4p(X)",
                    "p(()",
                    "p((,))",
                    "p(x,,y)",
                    "p@$%(x)",
                    "p((x, 2)",
                    "p(q(x))",
                    "q(3,)",
                    ""
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
        return parser.atom();
    }

    @Override
    protected String getRuleName() {
        return "atom";
    }
}
