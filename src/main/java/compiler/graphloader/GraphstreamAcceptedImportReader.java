package compiler.graphloader;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

final class GraphstreamAcceptedImportReader {

    private static final List<String> acceptslist = Arrays.asList("dgs", "dot", "gml", "tlp", "net", "graphml", "net");

    public static boolean acceptsExtension(String ext) {
        return acceptslist.contains(ext.toLowerCase());
    }

    public static Graph read(File file) throws IOException {
        MultiGraph g = new MultiGraph(file.getName());
        FileSource fs = FileSourceFactory.sourceFor(file.getAbsolutePath());
        fs.addSink(g);
        fs.readAll(file.getAbsolutePath());
        fs.removeSink(g);
        return g;
    }
}
