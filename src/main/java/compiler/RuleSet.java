package compiler;

import compiler.solver.VisElem;

import java.util.*;

/**
 * Intermediate representation of Visualization elements before they are delivered to an SVG generator.
 */
public class RuleSet implements Iterable<VisElem> {

    /**
     * Set of VisualiZation Elements of which all properties are set.
     */
    private Set<VisElem> completedrules = new HashSet<>();


    /**
     * Adds a Visualization Element to this set.
     * @param vis Visualization Element to be added.
     * @return <tt>true</tt> if this set did not already contain the Visualization Element
     */
    public boolean add(VisElem vis) {
        return completedrules.add(vis);
    }

    /**
     * Add all Visualization Elements from a Collection to this set.
     * @param c Element to be added.
     * @return <tt>true</tt> if this set changed as a result of the call.
     */
    public boolean addAll(Collection<VisElem> c) {
        return completedrules.addAll(c);
    }

    /**
     * Returns the number of Visualization Elements in this set.
     * @return The number of Visualization Elements in this set.
     */
    public int getVisElemCount() {
        return completedrules.size();
    }

    /**
     * Returns a Set of all Visualization elements contained.
     * @return a Set of all Visualization elements contained.
     */
    public Set<VisElem> getElements() {
        return completedrules;
    }

    /**
     * Returns an iterator that iterates over the Visualization elements .
     * @return An iterator that iterates over the Visualization elements .
     */
    @Override
    public Iterator<VisElem> iterator() {
        return completedrules.iterator();
    }

}
