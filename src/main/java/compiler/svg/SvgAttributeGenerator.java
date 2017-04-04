package compiler.svg;

import compiler.solver.VisElem;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains the mapping of visualization attributes to SVG attributes and the mapping of SVG attributes to their default
 * values.
 */
public class SvgAttributeGenerator {

    /** The name of the SVG element */
    private final String name;

    /** The mapping of visualization attributes to SVG attributes. */
    private final Map<String, String> mappings;

    /** The mapping of SVG attributes to their default values. */
    private final Map<String, String> defaults;

    /**
     * Creates a generator with the given name.
     *
     * @param name The given name.
     */
    public SvgAttributeGenerator(String name) {
        this.name = name;
        mappings = new HashMap<>();
        defaults = new HashMap<>();
    }

    /**
     * Associates the given visualization attribute with the given SVG attribute.
     *
     * @param key   The given visualization attribute.
     * @param value The given SVG attribute
     * @return The previous SVG attribute, or {@code null} if it didn't exist.
     */
    public String setMapping(String key, String value) {
        return mappings.put(key, value);
    }

    /**
     * Return the SVG attribute associated with the given visualization attribute.
     *
     * @param key The given visualization attribute.
     * @return The associated SVG attribute.
     */
    public String getMapping(String key) {
        return mappings.get(key);
    }

    /**
     * Removes the mapping of the given visualization attribute.
     *
     * @param key The given visualization attribute.
     * @return The associated SVG attribute.
     */
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

    /**
     * Add the SVG element generated from the given visualization element to the given parent SVG element.
     *
     * @param visElem The given visualization element.
     * @param parent  The given parent SVG element.
     */
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
