package thelibraryv2.predicates;

import thelibraryv2.exceptions.InvalidSizeException;
import thelibraryv2.predicates.Predicate;

import java.util.List;

public abstract class GraphPredicate extends Predicate {

    public abstract boolean check(List<Object> input) throws InvalidSizeException;


}
