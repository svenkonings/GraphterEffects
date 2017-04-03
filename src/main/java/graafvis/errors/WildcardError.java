package graafvis.errors;

public class WildcardError extends VisError {
    public WildcardError(int row, int column) {
        super(row, column);
        setMsg(String.format("Wildcard usage in the consequence of a clause at line %d:%d.", row, column));
    }
}
