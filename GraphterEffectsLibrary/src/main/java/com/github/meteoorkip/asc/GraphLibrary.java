package com.github.meteoorkip.asc;


import alice.tuprolog.*;
import com.github.meteoorkip.utils.GraphUtils;
import com.github.meteoorkip.utils.StringUtils;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.lang.Double;

import static com.github.meteoorkip.prolog.TuProlog.intVal;
import static com.github.meteoorkip.prolog.TuProlog.struct;

/**
 * Prolog library used for {@link Graph} predicates.
 */
@SuppressWarnings("WeakerAccess")
public abstract class GraphLibrary extends Library {

    /**
     * {@link Graph} on which predicates are performed.
     */
    protected final Graph graph;

    /**
     * Creates a new {@link GraphLibrary}.
     *
     * @param graph {@link Graph} on which predicates are performed.
     */
    public GraphLibrary(Graph graph) {
        this.graph = graph;
    }

    /**
     * Returns a {@link GraphLibraryLoader} that returns an instance of this library.
     *
     * @return A {@link GraphLibraryLoader} that returns an instance of this library.
     */
    public abstract GraphLibraryLoader getLoader();

    /**
     * Returns a Prolog theory to be added to the Prolog solver. Use an empty {@link String} if unwished for.
     *
     * @return a Prolog theory.
     */
    @Override
    public abstract String getTheory();


    /**
     * Method used for predicates with two arguments of which the second is a numeric property.
     *
     * @param ID           ID of the element with the numeric property.
     * @param numericValue Numeric value of this property.
     * @param numberGetter {@link GetNumber} that specifices what numeric value holds for this object.
     * @param nodes        Whether this method is applicable for {@link org.graphstream.graph.Node} objects.
     * @param edges        Whether this method is applicable for {@link org.graphstream.graph.Edge} objects.
     * @param graphs       Whether this method is applicable for {@link org.graphstream.graph.Graph} objects.
     * @return Whether unification was possible.
     */
    protected boolean numeric(Struct ID, Term numericValue, GetNumber numberGetter, boolean nodes, boolean edges, boolean graphs) {
        String name = ID.getName();
        Element gotten = GraphUtils.getByID(graph, name);
        if (!nodes && gotten instanceof Node) {
            return false;
        }
        if (!edges && gotten instanceof Edge) {
            return false;
        }
        if (!graphs && gotten instanceof Graph) {
            return false;
        }

        if (numericValue instanceof Int) {
            return ((Int) numericValue).intValue() == numberGetter.get(gotten);
        } else if (numericValue instanceof Var) {
            return numericValue.unify(getEngine(), intVal(numberGetter.get(gotten)));
        }
        return false;
    }

    /**
     * Method used for predicates with one argument that exists based on a condition.
     * Does not support {@link Var} IDs.
     *
     * @param ID         ID of which the condition holds.
     * @param actualbool {@link GetBool} that specifies whether the condition holds.
     * @param nodes      Whether this method is applicable for {@link org.graphstream.graph.Node} objects.
     * @param edges      Whether this method is applicable for {@link org.graphstream.graph.Edge} objects.
     * @param graphs     Whether this method is applicable for {@link org.graphstream.graph.Graph} objects.
     * @return Whether the condition holds.
     */
    protected boolean bool(Struct ID, GetBool actualbool, boolean nodes, boolean edges, boolean graphs) {
        Element gotten = GraphUtils.getByID(graph, ID.getName());
        return !(!nodes && gotten instanceof Node) && !(!edges && gotten instanceof Edge) && !(!graphs && gotten instanceof Graph) && actualbool.get(gotten);
    }


    /**
     * Interface that returns numeric information about a {@link Element}.
     */
    protected interface GetNumber {
        int get(Element n);
    }

    /**
     * Interface that returns boolean information about a {@link Element}.
     */
    protected interface GetBool {
        boolean get(Element n);
    }
}
