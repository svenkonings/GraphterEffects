package svg;

import org.dom4j.Element;
import solver.VisElem;
import solver.temp.SvgAttributes;
import solver.temp.VisType;

// TODO: Extendibility
public class SvgElementGenerator {
    /**
     * Transforms the given visualization element to a SVG element and adds it to the given parent SVG element. The
     * name-value pairs are added as attributes to the SVG element if they are applicable to this type according to
     * {@link SvgAttributes#fromVisType(VisType)}.
     *
     * @param visElem The visualization element.
     * @param parent  The parent SVG element.
     */
    public static void addElement(VisElem visElem, Element parent) {
        String type = visElem.getValue("type");
        if (type == null) {
            return;
        }
        switch (type) {
            case "rectangle":
                addRectangle(visElem, parent);
                break;
            case "ellipse":
                addEllips(visElem, parent);
                break;
        }
    }

    private static void addRectangle(VisElem visElem, Element parent) {
        Element element = parent.addElement("rect");
        element.addAttribute("x", visElem.getValue("x1"));
        element.addAttribute("y", visElem.getValue("y1"));
        element.addAttribute("width", visElem.getValue("width"));
        element.addAttribute("height", visElem.getValue("height"));
        element.addAttribute("fill", visElem.getValue("color"));
    }

    private static void addEllips(VisElem visElem, Element parent) {
        Element element = parent.addElement("ellipse");
        element.addAttribute("cx", visElem.getValue("centerX"));
        element.addAttribute("cy", visElem.getValue("centerY"));
        element.addAttribute("rx", visElem.getValue("radiusX"));
        element.addAttribute("ry", visElem.getValue("radiusY"));
        element.addAttribute("fill", visElem.getValue("color"));
    }
}
