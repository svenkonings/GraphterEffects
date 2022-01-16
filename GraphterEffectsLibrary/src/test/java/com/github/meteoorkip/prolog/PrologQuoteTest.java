package com.github.meteoorkip.prolog;


import it.unibo.tuprolog.core.Atom;
import it.unibo.tuprolog.core.Struct;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrologQuoteTest {

    @Test
    public void noQuotes() {
        test("test");
    }

    @Test
    public void specialCharactersAllowed() {
        test("test!");
    }


    @Test
    public void doubleQuotesUnaffected() {
        test("\"test\"");
    }

    @Test
    public void includesQuotesInvalidCharacter() {
        test("\"test!\"");
    }

    @Test
    public void includesSingleQuotes() {
        test("'test'");
    }

    @Test
    public void includesSingleQuotesInvalidCharacter() {
        test("'test!'");
    }

    @Test
    public void twiceQuoted() {
        test("\"\"test\"\"");
    }

    @Test
    public void twiceQuotedInvalidCharacter() {
        test("\"\"test!\"\"");
    }

    @Test
    public void twiceSingleQuoted() {
        test("''test''");
    }

    @Test
    public void twiceSingleQuotedInvalidCharacter() {
        test("''test!''");
    }

    @Test
    public void QuoteSingleQuote() {
       test("\"'test'\"");
    }

    @Test
    public void QuoteSingleQuoteInvalidCharacter() {
        test("\"'test!'\"");
    }

    @Test
    public void SingleQuoteQuote() {
        test("'\"test\"'");
    }

    @Test
    public void singleQuoteQuoteInvalidCharacter() {
        test("'\"test!\"'");
    }


    private void test(String toTest) {
        assertEquals(toTest, Atom.of(toTest).getValue());
        assertEquals(toTest, Struct.of(toTest).getFunctor());
    }
}
