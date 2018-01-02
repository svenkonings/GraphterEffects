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
public class LabelGenerationCheckTest {

    @Test
    public void testLabelCheck() {
        validityCheck("node labels: \"Wolf\" as wolf.");
        validityCheck("node labels: \"wolf\".");
        validityCheck("edge labels: \"!@#$\" as goat.");
        validityCheck("edge labels: \"go_at\".");
        validityCheck("node labels: \"`Something`\"");

        invalidityCheck("node labels: \"Wolf\".");
        invalidityCheck("node labels: \"_wolf\".");
        invalidityCheck("node labels: \"%^&$%\".");
        invalidityCheck("edge labels: \"Wolf\".");
        invalidityCheck("edge labels: \"_wolf\".");
        invalidityCheck("edge labels: \"%^&$%\".");
        invalidityCheck("edge labels: \"`wolf\".");
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
        LabelGenerationCheck checker = new LabelGenerationCheck();
        ctx.accept(checker);
        return checker.getErrors();
    }


}