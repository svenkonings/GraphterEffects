package com.github.meteoorkip.solver;

import it.unibo.tuprolog.core.Term;
import nl.svenkonings.jacomo.model.Model;

import java.util.*;

/**
 * Represents a mapping of terms to visualization elements.
 */
public class VisMap {

    /** The model that is used for the visualization elements. */
    private final Model model;

    /** The internal mapping. */
    private final Map<String, VisElem> mapping;

    /** The default lower bound of a variable. */
    private int lowerBound;

    /** The default upper bound of a variable. */
    private int upperBound;

    /**
     * Creates a new {@code VisMap} with the given model and an empty mapping.
     *
     * @param model The given model.
     */
    public VisMap(Model model) {
        this(model, new LinkedHashMap<>());
    }

    /**
     * Creates a new {@code VisMap} with the given model and the given mapping.
     *
     * @param model   The given model.
     * @param mapping The given mapping.
     */
    public VisMap(Model model, Map<String, VisElem> mapping) {
        this.model = model;
        this.mapping = mapping;
        this.lowerBound = 0;
        this.upperBound = 2000;
    }

    /**
     * Set the default bounds to the given lower and upper bound.
     *
     * @param lowerBound The given lower bound.
     * @param upperBound The given upper bound.
     */
    public void setBounds(int lowerBound, int upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
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
        return mapping.computeIfAbsent(key, k -> new VisElem(k, model, lowerBound, upperBound));
    }

    /**
     * Returns the model associated with this VisMap.
     *
     * @return The model associated with this VisMap.
     */
    public Model getModel() {
        return model;
    }

    /**
     * Get the default lower bound.
     *
     * @return The lower bound.
     */
    public int getLowerBound() {
        return lowerBound;
    }

    /**
     * Get the default upper bound.
     *
     * @return The upper bound.
     */
    public int getUpperBound() {
        return upperBound;
    }

    /**
     * Returns a Collection view of the values contained in this map.
     *
     * @return A view of the values contained in this map.
     */
    public Collection<VisElem> values() {
        return Collections.unmodifiableCollection(mapping.values());
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * @return A view of the keys contained in this map.
     */
    public Set<String> keySet() {
        return Collections.unmodifiableSet(mapping.keySet());
    }

    /**
     * Returns a view of this map.
     *
     * @return A vies of this map.
     */
    public Map<String, VisElem> getMapping() {
        return Collections.unmodifiableMap(mapping);
    }

    @Override
    public String toString() {
        return mapping.toString();
    }
}
