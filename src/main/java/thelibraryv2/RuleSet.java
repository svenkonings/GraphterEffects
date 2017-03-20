package thelibraryv2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by poesd_000 on 20/03/2017.
 */
public class RuleSet {

    private Map<VisElem, Map<String, String>> completedrules = new HashMap<>();

    public void set(VisElem vis, String key, String value) {
        if (!completedrules.containsKey(vis)) {
            completedrules.put(vis, new HashMap<>());
        }
        completedrules.get(vis).put(key, value);
    }
}
