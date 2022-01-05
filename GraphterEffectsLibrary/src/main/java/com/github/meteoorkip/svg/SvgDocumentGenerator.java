package com.github.meteoorkip.svg;

import com.github.meteoorkip.solver.VisElem;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Comparator;

/**
 * A generator for converting visualization elements to a SVG docuemtn
 */
// TODO: Extendibility
@SuppressWarnings("WeakerAccess")
public class SvgDocumentGenerator {

    /**
     * Generates a SVG document based on the given visualization elements.
     *
     * @param visElems The given visualization elements.
     * @return a SVG document.
     */
    public static Document generate(Collection<VisElem> visElems) {
        return generate(new SvgElementGenerator(), visElems);
    }

    /**
     * Generates a SVG document based on the given visualization elements with the given element generator.
     *
     * @param generator The given element generator
     * @param visElems  The given visualization elements.
     * @return a SVG document.
     */
    public static Document generate(SvgElementGenerator generator, Collection<VisElem> visElems) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("svg", "http://www.w3.org/2000/svg");
        root.addAttribute("style", "width: 100%; max-height: 100%;");

        int minX = minPos(visElems, "minX", true);
        int minY = minPos(visElems, "minY", true);
        int maxX = maxPos(visElems, "maxX", true);
        int maxY = maxPos(visElems, "maxY", true);
        int width = maxX - minY;
        int height = maxY - minY;
        root.addAttribute("viewBox", String.format("%d %d %d %d", minX, minY, width, height));

        int minZ = minPos(visElems, "z", false) - 1;
        visElems.stream().filter(elem -> elem.hasValue("global")).forEach(elem -> {
            elem.replaceVar("z", minZ);
            elem.replaceVar("x1", minX);
            elem.replaceVar("y1", minY);
            elem.replaceVar("x2", maxX);
            elem.replaceVar("y2", maxY);
            elem.replaceVar("width", width);
            elem.replaceVar("height", height);
        });

        //noinspection ConstantConditions
        visElems.stream()
                .sorted(Comparator.comparingInt(elem -> elem.getVar("z").getValue()))
                .forEachOrdered(visElem -> generator.generate(visElem, root));
        return document;
    }

    /**
     * Calculate the maximum value of the specified position attribute
     * from the list of visualization elements.
     *
     * @param visElems      The list of visualization elements.
     * @param pos           The name of the position attribute.
     * @param includeStroke Whether to include the stroke width.
     * @return The maximum value.
     */
    private static int maxPos(Collection<VisElem> visElems, String pos, boolean includeStroke) {
        return visElems.stream()
                .filter(visElem -> visElem.hasVar(pos))
                .mapToInt(visElem -> getMaxPos(visElem, pos, includeStroke))
                .max().orElse(0);
    }

    /**
     * Get the maximum value of the specified position attribute of the specified element.
     *
     * @param visElem       The visualization element.
     * @param pos           The position attribute.
     * @param includeStroke Whether to include the stroke width.
     * @return The position attribute value.
     */
    @SuppressWarnings("ConstantConditions")
    private static int getMaxPos(VisElem visElem, String pos, boolean includeStroke) {
        int result = visElem.getVar(pos).getValue();
        if (includeStroke) {
            result += getStrokeWidth(visElem);
        }
        return result;
    }

    /**
     * Calculate the minimum value of the specified position attribute
     * from the list of visualization elements.
     *
     * @param visElems      The list of visualization elements.
     * @param pos           The name of the position attribute.
     * @param includeStroke Whether to include the stroke width.
     * @return The minimum value.
     */
    private static int minPos(Collection<VisElem> visElems, String pos, boolean includeStroke) {
        return visElems.stream()
                .filter(visElem -> visElem.hasVar(pos))
                .mapToInt(visElem -> getMinPos(visElem, pos, includeStroke))
                .min().orElse(0);
    }

    /**
     * Get the minimum value of the specified position attribute
     * of the specified element.
     *
     * @param visElem       The visualization element.
     * @param pos           The position attribute.
     * @param includeStroke Whether to include the stroke width.
     * @return The position attribute value.
     */
    @SuppressWarnings("ConstantConditions")
    private static int getMinPos(VisElem visElem, String pos, boolean includeStroke) {
        int result = visElem.getVar(pos).getValue();
        if (includeStroke) {
            result -= getStrokeWidth(visElem);
        }
        return result;
    }

    /**
     * Get the stroke width of the specified element.
     *
     * @param visElem the visualization element.
     * @return the stroke width, or 1 (default stroke width)
     * if stroke width is not specified.
     */
    @SuppressWarnings("ConstantConditions")
    private static int getStrokeWidth(VisElem visElem) {
        return visElem.hasVar("strokeWidth") ? visElem.getVar("strokeWidth").getValue() : 1;
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


    /**
     * Write the given document to a String.
     *
     * @param document The given document.
     */
    public static String writeDocumentToString(Document document) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = null;
        StringWriter stringwriter = new StringWriter();
        try {
            writer = new XMLWriter(stringwriter, format);
            try {
                writer.write(document);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                stringwriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringwriter.toString();
    }
}
