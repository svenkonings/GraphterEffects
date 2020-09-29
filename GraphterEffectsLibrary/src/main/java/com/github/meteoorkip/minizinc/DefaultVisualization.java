package com.github.meteoorkip.minizinc;

import com.github.meteoorkip.graphloader.Importer;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Generates a Minizinc file of the default visualization of a graph
 */
public class DefaultVisualization {

    public static String generate(Graph graph) {
        StringBuilder lines = new StringBuilder();

        // node(X) -> shape(X,ellipse), size(X,10).
        for (Node x : graph.getEachNode()) {
            addLine(lines, "var int : width_%1$s = 10", nodeId(x));
            addLine(lines, "var int : height_%1$s = 10", nodeId(x));

            addLine(lines, "var int : radiusX_%1$s = width_%1$s div 2", nodeId(x));
            addLine(lines, "var int : radiusY_%1$s = height_%1$s div 2", nodeId(x));

            addLine(lines, "var 0..2000 : minX_%1$s", nodeId(x));
            addLine(lines, "var int : centerX_%1$s = minX_%1$s + radiusX_%1$s", nodeId(x));
            addLine(lines, "var int : maxX_%1$s = minX_%1$s + width_%1$s", nodeId(x));

            addLine(lines, "var 0..2000 : minY_%1$s", nodeId(x));
            addLine(lines, "var int : centerY_%1$s = minY_%1$s + radiusY_%1$s", nodeId(x));
            addLine(lines, "var int : maxY_%1$s = minY_%1$s + height_%1$s", nodeId(x));
        }

        // node(X), node(Y) -> distance(X,Y,1).
        for (Node x : graph.getEachNode()) {
            for (Node y : graph.getEachNode()) {
                if (x != y) {
                    addLine(lines, "constraint ((minX_%1$s >= maxX_%2$s) /\\ ((minX_%1$s - maxX_%2$s) >= 1)) \\/ ((minX_%2$s >= maxX_%1$s) /\\ ((minX_%2$s - maxX_%1$s) >= 1))", nodeId(x), nodeId(y));
                    addLine(lines, "constraint ((minY_%1$s >= maxY_%2$s) /\\ ((minY_%1$s - maxY_%2$s) >= 1)) \\/ ((minY_%2$s >= maxY_%1$s) /\\ ((minY_%2$s - maxY_%1$s) >= 1))", nodeId(x), nodeId(y));
                }
            }
        }

        // edge(S,T) -> line(S,T).
        for (Edge e : graph.getEachEdge()) {
            Node s = e.getSourceNode();
            Node t = e.getTargetNode();

            addLine(lines, "var int : x1_%1$s = centerX_%2$s", edgeId(e), nodeId(s));
            addLine(lines, "var int : y1_%1$s = centerY_%2$s", edgeId(e), nodeId(s));
            addLine(lines, "var int : x2_%1$s = centerX_%2$s", edgeId(e), nodeId(t));
            addLine(lines, "var int : y2_%1$s = centerY_%2$s", edgeId(e), nodeId(t));
            
            addLine(lines, "var int : minX_%1$s = min(x1_%1$s, x2_%1$s)", edgeId(e));
            addLine(lines, "var int : centerX_%1$s = (x1_%1$s + x2_%1$s) div 2", edgeId(e));
            addLine(lines, "var int : maxX_%1$s = max(x1_%1$s, x2_%1$s)", edgeId(e));

            addLine(lines, "var int : minY_%1$s = min(y1_%1$s, y2_%1$s)", edgeId(e));
            addLine(lines, "var int : centerY_%1$s = (y1_%1$s + y2_%1$s) div 2", edgeId(e));
            addLine(lines, "var int : maxY_%1$s = max(y1_%1$s, y2_%1$s)", edgeId(e));
        }

        return lines.toString();
    }

    private static String nodeId(Node node) {
        return "n" + node.getIndex();
    }
    
    private static String edgeId(Edge edge) {
        return "e" + edge.getIndex();
    }

    private static void addLine(StringBuilder stringBuilder, String line, String... ids) {
        stringBuilder.append(String.format(line, (Object[]) ids)).append(";\n");
    }

    public static void main(String[] args) throws IOException, SAXException {
        Graph graph = Importer.graphFromFile(args[0]);
        System.out.println(generate(graph));
    }
}
