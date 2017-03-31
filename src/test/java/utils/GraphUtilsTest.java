package utils;

import compiler.graphloader.Importer;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public final class GraphUtilsTest {

    @Test
    public void testChangeIDs() throws IOException, SAXException {
        Graph gfirst = Importer.graphFromFile(FileUtils.fromResources("dgs/graph1.dgs"));
        Graph gsecond = GraphUtils.changeIDs(gfirst);

        //attributes unchanged
        assertTrue(sameAttributes(gfirst, gsecond));
        for (Node n : gfirst.getEachNode()) {
            assertTrue(gsecond.getNode("_" + n.getId()) != null);
            assertTrue(sameAttributes(n, gsecond.getNode("_" + n.getId())));
        }
    }

    @Test
    public void testChangeIDs2() throws IOException, SAXException {
        Graph gfirst = Importer.graphFromFile(FileUtils.fromResources("gxl/test.gxl"));
        Graph gsecond = GraphUtils.changeIDs(gfirst);

        //attributes unchanged
        assertTrue(sameAttributes(gfirst, gsecond));
        for (Node n : gfirst.getEachNode()) {
            assertTrue(gsecond.getNode("_" + n.getId()) != null);
            assertTrue(sameAttributes(n, gsecond.getNode("_" + n.getId())));
        }
    }


    private boolean sameAttributes(Element e1, Element e2) {
        for (String key : e1.getAttributeKeySet()) {
            if (!e2.hasAttribute(key)) {
                return false;
            }
            if (!StringUtils.ObjectToString(e1.getAttribute(key)).equals(StringUtils.ObjectToString(e2.getAttribute(key)))) {
                return false;
            }
        }
        for (String key : e2.getAttributeKeySet()) {
            if (!e1.hasAttribute(key)) {
                return false;
            }
            if (!StringUtils.ObjectToString(e1.getAttribute(key)).equals(StringUtils.ObjectToString(e2.getAttribute(key)))) {
                return false;
            }
        }
        return true;
    }
}
