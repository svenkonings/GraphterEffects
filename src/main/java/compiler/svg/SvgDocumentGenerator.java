package compiler.svg;

import compiler.solver.VisElem;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * A generator for converting visualization elements to a SVG docuemtn
 */
public class SvgDocumentGenerator {
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
                .sorted(Comparator.comparingInt(elem -> elem.getVar("z").getValue()))
                .forEachOrdered(visElem -> SvgElementGenerator.addElement(visElem, root));
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
        int minX = min(visElems, "x1");
        int minY = min(visElems, "y1");
        int maxX = max(visElems, "x2");
        int maxY = max(visElems, "y2");
        if (minX >= 0 && minY >= 0 && maxX >= 0 && maxY >= 0) {
            element.addAttribute("viewBox", String.format("%d %d %d %d", minX, minY, maxX, maxY));
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
                .max().orElse(-1);
    }

    /**
     * Calculate the minimum value of the attribute with the given name.
     *
     * @param visElems The list of visualization elements.
     * @param name     The name of the attribute.
     * @return The minimum value.
     */
    private static int min(List<VisElem> visElems, String name) {
        return visElems.stream()
                .mapToInt(visElem -> visElem.getVar(name).getValue())
                .min().orElse(-1);
    }

    /**
     * Write the given document to the file with the given filename.
     *
     * @param document The given document.
     * @param filename The given filename.
     * @throws IOException If unable to write to the file
     */
    public static void writeDocument(Document document, String filename) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileWriter(filename), format);
            writer.write(document);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
