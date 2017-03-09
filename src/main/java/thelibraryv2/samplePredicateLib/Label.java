package thelibraryv2.samplePredicateLib;

import Graphstream.Edge;
import Graphstream.Node;
import thelibraryv2.exceptions.InvalidSizeException;
import thelibraryv2.predicates.GraphPredicate;

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
            return ((Node)input.get(0)).getProp("label").equals(input.get(1));
        } else if (input.get(0) instanceof Edge) {
            return ((Edge)input.get(0)).getProp("label").equals(input.get(1));
        }
        return false;
    }
}
