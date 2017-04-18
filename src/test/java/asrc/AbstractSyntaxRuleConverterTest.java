package asrc;

import alice.tuprolog.Term;
import graphloader.Importer;
import prolog.TuProlog;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.junit.Test;
import utils.FileUtils;

import java.util.List;
import java.util.Map;

import static asrc.GraphRuleTests.generateGraphProlog;
import static asrc.GraphRuleTests.graphTest;
import static prolog.TuProlog.and;
import static prolog.TuProlog.struct;
import static prolog.TuProlog.var;
import static org.junit.Assert.assertEquals;

public final class AbstractSyntaxRuleConverterTest {

    @Test
    public void convertToRulesGraph1() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph1.dot"));
        TuProlog prolog = generateGraphProlog(graph);
        graphTest(prolog, graph);
    }

    @Test
    public void convertToRulesGraph2() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph2.dot"));
        TuProlog prolog = generateGraphProlog(graph);
        graphTest(prolog, graph);
    }

    @Test
    public void convertToRulesGraph3() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph3.dot"));
        TuProlog prolog = generateGraphProlog(graph);
        graphTest(prolog, graph);
    }

    @Test
    public void convertToRulesGraph5() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("gxl/test.gxl"));
        TuProlog prolog = generateGraphProlog(graph);
        graphTest(prolog, graph);
    }


    @Test
    public void TestMST2() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph1.dot"));
        MSTTest(graph);
    }

    @Test
    public void TestMST3() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph2.dot"));
        MSTTest(graph);
    }

    @Test
    public void TestMST4() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph3.dot"));
        MSTTest(graph);
    }

    @Test
    public void TestMST5() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("gxl/test.gxl"));
        MSTTest(graph);
    }


    private void MSTTest(Graph graph) throws Exception {
        Kruskal kruskal = new Kruskal();
        kruskal.init(graph);
        kruskal.compute();
        int length = 0;
        for (Edge ignored : kruskal.getTreeEdges()) {
            length++;
        }
        TuProlog prolog = generateGraphProlog(graph);
        List<Map<String, Term>> a = prolog.solve(and(struct("edge", var("ID")), struct("inmst", var("ID"))));
        assertEquals(length, a.size());
    }
}
