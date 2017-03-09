package thelibraryv2.predicates;

import thelibraryv2.predicates.Predicate;

import java.util.*;

public abstract class VisPredicate extends Predicate {

    protected Set<List<Class>> acceptedCombinations = new HashSet<>();

    public boolean accepts(List<Class> input) {
        return acceptedCombinations.contains(input);
    }

    public boolean accepts(Class[] input) {
        List<Class> res = Arrays.asList(input);
        return acceptedCombinations.contains(res);
    }

    public abstract String modSVG(String beforeSVG, Object... args);
}
