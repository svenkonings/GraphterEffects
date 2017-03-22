package gxltest;

import net.sourceforge.gxl.*;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.xml.sax.SAXException;
import utils.Printer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static java.lang.System.out;

/**
 * Created by poesd_000 on 21/03/2017.
 */
public class GXLTest {


    public static void main(String[] args) throws IOException, SAXException {


        byte[] encoded = Files.readAllBytes(Paths.get("tictactoe.gps/move.gpr"));
        String gxml = new String(encoded, "UTF-8");
        gxml = gxml.replaceAll(" xmlns=\"http://www.gupro.de/GXL/gxl-1.0.dtd\"", "");


        GXLDocument doc = new GXLDocument(new ByteArrayInputStream(gxml.getBytes(StandardCharsets.UTF_8)));
        GXLGXL a = doc.getDocumentElement();
        GXLGraph graph = a.getGraphAt(0);

        MultiGraph tograph = new MultiGraph(graph.getAttribute("id"), false, false);

        boolean directed = graph.getAttribute("edgemode").equals("directed");

        for (int i = 0; i < graph.getGraphElementCount(); i++) {
            GXLGraphElement elem = graph.getGraphElementAt(i);
            String id = getID(elem);
            if (elem instanceof GXLNode) {
                Node n = tograph.addNode(id);
                for (int p = 0; p < elem.getAttrCount(); p++) {
                    n.setAttribute(elem.getAttrAt(p).getName(), ((GXLAtomicValue) (elem.getAttrAt(p)).getValue()).getValue());
                }
            } else if (elem instanceof GXLEdge) {
                Edge e = tograph.addEdge(id, elem.getAttribute("from"), elem.getAttribute("to"), directed);
                for (int p = 0; p < elem.getAttrCount(); p++) {
                    try {
                        e.setAttribute(elem.getAttrAt(p).getName(), ((GXLAtomicValue) (elem.getAttrAt(p)).getValue()).getValue());
                    } catch (NullPointerException ex) {
                        System.out.println(id);
                    }
                }
            }
        }
        Printer.pprint(tograph);
    }


    private static String getElementName(GXLValue value) throws InvocationTargetException, IllegalAccessException {
        Class c = GXLElement.class;
        Method[] ms = c.getDeclaredMethods();
        for (Method each : ms) {
            String methodName = each.getName();
            if (!methodName.equals("getElementName")) {
                continue;
            }
            each.setAccessible(true); // this is the key
            out.println(each.invoke(value, new Object[]{}));
        }
        return null;

    }


    private static Set<String> ids = new HashSet<>();
    private static int idcounter = 0;

    private static String getID(GXLGraphElement in) {
        String idgotten = in.getAttribute("id");
        if (idgotten != null && !ids.contains(idgotten)) {
            ids.add(idgotten);
            return idgotten;
        }
        while (ids.contains("ID?" + idcounter)) {
            idcounter++;
        }
        idgotten = "ID?" + idcounter;
        ids.add(idgotten);
        return idgotten;
    }


}
