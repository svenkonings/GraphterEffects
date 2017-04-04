package compiler.asrc;

import alice.tuprolog.Library;
import org.graphstream.graph.Graph;

public abstract class GraphLibrary extends Library {

    public final Graph graph;

    public GraphLibrary(Graph g) {
        this.graph = g;
    }

}
