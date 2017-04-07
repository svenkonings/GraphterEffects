package compiler.asrc;

import alice.tuprolog.*;
import compiler.graphloader.Importer;
import compiler.prolog.TuProlog;
import org.graphstream.graph.Graph;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import utils.FileUtils;
import utils.GraphUtils;
import utils.Printer;

import java.io.IOException;

import static compiler.prolog.TuProlog.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LibraryTest {

    TuProlog engine;
    private Graph digraph;
    private Graph undigraph;
    private Graph empty;
    private ASRCLibrary lib;

    @Before
    public void init() throws Exception {
        engine = new TuProlog();
        digraph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph4.dot"));
        undigraph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph5.dot"));
        empty = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/empty.dot"));
        lib = new ASRCLibrary(digraph);
        engine.loadLibrary(lib);
    }

    private void testSuccess(Term term, Graph graph) {
        lib.setGraph(graph);
        SolveInfo res = engine.getProlog().solve(term);
        assertTrue(res.isSuccess());
    }

    private void testFail(Term term, Graph graph) {
        lib.setGraph(graph);
        SolveInfo res = engine.getProlog().solve(term);
        assertFalse(res.isSuccess());
    }



    @Test
    public void Label() throws Exception{
        testSuccess(struct("label", struct("#(a;b)"), var("X")), digraph);
        testSuccess(and(struct("edge", var("X")),struct("label", var("X"), struct("\"bcd\""))), digraph);
        testSuccess(struct("label", struct("#(a;b)"), struct("abc")), digraph);
        testSuccess(and(struct("edge", var("X")), struct("label", var("X"), var("Y"))), digraph); //generation not supported
        testFail(struct("label", struct("#(e;a)"), var("X")), digraph);
        testFail(struct("label", var("X"), struct("weird")), digraph);
        testFail(struct("label", struct("#(a;b)"), struct("bcd")), digraph);
    }

    @Test
    public void Directed() throws Exception {
        testSuccess(struct("directed", struct("#(a;b)")), digraph);
        testFail(struct("undirected", struct("#(a;b)")), digraph);
        testSuccess(struct("directed", struct("#graph4.dot")), digraph);
        testFail(struct("directed", struct("#graph5.dot")), undigraph);
        testSuccess(struct("undirected", struct("#graph5.dot")), undigraph);
        testFail(struct("directed", var("X")), undigraph); //generation not supported
        testFail(struct("directed", var("X")), empty); //generation not supported
    }

    @Test
    public void Mixed() throws Exception {
        testFail(struct("mixed", struct("#empty.dot")), empty);
        testFail(struct("mixed", var("X")), empty);
    }

    @Test
    public void ComponentCount() throws Exception {
        testFail(struct("isconnected", var("X")), digraph); // generation not supported
        testSuccess(struct("isconnected", struct("#graph4.dot")), digraph);
        testFail(struct("isconnected", var("X")), empty); //generation not supported
        testSuccess(struct("isconnected", struct("#empty.dot")), empty);
    }

    @Test
    public void AttributeCount() throws Exception {
        testSuccess(struct("attributecount", struct("#graph5.dot"), intVal(0)), undigraph);
        testSuccess(struct("attributecount", struct("#(e;a)"), intVal(1)), undigraph);
        testSuccess(struct("attributecount", struct("#(e;d)"), intVal(2)), undigraph);
        testSuccess(struct("attributecount", struct("#(e;b)"), intVal(3)), undigraph);
        testFail(struct("attributecount", var("X"), intVal(3)), undigraph); //generation not supported
        testFail(struct("attributecount", var("X"), intVal(4)), undigraph);
        testSuccess(struct("attributecount", struct("#(e;b)"), var("X")), undigraph);
    }

    @Test
    public void flagTest() throws Exception {
        lib.setGraph(digraph);
        SolveInfo res = engine.getProlog().solve(struct("flag_list", var("X")));
        assertTrue(res.isSuccess());
    }

    @Test
    public void ComponentTest() throws Exception {
        lib.setGraph(digraph);
        GraphUtils.ConnectedComponentsCount(digraph);


        SolveInfo res = engine.getProlog().solve(and(struct("node", var("X")), struct("incomponent", var("X"), var("Y"))));
        System.out.println(res);

        assertTrue(res.isSuccess());
    }
}
