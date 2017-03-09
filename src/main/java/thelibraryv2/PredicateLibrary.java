package thelibraryv2;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class PredicateLibrary {

    private Map<Predicate, Method> predicatemap = new HashMap<>();

    public Map<Predicate, Method> getMap() {
        return predicatemap;
    }

    public boolean add(Predicate pred, Method med) {
        if (predicatemap.containsKey(pred)) {
            return false;
        }
        predicatemap.put(pred, med);
        return true;
        }


}
