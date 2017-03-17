package solver.collections3;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionSolver {
    public static void main(String[] args) {
        // Predefined
        Predicate node = new Predicate("node", 1);
        node.add("a");
        node.add("b");
        node.add("c");
        node.add("d");
        node.add("e");
        node.add("f");
        node.add("g");
        node.add("h");
        System.out.println(node);

        Predicate edge = new Predicate("edge", 3);
        edge.add("a", "c", "ac");
        edge.add("b", "c", "bc");
        System.out.println(edge);

        Predicate label = new Predicate("label", 2);
        label.add("a", "\"Toos\"");
        label.add("b", "\"Els\"");
        label.add("c", "\"Vera\"");
        label.add("ac", "\"dad\"");
        label.add("bc", "\"mom\"");
        System.out.println(label);

        // edge_with_label(X, Y, S) :- edge(X, Y, E), label(E, S).
        Predicate edgeWithLabel = new Predicate("edge_with_label", 3);
        List<Map<String, String>> edgeResults = edge.get("X", "Y", "E");
        List<Map<String, String>> labelResults = label.get("E", "S");
        List<Map<String, String>> edgeWithLabelValues = unify(edgeResults, labelResults);
        edgeWithLabelValues.forEach(value -> edgeWithLabel.add(value.get("X"), value.get("Y"), value.get("S")));
        System.out.println(edgeWithLabel);

        // female(X) :- edge_with_label(X, _, "mom").
        Predicate female = new Predicate("female", 1);
        List<Map<String, String>> femaleValues = edgeWithLabel.get("X", "Y", "\"mom\"");
        femaleValues.forEach(result -> female.add(result.get("X")));
        System.out.println(female);

        // male(X) :- edge_with_label(X, _, "dad").
        Predicate male = new Predicate("male", 1);
        List<Map<String, String>> maleValues = edgeWithLabel.get("X", "Y", "\"dad\"");
        maleValues.forEach(result -> male.add(result.get("X")));
        System.out.println(male);

//        // test(A, B, ...) :- node(A), node(B), ...
//        Predicate test = new Predicate("test", 8);
//        List<Map<String, String>> testValues = unify(node.get("A"), node.get("B"), node.get("C"), node.get("D"), node.get("E"), node.get("F"), node.get("G"), node.get("H"));
//        testValues.forEach(value -> test.add(value.get("A"), value.get("B"), value.get("C"), value.get("D"), value.get("E"), value.get("F"), value.get("G"), value.get("H")));
//        System.out.println(test);
    }

    @SafeVarargs
    private static List<Map<String, String>> unify(List<Map<String, String>>... valuesArray) {
        List<Map<String, String>> results = new ArrayList<>();
        results.add(new LinkedHashMap<>()); // Initial value
        for (List<Map<String, String>> values : valuesArray) {
            results = unify(results, values);
        }
        return results;
    }

    private static List<Map<String, String>> unify(List<Map<String, String>> firstValues, List<Map<String, String>> secondValues) {
        List<Map<String, String>> results = new ArrayList<>();
        for (Map<String, String> first : firstValues) {
            for (Map<String, String> second : secondValues) {
                if (unifyable(first, second)) {
                    results.add(unify(first, second));
                }
            }
        }
        return results;
    }

    private static Map<String, String> unify(Map<String, String> first, Map<String, String> second) {
        Map<String, String> result = new LinkedHashMap<>(first); // Insertion order
        result.putAll(second);
        return result;
    }

    private static boolean unifyable(Map<String, String> first, Map<String, String> second) {
        Set<String> intersection = intersection(first.keySet(), second.keySet());
        for (String key : intersection) {
            if (!first.get(key).equals(second.get(key))) {
                return false;
            }
        }
        return true;
    }

    private static <T> Set<T> intersection(Set<T> first, Set<T> second) {
        return first.stream().filter(second::contains).collect(Collectors.toSet());
    }
}
