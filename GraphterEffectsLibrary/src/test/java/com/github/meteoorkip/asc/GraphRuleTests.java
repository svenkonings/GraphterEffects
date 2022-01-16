package com.github.meteoorkip.asc;

import com.github.meteoorkip.prolog.PrologException;
import com.github.meteoorkip.prolog.TuProlog;
import com.github.meteoorkip.utils.GraphUtils;
import it.unibo.tuprolog.core.Term;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.meteoorkip.prolog.TuProlog.*;
import static com.github.meteoorkip.utils.TestUtils.*;

public class GraphRuleTests {

    public static TuProlog generateGraphProlog(Graph graph)  {
        TuProlog prolog = new TuProlog();
        prolog.loadLibrary(new ASCLibrary(graph));
        return prolog;
    }

    public static void graphTest(TuProlog prolog, Graph graph) throws PrologException {
        structureTest(prolog, graph);
        graphPropertiesTest(prolog, graph);
        graphIntegerInvariantsTest(prolog, graph);

        for (Node node : graph.nodes().collect(Collectors.toSet())) {
            nodePropertiesTest(prolog, node);
            nodeIntegerInvariantsTest(prolog, node);
        }

        for (Edge edge : graph.edges().collect(Collectors.toSet())) {
            edgePropertiesTest(prolog, edge);
            edgeIntegerInvariantsTest(prolog, edge);
        }
    }

    public static void structureTest(TuProlog prolog, Graph graph) {
        Collection<Map<String, Term>> answers1 = prolog.solve(struct("node", var("ID")));
        assert answers1.size() == graph.getNodeCount();
        graph.forEach(node -> {
            try {
                answerContains(answers1, var("ID"), atom(node.getId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Collection<Map<String, Term>> answers2 = prolog.solve(struct("edge", var("Target"), var("Source"), var("ID")));
        assert answers2.size() == graph.getEdgeCount();
        graph.edges().forEach(edge -> {
            try {
                answerContains(answers2, var("Target"), atom(edge.getTargetNode().getId()), var("Source"), atom(edge.getSourceNode().getId()), var("ID"), atom(edge.getId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void graphPropertiesTest(TuProlog prolog, Graph graph) throws PrologException {
        boolean fullydirected = true;
        boolean fullyundirected = true;
        for (Edge edge : graph.edges().collect(Collectors.toSet())) {
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

    public static void graphIntegerInvariantsTest(TuProlog prolog, Graph graph) throws PrologException {
        testPredicateValue(prolog, graph, "nodeCount", graph.getId(), intVal(graph.getNodeCount()));
        testPredicateValue(prolog, graph, "edgeCount", graph.getId(), intVal(graph.getEdgeCount()));
        testPredicateValue(prolog, graph, "attributeCount", graph.getId(), intVal(graph.getAttributeCount()));
    }

    public static void nodePropertiesTest(TuProlog prolog, Node node) throws PrologException {
        testAttributesCorrect(prolog, node);
    }

    public static void nodeIntegerInvariantsTest(TuProlog prolog, Node node) throws PrologException {
        testPredicateValue(prolog, node, "degree", node.getId(), intVal(node.getDegree()));
        testPredicateValue(prolog, node, "indegree", node.getId(), intVal(node.getInDegree()));
        testPredicateValue(prolog, node, "outdegree", node.getId(), intVal(node.getOutDegree()));
        testPredicateValue(prolog, node, "attributeCount", node.getId(), intVal(node.getAttributeCount()));
        testPredicateValue(prolog, node, "neighbourCount", node.getId(), intVal(GraphUtils.neighbourCount(node)));
    }

    public static void edgePropertiesTest(TuProlog prolog, Edge edge) throws PrologException {
        testSimpleFact(prolog, edge, "directed", edge.getId(), edge.isDirected());
        testSimpleFact(prolog, edge, "undirected", edge.getId(), !edge.isDirected());
        testAttributesCorrect(prolog, edge);
    }

    public static void edgeIntegerInvariantsTest(TuProlog prolog, Edge edge) throws PrologException {
        testPredicateValue(prolog, edge, "attributeCount", edge.getId(), intVal(edge.getAttributeCount()));
    }
}
