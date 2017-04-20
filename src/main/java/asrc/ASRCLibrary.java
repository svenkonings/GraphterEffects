package asrc;


import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import prolog.TuProlog;
import utils.GraphUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static prolog.TuProlog.struct;


/**
 * Default library for {@link Graph} predicates.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ASRCLibrary extends GraphLibrary {

    /**
     * Creates a new ASRCLibrary that retrieves information from a given {@link Graph}.
     * @param graph Given {@code Graph}
     */
    public ASRCLibrary(Graph graph) {
        super(graph);
    }

    @Override
    public GraphLibraryLoader getLoader() {
        return ASRCLibrary::new;
    }

    /**
     * Returns the Prolog Theory associated with this library. Contains generative predicates ({@link Graph}/{@link Edge}/{@link Node}) as well as functional predicates.
     * @return The Prolog theory.
     */
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

    /**
     * Returns whether the given object is directed, be it an {@link Edge} or {@link Graph}.
     * @param ID Identifier of the {@link Graph} element.
     * @return Whether the given object is directed.
     */
    public boolean directedsecond_1(Term ID) {
        return bool((Struct) ID.getTerm(), GraphUtils::isDirectedGeneral, false,true, true);
    }

    /**
     * Returns whether the given object is undirected, be it an {@link Edge} or {@link Graph}.
     * @param ID Identifier of the {@link Graph} element.
     * @return Whether the given object is undirected.
     */
    public boolean undirectedsecond_1(Term ID) {
        return bool((Struct) ID.getTerm(), GraphUtils::isUnDirectedGeneral, false,true, true);
    }

    /**
     * Logs the given term, then returns true
     * @param ignore Term to be logged
     * @return true
     */
    @SuppressWarnings("SameReturnValue")
    public boolean println_1(Term ignore) {
        TuProlog.log(ignore.getTerm().toString());
        System.out.println(ignore.getTerm().toString());
        return true;
    }

    /**
     * Returns whether the given {@link Graph} object is mixed.
     * @param ID Identifier of the {@link Graph} element.
     * @return Whether the given {@link Graph} is undirected.
     */
    public boolean mixedsecond_1(Term ID) {
        return directedsecond_1(ID) == undirectedsecond_1(ID);
    }

    /**
     * Returns whether the given {@link Graph} has this {@link Edge} count or unifies otherwise.
     * @param ID Identifier of the {@link Graph}.
     * @param count {@link Edge} count of the {@link Graph}
     * @return Whether unification was possible or the given count was correct.
     */
    public boolean edgecountsecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> ((Graph)n).getEdgeCount(), false, false, true);
    }

    /**
     * Returns whether the given {@link Graph} is a {@link SingleGraph}.
     * @param ID Identifier of the {@link Graph}.
     * @return Whether the {@link Graph} is a {@link SingleGraph}.
     */
    public boolean singlegraphsecond_1(Term ID) {
        return bool((Struct) ID.getTerm(), n -> n instanceof SingleGraph, false, false, true);
    }

    /**
     * Returns whether the given {@link Graph} is a {@link MultiGraph}.
     * @param ID Identifier of the {@link Graph}.
     * @return Whether the {@link Graph} is a {@link MultiGraph}.
     */
    public boolean multigraphsecond_1(Term ID) {
        return bool((Struct) ID.getTerm(), n -> n instanceof MultiGraph, false, false, true);
    }

    /**
     * Returns whether the given {@link Graph} has this {@link Node} count or unifies otherwise.
     * @param ID Identifier of the {@link Graph}.
     * @param count {@link Node} count of the {@link Graph}
     * @return Whether unification was possible or the given count was correct.
     */
    public boolean nodecountsecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> ((Graph)n).getNodeCount(), false, false, true);
    }

    /**
     * Returns whether the given {@link Graph} has this component count or unifies otherwise.
     * @param ID Identifier of the {@link Graph}.
     * @param count Component count of the {@link Graph}
     * @return Whether unification was possible or the given count was correct.
     */
    public boolean componentcountsecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> GraphUtils.ConnectedComponentsCount(((Graph) n)), false, false, true);
    }

    /**
     * Returns whether the given {@link Graph} element has this number of attributes or unifies otherwise.
     * @param ID Identifier of the {@link Graph}.
     * @param count Attribute count of the {@link Graph}
     * @return Whether unification was possible or the given count was correct.
     */
    public boolean attributecountsecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, Element::getAttributeCount, true, true, true);
    }

    /**
     * Returns whether the given {@link Node} has this degree or unifies otherwise.
     * @param ID Identifier of the {@link Node}.
     * @param count Degree of the {@link Node}
     * @return Whether unification was possible or the given degree was correct.
     */
    public boolean degreesecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> ((Node)n).getDegree(), true, false, false);
    }

    /**
     * Returns whether the given {@link Node} has this indegree or unifies otherwise.
     * @param ID Identifier of the {@link Node}.
     * @param count Indegree of the {@link Node}
     * @return Whether unification was possible or the given indegree was correct.
     */
    public boolean indegreesecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> ((Node)n).getInDegree(), true, false, false);
    }

    /**
     * Returns whether the given {@link Node} has this outdegree or unifies otherwise.
     * @param ID Identifier of the {@link Node}.
     * @param count Outdegree of the {@link Node}
     * @return Whether unification was possible or the given outdegree was correct.
     */
    public boolean outdegreesecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> ((Node)n).getOutDegree(), true, false, false);
    }

    /**
     * Returns whether the given {@link Node} has this number of neighbours or unifies otherwise.
     * @param ID Identifier of the {@link Node}.
     * @param count Number of neighbours the {@link Node} has.
     * @return Whether unification was possible or the given number was correct.
     */
    public boolean neighbourcountsecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> GraphUtils.neighbourCount((Node)n), true, false, false);
    }


    /**
     * Returns whether the given {@link Node} is in a specific component of the {@link Graph} or unifies otherwise.
     * @param ID Identifier of the {@link Node}
     * @param component Numeric identifier of the component this {@link Node} is in.
     * @return Whether unification was possible or the given component ID was correct.
     */
    public boolean incomponentsecond_2(Term ID, Term component) {
        GraphUtils.ConnectedComponentsCount(graph);
        return attributesecond_3(ID, struct("_ATTRIBUTE_DETERMINING_WHICH_COMPONENT_THE_NODE_BELONGS_TO_"), component);
    }

    private Dijkstra dijkstra;

    /**
     * Returns whether an {@link Edge} is in the shortest path between two {@link Node} objects or unifies otherwise.
     * @param ID Identifier of the {@link Edge}.
     * @param from Source {@link Node} of the shortest path.
     * @param to Target {@link Node} of the shortest path.
     * @return Whether the given {@link Edge} lies on the shortest path between the two {@link Node} objects.
     */
    public boolean inshortestpathsecond_3(Term ID, Term from, Term to) {
        if (dijkstra == null) {
            dijkstra = new Dijkstra(null, "_ATTRIBUTE_FOR_SHORTEST_PATH_", null);
            dijkstra.init(graph);
        }
        dijkstra.setSource(graph.getNode(((Struct) from.getTerm()).getName()));
        dijkstra.compute();
        Path a = dijkstra.getPath(graph.getNode(((Struct) to.getTerm()).getName()));
        return a.contains((Edge) graph.getEdge(((Struct) ID.getTerm()).getName()));
    }

    /**
     * Returns whether an {@link Edge} is in the minimal spanning tree of the {@link Graph}.
     * @param ID Identifier of the {@link Edge}.
     * @return Whether the {@link Edge} is in the minimal spanning tree of the {@link Graph}.
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean inmstsecond_1(Term ID) {
        try {
            return bool((Struct) ID.getTerm(), n -> GraphUtils.getMST(graph).contains(n), false, true, false);
        } catch (Exception | AssertionError e) {
            e.printStackTrace();
            return false;
        }
    }


    private Map<Element, Integer> indexing;

    /**
     * Returns whether a {@link Element} has the given index or unifies otherwise.
     * @param ID Identifier of the {@link Element}.
     * @param index Index of the {@link Element}.
     * @return Whether the {@link Element} has the given index or unifies otherwise.
     */
    public boolean index_2(Term ID, Term index) {
        if (indexing ==null) {
            indexing = new HashMap<>();
            List<Element> elems = new LinkedList<>(GraphUtils.elements(graph, true, true, true));
            for (int i = 0; i < elems.size(); i++) {
                indexing.put(elems.get(i), i);
            }
        }
        return numeric((Struct) ID.getTerm(), index, n -> indexing.get(n), true, true, true);
    }






}
