package com.github.meteoorkip.graafvis.checkers;

import com.github.meteoorkip.graafvis.ErrorListener;
import com.github.meteoorkip.graafvis.errors.VisError;
import com.github.meteoorkip.graafvis.grammar.GraafvisLexer;
import com.github.meteoorkip.graafvis.grammar.GraafvisParser;
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
public class VariableUsageCheckTest {

    @Test
    public void test() {
        validityCheck("node(X) -> a(X).");
        validityCheck("a(X), b(X) -> c(X).");
        validityCheck("a(X, Y) -> b(X).");
        validityCheck("a(X, X) -> b(X).");
        validityCheck("a{X, Y} -> b(Y).");
        validityCheck("a{(X, Y), Z} -> b(Y).");
        validityCheck("a(X, _) -> b(X).");
        validityCheck("a() -> b().");
        validityCheck("a() -> b().");

        invalidityCheck("a(X) -> b(Y).");
        invalidityCheck("a(X) -> b(X, Y).");
        invalidityCheck("a() -> b(X).");
        invalidityCheck("a(X), b() -> c(Y).");
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
        VariableUsageCheck checker = new VariableUsageCheck();
        ctx.accept(checker);
        return checker.getErrors();
    }


}