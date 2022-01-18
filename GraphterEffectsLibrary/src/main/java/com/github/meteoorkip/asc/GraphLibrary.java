package com.github.meteoorkip.asc;

import com.github.meteoorkip.utils.GraphUtils;
import it.unibo.tuprolog.core.*;
import it.unibo.tuprolog.core.operators.Operator;
import it.unibo.tuprolog.solve.ExecutionContext;
import it.unibo.tuprolog.solve.Signature;
import it.unibo.tuprolog.solve.Solution;
import it.unibo.tuprolog.solve.function.LogicFunction;
import it.unibo.tuprolog.solve.library.AliasedLibrary;
import it.unibo.tuprolog.solve.primitive.Solve;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.jetbrains.annotations.NotNull;

import java.lang.Integer;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.github.meteoorkip.prolog.TuProlog.atom;

public abstract class GraphLibrary implements AliasedLibrary {


    /**
     * {@link Graph} on which predicates are performed.
     */
    protected final Graph graph;
    private final String libraryName;
    /**
     * Creates a new {@link GraphLibrary}.
     *
     * @param graph {@link Graph} on which predicates are performed.
     * @param libraryName name of the library
     */
    public GraphLibrary(Graph graph, String libraryName) {
        this.graph = graph;
        this.libraryName = libraryName;
    }

