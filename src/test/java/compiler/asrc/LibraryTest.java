package compiler.asrc;

import alice.tuprolog.*;
import compiler.graphloader.Importer;
import compiler.prolog.TuProlog;
import org.graphstream.graph.Graph;
import org.junit.Test;
import org.xml.sax.SAXException;
import utils.FileUtils;
import utils.Printer;

import java.io.IOException;

import static compiler.prolog.TuProlog.*;
import static org.junit.Assert.assertTrue;

public class LibraryTest {

    @Test
    public void getLabel() throws Exception{
        TuProlog engine = new TuProlog();
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph4.dot"));
        ASRCLibrary lib = new ASRCLibrary(graph);
        engine.loadLibrary(lib);
        SolveInfo res = engine.getProlog().solve(struct("label", struct("#(a;b)"), var("X")));
        System.out.println(res);
        assertTrue(res.toString().contains("yes"));
    }

    @Test
    public void getID() throws Exception{
        TuProlog engine = new TuProlog();
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph4.dot"));
        ASRCLibrary lib = new ASRCLibrary(graph);
        engine.loadLibrary(lib);
        SolveInfo res = engine.getProlog().solve(struct("label", var("X"), struct("abc")));
        System.out.println(res);
        assertTrue(res.toString().contains("yes"));
    }

    @Test
    public void check() throws Exception{
        TuProlog engine = new TuProlog();
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph4.dot"));
        ASRCLibrary lib = new ASRCLibrary(graph);
        engine.loadLibrary(lib);
        SolveInfo res = engine.getProlog().solve(struct("label", struct("#(a;b)"), struct("abc")));
        System.out.println(res);
        assertTrue(res.toString().contains("yes"));
    }

    @Test
    public void generate() throws Exception {
        TuProlog engine = new TuProlog();
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph4.dot"));
        ASRCLibrary lib = new ASRCLibrary(graph);
        engine.loadLibrary(lib);
        SolveInfo res = engine.getProlog().solve(struct("label", var("X"), var("Y")));
        System.out.println(res);
        assertTrue(res.toString().contains("yes"));
    }

//    @Test
//    public void testLibrary() throws Exception {
//        TuProlog engine = new TuProlog();
//        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph4.dot"));
//        ASRCLibrary lib = new ASRCLibrary(graph);
//        engine.loadLibrary(lib);
//        SolveInfo res2 = engine.getProlog().solve(struct("label", var("X"), term("0.4")));
//        System.out.println(res2);
//
//        System.out.println();
//
//        SolveInfo res1 = engine.getProlog().solve(struct("label", term("#(a;b)"), var("X")));
//        System.out.println(res1);
//    }

}
