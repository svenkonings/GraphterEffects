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

public class LibraryTest {

    @Test
    public void testLibrary() throws InvalidLibraryException, InvalidTheoryException, MalformedGoalException, IOException, SAXException {
        TuProlog engine = new TuProlog();
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph3.dot"));
        Printer.pprint(graph);
        ASRCLibrary lib = new ASRCLibrary(graph);
        engine.loadLibrary(lib);
        //SolveInfo res = engine.getProlog().solve(struct("label", term("#(a;b)"), var("Hey")));
        SolveInfo res = engine.getProlog().solve(struct("label", var("ID"), term("0.4")));
        System.out.println(res);

    }
}
