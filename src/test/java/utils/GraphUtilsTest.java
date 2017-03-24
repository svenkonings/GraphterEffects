package utils;

import compiler.graphloader.Importer;
import org.graphstream.graph.Graph;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by poesd_000 (who is by the way better than you) on 24/03/2017.
 */
public final class GraphUtilsTest {

    @Test
    public void testChangeIDs() throws IOException, SAXException {
        Graph g = Importer.graphFromFile(FileUtils.fromResources("dgs/graph1.dgs"));
        Printer.pprint(g);
        g = GraphUtils.changeIDs(g);
        Printer.pprint(g);
    }
}
