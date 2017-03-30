package graafvis.checkers;

import graafvis.ErrorListener;
import graafvis.errors.VisError;
import graafvis.grammar.GraafvisLexer;
import graafvis.grammar.GraafvisParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 *
 */
public class WildcardUsageCheckTest {

    @Test
    public void test() {
        validityCheck("a(_) -> b(x).");
        validityCheck("a(X, _) -> b(X).");
        validityCheck("a{_,X} -> b(X), c().");
        invalidityCheck("a(x)->b(_).");
        invalidityCheck("a(_).");
        invalidityCheck("a(X) -> b(X, _).");
    }

    private void validityCheck(String program) {
        Assert.assertEquals(0, check(program).size());
    }

    private void invalidityCheck(String program) {
        Assert.assertNotEquals(0, check(program).size());
    }

    /** Run the checker on a program. Return resulting graafvis.errors */
    private ArrayList<VisError> check(String program) {
        CharStream stream = new ANTLRInputStream(program);
        GraafvisLexer lexer = new GraafvisLexer(stream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        GraafvisParser parser = new GraafvisParser(tokenStream);
        ErrorListener errorListener = new ErrorListener();
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        GraafvisParser.ProgramContext ctx = parser.program();
        WildcardUsageCheck checker = new WildcardUsageCheck();
        ctx.accept(checker);
        return checker.getErrors();
    }

}