package solver;

import alice.tuprolog.InvalidLibraryException;
import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import asrc.ASRCLibrary;
import asrc.GraphLibrary;
import org.chocosolver.solver.Model;
import org.graphstream.graph.Graph;
import prolog.TuProlog;
import utils.TermUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The constraint solver.
 */
public class Solver {

    private final GraphLibrary defaultGraphLibrary;

    private final Map<String, GraphLibrary> graphLibraries;

    private final VisLibrary defaultVisLibrary;

    private final Map<String, VisLibrary> visLibraries;

    /**
     * Creates a new solver.
     */
    public Solver() {
        defaultGraphLibrary = new ASRCLibrary();
        graphLibraries = new HashMap<>();
        defaultVisLibrary = new DefaultVisLibrary();
        visLibraries = new HashMap<>();
    }

    public GraphLibrary putGraphLibrary(String name, GraphLibrary library) {
        return graphLibraries.put(name, library);
    }

    public GraphLibrary getGraphLibrary(String name) {
        return graphLibraries.get(name);
    }

    public GraphLibrary removeGraphLibrary(String name) {
        return graphLibraries.remove(name);
    }

    public VisLibrary putVisLibrary(String name, VisLibrary library) {
        return visLibraries.put(name, library);
    }

    public VisLibrary getVisLibrary(String name) {
        return visLibraries.get(name);
    }

    public VisLibrary removeVisLibrary(String name) {
        return visLibraries.remove(name);
    }

    /**
     * Solves the constraints and returns a {@link List} of visualization elements.
     *
     * @return The {@link List} of visualization elements.
     */
    public VisMap solve(Graph graph, List<Term> terms) throws InvalidTheoryException, SolveException {
        TuProlog prolog = new TuProlog(terms);

        List<GraphLibrary> graphLibraries = prolog.solve("graphLibrary(X)").stream()
                .map(Map::values)
                .map(Collection::iterator)
                .filter(Iterator::hasNext)
                .map(Iterator::next)
                .map(TermUtils::stripQuotes)
                .map(this::getGraphLibrary)
                .collect(Collectors.toList());

        List<VisLibrary> visLibraries = prolog.solve("visLibrary(X)").stream()
                .map(Map::values)
                .map(Collection::iterator)
                .filter(Iterator::hasNext)
                .map(Iterator::next)
                .map(TermUtils::stripQuotes)
                .map(this::getVisLibrary)
                .collect(Collectors.toList());

        loadGraphLibrary(prolog, graph, defaultGraphLibrary);
        graphLibraries.forEach(library -> loadGraphLibrary(prolog, graph, library));

        loadVisLibrary(prolog, defaultVisLibrary);
        visLibraries.forEach(library -> loadVisLibrary(prolog, library));

        Model model = new Model();
        VisMap visMap = new VisMap(model);

        solveVisLibrary(visMap, prolog, defaultVisLibrary);
        visLibraries.forEach(library -> solveVisLibrary(visMap, prolog, library));

        setVisLibraryDefaults(visMap, defaultVisLibrary);
        visLibraries.forEach(library -> setVisLibraryDefaults(visMap, library));

        boolean succes = model.getSolver().solve();
        if (!succes) {
            throw new SolveException(visMap, "No solution found");
        }
        return visMap;
    }

    public static void loadGraphLibrary(TuProlog prolog, Graph graph, GraphLibrary library) {
        if (library == null) {
            throw new LibraryException("Library not found");
        }
        library.setGraph(graph);
        try {
            prolog.loadLibrary(library);
        } catch (InvalidLibraryException e) {
            throw new LibraryException(e);
        }
    }

    public static void loadVisLibrary(TuProlog prolog, VisLibrary library) {
        if (library == null) {
            throw new LibraryException("Library not found");
        }
        try {
            prolog.addTheory(library.getTerms().toArray(new Term[0]));
        } catch (InvalidTheoryException e) {
            throw new LibraryException(e);
        }
    }

    public static void solveVisLibrary(VisMap visMap, TuProlog prolog, VisLibrary library) {
        library.getQueries().forEach((query, queryConsumer) -> queryConsumer.accept(visMap, prolog.solve(query)));
    }

    public static void setVisLibraryDefaults(VisMap visMap, VisLibrary library) {
        visMap.values().forEach(library::setDefaults);
    }
}
