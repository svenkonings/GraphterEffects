package compiler.asrc;


import alice.tuprolog.*;
import org.graphstream.graph.*;
import utils.GraphUtils;
import utils.StringUtils;

import java.lang.Double;

import static compiler.prolog.TuProlog.intVal;
import static compiler.prolog.TuProlog.struct;

@SuppressWarnings("WeakerAccess")
public abstract class GraphLibrary extends Library {

    public Graph graph;

    public GraphLibrary(Graph g) {
        this.graph = g;
    }

    @Override
    public abstract String getTheory();

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    //supports backtracking but no generation
    public boolean attributesecond_3(Term ID, Term attrname, Term value) {
        try {
            value = value.getTerm();
            String rname = StringUtils.removeQuotation(((Struct)attrname.getTerm()).getName());
            String rstringvalue = value instanceof Struct ? ((Struct) value.getTerm()).getName() : null;
            int rintvalue = value instanceof Int ? ((Int) value).intValue() : 0;
            Element rID = GraphUtils.getByID(graph, ((Struct)ID.getTerm()).getName());

            if (!rID.hasAttribute(rname)) {
                return false;
            }

            if (value instanceof Struct) {
                Object res = rID.getAttribute(rname);
                if (res instanceof String) {
                    return /*StringUtils.stripOnce(((String)res)).equals(rstringvalue) ||*/ res.equals(rstringvalue);
                }
                return rID.getAttribute(rname).equals(rstringvalue);
            } else if (value instanceof Var) {
                Object atvalue = rID.getAttribute(rname);
                if (atvalue instanceof Double && (Double) atvalue == Math.floor((Double) atvalue) ) {
                    value.unify(getEngine(), intVal((int)((Double) atvalue).doubleValue()));
                } else {
                    value.unify(getEngine(), struct(StringUtils.ObjectToString(rID.getAttribute(rname))));
                }
                return true;

            } else if (value instanceof Int) {
                return rID.getNumber(rname) == (double) rintvalue;
            }
            return false;
        }catch (Exception | AssertionError e) {
            return false;
        }
    }

    //does not support backtracking
    boolean numeric(Struct key, Term value, GetNumber actualnumber, boolean nodes, boolean edges, boolean graphs) {
        Element gotten = GraphUtils.getByID(graph, key.getName());
        if (!nodes && gotten instanceof Node) {
            return false;
        }
        if (!edges && gotten instanceof Edge) {
            return false;
        }
        if (!graphs && gotten instanceof Graph) {
            return false;
        }

        if (value instanceof Int) {
            return ((Int) value).intValue() == actualnumber.get(gotten);
        } else if (value instanceof Var) {
            return value.unify(getEngine(), intVal(actualnumber.get(gotten)));
        }
        return false;
    }

    //does not support backtracking
    boolean bool(Struct key, GetBool actualbool, boolean nodes, boolean edges, boolean graphs) {
        Element gotten = GraphUtils.getByID(graph, key.getName());
        if (!nodes && gotten instanceof Node) {
            return false;
        }
        if (!edges && gotten instanceof Edge) {
            return false;
        }
        return !(!graphs && gotten instanceof Graph) && actualbool.get(gotten);
    }


    public interface GetNumber {
        int get(Element n);
    }

    public interface GetBool {
        boolean get(Element n);
    }
}
