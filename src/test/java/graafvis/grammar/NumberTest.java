package graafvis.grammar;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static graafvis.grammar.TestUtil.*;

/**
 *
 */
public class NumberTest {

    static final ArrayList<String> VALID_NUMBER_SAMPLES = new ArrayList<>();
    static {
        VALID_NUMBER_SAMPLES.add("1");
        VALID_NUMBER_SAMPLES.add("1234567890");
        VALID_NUMBER_SAMPLES.add("1.1234567890");
        VALID_NUMBER_SAMPLES.add("0.123456789");
        VALID_NUMBER_SAMPLES.add("01");
        VALID_NUMBER_SAMPLES.add("-1");
        VALID_NUMBER_SAMPLES.add("-0.1");
        VALID_NUMBER_SAMPLES.add("-0");
        VALID_NUMBER_SAMPLES.add("-0.011");
    }

    static final ArrayList<String> INVALID_NUMBER_SAMPLES = new ArrayList<>();
    static {
        INVALID_NUMBER_SAMPLES.add("a");
        INVALID_NUMBER_SAMPLES.add("-");
        INVALID_NUMBER_SAMPLES.add("0.1.1");
        INVALID_NUMBER_SAMPLES.add("--1");
        INVALID_NUMBER_SAMPLES.add("1a");
        INVALID_NUMBER_SAMPLES.add(".1");
        INVALID_NUMBER_SAMPLES.add("0-1");
    }

    @Test
    public void runTest() {
        writeTitleInLog("Number Term Test");
        for (String sample : VALID_NUMBER_SAMPLES) {
            testValidAntecedentNumber(sample);
            testValidConsequenceNumber(sample);
        }
        for (String sample : INVALID_NUMBER_SAMPLES) {
            testInvalidAntecedentNumber(sample);
            testInvalidConsequenceNumber(sample);
        }
    }

    private static void testValidAntecedentNumber(String sample) {
        GraafvisParser parser = getGraafvisParserFor(sample);
        ParseTree ctx = parser.aTerm();
        Assert.assertTrue(ctx instanceof GraafvisParser.NumberAntecedentContext);
        assertHasNoErrors(parser);

    }

    private static void testInvalidAntecedentNumber(String sample) {
        GraafvisParser parser = getGraafvisParserFor(sample);
        ParseTree ctx = parser.aTerm();

        if (ctx instanceof GraafvisParser.NumberAntecedentContext) {
            assertHasErrors(parser);
        }
    }

    private static void testValidConsequenceNumber(String sample) {
        GraafvisParser parser = getGraafvisParserFor(sample);
        ParseTree ctx = parser.cTerm();
        Assert.assertTrue(ctx instanceof GraafvisParser.NumberConsequenceContext);
        assertHasNoErrors(parser);
    }

    private static void testInvalidConsequenceNumber(String sample) {
        GraafvisParser parser = getGraafvisParserFor(sample);
        ParseTree ctx = parser.cTerm();
        assertHasErrors(parser);
        Assert.assertFalse(ctx instanceof GraafvisParser.NumberConsequenceContext);
    }



}
