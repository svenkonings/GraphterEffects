package compiler.asrc;

import compiler.graphloader.Importer;
import org.graphstream.graph.Graph;
import org.junit.Test;
import utils.FileUtils;
import za.co.wstoop.jatalog.Jatalog;

import static compiler.asrc.GraphRuleTests.generateGraphJatalog;
import static compiler.asrc.GraphRuleTests.graphTest;

public final class ASRCIntergrationTest {

    @Test
    public void intergrationTest1() throws Exception {
        Graph graph = Importer.fromFile(FileUtils.fromResources("asrc_testgraphs/graph1.dot"));



        Jatalog jatalog = generateGraphJatalog(graph);
        graphTest(jatalog, graph);
    }



}