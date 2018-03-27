package com.github.meteoorkip.asc;

import alice.tuprolog.Term;
import com.github.meteoorkip.graphloader.Importer;
import com.github.meteoorkip.prolog.TuProlog;
import com.github.meteoorkip.utils.FileUtils;
import com.github.meteoorkip.utils.GraphUtils;
import com.github.meteoorkip.utils.Printer;
import org.graphstream.graph.Graph;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.github.meteoorkip.asc.GraphRuleTests.graphTest;
import static com.github.meteoorkip.prolog.TuProlog.*;
import static org.junit.Assert.assertEquals;

public final class AbstractSyntaxConverterWLIB {

    @Test
    public void convertToRulesGraph1() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/simple_graphs/graph1.dot"));
        TuProlog prolog = new TuProlog();
        ASCLibrary library = new ASCLibrary(graph);
        prolog.loadLibrary(library);
        graphTest(prolog, graph);
    }

    @Test
    public void convertToRulesGraph2() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/simple_graphs/graph2.dot"));
        TuProlog prolog = new TuProlog();
        ASCLibrary library = new ASCLibrary(graph);
        prolog.loadLibrary(library);
        graphTest(prolog, graph);
    }

    @Test
    public void convertToRulesGraph3() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/simple_graphs/graph3.dot"));
        TuProlog prolog = new TuProlog();
        ASCLibrary library = new ASCLibrary(graph);
        prolog.loadLibrary(library);
        graphTest(prolog, graph);
    }

    @Test
    public void convertToRulesGraph5() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("library/strange_graphs/gxl/test.gxl"));
        TuProlog prolog = new TuProlog();
        ASCLibrary library = new ASCLibrary(graph);
        prolog.loadLibrary(library);
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

    private void MSTTest(Graph graph) throws Exception {
        int length = GraphUtils.getMST(graph).size();
        TuProlog prolog = new TuProlog();
        ASCLibrary library = new ASCLibrary(graph);
        prolog.loadLibrary(library);
        List<Map<String, Term>> a = prolog.solve(and(struct("edge", var("X")), struct("inMST", var("X"))));
        assertEquals(length, a.size());
    }
}
