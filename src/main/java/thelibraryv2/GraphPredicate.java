package thelibraryv2;

import thelibraryv2.exceptions.InvalidSizeException;

import java.util.List;

public abstract class GraphPredicate extends Predicate {

    public abstract boolean check(List<Object> input) throws InvalidSizeException;


}
