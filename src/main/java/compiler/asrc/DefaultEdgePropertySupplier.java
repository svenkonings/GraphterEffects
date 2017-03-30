package compiler.asrc;

import org.graphstream.graph.Edge;

public class DefaultEdgePropertySupplier extends PropertySupplier<Edge> {

    public DefaultEdgePropertySupplier() {
        String[] tosupport = {"edge", "directed", "undirected", "attributecount", "attribute"};
        supported = tosupport;
    }

    @Override
    public String[] getProperty(Edge input, String key) {
        return null;
    }
}
