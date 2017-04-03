package compiler.svg;

import compiler.solver.VisElem;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class SvgAttributeGenerator {
    private final String name;
    private final Map<String, String> mappings;
    private final Map<String, String> defaults;

    public SvgAttributeGenerator(String name) {
        this.name = name;
        mappings = new HashMap<>();
        defaults = new HashMap<>();
    }

    public String setMapping(String key, String value) {
        return mappings.put(key, value);
    }

    public String getMapping(String key) {
        return mappings.get(key);
    }

    public String removeMapping(String key) {
        return mappings.remove(key);
    }

    public void putMappings(Map<String, String> map) {
        mappings.putAll(map);
    }

    public Map<String, String> getMappings() {
        return mappings;
    }

    public String putDefault(String key, String value) {
        return defaults.put(key, value);
    }

    public String getDefault(String key) {
        return defaults.get(key);
    }

    public String removeDefault(String key) {
        return defaults.remove(key);
    }

    public void putDefaults(Map<String, String> map) {
        defaults.putAll(map);
    }

    public Map<String, String> getDefaults() {
        return defaults;
    }

    public void addElement(VisElem visElem, Element parent) {
        Element element = parent.addElement(name);
        visElem.getValues().forEach((key, value) -> {
            if (mappings.containsKey(key)) {
                element.addAttribute(mappings.get(key), value);
            }
        });
        defaults.forEach((key, value) -> {
            if (element.attribute(key) == null) {
                element.addAttribute(key, value);
            }
        });
    }
}
