package compiler.svg;

import compiler.solver.VisElem;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Contains the mapping of visualization attributes to element consumers and the list of default element consumers. The
 * mapping element consumers consume both an element and a value. The default element consumers only consume an element.
 * The consumers can modify the element, for example by setting the value of an attribute.
 */
public class SvgAttributeGenerator {

    /** The name of the SVG element */
    private final String name;

    /** The mapping of visualization attributes to element consumers. */
    private final Map<String, BiConsumer<Element, String>> mappings;

    /** The mapping of SVG attributes to element consumers. */
    private final List<Consumer<Element>> defaults;

    /**
     * Creates a generator with the given name.
     *
     * @param name The given name.
     */
    public SvgAttributeGenerator(String name) {
        this.name = name;
        mappings = new HashMap<>();
        defaults = new ArrayList<>();
    }

    /**
     * Associates the given visualization attribute with an element consumer that adds the consumed value to the given
     * SVG attribute.
     *
     * @param visAttr The given visualization attribute.
     * @param svgAttr The given SVG attribute
     * @return The previous element consumer, or {@code null} if it didn't exist.
     */
    public BiConsumer<Element, String> setMapping(String visAttr, String svgAttr) {
        return setMapping(visAttr, (elem, value) -> elem.addAttribute(svgAttr, value));
    }

    /**
     * Associates the given visualization attribute with the given element consumer.
     *
     * @param visAttr  The given visualization attribute.
     * @param consumer The given element consumer.
     * @return The previous element consumer, or {@code null} if it didn't exist.
     */
    public BiConsumer<Element, String> setMapping(String visAttr, BiConsumer<Element, String> consumer) {
        return mappings.put(visAttr, consumer);
    }

    /**
     * Return the element consumer associated with the given visualization attribute.
     *
     * @param visAttr The given visualization attribute.
     * @return The associated SVG attribute.
     */
    public BiConsumer<Element, String> getMapping(String visAttr) {
        return mappings.get(visAttr);
    }

    /**
     * Removes the mapping of the given visualization attribute.
     *
     * @param visAttr The given visualization attribute.
     * @return The associated SVG attribute.
     */
    public BiConsumer<Element, String> removeMapping(String visAttr) {
        return mappings.remove(visAttr);
    }

    public boolean addDefault(String svgAttr, String value) {
        return addDefault(elem -> {
            if (elem.attribute(svgAttr) == null) {
                elem.addAttribute(svgAttr, value);
            }
        });
    }

    public boolean addDefault(Consumer<Element> consumer) {
        return defaults.add(consumer);
    }

    public Consumer<Element> removeDefault(int index) {
        return defaults.remove(index);
    }

    /**
     * Add the SVG element generated from the given visualization element to the given parent SVG element.
     *
     * @param visElem The given visualization element.
     * @param parent  The given parent SVG element.
     */
    public void generate(VisElem visElem, Element parent) {
        Element element = parent.addElement(name);
        visElem.getValues().forEach((key, value) -> {
            if (mappings.containsKey(key)) {
                mappings.get(key).accept(element, value);
            }
        });
        defaults.forEach(consumer -> consumer.accept(element));
    }
}
