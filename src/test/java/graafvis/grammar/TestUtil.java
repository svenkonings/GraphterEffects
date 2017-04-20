package graafvis.grammar;

import graafvis.ErrorListener;
import org.antlr.v4.runtime.*;
import org.junit.Assert;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 */
abstract class TestUtil {

    /*
     * Flags
     */

    private static final boolean PRINT_PROCESS = false;
    private static final boolean LOG_PROCESS = true;

    /*
     * Log
     */

    private static final String TITLE_LINE = "###############################################################################################################";
    private static final String TITLE_TEXT = "\n# %s\n";
    private static final String TITLE = "\n" + TITLE_LINE + TITLE_TEXT + TITLE_LINE + "\n";

    private static PrintWriter writer;
    static {
        try {
            writer = new PrintWriter("src\\test\\java\\graafvis\\grammar\\testLog.txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    static void writeTitleInLog(String title) {
        printIfRequired(String.format(TITLE, title));
    }

    static void printIfRequired(String text) {
        if (PRINT_PROCESS) {
            System.out.print(text);
        }
        if (LOG_PROCESS) {
            writer.write(text);
            writer.flush();
        }
    }

    /*
     * Util
     */

    static GraafvisParser getGraafvisParserFor(String text) {
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

    /*
     * Assertions
     */

    static void assertHasErrors(GraafvisParser parser) {
        int errors = countErrors(parser);
        String msg = String.format("Parsed: %-64s | Did not expect 0 errors. Actual: %d\n", parser.getTokenStream().getText(), errors);
        printIfRequired(msg);
        Assert.assertNotEquals(msg, 0, errors);
    }

    static void assertHasNoErrors(GraafvisParser parser) {
        int errors = countErrors(parser);
        String msg = String.format("Parsed: %-64s |       Expected 0 errors. Actual: %d\n", parser.getTokenStream().getText(), errors);
        printIfRequired(msg);
        Assert.assertEquals(msg,0, errors);
    }


}
