package compiler.asrc;


import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import alice.tuprolog.Var;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import utils.GraphUtils;

import java.util.HashSet;
import java.util.Set;

import static compiler.prolog.TuProlog.struct;
import static org.junit.Assert.assertTrue;


public class ASRCLibrary extends GraphLibrary {


    public ASRCLibrary(Graph g) {
        super(g);
    }

    public boolean directed_1(Term ID) {
        if (ID instanceof Struct) {
            Element elem = GraphUtils.getByID(graph, ((Struct) ID).getName());
            if (elem instanceof Graph) {
                return GraphUtils.isFullyDirected(graph);
            } else if (elem instanceof Edge) {
                return ((Edge) elem).isDirected();
            }
        } else if (ID instanceof Var) {
            for (Edge e : graph.getEdgeSet()) {
                if (e.isDirected()) {
                    return ID.unify(getEngine(), struct(e.getId()));
                }
            }
            if (GraphUtils.isFullyDirected(graph)) {
                return ID.unify(getEngine(), struct(graph.getId()));
            }
        }
        return false;
    }



    public boolean label_2(Term ID, Term label) {
            if (ID instanceof Struct && label instanceof Struct) {
                return GraphUtils.getByID(graph, ((Struct) ID).getName()).getAttribute("label").toString().equals(((Struct) label).getName());
            } else if (ID instanceof Var && label instanceof Struct) {
                for (Element e : GraphUtils.elements(graph)) {
                    if (e.hasAttribute("label") && e.getAttribute("label").toString().equals(((Struct) label).getName())) {
                        if (ID.unify(getEngine(), struct(e.getId()))) {
                            return true;
                        }
                    }
                }
            } else if (ID instanceof Struct && label instanceof Var) {
                Element e = GraphUtils.getByID(graph, ((Struct) ID).getName());
                if (e.hasAttribute("label")) {
                    return label.unify(getEngine(), struct(e.getAttribute("label").toString()));
                }
            } else if (ID instanceof Var && label instanceof Var) {
                for (Element e : GraphUtils.elements(graph)) {
                    if (label_2(struct(e.getId()), label) && ID.unify(getEngine(), struct(e.getId()))) {
                        return true;
                    }
                }
            }
            return false;
    }






}
