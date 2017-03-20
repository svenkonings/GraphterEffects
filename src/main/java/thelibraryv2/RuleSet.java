package thelibraryv2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by poesd_000 on 20/03/2017.
 */
public class RuleSet {

    /**
     * Container of the completed rules
     */
    private Map<VisElem, Map<String, String>> completedrules = new HashMap<>();

    /**
     * Set a completed rule
     * @param vis Visualisation element of which parameters are set
     * @param key Key of the parameter
     * @param value Value of the parameter
     */
    public void set(VisElem vis, String key, String value) {
        if (!completedrules.containsKey(vis)) {
            completedrules.put(vis, new HashMap<>());
        }
        completedrules.get(vis).put(key, value);
    }
}
