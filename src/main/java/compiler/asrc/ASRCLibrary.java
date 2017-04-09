package compiler.asrc;


import alice.tuprolog.*;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import utils.GraphUtils;
import utils.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static compiler.prolog.TuProlog.struct;
import static compiler.prolog.TuProlog.intVal;
import static org.junit.Assert.assertTrue;


public class ASRCLibrary extends GraphLibrary {


    public ASRCLibrary(Graph g) {
        super(g);
    }

    @Override
    public String getTheory() {
        StringBuilder sb = new StringBuilder();
        sb.append("graph(\"").append(graph.getId()).append("\").\n");
        for (Node n : graph.getEachNode()) {
            sb.append("node(\"").append(n.getId()).append("\").\n");
        }
        for (Edge n : graph.getEachEdge()) {
            sb.append("edge(\"").append(n.getId()).append("\").\n");
            sb.append("edge(\"").append(n.getSourceNode().getId()).append("\", \"").append(n.getTargetNode().getId()).append("\").\n");
            sb.append("edge(\"").append(n.getSourceNode().getId()).append("\", \"").append(n.getTargetNode().getId()).append("\", \"").append(n.getId()).append("\").\n");
        }
        return sb.toString();
    }

    public boolean directed_1(Struct ID) {
        return bool(ID, GraphUtils::isDirectedGeneral, false, true, true);
    }

    public boolean undirected_1(Struct ID) {
        return bool(ID, GraphUtils::isUnDirectedGeneral, false, true, true);
    }

    public boolean mixed_1(Struct ID) {
        return !directed_1(ID) && !undirected_1(ID);
    }

    public boolean edgecount_2(Struct ID, Term count) {
        return numeric(ID, count, n -> ((Graph)n).getEdgeCount(), false, false, true);
    }

    public boolean singlegraph_1(Term ID) {
        return bool((Struct) ID.getTerm(), n -> n instanceof SingleGraph, false, false, true);
    }

    public boolean multigraph_1(Term ID) {
        return bool((Struct) ID.getTerm(), n -> n instanceof MultiGraph, false, false, true);
    }

    public boolean nodecount_2(Struct ID, Term count) {
        return numeric(ID, count, n -> ((Graph)n).getNodeCount(), false, false, true);
    }

    public boolean componentcount_2(Struct ID, Term count) {
        return numeric(ID, count, n -> GraphUtils.ConnectedComponentsCount(((Graph) n)), false, false, true);
    }

    public boolean attributecount_2(Struct ID, Term count) {
        return numeric(ID, count, Element::getAttributeCount, true, true, true);
    }

    public boolean isconnected_1(Struct ID) {
        return componentcount_2(ID, intVal(1)) || componentcount_2(ID, intVal(0));
    }

    public boolean degree_2(Struct ID, Term count) {
        return numeric(ID, count, n -> ((Node)n).getDegree(), true, false, false);
    }

    public boolean indegree_2(Struct ID, Term count) {
        return numeric(ID, count, n -> ((Node)n).getInDegree(), true, false, false);
    }

    public boolean outdegree_2(Struct ID, Term count) {
        return numeric(ID, count, n -> ((Node)n).getOutDegree(), true, false, false);
    }
    public boolean neighbourcount_2(Struct ID, Term count) {
        return numeric(ID, count, n -> GraphUtils.neighbourCount((Node)n), true, false, false);
    }

    public boolean label_2(Term ID, Term label) {
        return attribute_3(ID, struct("label"), label);
    }

    public boolean incomponent_2(Term ID, Term component) {
        GraphUtils.initComponentCount(graph);
        return attribute_3(ID, struct("_ATTRIBUTE_DETERMINING_WHICH_COMPONENT_THE_NODE_BELONGS_TO_"), component);
    }

    public boolean inmst_1(Term ID) {
        try {
            return bool((Struct) ID.getTerm(), n -> GraphUtils.getMST(graph).contains(n), false, true, false);
        } catch (Exception | AssertionError e) {
            e.printStackTrace();
            return false;
        }

    }






}
