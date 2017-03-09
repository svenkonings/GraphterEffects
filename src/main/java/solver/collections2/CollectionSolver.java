package solver.collections2;

import java.util.List;
import java.util.Objects;

public class CollectionSolver {
    public static void main(String[] args) {
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

        Predicate edgeWithLabel = new Predicate("edge_with_label", 3);
        List<String[]> edgeResults = edge.get("X", "Y", "E");
        List<String[]> labelResults = label.get("E", "S");
        for (String[] edgeResult : edgeResults) {
            for (String[] labelResult: labelResults) {
                if (Objects.equals(edgeResult[2], labelResult[0])) {
                    edgeWithLabel.add(edgeResult[0], edgeResult[1], labelResult[1]);
                }
            }
        }
        System.out.println(edgeWithLabel);
    }
}
