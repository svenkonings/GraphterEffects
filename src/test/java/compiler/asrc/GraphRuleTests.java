package compiler.asrc;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import utils.TestUtils;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Expr;
import za.co.wstoop.jatalog.Jatalog;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static utils.TestUtils.*;

public class GraphRuleTests {

    public static Jatalog generateGraphJatalog(Graph graph) throws DatalogException {
        List<Expr> exprList = AbstractSyntaxRuleConverter.convertToRules(graph);
        Jatalog jatalog = new Jatalog();
        addRules(jatalog,exprList);

        return jatalog;
    }

    public static void graphTest(Jatalog jatalog, Graph graph) throws DatalogException {
        structureTest(jatalog,graph);
        graphPropertiesTest(jatalog,graph);
        graphIntegerInvariantsTest(jatalog,graph);

        for (Node node: graph.getNodeSet()){
            nodePropertiesTest(jatalog,node);
            nodeIntegerInvariantsTest(jatalog,node);
        }

        for (Edge edge: graph.getEdgeSet()){
            edgePropertiesTest(jatalog, edge);
            edgeIntegerInvariantsTest(jatalog,edge);
        }
    }

    public static void structureTest(Jatalog jatalog, Graph graph) throws DatalogException {
        Collection<Map<String, String>> answers;

        answers = jatalog.query(Expr.expr("node","ID"));
        assert answers.size() == graph.getNodeSet().size();
        Collection<Map<String, String>> finalAnswers = answers;
        graph.getNodeSet().forEach(node -> {
            try {
                TestUtils.answerContains(finalAnswers, "ID", node.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        answers = jatalog.query(Expr.expr("edge","Target","Source","ID"));
        assert answers.size() == graph.getEdgeSet().size();
        Collection<Map<String, String>> finalAnswers1 = answers;
        graph.getEdgeSet().forEach(edge -> {
            try {
                TestUtils.answerContains(finalAnswers1, "Target", edge.getTargetNode().getId(),
                        "Source", edge.getSourceNode().getId(), "ID", edge.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void graphPropertiesTest(Jatalog jatalog, Graph graph) throws DatalogException {
        boolean fullydirected = true;
        boolean fullyundirected = true;
        for (Edge edge: graph.getEdgeSet()) {
            fullydirected = fullydirected && edge.isDirected();
            fullyundirected = fullyundirected && !edge.isDirected();
        }

        testSimpleFact(jatalog,graph,"directed",graph.getId(),fullydirected);
        testSimpleFact(jatalog,graph,"undirected",graph.getId(),fullyundirected);
        testSimpleFact(jatalog,graph,"mixed",graph.getId(),!(fullydirected||fullyundirected));

        testSimpleFact(jatalog,graph,"singlegraph",graph.getId(),graph instanceof SingleGraph);
        testSimpleFact(jatalog,graph,"multigraph",graph.getId(),graph instanceof MultiGraph);

        testAttributesCorrect(jatalog,graph);
    }

    public static void graphIntegerInvariantsTest(Jatalog jatalog, Graph graph) throws DatalogException {
        testPredicateValue(jatalog,graph,"nodecount",graph.getId(),String.valueOf(graph.getNodeSet().size()));
        testPredicateValue(jatalog,graph,"edgecount",graph.getId(),String.valueOf(graph.getEdgeSet().size()));
        testPredicateValue(jatalog,graph,"attributecount",graph.getId(),String.valueOf(graph.getAttributeKeySet().size()));
    }

    public static void nodePropertiesTest(Jatalog jatalog, Node node) throws DatalogException {
        testAttributesCorrect(jatalog,node);
    }

    public static void nodeIntegerInvariantsTest(Jatalog jatalog, Node node) throws DatalogException {
        testPredicateValue(jatalog,node,"degree",node.getId(),String.valueOf(node.getDegree()));
        testPredicateValue(jatalog,node,"indegree",node.getId(),String.valueOf(node.getInDegree()));
        testPredicateValue(jatalog,node,"outdegree",node.getId(),String.valueOf(node.getOutDegree()));
        testPredicateValue(jatalog,node,"attributecount",node.getId(),String.valueOf(node.getAttributeCount()));

        int neighbourcount = 0;
        Iterator it = node.getNeighborNodeIterator();
        while (it.hasNext()){
            neighbourcount++;
            it.next();
        }
        testPredicateValue(jatalog,node,"neighbourcount",node.getId(),String.valueOf(neighbourcount));
    }

    public static void edgePropertiesTest(Jatalog jatalog, Edge edge) throws DatalogException {
        testSimpleFact(jatalog,edge,"directed",edge.getId(),edge.isDirected());
        testSimpleFact(jatalog,edge,"undirected",edge.getId(),!edge.isDirected());
        testAttributesCorrect(jatalog,edge);
    }

    public static void edgeIntegerInvariantsTest(Jatalog jatalog, Edge edge) throws DatalogException {
        testPredicateValue(jatalog,edge,"attributecount",edge.getId(),String.valueOf(edge.getAttributeCount()));
    }


    public static void addRules(Jatalog jatalog, List<Expr> facts) {
        facts.forEach(fact -> {
            try {
                jatalog.fact(fact);
            } catch (DatalogException e) {
                e.printStackTrace();
            }
        });
    }
}
