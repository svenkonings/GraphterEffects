package com.github.meteoorkip.utils;

import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;

public class GraphUtils {

    private static final Map<Graph, Set<Edge>> spanningTrees = new HashMap<>();


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
     * Returns the number of neighbours a graph node has.
     *
     * @param node A given node.
     * @return The number of neighbours the node has.
     */
    public static int neighbourCount(Node node) {
        return Math.toIntExact(node.neighborNodes().count());
    }


    /**
     * Prefix to be placed before each graph identifier to avoid usage in Graafvis script.
     */
    public static final String ILLEGAL_PREFIX = "#";
    private static final Map<Graph, ConnectedComponents> connectedComponents = new HashMap<>();


    private static void initComponentCount(Graph graph) {
        if (connectedComponents.containsKey(graph)) {
            return;
        }
        ConnectedComponents connectedComponents = new ConnectedComponents();
        connectedComponents.init(graph);
        connectedComponents.compute();
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
