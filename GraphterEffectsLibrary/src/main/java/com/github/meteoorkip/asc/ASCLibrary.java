package com.github.meteoorkip.asc;


import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import com.github.meteoorkip.prolog.TuProlog;
import com.github.meteoorkip.utils.GraphUtils;
import com.github.meteoorkip.utils.StringUtils;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;

import static com.github.meteoorkip.prolog.TuProlog.struct;


/**
 * Default library for {@link Graph} predicates.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ASCLibrary extends GraphLibrary {

    /**
     * Creates a new ASCLibrary that retrieves information from a given {@link Graph}.
     *
     * @param graph Given {@code Graph}
     */
    public ASCLibrary(Graph graph) {
        super(graph);
    }

    @Override
    public GraphLibraryLoader getLoader() {
        return ASCLibrary::new;
    }



    private String StringRep(Object term) {
        try {
            if (term instanceof Integer || (term instanceof String && ((String) term).matches("\\d*"))) {
                return term.toString();
            } else if (term instanceof String && ((String) term).matches("\"\\d+\"")) {
                return ((String) term).replaceFirst("\"(\\d+)\"", "$1");
            } else if (term instanceof String[]) {
                String[] res = new String[((String[]) term).length];
                for (int i = 0; i < ((String[]) term).length; i++) {
                        res[i] = StringRep(((String[])term)[i]);
                }
                return Arrays.asList(res).toString();
            } else if (term instanceof String && ((String) term).matches("\".*\"")) {
                return "'" + term + "'";
            } if (term instanceof List) {
                if (((List) term).size()==0) {
                    return "[]";
                } else {
                    if (((List) term).get(0) instanceof String) {
                        return StringRep(((List)term).toArray(new String[0]));
                    } else {
                        throw new RuntimeException("Unknown attribute value type: " + term.getClass());
                    }
                }
            } else {
                throw new RuntimeException("Unknown attribute value type: " + term.getClass());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Returns the Prolog Theory associated with this library. Contains generative predicates ({@link Graph}/{@link
     * Edge}/{@link Node}) as well as functional predicates.
     *
     * @return The Prolog theory.
     */
    @Override
    public String getTheory() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("graph(\"" + graph.getId() + "\").\n");
            for (Node n : graph.getEachNode()) {
                sb.append("node(\"" + n.getId() + "\").\n");
                for (String attrKey : n.getAttributeKeySet()) {
                    sb.append("attribute(\"" + n.getId() + "\", '\"" + attrKey + "\"', " + StringRep(n.getAttribute(attrKey)) + ").\n");
                }
            }
            for (Edge n : graph.getEachEdge()) {
                sb.append("edge(\"" + n.getId() + "\").\n");
                sb.append("edge(\"" + n.getSourceNode().getId() + "\", \"" + n.getTargetNode().getId() + "\").\n");
                sb.append("edge(\"" + n.getSourceNode().getId() + "\", \"" + n.getTargetNode().getId() + "\", \"" + n.getId() + "\").\n");
                for (String attrKey : n.getAttributeKeySet()) {
                    sb.append("attribute(\"" + n.getId() + "\", '\"" + attrKey + "\"', " + StringRep(n.getAttribute(attrKey)) + ").\n");
                }
            }
            for (String attrKey : graph.getAttributeKeySet()) {
                sb.append("attribute(\"" + graph.getId() + "\", '\"" + attrKey + "\"', " + StringRep(graph.getAttribute(attrKey)) + ").\n");
            }

            sb.append("undirected(X) :- graph(X), undirectedSecond(X).\n");
            sb.append("undirected(X) :- edge(X), undirectedSecond(X).\n");
            sb.append("directed(X) :- graph(X), directedSecond(X).\n");
            sb.append("directed(X) :- edge(X), directedSecond(X).\n");
            sb.append("mixed(X) :- graph(X), mixedSecond(X).\n");
            sb.append("edgeCount(X, Y) :- graph(X), edgeCountSecond(X, Y).\n");
            sb.append("nodeCount(X, Y) :- graph(X), nodeCountSecond(X, Y).\n");
            sb.append("componentCount(X, Y) :- graph(X), componentCountSecond(X, Y).\n");
            sb.append("attributeCount(X, Y) :- graph(X), attributeCountSecond(X, Y).\n");
            sb.append("attributeCount(X, Y) :- node(X), attributeCountSecond(X, Y).\n");
            sb.append("attributeCount(X, Y) :- edge(X), attributeCountSecond(X, Y).\n");
            sb.append("singlegraph(X) :- graph(X), singlegraphSecond(X).\n");
            sb.append("multigraph(X) :- graph(X), multigraphSecond(X).\n");
            sb.append("isConnected(X) :- componentCount(X, 0) ; componentCount(X, 1).\n");
            sb.append("degree(X, Y) :- node(X), degreeSecond(X, Y).\n");
            sb.append("indegree(X, Y) :- node(X), indegreeSecond(X, Y).\n");
            sb.append("outdegree(X, Y) :- node(X), outdegreeSecond(X, Y).\n");
            sb.append("neighbourCount(X, Y) :- node(X), neighbourCountSecond(X, Y).\n");
            sb.append("label(X, Y) :- node(X), attribute(X, '\"label\"', Y).\n");
            sb.append("label(X, Y) :- graph(X), attribute(X, '\"label\"', Y).\n");
            sb.append("label(X, Y) :- edge(X), attribute(X, '\"label\"', Y).\n");
            sb.append("flag(X, Y) :- node(X), attribute(X, '\"flag\"', Y).\n");
            sb.append("flag(X, Y) :- graph(X), attribute(X, '\"flag\"', Y).\n");
            sb.append("flag(X, Y) :- edge(X), attribute(X, '\"flag\"', Y).\n");
            sb.append("type(X, Y) :- node(X), attribute(X, '\"type\"', Y).\n");
            sb.append("type(X, Y) :- graph(X), attribute(X, '\"type\"', Y).\n");
            sb.append("type(X, Y) :- edge(X), attribute(X, '\"type\"', Y).\n");
            sb.append("inComponent(X, Y) :- node(X), inComponentSecond(X, Y).\n");
            sb.append("inMST(X) :- edge(X), inMSTSecond(X).\n");
            sb.append("inShortestPath(X,Y,Z) :- edge(X), node(Y), node(Z), inShortestPathSecond(X,Y,Z).\n");
            sb.append("index(X,Y) :- node(X), indexSecond(X,Y).\n");
            sb.append("index(X,Y) :- edge(X), indexSecond(X,Y).\n");
            sb.append("index(X,Y) :- graph(X), indexSecond(X,Y).\n");
            sb.append("colour(X,Y) :- color(X,Y).");
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Returns whether the given object is directed, be it an {@link Edge} or {@link Graph}.
     *
     * @param ID Identifier of the {@link Graph} element.
     * @return Whether the given object is directed.
     */
    public boolean directedSecond_1(Term ID) {
        return bool((Struct) ID.getTerm(), GraphUtils::isDirectedGeneral, false, true, true);
    }

    /**
     * Returns whether the given object is undirected, be it an {@link Edge} or {@link Graph}.
     *
     * @param ID Identifier of the {@link Graph} element.
     * @return Whether the given object is undirected.
     */
    public boolean undirectedSecond_1(Term ID) {
        return bool((Struct) ID.getTerm(), GraphUtils::isUnDirectedGeneral, false, true, true);
    }

    /**
     * Logs the given term, then returns true
     *
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
     *
     * @param ID Identifier of the {@link Graph} element.
     * @return Whether the given {@link Graph} is undirected.
     */
    public boolean mixedSecond_1(Term ID) {
        return directedSecond_1(ID) == undirectedSecond_1(ID);
    }

    /**
     * Returns whether the given {@link Graph} has this {@link Edge} count or unifies otherwise.
     *
     * @param ID    Identifier of the {@link Graph}.
     * @param count {@link Edge} count of the {@link Graph}
     * @return Whether unification was possible or the given count was correct.
     */
    public boolean edgeCountSecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> ((Graph) n).getEdgeCount(), false, false, true);
    }

    /**
     * Returns whether the given {@link Graph} is a {@link SingleGraph}.
     *
     * @param ID Identifier of the {@link Graph}.
     * @return Whether the {@link Graph} is a {@link SingleGraph}.
     */
    public boolean singlegraphSecond_1(Term ID) {
        return bool((Struct) ID.getTerm(), n -> n instanceof SingleGraph, false, false, true);
    }

    /**
     * Returns whether the given {@link Graph} is a {@link MultiGraph}.
     *
     * @param ID Identifier of the {@link Graph}.
     * @return Whether the {@link Graph} is a {@link MultiGraph}.
     */
    public boolean multigraphSecond_1(Term ID) {
        return bool((Struct) ID.getTerm(), n -> n instanceof MultiGraph, false, false, true);
    }

    /**
     * Returns whether the given {@link Graph} has this {@link Node} count or unifies otherwise.
     *
     * @param ID    Identifier of the {@link Graph}.
     * @param count {@link Node} count of the {@link Graph}
     * @return Whether unification was possible or the given count was correct.
     */
    public boolean nodeCountSecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> ((Graph) n).getNodeCount(), false, false, true);
    }

    /**
     * Returns whether the given {@link Graph} has this component count or unifies otherwise.
     *
     * @param ID    Identifier of the {@link Graph}.
     * @param count Component count of the {@link Graph}
     * @return Whether unification was possible or the given count was correct.
     */
    public boolean componentCountSecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> GraphUtils.ConnectedComponentsCount(((Graph) n)), false, false, true);
    }

    /**
     * Returns whether the given {@link Graph} element has this number of attributes or unifies otherwise.
     *
     * @param ID    Identifier of the {@link Graph}.
     * @param count Attribute count of the {@link Graph}
     * @return Whether unification was possible or the given count was correct.
     */
    public boolean attributeCountSecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, Element::getAttributeCount, true, true, true);
    }

    /**
     * Returns whether the given {@link Node} has this degree or unifies otherwise.
     *
     * @param ID    Identifier of the {@link Node}.
     * @param count Degree of the {@link Node}
     * @return Whether unification was possible or the given degree was correct.
     */
    public boolean degreeSecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> ((Node) n).getDegree(), true, false, false);
    }

    /**
     * Returns whether the given {@link Node} has this indegree or unifies otherwise.
     *
     * @param ID    Identifier of the {@link Node}.
     * @param count Indegree of the {@link Node}
     * @return Whether unification was possible or the given indegree was correct.
     */
    public boolean indegreeSecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> ((Node) n).getInDegree(), true, false, false);
    }

    /**
     * Returns whether the given {@link Node} has this outdegree or unifies otherwise.
     *
     * @param ID    Identifier of the {@link Node}.
     * @param count Outdegree of the {@link Node}
     * @return Whether unification was possible or the given outdegree was correct.
     */
    public boolean outdegreeSecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> ((Node) n).getOutDegree(), true, false, false);
    }

    /**
     * Returns whether the given {@link Node} has this number of neighbours or unifies otherwise.
     *
     * @param ID    Identifier of the {@link Node}.
     * @param count Number of neighbours the {@link Node} has.
     * @return Whether unification was possible or the given number was correct.
     */
    public boolean neighbourCountSecond_2(Term ID, Term count) {
        return numeric((Struct) ID.getTerm(), count, n -> GraphUtils.neighbourCount((Node) n), true, false, false);
    }


    /**
     * Returns whether the given {@link Node} is in a specific component of the {@link Graph} or unifies otherwise.
     *
     * @param ID        Identifier of the {@link Node}
     * @param component Numeric identifier of the component this {@link Node} is in.
     * @return Whether unification was possible or the given component ID was correct.
     */
    public boolean inComponentSecond_2(Term ID, Term component) {
        GraphUtils.ConnectedComponentsCount(graph);
        return numeric((Struct) ID.getTerm(), component, n -> n.getAttribute("_ATTRIBUTE_DETERMINING_WHICH_COMPONENT_THE_NODE_BELONGS_TO_"), true, false, false);
    }

    private Dijkstra dijkstra;

    /**
     * Returns whether an {@link Edge} is in the shortest path between two {@link Node} objects or unifies otherwise.
     *
     * @param ID   Identifier of the {@link Edge}.
     * @param from Source {@link Node} of the shortest path.
     * @param to   Target {@link Node} of the shortest path.
     * @return Whether the given {@link Edge} lies on the shortest path between the two {@link Node} objects.
     */
    public boolean inShortestPathSecond_3(Term ID, Term from, Term to) {
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
     *
     * @param ID Identifier of the {@link Edge}.
     * @return Whether the {@link Edge} is in the minimal spanning tree of the {@link Graph}.
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean inMSTSecond_1(Term ID) {
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
     *
     * @param ID    Identifier of the {@link Element}.
     * @param index Index of the {@link Element}.
     * @return Whether the {@link Element} has the given index or unifies otherwise.
     */
    public boolean indexSecond_2(Term ID, Term index) {
        if (indexing == null) {
            indexing = new HashMap<>();
            List<Element> elems = new LinkedList<>(GraphUtils.elements(graph, true, true, true));
            elems.sort((o1, o2) -> StringUtils.compareStrings(o1.getId(), o2.getId()));
            for (int i = 0; i < elems.size(); i++) {
                indexing.put(elems.get(i), i);
            }
        }
        return numeric((Struct) ID.getTerm(), index, n -> indexing.get(n), true, true, true);
    }


}
