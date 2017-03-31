package compiler.svg;

import compiler.solver.VisElem;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class SvgElementGenerator {

    private final Map<String, SvgAttributeGenerator> generators;

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

    public SvgElementGenerator() {
        generators = new HashMap<>();
    }

    private void setDefaults() {
        generators.put("rectangle", rectangle());
        generators.put("ellipse", ellipse());
        generators.put("line", line());
        generators.put("image", image());
    }

    public SvgAttributeGenerator setGenerator(String type, SvgAttributeGenerator mapping) {
        return generators.put(type, mapping);
    }

    public SvgAttributeGenerator getGenerator(String type) {
        return generators.get(type);
    }

    public SvgAttributeGenerator removeGenerator(String type) {
        return generators.remove(type);
    }

    private static SvgAttributeGenerator shape(String name) {
        SvgAttributeGenerator mapping = new SvgAttributeGenerator(name);
        mapping.setMapping("colour", "fill");
        mapping.setMapping("border-colour", "stroke");
        return mapping;
    }

    private static SvgAttributeGenerator rectangle() {
        SvgAttributeGenerator mapping = shape("rect");
        mapping.setMapping("x1", "x");
        mapping.setMapping("y1", "y");
        mapping.setMapping("width", "width");
        mapping.setMapping("height", "height");
        return mapping;
    }

    private static SvgAttributeGenerator ellipse() {
        SvgAttributeGenerator mapping = shape("ellipse");
        mapping.setMapping("centerX", "cx");
        mapping.setMapping("centerY", "cy");
        mapping.setMapping("radiusX", "rx");
        mapping.setMapping("radiusY", "ry");
        return mapping;
    }

    private static SvgAttributeGenerator line() {
        SvgAttributeGenerator mapping = shape("line");
        mapping.setMapping("x1", "x1");
        mapping.setMapping("x2", "x2");
        mapping.setMapping("y1", "y1");
        mapping.setMapping("y2", "y2");
        return mapping;
    }

    private static SvgAttributeGenerator image() {
        SvgAttributeGenerator mapping = new SvgAttributeGenerator("image");
        mapping.setMapping("x1", "x");
        mapping.setMapping("y1", "y");
        mapping.setMapping("width", "width");
        mapping.setMapping("height", "height");
        mapping.setMapping("image", "href");
        return mapping;
    }
}
