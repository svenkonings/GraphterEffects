package compiler.asrc;

import org.graphstream.graph.Graph;

public final class DefaultGraphPropertySupplier extends PropertySupplier<Graph> {

    /*
    public DefaultGraphPropertySupplier() {
        String[] tosupport = {"graph", "directed", "undirected", "mixed", "attribute"};
        supported = tosupport;
    }

    @Override
    public List<Term> getProperty(Graph graph, String key) {
        switch (key) {
            case "graph":
                return NULL;
            case "directed":
                for (Edge edge: graph.getEdgeSet()) {
                    if (!edge.isDirected()) {
                        return FALSE;
                    }
                }
                return TRUE;
            case "undirected":
                for (Edge edge: graph.getEdgeSet()) {
                    if (edge.isDirected()) {
                        return FALSE;
                    }
                }
                return TRUE;
            case "mixed":
                if (graph.getEdgeSet().isEmpty()) {
                    return TRUE;
                }
                boolean dir = false;
                boolean undir = false;
                for (Edge edge: graph.getEdgeSet()) {
                    if (edge.isDirected()) {
                        dir = true;
                    } else {
                        undir = true;
                    }
                    if (dir && undir) {
                        return TRUE;
                    }
                }
                return FALSE;
            case "attribute":
                List<String> toreturn = new LinkedList<>();
                return null;
            //TODO



        }
        return null;
    }
    */
}
