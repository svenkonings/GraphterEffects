package thelibraryv2;

import java.util.*;

public abstract class VisPredicate extends Predicate {

    private Set<List<Class>> acceptedCombinations;

    public boolean accepts(List<Class> input) {
        return acceptedCombinations.contains(input);
    }

    public boolean accepts(Class[] input) {
        List<Class> res = Arrays.asList(input);
        return acceptedCombinations.contains(res);
    }

    public abstract String modSVG(String beforeSVG);
}
