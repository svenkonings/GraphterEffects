package utils; /**
 * Created by user on 21-3-2017.
 */

import org.graphstream.graph.Element;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Expr;
import za.co.wstoop.jatalog.Jatalog;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static utils.ExprUtils.elementExpr;

public class TestUtils {

    public static Jatalog createDatabase() throws DatalogException {

        Jatalog jatalog = new Jatalog();

        jatalog.fact("parent", "a", "aa")
                .fact("parent", "a", "ab")
                .fact("parent", "aa", "aaa")
                .fact("parent", "aa", "aab")
                .fact("parent", "aaa", "aaaa")
                .fact("parent", "c", "ca");

        jatalog.rule(Expr.expr("ancestor", "X", "Y"), Expr.expr("parent", "X", "Z"), Expr.expr("ancestor", "Z", "Y"))
                .rule(Expr.expr("ancestor", "X", "Y"), Expr.expr("parent", "X", "Y"))
                .rule(Expr.expr("sibling", "X", "Y"), Expr.expr("parent", "Z", "X"), Expr.expr("parent", "Z", "Y"), Expr.ne("X", "Y"))
                .rule(Expr.expr("related", "X", "Y"), Expr.expr("ancestor", "Z", "X"), Expr.expr("ancestor", "Z", "Y"));

        return jatalog;
    }

    public static boolean mapContains(Map<String, String> map, String key, String value) {
        if(map.containsKey(key)) {
            return map.get(key).equals(value);
        }
        return false;
    }

    public static boolean mapContains(Map<String, String> haystack, Map<String, String> needle) {
        for(String key : needle.keySet()) {
            if(!haystack.containsKey(key))
                return false;
            if(!haystack.get(key).equals(needle.get(key)))
                return false;
        }
        return true;
    }

    public static boolean answerContains(Collection<Map<String, String>> answers, String... kvPairs) throws Exception {
        Map<String, String> needle = new HashMap<String,String>();
        if(kvPairs.length % 2 != 0)
            throw new Exception("kvPairs must be even");
        for(int i = 0; i < kvPairs.length/2; i++) {
            String k = kvPairs[i*2];
            String v = kvPairs[i*2 + 1];
            needle.put(k, v);
        }
        for(Map<String, String> answer : answers) {
            if(mapContains(answer, needle))
                return true;
        }
        return false;
    }

    public static void testPredicateValue(Jatalog jatalog, Element element, String predicate, String expectedID, String expectedValue) throws DatalogException {
        Collection<Map<String, String>> answers;
        answers = jatalog.query(elementExpr(element), Expr.expr(predicate, expectedID, "Value"));
        assert answers.size() == 1;
        try {
            TestUtils.answerContains(answers,"ID",expectedID,"Value",expectedValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testAttributesCorrect(Jatalog jatalog, Element element) throws DatalogException {
        element.getAttributeKeySet().forEach(
                attributeKey -> {
                    try {
                        Collection<Map<String, String>> answers = jatalog.query(elementExpr(element), Expr.expr("attribute", attributeKey, element.getId(), "Value"));
                        TestUtils.answerContains(
                                answers, element.getId(), element.getAttribute(attributeKey));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }


    public static void testSimpleFact(Jatalog jatalog, Element element, String predicate, String expectedID, boolean shouldExist) throws DatalogException {
        assertEquals(shouldExist, !jatalog.query(elementExpr(element), Expr.expr(predicate, expectedID)).isEmpty());
    }


}
