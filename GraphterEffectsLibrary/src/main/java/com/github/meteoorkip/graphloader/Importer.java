package com.github.meteoorkip.graphloader;


import com.github.meteoorkip.utils.FileUtils;
import net.sourceforge.gxl.graphloader.GXLImporter;
import net.sourceforge.gxl.graphloader.GraphStreamImporter;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

/**
 * Class responsible for importing Graphs from a graph-representing format.
 */
public final class Importer {

    /**
     * Reads a {@link Graph} from a variety of formats.
     *
     * @param path Path to the file from which to read the Graph.
     * @return A {@link Graph} read from the file.
     * @throws IOException  Thrown when the File could not be read.
     * @throws SAXException Thrown when the File has a GXL extension but with faulty syntax.
     */
    public static Graph graphFromFile(String path) throws IOException, SAXException {
        return graphFromFile(new File(path));
    }

    /**
     * Reads a {@link Graph} from a variety of formats.
     *
     * @param file {@link File} from which to read the Graph
     * @return A {@link Graph} read from the file.
     * @throws IOException  Thrown when the File could not be read.
     * @throws SAXException Thrown when the File has a GXL extension but with faulty syntax.
     */
    public static Graph graphFromFile(File file) throws IOException, SAXException {
        Graph g;
        String extension = FileUtils.getExtension(file.getName());
        if (GXLImporter.acceptsExtension(extension)) {
            g = GXLImporter.read(file, false);
        } else if (GraphStreamImporter.acceptsExtension(extension)) {
            g = GraphStreamImporter.read(file);
        } else {
            try {
                g = GXLImporter.read(file, false);
            } catch (SAXException e) {
                g = null;
            }
        }
        if (g == null) {
            throw new UnsupportedOperationException("Unknown file extension for file: " + file.getName());
        }
        return g;
    }
}
