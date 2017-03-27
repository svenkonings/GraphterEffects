package errors;

/**
 *
 */
public class BlacklistedPredicateError extends Error {

    private final String predicate;

    BlacklistedPredicateError(int row, int column, String predicate) {
        super(row, column);
        this.setMsg(String.format("Found blacklisted predicate \"%s\" at line %d:%d", predicate, row, column));
        this.predicate = predicate;
    }

    public String getPredicate() {
        return predicate;
    }

}
