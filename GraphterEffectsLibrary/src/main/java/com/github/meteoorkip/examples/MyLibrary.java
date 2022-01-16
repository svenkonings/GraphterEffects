//package com.github.meteoorkip.examples;
//
//import com.github.meteoorkip.asc.GraphLibrary;
//import com.github.meteoorkip.asc.GraphLibraryLoader;
//import com.github.meteoorkip.prolog.TuProlog;
//import com.github.meteoorkip.utils.GraphUtils;
//import it.unibo.tuprolog.core.Struct;
//import it.unibo.tuprolog.core.Substitution;
//import it.unibo.tuprolog.core.Term;
//import it.unibo.tuprolog.core.operators.OperatorSet;
//import it.unibo.tuprolog.solve.ExecutionContext;
//import it.unibo.tuprolog.solve.Signature;
//import it.unibo.tuprolog.solve.Solution;
//import it.unibo.tuprolog.solve.primitive.Primitive;
//import it.unibo.tuprolog.solve.primitive.Solve;
//import kotlin.jvm.functions.Function1;
//import kotlin.sequences.Sequence;
//import kotlin.sequences.SequencesKt;
//import org.graphstream.graph.Edge;
//import org.graphstream.graph.Element;
//import org.graphstream.graph.Graph;
//import org.graphstream.graph.Node;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//import java.util.function.Consumer;
//import java.util.function.Function;
//import java.util.function.Predicate;
//
//import static com.github.meteoorkip.prolog.TuProlog.atom;
//import static com.github.meteoorkip.prolog.TuProlog.intVal;
//
//public class MyLibrary extends GraphLibrary {
//
//    public MyLibrary(Graph graph) {
//        super(graph, "MyLibrary");
//    }
//
//    @Override
//    public String getTheory() {
//        //This makes sure the predicates called are Struct objects, and Graph elements retrieved are of the correct type.
//        StringBuilder res = new StringBuilder();
//        res.append("hasloop(X) :- node(X), hasloopsecond(X).\n");
//        res.append("idlength(X,Y) :- node(X), idlengthsecond(X,Y).\n");
//        res.append("idlength(X,Y) :- edge(X), idlengthsecond(X,Y).\n");
//        res.append("idlength(X,Y) :- graph(X), idlengthsecond(X,Y).\n");
//        res.append("triangle(X,Y,Z) :- edge(X), edge(Y), edge(Z), trianglesecond(X,Y,Z).");
//        return res.toString();
//    }
//
//    public boolean hasloopsecond_1(Term node) {
//        //Use bool for boolean predicates.
//        return bool((Struct) node.getTerm(), n -> ((Node) n).hasEdgeBetween((Node) n), true, false, false);
//    }
//
//    public boolean idlengthsecond_2(Term element, Term length) {
//        //Use numeric for numeric predicates.
//        return numeric((Struct) element.getTerm(), length, n -> n.getId().length(), true, true, true);
//    }
//
//    public boolean trianglesecond_3(Term firstedge, Term secondedge, Term thirdedge) {
//        //Retrieve Edge objects from the terms.
//        Edge edge1 = (Edge) GraphUtils.getByID(graph, ((Struct) firstedge.getTerm()).getName());
//        Edge edge2 = (Edge) GraphUtils.getByID(graph, ((Struct) secondedge.getTerm()).getName());
//        Edge edge3 = (Edge) GraphUtils.getByID(graph, ((Struct) thirdedge.getTerm()).getName());
//
//        //Checking if the three edges form a triangle.
//        if (edge1.getTargetNode().equals(edge2.getSourceNode())) {
//            return edge2.getTargetNode().equals(edge3.getSourceNode()) && edge3.getTargetNode().equals(edge1.getSourceNode());
//        } else if (edge1.getTargetNode().equals(edge3.getSourceNode())) {
//            return edge3.getTargetNode().equals(edge2.getSourceNode()) && edge2.getTargetNode().equals(edge1.getSourceNode());
//        }
//        //These edges are not a triangle.
//        return false;
//    }
//
//    @NotNull
//    @Override
//    public OperatorSet getOperators() {
//        return OperatorSet.EMPTY;
//    }
//
//    @NotNull
//    @Override
//    public Map<Signature, Primitive> getPrimitives() {
//        Map<Signature, Primitive> res = new HashMap<>();
//        res.put(new Signature("hasloop", 1, false), request -> predicate(false, true, false, request, node -> ((Node)node).hasEdgeBetween(((Node)node))));
//        res.put(new Signature("idlength", 2, false), request -> property(true, true, true, request, element -> Optional.of(intVal(element.getId().length()))));
//        res.put(new Signature("triangle", 3, false), new Primitive() {
//            @NotNull
//            @Override
//            public Sequence<Solve.Response> solve(@NotNull Solve.Request<? extends ExecutionContext> request) {
//                //Retrieve the first term
//                Term firstEdgeTerm = request.getArguments().get(0);
//                //We really don't want
//                if (firstEdgeTerm.isVar()) {
//                    Optional<Sequence<Solve.Response>> recursiveSolutions = graph.edges().map(edge -> {
//                        Struct newQuery = request.getQuery().apply(Substitution.of(firstEdgeTerm.castToVar(), atom(edge.getId()))).castToStruct();
//                        return SequencesKt.map(SequencesKt.filter(request.solve(newQuery, Long.MAX_VALUE), Solution::isYes),
//                                solution -> request.replySuccess(solution.getSubstitution().plus(Substitution.of(firstEdgeTerm.castToVar(), atom(edge.getId()))).castToUnifier(), null));
//                    }).reduce(SequencesKt::plus);
//                    return recursiveSolutions.orElseGet(SequencesKt::emptySequence);
//                }
//                Term secondEdgeTerm = request.getArguments().get(1);
//                if (secondEdgeTerm.isVar()) {
//                    Optional<Sequence<Solve.Response>> recursiveSolutions = graph.edges().map(edge -> {
//                        Struct newQuery = request.getQuery().apply(Substitution.of(secondEdgeTerm.castToVar(), atom(edge.getId()))).castToStruct();
//                        return SequencesKt.map(SequencesKt.filter(request.solve(newQuery, Long.MAX_VALUE), Solution::isYes),
//                                solution -> request.replySuccess(solution.getSubstitution().plus(Substitution.of(secondEdgeTerm.castToVar(), atom(edge.getId()))).castToUnifier(), null));
//                    }).reduce(SequencesKt::plus);
//                    return recursiveSolutions.orElseGet(SequencesKt::emptySequence);
//                }
//                Term thirdEdgeTerm = request.getArguments().get(2);
//                if (thirdEdgeTerm.isVar()) {
//                    Optional<Sequence<Solve.Response>> recursiveSolutions = graph.edges().map(edge -> {
//                        Struct newQuery = request.getQuery().apply(Substitution.of(thirdEdgeTerm.castToVar(), atom(edge.getId()))).castToStruct();
//                        return SequencesKt.map(SequencesKt.filter(request.solve(newQuery, Long.MAX_VALUE), Solution::isYes),
//                                solution -> request.replySuccess(solution.getSubstitution().plus(Substitution.of(thirdEdgeTerm.castToVar(), atom(edge.getId()))).castToUnifier(), null));
//                    }).reduce(SequencesKt::plus);
//                    return recursiveSolutions.orElseGet(SequencesKt::emptySequence);
//                }
//                Edge edge1 = graph.getEdge(firstEdgeTerm.castToAtom().getValue());
//                Edge edge2 = graph.getEdge(secondEdgeTerm.castToAtom().getValue());
//                Edge edge3 = graph.getEdge(thirdEdgeTerm.castToAtom().getValue());
//                if (edge1 == null || edge2 == null || edge3 == null) {
//                    return SequencesKt.emptySequence();
//                }
//                boolean isTriangle = false;
//                if (edge1.getTargetNode().equals(edge2.getSourceNode())) {
//                    isTriangle = edge2.getTargetNode().equals(edge3.getSourceNode()) && edge3.getTargetNode().equals(edge1.getSourceNode());
//                } else if (edge1.getTargetNode().equals(edge3.getSourceNode())) {
//                    isTriangle = edge3.getTargetNode().equals(edge2.getSourceNode()) && edge2.getTargetNode().equals(edge1.getSourceNode());
//                }
//                if (isTriangle) {
//                    return SequencesKt.sequenceOf(request.replySuccess(Substitution.empty(), null));
//                } else {
//                    return SequencesKt.emptySequence();
//                }
//            }
//        });
//        return res;
//    }
//}
