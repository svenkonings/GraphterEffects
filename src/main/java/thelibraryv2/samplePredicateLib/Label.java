package thelibraryv2.samplePredicateLib;


import thelibraryv2.exceptions.InvalidSizeException;
import thelibraryv2.predicates.GraphPredicate;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleNode;
import org.graphstream.graph.Edge;

import java.util.List;

public class Label extends GraphPredicate {

        @Override
    public boolean check(List<Object> input) throws InvalidSizeException {
        if (input.size()!=2) {
            return false;
        }
        if (!(input.get(1) instanceof String)) {
            return false;
        }
        if (input.get(0) instanceof Node) {
            return ((Node)input.get(0)).getAttribute("label").equals(input.get(1));
        } else if (input.get(0) instanceof Edge) {
            return ((Edge)input.get(0)).getAttribute("label").equals(input.get(1));
        }
        return false;
    }
}
