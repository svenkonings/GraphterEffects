package utils;

import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;

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


    public static Collection<Element> elements(Graph in, boolean nodes, boolean edges, boolean graph) {
        Set<Element> res = new HashSet<>();
        if (nodes) {
            res.addAll(in.getNodeSet());
        }
        if (edges) {
            res.addAll(in.getEdgeSet());
        }
        if (graph) {
            res.add(in);
        }
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

    public static boolean isFullyDirected(Graph graph) {
        for (Edge edge : graph.getEdgeSet()) {
           if (!edge.isDirected()) {
               return false;
           }
        }
        return true;
    }

    public static int ConnectedComponentsCount(Graph graph) {
        ConnectedComponents a = new ConnectedComponents();
        a.init(graph);
        return a.getConnectedComponentsCount();
    }

    public static boolean isFullyUndirected(Graph graph) {
        for (Edge edge : graph.getEdgeSet()) {
            if (!edge.isDirected()) {
                return false;
            }
        }
        return true;
    }
}
