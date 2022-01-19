package com.github.meteoorkip.asc;

import com.github.meteoorkip.utils.GraphUtils;
import it.unibo.tuprolog.core.Atom;
import it.unibo.tuprolog.core.Clause;
import it.unibo.tuprolog.core.Substitution;
import it.unibo.tuprolog.core.Term;
import it.unibo.tuprolog.core.operators.OperatorSet;
import it.unibo.tuprolog.solve.Signature;
import it.unibo.tuprolog.solve.primitive.Primitive;
import it.unibo.tuprolog.theory.Theory;
import kotlin.sequences.SequencesKt;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.github.meteoorkip.prolog.TuProlog.*;

public class ASCLibrary extends GraphLibrary {


    private final Map<Node, Map<Node, Path>> shortestPaths = new HashMap<>();
    private final Map<Node, Dijkstra> dijkstras = new HashMap<>();

    /**
     * Creates a new ASCLibrary that retrieves information from a given {@link Graph}.
     *
     * @param graph Given {@code Graph}
     */
    public ASCLibrary(Graph graph) {
        super(graph, "ASCLibrary");
    }

    @NotNull
    @Override
    public OperatorSet getOperators() {
        return OperatorSet.EMPTY;
    }

    @NotNull
    @Override
    public Map<Signature, Primitive> getPrimitives() {
        Map<Signature, Primitive> res = new HashMap<>();
        res.put(new Signature("inComponent", 2, false), request -> {
            GraphUtils.ConnectedComponentsCount(graph);
            return property(false, true, false, request, node -> Optional.of(atom(node.getId())));
        });
        res.put(new Signature("componentCount", 2, false), request -> property(true, false, false, request, element -> Optional.of(intVal(GraphUtils.ConnectedComponentsCount((Graph) element)))));
        res.put(new Signature("inMST", 1, false), request -> predicate(false, false, true, request, edge -> GraphUtils.getMST(graph).contains((Edge) edge)));
        res.put(new Signature("inShortestPath", 3, false), request -> {
            Term firstArgument = request.getArguments().get(0);
            Term secondArgument = request.getArguments().get(1);
            Term thirdArgument = request.getArguments().get(2);
            if (firstArgument.isVar()) {
                return makeElementAtom(request, firstArgument.castToVar(), graph.edges());
            }
            Edge edge = graph.getEdge(firstArgument.castToAtom().getValue());
            if (edge == null) {
                return SequencesKt.emptySequence();
            }
            if (secondArgument.isVar()) {
                return makeElementAtom(request, firstArgument.castToVar(), graph.nodes());
            }
            Node sourceNode = graph.getNode(secondArgument.castToAtom().getValue());
            if (sourceNode == null) {
                return SequencesKt.emptySequence();
            }
            if (thirdArgument.isVar()) {
                return SequencesKt.asSequence(graph.nodes().filter(targetNode -> inShortestPath(edge, sourceNode, targetNode)).map(targetNode -> request.replySuccess(Substitution.of(thirdArgument.castToVar(), atom(targetNode.getId())), null)).iterator());
            } else {
                Node targetNode = graph.getNode(thirdArgument.castToAtom().getValue());
                if (targetNode == null || !inShortestPath(edge, sourceNode, targetNode)) {
                    return SequencesKt.emptySequence();
                } else {
                    return SequencesKt.sequenceOf(request.replySuccess(Substitution.empty(), null));
                }
            }
        });
        res.put(new Signature("text_concat", 3, false), request -> {
            Term one = request.getArguments().get(0);
            Term two = request.getArguments().get(1);
            Term three = request.getArguments().get(2);
            if (one.isAtom()) {
                String oneContents = one.castToAtom().getValue();
                if (two.isAtom()) {
                    String twoContents = two.castToAtom().getValue();
                    Atom threeShouldBe = atom(oneContents + two.castToAtom().getValue());
                    if (three.isVar()) {
                        return SequencesKt.sequenceOf(request.replySuccess(Substitution.of(three.castToVar(), atom(oneContents + twoContents)), null));
                    } else if (three.equals(threeShouldBe)) {
                        return SequencesKt.sequenceOf(request.replySuccess(Substitution.empty(), null));
                    } else {
                        return SequencesKt.emptySequence();
                    }
                } else if (three.isAtom()) {
                    String threeContents = one.castToAtom().getValue();
                    if (threeContents.startsWith(oneContents)) {
                        String twoShouldBe = threeContents.substring(oneContents.length());
                        if (two.isAtom() && two.castToAtom().getValue().equals(twoShouldBe)) {
                            return SequencesKt.sequenceOf(request.replySuccess(Substitution.empty(), null));
                        } else if (two.isVar()) {
                            return SequencesKt.sequenceOf(request.replySuccess(Substitution.of(two.castToVar(), atom(twoShouldBe)), null));
                        } else {
                            return SequencesKt.emptySequence();
                        }
                    } else {
                        return SequencesKt.emptySequence();
                    }
                } else {
                    return SequencesKt.emptySequence();
                }
            } else if (one.isVar() && two.isAtom() && three.isAtom()) {
                String twoContents = two.castToAtom().getValue();
                String threeContents = one.castToAtom().getValue();
                if (threeContents.endsWith(twoContents)) {
                    String oneShouldBe = threeContents.substring(0, threeContents.length() - twoContents.length());
                    return SequencesKt.sequenceOf(request.replySuccess(Substitution.of(one.castToVar(), atom(oneShouldBe)), null));
                } else {
                    return SequencesKt.emptySequence();
                }
            } else {
                return SequencesKt.emptySequence();
            }
        });
        res.put(new Signature("text_term", 2, false), request -> {
            if (request.getArguments().get(0).isVar()) {
                return SequencesKt.sequenceOf(request.replySuccess(Substitution.of(request.getArguments().get(0).castToVar(), atom(request.getArguments().get(1).toString())), null));
            } else if (request.getArguments().get(0).isAtom() && request.getArguments().get(0).equals(atom(request.getArguments().get(1).toString()))) {
                return SequencesKt.sequenceOf(request.replySuccess(Substitution.empty(), null));
            } else {
                return SequencesKt.emptySequence();
            }
        });
        return res;
    }

