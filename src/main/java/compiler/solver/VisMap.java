package compiler.solver;

import org.chocosolver.solver.Model;

import java.util.*;

/**
 * Represents a mapping of keys (consisting of multiple strings) to visualization elements.
 */
public class VisMap {

    /** The model that is used for the visualization elements. */
    private final Model model;

    /** The internal mapping. */
    private final HashMap<List<String>, VisElem> mapping;

    /**
     * Creates a new {@code VisMap} with the given model and an empty mapping.
     *
     * @param model The given model.
     */
    public VisMap(Model model) {
        this.model = model;
        this.mapping = new HashMap<>();
    }

    /**
     * Get or compute the visualization element that belongs to the given key.
     *
     * @param key The given key.
     * @return The visualization element.
     */
    public VisElem get(String... key) {
        return get(Arrays.asList(key));
    }

    /**
     * Get or compute the visualization element that belongs to the given key.
     *
     * @param key The given key.
     * @return The visualization element.
     */
    public VisElem get(List<String> key) {
        return mapping.computeIfAbsent(Collections.unmodifiableList(key), k -> new VisElem(model));
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
