package compiler.asrc;

import org.graphstream.algorithm.Kruskal;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import utils.ExprUtils;
import utils.StringUtils;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Expr;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class AbstractSyntaxRuleConverter {

    //TODO: Labels and Flags
    //TODO: Results of algorithms

    /**
     * TODO: Write a description
     * @param  graph the graph which to convert to rules
     * @return the image at the specified URL
     */

    public static List<Expr> convertToRules(Graph graph) throws DatalogException {
        List<Expr> exprlist = new LinkedList<>();

        for (Node node: graph.getNodeSet()){
            exprlist.addAll(generateNodeRules(node));
        }

        boolean fullydirected = true;
        boolean fullyundirected = true;

        for (Edge edge: graph.getEdgeSet()) {
            fullydirected = fullydirected && edge.isDirected();
            fullyundirected = fullyundirected && !edge.isDirected();
            exprlist.addAll(generateEdgeRules(edge));
        }

        //TODO: is an graph with 0 edges directed or undirected or mixed?
        if (fullydirected) {
            exprlist.add(new Expr("directed", graph.getId()));
        } else if (fullyundirected) {
            exprlist.add(new Expr("undirected", graph.getId()));
        } else {
            exprlist.add(new Expr("mixed", graph.getId()));
        }

        exprlist.addAll(generateGraphRules(graph));

        //TODO: read graph properties

        return exprlist;
    }

    /**
     * TODO: Write a description
     * @param  node the node for which to generate the rules
     * @return the generated rules
     */
    private static List<Expr> generateNodeRules(Node node) {
        //TODO: neighbourcount toevoegen. Want degrees tellen ook loops naar jezelf mee, je kan dus meerdere lijnen naar dezelfde hebben en loops worden ook meegerekend.
        // TODO: Deze worden 2x meegerkend/
        List<Expr> exprlist = new LinkedList<>();

        exprlist.add(new Expr("node",node.getId()));

        exprlist.add(new Expr("degree",node.getId(),String.valueOf(node.getDegree())));
        exprlist.add(new Expr("indegree",node.getId(),String.valueOf(node.getInDegree())));
        exprlist.add(new Expr("outdegree",node.getId(),String.valueOf(node.getOutDegree())));

        int neighbourcount = 0;
        Iterator it = node.getNeighborNodeIterator();
        while (it.hasNext()){
            neighbourcount++;
            it.next();
        }
        exprlist.add(new Expr("neighbourcount",node.getId(),String.valueOf(neighbourcount)));

        exprlist.add(new Expr("attributecount",node.getId(), String.valueOf(node.getAttributeCount())));
        for (String attributeKey: node.getAttributeKeySet()){
            String attributeString = StringUtils.ObjectToString(node.getAttribute(attributeKey));
            exprlist.add(new Expr("attribute",attributeKey, node.getId(), attributeString ));
        }
        return exprlist;
    }

    // attriute("label",X,T) -> label(X,T).
    //

    /**
     * TODO: Write a description
     * @param  edge the edge for which to generate the rules
     * @return the generated rules
     */
    private static List<Expr> generateEdgeRules(Edge edge) {
        //TODO: Willen we iets doen met loop? (Does the source and target of this edge identify the same node ?)
        List<Expr> exprlist = new LinkedList<>();

        exprlist.add(new Expr("edge",edge.getTargetNode().getId(), edge.getSourceNode().getId(),edge.getId()));

        if (edge.isDirected()) {
            exprlist.add(new Expr("directed", edge.getId()));
        } else {
            exprlist.add(new Expr("undirected", edge.getId()));
        }

        exprlist.add(new Expr("attributecount",edge.getId(), String.valueOf(edge.getAttributeCount())));
        for (String attributeKey: edge.getAttributeKeySet()){
            String attributeString = StringUtils.ObjectToString(edge.getAttribute(attributeKey));
            exprlist.add(new Expr("attribute", attributeKey, edge.getId(), attributeString));
        }



        return exprlist;
    }

    /**
     * TODO: Write a description
     * @param  graph the graph for which to generate the rules
     * @return the generated rules
     */
    public static List<Expr> generateGraphRules(Graph graph) {
        List<Expr> exprlist = new LinkedList<>();
        exprlist.add(new Expr("graph",graph.getId()));

        exprlist.addAll(generateGraphTypeRules(graph));
        exprlist.add(new Expr("edgecount",graph.getId(), String.valueOf(graph.getEdgeCount())));
        exprlist.add(new Expr("nodecount",graph.getId(), String.valueOf(graph.getNodeCount())));

        exprlist.add(new Expr("attributecount",graph.getId(), String.valueOf(graph.getAttributeCount())));
        for (String attributeKey: graph.getAttributeKeySet()){
            exprlist.add(new Expr(attributeKey, graph.getId(), graph.getAttribute(attributeKey)));
        }

        //For the minimum spanning tree:
        Kruskal kruskal = new Kruskal();
        kruskal.init(graph);
        kruskal.compute();
        kruskal.getTreeEdges().forEach(edge ->
        exprlist.add(new Expr("inmst", ExprUtils.elementExpr(edge).getTerms())));
        System.out.println("test");
        return exprlist;
    }

    /**
     * TODO: Write a description
     * @param  graph the graph for which to generate the rules
     * @return the generated rules
     */
    private static List<Expr> generateGraphTypeRules(Graph graph){
        List<Expr> exprlist = new LinkedList<>();
        if (graph instanceof SingleGraph) {
            exprlist.add(new Expr("singlegraph", graph.getId()));
        }
        else if (graph instanceof MultiGraph) {
            exprlist.add(new Expr("multigraph", graph.getId()));
        }
        else {
            //TODO, figure out which class it is then and throw an error.
            //If You manage to do this, you wouldn't the if/else structure above and can
            //just apply it directly.
        }
        return exprlist;
    }



}