    private boolean inShortestPath(Edge edge, Node from, Node to) {
        shortestPaths.computeIfAbsent(from, edges -> new HashMap<>());
        shortestPaths.get(from).computeIfAbsent(to, edges -> {
            dijkstras.computeIfAbsent(from, from1 -> {
                Dijkstra res = new Dijkstra();
                res.init(graph);
                res.setSource(from1);
                res.compute();
                return res;
            });
            return dijkstras.get(from).getPath(to);
        });
        return shortestPaths.get(from).get(to).contains(edge);
    }


    private Theory theory = null;
    @NotNull
    @Override
    public Theory getTheory() {
        if (theory == null) {
            List<Clause> clauses = new ArrayList<>();
            addLogicClauses(clauses);
            addGraphClauses(clauses);
            addNodeClauses(clauses);
            addEdgeClauses(clauses);
            theory = Theory.of(clauses);
        }
        return theory;
    }

    private void addEdgeClauses(List<Clause> clauses) {
        graph.edges().forEach(edge -> {
            clauses.add(fact("edge", atom(edge.getId())));
            clauses.add(fact("edge", atom(edge.getSourceNode().getId()), atom(edge.getTargetNode().getId())));
            clauses.add(fact("edge", atom(edge.getSourceNode().getId()), atom(edge.getTargetNode().getId()), atom(edge.getId())));
            edge.attributeKeys().forEach(key -> clauses.add(fact("attribute", atom(edge.getId()), atom(key), (Term) edge.getAttribute(key))));
            if (edge.isDirected()) {
                clauses.add(fact("directed", atom(edge.getId())));
            } else {
                clauses.add(fact("undirected", atom(edge.getId())));
            }
            clauses.add(fact("attributeCount", atom(edge.getId()), intVal(edge.getAttributeCount())));
        });
    }

    private void addNodeClauses(List<Clause> clauses) {
        graph.nodes().forEach(node -> {
            clauses.add(fact("node", atom(node.getId())));
            node.attributeKeys().forEach(key -> clauses.add(fact("attribute", atom(node.getId()), atom(key), (Term) node.getAttribute(key))));
            clauses.add(fact("attributeCount", atom(node.getId()), intVal(node.getAttributeCount())));
            clauses.add(fact("degree", atom(node.getId()), intVal(node.getDegree())));
            clauses.add(fact("indegree", atom(node.getId()), intVal(node.getInDegree())));
            clauses.add(fact("outdegree", atom(node.getId()), intVal(node.getOutDegree())));
            clauses.add(fact("neighbourCount", atom(node.getId()), intVal(GraphUtils.neighbourCount(node))));
        });
    }

    private void addGraphClauses(List<Clause> clauses) {
        clauses.add(fact("graph", atom(graph.getId())));
        if (graph instanceof SingleGraph) {
            clauses.add(fact("singlegraph", atom(graph.getId())));
        }
        if (graph instanceof MultiGraph) {
            clauses.add(fact("multigraph", atom(graph.getId())));
        }
        boolean hasDirectedEdge = graph.edges().anyMatch(Edge::isDirected);
        boolean hasUndirectedEdge = graph.edges().anyMatch(e -> !e.isDirected());
        if (hasDirectedEdge == hasUndirectedEdge) {
            clauses.add(fact("mixed", atom(graph.getId())));
        }
        if (!hasUndirectedEdge) {
            clauses.add(fact("directed", atom(graph.getId())));
        }
        if (!hasDirectedEdge) {
            clauses.add(fact("undirected", atom(graph.getId())));
        }
        clauses.add(fact("nodeCount", atom(graph.getId()), intVal(graph.getNodeCount())));
        clauses.add(fact("edgeCount", atom(graph.getId()), intVal(graph.getEdgeCount())));
        clauses.add(fact("attributeCount", atom(graph.getId()), intVal(graph.getAttributeCount())));
        graph.attributeKeys().forEach(key -> clauses.add(fact("attribute", atom(graph.getId()), atom(key), (Term) graph.getAttribute(key))));
    }

    private void addLogicClauses(List<Clause> clauses) {
        clauses.add(clause(struct("isConnected", var("X")), or(struct("componentCount", var("X"), intVal(0)), struct("componentCount", var("X"), intVal(1)))));
        clauses.add(clause(struct("type", var("X"), var("Y")), struct("attribute", var("X"), atom("type"), var("Y"))));
        clauses.add(clause(struct("flag", var("X"), var("Y")), struct("attribute", var("X"), atom("flag"), var("Y"))));
        clauses.add(clause(struct("label", var("X"), var("Y")), struct("attribute", var("X"), atom("label"), var("Y"))));
    }

}
