import org.antlr.v4.runtime.*;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public abstract class GrammarTest {

    protected abstract String[] getValidSamples();

    protected abstract String[] getInvalidSamples();

    protected abstract ParserRuleContext parse(GraafvisParser parser);

    @Test
    public void test() {
        for (String sample : getValidSamples()) {
            GraafvisParser parser = getGraafvisParser(sample);
            parse(parser);
            Assert.assertEquals(sample, 0, countErrors(parser));
        }
        for (String sample : getInvalidSamples()) {
            GraafvisParser parser = getGraafvisParser(sample);
            parse(parser);
            Assert.assertNotEquals(sample, 0, countErrors(parser));
        }
    }

    private static GraafvisParser getGraafvisParser(String text) {
        CharStream stream = new ANTLRInputStream(text);
        GraafvisLexer lexer = new GraafvisLexer(stream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        GraafvisParser parser = new GraafvisParser(tokenStream);
        ErrorListener errorListener = new ErrorListener();
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        return parser;
    }

    private static int countErrors(GraafvisParser parser) {
        int count = 0;
        for (ANTLRErrorListener listener : parser.getErrorListeners()) {
            if (listener instanceof ErrorListener) {
                count += ((ErrorListener) listener).getErrors().size();
            }
        }
        return count;
    }


}
