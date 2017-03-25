package solvers.collections2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionSolver {
    public static void main(String[] args) {
        // Predefined
        Predicate node = new Predicate("node", 1);
        node.add("a");
        node.add("b");
        node.add("c");
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
        List<String[]> edgeResults = edge.get("X", "Y", "E");
        List<String[]> labelResults = label.get("E", "S");

        List<String[]> edgeWithLabelValues = unify(new int[][]{new int[] {2, 3}}, new int[]{0, 1, 4}, edgeResults, labelResults);
        Predicate edgeWithLabel = new Predicate("edge_with_label", 3, edgeWithLabelValues);
        System.out.println(edgeWithLabel);

        // female(X) :- edge_with_label(X, _, "mom").
        List<String[]> edgeWithMomResults = edgeWithLabel.get("X", "Y", "\"mom\"");
        Predicate female = new Predicate("female", 1);
        for (String[] edgeWithLabelResult : edgeWithMomResults) {
            female.add(edgeWithLabelResult[0]);
        }
        System.out.println(female);

        // male(X) :- edge_with_label(X, _, "dad").
        Predicate male = new Predicate("male", 1);
        List<String[]> edgeWithLabelResults2 = edgeWithLabel.get("X", "Y", "\"dad\"");
        for (String[] edgeWithLabelResult : edgeWithLabelResults2) {
            male.add(edgeWithLabelResult[0]);
        }
        System.out.println(male);
    }

    @SafeVarargs
    private static List<String[]> unify(int[][] mergeColumnsArray, int[] getColumns, List<String[]>... lists) {
        List<String[]> combinedList = combine(lists);
        List<String[]> mergedList = mergeColumns(combinedList, mergeColumnsArray);
        return getColumns(mergedList, getColumns);
    }

    private static List<String[]> getColumns(List<String[]> results, int... columns) {
        List<String[]> newResults = new ArrayList<>();
        for (String[] result : results) {
            String[] newResult = new String[columns.length];
            for (int i = 0; i < columns.length; i++) {
                newResult[i] = result[columns[i]];
            }
            newResults.add(newResult);
        }
        return newResults;
    }

    private static List<String[]> mergeColumns(List<String[]> results, int[]... columnsArray) {
        Stream<String[]> resultStream = results.stream();
        for (int[] columns : columnsArray) {
            resultStream = resultStream.filter(result -> {
                for (int i = 0; i < columns.length - 1; i++) {
                    if (!result[columns[i]].equals(result[columns[i + 1]])) {
                        return false;
                    }
                }
                return true;
            });
        }
        return resultStream.collect(Collectors.toList());
    }

    @SafeVarargs
    private static List<String[]> combine(List<String[]>... lists) {
        assert lists.length > 0;
        List<String[]> results = new ArrayList<>();
        results.add(new String[0]);
        for (List<String[]> list : lists) {
            List<String[]> newResults = new ArrayList<>();
            for (String[] result : results) {
                for (String[] value : list) {
                    newResults.add(concat(result, value));
                }
            }
            results = newResults;
        }
        return results;
    }

    private static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
