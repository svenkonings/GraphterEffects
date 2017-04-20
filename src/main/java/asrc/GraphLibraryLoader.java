package asrc;

import org.graphstream.graph.Graph;

public interface GraphLibraryLoader {
    GraphLibrary getInstance(Graph graph);
}
