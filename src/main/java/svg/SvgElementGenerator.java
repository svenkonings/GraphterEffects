package svg;

import org.dom4j.Element;
import solver.VisElem;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Generates SVG elements from visualization elements.
 */
@SuppressWarnings("WeakerAccess")
public class SvgElementGenerator {

    /** The mapping from visualization element type to {@link SvgAttributeGenerator} */
    private final Map<String, SvgAttributeGenerator> generators;

    /**
     * Creates a new generator with the default mapping.
     */
    public SvgElementGenerator() {
        generators = new LinkedHashMap<>();
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
        generators.put("square", square());
        generators.put("circle", circle());
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
    public void generate(VisElem visElem, Element parent) {
        String type = visElem.getValue("type");
        if (type == null) {
            return;
        }
        SvgAttributeGenerator attr = getGenerator(type);
        if (attr != null) {
            attr.generate(visElem, parent);
        }
    }

    /**
     * Set the given type with the given {@link SvgAttributeGenerator}.
     *
     * @param type The given type.
     * @param attr The given {@link SvgAttributeGenerator}.
     * @return The previous {@link SvgAttributeGenerator} associated with the given type, or {@code null} if it didn't
     * exist.
     */
    public SvgAttributeGenerator setGenerator(String type, SvgAttributeGenerator attr) {
        return generators.put(type, attr);
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

    public static SvgAttributeGenerator element(String name) {
        SvgAttributeGenerator attr = new SvgAttributeGenerator(name);
        attr.setMapping("colour", "fill");
        attr.setMapping("stroke", "stroke");
        attr.setMapping("strokeWidth", "stroke-width");
        attr.setMapping("strokeDashArray", "stroke-dasharray");
        return attr;
    }

    public static SvgAttributeGenerator shape(String name) {
        SvgAttributeGenerator attr = element(name);
        attr.addDefault(elem -> {
            if (elem.attribute("fill") == null) {
                elem.addAttribute("fill", "white");
                if (elem.attribute("stroke") == null) {
                    elem.addAttribute("stroke", "black");
                }
            }
        });
        return attr;
    }

    public static SvgAttributeGenerator rectangle() {
        SvgAttributeGenerator attr = shape("rect");
        attr.setMapping("x1", "x");
        attr.setMapping("y1", "y");
        attr.setMapping("width", "width");
        attr.setMapping("height", "height");
        return attr;
    }

    public static SvgAttributeGenerator ellipse() {
        SvgAttributeGenerator attr = shape("ellipse");
        attr.setMapping("centerX", "cx");
        attr.setMapping("centerY", "cy");
        attr.setMapping("radiusX", "rx");
        attr.setMapping("radiusY", "ry");
        return attr;
    }

    public static SvgAttributeGenerator square() {
        SvgAttributeGenerator attr = shape("rect");
        attr.setMapping("x1", "x");
        attr.setMapping("y1", "y");
        attr.setMapping("width", "width");
        attr.setMapping("height", "height");
        return attr;
    }

    public static SvgAttributeGenerator circle() {
        SvgAttributeGenerator attr = shape("circle");
        attr.setMapping("centerX", "cx");
        attr.setMapping("centerY", "cy");
        attr.setMapping("radius", "r");
        return attr;
    }

    public static SvgAttributeGenerator line() {
        SvgAttributeGenerator attr = shape("line");
        attr.setMapping("x1", "x1");
        attr.setMapping("x2", "x2");
        attr.setMapping("y1", "y1");
        attr.setMapping("y2", "y2");
        attr.setMapping("markerStart", (element, value) -> {
            addArrowDef(element);
            element.addAttribute("marker-start", "url(#arrow)");
        });
        attr.setMapping("markerEnd", (element, value) -> {
            addArrowDef(element);
            element.addAttribute("marker-end", "url(#arrow)");
        });
        return attr;
    }

    private static void addArrowDef(Element element) {
        Element root = element.getParent();
        Element defs = root.element("defs");
        boolean hasArrow = false;
        if (defs == null) {
            defs = root.addElement("defs");
        } else {
            for (Object object : defs.elements("marker")) {
                if (object instanceof Element && "arrow".equals(((Element) object).attributeValue("id"))) {
                    hasArrow = true;
                    break;
                }
            }
        }
        if (!hasArrow) {
            Element arrow = defs.addElement("marker");
            arrow.addAttribute("id", "arrow");
            arrow.addAttribute("markerWidth", "10");
            arrow.addAttribute("markerHeight", "10");
            arrow.addAttribute("refX", "0");
            arrow.addAttribute("refY", "3");
            arrow.addAttribute("orient", "auto");
            arrow.addAttribute("markerUnits", "strokeWidth");
            arrow.addAttribute("id", "arrow");
            Element path = arrow.addElement("path");
            path.addAttribute("d", "M0,0 L0,6 L9,3 z");
        }
    }

    public static SvgAttributeGenerator image() {
        SvgAttributeGenerator attr = element("image");
        attr.setMapping("x1", "x");
        attr.setMapping("y1", "y");
        attr.setMapping("width", "width");
        attr.setMapping("height", "height");
        attr.setMapping("image", (element, value) -> {
            Element root = element.getParent();
            if (root.getNamespaceForPrefix("xlink") == null) {
                root.addNamespace("xlink", "http://www.w3.org/1999/xlink");
            }
            element.addAttribute("xlink:href", value);
        });
        // TODO: Add predicate for this
        attr.addDefault("preserveAspectRatio", "none");
        return attr;
    }

    public static SvgAttributeGenerator text() {
        SvgAttributeGenerator attr = element("text");
        attr.setMapping("x1", "x");
        attr.setMapping("y1", "y");
        attr.setMapping("width", "textLength");
        attr.setMapping("height", "font-size");
        attr.setMapping("text", Element::addText);
        attr.addDefault("alignment-baseline", "hanging");
        attr.addDefault("lengthAdjust", "spacingAndGlyphs");
        return attr;
    }
}
