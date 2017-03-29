package graafvis;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class PropositionalFormulaTest extends GrammarTest {

    private static final List<String> FORMULAS = Arrays.asList
            (
                    "%s",
                    "not %s",
                    "not (%s)",
                    "(%s)",
                    "%s %s %s",
                    "(%s %s %s)"
            );

    public static final List<String> VALID_SAMPLES = new ArrayList<>();
    static {
        List<String> literals = LiteralTest.VALID_SAMPLES;
        List<String> ops = VALID_BOOL_OP_SAMPLES;
        // Add formula 0
        for (String sample : literals) {
            VALID_SAMPLES.add(String.format(FORMULAS.get(0), sample));
        }
        // Add formula 1
        for (String sample : literals) {
            VALID_SAMPLES.add(String.format(FORMULAS.get(1), sample));
        }
        // Add formula 2
        for (String sample : literals) {
            VALID_SAMPLES.add(String.format(FORMULAS.get(2), sample));
        }
        // Add formula 3
        for (String sample : literals) {
            VALID_SAMPLES.add(String.format(FORMULAS.get(3), sample));
        }
        // Add formula 4
        for (String sample : literals) {
            VALID_SAMPLES.add(String.format(FORMULAS.get(4), sample, ops.get(0), sample));
        }
        for (String op : ops) {
            VALID_SAMPLES.add(String.format(FORMULAS.get(4), literals.get(0), op, literals.get(0)));
        }
        // Add formula 5
        for (String sample : literals) {
            VALID_SAMPLES.add(String.format(FORMULAS.get(5), sample, ops.get(0), sample));
        }
        for (String op : ops) {
            VALID_SAMPLES.add(String.format(FORMULAS.get(5), literals.get(0), op, literals.get(0)));
        }
    }

    public static final List<String> INVALID_SAMPLES = Arrays.asList
            (
                    "",
                    ")p(X)(",
                    "(p(X)",
                    "p(x) p(x)",
                    "p(p(x))",
                    "p((x",
                    "(x)",
                    ",",
                    ", p(x)"
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
        return parser.propositional_formula();
    }

    @Override
    protected String getRuleName() {
        return "propositional formula";
    }
}
