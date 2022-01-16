package com.github.meteoorkip.utils;

import com.github.meteoorkip.graphloader.Importer;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public final class GraphUtilsTest {

    @Test
    public void testChangeIDs() throws IOException, SAXException {
        Graph gfirst = Importer.graphFromFile(FileUtils.fromResources("library/strange_graphs/dgs/graph1.dgs"));
        Graph gsecond = GraphUtils.changeIDs(gfirst);

        //attributes unchanged
        assertTrue(sameAttributes(gfirst, gsecond));
        gfirst.nodes().forEach(n -> {
            assertNotNull(gsecond.getNode(GraphUtils.ILLEGAL_PREFIX + n.getId()));
            assertTrue(sameAttributes(n, gsecond.getNode(GraphUtils.ILLEGAL_PREFIX + n.getId())));
        });
    }

    @Test
    public void testChangeIDs2() throws IOException, SAXException {
        Graph gfirst = Importer.graphFromFile(FileUtils.fromResources("library/strange_graphs/gxl/test.gxl"));
        Graph gsecond = GraphUtils.changeIDs(gfirst);

        //attributes unchanged
        assertTrue(sameAttributes(gfirst, gsecond));
        gfirst.nodes().forEach(n -> {
            assertNotNull(gsecond.getNode(GraphUtils.ILLEGAL_PREFIX + n.getId()));
            assertTrue(sameAttributes(n, gsecond.getNode(GraphUtils.ILLEGAL_PREFIX + n.getId())));
        });
    }


    private boolean sameAttributes(Element e1, Element e2) {
        for (String key : e1.attributeKeys().collect(Collectors.toSet())) {
            if (!e2.hasAttribute(key)) {
                return false;
            }
            if (!StringUtils.ObjectToString(e1.getAttribute(key)).equals(StringUtils.ObjectToString(e2.getAttribute(key)))) {
                return false;
            }
        }
        for (String key : e2.attributeKeys().collect(Collectors.toSet())) {
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
