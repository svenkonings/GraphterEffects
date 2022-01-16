package com.github.meteoorkip.solver;


import com.github.meteoorkip.asc.ASCLibrary;
import com.github.meteoorkip.asc.GraphLibrary;
import com.github.meteoorkip.asc.GraphLibraryLoader;
import com.github.meteoorkip.prolog.PrologException;
import com.github.meteoorkip.prolog.TuProlog;
import com.github.meteoorkip.solver.library.DefaultVisLibrary;
import com.github.meteoorkip.solver.library.LibraryException;
import com.github.meteoorkip.solver.library.QueryConsumer;
import com.github.meteoorkip.solver.library.VisLibrary;
import com.github.meteoorkip.utils.TermUtils;
import it.unibo.tuprolog.core.Clause;
import it.unibo.tuprolog.core.Term;
import nl.svenkonings.jacomo.model.Model;
import nl.svenkonings.jacomo.solvers.chocosolver.ChocoSolver;
import nl.svenkonings.jacomo.solvers.ortools.OrToolsSolver;
import org.graphstream.graph.Graph;

import java.util.*;

import static com.github.meteoorkip.prolog.TuProlog.struct;
import static com.github.meteoorkip.prolog.TuProlog.var;

/**
 * The constraint solver.
 */
@SuppressWarnings("WeakerAccess")
public class Solver {
    /** The loader for the default graph library. */
    private final GraphLibraryLoader defaultGraphLibraryLoader;

    /** The mapping of names to library loaders. */
    private final Map<String, GraphLibraryLoader> graphLibraryLoaders;

    /** The default visualization library. */
    private final VisLibrary defaultVisLibrary;

    /** The mapping of names to visualization libraries. */
    private final Map<String, VisLibrary> visLibraries;

    /**
     * Creates a new solver.
     */
    public Solver() {
        defaultGraphLibraryLoader = ASCLibrary::new;
        graphLibraryLoaders = new LinkedHashMap<>();
        defaultVisLibrary = new DefaultVisLibrary();
        visLibraries = new LinkedHashMap<>();
    }

    /**
     * Add a new {@link GraphLibraryLoader} to the mapping with the given name.
     *
     * @param name   The given name.
     * @param loader The given {@link GraphLibraryLoader}.
     * @return The previous mapping, or null if there wasn't one.
     */
    public GraphLibraryLoader putGraphLibraryLoader(String name, GraphLibraryLoader loader) {
        return graphLibraryLoaders.put(name, loader);
    }

    /**
     * Get the {@link GraphLibraryLoader} associated to the given name.
     *
     * @param name The given name.
     * @return The {@link GraphLibraryLoader}.
     * @throws LibraryException If the loader doesn't exist.
     */
    public GraphLibraryLoader getGraphLibraryLoader(String name) {
        GraphLibraryLoader loader = graphLibraryLoaders.get(name);
        if (loader == null) {
            throw new LibraryException("Graph library %s not found", name);
        }
        return loader;
    }

    /**
     * Removes the {@link GraphLibraryLoader} associated to the given name.
     *
     * @param name The given name.
     * @return The removed {@link GraphLibraryLoader}.
     */
    public GraphLibraryLoader removeGraphLibraryLoader(String name) {
        return graphLibraryLoaders.remove(name);
    }

    /**
     * Get an instance of the {@link GraphLibrary} from the loader associated to the given name.
     *
     * @param name  The given name.
     * @param graph The graph used for the instance of this {@link GraphLibrary}.
     * @return The {@link GraphLibrary}.
     */
    public GraphLibrary getGraphLibrary(String name, Graph graph) {
        return getGraphLibraryLoader(name).getInstance(graph);
    }

    /**
     * Add a new {@link VisLibrary} to the mapping with the given name.
     *
     * @param name    The given name.
     * @param library The given {@link VisLibrary}.
     * @return The previous mapping, or null if there wasn't one.
     */
    public VisLibrary putVisLibrary(String name, VisLibrary library) {
        return visLibraries.put(name, library);
    }

    /**
     * Get the {@link VisLibrary} associated to the given name.
     *
     * @param name The given name.
     * @return The {@link VisLibrary}.
     * @throws LibraryException If the library doesn't exist.
     */
    public VisLibrary getVisLibrary(String name) {
        VisLibrary library = visLibraries.get(name);
        if (library == null) {
            throw new LibraryException("Vis library %s not found", name);
        }
        return library;
    }

    /**
     * Removes the {@link VisLibrary} associated to the given name.
     *
     * @param name The given name.
     * @return The removed {@link VisLibrary}.
     */
    public VisLibrary removeVisLibrary(String name) {
        return visLibraries.remove(name);
    }

    /**
     * Creates a {@link TuProlog} object with the given terms and loads all the visualition library imports specified
     * in the terms.
     *
     * @param terms The given terms.
     * @return The created {@link TuProlog} object.
     */
    public TuProlog loadProlog(Collection<Clause> terms) {
        return loadProlog(null, terms);
    }

