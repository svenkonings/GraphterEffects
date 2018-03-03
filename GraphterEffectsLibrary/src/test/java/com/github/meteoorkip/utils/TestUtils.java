package com.github.meteoorkip.utils;

import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import com.github.meteoorkip.prolog.TuProlog;
import org.graphstream.graph.Element;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.meteoorkip.prolog.TuProlog.*;
import static com.github.meteoorkip.utils.TermUtils.elementTerm;
import static org.junit.Assert.assertEquals;

public final class TestUtils {

    public static boolean mapContains(Map<String, Term> map, String key, Term value) {
        return map.containsKey(key) && map.get(key).equals(value);
    }

    public static boolean mapContains(Map<String, Term> haystack, Map<String, Term> needle) {
        return haystack.entrySet().containsAll(needle.entrySet());
    }

    public static boolean answerContains(Collection<Map<String, Term>> answers, String... kvPairs) throws Exception {
        Map<String, Term> needle = new HashMap<>();
        if (kvPairs.length % 2 != 0)
            throw new Exception("kvPairs must be even");
        for (int i = 0; i < kvPairs.length; i += 2) {
            String k = kvPairs[i];
            String v = kvPairs[i + 1];
            needle.put(k, term(v));
        }
        for (Map<String, Term> answer : answers) {
            if (mapContains(answer, needle))
                return true;
        }
        return false;
    }

    public static void testPredicateValue(TuProlog prolog, Element element, String predicate, String expectedID, String expectedValue) {
        Collection<Map<String, Term>> answers = prolog.solve(and(elementTerm(element), struct(predicate, term(expectedID), var("Value"))));
        try {
            assert answers.size() == 1;
            assert answerContains(answers, "Value", expectedValue);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError();
        } catch (AssertionError e) {
            e.printStackTrace();
            throw new AssertionError((and(elementTerm(element), struct(predicate, term(expectedID), var("Value"))).toString()) + " did not yield any results.");
        }
    }

    public static void testAttributesCorrect(TuProlog prolog, Element element) {
        element.getAttributeKeySet().forEach(
                attributeKey -> {

                    List<Map<String, Term>> answers = prolog.solve(and(elementTerm(element), struct("attribute", struct(element.getId()), struct("\"" + attributeKey + "\""), var("Value"))));
                    String expectedValue = StringUtils.ObjectToString(element.getAttribute(attributeKey));
                    if (StringUtils.isDouble(StringUtils.removeQuotation(expectedValue)) || expectedValue.contains("[")) {
                        return;
                    }
                    try {
                        assert answerContains(answers, "Value", expectedValue) || ((Struct) answers.get(0).get("Value")).getName().equals(expectedValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                        assert false;
                    } catch (AssertionError e) {
                        throw new AssertionError(answers + " does not contain " + expectedValue);
                    }
                }
        );
    }


    public static void testSimpleFact(TuProlog prolog, Element element, String predicate, String expectedID, boolean shouldExist) {
        assertEquals(shouldExist, !prolog.solve(and(elementTerm(element), struct(predicate, term(expectedID)))).isEmpty());
    }


    public static void showImage(Image image) {
        JFrame f = new JFrame();
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(image));
        f.add(label);
        f.setVisible(true);
        f.pack();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
