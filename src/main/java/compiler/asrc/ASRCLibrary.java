package compiler.asrc;


import alice.tuprolog.*;
import compiler.prolog.TuProlog;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import utils.GraphUtils;

import static compiler.prolog.TuProlog.*;
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
        sb.append("undirected(X) :- graph(X), undirectedsecond(X).\n");
        sb.append("undirected(X) :- edge(X), undirectedsecond(X).\n");
        sb.append("directed(X) :- graph(X), directedsecond(X).\n");
        sb.append("directed(X) :- edge(X), directedsecond(X).\n");
        sb.append("mixed(X) :- graph(X), mixedsecond(X).\n");
        sb.append("edgecount(X, Y) :- graph(X), edgecountsecond(X, Y).\n");
        sb.append("nodecount(X, Y) :- graph(X), nodecountsecond(X, Y).\n");
        sb.append("componentcount(X, Y) :- graph(X), componentcountsecond(X, Y).\n");
        sb.append("attributecount(X, Y) :- graph(X), attributecountsecond(X, Y).\n");
        sb.append("attributecount(X, Y) :- node(X), attributecountsecond(X, Y).\n");
        sb.append("attributecount(X, Y) :- edge(X), attributecountsecond(X, Y).\n");
        sb.append("singlegraph(X) :- graph(X), singlegraphsecond(X).\n");
        sb.append("multigraph(X) :- graph(X), multigraphsecond(X).\n");
        sb.append("isconnected(X) :- componentcount(X, 0) ; componentcount(X, 1).\n");
        sb.append("degree(X, Y) :- node(X), degreesecond(X, Y).\n");
        sb.append("indegree(X, Y) :- node(X), indegreesecond(X, Y).\n");
        sb.append("outdegree(X, Y) :- node(X), outdegreesecond(X, Y).\n");
        sb.append("neighbourcount(X, Y) :- node(X), neighbourcountsecond(X, Y).\n");
        sb.append("attribute(X, Y, Z) :- graph(X), attributesecond(X, Y, Z).\n");
        sb.append("attribute(X, Y, Z) :- node(X), attributesecond(X, Y, Z).\n");
        sb.append("attribute(X, Y, Z) :- edge(X), attributesecond(X, Y, Z).\n");
        sb.append("label(X, Y) :- node(X), attribute(X, \"label\", Y).\n");
        sb.append("label(X, Y) :- graph(X), attribute(X, \"label\", Y).\n");
        sb.append("label(X, Y) :- edge(X), attribute(X, \"label\", Y).\n");
        sb.append("flag(X, Y) :- node(X), attribute(X, \"flag\", Y).\n");
        sb.append("flag(X, Y) :- graph(X), attribute(X, \"flag\", Y).\n");
        sb.append("flag(X, Y) :- edge(X), attribute(X, \"flag\", Y).\n");
        sb.append("type(X, Y) :- node(X), attribute(X, \"type\", Y).\n");
        sb.append("type(X, Y) :- graph(X), attribute(X, \"type\", Y).\n");
        sb.append("type(X, Y) :- edge(X), attribute(X, \"type\", Y).\n");
        sb.append("incomponent(X, Y) :- node(X), incomponentsecond(X, Y).\n");
        sb.append("inmst(X) :- edge(X), inmstsecond(X).\n");
        sb.append("inshortestpath(X,Y,Z) :- edge(X), node(Y), node(Z), inshortestpathsecond(X,Y,Z).\n");
        return sb.toString();
    }

    public boolean directedsecond_1(Term ID) {
        return bool((Struct) ID.getTerm(), GraphUtils::isDirectedGeneral, false, true, true);
    }

    public boolean undirectedsecond_1(Term ID) {
        return bool((Struct) ID.getTerm(), GraphUtils::isUnDirectedGeneral, false, true, true);
    }

    public boolean println_1(Term ignore) {
        TuProlog.log(ignore.getTerm().toString());
        return true;
    }

    public boolean mixedsecond_1(Term ID) {
        return directedsecond_1(ID) == undirectedsecond_1(ID);
    }

    public boolean edgecountsecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> ((Graph)n).getEdgeCount(), false, false, true);
    }

    public boolean singlegraphsecond_1(Term ID) {
        return bool((Struct) ID.getTerm(), n -> n instanceof SingleGraph, false, false, true);
    }

    public boolean multigraphsecond_1(Term ID) {
        return bool((Struct) ID.getTerm(), n -> n instanceof MultiGraph, false, false, true);
    }

    public boolean nodecountsecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> ((Graph)n).getNodeCount(), false, false, true);
    }

    public boolean componentcountsecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> GraphUtils.ConnectedComponentsCount(((Graph) n)), false, false, true);
    }

    public boolean attributecountsecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, Element::getAttributeCount, true, true, true);
    }

    public boolean degreesecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> ((Node)n).getDegree(), true, false, false);
    }

    public boolean indegreesecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> ((Node)n).getInDegree(), true, false, false);
    }

    public boolean outdegreesecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> ((Node)n).getOutDegree(), true, false, false);
    }
    public boolean neighbourcountsecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> GraphUtils.neighbourCount((Node)n), true, false, false);
    }

    public boolean incomponentsecond_2(Term ID, Term component) {
        GraphUtils.initComponentCount(graph);
        return attributesecond_3(ID, struct("_ATTRIBUTE_DETERMINING_WHICH_COMPONENT_THE_NODE_BELONGS_TO_"), component);
    }

    public boolean inshortestpathsecond_3(Term ID, Term from, Term to) {
        try {
            Dijkstra dijkstra = new Dijkstra(null, "_ATTRIBUTE_FOR_SHORTEST_PATH_", null);
            dijkstra.init(graph);
            dijkstra.setSource(graph.getNode(((Struct) from.getTerm()).getName()));
            dijkstra.compute();
            Path a = dijkstra.getPath(graph.getNode(((Struct) to.getTerm()).getName()));
            return a.contains((Edge) graph.getEdge(((Struct) ID.getTerm()).getName()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean inmstsecond_1(Term ID) {
        try {
            return bool((Struct) ID.getTerm(), n -> GraphUtils.getMST(graph).contains(n), false, true, false);
        } catch (Exception | AssertionError e) {
            e.printStackTrace();
            return false;
        }
    }






}
