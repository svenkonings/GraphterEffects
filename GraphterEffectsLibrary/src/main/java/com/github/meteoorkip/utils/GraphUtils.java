package com.github.meteoorkip.utils;

import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.algorithm.Kruskal;
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

    /**
     * Prefix to be placed before each graph identifier to avoid usage in Graafvis script.
     */
    public static final String ILLEGAL_PREFIX = "#";

    /**
     * Changes a Graph such that all objects in the Graph have a new ID that is the old one prefixed by an illegal
     * prefix.
     *
     * @param input Graph to change.
     * @return Changed graph.
     */
    public static Graph changeIDs(Graph input) {
        Graph res = newGraphWithSameType(input, ILLEGAL_PREFIX + input.getId());
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

    /**
     * Returns the neighbourset of a graph node.
     *
     * @param node A given node.
     * @return A set of all its neighbours.
     */
    public static Set<Node> neighbours(Node node) {
        Set<Node> neighbours = new HashSet<>();
        for (Edge e : node.getEachEdge()) {
            neighbours.add(e.getOpposite(node));
        }
        neighbours.remove(node);
        return neighbours;
    }

    /**
     * Returns the number of neighbours a graph node has.
     *
     * @param node A given node.
     * @return The number of neighbours the node has.
     */
    public static int neighbourCount(Node node) {
        return neighbours(node).size();
    }


    /**
     * Returns a set of graph elements containing specific types.
     *
     * @param in    Graph from which elements are returned.
     * @param nodes Whether the resulting set contains the nodes from the graph.
     * @param edges Whether the resulting set contains the edges from the graph.
     * @param graph Whether the resulting set contains the graph itself.
     * @return The resulting set.
     */
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

    /**
     * Retrieves an element (Node/Edge/Graph) from a graph or null if it does not exist.
     *
     * @param in Graph from which an element is retrieved.
     * @param ID Identifier of the element to be retrieved.
     * @return The element from the graph with the given identifier or null if it did not exist.
     */
    public static Element getByID(Graph in, String ID) {
        if (in.getId().equals(ID)) {
            return in;
        }
        Element res = in.getEdge(ID);
        if (res == null) {
            res = in.getNode(ID);
        }
        return res;
    }

    /**
     * Returns whether each edge in a given graph is directed.
     *
     * @param graph A given graph.
     * @return Whether each edge in this graph is directed.
     */
    public static boolean isFullyDirected(Graph graph) {
        for (Edge edge : graph.getEdgeSet()) {
            if (!edge.isDirected()) {
                return false;
            }
        }
        return true;
    }


    private static Map<Graph, ConnectedComponents> ccmap = new HashMap<>();

    private static void initComponentCount(Graph graph) {
        if (ccmap.containsKey(graph)) {
            return;
        }
        ConnectedComponents connectedComponents = new ConnectedComponents();
        connectedComponents.init(graph);
        connectedComponents.setCountAttribute("_ATTRIBUTE_DETERMINING_WHICH_COMPONENT_THE_NODE_BELONGS_TO_");
        connectedComponents.compute();
        ccmap.put(graph, connectedComponents);
    }

    /**
     * Returns the number of connected components a graph has.
     *
     * @param graph A given graph.
     * @return The number of connected components in this graph.
     */
    public static int ConnectedComponentsCount(Graph graph) {
        initComponentCount(graph);
        return ccmap.get(graph).getConnectedComponentsCount();
    }

    /**
     * Returns whether each edge in a given graph is undirected.
     *
     * @param graph A given graph.
     * @return Whether each edge in this graph is undirected.
     */
    public static boolean isFullyUndirected(Graph graph) {
        for (Edge edge : graph.getEdgeSet()) {
            if (edge.isDirected()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether a graph contains a directed edge.
     *
     * @param graph A given graph.
     * @return Whether the given graph contains a directed edge.
     */
    public static boolean containsDirected(Graph graph) {
        for (Edge e : graph.getEachEdge()) {
            if (e.isDirected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether a graph contains an undirected edge.
     *
     * @param graph A given graph.
     * @return Whether the given graph contains an undirected edge.
     */
    public static boolean containsUndirected(Graph graph) {
        for (Edge e : graph.getEachEdge()) {
            if (!e.isDirected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether the element is a directed edge or a fully directed graph.
     *
     * @param element Identifier of the element.
     * @return Whether the element is a directed edge or a fully directed graph.
     */
    public static boolean isDirectedGeneral(Element element) {
        if (element instanceof Graph) {
            return isFullyDirected((Graph) element);
        } else if (element instanceof Edge) {
            return ((Edge) element).isDirected();
        }
        return false;
    }

    /**
     * Returns whether the element is an undirected edge or a fully undirected graph.
     *
     * @param element Identifier of the element.
     * @return Whether the element is an undirected edge or a fully undirected graph.
     */
    public static boolean isUnDirectedGeneral(Element element) {
        if (element instanceof Graph) {
            return isFullyUndirected((Graph) element);
        } else if (element instanceof Edge) {
            return !((Edge) element).isDirected();
        }
        return false;
    }

    private static Map<Graph, Set<Edge>> graphmap = new HashMap<>();

    /**
     * Returns the minimal spanning tree of a graph.
     *
     * @param graph A given graph.
     * @return A set of edges containing its minimal spanning tree.
     */
    public static Set<Edge> getMST(Graph graph) {
        if (graphmap.containsKey(graph)) {
            return graphmap.get(graph);
        }
        Kruskal kruskal = new Kruskal();
        kruskal.init(graph);
        kruskal.compute();
        Set<Edge> res = new HashSet<>();
        kruskal.getTreeEdges().forEach(res::add);
        graphmap.put(graph, res);
        return res;
    }

    /**
     * Returns a new grapg of the same type as a given graph.
     *
     * @param graph A given graph.
     * @return A new graph of the same type.
     */
    public static Graph newGraphWithSameType(Graph graph) {
        return newGraphWithSameType(graph, graph.getId());
    }

    /**
     * Returns a new graph of the same type as a given graph.
     *
     * @param graph A given graph.
     * @param ID    ID of the new graph.
     * @return A new graph of the same type.
     */
    public static Graph newGraphWithSameType(Graph graph, String ID) {
        if (graph instanceof DefaultGraph) {
            return new DefaultGraph(ID);
        } else if (graph instanceof SingleGraph) {
            return new SingleGraph(ID);
        } else if (graph instanceof MultiGraph) {
            return new MultiGraph(ID);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns a graph that is identical to the input graph  but with quotes around each attribute value.
     *
     * @param graph A given graph.
     * @return A graph that is equal but with enforced quotes.
     */
    public static Graph enforceQuotes(Graph graph) {
        Graph res = newGraphWithSameType(graph);
        for (String key : graph.getAttributeKeySet()) {
            Object[] arr = {graph.getAttribute(key)};
            res.setAttribute(key, arr);
        }
        for (Node node : graph.getEachNode()) {
            Node added = res.addNode(node.getId());
            for (String key : node.getAttributeKeySet()) {
                Object[] arr = {StringUtils.enforceQuotesIfString(node.getAttribute(key))};
                added.setAttribute(key, arr);
            }
        }
        for (Edge edge : graph.getEachEdge()) {
            Edge added = res.addEdge(edge.getId(), edge.getSourceNode().getId(), edge.getTargetNode().getId(), edge.isDirected());
            for (String key : edge.getAttributeKeySet()) {
                Object[] arr = {StringUtils.enforceQuotesIfString(edge.getAttribute(key))};
                added.setAttribute(key, arr);
            }
        }
        return res;
    }

    public static Graph getEmptyGraph() {
        return new MultiGraph("g");
    }
}
