package svg;

import org.dom4j.Element;
import solver.VisElem;

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
    }

    public AttributeMapping getMapping(String type) {
        return elementMapping.get(type);
    }

    private static AttributeMapping rectangle() {
        AttributeMapping mapping = new AttributeMapping("rect");
        mapping.putMapping("x1", "x");
        mapping.putMapping("y1", "y");
        mapping.putMapping("width", "width");
        mapping.putMapping("height", "height");
        mapping.putMapping("colour", "fill");
        return mapping;
    }

    private static AttributeMapping ellipse() {
        AttributeMapping mapping = new AttributeMapping("ellipse");
        mapping.putMapping("centerX", "cx");
        mapping.putMapping("centerY", "cy");
        mapping.putMapping("radiusX", "rx");
        mapping.putMapping("radiusY", "ry");
        mapping.putMapping("colour", "fill");
        return mapping;
    }
}
