package solver;

import java.util.HashMap;
import java.util.Map;

public class IdMap {
    private final Map<Object, Integer> keyMap;
    private final Map<Integer, Object> valueMap;

    public IdMap() {
        keyMap = new HashMap<>();
        valueMap = new HashMap<>();
    }

    public Integer getId(Object key) {
        if (!keyMap.containsKey(key)) {
            keyMap.put(key, keyMap.size());
            valueMap.put(valueMap.size(), key);
        }
        return keyMap.get(key);
    }

    public Object getKey(Integer id) {
        return valueMap.get(id);
    }

    @Override
    public String toString() {
        return valueMap.toString();
    }
}
