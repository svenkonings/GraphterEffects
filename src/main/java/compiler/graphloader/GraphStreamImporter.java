package compiler.graphloader;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Class used to import graphs from formats natively supported by GraphStream.
 */
final class GraphStreamImporter {

    /**
     * List of file extensions accepted by this importer.
     */
    private static final List<String> acceptslist = Arrays.asList("dgs", "dot", "gml", "tlp", "net", "graphml", "net");

    /**
     * Returns whether an extension is accepted by this importer.
     *
     * @param ext File extension to verify.
     * @return <tt>true</tt> if the file extension is accepted.
     */
    static boolean acceptsExtension(String ext) {
        return acceptslist.contains(ext.toLowerCase());
    }

    /**
     * Reads a file in some graph format into a GraphStream graph Object.
     *
     * @param file File to read into a GraphsStream Graph Object.
     * @return A GraphStream Graph Object containing the graph represented in the file.
     * @throws IOException Thrown when the file could not be read.
     */
    static Graph read(File file) throws IOException {
        MultiGraph g = new MultiGraph(file.getName());
        FileSource fs = FileSourceFactory.sourceFor(file.getAbsolutePath());
        fs.addSink(g);
        fs.readAll(file.getAbsolutePath());
        fs.removeSink(g);
        return g;
    }

    /**
     * Returns an iterator iterating over all accepted extensions.
     *
     * @return An iterator iterating over all accepted extensions.
     */
    public static Iterator<String> accepted() {
        return acceptslist.iterator();
    }
}
