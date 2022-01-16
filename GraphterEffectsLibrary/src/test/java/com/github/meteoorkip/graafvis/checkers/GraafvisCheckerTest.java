package com.github.meteoorkip.graafvis.checkers;

import com.github.meteoorkip.graafvis.ErrorListener;
import com.github.meteoorkip.graafvis.errors.VisError;
import com.github.meteoorkip.graafvis.grammar.GraafvisLexer;
import com.github.meteoorkip.graafvis.grammar.GraafvisParser;
import com.github.meteoorkip.graafvis.warnings.Warning;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 */
public class GraafvisCheckerTest {

    private static final String PROGRAM_1 =
            "node labels: \"Wolf\".\n" +
            "edge labels: \"Boat\" as boat.\n" +
            "node(X), boat(X) -> predicate(X).\n" +
            "node(X) -> edge(X,Y).\n" +
            "predicate(X) -> boat(_).\n" +
            "predicate(X, Y) -> goat(Y)."
    ;

    @Test
    public void test() {
        GraafvisParser.ProgramContext program = parse(PROGRAM_1);
        CheckerResult checkerResult = new GraafvisChecker().check(program);

        List<VisError> errors = checkerResult.getErrors();
        assertEquals(3, errors.size());
        assertEquals("Could not turn Wolf into a functor at line 1:0", errors.get(0).toString());
        assertEquals("Found blacklisted functor \"edge\" at line 4:11", errors.get(1).toString());
        assertEquals("Introduced a new variable \"Y\" in the consequence of a clause at line 4:18.", errors.get(2).toString());

        List<Warning> warnings = checkerResult.getWarnings();
        assertEquals(2, warnings.size());
        assertEquals("Singleton variable X found at line 5:10.", warnings.get(0).toString());
    }

    private static GraafvisParser.ProgramContext parse(String program) {
        CharStream stream = new ANTLRInputStream(program);
        GraafvisLexer lexer = new GraafvisLexer(stream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        GraafvisParser parser = new GraafvisParser(tokenStream);
        ErrorListener errorListener = new ErrorListener();
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        return parser.program();
    }


}