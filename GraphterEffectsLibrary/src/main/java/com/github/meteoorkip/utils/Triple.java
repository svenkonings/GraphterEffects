package com.github.meteoorkip.utils;

import java.util.Iterator;

/**
 * Class used as a container for 2 Objects.
 *
 * @param <T> Type of the first Object.
 * @param <U> Type of the second Object.
 */
public class Triple<T, U, V> implements Iterable {
    /**
     * First Object contained in this Triple.
     */
    private final T t;
    /**
     * Second Object contained in this Triple.
     */
    private final U u;
    /**
     * Third Object contained in this Triple.
     */
    private final V v;

    /**
     * Creates a new Triple.
     *
     * @param t The first Object to be contained.
     * @param u The second Object to be contained.
     * @param v The third Object to be contained.
     */
    public Triple(T t, U u, V v) {
        this.t = t;
        this.u = u;
        this.v = v;
    }

    /**
     * Returns the first element contained in this Triple.
     *
     * @return The first element in this Triple.
     */
    public T getFirst() {
        return t;
    }

    /**
     * Returns the second element contained in this Triple.
     *
     * @return The second element in this Triple.
     */
    public U getSecond() {
        return u;
    }

    /**
     * Returns the second element contained in this Triple.
     *
     * @return The second element in this Triple.
     */
    public V getThird() { return v; }

    /**
     * Retrieves an element from this Triple based on index.
     *
     * @param index Index of the element to return.
     * @return Element in this Triple on that index.
     */
    public Object get(int index) {
        if (index == 0) {
            return t;
        } else if (index == 1) {
            return u;
        } else if (index == 2) {
            return v;
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Returns a String representation of this Triple.
     *
     * @return A String representation of this Triple.
     */
    @Override
    public String toString() {
        return "(" + t + ", " + u + ", " + v + ")";
    }


    /**
     * Returns an Iterator that iterates over the three Objects in this Triple.
     *
     * @return an Iterator that iterates over the three Objects in this Triple.
     */
    @Override
    public Iterator iterator() {
        return new TripleIterator();
    }

    private class TripleIterator implements Iterator {

        private int i;

        TripleIterator() {
            this.i = 0;
        }

        @Override
        public boolean hasNext() {
            return i == 0;
        }

        @Override
        public Object next() {
            i += 1;
            return i - 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Triple){
            Triple tr = (Triple) o;
            return t.equals(tr.t) && u.equals(tr.u) & v.equals(tr.v);
        } else {
            return false;
        }
    }

}
