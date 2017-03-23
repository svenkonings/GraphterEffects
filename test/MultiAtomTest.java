import org.antlr.v4.runtime.ParserRuleContext;

/**
 *
 */
public class MultiAtomTest extends GrammarTest {

    private static final String[] MULTI_ATOMS = new String[]
            {
                    "%s{}",
                    "%s{%s}",
                    "%s{%s,%s}"
            };
    private static final String[] INVALID_SAMPLES = new String[]
            {
                    "Predicate{}",
                    "predicate()",
                    "p{1,}",
                    "_p{}",
                    "p{()}",
                    ""
            };

    private PredicateTest predicateTest = new PredicateTest();
    private TermTest termTest = new TermTest();

    @Override
    protected String[] getValidSamples() {
        String[] predicateSamples = predicateTest.getValidSamples();
        String[] termSamples = termTest.getValidSamples();

        int atomCount = 0;
        // Atom 0
        atomCount += predicateSamples.length;
        // Atom 1
        atomCount += predicateSamples.length + termSamples.length;
        // Atom 2
        atomCount += predicateSamples.length + (2 * termSamples.length);
        // Create array to store possible atoms
        String[] samples = new String[atomCount];
        // Add atom examples
        int samplePointer = 0;
        // AtomType 0
        for (int i = 0; i < predicateSamples.length; i++, samplePointer++) {
            samples[samplePointer] = String.format(MULTI_ATOMS[0], predicateSamples[i]);
        }
        // AtomType 1
        for (int i = 0; i < predicateSamples.length; i++, samplePointer++) {
            samples[samplePointer] = String.format(MULTI_ATOMS[1], predicateSamples[i], termSamples[0]);
        }
        for (int i = 0; i < termSamples.length; i++, samplePointer++) {
            samples[samplePointer] = String.format(MULTI_ATOMS[1], predicateSamples[0], termSamples[i]);
        }
        // AtomType 2
        for (int i = 0; i < predicateSamples.length; i++, samplePointer++) {
            samples[samplePointer] = String.format(MULTI_ATOMS[2], predicateSamples[i], termSamples[0], termSamples[0]);
        }
        for (int i = 0; i < termSamples.length; i++, samplePointer++) {
            samples[samplePointer] = String.format(MULTI_ATOMS[2], predicateSamples[0], termSamples[i], termSamples[0]);
        }
        for (int i = 0; i < termSamples.length; i++, samplePointer++) {
            samples[samplePointer] = String.format(MULTI_ATOMS[2], predicateSamples[0], termSamples[0], termSamples[i]);
        }
        return samples;
    }

    @Override
    protected String[] getInvalidSamples() {
        return INVALID_SAMPLES;
    }

    @Override
    protected ParserRuleContext parse(GraafvisParser parser) {
        return parser.multi_atom();
    }

    @Override
    protected String getRuleName() {
        return "multi_atom";
    }


}
