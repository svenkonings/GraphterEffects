package compiler.solver;

import alice.tuprolog.Term;
import org.chocosolver.solver.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a mapping of terms to visualization elements.
 */
public class TuMap {

    /** The model that is used for the visualization elements. */
    private final Model model;

    /** The internal mapping. */
    private final HashMap<String, VisElem> mapping;

    /**
     * Creates a new {@code VisMap} with the given model and an empty mapping.
     *
     * @param model The given model.
     */
    public TuMap(Model model) {
        this.model = model;
        this.mapping = new HashMap<>();
    }

    /**
     * Get or compute the visualization element that belongs to the given term.
     *
     * @param term The given term.
     * @return The visualization element.
     */
    public VisElem get(Term term) {
        return get(term.toString());
    }

    /**
     * Get or compute the visualization element that belongs to the given key.
     *
     * @param key The given key.
     * @return The visualization element.
     */
    public VisElem get(String key) {
        return mapping.computeIfAbsent(key, k -> new VisElem(model));
    }

    /**
     * Get a copy of the list of visualiztion elements of this map
     *
     * @return A list of visualization elements.
     */
    public List<VisElem> values() {
        return new ArrayList<>(mapping.values());
    }

    @Override
    public String toString() {
        return mapping.toString();
    }
}
