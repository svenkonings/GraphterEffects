package utils;

import java.util.Iterator;

/**
 * Class used as a container for 2 Objects.
 * @param <T> Type of the first Object.
 * @param <U> Type of the second Object.
 */
public class Pair<T, U> implements Iterable {
    /**
     * First Object contained in this Pair.
     */
    private final T t;
    /**
     * Seconf Object contained in this Pair.
     */
    private final U u;

    /**
     * Creates a new Pair.
     * @param t The first Object to be contained.
     * @param u The second Object to be contained.
     */
    public Pair(T t, U u) {
        this.t= t;
        this.u= u;
    }

    /**
     * Returns the first element contained in this Pair.
     * @return The first element in this Pair.
     */
    public T getFirst() {
        return t;
    }

    /**
     * Returns the second element contained in this Pair.
     * @return The second element in this Pair.
     */
    public U getSecond() {
        return u;
    }

    /**
     * Retrieves an element from this Pair based on index.
     * @param index Index of the element to return.
     * @return Element in this Pair on that index.
     */
    public Object get(int index) {
        if (index==0) {
            return t;
        } else if (index==1) {
            return u;
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Returns a String representation of this Pair.
     * @return A String representation of this Pair.
     */
    @Override
    public String toString(){
        return "(" + t + ", " + u + ")";
    }


    /**
     * Returns an Iterator that iterates over the two Objects in this Pair.
     * @return an Iterator that iterates over the two Objects in this Pair.
     */
    @Override
    public Iterator iterator() {
        return new PairIterator();
    }

    private class PairIterator implements Iterator {

        private int i;

        PairIterator() {
            this.i = 0;
        }

        @Override
        public boolean hasNext() {
            return i==0;
        }

        @Override
        public Object next() {
            i+=1;
            return i-1;
        }
    }
}