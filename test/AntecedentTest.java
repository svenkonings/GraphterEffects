import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

/**
 *
 */
public class AntecedentTest extends GrammarTest {

    public static final List<String> VALID_SAMPLES = PropositionalFormulaTest.VALID_SAMPLES;

    public static final List<String> INVALID_SAMPLES = PropositionalFormulaTest.INVALID_SAMPLES;

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
        return parser.antecedent();
    }

    @Override
    protected String getRuleName() {
        return "antecedent";
    }
}
