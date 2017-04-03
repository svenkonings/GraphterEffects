package utils;

import alice.tuprolog.Term;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;

import static compiler.prolog.TuProlog.struct;
import static compiler.prolog.TuProlog.term;

/**
 * Class used for methods to generate {@link Term} Objects and related tasks.
 */
public final class TermUtils {


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
            return struct("edge", term(edge.getTargetNode().getId()), term(edge.getSourceNode().getId()), term(element.getId()));
        } else {
            return struct("node", term(element.getId()));
        }
    }
}
