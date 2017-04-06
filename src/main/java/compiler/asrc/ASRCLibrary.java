package compiler.asrc;


import alice.tuprolog.Int;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import alice.tuprolog.Var;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import utils.GraphUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static compiler.prolog.TuProlog.struct;
import static compiler.prolog.TuProlog.intVal;
import static org.junit.Assert.assertTrue;


public class ASRCLibrary extends GraphLibrary {


    public ASRCLibrary(Graph g) {
        super(g);
    }

    public boolean graph_1(Term ID) {
        if (ID instanceof Struct) {
            return ((Struct) ID).getName().equals(graph.getId());
        } else if (ID instanceof Var) {
            return ID.unify(getEngine(), struct(graph.getId()));
        }
        return false;
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

    public boolean mixed_1(Term ID) {
        boolean isMixed = !GraphUtils.isFullyDirected(graph) && !GraphUtils.isFullyUndirected(graph);
        if (ID instanceof Var && isMixed) {
            return ID.unify(getEngine(), struct(graph.getId()));
        }
        return  (ID instanceof Struct && ((Struct) ID).getName().equals(graph.getId()) && isMixed);
    }

    public boolean edgecount_2(Term ID, Term count) {
        return numeric(ID, count, n -> ((Graph)n).getEdgeCount(), false, false, true);
    }

    public boolean singlegraph_1(Term ID) {
        return ID instanceof Struct && ((Struct) ID).getName().equals(graph.getId()) && graph instanceof SingleGraph;
    }

    public boolean multigraph_1(Term ID) {
        return ID instanceof Struct && ((Struct) ID).getName().equals(graph.getId()) && graph instanceof SingleGraph;
    }

    public boolean nodecount_2(Term ID, Term count) {
        return numeric(ID, count, n -> ((Graph)n).getNodeCount(), false, false, true);
    }

    public boolean componentcount_2(Term ID, Term count) {
        return numeric(ID, count, n -> GraphUtils.ConnectedComponentsCount(((Graph) n)), false, false, true);
    }

    public boolean attributecount_2(Term ID, Term count) {
        return numeric(ID, count, Element::getAttributeCount, true, true, true);
    }

    public boolean isconnected_1(Term ID) {
        return componentcount_2(ID, intVal(1)) || componentcount_2(ID, intVal(0));
    }

    public boolean scary_1(Term maybe) {
        maybe = maybe.getTerm();
        if (maybe.unify(getEngine(), struct("notthisone"))) {
            return true;
        } else if (maybe.unify(getEngine(), struct("alsonotthisone"))) {
            return true;
        } else if (maybe.unify(getEngine(), struct("thisone"))) {
            return true;
        }
        System.out.println(":(");
        return false;
    }

    public boolean scarysecond_1(Term maybe) {
        return maybe.unify(getEngine(), struct("thisone"));
    }



    public boolean label_2(Term ID, Term label) {
            if (ID instanceof Struct && label instanceof Struct) {
                return GraphUtils.getByID(graph, ((Struct) ID).getName()).getAttribute("label").toString().equals(((Struct) label).getName());
            } else if (ID instanceof Var && label instanceof Struct) {
                for (Element e : GraphUtils.elements(graph, true, true, false)) {
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
                for (Element e : GraphUtils.elements(graph, true, true, false)) {
                    if (label_2(struct(e.getId()), label) && ID.unify(getEngine(), struct(e.getId()))) {
                        return true;
                    }
                }
            }
            return false;
    }






}
