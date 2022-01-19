package com.github.meteoorkip.asc;

import com.github.meteoorkip.graphloader.Importer;
import com.github.meteoorkip.prolog.TuProlog;
import com.github.meteoorkip.utils.FileUtils;
import it.unibo.tuprolog.core.Term;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.github.meteoorkip.asc.GraphRuleTests.generateGraphProlog;
import static com.github.meteoorkip.asc.GraphRuleTests.graphTest;
import static com.github.meteoorkip.prolog.TuProlog.struct;
import static com.github.meteoorkip.prolog.TuProlog.var;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class AbstractSyntaxConverterTest {

    @Test
    public void convertToRulesGraph1() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/simple_graphs/graph1.dot"));
        TuProlog prolog = generateGraphProlog(graph);
        graphTest(prolog, graph);
    }

    @Test
    public void convertToRulesGraph2() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/simple_graphs/graph2.dot"));
        TuProlog prolog = generateGraphProlog(graph);
        graphTest(prolog, graph);
    }

    @Test
    public void convertToRulesGraph3() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/simple_graphs/graph3.dot"));
        TuProlog prolog = generateGraphProlog(graph);
        graphTest(prolog, graph);
    }

    @Test
    public void convertToRulesGraph5() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/strange_graphs/gxl/test.gxl"));
        TuProlog prolog = generateGraphProlog(graph);
        graphTest(prolog, graph);
    }


    @Test
    public void TestMST2() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/simple_graphs/graph1.dot"));
        MSTTest(graph);
    }

    @Test
    public void TestMST3() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/simple_graphs/graph2.dot"));
        MSTTest(graph);
    }

    @Test
    public void TestMST4() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/simple_graphs/graph3.dot"));
        MSTTest(graph);
    }

    @Test
    public void TestMST5() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/strange_graphs/gxl/test.gxl"));
        MSTTest(graph);
    }


    private void MSTTest(Graph graph) {
        Kruskal kruskal = new Kruskal();
        kruskal.init(graph);
        kruskal.compute();
        int length = 0;
        for (Edge ignored : kruskal.getTreeEdges()) {
            length++;
        }
        TuProlog prolog = generateGraphProlog(graph);
        List<Map<String, Term>> a = prolog.solve(struct("edge", var("ID")), struct("inMST", var("ID")));
        assertEquals(length, a.size());
    }
}
