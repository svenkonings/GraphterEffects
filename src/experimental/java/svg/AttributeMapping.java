package svg;

import org.dom4j.Element;
import solver.VisElem;

import java.util.HashMap;
import java.util.Map;

public class AttributeMapping {
    private final String name;
    private final Map<String, String> mapping;
    private final Map<String, String> defaults;

    public AttributeMapping(String name) {
        this.name = name;
        mapping = new HashMap<>();
        defaults = new HashMap<>();
    }

    public String putMapping(String key, String value) {
        return mapping.put(key, value);
    }

    public void putMappings(Map<String, String> map) {
        mapping.putAll(map);
    }

    public String putDefault(String key, String value) {
        return defaults.put(key, value);
    }

    public void putDefaults(Map<String, String> map) {
        defaults.putAll(map);
    }

    public void addElement(VisElem visElem, Element parent) {
        Element element = parent.addElement(name);
        visElem.getValues().forEach((key, value) -> {
            if (mapping.containsKey(key)) {
                element.addAttribute(mapping.get(key), value);
            }
        });
        defaults.forEach((key, value) -> {
            if (element.attribute(key) == null) {
                element.addAttribute(key, value);
            }
        });
    }
}
