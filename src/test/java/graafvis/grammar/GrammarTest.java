package graafvis.grammar;

import graafvis.ErrorListener;
import org.antlr.v4.runtime.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 */
public class GrammarTest {

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
            writer = new PrintWriter("src\\test\\java\\graafvis\\grammar\\testLog.txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Token samples
     */

//    protected static final List<String> VALID_ID_SAMPLES = Arrays.asList
//            (
//                    "a",
//                    "a_b",
//                    "aAa",
//                    "a123",
//                    "a_"
//            );
//
//    protected static final List<String> VALID_HID_SAMPLES = Arrays.asList
//            (
//                    "A",
//                    "A_b",
//                    "A_B",
//                    "Aa",
//                    "A_",
//                    "A123a"
//            );
//
//    protected static final List<String> VALID_AND_OP_SAMPLES = Arrays.asList
//            (
//                    ",",
//                    "and"
//            );
//
//    protected static final List<String> VALID_OR_OP_SAMPLES = Arrays.asList
//            (
//                    ";",
//                    "or"
//            );

    /*
     * Variables
     */

//    private static final List<String> VALID_VARIABLES = Arrays.asList
//            (
//                    "X",
//                    "Xx",
//                    "X123",
//                    "X_X",
//                    "X_",
//                    "ABC"
//            );
//    private static final List<String> INVALID_VARIABLES = Arrays.asList
//            (
//                    "xX",
//                    "_X",
//                    "1X",
//                    "",
//                    "1",
//                    "x"
//            );
//    @Test
//    public void testVariables() {
//        for (String sample : VALID_VARIABLES) {
//            assertAntecedentVariable(sample);
//            assertConsequenceVariable(sample);
//        }
//        for (String sample : INVALID_VARIABLES) {
//            assertNotAntecedentVariable(sample);
//            assertNotConsequenceVariable(sample);
//        }
//    }
//
//    private static void assertAntecedentVariable(String sample) {
//        printIfRequired(String.format("Parsed antecedent variable: %s\n", sample));
//        Assert.assertEquals(sample, true, getGraafvisParser(sample).aTerm() instanceof GraafvisParser.VariableAntecedentContext);
//    }
//
//    private static void assertNotAntecedentVariable(String sample) {
//        printIfRequired(String.format("Failed to parse antecedent variable: %s\n", sample));
//        Assert.assertEquals(sample, false, getGraafvisParser(sample).aTerm() instanceof GraafvisParser.VariableAntecedentContext);
//    }
//
//    private static void assertConsequenceVariable(String sample) {
//        printIfRequired(String.format("Parsed consequence variable: %s\n", sample));
//        Assert.assertEquals(sample, true, getGraafvisParser(sample).cTerm() instanceof GraafvisParser.VariableConsequenceContext);
//    }
//
//    private static void assertNotConsequenceVariable(String sample) {
//        printIfRequired(String.format("Failed to parse consequence variable: %s\n", sample));
//        Assert.assertEquals(sample, false, getGraafvisParser(sample).cTerm() instanceof GraafvisParser.VariableConsequenceContext);
//    }
//
//    /*
//     * String
//     */
//
//    private static final List<String> VALID_STRINGS = Arrays.asList
//            (
//                    "\"a\"",
//                    "\"\"",
//                    "\"123\"",
//                    "\"abc\"",
//                    "\"a1b\"",
//                    "\"a%^&#\"",
//                    "\"a_b\""
//            );
//    private static final List<String> INVALID_STRINGS = Arrays.asList
//            (
//                    "",
//                    "string",
//                    "\"",
//                    "\"test",
//                    "test\""
//            );
//    @Test
//    public void testStrings() {
//        final String type = "string";
//    }
//
//    private static void assertAntecedentString(String sample, Class c) {
//        printIfRequired(String.format("Parsed antecedent variable: %s\n", sample));
//        Assert.assertEquals(sample, true, getGraafvisParser(sample).aTerm() instanceof GraafvisParser.StringAntecedentContext);
//    }
//
//    private static void assertNotAntecedentString(String sample) {
//        printIfRequired(String.format("Failed to parse antecedent variable: %s\n", sample));
//        Assert.assertEquals(sample, false, getGraafvisParser(sample).aTerm() instanceof GraafvisParser.StringAntecedentContext);
//    }
//
//    private static void assertConsequenceString(String sample) {
//        printIfRequired(String.format("Parsed consequence variable: %s\n", sample));
//        Assert.assertEquals(sample, true, getGraafvisParser(sample).cTerm() instanceof GraafvisParser.StringConsequenceContext);
//    }
//
//    private static void assertNotConsequenceString(String sample) {
//        printIfRequired(String.format("Failed to parse consequence variable: %s\n", sample));
//        Assert.assertEquals(sample, false, getGraafvisParser(sample).cTerm() instanceof GraafvisParser.StringConsequenceContext);
//    }
//
//    /*
//     * Number
//     */
//
//    private static final List<String> VALID_NUMBERS = Arrays.asList
//            (
//                    "1",
//                    "0123",
//                    "0",
//                    "9876543210",
//                    "-1"
//            );
//    private static final List<String> INVALID_NUMBERS = Arrays.asList
//            (
//                    "a",
//                    "-a",
//                    "_1",
//                    "a2",
//                    "A1",
//                    ""
//            );
//    @Test
//    public void testNumbers() {
//        final String type = "number";
//    }

    /*
     * Helper methods
     */


    protected static void printIfRequired(String text) {
        if (PRINT_PROCESS) {
            System.out.print(text);
        }
        if (LOG_PROCESS) {
            writer.write(text);
            writer.flush();
        }
    }

//    private static void myAssertEquals(String type, String sample, int expected, int actual) {
//        if (PRINT_PROCESS | LOG_PROCESS) {
//            String msg = String.format("Parsed %s: %s | Expected %d graafvis.errors. Found %d\n", type, sample, expected, actual);
//            if (PRINT_PROCESS) {
//                System.out.print(msg);
//            }
//            if (LOG_PROCESS) {
//                writer.write(msg);
//                writer.flush();
//            }
//        }
//        Assert.assertEquals(sample, expected, actual);
//    }
//
//    private static void myAssertNotEquals(String type, String sample, int unexpected, int actual) {
//        if (PRINT_PROCESS | LOG_PROCESS) {
//            String msg = String.format("Parsed %s: %s | Did not expect %d graafvis.errors. Found %d\n", type, sample, unexpected, actual);
//            if (PRINT_PROCESS) {
//                System.out.print(msg);
//            }
//            if (LOG_PROCESS) {
//                writer.write(msg);
//                writer.flush();
//            }
//        }
//        Assert.assertNotEquals(sample, unexpected, actual);
//    }

    protected static GraafvisParser getGraafvisParser(String text) {
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