package compiler.graphloader;


import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;
import utils.GraphUtils;
import utils.FileUtils;

import java.io.IOException;

/**
 * Class responsible for importing Graphs from a graph-representing format.
 */
public final class Importer {

    /**
     * Reads a graph from a variety of formats.
     *
     * @param path File from which to read the Graph
     * @return A GraphStream graph read from the file.
     * @throws IOException  Thrown when the File could not be read.
     * @throws SAXException Thrown when the File has a GXL extension but with faulty syntax.
     */
    public static Graph graphFromFile(String path) throws IOException, SAXException {
        return graphFromFile(path, true);
    }

    /**
     * @param path           File from which to read the Graph
     * @param addUnderscores <tt>true</tt> if underscores should be added to the IDs in the graph.
     * @return A GraphStream graph read from the file.
     * @throws IOException  Thrown when the File could not be read.
     * @throws SAXException Thrown when the File has a GXL extension but with faulty syntax.
     */
    public static Graph graphFromFile(String path, boolean addUnderscores) throws IOException, SAXException {
        Graph g;
        String extension = FileUtils.getExtension(path);
        if (GXLImporter.acceptsExtension(extension)) {
            g = GXLImporter.read(path, false);
        } else if (GraphStreamImporter.acceptsExtension(extension)) {
            g = GraphStreamImporter.read(path);
        } else {
            try {
                g = GXLImporter.read(path, false, false);
            } catch (SAXException e) {
                g = null;
            }
        }
        if (g == null) {
            throw new UnsupportedOperationException("Unknown file extension for file: " + path);
        } else if (addUnderscores) {
            g = GraphUtils.changeIDs(g);
        }
        return g;
    }
}
