package compiler.asrc;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Expr;
import za.co.wstoop.jatalog.Rule;

import java.util.Arrays;
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
    public static List<Rule> convertToRules(Graph graph) throws DatalogException {
        List<Rule> rulelist = new LinkedList<>();

        for (Node node: graph.getNodeSet()){
            rulelist.addAll(generateNodeRules(node));
        }

        boolean fullydirected = true;
        boolean fullyundirected = true;

        for (Edge edge: graph.getEdgeSet()) {
            fullydirected = fullydirected && edge.isDirected();
            fullyundirected = fullyundirected && !edge.isDirected();
            rulelist.addAll(generateEdgeRules(edge));
        }

        //TODO: is an graph with 0 edges directed or undirected or mixed?
        if (fullydirected) {
            rulelist.add(generateRule("directed", graph.getId()));
        } else if (fullyundirected) {
            rulelist.add(generateRule("undirected", graph.getId()));
        } else {
            rulelist.add(generateRule("mixed", graph.getId()));
        }

        rulelist.addAll(generateGraphRules(graph));

        return rulelist;
    }

    /**
     * TODO: Write a description
     * @param  parameters the parameters of the rule
     * @return the generated rule
     */
    private static Rule generateRule(String... parameters) {
        if (parameters.length > 2) {
            return new Rule(new Expr(parameters[0], Arrays.copyOfRange(parameters, 1, parameters.length)));
        } else {
            return new Rule(new Expr(parameters[0], parameters[1]));
        }
    }

    /**
     * TODO: Write a description
     * @param  node the node for which to generate the rules
     * @return the generated rules
     */
    private static List<Rule> generateNodeRules(Node node) {
        //TODO: neighbourcount toevoegen. Want degrees tellen ook loops naar jezelf mee, je kan dus meerdere lijnen naar dezelfde hebben en loops worden ook meegerekend.
        // TODO: Deze worden 2x meegerkend/
        List<Rule> rulelist = new LinkedList<>();

        rulelist.add(new Rule(new Expr("node",node.getId())));

        rulelist.add(generateRule("degree",node.getId(),String.valueOf(node.getDegree())));
        rulelist.add(generateRule("indegree",node.getId(),String.valueOf(node.getInDegree())));
        rulelist.add(generateRule("outdegree",node.getId(),String.valueOf(node.getOutDegree())));

        int neighbourcount = 0;
        Iterator it = node.getNeighborNodeIterator();
        while (it.hasNext()){
            neighbourcount++;
            it.next();
        }
        rulelist.add(generateRule("neighbourcount",node.getId(),String.valueOf(neighbourcount)));

        rulelist.add(generateRule("attributecount",node.getId(), String.valueOf(node.getAttributeCount())));
        for (String attributeKey: node.getAttributeKeySet()){
            rulelist.add(generateRule("attribute",attributeKey, node.getId(), node.getAttribute(attributeKey)));
        }
        return rulelist;
    }

    // attriute("label",X,T) -> label(X,T).
    //

    /**
     * TODO: Write a description
     * @param  edge the edge for which to generate the rules
     * @return the generated rules
     */
    private static List<Rule> generateEdgeRules(Edge edge) {
        //TODO: Willen we iets doen met loop? (Does the source and target of this edge identify the same node ?)
        List<Rule> rulelist = new LinkedList<>();

        rulelist.add(generateRule("edge",edge.getTargetNode().getId(), edge.getSourceNode().getId(),edge.getId()));

        if (edge.isDirected()) {
            rulelist.add(generateRule("directed", edge.getId()));
        } else {
            rulelist.add(generateRule("undirected", edge.getId()));
        }

        rulelist.add(generateRule("attributecount",edge.getId(), String.valueOf(edge.getAttributeCount())));
        for (String attributeKey: edge.getAttributeKeySet()){
            try {
                rulelist.add(generateRule("attribute", attributeKey, edge.getId(), edge.getAttribute(attributeKey).toString()));
            } catch (Exception e){
                System.out.println("WTF???");
            }
        }



        return rulelist;
    }

    /**
     * TODO: Write a description
     * @param  graph the graph for which to generate the rules
     * @return the generated rules
     */
    public static List<Rule> generateGraphRules(Graph graph) {
        List<Rule> rulelist = new LinkedList<>();
        rulelist.add(generateRule("graph",graph.getId()));

        rulelist.addAll(generateGraphTypeRules(graph));
        rulelist.add(generateRule("edgecount",graph.getId(), String.valueOf(graph.getEdgeCount())));
        rulelist.add(generateRule("nodecount",graph.getId(), String.valueOf(graph.getNodeCount())));

        rulelist.add(generateRule("attributecount",graph.getId(), String.valueOf(graph.getAttributeCount())));
        for (String attributeKey: graph.getAttributeKeySet()){
            rulelist.add(generateRule(attributeKey, graph.getId(), graph.getAttribute(attributeKey)));
        }

        return rulelist;
    }

    /**
     * TODO: Write a description
     * @param  graph the graph for which to generate the rules
     * @return the generated rules
     */
    private static List<Rule> generateGraphTypeRules(Graph graph){
        List<Rule> rulelist = new LinkedList<>();
        if (graph instanceof SingleGraph) {
            rulelist.add(generateRule("singlegraph", graph.getId()));
        }
        else if (graph instanceof MultiGraph) {
            rulelist.add(generateRule("multigraph", graph.getId()));
        }
        else {
            //TODO, figure out which class it is then and throw an error.
            //If You manage to do this, you wouldn't the if/else structure above and can
            //just apply it directly.
        }
        return rulelist;
    }


}
