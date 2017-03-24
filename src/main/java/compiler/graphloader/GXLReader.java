package compiler.graphloader;

import net.sourceforge.gxl.*;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public final class GXLReader {


    private static Set<String> ids = new HashSet<>();
    private static int idcounter = 0;
    private static final List<String> acceptslist = Arrays.asList("gxl", "gst", "gpl", "gst", "gpr", "gty");

    public static boolean acceptsExtension(String ext) {
        return acceptslist.contains(ext.toLowerCase());
    }


    public static MultiGraph read(File file) throws IOException, SAXException {
        return read(file.getAbsolutePath());
    }

    public static MultiGraph read(String path) throws IOException, SAXException {
        ids.clear();
        idcounter = 0;
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        String gxml = new String(encoded, "UTF-8");
        gxml = gxml.replaceAll(" xmlns=\"http://www.gupro.de/GXL/gxl-1.0.dtd\"", "");

        gxml = removeDupAttr(gxml);

        GXLDocument doc = new GXLDocument(new ByteArrayInputStream(gxml.getBytes(StandardCharsets.UTF_8)));
        GXLGXL a = doc.getDocumentElement();
        GXLGraph graph = a.getGraphAt(0);

        MultiGraph tograph = new MultiGraph(graph.getAttribute("id"), true, false);
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
            String id = getID(elem);
            Node n = tograph.addNode(id);
            for (int p = 0; p < elem.getAttrCount(); p++) {
                GXLValue content = (elem.getAttrAt(p)).getValue();
                n.setAttribute(elem.getAttrAt(p).getName(), "\"" + getFromGXLValue(content) + "\"");
            }
        }

        for (GXLGraphElement elem : edges) {
            String id = getID(elem);
            Edge e = tograph.addEdge(id, "_" + elem.getAttribute("from"), "_" + elem.getAttribute("to"), directed);
            for (int p = 0; p < elem.getAttrCount(); p++) {
                GXLValue content = (elem.getAttrAt(p)).getValue();
                e.setAttribute(elem.getAttrAt(p).getName(), "\"" + getFromGXLValue(content) + "\"");
            }
        }
        return tograph;
    }

    private static String removeDupAttr(String gxml) {
        int index1 = gxml.indexOf("<attr name=\"$version\">");
        int index2 = gxml.indexOf("<attr name=\"$version\">", index1 + 1);
        if (index2 == -1) {
            return gxml;
        }
        int end = gxml.indexOf("</attr>", index2);
        return removeDupAttr(gxml.substring(0, index2) + gxml.substring(end + 6));
    }

    private static String getFromGXLValue(GXLValue in) {
        if (in instanceof GXLAtomicValue) {
            return ((GXLAtomicValue) in).getValue();
        } else if (in instanceof GXLCompositeValue) {
            List<Object> res = new LinkedList<>();
            GXLCompositeValue a = (GXLCompositeValue) in;
            for (int i = 0; i<a.getValueCount(); i++) {
                res.add(getFromGXLValue(a.getValueAt(i)));
            }
            return res.toString();
        }
        throw new UnsupportedOperationException();
    }

    private static String getID(GXLGraphElement in) {
        String idgotten = in.getAttribute("id");
        if (idgotten != null && !ids.contains("_" + idgotten)) {
            ids.add("_" + idgotten);
            return "_" + idgotten;
        }
        String prefix = "UNKNOWN";
        if (in instanceof GXLNode) {
            prefix = "n";
        } else if (in instanceof GXLEdge){
            prefix = "e";
        }
        while (ids.contains("_" + prefix + "ID?" + idcounter)) {
            idcounter++;
        }
        idgotten = "_" + prefix + "ID?" + idcounter;
        ids.add(idgotten);
        return idgotten;
    }


}
