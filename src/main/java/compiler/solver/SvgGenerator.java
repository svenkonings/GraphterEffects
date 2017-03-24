package compiler.solver;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * A generator for converting visualization elements to a SVG docuemtn
 */
public class SvgGenerator {
    /**
     * Generates a SVG document based on the given visualization elements.
     *
     * @param visElems The given visualization elements.
     * @return a SVG document.
     */
    public static Document generate(List<VisElem> visElems) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("svg", "http://www.w3.org/2000/svg");
        setViewBox(root, visElems);
        visElems.stream()
                .sorted(Comparator.comparing(elem -> elem.getVar("z").getValue()))
                .forEach(visElem -> addElement(visElem, root));
        return document;
    }

    /**
     * Calculate and set the viewBox attribute of the given SVG element. The viewBox is calculated based on the maximum
     * x2 and y2 values of the given list of visualization elements.
     *
     * @param element  The given element.
     * @param visElems The given list of visualization elements.
     */
    private static void setViewBox(Element element, List<VisElem> visElems) {
        int maxX = max(visElems, "x2");
        int maxY = max(visElems, "y2");
        if (maxX > 0 && maxY > 0) {
            element.addAttribute("viewBox", String.format("0 0 %d %d", maxX, maxY));
        }
    }

    /**
     * Calculate the maximum value of the attribute with the given name.
     *
     * @param visElems The list of visualization elements.
     * @param name     The name of the attribute.
     * @return The maximum value.
     */
    private static int max(List<VisElem> visElems, String name) {
        return visElems.stream()
                .mapToInt(visElem -> visElem.getVar(name).getValue())
                .max().orElse(0);
    }

    /**
     * Transforms the given visualization element to a SVG element and adds it to the given parent SVG element. The
     * name-value pairs are added as attributes to the SVG element if they are applicable to this type according to
     * {@link SvgAttributes#fromVisType(VisType)}.
     *
     * @param visElem The visualization element.
     * @param parent  The parent SVG element.
     */
    private static void addElement(VisElem visElem, Element parent) {
        VisType type = visElem.getType();
        if (type == null) {
            return;
        }
        Element element = parent.addElement(VisType.toSvgElement(type));
        Set<String> attributes = SvgAttributes.fromVisType(type);
        visElem.getValues().entrySet().stream()
                .filter(entry -> attributes.contains(entry.getKey()))
                .forEach(entry -> element.addAttribute(entry.getKey(), entry.getValue()));
    }
}
