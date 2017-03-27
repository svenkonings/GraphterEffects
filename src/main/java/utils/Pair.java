package utils;

import java.util.Iterator;

public class Pair<T, U> implements Iterable {
    private final T t;
    private final U u;

    public Pair(T t, U u) {
        this.t= t;
        this.u= u;
    }

    public T getFirst() {
        return t;
    }

    public U getSecond() {
        return u;
    }

    public Object get(int index) {
        if (index==0) {
            return t;
        } else if (index==1) {
            return u;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public String toString(){
        return "(" + t + ", " + u + ")";
    }


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