    protected String stringRep(Object term) {
        try {
            if (term instanceof Integer || (term instanceof String && ((String) term).matches("\\d*"))) {
                return term.toString();
            } else if (term instanceof String && ((String) term).matches("\"\\d+\"")) {
                return ((String) term).replaceFirst("\"(\\d+)\"", "$1");
            } else if (term instanceof String[]) {
                String[] res = new String[((String[]) term).length];
                for (int i = 0; i < ((String[]) term).length; i++) {
                    res[i] = stringRep(((String[]) term)[i]);
                }
                return Arrays.asList(res).toString();
            } else if (term instanceof String && ((String) term).matches("\".*\"")) {
                return (String) term;
                //                return "'" + term + "'";
            }
            if (term instanceof List) {
                if (((List) term).size() == 0) {
                    return "[]";
                } else {
                    if (((List) term).get(0) instanceof String) {
                        return stringRep(((List) term).toArray(new String[0]));
                    } else {
                        throw new RuntimeException("Unknown attribute value type: " + term.getClass());
                    }
                }
            } else if (term instanceof String) {
                return (String) term;
            } else {
                throw new RuntimeException("Unknown attribute value type: " + term.getClass());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public final boolean contains(@NotNull Operator operator) {
        return false;
    }

    @NotNull
    @Override
    public Map<Signature, LogicFunction> getFunctions() {
        return new HashMap<>();
    }


    @NotNull
    @Override
    public String getAlias() {
        return libraryName;
    }

    protected Sequence<Solve.Response> predicate(boolean graphApplicable, boolean nodesApplicable, boolean edgesApplicable, Solve.Request<? extends ExecutionContext> request, Predicate<Element> predicate) {
        if (request.getArguments().get(0).isVar()) {
            Set<Element> candidates = GraphUtils.getGraphElements(graph, graphApplicable, nodesApplicable, edgesApplicable, predicate);
            return SequencesKt.asSequence(candidates.stream().map(element -> request.replySuccess(Substitution.of(request.getArguments().get(0).castToVar(), atom(element.getId())), null)).iterator());
        } else {
            String id = request.getArguments().get(0).castToAtom().getValue();
            Optional<Element> elementSelected = GraphUtils.getById(graph, id);
            if (!elementSelected.isPresent() || !graphApplicable && elementSelected.get() instanceof Graph || !edgesApplicable && elementSelected.get() instanceof Edge || !nodesApplicable && elementSelected.get() instanceof Node || !predicate.test(elementSelected.get())) {
                return SequencesKt.emptySequence();
            } else {
                return SequencesKt.sequenceOf(request.replySuccess(Substitution.empty(), null));
            }
        }
    }

    protected Sequence<Solve.Response> property(boolean graphApplicable, boolean nodesApplicable, boolean edgesApplicable, Solve.Request<? extends ExecutionContext> request, Function<Element, Optional<Term>> mapper) {
        if (request.getArguments().get(0).isVar()) {
            Set<Element> candidates = new HashSet<>();
            if (graphApplicable) {
                candidates.add(graph);
            }
            if (nodesApplicable) {
                graph.nodes().forEach(candidates::add);
            }
            if (edgesApplicable) {
                graph.edges().forEach(candidates::add);
            }
            Sequence<Solve.Response> res = SequencesKt.emptySequence();
            for (Element candidate : candidates) {
                Substitution.Unifier substitution = Substitution.of(request.getArguments().get(0).asVar(), Atom.of(candidate.getId()));
                Struct newQuery = request.getQuery().apply(substitution).asStruct();
                Sequence<Solve.Response> solutions = SequencesKt.map(SequencesKt.filter(request.solve(newQuery, Long.MAX_VALUE), Solution::isYes), solution -> request.replySuccess(substitution.plus(solution.getSubstitution()).castToUnifier(), null));
                res = SequencesKt.plus(res, solutions);
            }
            return res;
        }
        String id = request.getArguments().get(0).castToAtom().getValue();
        Optional<Element> elementSelected = GraphUtils.getById(graph, id);
        if (!elementSelected.isPresent()) {
            return SequencesKt.emptySequence();
        }
        if (!graphApplicable && elementSelected.get() instanceof Graph) {
            return SequencesKt.emptySequence();
        }
        if (!edgesApplicable && elementSelected.get() instanceof Edge) {
            return SequencesKt.emptySequence();
        }
        if (!nodesApplicable && elementSelected.get() instanceof Node) {
            return SequencesKt.emptySequence();
        }
        Optional<Term> shouldBe = mapper.apply(elementSelected.get());
        if (!shouldBe.isPresent()) {
            return SequencesKt.emptySequence();
        }
        if (request.getArguments().get(1).equals(shouldBe.get(), false)) {
            return SequencesKt.sequenceOf(request.replySuccess(Substitution.empty(), null));
        } else if (request.getArguments().get(1).isVar()) {
            return SequencesKt.sequenceOf(request.replySuccess(Substitution.of(request.getArguments().get(1).castToVar(), shouldBe.get()), null));
        } else {
            return SequencesKt.emptySequence();
        }
    }


    @Override
    public final boolean contains(@NotNull Signature signature) {
        return getFunctions().containsKey(signature) || getPrimitives().containsKey(signature);
    }

    @Override
    public final boolean hasPrimitive(@NotNull Signature signature) {
        return getPrimitives().containsKey(signature);
    }

    @Override
    public final boolean hasProtected(@NotNull Signature signature) {
        return false;
    }

    protected Sequence<Solve.Response> makeEdgeAtom(Solve.Request<? extends ExecutionContext> request, Var term, Predicate<Edge> filter) {
        return makeAtom(false, false, true, request, term, x -> filter.test((Edge) x));
    }


    protected Sequence<Solve.Response> makeAtom(boolean graphApplicable, boolean nodesApplicable, boolean edgesApplicable, Solve.Request<? extends ExecutionContext> request, Var firstEdgeTerm, Predicate<Element> filter) {
        Optional<Sequence<Solve.Response>> recursiveSolutions = GraphUtils.getGraphElements(graph, graphApplicable, nodesApplicable, edgesApplicable, filter).stream().map(edge -> {
            Struct newQuery = request.getQuery().apply(Substitution.of(firstEdgeTerm.castToVar(), atom(edge.getId()))).castToStruct();
            return SequencesKt.map(SequencesKt.filter(request.solve(newQuery, Long.MAX_VALUE), Solution::isYes),
                    solution -> request.replySuccess(solution.getSubstitution().plus(Substitution.of(firstEdgeTerm.castToVar(), atom(edge.getId()))).castToUnifier(), null));
        }).reduce(SequencesKt::plus);
        return recursiveSolutions.orElseGet(SequencesKt::emptySequence);
    }
}
