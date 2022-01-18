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
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GraphUtils {

    private static Map<Graph, Set<Edge>> spanningTrees = new HashMap<>();


    public static Graph getEmptyGraph() {
        return new MultiGraph("g");
    }

    /**
     * Returns the minimal spanning tree of a graph.
     *
     * @param graph A given graph.
     * @return A set of edges containing its minimal spanning tree.
     */
    public static Set<Edge> getMST(Graph graph) {
        if (spanningTrees.containsKey(graph)) {
            return spanningTrees.get(graph);
        }
        Kruskal kruskal = new Kruskal();
        kruskal.init(graph);
        kruskal.compute();
        Set<Edge> res = new HashSet<>();
        kruskal.getTreeEdges().forEach(res::add);
        spanningTrees.put(graph, res);
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
        node.edges().forEach(e ->
            neighbours.add(e.getOpposite(node))
        );
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
     * Prefix to be placed before each graph identifier to avoid usage in Graafvis script.
     */
    public static final String ILLEGAL_PREFIX = "#";
    private static final Map<Graph, ConnectedComponents> connectedComponents = new HashMap<>();

    /**
     * Returns whether each edge in a given graph is undirected.
     *
     * @param graph A given graph.
     * @return Whether each edge in this graph is undirected.
     */
    public static boolean isFullyUndirected(Graph graph) {
        for (Edge edge : graph.edges().collect(Collectors.toSet())) {
            if (edge.isDirected()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether each edge in a given graph is directed.
     *
     * @param graph A given graph.
     * @return Whether each edge in this graph is directed.
     */
    public static boolean isFullyDirected(Graph graph) {
        for (Edge edge : graph.edges().collect(Collectors.toSet())) {
            if (!edge.isDirected()) {
                return false;
            }
        }
        return true;
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


    private static void initComponentCount(Graph graph) {
        if (connectedComponents.containsKey(graph)) {
            return;
        }
        ConnectedComponents connectedComponents = new ConnectedComponents();
        connectedComponents.init(graph);
        connectedComponents.compute();
        connectedComponents.setCountAttribute("_ATTRIBUTE_DETERMINING_WHICH_COMPONENT_THE_NODE_BELONGS_TO_");
        GraphUtils.connectedComponents.put(graph, connectedComponents);
    }

    /**
     * Returns the number of connected components a graph has.
     *
     * @param graph A given graph.
     * @return The number of connected components in this graph.
     */
    public static int ConnectedComponentsCount(Graph graph) {
        initComponentCount(graph);
        return connectedComponents.get(graph).getConnectedComponentsCount();
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
     * Changes a Graph such that all objects in the Graph have a new ID that is the old one prefixed by an illegal
     * prefix.
     *
     * @param input Graph to change.
     * @return Changed graph.
     */
    public static Graph changeIDs(Graph input) {
        Graph res = newGraphWithSameType(input, ILLEGAL_PREFIX + input.getId());
        input.attributeKeys().forEach(key -> {
            Object[] arr = {input.getAttribute(key)};
            res.setAttribute(key, arr);
        });
        input.nodes().forEach(node -> {
            Node added = res.addNode(ILLEGAL_PREFIX + node.getId());
            node.attributeKeys().forEach(key -> {
                Object[] arr = {node.getAttribute(key)};
                added.setAttribute(key, arr);
            });
        });
        input.edges().forEach(edge -> {
            Edge added = res.addEdge(ILLEGAL_PREFIX + edge.getId(), ILLEGAL_PREFIX + edge.getSourceNode(), ILLEGAL_PREFIX + edge.getTargetNode(), edge.isDirected());
            edge.attributeKeys().forEach(key -> {
                Object[] arr = {edge.getAttribute(key)};
                added.setAttribute(key, arr);
            });
        });
        return res;
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
        graph.attributeKeys().forEach(key -> {
            Object[] arr = {graph.getAttribute(key)};
            res.setAttribute(key, arr);
        });
        graph.nodes().forEach(node -> {
            Node added = res.addNode(node.getId());
            node.attributeKeys().forEach(key -> {
                Object[] arr = {StringUtils.enforceQuotesIfString(node.getAttribute(key))};
                added.setAttribute(key, arr);
            });
        });
        graph.edges().forEach(edge -> {
            Edge added = res.addEdge(edge.getId(), edge.getSourceNode().getId(), edge.getTargetNode().getId(), edge.isDirected());
            edge.attributeKeys().forEach(key -> {
                Object[] arr = {StringUtils.enforceQuotesIfString(edge.getAttribute(key))};
                added.setAttribute(key, arr);
            });
        });
        return res;
    }

    public static Optional<Element> getById(Graph graph, String id) {
        if (graph.getId().equals(id)) {
            return Optional.of(graph);
        }
        Node node = graph.getNode(id);
        if (node != null) {
            return Optional.of(node);
        }
        Edge edge = graph.getEdge(id);
        if (edge != null) {
            return Optional.of(edge);
        }
        return Optional.empty();

    }

    @NotNull
    public static Set<Element> getGraphElements(Graph graph, boolean graphApplicable, boolean nodesApplicable, boolean edgesApplicable, Predicate<Element> predicate) {
        Set<Element> candidates = new HashSet<>();
        if (graphApplicable && predicate.test(graph)) {
            candidates.add(graph);
        }
        if (nodesApplicable) {
            graph.nodes().filter(predicate).forEach(candidates::add);
        }
        if (edgesApplicable) {
            graph.edges().filter(predicate).forEach(candidates::add);
        }
        return candidates;
    }
}
