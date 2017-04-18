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
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Class used to import graphs saved in GXL format.
 */
@SuppressWarnings("WeakerAccess")
final class GXLImporter {

    /**
     * Set of seen IDs to ensure uniqueness of IDs given to GraphStream Graph elements.
     */
    private static final Set<String> ids = new HashSet<>();
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
    public static boolean acceptsExtension(String ext) {
        return acceptslist.contains(ext.toLowerCase());
    }


    /**
     * Reads a {@link File} in GXL format into a {@link Graph}.
     * @param file {@link File} to read into a {@link Graph}.
     * @param addPrefix <tt>true</tt> if an illegal prefix is to be added to the IDs of the read graph.
     * @return {@link Graph} containing the graph represented in the file.
     * @throws IOException  Thrown when the file could not be read.
     * @throws SAXException Thrown when the file contains incorrect syntax.
     */
    public static Graph read(File file, boolean addPrefix) throws IOException, SAXException {
        return read(file.getAbsolutePath(), addPrefix);
    }

    /**
     * Reads a file in GXL format into a {@link Graph}.
     * @param path Path to the file to read into a {@link Graph}.
     * @param addPrefix <tt>true</tt> if an illegal prefix is to be added to the IDs of the read graph.
     * @return {@link Graph} containing the graph represented in the file.
     * @throws IOException  Thrown when the file could not be read.
     * @throws SAXException Thrown when the file contains incorrect syntax.
     */
    public static Graph read(String path, boolean addPrefix) throws IOException, SAXException {
        try {
            return read(path, addPrefix, false);
        } catch (EdgeRejectedException e) {
            return read(path, addPrefix, true);
        }
    }


    /**
     * Reads a file in GXL format into a GraphStream graph Object.
     *
     * @param file File to read into a GraphsStream Graph Object.
     * @return A GraphStream Graph Object containing the graph represented in the file.
     * @throws IOException  Thrown when the file could not be read.
     * @throws SAXException Thrown when the file contains incorrect syntax.
     */
    public static Graph read(File file, boolean addUnderscores, boolean multigraph) throws IOException, SAXException {
        return read(file.getAbsolutePath(), addUnderscores, multigraph);
    }

    /**
     * Reads a file in GXL format into a {@link Graph}.
     *
     * @param path           Path to the file to read into a {@link Graph}.
     * @param addprefix <tt>true</tt> if an illegal prefix is to be added to the IDs of the read graph.
     * @return {@link Graph} containing the graph represented in the file.
     * @throws IOException  Thrown when the file could not be read.
     * @throws SAXException Thrown when the file contains incorrect syntax.
     */
    public static Graph read(String path, boolean addprefix, boolean multigraph) throws IOException, SAXException {
        ids.clear();
        idcounter = 0;
        String prefix = "";
        if (addprefix) {
            prefix = GraphUtils.ILLEGAL_PREFIX;
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
            tograph = new MultiGraph(prefix + graph.getAttribute("id"), true, false);
        } else {
            tograph = new SingleGraph(prefix + graph.getAttribute("id"), true, false);
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
            } else if (elem instanceof GXLEdge) {
                edges.add(elem);
            } else {
                throw new RuntimeException();
            }
        }
        for (GXLGraphElement elem : nodes) {
            String id = getID(elem, addprefix);
            Node n = tograph.addNode(id);
            for (int p = 0; p < elem.getAttrCount(); p++) {
                GXLValue content = (elem.getAttrAt(p)).getValue();
                n.setAttribute(elem.getAttrAt(p).getName(), getFromGXLValue(content, true));
            }
        }
        for (GXLGraphElement elem : edges) {
            String id = getID(elem, addprefix);
            Edge e = tograph.addEdge(id, prefix + elem.getAttribute("from"), prefix + elem.getAttribute("to"), directed);
            for (int p = 0; p < elem.getAttrCount(); p++) {
                GXLValue content = (elem.getAttrAt(p)).getValue();
                e.setAttribute(elem.getAttrAt(p).getName(), getFromGXLValue(content, true));
            }
        }
        GXLAttr version = graph.getAttr("$version");
        if (version != null && "curly".equals(getFromGXLValue(version.getValue(), false))) {
            tograph = Grooveify(tograph);
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
     * Returns a {@link String} or {@link List} read from a {@link GXLValue}.
     *
     * @param in {@link GXLValue} to be read from.
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

    private static Graph Grooveify(Graph input) {
        Graph res = GraphUtils.newGraphWithSameType(input);
        for (String key : input.getAttributeKeySet()) {
            res.setAttribute(key, (Object) input.getAttribute(key));
        }
        for (Node node : input.getEachNode()) {
            Node added = res.addNode(node.getId());
            for (String key : node.getAttributeKeySet()) {
                added.setAttribute(key, (Object) node.getAttribute(key));
            }
        }
        for (Edge edge : input.getEachEdge()) {
            if (edge.getSourceNode().equals(edge.getTargetNode())) {
                String label = edge.getAttribute("label");
                if (label == null) {
                    continue;
                }
                label = label.substring(1, label.length() - 1);
                String[] attribute = label.split(":", 2);
                if (attribute.length == 1) {
                    res.getNode(edge.getSourceNode().getId()).setAttribute("label", "\"" + attribute[0] + "\"");
                } else if (attribute.length == 2) {
                    // FIXME: Should support type imports instead
                    if ("type".equals(attribute[0])) {
                        attribute[0] = "label";
                    }
                    res.getNode(edge.getSourceNode().getId()).setAttribute(attribute[0], "\"" + attribute[1] + "\"");
                }
            } else {
                Edge added = res.addEdge(edge.getId(), edge.getSourceNode().getId(), edge.getTargetNode().getId(), edge.isDirected());
                for (String key : edge.getAttributeKeySet()) {
                    added.setAttribute(key, (Object) edge.getAttribute(key));
                }
            }
        }
        return res;
    }
}
