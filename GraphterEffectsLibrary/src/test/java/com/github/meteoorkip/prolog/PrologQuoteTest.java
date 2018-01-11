package com.github.meteoorkip.prolog;

import alice.tuprolog.InvalidTermException;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PrologQuoteTest {

    @Test
    public void quoteTest() {
        // Normal terms
        Term termNormal = Term.createTerm("test");
        assertEquals(termNormal.toString(), "test");
        assertEquals(getName(termNormal), "test");

        Term structNormal = new Struct("test");
        assertEquals(structNormal.toString(), "test");
        assertEquals(getName(structNormal), "test");

        // Terms with invalid character
        try {
            Term termInvalid = Term.createTerm("!test");
            fail();
        } catch (InvalidTermException ignored) {
        }

        Term structInvalid = new Struct("!test");
        assertEquals(structInvalid.toString(), "'!test'");
        assertEquals(getName(structInvalid), "!test");

        // Normal terms quoted with double quotes
        Term termNormalDoubleQuoted = Term.createTerm("\"test\"");
        assertEquals(termNormalDoubleQuoted.toString(), "test");
        assertEquals(getName(termNormalDoubleQuoted), "test");

        Term structNormalDoubleQuoted = new Struct("\"test\"");
        assertEquals(structNormalDoubleQuoted.toString(), "'\"test\"'");
        assertEquals(getName(structNormalDoubleQuoted), "\"test\"");

        // Terms with invalid character quoted with double quotes
        Term termInvalidDoubleQuoted = Term.createTerm("\"!test\"");
        assertEquals(termInvalidDoubleQuoted.toString(), "'!test'");
        assertEquals(getName(termInvalidDoubleQuoted), "!test");

        Term structInvalidDoubleQuoted = new Struct("\"!test\"");
        assertEquals(structInvalidDoubleQuoted.toString(), "'\"!test\"'");
        assertEquals(getName(structInvalidDoubleQuoted), "\"!test\"");

        // Normal terms quoted with single quotes
        Term termNormalSingleQuoted = Term.createTerm("'test'");
        assertEquals(termNormalSingleQuoted.toString(), "test");
        assertEquals(getName(termNormalSingleQuoted), "test");

        Term structNormalSingleQuoted = new Struct("'test'");
        assertEquals(structNormalSingleQuoted.toString(), "''test''");
        assertEquals(getName(structNormalSingleQuoted), "'test'");

        // Terms with invalid character quoted with single quotes
        Term termInvalidSingleQuoted = Term.createTerm("'!test'");
        assertEquals(termInvalidSingleQuoted.toString(), "'!test'");
        assertEquals(getName(termInvalidSingleQuoted), "!test");

        Term structInvalidSingleQuoted = new Struct("'!test'");
        assertEquals(structInvalidSingleQuoted.toString(), "''!test''");
        assertEquals(getName(structInvalidSingleQuoted), "'!test'");

        // Normal terms quoted with double quotes twice
        try {
            Term termNormalDoubleQuotedTwice = Term.createTerm("\"\"test\"\"");
            fail();
        } catch (InvalidTermException ignored) {
        }

        Term structNormalDoubleQuotedTwice = new Struct("\"\"test\"\"");
        assertEquals(structNormalDoubleQuotedTwice.toString(), "'\"\"test\"\"'");
        assertEquals(getName(structNormalDoubleQuotedTwice), "\"\"test\"\"");

        // Terms with invalid character quoted with double quotes twice
        try {
            Term termInvalidDoubleQuotedTwice = Term.createTerm("\"\"!test\"\"");
            fail();
        } catch (InvalidTermException ignored) {
        }
        Term structInvalidDoubleQuotedTwice = new Struct("\"\"!test\"\"");
        assertEquals(structInvalidDoubleQuotedTwice.toString(), "'\"\"!test\"\"'");
        assertEquals(getName(structInvalidDoubleQuotedTwice), "\"\"!test\"\"");

        // Normal terms quoted with single quotes twice
        try {
            Term termNormalSingleQuotedTwice = Term.createTerm("''test''");
            fail();
        } catch (InvalidTermException ignored) {
        }

        Term structNormalSingleQuotedTwice = new Struct("''test''");
        assertEquals(structNormalSingleQuotedTwice.toString(), "'''test'''");
        assertEquals(getName(structNormalSingleQuotedTwice), "''test''");

        // Terms with invalid character quoted with single quotes twice
        try {
            Term termInvalidSingleQuotedTwice = Term.createTerm("''!test''");
            fail();
        } catch (InvalidTermException ignored) {
        }

        Term structInvalidSingleQuotedTwice = new Struct("''!test''");
        assertEquals(structInvalidSingleQuotedTwice.toString(), "'''!test'''");
        assertEquals(getName(structInvalidSingleQuotedTwice), "''!test''");

        // Normal terms quoted with single and then double quotes
        Term termNormalSingleDoubleQuoted = Term.createTerm("\"'test'\"");
        assertEquals(termNormalSingleDoubleQuoted.toString(), "''test''");
        assertEquals(getName(termNormalSingleDoubleQuoted), "'test'");

        Term structNormalSingleDoubleQuoted = new Struct("\"'test'\"");
        assertEquals(structNormalSingleDoubleQuoted.toString(), "'\"'test'\"'");
        assertEquals(getName(structNormalSingleDoubleQuoted), "\"'test'\"");

        // Terms with invalid character quoted with single and then double quotes
        Term termInvalidSingleDoubleQuoted = Term.createTerm("\"'!test'\"");
        assertEquals(termInvalidSingleDoubleQuoted.toString(), "''!test''");
        assertEquals(getName(termInvalidSingleDoubleQuoted), "'!test'");

        Term structInvalidSingleDoubleQuoted = new Struct("\"'!test'\"");
        assertEquals(structInvalidSingleDoubleQuoted.toString(), "'\"'!test'\"'");
        assertEquals(getName(structInvalidSingleDoubleQuoted), "\"'!test'\"");

        // Normal terms quoted with double and then single quotes
        Term termNormalDoubleSingleQuoted = Term.createTerm("'\"test\"'");
        assertEquals(termNormalDoubleSingleQuoted.toString(), "'\"test\"'");
        assertEquals(getName(termNormalDoubleSingleQuoted), "\"test\"");

        Term structNormalDoubleSingleQuoted = new Struct("'\"test\"'");
        assertEquals(structNormalDoubleSingleQuoted.toString(), "''\"test\"''");
        assertEquals(getName(structNormalDoubleSingleQuoted), "'\"test\"'");

        // Terms with invalid character quoted with single quotes
        Term termInvalidDoubleSingleQuoted = Term.createTerm("'\"!test\"'");
        assertEquals(termInvalidDoubleSingleQuoted.toString(), "'\"!test\"'");
        assertEquals(getName(termInvalidDoubleSingleQuoted), "\"!test\"");

        Term structInvalidDoubleSingleQuoted = new Struct("'\"!test\"'");
        assertEquals(structInvalidDoubleSingleQuoted.toString(), "''\"!test\"''");
        assertEquals(getName(structInvalidDoubleSingleQuoted), "'\"!test\"'");
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
