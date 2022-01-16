package com.github.meteoorkip.graafvis.grammar;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.meteoorkip.graafvis.grammar.TestUtil.*;

/**
 *
 */
public class FunctorTest {

    static final ArrayList<String> VALID_FUNCTOR_SAMPLES = new ArrayList<>();

    static {
        VALID_FUNCTOR_SAMPLES.add("p");
        VALID_FUNCTOR_SAMPLES.add("cool");
        VALID_FUNCTOR_SAMPLES.add("c00l");
        VALID_FUNCTOR_SAMPLES.add("c0_oL");
        VALID_FUNCTOR_SAMPLES.add("`C%^*()_+{}:;/.,1`");
        VALID_FUNCTOR_SAMPLES.add("`_`");
        VALID_FUNCTOR_SAMPLES.add("`infix`");
        VALID_FUNCTOR_SAMPLES.add("cC__");
    }

    static final ArrayList<String> INVALID_FUNCTOR_SAMPLES = new ArrayList<>();

    static {
        INVALID_FUNCTOR_SAMPLES.add("Uncool");
        INVALID_FUNCTOR_SAMPLES.add("_uncool");
        INVALID_FUNCTOR_SAMPLES.add("0ncool");
        INVALID_FUNCTOR_SAMPLES.add("1111");
        INVALID_FUNCTOR_SAMPLES.add("``");
        INVALID_FUNCTOR_SAMPLES.add("\"string\"");
        INVALID_FUNCTOR_SAMPLES.add("`uncool");
    }

    @Test
    public void runTest() {
        writeTitleInLog("Functor test");
        for (String sample : VALID_FUNCTOR_SAMPLES) {
            testValidSample(sample);
        }
        for (String sample : INVALID_FUNCTOR_SAMPLES) {
            testInvalidSample(sample);
        }
    }

    private static void testValidSample(String sample) {
        GraafvisParser parser = getGraafvisParserFor(sample);
        parser.functor();
        assertHasNoErrors(parser);
    }

    private static void testInvalidSample(String sample) {
        GraafvisParser parser = getGraafvisParserFor(sample);
        parser.functor();
        assertHasErrors(parser);
    }

}
