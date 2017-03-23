package compiler.graphloader;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;

import java.io.File;
import java.io.IOException;

final class GraphstreamAcceptedImportReader {

    public static Graph readDOT(File file) throws IOException {
        MultiGraph g = new MultiGraph(file.getName());
        FileSource fs = FileSourceFactory.sourceFor(file.getAbsolutePath());
        fs.addSink(g);
        fs.readAll(file.getAbsolutePath());
        fs.removeSink(g);
        return g;
    }
}
