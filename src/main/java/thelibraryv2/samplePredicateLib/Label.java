package thelibraryv2.samplePredicateLib;

import thelibraryv2.*;

import java.util.LinkedList;
import java.util.List;

public class Label extends Predicate {

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
