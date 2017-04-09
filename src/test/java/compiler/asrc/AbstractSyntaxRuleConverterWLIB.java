package compiler.asrc;

import alice.tuprolog.SolveInfo;
import alice.tuprolog.Term;
import compiler.graphloader.Importer;
import compiler.prolog.TuProlog;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.junit.Test;
import utils.FileUtils;
import utils.GraphUtils;
import utils.Printer;

import java.util.List;
import java.util.Map;

import static compiler.asrc.GraphRuleTests.generateGraphProlog;
import static compiler.asrc.GraphRuleTests.graphTest;
import static compiler.prolog.TuProlog.and;
import static compiler.prolog.TuProlog.struct;
import static compiler.prolog.TuProlog.var;
import static org.junit.Assert.assertEquals;

public final class AbstractSyntaxRuleConverterWLIB {

    @Test
    public void convertToRulesGraph1() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph1.dot"));
        TuProlog prolog = new TuProlog();
        prolog.loadLibrary(new ASRCLibrary(graph));
        graphTest(prolog, graph);
    }

    @Test
    public void convertToRulesGraph2() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph2.dot"));
        TuProlog prolog = new TuProlog();
        prolog.loadLibrary(new ASRCLibrary(graph));
        graphTest(prolog, graph);
    }

    @Test
    public void convertToRulesGraph3() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph3.dot"));
        TuProlog prolog = new TuProlog();
        prolog.loadLibrary(new ASRCLibrary(graph));
        graphTest(prolog, graph);
    }

    @Test
    public void convertToRulesGraph5() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("gxl/test.gxl"));
        TuProlog prolog = new TuProlog();
        prolog.loadLibrary(new ASRCLibrary(graph));
        Printer.pprint(graph);
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
        int length = GraphUtils.getMST(graph).size();
        TuProlog prolog = new TuProlog();
        prolog.loadLibrary(new ASRCLibrary(graph));
        List<Map<String, Term>> a = prolog.solve(and(struct("edge", var("X")),struct("inmst", var("X"))));
        assertEquals(length, a.size());
    }
}
