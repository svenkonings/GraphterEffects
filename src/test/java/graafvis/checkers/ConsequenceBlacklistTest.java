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
public class ConsequenceBlacklistTest {

    @Test
    public void testDefaultBlackList() {
        String validProgram1 = "node(X) -> a(X).";
        Assert.assertEquals(0, check(validProgram1).size());
        String validProgram2 = "edge(X) -> a(X).";
        Assert.assertEquals(0, check(validProgram2).size());
        String validProgram3 = "node{X, Y} -> a(X), a(Y).";
        Assert.assertEquals(0, check(validProgram3).size());

        String invalidProgram1 = "node(x).";
        Assert.assertNotEquals(0, check(invalidProgram1).size());
        String invalidProgram2 = "a(X) -> node(X).";
        Assert.assertNotEquals(0, check(invalidProgram2).size());
        String invalidProgram3 = "a(X), b(Y) -> edge(X, Y).";
        Assert.assertNotEquals(0, check(invalidProgram3).size());
    }

    @Test
    public void testGeneratedBlackList() {
        String validProgram1 = "wolf(x).";
        Assert.assertEquals(0, check(validProgram1).size());
        String validProgram2 = "node labels: \"goat\" as boat. goat(x).";
        Assert.assertEquals(0, check(validProgram2).size());
        String validProgram3 = "node labels: \"#@$%\" as wolf. test(x).";
        Assert.assertEquals(0, check(validProgram3).size());

        String invalidProgram1 = "node labels: \"wolf\". wolf(x).";
        Assert.assertNotEquals(0, check(invalidProgram1).size());
        String invalidProgram2 = "node labels: \"#olf\" as wolf. wolf(x).";
        Assert.assertNotEquals(0, check(invalidProgram2).size());
        String invalidProgram3 = "node labels: \"wolf\" as wolf. a(x) -> wolf(x).";
        Assert.assertNotEquals(0, check(invalidProgram3).size());
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
        ConsequenceBlacklist blacklist = new ConsequenceBlacklist();
        ctx.accept(blacklist);
        return blacklist.getErrors();
    }


}