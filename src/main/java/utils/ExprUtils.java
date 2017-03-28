package utils;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import za.co.wstoop.jatalog.Expr;
/**
 * Class used for methods to generate {@link za.co.wstoop.jatalog.Expr} Objects and related tasks.
 */
public final class ExprUtils {


    /**
     * Converts an {@link Element} to an {@link za.co.wstoop.jatalog.Expr}.
     * In the case of an Graph, the generated expression will of the form graph(ID). <br>
     * In the case of an Node, it will be node(ID). <br>
     * In the case of an Edge, it will be edge(TargedID,SourceID,ID). <br>
     * @param element {@link Element} to be converted to an {@link za.co.wstoop.jatalog.Expr}
     * @return the generated {@link za.co.wstoop.jatalog.Expr}
     */
    public static Expr elementExpr(Element element){
        if(element instanceof Graph) {
            return Expr.expr("graph", element.getId());
        } else if (element instanceof Edge){
            Edge edge = (Edge) element;
            return Expr.expr("edge", edge.getTargetNode().getId(), edge.getSourceNode().getId(),element.getId());
        } else {
            return Expr.expr("node", element.getId());
        }
    }
}
