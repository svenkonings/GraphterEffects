package thelibrary;

import java.util.List;

public abstract class Predicate {

    public List<Class> expected;

    public List<Class> getExpectedClasses() {
        return expected;
    }

    public abstract List<Object> solveForNull(List<Object> input) throws MultipleNullException, InvalidSizeException, NoSolutionException;


}
