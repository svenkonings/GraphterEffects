package compiler.asrc;

import compiler.graphloader.Importer;
import org.graphstream.graph.Graph;
import org.junit.Test;
import utils.FileUtils;
import za.co.wstoop.jatalog.Jatalog;

import static compiler.asrc.GraphRuleTests.generateGraphJatalog;
import static compiler.asrc.GraphRuleTests.graphTest;

public final class AbstractSyntaxRuleConverterTest {

    @Test
    public void convertToRulesGraph1() throws Exception {
        Graph graph = Importer.fromFile(FileUtils.fromResources("asrc_testgraphs/graph1.dot"));
        Jatalog jatalog = generateGraphJatalog(graph);
        graphTest(jatalog, graph);
    }

    @Test
    public void convertToRulesGraph2() throws Exception {
        Graph graph = Importer.fromFile(FileUtils.fromResources("asrc_testgraphs/graph2.dot"));
        Jatalog jatalog = generateGraphJatalog(graph);
        graphTest(jatalog,graph);
    }

    @Test
    public void convertToRulesGraph3() throws Exception {
        Graph graph = Importer.fromFile(FileUtils.fromResources("asrc_testgraphs/graph3.dot"));
        Jatalog jatalog = generateGraphJatalog(graph);
        graphTest(jatalog, graph);
    }

}