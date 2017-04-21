package solver;

import org.chocosolver.solver.Model;
import prolog.TuProlog;

/**
 * Class containing the results of the {@link Solver} after the solving process.
 */
public class SolveResults {

    /** Whether a solution was found. */
    private final boolean succes;

    /** The prolog object used during the solving process. */
    private final TuProlog prolog;

    /** The model used during the solving process. */
    private final Model model;

    /** The mapping of visualization elements used during the solving process. */
    private final VisMap visMap;

    /**
     * Creates a new instance with the given results.
     *
     * @param succes Whether a solution was found.
     * @param prolog The prolog object used during the solving process.
     * @param model  The model used during the solving process.
     * @param visMap The mapping of visualization elements used during the solving process.
     */
    public SolveResults(boolean succes, TuProlog prolog, Model model, VisMap visMap) {
        this.succes = succes;
        this.prolog = prolog;
        this.model = model;
        this.visMap = visMap;
    }

    /**
     * Returns whether a solution was found.
     *
     * @return Whether a solution was found.
     */
    public boolean isSucces() {
        return succes;
    }

    /**
     * Returns the prolog object used during the solving process.
     *
     * @return The prolog object used during the solving process.
     */
    public TuProlog getProlog() {
        return prolog;
    }

    /**
     * returns the model used during the solving process.
     *
     * @return The model used during the solving process.
     */
    public Model getModel() {
        return model;
    }

    /**
     * Returns the mapping of visualization elements used during the solving process.
     *
     * @return The mapping of visualization elements used during the solving process.
     */
    public VisMap getVisMap() {
        return visMap;
    }
}
