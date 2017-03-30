package compiler.asrc;

import compiler.graphloader.Importer;
import compiler.prolog.TuProlog;
import org.graphstream.graph.Graph;
import org.junit.Test;
import utils.FileUtils;

import static compiler.asrc.GraphRuleTests.generateGraphProlog;
import static compiler.asrc.GraphRuleTests.graphTest;

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
    public void convertToRulesGraph4() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("gxl/test.gxl"));
        TuProlog prolog = generateGraphProlog(graph);
        graphTest(prolog, graph);
    }
}
