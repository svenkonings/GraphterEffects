package com.github.meteoorkip.utils;

import it.unibo.tuprolog.core.Atom;
import it.unibo.tuprolog.core.Struct;
import it.unibo.tuprolog.core.Term;
import it.unibo.tuprolog.core.Var;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;

import static com.github.meteoorkip.prolog.TuProlog.*;
import static com.github.meteoorkip.utils.StringUtils.parseInt;
import static com.github.meteoorkip.utils.StringUtils.removeQuotation;

/**
 * Class used for methods to generate {@link Term} Objects and related tasks.
 */
public class TermUtils {

    public static int termToInt(Term term) {
        return parseInt(stripQuotes(term));
    }

    public static String termToString(Term term) {
        if (term instanceof Atom) {
            return term.castToAtom().getValue();
        } else if (term instanceof Struct) {
            StringBuilder sb = new StringBuilder(term.castToStruct().getFunctor()).append("(");
            for (int i = 0; i < term.castToStruct().getArity(); i++) {
                sb.append(termToString(term.castToStruct().getArgAt(i)));
                if (i < term.castToStruct().getArity() - 1) {
                    sb.append(",");
                }
            }
            return sb.append(")").toString();
        } else if (term instanceof Var) {
            return term.castToVar().getName();
        } else if (term instanceof it.unibo.tuprolog.core.Integer) {
            return ((it.unibo.tuprolog.core.Integer)term).getIntValue().toString();
        } else {
            throw new UnsupportedOperationException(term.getClass().getName());
        }
    }

    public static String stripQuotes(Term term) {
        return removeQuotation(term.toString());
    }

    /**
     * Converts an {@link Element} to an {@link Term}.
     * In the case of an Graph, the generated expression will of the form graph(ID). <br>
     * In the case of an Node, it will be node(ID). <br>
     * In the case of an Edge, it will be edge(TargedID,SourceID,ID). <br>
     *
     * @param element {@link Element} to be converted to an {@link Term}
     * @return the generated {@link Term}
     */
    public static Term elementTerm(Element element) {
        if (element instanceof Graph) {
            return struct("graph", atom(element.getId()));
        } else if (element instanceof Edge) {
            Edge edge = (Edge) element;
            return struct("edge", atom(edge.getSourceNode().getId()), atom(edge.getTargetNode().getId()), atom(element.getId()));
        } else {
            return struct("node", atom(element.getId()));
        }
    }
}
