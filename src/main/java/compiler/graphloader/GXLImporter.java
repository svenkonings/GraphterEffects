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

final class GXLImporter {


    private static Set<String> ids = new HashSet<>();
    private static int idcounter = 0;
    private static final List<String> acceptslist = Arrays.asList("gxl", "gst", "gpl", "gst", "gpr", "gty");

    static boolean acceptsExtension(String ext) {
        return acceptslist.contains(ext.toLowerCase());
    }


    static MultiGraph read(File file) throws IOException, SAXException {
        return read(file.getAbsolutePath());
    }

    static MultiGraph read(String path) throws IOException, SAXException {
        return read(path, false);
    }

    static MultiGraph read(String path, boolean addUnderscores) throws IOException, SAXException {
        ids.clear();
        idcounter = 0;
        String underscore = "";
        if (addUnderscores) {
            underscore = "_";
        }
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        String gxml = new String(encoded, "UTF-8");
        gxml = gxml.replaceAll(" xmlns=\"http://www.gupro.de/GXL/gxl-1.0.dtd\"", "");

        gxml = removeDupAttr(gxml);

        GXLDocument doc = new GXLDocument(new ByteArrayInputStream(gxml.getBytes(StandardCharsets.UTF_8)));
        GXLGXL a = doc.getDocumentElement();
        GXLGraph graph = a.getGraphAt(0);

        MultiGraph tograph = new MultiGraph(underscore + graph.getAttribute("id"), true, false);
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
                n.setAttribute(elem.getAttrAt(p).getName(), "\"" + getFromGXLValue(content) + "\"");
            }
        }

        for (GXLGraphElement elem : edges) {
            String id = getID(elem, addUnderscores);
            Edge e = tograph.addEdge(id, underscore + elem.getAttribute("from"), underscore + elem.getAttribute("to"), directed);
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

    private static Object getFromGXLValue(GXLValue in) {
        if (in instanceof GXLAtomicValue) {
            return ((GXLAtomicValue) in).getValue();
        } else if (in instanceof GXLCompositeValue) {
            List<Object> res = new LinkedList<>();
            GXLCompositeValue a = (GXLCompositeValue) in;
            for (int i = 0; i<a.getValueCount(); i++) {
                res.add(getFromGXLValue(a.getValueAt(i)));
            }
            return res;
        }
        throw new UnsupportedOperationException();
    }

    private static String getID(GXLGraphElement in, boolean addUnderscore) {
        String underscore = "";
        if (addUnderscore) {
            underscore = "_";
        }
        String idgotten = in.getAttribute("id");
        if (idgotten != null && !ids.contains(underscore + idgotten)) {
            ids.add(underscore + idgotten);
            return underscore + idgotten;
        }
        String prefix = "UNKNOWN";
        if (in instanceof GXLNode) {
            prefix = "n";
        } else if (in instanceof GXLEdge){
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
