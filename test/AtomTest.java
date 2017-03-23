import org.antlr.v4.runtime.ParserRuleContext;

/**
 *
 */
public class AtomTest extends GrammarTest {

    private PredicateTest predicateTest = new PredicateTest();
    private TermTest termTest = new TermTest();

    private static final String[] ATOMS = new String[]
            {
                    "%s(%s)",
                    "%s(%s,%s)",
                    "%s(%s,%s,%s)",
                    "%s()"
            };
    private static final String[] INVALID_SAMPLES = new String[]
            {
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
            };


    @Override
    protected String[] getValidSamples() {
        String[] validTerms = termTest.getValidSamples();
        String[] validPredicates = predicateTest.getValidSamples();

        int atomCount = 0;
        // Atom 0
        atomCount += validPredicates.length + validTerms.length;
        // Atom 1
        atomCount += validPredicates.length + (2 * validTerms.length);
        // Atom 2
        atomCount += validPredicates.length + (3 * validTerms.length);
        // Atom 3
        atomCount += validPredicates.length;
        // Create array to store possible atoms
        String[] samples = new String[atomCount];
        // Add atom examples
        int samplePointer = 0;
        // AtomType 0
        for (int i = 0; i < validPredicates.length; i++, samplePointer++) {
            samples[samplePointer] = String.format(ATOMS[0], validPredicates[i], validTerms[0]);
        }
        for (int i = 0; i < validTerms.length; i++, samplePointer++) {
            samples[samplePointer] = String.format(ATOMS[0], validPredicates[0], validTerms[i]);
        }
        // AtomType 1
        for (int i = 0; i < validPredicates.length; i++, samplePointer++) {
            samples[samplePointer] = String.format(ATOMS[1], validPredicates[i], validTerms[0], validTerms[0]);
        }
        for (int i = 0; i < validTerms.length; i++, samplePointer++) {
            samples[samplePointer] = String.format(ATOMS[1], validPredicates[0], validTerms[i], validTerms[0]);
        }
        for (int i = 0; i < validTerms.length; i++, samplePointer++) {
            samples[samplePointer] = String.format(ATOMS[1], validPredicates[0], validTerms[0], validTerms[i]);
        }
        // AtomType 2
        for (int i = 0; i < validPredicates.length; i++, samplePointer++) {
            samples[samplePointer] = String.format(ATOMS[2], validPredicates[i], validTerms[0], validTerms[0], validTerms[0]);
        }
        for (int i = 0; i < validTerms.length; i++, samplePointer++) {
            samples[samplePointer] = String.format(ATOMS[2], validPredicates[0], validTerms[i], validTerms[0], validTerms[0]);
        }
        for (int i = 0; i < validTerms.length; i++, samplePointer++) {
            samples[samplePointer] = String.format(ATOMS[2], validPredicates[0], validTerms[0], validTerms[i], validTerms[0]);
        }
        for (int i = 0; i < validTerms.length; i++, samplePointer++) {
            samples[samplePointer] = String.format(ATOMS[2], validPredicates[0], validTerms[0], validTerms[0], validTerms[i]);
        }
        // AtomType 3
        for (int i = 0; i < validPredicates.length; i++, samplePointer++) {
            samples[samplePointer] = String.format(ATOMS[3], validPredicates[i]);
        }
        return samples;
    }

    @Override
    protected String[] getInvalidSamples() {
        return INVALID_SAMPLES;
    }

    @Override
    public void test() {
        predicateTest.test();
        termTest.test();
        super.test();
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
