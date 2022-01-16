package com.github.meteoorkip.graafvis.checkers;

import com.github.meteoorkip.graafvis.ErrorListener;
import com.github.meteoorkip.graafvis.grammar.GraafvisLexer;
import com.github.meteoorkip.graafvis.grammar.GraafvisParser;
import com.github.meteoorkip.graafvis.warnings.Warning;
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
public class SingletonVariablesCheckTest {

    @Test
    public void test() {
        validityCheck("p(X) -> q(X).");
        validityCheck("p(X, Y) -> q(X), q(Y).");
        validityCheck("p(X), p(Y) -> q(X,Y).");
        validityCheck("p([X|[Y]]) -> q(X), q(Y).");
        validityCheck("p(q(X)) -> q(X).");
        validityCheck("q(x) -> p(q(x)).");
        validityCheck("p(x).");
        validityCheck("p(X, X) -> q(a).");
        validityCheck("p(X), q(X) -> q(a).");

        invalidityCheck("p(X) -> p(a).");
        invalidityCheck("p(X, Y) -> p(X).");
        invalidityCheck("p([X|[]]) -> q(x).");
        invalidityCheck("p{(X, Y)} -> q(Y).");
    }

    private void validityCheck(String program) {
        assertEquals(0, check(program).size());
    }

    private void invalidityCheck(String program) {
        assertNotEquals(0, check(program).size());
    }

    /** Run the checker on a program. Return resulting graafvis.errors */
    private ArrayList<Warning> check(String program) {
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
        SingletonVariablesCheck checker = new SingletonVariablesCheck();
        ctx.accept(checker);
        return checker.getWarnings();
    }


}