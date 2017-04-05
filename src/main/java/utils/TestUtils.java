package utils;

import alice.tuprolog.Term;
import compiler.prolog.TuProlog;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static compiler.prolog.TuProlog.*;
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
                        Collection<Map<String, Term>> answers = prolog.solve(and(elementTerm(element), struct("attribute", term(attributeKey), term(element.getId()), var("Value"))));
                        String expectedValue = StringUtils.ObjectToString(element.getAttribute(attributeKey));
                        assert answerContains(answers, "Value", expectedValue);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
        );
    }


    public static void testSimpleFact(TuProlog prolog, Element element, String predicate, String expectedID, boolean shouldExist) {
        assertEquals(shouldExist, !prolog.solve(and(elementTerm(element), struct(predicate, term(expectedID)))).isEmpty());
    }

    /**
     * Converts an {@link Element} to an {@link Term}.
     * In the case of an Graph, the generated expression will of the form graph(ID). <br>
     * In the case of an Node, it will be node(ID). <br>
     * In the case of an Edge, it will be edge(TargedID,SourceID,ID). <br>
     *
     * @param element {@link Element} to be converted to an {@link Term}
     * @return the generated {@link Term}
     */
    public static Term elementTerm(Element element) {
        if (element instanceof Graph) {
            return struct("graph", term(element.getId()));
        } else if (element instanceof Edge) {
            Edge edge = (Edge) element;
            return struct("edge", term(edge.getTargetNode().getId()), term(edge.getSourceNode().getId()), term(element.getId()));
        } else {
            return struct("node", term(element.getId()));
        }
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
