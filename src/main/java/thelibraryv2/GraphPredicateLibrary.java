package thelibraryv2;

import thelibraryv2.predicates.GraphPredicate;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class GraphPredicateLibrary {


    private Map<GraphPredicate, Method> predicatemap = new HashMap<>();

    public Map<GraphPredicate, Method> getMap() {
        return predicatemap;
    }

    public boolean add(GraphPredicate pred, Method med) {
        if (predicatemap.containsKey(pred)) {
            return false;
        }
        predicatemap.put(pred, med);
        return true;
        }


}
