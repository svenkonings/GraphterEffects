package solvers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by poesd_000 on 09/03/2017.
 */
public class VisElem {

    private Map<String, String> properties = new HashMap<>();


    public void set(String key, String value) {
        properties.put(key, value);
    }

    public String get(String key) {
        return properties.get(key);
    }

    public boolean hasKey(String key) {
        return properties.containsKey(key);
    }
}
