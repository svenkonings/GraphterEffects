package compiler.svg;

import compiler.solver.VisElem;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * Generates SVG elements from visualization elements.
 */
public class SvgElementGenerator {

    /** The mapping from visualization element type to {@link SvgAttributeGenerator} */
    private final Map<String, SvgAttributeGenerator> generators;

    /**
     * Creates a new generator with the default mapping.
     */
    public SvgElementGenerator() {
        generators = new HashMap<>();
        setDefaults();
    }

    /**
     * Creates a generator with the given mapping.
     *
     * @param generators The given mapping.
     */
    public SvgElementGenerator(Map<String, SvgAttributeGenerator> generators) {
        this.generators = generators;
    }

    /**
     * Set the default mapping.
     */
    private void setDefaults() {
        generators.put("rectangle", rectangle());
        generators.put("ellipse", ellipse());
        generators.put("line", line());
        generators.put("image", image());
        generators.put("text", text());
    }

    /**
     * Add the SVG element generated from the given visualization element to the given parent SVG element.
     *
     * @param visElem The given visualization element.
     * @param parent  The given parent SVG element.
     */
    public void addElement(VisElem visElem, Element parent) {
        String type = visElem.getValue("type");
        if (type == null) {
            return;
        }
        SvgAttributeGenerator mapping = getGenerator(type);
        if (mapping != null) {
            mapping.addElement(visElem, parent);
        }
    }

    /**
     * Set the given type with the given {@link SvgAttributeGenerator}.
     *
     * @param type    The given type.
     * @param mapping The given {@link SvgAttributeGenerator}.
     * @return The previous {@link SvgAttributeGenerator} associated with the given type, or {@code null} if it didn't
     * exist.
     */
    public SvgAttributeGenerator setGenerator(String type, SvgAttributeGenerator mapping) {
        return generators.put(type, mapping);
    }

    /**
     * Get the {@link SvgAttributeGenerator} associated with the given type.
     *
     * @param type The given type.
     * @return The {@link SvgAttributeGenerator}, or {@code null} if it doesn't exist.
     */
    public SvgAttributeGenerator getGenerator(String type) {
        return generators.get(type);
    }

    /**
     * Remove the given type.
     *
     * @param type The given type.
     * @return The associated {@link SvgAttributeGenerator}, or {@code null} if it doesn't exist.
     */
    public SvgAttributeGenerator removeGenerator(String type) {
        return generators.remove(type);
    }

    public static SvgAttributeGenerator shape(String name) {
        SvgAttributeGenerator mapping = new SvgAttributeGenerator(name);
        mapping.setMapping("colour", "fill");
        mapping.setMapping("stroke", "stroke");
        return mapping;
    }

    public static SvgAttributeGenerator rectangle() {
        SvgAttributeGenerator mapping = shape("rect");
        mapping.setMapping("x1", "x");
        mapping.setMapping("y1", "y");
        mapping.setMapping("width", "width");
        mapping.setMapping("height", "height");
        return mapping;
    }

    public static SvgAttributeGenerator ellipse() {
        SvgAttributeGenerator mapping = shape("ellipse");
        mapping.setMapping("centerX", "cx");
        mapping.setMapping("centerY", "cy");
        mapping.setMapping("radiusX", "rx");
        mapping.setMapping("radiusY", "ry");
        return mapping;
    }

    public static SvgAttributeGenerator line() {
        SvgAttributeGenerator mapping = shape("line");
        mapping.setMapping("x1", "x1");
        mapping.setMapping("x2", "x2");
        mapping.setMapping("y1", "y1");
        mapping.setMapping("y2", "y2");
        return mapping;
    }

    public static SvgAttributeGenerator image() {
        SvgAttributeGenerator mapping = new SvgAttributeGenerator("image");
        mapping.setMapping("x1", "x");
        mapping.setMapping("y1", "y");
        mapping.setMapping("width", "width");
        mapping.setMapping("height", "height");
        mapping.setMapping("image", "href");
        return mapping;
    }

    public static SvgAttributeGenerator text() {
        SvgAttributeGenerator mapping = shape("text");
        mapping.setMapping("x1", "x");
        mapping.setMapping("y1", "y");
        mapping.setMapping("fontSize", "font-size");
        mapping.setMapping("text", Element::addText);
        return mapping;
    }
}
