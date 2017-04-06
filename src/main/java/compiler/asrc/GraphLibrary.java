package compiler.asrc;


import alice.tuprolog.*;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import utils.GraphUtils;
import utils.StringUtils;

import static compiler.prolog.TuProlog.intVal;
import static compiler.prolog.TuProlog.struct;

public abstract class GraphLibrary extends Library {

    public Graph graph;

    public GraphLibrary(Graph g) {
        this.graph = g;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    boolean attribute(Term ID, Term attrname, Term value) {
        Element rID = null;
        String rname = null;
        String rstringvalue = null;
        int rintvalue = 0;
        if (attrname instanceof Struct) {
            rname = StringUtils.removeQuotation(((Struct) attrname).getName());
        }
        if (value instanceof Struct) {
            rstringvalue = StringUtils.removeQuotation(((Struct) value).getName());
        }
        if (ID instanceof Struct) {
            rID = GraphUtils.getByID(graph, ((Struct) ID).getName());
        }
        if (value instanceof Int) {
            rintvalue = ((Int) value).intValue();
        }

        if (ID instanceof Struct && attrname instanceof Struct && value instanceof Struct) {
            return rID.getAttribute(rname).equals(rintvalue);
        } else if (ID instanceof Struct && attrname instanceof Struct && value instanceof Var) {
            return value.unify(getEngine(), rID.getAttribute(rname));
        } else if (ID instanceof Struct && attrname instanceof Struct && value instanceof Int) {
            return rID.getNumber(rname) == (double)rintvalue;
        } else if (ID instanceof Struct && attrname instanceof Var && value instanceof Struct) {
            for (String key : rID.getEachAttributeKey()) {
                if (rID.getAttribute(key).equals(rstringvalue)) {
                    boolean res = attrname.unify(getEngine(), new Struct("\"" + key + "\""));
                    if (res) {
                        return true;
                    }
                }
            }
        } else if (ID instanceof Struct && attrname instanceof Var && value instanceof Var) {
            for (String key : rID.getEachAttributeKey()) {
                boolean res = attrname.unify(getEngine(), new Struct("\"" + key + "\"")) && value.unify(getEngine(), intVal(rintvalue));
                if (res) {
                    return true;
                }
            }
        } else if (ID instanceof Struct && attrname instanceof Var && value instanceof Int) {
            for (String key : rID.getEachAttributeKey()) {
                if (rID.getNumber(key) == (double) rintvalue) {
                    boolean res = attrname.unify(getEngine(), new Struct("\"" + key + "\""));
                    if (res) {
                        return true;
                    }
                }
            }
        } else if (ID instanceof Var && attrname instanceof Struct && value instanceof Struct) {
            for (Element e : GraphUtils.elements(graph, true, true, true)) {
                if (e.hasAttribute(rname) && e.getAttribute(rname).equals(rstringvalue)) {
                    boolean res = ID.unify(getEngine(), struct(e.getId()));
                    if (res) {
                        return true;
                    }
                }
            }
        } else if (ID instanceof Var && attrname instanceof Struct && value instanceof Var) {

        } else if (ID instanceof Var && attrname instanceof Struct && value instanceof Int) {

        } else if (ID instanceof Var && attrname instanceof Var && value instanceof Struct) {

        } else if (ID instanceof Var && attrname instanceof Var && value instanceof Var) {

        } else if (ID instanceof Var && attrname instanceof Var && value instanceof Int) {

        }
        return false;
    }

    boolean numeric(Term ID, Term count, GetNumber real, boolean nodes, boolean edges, boolean graphs) {
        if (ID instanceof Struct) {
            if (count instanceof Int) {
                return ((Int) count).intValue() == real.get(GraphUtils.getByID(graph, ((Struct) ID).getName()));
            } else if (count instanceof Var) {
                return count.unify(getEngine(), intVal(real.get(GraphUtils.getByID(graph,((Struct) ID).getName()))));
            }
        } else if (ID instanceof Var) {
            if (count instanceof Int) {
                for (Element e : GraphUtils.elements(graph, nodes, edges, graphs)) {
                    if (real.get(e)==((Int) count).intValue()) {
                        return ID.unify(getEngine(), struct(e.getId()));
                    }
                }
            } else if (count instanceof Var) {
                for (Element e : GraphUtils.elements(graph, nodes, edges, graphs)) {
                    return ID.unify(getEngine(), struct(e.getId())) && count.unify(getEngine(), intVal(real.get(e)));
                }
            }
        }
        return false;
    }

    public interface GetNumber {
        int get(Element n);
    }
}
