package graafvis.errors;

public class BlacklistedFunctorError extends VisError {

    private final String predicate;

    public BlacklistedFunctorError(int row, int column, String predicate) {
        super(row, column);
        this.setMsg(String.format("Found blacklisted predicate \"%s\" at line %d:%d", predicate, row, column));
        this.predicate = predicate;
    }

    public String getPredicate() {
        return predicate;
    }

}
