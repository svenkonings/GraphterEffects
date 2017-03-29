package compiler.asrc;

import org.graphstream.graph.Node;

public class DefaultNodePropertySupplier extends PropertySupplier<Node> {

    public DefaultNodePropertySupplier() {
        String[] tosupport = {"node", "neighbourcount", "degree", "indegree", "outdegree", "attributecount", "attribute"};
        supported = tosupport;
    }

    @Override
    public String[] getProperty(Node input, String key) {
        return null;
    }
}
