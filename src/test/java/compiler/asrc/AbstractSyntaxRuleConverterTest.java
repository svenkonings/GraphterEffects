package compiler.asrc;

import alice.tuprolog.Term;
import compiler.graphloader.Importer;
import compiler.prolog.TuProlog;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.junit.Test;
import utils.FileUtils;

import java.util.List;
import java.util.Map;

import static compiler.asrc.GraphRuleTests.generateGraphProlog;
import static compiler.asrc.GraphRuleTests.graphTest;
import static compiler.prolog.TuProlog.struct;
import static compiler.prolog.TuProlog.var;
import static org.junit.Assert.assertEquals;

public final class AbstractSyntaxRuleConverterTest {

    @Test
    public void convertToRulesGraph1() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph1.dot"));
        TuProlog prolog = generateGraphProlog(graph, false);
        graphTest(prolog, graph);
    }

    @Test
    public void convertToRulesGraph2() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph2.dot"));
        TuProlog prolog = generateGraphProlog(graph, false);
        graphTest(prolog, graph);
    }

    @Test
    public void convertToRulesGraph3() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph3.dot"));
        TuProlog prolog = generateGraphProlog(graph, false);
        graphTest(prolog, graph);
    }
//TODO
//    @Test
//    public void convertToRulesGraph4() throws Exception {
//        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/multi1.dot"));
//        TuProlog prolog = generateGraphProlog(graph);
//        graphTest(prolog, graph);
//    }

    @Test
    public void convertToRulesGraph5() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("gxl/test.gxl"));
        TuProlog prolog = generateGraphProlog(graph, false);
        graphTest(prolog, graph);
    }

//    @Test
//    public void TestMST1() throws Exception {
//        Graph graph = Importer.graphFromFile(FileUtils.fromResources("demo/demo1.dot"));
//        MSTTest(graph, false);
//    }

    @Test
    public void TestMST2() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph1.dot"));
        MSTTest(graph, false);
    }

    @Test
    public void TestMST3() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph2.dot"));
        MSTTest(graph, false);
    }

    @Test
    public void TestMST4() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph3.dot"));
        MSTTest(graph, false);
    }

    @Test
    public void TestMST5() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("gxl/test.gxl"));
        MSTTest(graph, false);
    }

    //TODO
//    @Test
//    public void TestMST6() throws Exception {
//        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/multi1.dot"));
//        MSTTest(graph);
//    }


    private void MSTTest(Graph graph, boolean GROOVEMode) throws Exception {
        Kruskal kruskal = new Kruskal();
        kruskal.init(graph);
        kruskal.compute();
        int length = 0;
        for (Edge ignored : kruskal.getTreeEdges()) {
            length++;
        }
        TuProlog prolog = generateGraphProlog(graph, GROOVEMode);
        List<Map<String, Term>> a = prolog.solve(struct("inmst", var("ID")));
        assertEquals(length, a.size());
    }
}
