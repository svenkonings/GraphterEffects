package com.github.meteoorkip.asc.library;

import com.github.meteoorkip.asc.ASCLibrary;
import com.github.meteoorkip.graphloader.Importer;
import com.github.meteoorkip.prolog.TuProlog;
import com.github.meteoorkip.utils.FileUtils;
import com.github.meteoorkip.utils.GraphUtils;
import it.unibo.tuprolog.core.Struct;
import it.unibo.tuprolog.core.Term;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.meteoorkip.prolog.TuProlog.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LibraryFunctionalityTest {

    TuProlog diengine;
    TuProlog undiengine;
    TuProlog emptyengine;

    private Graph digraph;
    private Graph undigraph;
    private Graph empty;

    final Map<Graph, TuProlog> asrcmap = new HashMap<>();

    @BeforeEach
    public void init() throws Exception {
        diengine = new TuProlog();
        digraph = Importer.graphFromFile(FileUtils.fromResources("library/simple_graphs/graph4.dot"));
        diengine.loadLibrary(new ASCLibrary(digraph));
        asrcmap.put(digraph, diengine);

        undiengine = new TuProlog();
        undigraph = Importer.graphFromFile(FileUtils.fromResources("library/simple_graphs/graph5.dot"));
        undiengine.loadLibrary(new ASCLibrary(undigraph));
        asrcmap.put(undigraph, undiengine);

        emptyengine = new TuProlog();
        empty = Importer.graphFromFile(FileUtils.fromResources("library/simple_graphs/empty.dot"));
        emptyengine.loadLibrary(new ASCLibrary(empty));
        asrcmap.put(empty, emptyengine);
    }

    private void testSuccess(Struct struct, Graph graph) {
        List<Map<String, Term>> res = asrcmap.get(graph).solve(struct);
        assertFalse(res.isEmpty());
        //System.out.println(term.toString() + "\n-->\n" + res.toString().replaceAll("\n", "\t\t") + "\n");
    }

    private void testFail(Struct struct, Graph graph) {
        List<Map<String, Term>> res = asrcmap.get(graph).solve(struct);
        assertTrue(res.isEmpty(), () -> "Found a solution anyway: " + res.toString().replaceAll("\n", "\t "));
    }


    @Test
    public void Label() {
        testSuccess(struct("edge", struct("#(a;b)")), digraph);
        testSuccess(struct("attribute", struct("#(a;b)"), struct("label"), var("X")), digraph);
        testSuccess(struct("label", struct("#(a;c)"), var("X")), digraph);


        testSuccess(struct("label", var("X"), struct("bcd")), digraph);
        testSuccess(struct("label", struct("#(a;b)"), struct("abc")), digraph);
        testSuccess(struct("label", struct("#(a;b)"), var("X")), digraph);

        testSuccess(struct("label", var("X"), var("Y")), digraph); //generation not supported
        testFail(struct("label", struct("#(e;a)"), var("X")), digraph);
        testFail(struct("label", var("X"), struct("weird")), digraph);
        testFail(struct("label", struct("#(a;b)"), struct("bcd")), digraph);
    }

    @Test
    public void Directed() {
        testSuccess(struct("directed", struct("#(a;b)")), digraph);
        testFail(struct("undirected", struct("#(a;b)")), digraph);
        testSuccess(struct("directed", struct("#graph4.dot")), digraph);
        testFail(struct("directed", struct("#graph5.dot")), undigraph);
        testSuccess(struct("graph", var("X")), undigraph);
        testSuccess(struct("undirected", struct("#graph5.dot")), undigraph);
        testFail(struct("directed", var("X")), undigraph);
        testSuccess(struct("directed", var("X")), empty);
    }

    @Test
    public void Mixed() {
        testSuccess(struct("mixed", struct("#empty.dot")), empty);
        testSuccess(struct("mixed", var("X")), empty);
    }

    @Test
    public void ComponentCount() {
        testSuccess(struct("isConnected", var("X")), digraph); // generation not supported
        testSuccess(struct("isConnected", struct("#graph4.dot")), digraph);
        testSuccess(struct("isConnected", var("X")), empty); //generation not supported
        testSuccess(struct("isConnected", struct("#empty.dot")), empty);
    }

    @Test
    public void AttributeCount() {
        testSuccess(struct("attributeCount", struct("#graph5.dot"), intVal(0)), undigraph);
        testSuccess(struct("attributeCount", struct("#(e;a)"), intVal(1)), undigraph);
        testSuccess(struct("attributeCount", struct("#(e;d)"), intVal(2)), undigraph);
        testSuccess(struct("attributeCount", struct("#(e;b)"), intVal(3)), undigraph);
        testSuccess(struct("attributeCount", var("X"), intVal(3)), undigraph); //generation not supported
        testFail(struct("attributeCount", var("X"), intVal(4)), undigraph);
        testSuccess(struct("attributeCount", struct("#(e;b)"), var("X")), undigraph);
    }

    @Test
    public void flagTest() {
        List<Map<String, Term>> res = asrcmap.get(digraph).solve(struct("current_prolog_flag", var("X"), var("Y")));
        assertFalse(res.isEmpty());
    }

    @Test
    public void ComponentTest() {
        GraphUtils.ConnectedComponentsCount(digraph);
        List<Map<String, Term>> res = asrcmap.get(digraph).solve(and(struct("node", var("X")), struct("inComponent", var("X"), var("Y"))));
        assertFalse(res.isEmpty());
    }
}
