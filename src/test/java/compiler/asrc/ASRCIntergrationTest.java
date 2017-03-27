package compiler.asrc;

import compiler.graphloader.Importer;
import org.graphstream.graph.Graph;
import org.junit.Test;
import utils.FileUtils;
import utils.GraphUtils;
import utils.Printer;
import za.co.wstoop.jatalog.Jatalog;

import static compiler.asrc.GraphRuleTests.generateGraphJatalog;
import static compiler.asrc.GraphRuleTests.graphTest;

public final class ASRCIntergrationTest {

    @Test
    public void intergrationTestDOT() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph3.dot"));
        Printer.pprint(graph);

        Jatalog jatalog = generateGraphJatalog(graph);
        graphTest(jatalog, graph);
    }

    @Test
    public void intergrationTestGXL() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("gxl/test.gxl"));
        Printer.pprint(graph);

        Jatalog jatalog = generateGraphJatalog(graph);
        graphTest(jatalog, graph);
    }

    @Test
    public void intergrationTestGST() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("gxl/tictactoe.gst"));
        Printer.pprint(graph);

        Jatalog jatalog = generateGraphJatalog(graph);
        graphTest(jatalog, graph);
    }

    @Test
    public void intergrationTestGPR() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("groovegraphs/tictactoe.gps/move.gpr"));
        Printer.pprint(graph);

        Jatalog jatalog = generateGraphJatalog(graph);
        graphTest(jatalog, graph);
    }

    @Test
    public void intergrationTestDGS() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("dgs/graph1.dgs"));
        //Printer.pprint(graph);

        Jatalog jatalog = generateGraphJatalog(graph);
        graphTest(jatalog, graph);
    }



}