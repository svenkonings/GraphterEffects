import org.antlr.v4.runtime.ParserRuleContext;

/**
 *
 */
public class AtomTest extends GrammarTest {

    public static final String[] VALID_ATOMS = new String[]
            {
                    "p(x)",
                    "q(X)",
                    "p(0)",
                    "p()",
                    "p(_)",
                    "predicate(X)",
                    "predi_cate(X)",
                    "pred1cate(1)",
                    "p(x,y,z)",
                    "predicate(x, y  , z)",
                    "predicate(X,0,abc )",
                    "q(X,_)",
                    "predicate((x))",
                    "p((x,y))",
                    "q((a,b),(c,d))",
                    "q((a,_),(_,_))",
                    "p(\tx,3)"
            };
    public static final String[] INVALID_ATOMS = new String[]
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
        return VALID_ATOMS;
    }

    @Override
    protected String[] getInvalidSamples() {
        return INVALID_ATOMS;
    }

    @Override
    protected ParserRuleContext parse(GraafvisParser parser) {
        return parser.atom();
    }


}
