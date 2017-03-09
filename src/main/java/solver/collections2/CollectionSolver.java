package solver.collections2;

import java.util.List;
import java.util.Objects;

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
        Predicate edgeWithLabel = new Predicate("edge_with_label", 3);
        List<String[]> edgeResults = edge.get("X", "Y", "E");
        List<String[]> labelResults = label.get("E", "S");
        for (String[] edgeResult : edgeResults) {
            for (String[] labelResult : labelResults) {
                if (Objects.equals(edgeResult[2], labelResult[0])) {
                    edgeWithLabel.add(edgeResult[0], edgeResult[1], labelResult[1]);
                }
            }
        }
        System.out.println(edgeWithLabel);

        // female(X) :- edge_with_label(X, _, "mom").
        Predicate female = new Predicate("female", 1);
        List<String[]> edgeWithLabelResults1 = edgeWithLabel.get("X", "Y", "\"mom\"");
        for (String[] edgeWithLabelResult : edgeWithLabelResults1) {
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
}
