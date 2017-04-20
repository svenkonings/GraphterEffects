package solver;

import alice.tuprolog.InvalidLibraryException;
import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import asrc.ASRCLibrary;
import asrc.GraphLibrary;
import asrc.GraphLibraryLoader;
import org.chocosolver.solver.Model;
import org.graphstream.graph.Graph;
import prolog.TuProlog;
import solver.library.DefaultVisLibrary;
import solver.library.LibraryException;
import solver.library.VisLibrary;
import utils.TermUtils;

import java.util.*;

/**
 * The constraint solver.
 */
@SuppressWarnings("WeakerAccess")
public class Solver {
    private final GraphLibraryLoader defaultGraphLibraryLoader;
    private final Map<String, GraphLibraryLoader> graphLibraryLoaders;

    private final VisLibrary defaultVisLibrary;
    private final Map<String, VisLibrary> visLibraries;

    /**
     * Creates a new solver.
     */
    public Solver() {
        defaultGraphLibraryLoader = ASRCLibrary::new;
        graphLibraryLoaders = new HashMap<>();
        defaultVisLibrary = new DefaultVisLibrary();
        visLibraries = new HashMap<>();
    }

    public GraphLibraryLoader putGraphLibraryLoader(String name, GraphLibraryLoader loader) {
        return graphLibraryLoaders.put(name, loader);
    }

    public GraphLibraryLoader getGraphLibraryLoader(String name) {
        GraphLibraryLoader loader = graphLibraryLoaders.get(name);
        if (loader == null) {
            throw new LibraryException("Graph library %s not found", name);
        }
        return loader;
    }

    public GraphLibraryLoader removeGraphLibraryLoader(String name) {
        return graphLibraryLoaders.remove(name);
    }

    public GraphLibrary getGraphLibrary(String name, Graph graph) {
        return getGraphLibraryLoader(name).getInstance(graph);
    }

    public VisLibrary putVisLibrary(String name, VisLibrary library) {
        return visLibraries.put(name, library);
    }

    public VisLibrary getVisLibrary(String name) {
        VisLibrary library = visLibraries.get(name);
        if (library == null) {
            throw new LibraryException("Vis library %s not found", name);
        }
        return library;
    }

    public VisLibrary removeVisLibrary(String name) {
        return visLibraries.remove(name);
    }

    public TuProlog getProlog(Collection<Term> terms) {
        return getProlog(null, terms);
    }

    public TuProlog getProlog(Graph graph, Collection<Term> terms) {
        TuProlog prolog;
        try {
            prolog = new TuProlog(terms);
        } catch (InvalidTheoryException e) {
            throw new LibraryException(e);
        }

        if (graph != null) {
            Set<GraphLibrary> graphLibraries = getGraphLibraries(prolog, graph);
            graphLibraries.forEach(library -> loadGraphLibrary(prolog, library));
        }
        getVisLibraries(prolog).forEach(library -> loadVisLibrary(prolog, library));
        return prolog;
    }

    public SolveResults solve(Collection<Term> terms) {
        return solve(getProlog(terms));
    }

    public SolveResults solve(Graph graph, Collection<Term> terms) {
        return solve(getProlog(graph, terms));
    }

    /**
     * FIXME
     * Solves the constraints and returns a {@link List} of visualization elements.
     *
     * @return The {@link List} of visualization elements.
     */
    public SolveResults solve(TuProlog prolog) {
        Model model = new Model();
        VisMap visMap = new VisMap(model);

        Set<VisLibrary> visLibraries = getVisLibraries(prolog);
        visLibraries.forEach(library -> solveVisLibrary(visMap, prolog, library));
        visLibraries.forEach(library -> setVisLibraryDefaults(visMap, library));

        boolean succes = model.getSolver().solve();
        return new SolveResults(succes, prolog, model, visMap);
    }

    private Set<GraphLibrary> getGraphLibraries(TuProlog prolog, Graph graph) {
        Set<GraphLibrary> libraries = new HashSet<>();
        libraries.add(defaultGraphLibraryLoader.getInstance(graph));
        prolog.solve("graphLibrary(X)").stream()
                .map(map -> map.get("X"))
                .map(TermUtils::stripQuotes)
                .map(name -> getGraphLibrary(name, graph))
                .forEach(libraries::add);
        return libraries;
    }

    private Set<VisLibrary> getVisLibraries(TuProlog prolog) {
        Set<VisLibrary> libraries = new HashSet<>();
        libraries.add(defaultVisLibrary);
        prolog.solve("visLibrary(X)").stream()
                .map(map -> map.get("X"))
                .map(TermUtils::stripQuotes)
                .map(this::getVisLibrary)
                .forEach(libraries::add);
        return libraries;
    }

    private static void loadGraphLibrary(TuProlog prolog, GraphLibrary library) {
        try {
            prolog.loadLibrary(library);
        } catch (InvalidLibraryException e) {
            throw new LibraryException(e);
        }
    }

    private static void loadVisLibrary(TuProlog prolog, VisLibrary library) {
        try {
            prolog.addTheory(library.getTerms().toArray(new Term[0]));
        } catch (InvalidTheoryException e) {
            throw new LibraryException(e);
        }
    }

    private static void solveVisLibrary(VisMap visMap, TuProlog prolog, VisLibrary library) {
        library.getQueries().forEach((query, queryConsumer) -> queryConsumer.accept(visMap, prolog.solve(query)));
    }

    private static void setVisLibraryDefaults(VisMap visMap, VisLibrary library) {
        visMap.values().forEach(library::setDefaults);
    }
}
