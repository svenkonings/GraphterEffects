package net.sourceforge.gxl.graphloader;

import it.unibo.tuprolog.core.Struct;
import net.sourceforge.gxl.*;
import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.github.meteoorkip.prolog.TuProlog.atom;
import static com.github.meteoorkip.utils.GraphUtils.ILLEGAL_PREFIX;

/**
 * Class used to import graphs saved in GXL format.
 */
@SuppressWarnings("WeakerAccess")
public final class GXLImporter {

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
    private static final List<String> acceptslist = Arrays.asList("gxl", "gst", "gpl", "gpr", "gty");

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
     *
     * @param file      {@link File} to read into a {@link Graph}.
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
     *
     * @param path      Path to the file to read into a {@link Graph}.
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
     * Reads a file in GXL format into a {@link Graph}.
     *
     * @param path      Path to the file to read into a {@link Graph}.
     * @param addprefix <tt>true</tt> if an illegal prefix is to be added to the IDs of the read graph.
     * @param multigraph <tt>true</tt> if the graph may have multiple edges with the same source and target
     * @return {@link Graph} containing the graph represented in the file.
     * @throws IOException  Thrown when the file could not be read.
     * @throws SAXException Thrown when the file contains incorrect syntax.
     */
    public static Graph read(String path, boolean addprefix, boolean multigraph) throws IOException, SAXException {
        ids.clear();
        idcounter = 0;
        String prefix = "";
        if (addprefix) {
            prefix = ILLEGAL_PREFIX;
        }
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        String gxml = new String(encoded, StandardCharsets.UTF_8);
        gxml = gxml.replaceAll(" xmlns=\"http://www.gupro.de/GXL/gxl-1.0.dtd\"", "");

        gxml = removeDupAttr(gxml);

        GXLDocument doc = new GXLDocument(new ByteArrayInputStream(gxml.getBytes(StandardCharsets.UTF_8)));
        GXLGXL a = doc.getDocumentElement();
        GXLGraph graph = a.getGraphAt(0);

        Graph tograph;
        if (multigraph) {
            tograph = new MultiGraph(ILLEGAL_PREFIX + prefix + graph.getAttribute("id"), true, false);
        } else {
            tograph = new SingleGraph(ILLEGAL_PREFIX + prefix + graph.getAttribute("id"), true, false);
        }

        for (int p = 0; p < graph.getAttrCount(); p++) {
            GXLValue content = (graph.getAttrAt(p)).getValue();
            tograph.setAttribute(graph.getAttrAt(p).getName(), getFromGXLValue(content));

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
            Node n = tograph.addNode(ILLEGAL_PREFIX + id);
            for (int p = 0; p < elem.getAttrCount(); p++) {
                GXLValue content = (elem.getAttrAt(p)).getValue();
                n.setAttribute(elem.getAttrAt(p).getName(), getFromGXLValue(content));
            }
        }
        for (GXLGraphElement elem : edges) {
            String id = getID(elem, addprefix);
            Edge e = tograph.addEdge(ILLEGAL_PREFIX + id, ILLEGAL_PREFIX + prefix + elem.getAttribute("from"), ILLEGAL_PREFIX + prefix + elem.getAttribute("to"), directed);
            for (int p = 0; p < elem.getAttrCount(); p++) {
                GXLValue content = (elem.getAttrAt(p)).getValue();
                e.setAttribute(elem.getAttrAt(p).getName(), getFromGXLValue(content));
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
     * Returns a {@link String} or {@link List} read from a {@link GXLValue}.
     *
     * @param in {@link GXLValue} to be read from.
     * @return String or List Object, depending on whether it's an atomic or composite GXLValue.
     */
    private static Struct getFromGXLValue(GXLValue in) {
        if (in instanceof GXLAtomicValue) {
            return atom(((GXLAtomicValue) in).getValue());
        } else if (in instanceof GXLCompositeValue) {
            it.unibo.tuprolog.core.List res = it.unibo.tuprolog.core.List.empty();
            GXLCompositeValue a = (GXLCompositeValue) in;
            for (int i = 0; i < a.getValueCount(); i++) {
                res.addLast(getFromGXLValue(a.getValueAt(i)));
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
            underscore = ILLEGAL_PREFIX;
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
