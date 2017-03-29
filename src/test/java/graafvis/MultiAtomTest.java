package graafvis;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class MultiAtomTest extends GrammarTest {

    private static final List<String> MULTI_ATOMS = Arrays.asList
            (
                    "%s{%s}",
                    "%s{%s,%s}"
            );

    public static final List<String> VALID_SAMPLES = new ArrayList<>();
    static {
        List<String> predicates = PredicateTest.VALID_SAMPLES;
        List<String> terms = TermTest.VALID_SAMPLES;

        // Atom type 0
        for (String predicate : predicates) {
            VALID_SAMPLES.add(String.format(MULTI_ATOMS.get(0), predicate, terms.get(0)));
        }
        for (String term : terms) {
            VALID_SAMPLES.add(String.format(MULTI_ATOMS.get(0), predicates.get(0), term));
        }
        // Atom type 1
        for (String predicate : predicates) {
            VALID_SAMPLES.add(String.format(MULTI_ATOMS.get(1), predicate, terms.get(0), terms.get(0)));
        }
        for (String term : terms) {
            VALID_SAMPLES.add(String.format(MULTI_ATOMS.get(1), predicates.get(0), term, term));
        }

    }

    public static final List<String> INVALID_SAMPLES = Arrays.asList
            (
                    "Predicate{}",
                    "predicate()",
                    "p{1,}",
                    "_p{}",
                    "p{()}",
                    "p{}",
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
        return parser.multi_atom();
    }

    @Override
    protected String getRuleName() {
        return "multi atom";
    }
}
