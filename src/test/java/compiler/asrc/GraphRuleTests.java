package compiler.asrc;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import compiler.prolog.TuProlog;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static compiler.prolog.TuProlog.struct;
import static compiler.prolog.TuProlog.var;
import static utils.TestUtils.*;

public class GraphRuleTests {

    public static TuProlog generateGraphProlog(Graph graph) throws UnknownGraphTypeException, InvalidTheoryException {
        List<Term> terms = AbstractSyntaxRuleConverter.convertToRules(graph);
        return new TuProlog(terms);
    }

    public static void graphTest(TuProlog prolog, Graph graph) {
        structureTest(prolog, graph);
        graphPropertiesTest(prolog, graph);
        graphIntegerInvariantsTest(prolog, graph);

        for (Node node : graph.getNodeSet()) {
            nodePropertiesTest(prolog, node);
            nodeIntegerInvariantsTest(prolog, node);
        }

        for (Edge edge : graph.getEdgeSet()) {
            edgePropertiesTest(prolog, edge);
            edgeIntegerInvariantsTest(prolog, edge);
        }
    }

    public static void structureTest(TuProlog prolog, Graph graph) {
        Collection<Map<String, Term>> answers1 = prolog.solve(struct("node", var("ID")));
        assert answers1.size() == graph.getNodeSet().size();
        graph.getNodeSet().forEach(node -> {
            try {
                answerContains(answers1, "ID", node.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Collection<Map<String, Term>> answers2 = prolog.solve(struct("edge", var("Target"), var("Source"), var("ID")));
        assert answers2.size() == graph.getEdgeSet().size();
        graph.getEdgeSet().forEach(edge -> {
            try {
                answerContains(answers2, "Target", edge.getTargetNode().getId(), "Source", edge.getSourceNode().getId(), "ID", edge.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void graphPropertiesTest(TuProlog prolog, Graph graph) {
        boolean fullydirected = true;
        boolean fullyundirected = true;
        for (Edge edge : graph.getEdgeSet()) {
            fullydirected = fullydirected && edge.isDirected();
            fullyundirected = fullyundirected && !edge.isDirected();
        }

        testSimpleFact(prolog, graph, "directed", graph.getId(), fullydirected);
        testSimpleFact(prolog, graph, "undirected", graph.getId(), fullyundirected);
        testSimpleFact(prolog, graph, "mixed", graph.getId(), !(fullydirected || fullyundirected));

        testSimpleFact(prolog, graph, "singlegraph", graph.getId(), graph instanceof SingleGraph);
        testSimpleFact(prolog, graph, "multigraph", graph.getId(), graph instanceof MultiGraph);

        testAttributesCorrect(prolog, graph);
    }

    public static void graphIntegerInvariantsTest(TuProlog prolog, Graph graph) {
        testPredicateValue(prolog, graph, "nodecount", graph.getId(), String.valueOf(graph.getNodeSet().size()));
        testPredicateValue(prolog, graph, "edgecount", graph.getId(), String.valueOf(graph.getEdgeSet().size()));
        testPredicateValue(prolog, graph, "attributecount", graph.getId(), String.valueOf(graph.getAttributeKeySet().size()));
    }

    public static void nodePropertiesTest(TuProlog prolog, Node node) {
        testAttributesCorrect(prolog, node);
    }

    public static void nodeIntegerInvariantsTest(TuProlog prolog, Node node) {
        testPredicateValue(prolog, node, "degree", node.getId(), String.valueOf(node.getDegree()));
        testPredicateValue(prolog, node, "indegree", node.getId(), String.valueOf(node.getInDegree()));
        testPredicateValue(prolog, node, "outdegree", node.getId(), String.valueOf(node.getOutDegree()));
        testPredicateValue(prolog, node, "attributecount", node.getId(), String.valueOf(node.getAttributeCount()));

        int neighbourcount = 0;
        Iterator it = node.getNeighborNodeIterator();
        while (it.hasNext()) {
            neighbourcount++;
            it.next();
        }
        testPredicateValue(prolog, node, "neighbourcount", node.getId(), String.valueOf(neighbourcount));
    }

    public static void edgePropertiesTest(TuProlog prolog, Edge edge) {
        testSimpleFact(prolog, edge, "directed", edge.getId(), edge.isDirected());
        testSimpleFact(prolog, edge, "undirected", edge.getId(), !edge.isDirected());
        testAttributesCorrect(prolog, edge);
    }

    public static void edgeIntegerInvariantsTest(TuProlog prolog, Edge edge) {
        testPredicateValue(prolog, edge, "attributecount", edge.getId(), String.valueOf(edge.getAttributeCount()));
    }
}
