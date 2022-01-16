package com.github.meteoorkip.graphloader;

import com.github.meteoorkip.utils.FileUtils;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public final class GXLImporterTest {

    @Test
    public void GPLWithoutError() throws IOException, SAXException {
        testFromFolder("library/strange_graphs/groovegraphs");
    }

    @Test
    public void GXLWithoutError() throws IOException, SAXException {
        testFromFolder("library/strange_graphs/gxl");
    }

    @Test
    public void GrooveGXL() throws IOException, SAXException {
        Graph g = Importer.graphFromFile(FileUtils.fromResources("library/strange_graphs/gxl/typicalgroove.gxl"), true);
        assertEquals("\"type:testtype\"", g.getNode("#node1").getAttribute("label"));
        assertNotEquals("type:testtype", g.getNode("#node1").getAttribute("label"));
        assertEquals(g.getNode("#node1").getAttribute("label"), "\"type:testtype\"");
        //assertEquals(g.getNode("#node1").getAttribute("type"), "\"testtype\"");
    }

    private void testFromFolder(String folder) throws IOException, SAXException {
        for (File f : FileUtils.recursiveInDirectory(FileUtils.fromResources(folder))) {
            try {
                if (GXLImporter.acceptsExtension(FileUtils.getExtension(f.getName()))) {
                    Importer.graphFromFile(f);
                }
            } catch (Exception e) {
                System.err.println("Error reading file " + f.getPath());
                throw e;
            }
        }
    }
}