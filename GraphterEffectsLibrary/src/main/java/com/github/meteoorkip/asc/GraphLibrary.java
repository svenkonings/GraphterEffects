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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
            return makeElementAtom(request, request.getArguments().get(0).castToVar(), GraphUtils.getGraphElements(graph, graphApplicable, nodesApplicable, edgesApplicable, x -> true).stream());
        }
        Optional<Element> elementSelected = GraphUtils.getById(graph, request.getArguments().get(0).castToAtom().getValue());
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

    /**
     * When called from a Primitive, this method will substitute a Var with Atoms representing graph elements and perform
     * a recursive search using Prolog. The result of this method may then be returned from the Primitive without modification.
     * @param request request to solve
     * @param var variable to substitute to
     * @param stream graph elements that are suitable substitutions
     * @return responses to the request (gathered recursively)
     */
    protected Sequence<Solve.Response> makeElementAtom(Solve.Request<? extends ExecutionContext> request, Var var, Stream<? extends Element> stream) {
       return makeAtom(request, var, stream.map(e -> atom(e.getId())));
    }

    /**
     * When called from a Primitive, this method will substitute a Var with provided Atoms and perform
     * a recursive search using Prolog. The result of this method may then be returned from the Primitive without modification.
     * @param request request to solve
     * @param var variable to substitute to
     * @param atomStream suitable substitutions
     * @return responses to the request (gathered recursively)
     */
    protected Sequence<Solve.Response> makeAtom(Solve.Request<? extends ExecutionContext> request, Var var, Stream<Atom> atomStream) {
        Optional<Sequence<Solve.Response>> recursiveSolutions = atomStream.map(element -> {
            Struct newQuery = request.getQuery().apply(Substitution.of(var, element)).castToStruct();
            return SequencesKt.map(SequencesKt.filter(request.solve(newQuery, Long.MAX_VALUE), Solution::isYes),
                    solution -> request.replySuccess(solution.getSubstitution().plus(Substitution.of(var, element)).castToUnifier(), null));
        }).reduce(SequencesKt::plus);
        return recursiveSolutions.orElseGet(SequencesKt::emptySequence);
    }

}
