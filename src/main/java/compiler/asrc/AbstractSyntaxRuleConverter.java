package compiler.asrc;

import alice.tuprolog.Term;
import exceptions.UnknownGraphTypeException;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import utils.GraphUtils;
import utils.StringUtils;
import za.co.wstoop.jatalog.DatalogException;

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
     * @param  graph the graph which to convert to rules
     * @return the image at the specified URL
     */

    public static List<Term> convertToRules(Graph graph) throws DatalogException, UnknownGraphTypeException {
        List<Term> termList = new ArrayList<>();

        for (Node node: graph.getNodeSet()){
            termList.addAll(generateNodeRules(node));
        }

        boolean fullydirected = true;
        boolean fullyundirected = true;

        for (Edge edge: graph.getEdgeSet()) {
            fullydirected = fullydirected && edge.isDirected();
            fullyundirected = fullyundirected && !edge.isDirected();
            termList.addAll(generateEdgeRules(edge));
        }

        if (fullydirected && fullyundirected) {
            termList.add(fact(compound("directed", atom(graph.getId()))));
            termList.add(fact(compound("undirected", atom(graph.getId()))));
            termList.add(fact(compound("mixed", atom(graph.getId()))));
        } else if (fullydirected) {
            termList.add(fact(compound("directed", atom(graph.getId()))));
        } else if (fullyundirected) {
            termList.add(fact(compound("undirected", atom(graph.getId()))));
        } else {
            termList.add(fact(compound("mixed", atom(graph.getId()))));
        }

        termList.addAll(generateGraphRules(graph));

        return termList;
    }

    /**
     * TODO: Write a description
     * @param  node the node for which to generate the rules
     * @return the generated rules
     */
    private static List<Term> generateNodeRules(Node node) {
        List<Term> termList = new ArrayList<>();
        
        termList.add(fact(compound("node",atom(node.getId()))));
        termList.add(fact(compound("neighbourcount", intVal(GraphUtils.neighbourCount(node)))));

        termList.add(fact(compound("degree", atom(node.getId()), intVal(node.getDegree()))));
        termList.add(fact(compound("indegree", atom(node.getId()), intVal(node.getInDegree()))));
        termList.add(fact(compound("outdegree", atom(node.getId()), intVal(node.getOutDegree()))));

        int neighbourcount = 0;
        Iterator it = node.getNeighborNodeIterator();
        while (it.hasNext()){
            neighbourcount++;
            it.next();
        }
        termList.add(fact(compound("neighbourcount",atom(node.getId()),intVal((neighbourcount)))));

        termList.add(fact(compound("attributecount", atom(node.getId()), intVal(node.getAttributeCount()))));
        for (String attributeKey: node.getAttributeKeySet()){
            String attributeString = StringUtils.ObjectToString(node.getAttribute(attributeKey));
            termList.add(fact(compound("attribute", atom(attributeKey), atom(node.getId()), atom(attributeString) )));
        }
        return termList;
    }

    // attriute("label",X,T) -> label(X,T).
    //

    /**
     * TODO: Write a description
     * @param  edge the edge for which to generate the rules
     * @return the generated rules
     */
    private static List<Term> generateEdgeRules(Edge edge) {
        //TODO: Willen we iets doen met loop? (Does the source and target of this edge identify the same node ?)
        List<Term> termList = new ArrayList<>();

        termList.add(fact(compound("edge",atom(edge.getTargetNode().getId()), atom(edge.getSourceNode().getId()), atom(edge.getId()))));

        if (edge.isDirected()) {
            termList.add(fact(compound("directed",atom(edge.getId()))));
        } else {
            termList.add(fact(compound("undirected",atom(edge.getId()))));
        }

        termList.add(fact(compound("attributecount", atom(edge.getId()), intVal(edge.getAttributeCount()))));
        for (String attributeKey: edge.getAttributeKeySet()){
            String attributeString = StringUtils.ObjectToString(edge.getAttribute(attributeKey));
            termList.add(fact(compound("attribute", atom(attributeKey), atom(edge.getId()), atom(attributeString) )));
        }
        return termList;
    }

    /**
     * TODO: Write a description
     * @param  graph the graph for which to generate the rules
     * @return the generated rules
     */
    public static List<Term> generateGraphRules(Graph graph) throws UnknownGraphTypeException {
        List<Term> termList = new ArrayList<>();
        termList.add(fact(compound("graph",atom(graph.getId()))));

        termList.addAll(generateGraphTypeRules(graph));
        termList.add(fact(compound("edgecount",atom(graph.getId()),intVal((graph.getEdgeSet().size())))));
        termList.add(fact(compound("nodecount",atom(graph.getId()),intVal((graph.getEdgeSet().size())))));

        termList.add(fact(compound("attributecount", atom(graph.getId()), intVal(graph.getAttributeCount()))));
        for (String attributeKey: graph.getAttributeKeySet()){
            String attributeString = StringUtils.ObjectToString(graph.getAttribute(attributeKey));
            termList.add(fact(compound("attribute", atom(attributeKey), atom(graph.getId()), atom(attributeString) ))); //TODO: Attributes aren't always strings.
        }

        //For the minimum spanning tree:
        Kruskal kruskal = new Kruskal();
        kruskal.init(graph);
        kruskal.compute();
        kruskal.getTreeEdges().forEach(edge ->
                termList.add(fact(compound("inmst", atom(edge.getId()))))
        );
        return termList;
    }

    /**
     * TODO: Write a description
     * @param  graph the graph for which to generate the rules
     * @return the generated rules
     */
    private static List<Term> generateGraphTypeRules(Graph graph) throws UnknownGraphTypeException {
        List<Term> termList = new LinkedList<>();
        if (graph instanceof SingleGraph) {
            termList.add(fact(compound("singlegraph",atom(graph.getId()))));
        }
        else if (graph instanceof MultiGraph) {
            termList.add(fact(compound("multigraph", atom(graph.getId()))));
        }
        else {
            throw new UnknownGraphTypeException("Unknown graph type: " + graph.getClass().getName());
             //TODO, figure out which class it is then and throw an error.
            //If You manage to do this, you wouldn't the if/else structure above and can
            //just apply it directly.
        }
        return termList;
    }



}
