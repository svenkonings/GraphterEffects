package compiler.svg;

import compiler.solver.VisElem;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

// TODO: Extendibility
public class SvgElementGenerator {

    private static SvgElementGenerator generator;

    private final Map<String, AttributeMapping> elementMapping;

    public static SvgElementGenerator getInstance() {
        if (generator == null) {
            generator = new SvgElementGenerator();
        }
        return generator;
    }

    public static void addElement(VisElem visElem, Element parent) {
        SvgElementGenerator generator = getInstance();
        String type = visElem.getValue("type");
        if (type == null) {
            return;
        }
        AttributeMapping mapping = generator.getMapping(type);
        if (mapping != null) {
            mapping.addElement(visElem, parent);
        }
    }

    public SvgElementGenerator() {
        elementMapping = new HashMap<>();
        elementMapping.put("rectangle", rectangle());
        elementMapping.put("ellipse", ellipse());
        elementMapping.put("line", line());
        elementMapping.put("image", image());
    }

    public AttributeMapping getMapping(String type) {
        return elementMapping.get(type);
    }

    private static AttributeMapping shape(String name) {
        AttributeMapping mapping = new AttributeMapping(name);
        mapping.putMapping("colour", "fill");
        mapping.putMapping("border-colour", "stroke");
        // TODO: Move to VisElem?
        mapping.putDefault("fill", "white");
        mapping.putDefault("stroke", "black");
        return mapping;
    }

    private static AttributeMapping rectangle() {
        AttributeMapping mapping = shape("rect");
        mapping.putMapping("x1", "x");
        mapping.putMapping("y1", "y");
        mapping.putMapping("width", "width");
        mapping.putMapping("height", "height");
        return mapping;
    }

    private static AttributeMapping ellipse() {
        AttributeMapping mapping = shape("ellipse");
        mapping.putMapping("centerX", "cx");
        mapping.putMapping("centerY", "cy");
        mapping.putMapping("radiusX", "rx");
        mapping.putMapping("radiusY", "ry");
        return mapping;
    }

    private static AttributeMapping line() {
        AttributeMapping mapping = shape("line");
        mapping.putMapping("x1", "x1");
        mapping.putMapping("x2", "x2");
        mapping.putMapping("y1", "y1");
        mapping.putMapping("y2", "y2");
        return mapping;
    }

    private static AttributeMapping image() {
        AttributeMapping mapping = new AttributeMapping("image");
        mapping.putMapping("x1", "x");
        mapping.putMapping("y1", "y");
        mapping.putMapping("width", "width");
        mapping.putMapping("height", "height");
        mapping.putMapping("image", "href");
        return mapping;
    }
}
