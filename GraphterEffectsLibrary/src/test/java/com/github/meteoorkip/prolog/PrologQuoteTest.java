package com.github.meteoorkip.prolog;

import alice.tuprolog.InvalidTermException;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PrologQuoteTest {

    @Test
    public void noQuotes() {
        Term termNormal = Term.createTerm("test");
        assertEquals(termNormal.toString(), "test");
        assertEquals(getName(termNormal), "test");

        Term structNormal = new Struct("test");
        assertEquals(structNormal.toString(), "test");
        assertEquals(getName(structNormal), "test");
        //prolog declare: "cat"
        //prolog get: "cat"
    }

    @Test
    public void invalidCharacter() {
        try {
            Term termInvalid = Term.createTerm("test!");
            fail();
        } catch (InvalidTermException ignored) {
        }

        Term structInvalid = new Struct("test!");
        assertEquals(structInvalid.toString(), "'test!'");
        assertEquals(getName(structInvalid), "test!");
    }


    @Test
    public void includesQuotes() {
        Term termNormalDoubleQuoted = Term.createTerm("\"test\"");
        assertEquals(termNormalDoubleQuoted.toString(), "test");
        assertEquals(getName(termNormalDoubleQuoted), "test");

        Term structNormalDoubleQuoted = new Struct("\"test\"");
        assertEquals(structNormalDoubleQuoted.toString(), "'\"test\"'");
        assertEquals(getName(structNormalDoubleQuoted), "\"test\"");
    }

    @Test
    public void includesQuotesInvalidCharacter() {
        Term termInvalidDoubleQuoted = Term.createTerm("\"test!\"");
        assertEquals(termInvalidDoubleQuoted.toString(), "'test!'");
        assertEquals(getName(termInvalidDoubleQuoted), "test!");

        Term structInvalidDoubleQuoted = new Struct("\"test!\"");
        assertEquals(structInvalidDoubleQuoted.toString(), "'\"test!\"'");
        assertEquals(getName(structInvalidDoubleQuoted), "\"test!\"");
    }

    @Test
    public void includesSingleQuotes() {
        Term termNormalSingleQuoted = Term.createTerm("'test'");
        assertEquals(termNormalSingleQuoted.toString(), "test");
        assertEquals(getName(termNormalSingleQuoted), "test");

        Term structNormalSingleQuoted = new Struct("'test'");
        assertEquals(structNormalSingleQuoted.toString(), "''test''");
        assertEquals(getName(structNormalSingleQuoted), "'test'");
    }

    @Test
    public void includesSingleQuotesInvalidCharacter() {
        Term termInvalidSingleQuoted = Term.createTerm("'test!'");
        assertEquals(termInvalidSingleQuoted.toString(), "'test!'");
        assertEquals(getName(termInvalidSingleQuoted), "test!");

        Term structInvalidSingleQuoted = new Struct("'test!'");
        assertEquals(structInvalidSingleQuoted.toString(), "''test!''");
        assertEquals(getName(structInvalidSingleQuoted), "'test!'");
    }

    @Test
    public void twiceQuoted() {
        try {
            Term termNormalDoubleQuotedTwice = Term.createTerm("\"\"test\"\"");
            fail();
        } catch (InvalidTermException ignored) {
        }

        Term structNormalDoubleQuotedTwice = new Struct("\"\"test\"\"");
        assertEquals(structNormalDoubleQuotedTwice.toString(), "'\"\"test\"\"'");
        assertEquals(getName(structNormalDoubleQuotedTwice), "\"\"test\"\"");
    }

    @Test
    public void twiceQuotedInvalidCharacter() {
        // Terms with invalid character quoted with double quotes twice
        try {
            Term termInvalidDoubleQuotedTwice = Term.createTerm("\"\"test!\"\"");
            fail();
        } catch (InvalidTermException ignored) {
        }
        Term structInvalidDoubleQuotedTwice = new Struct("\"\"test!\"\"");
        assertEquals(structInvalidDoubleQuotedTwice.toString(), "'\"\"test!\"\"'");
        assertEquals(getName(structInvalidDoubleQuotedTwice), "\"\"test!\"\"");
    }

    @Test
    public void twiceSingleQuoted() {
        // Normal terms quoted with single quotes twice
        try {
            Term termNormalSingleQuotedTwice = Term.createTerm("''test''");
            fail();
        } catch (InvalidTermException ignored) {
        }

        Term structNormalSingleQuotedTwice = new Struct("''test''");
        assertEquals(structNormalSingleQuotedTwice.toString(), "'''test'''");
        assertEquals(getName(structNormalSingleQuotedTwice), "''test''");
    }

    @Test
    public void twiceSingleQuotedInvalidCharacter() {
        try {
            Term termInvalidSingleQuotedTwice = Term.createTerm("''test!''");
            fail();
        } catch (InvalidTermException ignored) {
        }

        Term structInvalidSingleQuotedTwice = new Struct("''test!''");
        assertEquals(structInvalidSingleQuotedTwice.toString(), "'''test!'''");
        assertEquals(getName(structInvalidSingleQuotedTwice), "''test!''");
    }

    @Test
    public void QuoteSingleQuote() {
        Term termNormalSingleDoubleQuoted = Term.createTerm("\"'test'\"");
        assertEquals(termNormalSingleDoubleQuoted.toString(), "''test''");
        assertEquals(getName(termNormalSingleDoubleQuoted), "'test'");

        Term structNormalSingleDoubleQuoted = new Struct("\"'test'\"");
        assertEquals(structNormalSingleDoubleQuoted.toString(), "'\"'test'\"'");
        assertEquals(getName(structNormalSingleDoubleQuoted), "\"'test'\"");
    }

    @Test
    public void QuoteSingleQuoteInvalidCharacter() {
        Term termInvalidSingleDoubleQuoted = Term.createTerm("\"'test!'\"");
        assertEquals(termInvalidSingleDoubleQuoted.toString(), "''test!''");
        assertEquals(getName(termInvalidSingleDoubleQuoted), "'test!'");

        Term structInvalidSingleDoubleQuoted = new Struct("\"'test!'\"");
        assertEquals(structInvalidSingleDoubleQuoted.toString(), "'\"'test!'\"'");
        assertEquals(getName(structInvalidSingleDoubleQuoted), "\"'test!'\"");
    }

    @Test
    public void SingleQuoteQuote() {
        Term termNormalDoubleSingleQuoted = Term.createTerm("'\"test\"'");
        assertEquals(termNormalDoubleSingleQuoted.toString(), "'\"test\"'");
        assertEquals(getName(termNormalDoubleSingleQuoted), "\"test\"");

        Term structNormalDoubleSingleQuoted = new Struct("'\"test\"'");
        assertEquals(structNormalDoubleSingleQuoted.toString(), "''\"test\"''");
        assertEquals(getName(structNormalDoubleSingleQuoted), "'\"test\"'");
    }

    @Test
    public void singleQuoteQuoteInvalidCharacter() {
        // Terms with invalid character quoted with single quotes
        Term termInvalidDoubleSingleQuoted = Term.createTerm("'\"test!\"'");
        assertEquals(termInvalidDoubleSingleQuoted.toString(), "'\"test!\"'");
        assertEquals(getName(termInvalidDoubleSingleQuoted), "\"test!\"");

        Term structInvalidDoubleSingleQuoted = new Struct("'\"test!\"'");
        assertEquals(structInvalidDoubleSingleQuoted.toString(), "''\"test!\"''");
        assertEquals(getName(structInvalidDoubleSingleQuoted), "'\"test!\"'");
    }

    private static String getName(Term term) {
        // Currently doesn't work for predicates with illegal names
        if (term instanceof Struct && term.isAtomic()) {
            return ((Struct) term).getName();
        } else {
            return term.toString();
        }
    }
}
