package utils;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;


public class Printer {

    public static void pprint(Graph g) {

        StringBuilder builder = new StringBuilder();
        builder.append(g.toString());
        builder.append("\t{");


        String attrstring = "";
        for (String key : g.getAttributeKeySet()) {
            attrstring += "\"" + key + "\":\"" + g.getAttribute(key) + "\",";
        }
        if (attrstring.endsWith(",")) {
            attrstring = attrstring.substring(0, attrstring.length()-1);
        }
        attrstring = attrstring + "}";
        builder.append(attrstring).append("\n");

        List<Node> nodeset = new LinkedList<>(g.getNodeSet());
        Collections.sort(nodeset, (o1, o2) -> o1.getId().compareTo(o2.getId()));

        builder.append("NODES\n");
        for (Node n : nodeset) {
            builder.append("\t").append(n);
            builder.append("\t{");
            attrstring = "";
            for (String key : n.getAttributeKeySet()) {
                attrstring += "\"" + key + "\":\"" + n.getAttribute(key) + "\",";
            }
            if (attrstring.endsWith(",")) {
                attrstring = attrstring.substring(0, attrstring.length()-1);
            }
            attrstring = attrstring + "}";
            builder.append(attrstring).append("\n");
        }

        List<Edge> edgeset = new LinkedList<>(g.getEdgeSet());
        Collections.sort(edgeset, (o1, o2) -> o1.getId().compareTo(o2.getId()));
        builder.append("EDGES\n");
        for (Edge e : edgeset) {
            builder.append("\t").append(e);
            builder.append("\t{");
            attrstring = "";
            for (String key : e.getAttributeKeySet()) {
                attrstring += "\"" + key + "\":\"" + e.getAttribute(key) + "\",";
            }
            if (attrstring.endsWith(",")) {
                attrstring = attrstring.substring(0, attrstring.length()-1);
            }
            attrstring = attrstring + "}";
            builder.append(attrstring).append("\n");
        }



        System.out.println(builder.toString());
    }

}
