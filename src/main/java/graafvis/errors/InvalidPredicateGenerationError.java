package graafvis.errors;

/**
 *
 */
public class InvalidPredicateGenerationError extends VisError {

    private final String predicate;

    public InvalidPredicateGenerationError(int row, int column, String predicate) {
        super(row, column);
        this.predicate = predicate;
        this.setMsg(String.format("Could not turn %s into a predicate at line %d:%d", predicate, row, column));
    }

    public String getPredicate() {
        return predicate;
    }

}
