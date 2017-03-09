package thelibraryv2;

import java.util.List;

public abstract class Predicate {

    public abstract boolean check(List<Object> input) throws InvalidSizeException;


}
