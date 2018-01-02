package com.github.meteoorkip.utils;

import alice.tuprolog.Term;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;

import static com.github.meteoorkip.prolog.TuProlog.struct;
import static com.github.meteoorkip.prolog.TuProlog.term;
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
        return term.toString().replaceAll("'", "");
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
            return struct("graph", term(element.getId()));
        } else if (element instanceof Edge) {
            Edge edge = (Edge) element;
            return struct("edge", term(edge.getSourceNode().getId()), term(edge.getTargetNode().getId()), term(element.getId()));
        } else {
            return struct("node", term(element.getId()));
        }
    }
}
