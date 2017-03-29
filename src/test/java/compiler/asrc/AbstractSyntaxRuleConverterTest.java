package compiler.asrc;

import compiler.graphloader.Importer;
import exceptions.UnknownGraphTypeException;
import org.graphstream.graph.Graph;
import org.junit.Test;
import org.xml.sax.SAXException;
import utils.FileUtils;
import utils.Printer;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Jatalog;

import java.io.IOException;

import static compiler.asrc.GraphRuleTests.generateGraphJatalog;
import static compiler.asrc.GraphRuleTests.graphTest;

public final class AbstractSyntaxRuleConverterTest {

    @Test
    public void convertToRulesGraph1() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph1.dot"));
        Jatalog jatalog = generateGraphJatalog(graph);
        graphTest(jatalog, graph);
    }

    @Test
    public void convertToRulesGraph2() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph2.dot"));
        Jatalog jatalog = generateGraphJatalog(graph);
        graphTest(jatalog,graph);
    }

    @Test
    public void convertToRulesGraph3() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph3.dot"));
        Jatalog jatalog = generateGraphJatalog(graph);
        graphTest(jatalog, graph);
    }

    @Test
    public void convertToRulesGraph4() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("gxl/test.gxl"));
        Jatalog jatalog = generateGraphJatalog(graph);
        graphTest(jatalog, graph);
    }

}