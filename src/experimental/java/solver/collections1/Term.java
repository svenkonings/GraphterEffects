package solver.collections1;

public class Term<T> {
    private T value;

    public Term() {
        this.value = null;
    }

    public Term(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T newValue) {
        if (value != null) {
            throw new IllegalStateException("Value already set.");
        }
        this.value = newValue;
    }

    public boolean isVariable() {
        return value != null;
    }
}