    /**
     * Creates a {@link TuProlog} object with the given terms and loads all the graph and visualization library imports
     * specified in the terms.
     *
     * @param clauses The given terms.
     * @param graph The graph used for the {@link GraphLibrary}.
     * @return The created {@link TuProlog} object.
     */
    public TuProlog loadProlog(Graph graph, Collection<Clause> clauses) {
        TuProlog prolog;
        prolog = new TuProlog(clauses);
        if (graph != null) {
            getGraphLibraries(prolog, graph).forEach(library -> loadGraphLibrary(prolog, library));
        }
        getVisLibraries(prolog).forEach(library -> loadVisLibrary(prolog, library));
        return prolog;
    }

    /**
     * Solves the constraints and returns the {@link SolveResults}.
     *
     * @return The {@link SolveResults}.
     */
    public SolveResults solve(Collection<Clause> terms) throws PrologException {
        return solve(loadProlog(terms));
    }

    /**
     * Solves the constraints and returns the {@link SolveResults}.
     *
     * @return The {@link SolveResults}.
     */
    public SolveResults solve(Graph graph, Collection<Clause> terms) throws PrologException {
        TuProlog a = loadProlog(graph, terms);
        return solve(a);
    }

    /**
     * Solves the constraints and returns the {@link SolveResults}.
     *
     * @return The {@link SolveResults}.
     */
    public SolveResults solve(TuProlog prolog) {
        Model model = new Model();
        VisMap visMap = new VisMap(model);

        Set<VisLibrary> visLibraries = getVisLibraries(prolog);
        visLibraries.forEach(library -> solveVisLibrary(visMap, prolog, library));
        visLibraries.forEach(library -> applyVisLibraryConsumer(visMap, library));

//        ChocoSolver solver = new ChocoSolver();
        OrToolsSolver solver = new OrToolsSolver();
        boolean success = solver.solveAndUpdate(model);
        return new SolveResults(success, prolog, model, visMap);
    }

    /**
     * Get the set of {@link GraphLibrary}s that are imported in the theory of the given {@link TuProlog} object.
     *
     * @param prolog The given {@link TuProlog} object.
     * @param graph  The graph used for the {@link GraphLibrary}.
     * @return The set of {@link GraphLibrary}s.
     */
    private Set<GraphLibrary> getGraphLibraries(TuProlog prolog, Graph graph) {
        Set<GraphLibrary> libraries = new LinkedHashSet<>();
        libraries.add(defaultGraphLibraryLoader.getInstance(graph));
        prolog.solve(struct("graphLibrary", var("X"))).stream()
                .map(map -> map.get("X"))
                .map(TermUtils::stripQuotes)
                .map(name -> getGraphLibrary(name, graph))
                .forEach(libraries::add);
        return libraries;
    }

    /**
     * Get the set of {@link VisLibrary}s that are imported in the theory of the given {@link TuProlog} object.
     *
     * @param prolog The given {@link TuProlog} object.
     * @return The set of {@link VisLibrary}s.
     */
    private Set<VisLibrary> getVisLibraries(TuProlog prolog) {
        Set<VisLibrary> libraries = new LinkedHashSet<>();
        libraries.add(defaultVisLibrary);
        prolog.solve(struct("visLibrary", var("X"))).stream()
                .map(map -> map.get("X"))
                .map(TermUtils::stripQuotes)
                .map(this::getVisLibrary)
                .forEach(libraries::add);
        return libraries;
    }

    /**
     * Load the given {@link GraphLibrary} in the given {@link TuProlog} object.
     *
     * @param prolog  The given {@link TuProlog} object.
     * @param library The library to be loaded.
     */
    private static void loadGraphLibrary(TuProlog prolog, GraphLibrary library) {
        prolog.loadLibrary(library);
    }

    /**
     * Load the term list of the {@link VisLibrary} in the given {@link TuProlog} object.
     *
     * @param prolog  The given {@link TuProlog} object.
     * @param library The library containing the terms to be loaded.
     */
    private static void loadVisLibrary(TuProlog prolog, VisLibrary library) {
        prolog.addTheory(library.getClauses());
    }

    /**
     * Solve the queries of the mapping in the {@link VisLibrary} and apply the consequences.
     *
     * @param visMap  The mapping of visualization elements which is used while applying the consequences.
     * @param prolog  The {@link TuProlog} object in which the queries are solved.
     * @param library The given {@link VisLibrary}.
     */
    private static void solveVisLibrary(VisMap visMap, TuProlog prolog, VisLibrary library) {
        Set<Map.Entry<String, QueryConsumer>> entries = library.getQueries().entrySet();
        for (Map.Entry<String, QueryConsumer> entry : entries) {
            List<Map<String, Term>> result = prolog.solve(entry.getKey());
            entry.getValue().accept(visMap, result);
        }
    }

    /**
     * Calls the {@link VisLibrary#applyElemConsumer(VisElem)} method of the given library for every visualization
     * element.
     *
     * @param visMap  The given mapping containing the visualization elements.
     * @param library The given library.
     */
    private static void applyVisLibraryConsumer(VisMap visMap, VisLibrary library) {
        visMap.values().forEach(library::applyElemConsumer);
    }
}
