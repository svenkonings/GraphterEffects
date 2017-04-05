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
// TODO: Extendability
public class SvgDocumentGenerator {

    /**
     * Generates a SVG document based on the given visualization elements.
     *
     * @param visElems The given visualization elements.
     * @return a SVG document.
     */
    public static Document generate(List<VisElem> visElems) {
        return generate(new SvgElementGenerator(), visElems);
    }

    /**
     * Generates a SVG document based on the given visualization elements with the given element generator.
     *
     * @param generator The given element generator
     * @param visElems  The given visualization elements.
     * @return a SVG document.
     */
    public static Document generate(SvgElementGenerator generator, List<VisElem> visElems) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("svg", "http://www.w3.org/2000/svg");

        // TODO: Extendibility
        int minX = min(visElems, "minX");
        int minY = min(visElems, "minY");
        int width = max(visElems, "maxX") - minX;
        int height = max(visElems, "maxY") - minY;
        root.addAttribute("viewBox", String.format("%d %d %d %d", minX, minY, width, height));

        int z = min(visElems, "z") - 1;
        visElems.stream()
                .filter(visElem -> visElem.getValue("type").equals("backgroundImage"))
                .findAny()
                .ifPresent(visElem -> {
                    visElem.setVar("z", z);
                    visElem.setVar("x1", minX);
                    visElem.setVar("y1", minY);
                    visElem.setVar("width", width);
                    visElem.setVar("height", height);
                    SvgAttributeGenerator mapping = SvgElementGenerator.image();
                    mapping.addDefault("preserveAspectRatio", "none");
                    mapping.addElement(visElem, root);
                });

        visElems.stream()
                .sorted(Comparator.comparingInt(elem -> elem.getVar("z").getValue()))
                .forEachOrdered(visElem -> generator.addElement(visElem, root));
        return document;
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
     * Calculate the minimum value of the attribute with the given name.
     *
     * @param visElems The list of visualization elements.
     * @param name     The name of the attribute.
     * @return The minimum value.
     */
    private static int min(List<VisElem> visElems, String name) {
        return visElems.stream()
                .mapToInt(visElem -> visElem.getVar(name).getValue())
                .min().orElse(0);
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
