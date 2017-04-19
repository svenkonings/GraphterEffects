package solver;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import org.chocosolver.solver.Model;
import prolog.TuProlog;
import utils.TermUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The constraint solver.
 */
public class Solver {

    private final VisLibrary defaultLibrary;

    private final Map<String, VisLibrary> libraries;

    /**
     * Creates a new solver.
     */
    public Solver() {
        defaultLibrary = new DefaultVisLibrary();
        libraries = new HashMap<>();
    }

    public VisLibrary putLibrary(String name, VisLibrary library) {
        return libraries.put(name, library);
    }

    public VisLibrary getLibrary(String name) {
        return libraries.get(name);
    }

    public VisLibrary removeLibrary(String name) {
        return libraries.remove(name);
    }

    /**
     * Solves the constraints and returns a {@link List} of visualization elements.
     *
     * @return The {@link List} of visualization elements.
     */
    public VisMap solve(TuProlog prolog) throws InvalidTheoryException, SolveException {
        List<VisLibrary> libraries = prolog.solve("visLibrary(X)").stream()
                .map(Map::values)
                .map(Collection::iterator)
                .filter(Iterator::hasNext)
                .map(Iterator::next)
                .map(TermUtils::stripQuotes)
                .map(this::getLibrary)
                .collect(Collectors.toList());

        loadLibrary(prolog, defaultLibrary);
        libraries.forEach(library -> loadLibrary(prolog, library));

        Model model = new Model();
        VisMap visMap = new VisMap(model);

        solveLibrary(visMap, prolog, defaultLibrary);
        libraries.forEach(library -> solveLibrary(visMap, prolog, library));

        setLibraryDefaults(visMap, defaultLibrary);
        libraries.forEach(library -> setLibraryDefaults(visMap, library));

        boolean succes = model.getSolver().solve();
        if (!succes) {
            throw new SolveException(visMap, "No solution found");
        }
        return visMap;
    }

    public static void loadLibrary(TuProlog prolog, VisLibrary library) {
        if (library == null) {
            throw new LibraryException("Library not found");
        }
        try {
            prolog.addTheory(library.getTerms().toArray(new Term[0]));
        } catch (InvalidTheoryException e) {
            throw new LibraryException(e);
        }
    }

    public static void solveLibrary(VisMap visMap, TuProlog prolog, VisLibrary library) {
        library.getQueries().forEach((query, queryConsumer) -> queryConsumer.accept(visMap, prolog.solve(query)));
    }

    public static void setLibraryDefaults(VisMap visMap, VisLibrary library) {
        visMap.values().forEach(library::setDefaults);
    }
}
