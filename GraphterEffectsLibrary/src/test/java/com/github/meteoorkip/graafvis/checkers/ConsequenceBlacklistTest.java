package com.github.meteoorkip.graafvis.checkers;

import com.github.meteoorkip.graafvis.ErrorListener;
import com.github.meteoorkip.graafvis.errors.VisError;
import com.github.meteoorkip.graafvis.grammar.GraafvisLexer;
import com.github.meteoorkip.graafvis.grammar.GraafvisParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 *
 */
public class ConsequenceBlacklistTest {

    @Test
    public void testDefaultBlackList() {
        validityCheck("node(X) -> a(X).");
        validityCheck("edge(X) -> a(X).");
        validityCheck("node{X, Y} -> a(X), a(Y).");

        invalidityCheck("node(x).");
        invalidityCheck("a(X) -> node(X).");
        invalidityCheck("a(X), b(Y) -> edge(X, Y).");
    }

    @Test
    public void testGeneratedBlackList() {
        validityCheck("wolf(x).");
        validityCheck("node labels: \"goat\" as boat. goat(x).");
        validityCheck("node labels: \"#@$%\" as wolf. test(x).");

        invalidityCheck("node labels: \"wolf\". wolf(x).");
        invalidityCheck("node labels: \"#olf\" as wolf. wolf(x).");
        invalidityCheck("node labels: \"wolf\" as wolf. a(x) -> wolf(x).");
    }

    private void validityCheck(String program) {
        assertEquals(0, check(program).size());
    }

    private void invalidityCheck(String program) {
        assertNotEquals(0, check(program).size());
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