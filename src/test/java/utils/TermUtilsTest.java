package utils;

import org.junit.Test;

import static prolog.TuProlog.*;
import static org.junit.Assert.assertEquals;

public class TermUtilsTest {

    @Test
    public void testTermToString() {
        assertEquals("a", TermUtils.termToString(struct("a")));
        assertEquals("b(X,Y)", TermUtils.termToString(struct("b", var("X"), var("Y"))));
        assertEquals("\"test1\"", TermUtils.termToString(struct("\"test1\"")));
        assertEquals("\"test2\"(X,Y)", TermUtils.termToString(struct("\"test2\"", var("X"), var("Y"))));
//        assertEquals("\"_test_3\"", TermUtils.termToString(term("\"_test_3\"")));
        assertEquals("test_4", TermUtils.termToString(term("test_4")));
        assertEquals("1", TermUtils.termToString(intVal(1)));
        assertEquals("2", TermUtils.termToString(number("2")));
        assertEquals("X", TermUtils.termToString(var("X")));
    }
}
