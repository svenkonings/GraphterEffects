package utils;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class used for methods to manipulate Graph Objects and related tasks.
 */
public final class GraphUtils {

    /**
     * Changes a Graph such that all objects in the Graph have a new ID that is the old one prefixed by an underscore.
     * @param input Graph to change.
     * @return Changed graph.
     */
    public static Graph changeIDs(Graph input) {
        Graph res;
        if (input instanceof DefaultGraph) {
            res = new DefaultGraph("_" + input.getId());
        } else if (input instanceof SingleGraph) {
            res = new SingleGraph("_" + input.getId());
        } else if (input instanceof MultiGraph) {
            res = new MultiGraph("_" + input.getId());
        } else {
            throw new UnsupportedOperationException();
        }
        for (String key : input.getAttributeKeySet()) {
            String[] arr = {input.getAttribute(key)};
            res.setAttribute(key, arr);
        }
        for (Node node : input.getEachNode()) {
            Node added = res.addNode("_" + node.getId());
            for (String key : node.getAttributeKeySet()) {
                Object[] arr = {node.getAttribute(key)};
                added.setAttribute(key, arr);
            }
        }
        for (Edge edge : input.getEachEdge()) {
            Edge added = res.addEdge("_" + edge.getId(), "_" + edge.getSourceNode(), "_" + edge.getTargetNode(), edge.isDirected());
            for (String key : edge.getAttributeKeySet()) {
                Object[] arr = {edge.getAttribute(key)};
                added.setAttribute(key, arr);
            }
        }

        return res;
    }

    public static Set<Node> neighbours(Node node) {
        Set<Node> neighbours = new HashSet<>();
        for (Edge e : node.getEachEdge()) {
            neighbours.add(e.getOpposite(node));
        }
        neighbours.remove(node);
        return neighbours;
    }

    public static int neighbourCount(Node node) {
        return neighbours(node).size();
    }
}
