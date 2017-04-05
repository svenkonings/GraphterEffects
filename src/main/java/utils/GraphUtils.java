package utils;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.HashSet;
import java.util.Set;

/**
 * Class used for methods to manipulate Graph Objects and related tasks.
 */
public final class GraphUtils {

    public static final String ILLEGAL_PREFIX = "#";

    /**
     * Changes a Graph such that all objects in the Graph have a new ID that is the old one prefixed by the illegal
     * prefix.
     *
     * @param input Graph to change.
     * @return Changed graph.
     */
    public static Graph changeIDs(Graph input) {
        Graph res;
        if (input instanceof DefaultGraph) {
            res = new DefaultGraph(ILLEGAL_PREFIX + input.getId());
        } else if (input instanceof SingleGraph) {
            res = new SingleGraph(ILLEGAL_PREFIX + input.getId());
        } else if (input instanceof MultiGraph) {
            res = new MultiGraph(ILLEGAL_PREFIX + input.getId());
        } else {
            throw new UnsupportedOperationException();
        }
        for (String key : input.getAttributeKeySet()) {
            Object[] arr = {input.getAttribute(key)};
            res.setAttribute(key, arr);
        }
        for (Node node : input.getEachNode()) {
            Node added = res.addNode(ILLEGAL_PREFIX + node.getId());
            for (String key : node.getAttributeKeySet()) {
                Object[] arr = {node.getAttribute(key)};
                added.setAttribute(key, arr);
            }
        }
        for (Edge edge : input.getEachEdge()) {
            Edge added = res.addEdge(ILLEGAL_PREFIX + edge.getId(), ILLEGAL_PREFIX + edge.getSourceNode(), ILLEGAL_PREFIX + edge.getTargetNode(), edge.isDirected());
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


    public static Set<Element> elements(Graph in) {
        Set<Element> res = new HashSet<>();
        res.addAll(in.getEdgeSet());
        res.addAll(in.getNodeSet());
        return res;
    }

    public static Element getByID(Graph in, String ID) {
        if (in.getId().equals(ID)) {
            return in;
        }
        Element res = in.getEdge(ID);
        if (res==null) {
            res = in.getNode(ID);
        }
        return res;
    }
}
