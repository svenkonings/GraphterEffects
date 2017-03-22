package asrc;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import org.junit.Test;
import utils.TestUtils;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Expr;
import za.co.wstoop.jatalog.Jatalog;
import za.co.wstoop.jatalog.Rule;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static utils.TestUtils.*;

/**
 * Created by user on 21-3-2017.
 */
public class AbstractSyntaxRuleConverterTest {

    @Test
    public void convertToRulesGraph1() throws Exception {
        DefaultGraph graph = importDGraph("resources/asrc_testgraphs/graph1.dot");
        Jatalog jatalog = generateGraphJatalog(graph);
        graphTest(jatalog, graph);
    }

    @Test
    public void convertToRulesGraph2() throws Exception {
        DefaultGraph graph = importDGraph("resources/asrc_testgraphs/graph2.dot");
        Jatalog jatalog = generateGraphJatalog(graph);
        graphTest(jatalog,graph);
    }

    @Test
    public void convertToRulesGraph3() throws Exception {
        DefaultGraph graph = importDGraph("resources/asrc_testgraphs/graph3.dot");
        Jatalog jatalog = generateGraphJatalog(graph);
        graphTest(jatalog, graph);
    }

    private Jatalog generateGraphJatalog(Graph graph) throws DatalogException {
        List<Rule> rulesList = AbstractSyntaxRuleConverter.convertToRules(graph);
        Jatalog jatalog = new Jatalog();
        addRules(jatalog,rulesList);

        return jatalog;
    }

    private void graphTest(Jatalog jatalog, Graph graph) throws DatalogException {
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

    private void structureTest(Jatalog jatalog, Graph graph) throws DatalogException {
        Collection<Map<String, String>> answers;

        answers = jatalog.query(Expr.expr("node","ID"));
        assert answers.size() == graph.getNodeSet().size();
        Collection<Map<String, String>> finalAnswers = answers;
        graph.getNodeSet().stream().forEach(node -> {
            try {
                TestUtils.answerContains(finalAnswers, "ID", node.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        answers = jatalog.query(Expr.expr("edge","Target","Source","ID"));
        assert answers.size() == graph.getEdgeSet().size();
        Collection<Map<String, String>> finalAnswers1 = answers;
        graph.getEdgeSet().stream().forEach(edge -> {
            try {
                TestUtils.answerContains(finalAnswers1, "Target", edge.getTargetNode().getId(),
                        "Source", edge.getSourceNode().getId(), "ID", edge.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void graphPropertiesTest(Jatalog jatalog, Graph graph) throws DatalogException {
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

    private void graphIntegerInvariantsTest(Jatalog jatalog, Graph graph) throws DatalogException {
        testPredicateValue(jatalog,graph,"nodecount",graph.getId(),String.valueOf(graph.getNodeSet().size()));
        testPredicateValue(jatalog,graph,"edgecount",graph.getId(),String.valueOf(graph.getEdgeSet().size()));
        testPredicateValue(jatalog,graph,"attributecount",graph.getId(),String.valueOf(graph.getAttributeKeySet().size()));
    }

    private void nodePropertiesTest(Jatalog jatalog, Node node) throws DatalogException {
        testAttributesCorrect(jatalog,node);
    }

    private void nodeIntegerInvariantsTest(Jatalog jatalog, Node node) throws DatalogException {
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

    private void edgePropertiesTest(Jatalog jatalog, Edge edge) throws DatalogException {
        testSimpleFact(jatalog,edge,"directed",edge.getId(),edge.isDirected());
        testSimpleFact(jatalog,edge,"undirected",edge.getId(),!edge.isDirected());
        testAttributesCorrect(jatalog,edge);
    }

    private void edgeIntegerInvariantsTest(Jatalog jatalog, Edge edge) throws DatalogException {
        testPredicateValue(jatalog,edge,"attributecount",edge.getId(),String.valueOf(edge.getAttributeCount()));
    }

    public DefaultGraph importDGraph(String filepath) throws IOException {
        String name = filepath.replace("\"","");
        name = name.split("/")[name.split("/").length-1];
        DefaultGraph g = new DefaultGraph(name);
        FileSource fs = FileSourceFactory.sourceFor(filepath);
        fs.addSink(g);
        fs.readAll(filepath);
        fs.removeSink(g);
        return g;
    }

    public SingleGraph importSGraph(String filepath) throws IOException {
        SingleGraph g = new SingleGraph("g");
        FileSource fs = FileSourceFactory.sourceFor(filepath);
        fs.addSink(g);
        fs.readAll(filepath);
        fs.removeSink(g);
        return g;
    }

    public MultiGraph importMGraph(String filepath) throws IOException {
        MultiGraph g = new MultiGraph("g");
        FileSource fs = FileSourceFactory.sourceFor(filepath);
        fs.addSink(g);
        fs.readAll(filepath);
        fs.removeSink(g);
        return g;
    }

    private void addRules(Jatalog jatalog, List<Rule> rules) {
        rules.forEach(rule -> {
            try {
                if (rule.getBody().size() == 0){
                    jatalog.fact(rule.getHead());
                } else {
                    jatalog.rule(rule);
                }
            } catch (DatalogException e) {
                System.out.println(rule.toString());
                e.printStackTrace();
            }
        });
    }
}