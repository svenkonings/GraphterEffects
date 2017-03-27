import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

/**
 *
 */
public class NodeLabelGenTest extends GrammarTest {

//    private static final List<String>


    @Override
    protected List<String> getValidSamples() {
        return null;
    }

    @Override
    protected List<String> getInvalidSamples() {
        return null;
    }

    @Override
    protected ParserRuleContext parse(GraafvisParser parser) {
        return parser.node_label_gen();
    }

    @Override
    protected String getRuleName() {
        return "node label gen";
    }
}
