package compiler.asrc;

import alice.tuprolog.Library;
import org.graphstream.graph.Graph;

public abstract class GraphLibrary extends Library {

    public Graph graph;

    public GraphLibrary(Graph g) {
        this.graph = g;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
}
