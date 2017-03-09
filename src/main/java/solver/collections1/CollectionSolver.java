package solver.collections1;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.Arrays;
import java.util.stream.Stream;

public class CollectionSolver {

    private final MultiGraph graph;

    public CollectionSolver() {
        graph = new MultiGraph("ABC");
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");
    }

    public void edge(Term<Node> node0, Term<Node> node1, Term<Edge> edge) {
        if (!hasVariable(node0, node1, edge)) {
            boolean result = graph.getNodeSet().contains(node0.getValue()) &&
                    graph.getNodeSet().contains(node1.getValue()) &&
                    graph.getEdgeSet().contains(edge.getValue());
        }
        if (edge.isVariable()) {
            Stream<Edge> result = graph.getEdgeSet().stream();
            if (!node0.isVariable()) {
                result = result.filter(edge1 -> edge1.getNode0().equals(node0.getValue()));
            }
            if (!node1.isVariable()) {
                result=result.filter(edge1 -> edge1.getNode1().equals(node1.getValue()));
            }
        }
    }

    private static boolean hasVariable(Term... terms) {
        return Arrays.stream(terms).anyMatch(Term::isVariable);
    }
}
