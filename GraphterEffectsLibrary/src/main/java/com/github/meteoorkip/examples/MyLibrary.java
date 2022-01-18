package com.github.meteoorkip.examples;

import com.github.meteoorkip.asc.GraphLibrary;
import com.github.meteoorkip.utils.CollectionUtils;
import it.unibo.tuprolog.core.Substitution;
import it.unibo.tuprolog.core.Term;
import it.unibo.tuprolog.core.operators.OperatorSet;
import it.unibo.tuprolog.solve.Signature;
import it.unibo.tuprolog.solve.primitive.Primitive;
import it.unibo.tuprolog.theory.Theory;
import kotlin.sequences.SequencesKt;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.jetbrains.annotations.NotNull;

import java.util.*;

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
                return makeElementAtom(request, terms[0].castToVar(), graph.edges());
            }
            Edge edge1 = graph.getEdge(terms[0].castToAtom().getValue());
            if (edge1 == null) {
                return SequencesKt.emptySequence();
            }
            //If the second term is a variable, recursively attempt it with every edge that extends the previous one.
            if (terms[1].isVar()) {
                return makeElementAtom(request, terms[1].castToVar(), edge1.getTargetNode().leavingEdges());
            }
            Edge edge2 = graph.getEdge(terms[1].castToAtom().getValue());
            if (edge2 == null) {
                return SequencesKt.emptySequence();
            }
            //If the second term is a variable, recursively attempt it with every edge that closes the end of the second to the first.
            if (terms[2].isVar()) {
                return makeElementAtom(request, terms[2].castToVar(), edge2.getTargetNode().leavingEdges().filter(e -> e.getTargetNode() == edge1.getSourceNode()));
            }
            Edge edge3 = graph.getEdge(terms[2].castToAtom().getValue());
            if (edge3 == null || CollectionUtils.setOf(edge1.getSourceNode(), edge2.getSourceNode(), edge3.getSourceNode()).size() < 3) {
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
