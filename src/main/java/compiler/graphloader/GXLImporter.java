package compiler.graphloader;

import net.sourceforge.gxl.*;
import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.xml.sax.SAXException;
import utils.GraphUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Class used to import graphs saved in GXL format.
 */
final class GXLImporter {

    /**
     * Set of seen IDs to ensure uniqueness of IDs given to GraphStream Graph elements.
     */
    private static Set<String> ids = new HashSet<>();
    /**
     * Counter used to guarantee unique IDs for GraphStream Graph elements.
     */
    private static int idcounter = 0;
    /**
     * List of file extensions accepted by this importer.
     */
    private static final List<String> acceptslist = Arrays.asList("gxl", "gst", "gpl", "gst", "gpr", "gty");

    /**
     * Returns whether an extension is accepted by this importer.
     *
     * @param ext File extension to verify.
     * @return <tt>true</tt> if the file extension is accepted.
     */
    static boolean acceptsExtension(String ext) {
        return acceptslist.contains(ext.toLowerCase());
    }

    static Graph read(String path, boolean addUnderscores) throws IOException, SAXException {
        try {
            return read(path, addUnderscores, false);
        } catch (EdgeRejectedException e) {
            return read(path, addUnderscores, true);
        }
    }

    /**
     * Reads a file in GXL format into a GraphStream graph Object.
     *
     * @param path           Path to the file to read into a GraphsStream Graph Object.
     * @param addUnderscores <tt>true</tt> if underscores are to be added to the IDs of the read graph.
     * @return A GraphStream Graph Object containing the graph represented in the file.
     * @throws IOException  Thrown when the file could not be read.
     * @throws SAXException Thrown when the file contains incorrect syntax.
     */
    static Graph read(String path, boolean addUnderscores, boolean multigraph) throws IOException, SAXException {
        ids.clear();
        idcounter = 0;
        String underscore = "";
        if (addUnderscores) {
            underscore = GraphUtils.ILLEGAL_PREFIX;
        }
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        String gxml = new String(encoded, "UTF-8");
        gxml = gxml.replaceAll(" xmlns=\"http://www.gupro.de/GXL/gxl-1.0.dtd\"", "");

        gxml = removeDupAttr(gxml);

        GXLDocument doc = new GXLDocument(new ByteArrayInputStream(gxml.getBytes(StandardCharsets.UTF_8)));
        GXLGXL a = doc.getDocumentElement();
        GXLGraph graph = a.getGraphAt(0);

        Graph tograph;
        if (multigraph) {
            tograph = new MultiGraph(underscore + graph.getAttribute("id"), true, false);
        } else {
            tograph = new SingleGraph(underscore + graph.getAttribute("id"), true, false);
        }

        for (int p = 0; p < graph.getAttrCount(); p++) {
            GXLValue content = (graph.getAttrAt(p)).getValue();
            tograph.setAttribute(graph.getAttrAt(p).getName(), getFromGXLValue(content, true));

        }


        boolean directed = graph.getAttribute("edgemode").equals("directed");

        List<GXLGraphElement> nodes = new LinkedList<>();
        List<GXLGraphElement> edges = new LinkedList<>();
        for (int i = 0; i < graph.getGraphElementCount(); i++) {
            GXLGraphElement elem = graph.getGraphElementAt(i);
            if (elem instanceof GXLNode) {
                nodes.add(elem);
            } else {
                edges.add(elem);
            }
        }
        for (GXLGraphElement elem : nodes) {
            String id = getID(elem, addUnderscores);
            Node n = tograph.addNode(id);
            for (int p = 0; p < elem.getAttrCount(); p++) {
                GXLValue content = (elem.getAttrAt(p)).getValue();
                n.setAttribute(elem.getAttrAt(p).getName(), getFromGXLValue(content, true));
            }
        }
        for (GXLGraphElement elem : edges) {
            String id = getID(elem, addUnderscores);
            Edge e = tograph.addEdge(id, underscore + elem.getAttribute("from"), underscore + elem.getAttribute("to"), directed);
            for (int p = 0; p < elem.getAttrCount(); p++) {
                GXLValue content = (elem.getAttrAt(p)).getValue();
                e.setAttribute(elem.getAttrAt(p).getName(), getFromGXLValue(content, true));
            }
        }
        return tograph;
    }

    /**
     * If a gxl file contains the version attribute two times, removes one of the two times.
     *
     * @param gxml String containing the gxl file.
     * @return A String with no duplicate version attributes.
     */
    private static String removeDupAttr(String gxml) {
        int index1 = gxml.indexOf("<attr name=\"$version\">");
        int index2 = gxml.indexOf("<attr name=\"$version\">", index1 + 1);
        if (index2 == -1) {
            return gxml;
        }
        int end = gxml.indexOf("</attr>", index2);
        return removeDupAttr(gxml.substring(0, index2) + gxml.substring(end + 6));
    }

    /**
     * Returns a String or List read from a GXLValue.
     *
     * @param in GXLValue to be read from.
     * @return String or List Object, depending on whether it's an atomic or composite GXLValue.
     */
    private static Object getFromGXLValue(GXLValue in, boolean addQuotes) {
        String quotes = addQuotes ? "\"" : "";
        if (in instanceof GXLAtomicValue) {
            return quotes + ((GXLAtomicValue) in).getValue() + quotes;
        } else if (in instanceof GXLCompositeValue) {
            List<Object> res = new LinkedList<>();
            GXLCompositeValue a = (GXLCompositeValue) in;
            for (int i = 0; i < a.getValueCount(); i++) {
                res.add(getFromGXLValue(a.getValueAt(i), addQuotes));
            }
            return res;
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Given a graph element, return its ID as specified in GXL or a generated one if not specified.
     *
     * @param in            Graph element of which to return an ID.
     * @param addUnderscore <tt>true</tt> if underscores should be added to IDs.
     * @return A unique String to be used as identifier.
     */
    private static String getID(GXLGraphElement in, boolean addUnderscore) {
        String underscore = "";
        if (addUnderscore) {
            underscore = GraphUtils.ILLEGAL_PREFIX;
        }
        String idgotten = in.getAttribute("id");
        if (idgotten != null && !ids.contains(underscore + idgotten)) {
            ids.add(underscore + idgotten);
            return underscore + idgotten;
        }
        String prefix = "UNKNOWN";
        if (in instanceof GXLNode) {
            prefix = "n";
        } else if (in instanceof GXLEdge) {
            prefix = "e";
        }
        while (ids.contains(underscore + prefix + "ID?" + idcounter)) {
            idcounter++;
        }
        idgotten = underscore + prefix + "ID?" + idcounter;
        ids.add(idgotten);
        return idgotten;
    }
}
