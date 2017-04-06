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
        testSuccess(struct("label", var("X"), struct("bcd")), digraph);
        testSuccess(struct("label", struct("#(a;b)"), struct("abc")), digraph);
        testSuccess(struct("label", var("X"), var("Y")), digraph);
        testFail(struct("label", struct("#(e;a)"), var("X")), digraph);
        testFail(struct("label", var("X"), struct("weird")), digraph);
        testFail(struct("label", struct("#(a;b)"), struct("bcd")), digraph);
    }

    @Test
    public void Directed() throws Exception {
        testSuccess(struct("directed", struct("#(a;b)")), digraph);
        testSuccess(struct("directed", var("X")), digraph);
        testFail(struct("directed", var("X")), undigraph);
        testSuccess(struct("directed", var("X")), empty);
    }

    @Test
    public void Mixed() throws Exception {
        testFail(struct("mixed", struct("#empty.dot")), empty);
        testFail(struct("mixed", var("X")), empty);
    }

    @Test
    public void ComponentCount() throws Exception {
        testSuccess(struct("isconnected", var("X")), digraph);
        testSuccess(struct("isconnected", struct("#graph4.dot")), digraph);
        testSuccess(struct("isconnected", var("X")), empty);
        testSuccess(struct("isconnected", struct("#empty.dot")), empty);
    }

    @Test
    public void AttributeCount() throws Exception {
        testSuccess(struct("attributecount", struct("#graph5.dot"), intVal(0)), undigraph);
        testSuccess(struct("attributecount", struct("#(e;a)"), intVal(1)), undigraph);
        testSuccess(struct("attributecount", struct("#(e;d)"), intVal(2)), undigraph);
        testSuccess(struct("attributecount", struct("#(e;b)"), intVal(3)), undigraph);
        testSuccess(struct("attributecount", var("X"), intVal(3)), undigraph);
        testFail(struct("attributecount", var("X"), intVal(4)), undigraph);
        testSuccess(struct("attributecount", struct("#(e;b)"), var("X")), undigraph);
    }

    @Test
    public void scaryTest() throws Exception {
        engine = new TuProlog();
        lib = new ASRCLibrary(digraph);
        //engine.addTheory(clause(struct("yes", var("X")), struct("scary", var("X"))));
        engine.addTheory(clause(struct("yes", var("X")), and(struct("scary", var("X")), struct("scarysecond", var("X")))));
        engine.loadLibrary(lib);


        SolveInfo res = engine.getProlog().solve(struct("yes", var("X")));
        System.out.println(res);
        res = engine.getProlog().solve(and(struct("yes", var("X"))));
        System.out.println(res);
    }


}
