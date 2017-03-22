package utils;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import za.co.wstoop.jatalog.Expr;

/**
 * Created by user on 22-3-2017.
 */
public class ExprUtils {

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
