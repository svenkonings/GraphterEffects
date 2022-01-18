package com.github.meteoorkip.examples;

import com.github.meteoorkip.asc.GraphLibrary;
import it.unibo.tuprolog.core.Substitution;
import it.unibo.tuprolog.core.Term;
import it.unibo.tuprolog.core.operators.OperatorSet;
import it.unibo.tuprolog.solve.ExecutionContext;
import it.unibo.tuprolog.solve.Signature;
import it.unibo.tuprolog.solve.primitive.Primitive;
import it.unibo.tuprolog.solve.primitive.Solve;
import it.unibo.tuprolog.theory.Theory;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.ToIntFunction;

import static com.github.meteoorkip.prolog.TuProlog.intVal;

public class MyLibrary extends GraphLibrary {

    public MyLibrary(Graph graph) {
        super(graph, "MyLibrary");
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
        res.put(new Signature("hasloop", 1, false), request -> predicate(false, true, false, request, node -> ((Node)node).hasEdgeBetween(((Node)node))));
        res.put(new Signature("idlength", 2, false), request -> property(true, true, true, request, element -> Optional.of(intVal(element.getId().length()))));
        res.put(new Signature("triangle", 3, false), request -> {
            //Store terms in separate variables for readability.
            //The order of the terms is for this primitive irrelevant, so we sort them var-last for performance reasons.
            Term[] terms = request.getArguments().toArray(new Term[0]);
            Arrays.sort(terms, Comparator.comparingInt(term -> term.isVar() ? 1 : 0));

            //If the first term is a variable, recursively attempt it with every edge for which 'true' holds (all edges).
            if (terms[0].isVar()) {
                return makeEdgeAtom(request, terms[0].castToVar(), x -> true);
            }
            Edge edge1 = graph.getEdge(terms[0].castToAtom().getValue());
            //If the second term is a variable, recursively attempt it with every edge that extends the previous one.
            if (terms[1].isVar()) {
                return makeEdgeAtom(request, terms[1].castToVar(), edge -> edge != edge1 && edge.getSourceNode() == edge1.getTargetNode());
            }
            Edge edge2 = graph.getEdge(terms[1].castToAtom().getValue());
            //If the second term is a variable, recursively attempt it with every edge that closes the end of the second to the first.
            if (terms[2].isVar()) {
                return makeEdgeAtom(request, terms[2].castToVar(), edge -> edge != edge1 && edge != edge2 && edge.getSourceNode() == edge2.getTargetNode() && edge.getTargetNode() == edge1.getSourceNode());
            }
            Edge edge3 = graph.getEdge(terms[2].castToAtom().getValue());
            if (edge1 == null || edge2 == null || edge3 == null) {
                return SequencesKt.emptySequence();
            }
            boolean isTriangle = false;
            if (edge1.getTargetNode().equals(edge2.getSourceNode())) {
                isTriangle = edge2.getTargetNode().equals(edge3.getSourceNode()) && edge3.getTargetNode().equals(edge1.getSourceNode());
            } else if (edge1.getTargetNode().equals(edge3.getSourceNode())) {
                isTriangle = edge3.getTargetNode().equals(edge2.getSourceNode()) && edge2.getTargetNode().equals(edge1.getSourceNode());
            }
            if (isTriangle) {
                return SequencesKt.sequenceOf(request.replySuccess(Substitution.empty(), null));
            } else {
                return SequencesKt.emptySequence();
            }
        });
        return res;
    }


    @NotNull
    @Override
    public Theory getTheory() {
        return Theory.empty();
    }
}
