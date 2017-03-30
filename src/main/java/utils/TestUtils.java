package utils;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import compiler.prolog.TuProlog;
import org.graphstream.graph.Element;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static compiler.prolog.TuProlog.*;
import static org.junit.Assert.assertEquals;
import static utils.TermUtils.elementTerm;

public final class TestUtils {

    public static TuProlog createDatabase() throws InvalidTheoryException {
        TuProlog prolog = new TuProlog();
        prolog.addTheory(
                struct("parent", term("a"), term("aa")),
                struct("parent", term("a"), term("ab")),
                struct("parent", term("aa"), term("aaa")),
                struct("parent", term("aa"), term("aab")),
                struct("parent", term("aaa"), term("aaaa")),
                struct("parent", term("c"), term("ca"))
        );
        prolog.addTheory(
                struct("ancestor", var("X"), var("Y")),
                struct("parent", var("X"), var("Z")),
                struct("ancestor", var("Z"), var("Y")),
                and(struct("ancestor", var("X"), var("Y")), struct("parent", var("X"), var("Y"))),
                and(struct("sibling", var("X"), var("Y")), struct("parent", var("Z"), var("X")), struct("parent", var("Z"), var("Y")), struct("=\\\\=", var("X"), var("Y"))),
                and(struct("related", var("X"), var("Y")), struct("ancestor", var("Z"), var("X")), struct("ancestor", var("Z"), var("Y")))
        );
        return prolog;
    }

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
        Collection<Map<String, Term>> answers = prolog.query(and(elementTerm(element), struct(predicate, term(expectedID), var("Value"))));
        assert answers.size() == 1;
        try {
            assert answerContains(answers, "Value", expectedValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testAttributesCorrect(TuProlog prolog, Element element) {
        element.getAttributeKeySet().forEach(
                attributeKey -> {
                    try {
                        Collection<Map<String, Term>> answers = prolog.query(and(elementTerm(element), struct("attribute", term(attributeKey), term(element.getId()), var("Value"))));
                        String expectedValue = StringUtils.ObjectToString(element.getAttribute(attributeKey));
                        assert answerContains(answers, "Value", expectedValue);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
        );
    }


    public static void testSimpleFact(TuProlog prolog, Element element, String predicate, String expectedID, boolean shouldExist) {
        assertEquals(shouldExist, !prolog.query(and(elementTerm(element), struct(predicate, term(expectedID)))).isEmpty());
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

    public static void showSVG(File file, long time) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("\"/Program Files (x86)/Google/Chrome/Application/chrome.exe\"", file.getAbsolutePath(), "--new-window");
        Process p = pb.start();
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        p.destroy();
    }

}
