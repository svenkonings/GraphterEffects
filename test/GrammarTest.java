import graafvis.ErrorListener;
import graafvis.GraafvisLexer;
import graafvis.GraafvisParser;
import org.antlr.v4.runtime.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public abstract class GrammarTest {

    /**
     * Flags
     */

    private static final boolean PRINT_PROCESS = false;
    private static final boolean LOG_PROCESS = true;

    /**
     * Log
     */

    private static PrintWriter writer;
    static {
        try {
            writer = new PrintWriter("test/testLog.txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Token samples
     */

    protected static final List<String> VALID_STRING_SAMPLES = Arrays.asList
            (
                    "\"a\"",
                    "\"\"",
                    "\"123\"",
                    "\"abc\"",
                    "\"a1b\"",
                    "\"a%^&#\"",
                    "\"a_b\""
            );
    protected static final List<String> VALID_NUMBER_SAMPLES = Arrays.asList
            (
                    "1",
                    "0123",
                    "0",
                    "921345156224167352"
            );
    protected static final List<String> VALID_ID_SAMPLES = Arrays.asList
            (
                    "a",
                    "a_b",
                    "aAa",
                    "a123",
                    "a_"
            );
    protected static final List<String> VALID_NUM_OP_SAMPLES = Arrays.asList
            (
                    "-",
                    "+",
                    "%",
                    "*",
                    "/",
                    "^"
            );
    protected static final List<String> VALID_EQ_OP_SAMPLES = Arrays.asList
            (
                    "==",
                    "<",
                    ">="
            );
    protected static final List<String> VALID_BOOL_OP_SAMPLES = Arrays.asList
            (
                    ",",
                    "and",
                    "or"
            );

    /**
     * Abstract methods
     */

    protected abstract List<String> getValidSamples();

    protected abstract List<String> getInvalidSamples();

    protected abstract ParserRuleContext parse(GraafvisParser parser);

    protected abstract String getRuleName();

    /**
     * Methods
     */

    @Test
    public void test() {
        for (String sample : getValidSamples()) {
            GraafvisParser parser = getGraafvisParser(sample);
            parse(parser);
            myAssertEquals(getRuleName(), sample, 0, countErrors(parser));
        }
        for (String sample : getInvalidSamples()) {
            GraafvisParser parser = getGraafvisParser(sample);
            parse(parser);
            myAssertNotEquals(getRuleName(), sample, 0, countErrors(parser));
        }
    }

    private static void myAssertEquals(String type, String sample, int expected, int actual) {
        if (PRINT_PROCESS | LOG_PROCESS) {
            String msg = String.format("Parsed %s: %s | Expected %d graafvis.errors. Found %d\n", type, sample, expected, actual);
            if (PRINT_PROCESS) {
                System.out.print(msg);
            }
            if (LOG_PROCESS) {
                writer.write(msg);
                writer.flush();
            }
        }
        Assert.assertEquals(sample, expected, actual);
    }

    private static void myAssertNotEquals(String type, String sample, int unexpected, int actual) {
        if (PRINT_PROCESS | LOG_PROCESS) {
            String msg = String.format("Parsed %s: %s | Did not expect %d graafvis.errors. Found %d\n", type, sample, unexpected, actual);
            if (PRINT_PROCESS) {
                System.out.print(msg);
            }
            if (LOG_PROCESS) {
                writer.write(msg);
                writer.flush();
            }
        }
        Assert.assertNotEquals(sample, unexpected, actual);
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
