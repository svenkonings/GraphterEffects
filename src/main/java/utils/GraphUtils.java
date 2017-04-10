package utils;

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



    private static Map<Graph, ConnectedComponents> ccmap = new HashMap<>();
    public static void initComponentCount(Graph graph) {
        if (ccmap.containsKey(graph)) {return;}
        ConnectedComponents connectedComponents = new ConnectedComponents();
        connectedComponents.init(graph);
        connectedComponents.setCountAttribute("_ATTRIBUTE_DETERMINING_WHICH_COMPONENT_THE_NODE_BELONGS_TO_");
        connectedComponents.compute();
        ccmap.put(graph, connectedComponents);
    }

    public static int ConnectedComponentsCount(Graph graph) {
        initComponentCount(graph);
        return ccmap.get(graph).getConnectedComponentsCount();
    }

    public static boolean isFullyUndirected(Graph graph) {
        for (Edge edge : graph.getEdgeSet()) {
            if (edge.isDirected()) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean containsDirected(Graph g) {
        for (Edge e : g.getEachEdge()) {
            if (e.isDirected()) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsUndirected(Graph g) {
        for (Edge e : g.getEachEdge()) {
            if (!e.isDirected()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDirectedGeneral(Element e) {
        if (e instanceof Graph) {
            return isFullyDirected((Graph) e);
        } else if (e instanceof Edge) {
            return ((Edge) e).isDirected();
        }
        return false;
    }

    public static boolean isUnDirectedGeneral(Element e) {
        if (e instanceof Graph) {
            return isFullyUndirected((Graph) e);
        } else if (e instanceof Edge) {
            return !((Edge) e).isDirected();
        }
        return false;
    }

    private static Map<Graph, Set<Edge>> graphmap = new HashMap<>();

    public static Set<Edge> getMST(Graph g) {
        if (graphmap.containsKey(g)) {
            return graphmap.get(g);
        }
        Kruskal kruskal = new Kruskal();
        kruskal.init(g);
        kruskal.compute();
        Set<Edge> res = new HashSet<>();
        kruskal.getTreeEdges().forEach(res::add);
        graphmap.put(g, res);
        return res;
    }

    public static Graph enforceQuotes(Graph g) {
        Graph res;
        if (g instanceof DefaultGraph) {
            res = new DefaultGraph(g.getId());
        } else if (g instanceof SingleGraph) {
            res = new SingleGraph(g.getId());
        } else if (g instanceof MultiGraph) {
            res = new MultiGraph(g.getId());
        } else {
            throw new UnsupportedOperationException();
        }
        for (String key : g.getAttributeKeySet()) {
            Object[] arr = {g.getAttribute(key)};
            res.setAttribute(key, arr);
        }
        for (Node node : g.getEachNode()) {
            Node added = res.addNode(node.getId());
            for (String key : node.getAttributeKeySet()) {
                Object[] arr = {StringUtils.ObjectToString(node.getAttribute(key))};
                added.setAttribute(key, arr);
            }
        }
        for (Edge edge : g.getEachEdge()) {
            Edge added = res.addEdge(edge.getId(), edge.getSourceNode().getId(), edge.getTargetNode().getId(), edge.isDirected());
            for (String key : edge.getAttributeKeySet()) {
                Object[] arr = {StringUtils.enforceQuotesIfString(edge.getAttribute(key))};
                added.setAttribute(key, arr);
            }
        }
        return res;
    }
}
