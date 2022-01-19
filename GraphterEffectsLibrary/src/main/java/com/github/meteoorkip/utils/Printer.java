package com.github.meteoorkip.utils;

import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Class used to print textual representations into the console.
 */
public final class Printer {

    /**
     * Prints a user-friendly text representation of a Graph into the console.
     *
     * @param g Graph to be printed.
     */
    public static void pprint(Graph g) {

        StringBuilder builder = new StringBuilder();
        builder.append(g.toString());
        builder.append("\t{");

        final StringBuilder attrstring = new StringBuilder();
        g.attributeKeys().forEach(key -> attrstring.append("\"").append(key).append("\":\"").append(g.getAttribute(key)).append("\","));
        if (attrstring.toString().endsWith(",")) {
            builder.append(attrstring.substring(0, attrstring.length() - 1)).append("}\n");
        } else {
            builder.append(attrstring).append("}\n");
        }

        List<Element> nodeset = g.nodes().sorted((o1, o2) -> StringUtils.compareStrings(o1.getId(), o2.getId())).collect(Collectors.toCollection(LinkedList::new));
        builder.append("NODES\n");
        addfromlist(builder, nodeset);
        List<Element> edgeset = g.edges().sorted((o1, o2) -> StringUtils.compareStrings(o1.getId(), o2.getId())).collect(Collectors.toCollection(LinkedList::new));
        builder.append("EDGES\n");
        addfromlist(builder, edgeset);
        System.out.println(builder);
    }


    /**
     * Adds a String representation of elements of a graph to a Stringbuilder.
     * @param builder     Builder to add the String representation to.
     * @param list        List of elements of which the String representation needs to be added.
     */
    private static void addfromlist(StringBuilder builder, List<Element> list) {
        for (Element e : list) {
            builder.append("\t").append(e);
            builder.append("\t{");
            final StringBuilder attrstring = new StringBuilder();
            e.attributeKeys().forEach(key -> attrstring.append(key).append(":").append(e.getAttribute(key)).append(","));
            if (attrstring.toString().endsWith(",")) {
                builder.append(attrstring.substring(0, attrstring.length() - 1)).append("}\n");
            } else {
                builder.append(attrstring).append("}\n");
            }
        }
    }
}
