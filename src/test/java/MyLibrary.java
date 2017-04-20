import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import asrc.GraphLibrary;
import asrc.GraphLibraryLoader;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import utils.GraphUtils;

public class MyLibrary extends GraphLibrary {

    @Override
    public GraphLibraryLoader getLoader() {
        return null;
    }

    @Override
    public String getTheory() {
        //This makes sure the predicates called are Struct objects, and Graph elements retrieved are of the correct type.
        return "hasloop(X) :- node(X), hasloopsecond(X).\n" +
                "idlength(X,Y) :- node(X), idlengthsecond(X,Y).\n" +
                "idlength(X,Y) :- edge(X), idlengthsecond(X,Y).\n" +
                "idlength(X,Y) :- graph(X), idlengthsecond(X,Y).\n" +
                "triangle(X,Y,Z) :- edge(X), edge(Y), edge(Z), trianglesecond(X,Y,Z).";
    }

    public boolean hasloopsecond_1(Term node) {
        //Use bool for boolean predicates.
        return bool((Struct) node.getTerm(), n -> ((Node)n).hasEdgeBetween((Node) n), true, false, false);
    }

    public boolean idlengthsecond_2(Term element, Term length) {
        //Use numeric for numeric predicates.
        return numeric((Struct) element.getTerm(), length, n -> n.getId().length(), true, true, true);
    }

    public boolean trianglesecond_3(Term firstedge, Term secondedge, Term thirdedge) {
        //Retrieve Edge objects from the terms.
        Edge edge1 = (Edge) GraphUtils.getByID(graph, ((Struct)firstedge.getTerm()).getName());
        Edge edge2 = (Edge) GraphUtils.getByID(graph, ((Struct)secondedge.getTerm()).getName());
        Edge edge3 = (Edge) GraphUtils.getByID(graph, ((Struct)thirdedge.getTerm()).getName());

        //Checking if the three edges form a triangle.
        if (edge1.getTargetNode().equals(edge2.getSourceNode())) {
            return edge2.getTargetNode().equals(edge3.getSourceNode()) && edge3.getTargetNode().equals(edge1.getSourceNode());
        } else if (edge1.getTargetNode().equals(edge3.getSourceNode())) {
            return edge3.getTargetNode().equals(edge2.getSourceNode()) && edge2.getTargetNode().equals(edge1.getSourceNode());
        }
        //These edges are not a triangle.
        return false;
    }
}
