package compiler.asrc;

import alice.tuprolog.Term;
import exceptions.UnknownGraphTypeException;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.algorithm.coloring.WelshPowell;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import utils.GraphUtils;
import utils.Printer;
import utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static compiler.prolog.TuProlog.*;

public final class AbstractSyntaxRuleConverter {

    //TODO: Labels and Flags
    //TODO: Results of algorithms

    /**
     * TODO: Write a description
     *
     * @param graph the graph which to convert to rules
     * @return the image at the specified URL
     */

    public static List<Term> convertToRules(Graph graph) throws UnknownGraphTypeException {
        List<Term> termList = new ArrayList<>();

        for (Node node : graph.getNodeSet()) {
            termList.addAll(generateNodeRules(node));
        }

        boolean fullydirected = true;
        boolean fullyundirected = true;

        for (Edge edge : graph.getEdgeSet()) {
            fullydirected = fullydirected && edge.isDirected();
            fullyundirected = fullyundirected && !edge.isDirected();
            termList.addAll(generateEdgeRules(edge));
        }

        if (fullydirected && fullyundirected) {
            termList.add(struct("directed", term(graph.getId())));
            termList.add(struct("undirected", term(graph.getId())));
            termList.add(struct("mixed", term(graph.getId())));
        } else if (fullydirected) {
            termList.add(struct("directed", term(graph.getId())));
        } else if (fullyundirected) {
            termList.add(struct("undirected", term(graph.getId())));
        } else {
            termList.add(struct("mixed", term(graph.getId())));
        }

        ConnectedComponents a = new ConnectedComponents();
        a.init(graph);
        int ccount = a.getConnectedComponentsCount();
        termList.add(struct("componentcount", term(graph.getId()), intVal(ccount)));
        if (ccount==1){
            termList.add(struct("isconnected", term(graph.getId())));
        }
        termList.addAll(generateGraphRules(graph));
        return termList;
    }

    /**
     * TODO: Write a description
     *
     * @param node the node for which to generate the rules
     * @return the generated rules
     */
    private static List<Term> generateNodeRules(Node node) {
        List<Term> termList = new ArrayList<>();

        termList.add(struct("node", term(node.getId())));
        termList.add(struct("neighbourcount", intVal(GraphUtils.neighbourCount(node))));

        termList.add(struct("degree", term(node.getId()), intVal(node.getDegree())));
        termList.add(struct("indegree", term(node.getId()), intVal(node.getInDegree())));
        termList.add(struct("outdegree", term(node.getId()), intVal(node.getOutDegree())));

        int neighbourcount = 0;
        Iterator it = node.getNeighborNodeIterator();
        while (it.hasNext()) {
            neighbourcount++;
            it.next();
        }
        termList.add(struct("neighbourcount", term(node.getId()), intVal((neighbourcount))));

        termList.add(struct("attributecount", term(node.getId()), intVal(node.getAttributeCount())));
        for (String attributeKey : node.getAttributeKeySet()) {
            String attributeString = StringUtils.ObjectToString(node.getAttribute(attributeKey));
            termList.add(struct("attribute", term(attributeKey), term(node.getId()), term(attributeString)));
        }
        return termList;
    }

    // attriute("label",X,T) -> label(X,T).
    //

    /**
     * TODO: Write a description
     *
     * @param edge the edge for which to generate the rules
     * @return the generated rules
     */
    private static List<Term> generateEdgeRules(Edge edge) {
        //TODO: Willen we iets doen met loop? (Does the source and target of this edge identify the same node ?)
        List<Term> termList = new ArrayList<>();

        termList.add(struct("edge", term(edge.getTargetNode().getId()), term(edge.getSourceNode().getId()), term(edge.getId())));

        if (edge.isDirected()) {
            termList.add(struct("directed", term(edge.getId())));
        } else {
            termList.add(struct("undirected", term(edge.getId())));
        }

        termList.add(struct("attributecount", term(edge.getId()), intVal(edge.getAttributeCount())));
        for (String attributeKey : edge.getAttributeKeySet()) {
            String attributeString = StringUtils.ObjectToString(edge.getAttribute(attributeKey));
            termList.add(struct("attribute", term(attributeKey), term(edge.getId()), term(attributeString)));
        }
        return termList;
    }

    /**
     * TODO: Write a description
     *
     * @param graph the graph for which to generate the rules
     * @return the generated rules
     */
    public static List<Term> generateGraphRules(Graph graph) throws UnknownGraphTypeException {
        List<Term> termList = new ArrayList<>();
        termList.add(struct("graph", term(graph.getId())));

        termList.addAll(generateGraphTypeRules(graph));
        termList.add(struct("edgecount", term(graph.getId()), intVal((graph.getEdgeCount()))));
        termList.add(struct("nodecount", term(graph.getId()), intVal((graph.getNodeCount()))));

        termList.add(struct("attributecount", term(graph.getId()), intVal(graph.getAttributeCount())));
        for (String attributeKey : graph.getAttributeKeySet()) {
            String attributeString = StringUtils.ObjectToString(graph.getAttribute(attributeKey));
            termList.add(struct("attribute", term(attributeKey), term(graph.getId()), term(attributeString)));
        }

        //For the minimum spanning tree:
        Kruskal kruskal = new Kruskal();
        kruskal.init(graph);
        kruskal.compute();
        kruskal.getTreeEdges().forEach(edge ->
                termList.add(struct("inmst", term(edge.getId())))
        );

        WelshPowell a = new WelshPowell();
        a.init(Graphs.clone(graph));
        a.compute();
        termList.add(struct("chromaticnumber", term(graph.getId()), term(String.valueOf(a.getChromaticNumber()))));

        return termList;
    }

    /**
     * TODO: Write a description
     *
     * @param graph the graph for which to generate the rules
     * @return the generated rules
     */
    private static List<Term> generateGraphTypeRules(Graph graph) throws UnknownGraphTypeException {
        List<Term> termList = new LinkedList<>();
        if (graph instanceof SingleGraph) {
            termList.add(struct("singlegraph", term(graph.getId())));
        } else if (graph instanceof MultiGraph) {
            termList.add(struct("multigraph", term(graph.getId())));
        } else {
            throw new UnknownGraphTypeException("Unknown graph type: " + graph.getClass().getName());
            //TODO, figure out which class it is then and throw an error.
            //If You manage to do this, you wouldn't the if/else structure above and can
            //just apply it directly.
        }
        return termList;
    }
}
