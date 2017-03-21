import org.antlr.v4.runtime.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GrammarTest {


    @Test
    public void testAtom() {
        final String[] rightAtoms = new String[]
                {
//                      Standard predicates
                        "p(x)",
                        "q(X)",
                        "p(0)",
                        "p()",
                        "p(_)",
                        "predicate(X)",
                        "predi_cate(X)",
                        "pred1cate(1)",
//                      Multiple terms
                        "p(x,y,z)",
                        "predicate(x, y  , z)",
                        "predicate(X,0,abc )",
                        "q(X,_)",
//                      Tuple terms
                        "predicate((x))",
                        "p((x,y))",
                        "q((a,b),(c,d))",
                        "q((a,_),(_,_))"
                };
        final String[] wrongAtoms = new String[]
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

        for (String atom : rightAtoms) {
            Assert.assertEquals(atom, 0, parseAtom(atom).size());
        }
        for (String atom : wrongAtoms) {
            Assert.assertNotEquals(atom, 0 , parseAtom(atom).size());
        }

    }

    public List<Error> parseAtom(String text) {
        GraafvisParser parser = getGraafvisParser(text);
        parser.atom();
        List<Error> errors = new ArrayList<>();
        for (ANTLRErrorListener listener : parser.getErrorListeners()) {
            if (listener instanceof ErrorListener) {
                errors.addAll(((ErrorListener) listener).getErrors());
            }
        }
        return errors;
    }

    public GraafvisParser getGraafvisParser(String text) {
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


    /**
     * Antlr error listener to collect errors rather than send them to stderr.
     */
    class ErrorListener extends BaseErrorListener {
        /**
         * Errors collected by the listener.
         */
        private final List<Error> errors;

        public ErrorListener() {
            this.errors = new ArrayList<>();
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                                Object offendingSymbol, int line, int charPositionInLine,
                                String msg, RecognitionException e) {
            this.errors.add(new SyntaxError(line, charPositionInLine, msg));
        }

        /**
         * Indicates if the listener has collected any errors.
         */
        public boolean hasErrors() {
            return !this.errors.isEmpty();
        }

        /**
         * Returns the (possibly empty) list of errors collected by the listener.
         */
        public List<Error> getErrors() {
            return this.errors;
        }
    }

    class SyntaxError extends Error {

        public SyntaxError(int row, int column) {
            super(row, column);
            this.setMsg("Syntax Error");
        }

        public SyntaxError(int row, int column, String msg) {
            super(row, column);
            this.setMsg("Syntax Error: " + msg);
        }


    }

    abstract class Error {

        private final int row;
        private final int column;

        private String msg;

        Error(int row, int column) {
            this.row = row;
            this.column = column;
            this.msg = "D'OH";
        }

        public Error(int row, int column, String msg) {
            this.row = row;
            this.column = column;
            this.msg = msg;

        }

        @Override
        public String toString() {
            return String.format("D'OH at line %d:%d - %s", row, column, msg);
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }


    }

}
