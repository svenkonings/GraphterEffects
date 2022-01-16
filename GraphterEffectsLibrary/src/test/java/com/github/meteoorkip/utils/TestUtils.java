package com.github.meteoorkip.utils;

import com.github.meteoorkip.prolog.PrologException;
import com.github.meteoorkip.prolog.TuProlog;
import it.unibo.tuprolog.core.Struct;
import it.unibo.tuprolog.core.Term;
import org.graphstream.graph.Element;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.meteoorkip.prolog.TuProlog.*;
import static com.github.meteoorkip.utils.TermUtils.elementTerm;
import static org.junit.jupiter.api.Assertions.assertEquals;


public final class TestUtils {

    public static boolean mapContains(Map<String, Term> map, String key, Term value) {
        return map.containsKey(key) && map.get(key).equals(value);
    }

    public static<K, V>  boolean mapContains(Map<K, V> haystack, Map<K, V> needle) {
        return haystack.entrySet().containsAll(needle.entrySet());
    }

    public static boolean answerContains(Collection<Map<String, Term>> answers, Term... kvPairs) throws Exception {
        Map<String, Term> needle = new HashMap<>();
        if (kvPairs.length % 2 != 0)
            throw new Exception("kvPairs must be even");
        for (int i = 0; i < kvPairs.length; i += 2) {
            Term k = kvPairs[i];
            Term v = kvPairs[i + 1];
            needle.put(k.castToVar().getName(), v);
        }
        for (Map<String, Term> answer : answers) {
            if (mapContains(answer, needle))
                return true;
        }
        return false;
    }

    public static void testPredicateValue(TuProlog prolog, Element element, String predicate, String expectedID, Term expectedValue) {
        Collection<Map<String, Term>> answers = prolog.solve(and(elementTerm(element), struct(predicate, atom(expectedID), var("Value"))));
        try {
            assert answers.size() == 1;
            assert answerContains(answers, var("Value"), expectedValue);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError();
        } catch (AssertionError e) {
            e.printStackTrace();
            throw new AssertionError((and(elementTerm(element), struct(predicate, atom(expectedID), var("Value"))).toString()) + " did not yield any results.");
        }
    }

    public static void testAttributesCorrect(TuProlog prolog, Element element) {
        for (String attributeKey : element.attributeKeys().collect(Collectors.toSet())) {
                List<Map<String, Term>> answers = prolog.solve(and(elementTerm(element), struct("attribute", struct(element.getId()), struct("\"" + attributeKey + "\""), var("Value"))));
                String expectedValue = StringUtils.ObjectToString(element.getAttribute(attributeKey));
                if (StringUtils.isDouble(StringUtils.removeQuotation(expectedValue)) || expectedValue.contains("[")) {
                    return;
                }
                try {
                    assert answerContains(answers, var("Value"), atom(expectedValue)) || ((Struct) answers.get(0).get("Value")).getFunctor().equals(expectedValue);
                } catch (Exception e) {
                    e.printStackTrace();
                    assert false;
                } catch (AssertionError e) {
                    throw new AssertionError(answers + " does not contain " + expectedValue);
                }
            }
        }



    public static void testSimpleFact(TuProlog prolog, Element element, String predicate, String expectedID, boolean shouldExist) {
        assertEquals(shouldExist, !prolog.solve(and(elementTerm(element), struct(predicate, atom(expectedID)))).isEmpty(), () -> "Predicate of element " + element + " " + predicate + "(" + expectedID + ") was expected to be " + shouldExist + " but Prolog disagrees.");
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
