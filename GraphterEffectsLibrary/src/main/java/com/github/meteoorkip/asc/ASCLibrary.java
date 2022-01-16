package com.github.meteoorkip.asc;

import com.github.meteoorkip.utils.GraphUtils;
import com.github.meteoorkip.utils.StringUtils;
import it.unibo.tuprolog.core.*;
import it.unibo.tuprolog.core.operators.OperatorSet;
import it.unibo.tuprolog.solve.Signature;
import it.unibo.tuprolog.solve.Solution;
import it.unibo.tuprolog.solve.primitive.Primitive;
import it.unibo.tuprolog.solve.primitive.Solve;
import it.unibo.tuprolog.theory.Theory;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.jetbrains.annotations.NotNull;

import java.util.List;
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
        res.put(new Signature("graph", 1, false), request -> predicate(true, false, false, request, ignored -> true));
        res.put(new Signature("node", 1, false), request -> predicate(false, true, false, request, ignored -> true));
        res.put(new Signature("edge", 1, false), request -> predicate(false, false, true, request, ignored -> true));
        res.put(new Signature("inComponent", 2, false), request -> {
            GraphUtils.ConnectedComponentsCount(graph);
            return property(false, true, false, request, node -> Optional.of(atom(node.getId())));
        });
        res.put(new Signature("label", 2, false), request -> property(true, true, true, request, element -> {
            String label = (String) element.getAttribute("label");
            return label == null ? Optional.empty() : Optional.of(atom(label));
        }));
        res.put(new Signature("flag", 2, false), request -> property(true, true, true, request, element -> {
            String label = (String) element.getAttribute("flag");
            return label == null ? Optional.empty() : Optional.of(atom(label));
        }));
        res.put(new Signature("type", 2, false), request -> property(true, true, true, request, element -> {
            String label = (String) element.getAttribute("type");
            return label == null ? Optional.empty() : Optional.of(atom(label));
        }));
        res.put(new Signature("mixed", 1, false), request -> predicate(true, false, false, request, element -> GraphUtils.isDirectedGeneral(element) == GraphUtils.isUnDirectedGeneral(element)));
        res.put(new Signature("directed", 1, false), request -> predicate(true, false, true, request, GraphUtils::isDirectedGeneral));
        res.put(new Signature("singlegraph", 1, false), request -> predicate(true, false, true, request, graph -> graph instanceof SingleGraph));
        res.put(new Signature("multigraph", 1, false), request -> predicate(true, false, true, request, graph -> graph instanceof MultiGraph));
        res.put(new Signature("nodeCount", 2, false), request -> property(true, false, false, request, element -> Optional.of(intVal(((Graph) element).getNodeCount()))));
        res.put(new Signature("edgeCount", 2, false), request -> property(true, false, false, request, element -> Optional.of(intVal(((Graph) element).getEdgeCount()))));
        res.put(new Signature("undirected", 1, false), request -> predicate(true, false, true, request, GraphUtils::isUnDirectedGeneral));
        res.put(new Signature("componentCount", 2, false), request -> property(true, false, false, request, element -> Optional.of(intVal(GraphUtils.ConnectedComponentsCount((Graph) element)))));
        res.put(new Signature("attributeCount", 2, false), request -> property(true, true, true, request, element -> Optional.of(intVal(element.getAttributeCount()))));
        res.put(new Signature("degree", 2, false), request -> property(false, true, false, request, node -> Optional.of(intVal(((Node) node).getDegree()))));
        res.put(new Signature("indegree", 2, false), request -> property(false, true, false, request, node -> Optional.of(intVal(((Node) node).getInDegree()))));
        res.put(new Signature("outdegree", 2, false), request -> property(false, true, false, request, node -> Optional.of(intVal(((Node) node).getOutDegree()))));
        res.put(new Signature("neighbourCount", 2, false), request -> property(false, true, false, request, node -> Optional.of(intVal(GraphUtils.neighbourCount((Node) node)))));
        res.put(new Signature("inMST", 1, false), request -> predicate(false, false, true, request, edge -> GraphUtils.getMST(graph).contains((Edge) edge)));
        res.put(new Signature("edge", 3, false), request -> {
            List<Term> arguments = request.getArguments();
            if (arguments.get(0).isVar()) {
                Optional<Sequence<Solution>> subSolutions = graph.edges().map(Edge::getSourceNode).distinct().map(sourceNode -> {
                    Struct newQuery = request.getQuery().apply(Substitution.of(arguments.get(0).castToVar(), atom(sourceNode.getId()))).castToStruct();
                    return request.solve(newQuery, Long.MAX_VALUE);
                }).distinct().reduce(SequencesKt::plus);
                if (!subSolutions.isPresent()) {
                    return SequencesKt.emptySequence();
                } else {
                    return SequencesKt.map(subSolutions.get(), solution -> request.replySuccess(solution.getSubstitution().plus(Substitution.of(arguments.get(0).castToVar(), solution.getSolvedQuery().getArgAt(0))).castToUnifier(), null));
                }
            }
            Optional<Element> source = GraphUtils.getById(graph, arguments.get(0).castToAtom().getValue());
            if (!source.isPresent()) {
                return SequencesKt.emptySequence();
            }
            Term secondArgument = arguments.get(1);
            if (secondArgument.isVar()) {
                Optional<Sequence<Solution>> subSolutions = graph.edges().filter(x -> x.getSourceNode() == source.get()).map(Edge::getTargetNode).distinct().map(sourceNode -> {
                    Struct newQuery = request.getQuery().apply(Substitution.of(secondArgument.castToVar(), atom(sourceNode.getId()))).castToStruct();
                    return request.solve(newQuery, Long.MAX_VALUE);
                }).distinct().reduce(SequencesKt::plus);
                if (!subSolutions.isPresent()) {
                    return SequencesKt.emptySequence();
                } else {
                    return SequencesKt.map(subSolutions.get(), solution -> request.replySuccess(solution.getSubstitution().plus(Substitution.of(secondArgument.castToVar(), solution.getSolvedQuery().getArgAt(1))).castToUnifier(), null));
                }
            }
            Optional<Element> target = GraphUtils.getById(graph, secondArgument.castToAtom().getValue());
            if (!target.isPresent()) {
                return SequencesKt.emptySequence();
            }
            Term thirdArgument = arguments.get(2);
            if (thirdArgument.isVar()) {
                Optional<Sequence<Solution>> subSolutions = graph.edges().filter(x -> x.getTargetNode() == target.get() && x.getSourceNode() == source.get()).map(edge -> {
                    Struct newQuery = request.getQuery().apply(Substitution.of(thirdArgument.castToVar(), atom(edge.getId()))).castToStruct();
                    return request.solve(newQuery, Long.MAX_VALUE);
                }).distinct().reduce(SequencesKt::plus);
                if (!subSolutions.isPresent()) {
                    return SequencesKt.emptySequence();
                } else {
                    return SequencesKt.map(subSolutions.get(), solution -> request.replySuccess(solution.getSubstitution().plus(Substitution.of(thirdArgument.castToVar(), solution.getSolvedQuery().getArgAt(2))).castToUnifier(), null));
                }
            }
            Optional<Element> edge = GraphUtils.getById(graph, thirdArgument.castToAtom().getValue());
            if (!edge.isPresent() || !(edge.get() instanceof Edge) || ((Edge) edge.get()).getSourceNode() != source.get() || ((Edge) edge.get()).getTargetNode() != target.get()) {
                return SequencesKt.emptySequence();
            } else {
                return SequencesKt.sequenceOf(request.replySuccess(Substitution.empty(), null));
            }
        });
        res.put(new Signature("inShortestPath", 3, false), request -> {
            List<Term> arguments = request.getArguments();
            if (arguments.get(0).isVar()) {
                Optional<Sequence<Solution>> subSolutions = graph.edges().map(edge -> request.getQuery().apply(Substitution.of(arguments.get(0).castToVar(), atom(edge.getId()))).castToStruct()).map(newQuery -> request.solve(newQuery, Long.MAX_VALUE)).distinct().reduce(SequencesKt::plus);
                if (!subSolutions.isPresent()) {
                    return SequencesKt.emptySequence();
                } else {
                    Sequence<Solve.Response>[] toReturn = new Sequence[]{SequencesKt.emptySequence()};
                    SequencesKt.forEach(subSolutions.get(), solution -> {
                        if (solution.isYes()) {
                            toReturn[0] = SequencesKt.plus(toReturn[0], request.replySuccess(solution.getSubstitution().plus(Substitution.of(arguments.get(0).castToVar(), solution.getSolvedQuery().getArgAt(0))).castToUnifier(), null));
                        }
                        return null;
                    });
                    return toReturn[0];
                }
            }
            Optional<Element> edge = GraphUtils.getById(graph, arguments.get(0).castToAtom().getValue());
            if (!edge.isPresent()) {
                return SequencesKt.emptySequence();
            }
            if (arguments.get(1).isVar()) {
                Optional<Sequence<Solution>> subSolutions = graph.nodes().map(sourceNode -> request.getQuery().apply(Substitution.of(arguments.get(1).castToVar(), atom(sourceNode.getId()))).castToStruct()).map(newQuery -> request.solve(newQuery, Long.MAX_VALUE)).distinct().reduce(SequencesKt::plus);
                if (!subSolutions.isPresent()) {
                    return SequencesKt.emptySequence();
                } else {
                    final Sequence<Solve.Response>[] toReturn = new Sequence[]{SequencesKt.emptySequence()};
                    SequencesKt.forEach(subSolutions.get(), solution -> {
                        if (solution.isYes()) {
                            toReturn[0] = SequencesKt.plus(toReturn[0], request.replySuccess(solution.getSubstitution().plus(Substitution.of(arguments.get(1).castToVar(), solution.getSolvedQuery().getArgAt(1))).castToUnifier(), null));
                        }
                        return null;
                    });
                    return toReturn[0];

                }
            }
            Optional<Element> sourceNode = GraphUtils.getById(graph, arguments.get(1).castToAtom().getValue());
            if (!sourceNode.isPresent()) {
                return SequencesKt.emptySequence();
            }
            if (arguments.get(2).isVar()) {
                return SequencesKt.asSequence(graph.nodes().filter(targetNode -> inShortestPath((Edge) edge.get(), (Node) sourceNode.get(), targetNode)).map(targetNode -> request.replySuccess(Substitution.of(arguments.get(2).castToVar(), atom(targetNode.getId())), null)).iterator());
            } else {
                Optional<Element> targetNode = GraphUtils.getById(graph, arguments.get(2).castToAtom().getValue());
                if (!targetNode.isPresent() || !inShortestPath((Edge) edge.get(), (Node) sourceNode.get(), (Node) targetNode.get())) {
                    return SequencesKt.emptySequence();
                } else {
                    return SequencesKt.sequenceOf(request.replySuccess(Substitution.empty(), null));
                }
            }
        });
        res.put(new Signature("edge", 2, false), request -> {
            List<Term> arguments = request.getArguments();
            if (arguments.get(0).isVar()) {
                Optional<Sequence<Solution>> subSolutions = graph.edges().map(Edge::getSourceNode).distinct().map(sourceNode -> {
                    Struct newQuery = request.getQuery().apply(Substitution.of(arguments.get(0).castToVar(), atom(sourceNode.getId()))).castToStruct();
                    return request.solve(newQuery, Long.MAX_VALUE);
                }).distinct().reduce(SequencesKt::plus);
                if (!subSolutions.isPresent()) {
                    return SequencesKt.emptySequence();
                } else {
                    return SequencesKt.map(subSolutions.get(), solution -> request.replySuccess(solution.getSubstitution().plus(Substitution.of(arguments.get(0).castToVar(), solution.getSolvedQuery().getArgAt(0))).castToUnifier(), null));
                }
            }
            Optional<Element> source = GraphUtils.getById(graph, arguments.get(0).castToAtom().getValue());
            if (!source.isPresent()) {
                return SequencesKt.emptySequence();
            }
            Term secondArgument = arguments.get(1);
            if (secondArgument.isVar()) {
                Optional<Sequence<Solution>> subSolutions = graph.edges().filter(x -> x.getSourceNode() == source.get()).map(Edge::getTargetNode).distinct().map(sourceNode -> {
                    Struct newQuery = request.getQuery().apply(Substitution.of(secondArgument.castToVar(), atom(sourceNode.getId()))).castToStruct();
                    return request.solve(newQuery, Long.MAX_VALUE);
                }).distinct().reduce(SequencesKt::plus);
                if (!subSolutions.isPresent()) {
                    return SequencesKt.emptySequence();
                } else {
                    return SequencesKt.map(subSolutions.get(), solution -> request.replySuccess(solution.getSubstitution().plus(Substitution.of(secondArgument.castToVar(), solution.getSolvedQuery().getArgAt(1))).castToUnifier(), null));
                }
            } else {
                Optional<Element> target = GraphUtils.getById(graph, secondArgument.castToAtom().getValue());
                if (!target.isPresent() || graph.edges().noneMatch(x -> {
                    if (x.isDirected()) {
                        return x.getSourceNode() == source.get() && x.getTargetNode() == target.get();
                    } else {
                        return Arrays.equals(new Element[]{x.getSourceNode(), x.getTargetNode()}, new Element[]{source.get(), target.get()});
                    }
                })) {
                    return SequencesKt.emptySequence();
                } else {
                    return SequencesKt.sequenceOf(request.replySuccess(Substitution.empty(), null));
                }
            }
        });
        res.put(new Signature("attribute", 3, false), request -> {
            List<Term> arguments = request.getArguments();
            Set<Element> allElements = new HashSet<>();
            allElements.add(graph);
            graph.edges().forEach(allElements::add);
            graph.nodes().forEach(allElements::add);
            if (arguments.get(0).isVar()) {
                Optional<Sequence<Solution>> subSolutions = allElements.stream().map(element -> {
                    Struct newQuery = request.getQuery().apply(Substitution.of(arguments.get(0).castToVar(), atom(element.getId()))).castToStruct();
                    return request.solve(newQuery, Long.MAX_VALUE);
                }).distinct().reduce(SequencesKt::plus);
                if (!subSolutions.isPresent()) {
                    return SequencesKt.emptySequence();
                } else {
                    Sequence<Solution> realSubSolutions = SequencesKt.distinct(SequencesKt.filter(subSolutions.get(), Solution::isYes));
                    final Sequence<Solve.Response>[] responses = new Sequence[]{SequencesKt.emptySequence()};
                    SequencesKt.forEach(realSubSolutions, solution -> {
                        if (solution.isYes()) {
                            responses[0] = SequencesKt.plus(responses[0], request.replySuccess(solution.getSubstitution().plus(Substitution.of(arguments.get(0).castToVar(), solution.getSolvedQuery().getArgAt(0))).castToUnifier(), null));
                        }
                        return null;
                    });
                    return responses[0];
                }
            }
            Optional<Element> element = GraphUtils.getById(graph, arguments.get(0).castToAtom().getValue());
            if (!element.isPresent()) {
                return SequencesKt.emptySequence();
            }
            if (arguments.get(1).isVar()) {
                Optional<Sequence<Solution>> subSolutions = element.get().attributeKeys().map(key -> {
                    Struct newQuery = request.getQuery().apply(Substitution.of(arguments.get(1).castToVar(), atom(key))).castToStruct();
                    return request.solve(newQuery, Long.MAX_VALUE);
                }).distinct().reduce(SequencesKt::plus);
                if (!subSolutions.isPresent()) {
                    return SequencesKt.emptySequence();
                } else {
                    return SequencesKt.map(subSolutions.get(), solution -> request.replySuccess(solution.getSubstitution().plus(Substitution.of(arguments.get(1).castToVar(), solution.getSolvedQuery().getArgAt(1))).castToUnifier(), null));
                }
            }
            String attributeKey = arguments.get(1).castToAtom().getValue();
            Object attributeValue = element.get().getAttribute(StringUtils.removeQuotation(attributeKey));
            Term thirdArgument = arguments.get(2);
            if (thirdArgument.isVar()) {
                return SequencesKt.sequenceOf(request.replySuccess(Substitution.of(thirdArgument.castToVar(), atom(stringRep(attributeValue))), null));
            } else if (thirdArgument.isAtom() && thirdArgument.castToAtom().getValue().equals(attributeValue)) {
                return SequencesKt.sequenceOf(request.replySuccess(Substitution.empty(), null));
            } else {
                return SequencesKt.emptySequence();
            }
        });
        res.put(new Signature("text_concat", 3, false), request -> {
            Term one = request.getArguments().get(0);
            Term two = request.getArguments().get(1);
            Term three = request.getArguments().get(2);
            if (one.isAtom()) {
                String oneContents = stringRep(one.castToAtom().getValue());
                if (two.isAtom()) {
                    String twoContents = stringRep(two.castToAtom().getValue());
                    Atom threeShouldBe = atom(oneContents + two.castToAtom().getValue());
                    if (three.isVar()) {
                        return SequencesKt.sequenceOf(request.replySuccess(Substitution.of(three.castToVar(), atom(oneContents + twoContents)), null));
                    } else if (three.equals(threeShouldBe)) {
                        return SequencesKt.sequenceOf(request.replySuccess(Substitution.empty(), null));
                    } else {
                        return SequencesKt.emptySequence();
                    }
                } else if (three.isAtom()) {
                    String threeContents = stringRep(one.castToAtom().getValue());
                    if (threeContents.startsWith(oneContents)) {
                        String twoShouldBe = threeContents.substring(oneContents.length());
                        if (two.isAtom() && stringRep(two.castToAtom().getValue()).equals(twoShouldBe)) {
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
                String twoContents = stringRep(two.castToAtom().getValue());
                String threeContents = stringRep(one.castToAtom().getValue());
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

    @NotNull
    @Override
    public Theory getTheory() {
        Clause clause0 = clause(struct("isConnected", var("X")), or(struct("componentCount", var("X"), intVal(0)), struct("componentCount", var("X"), intVal(1))));
        Clause clause1 = fact(struct("graphLibrary", var("X"), Truth.FALSE));
        return Theory.of(clause0, clause1);
    }
}
