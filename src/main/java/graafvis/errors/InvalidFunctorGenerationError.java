package graafvis.errors;

/**
 * An error that occurs when a label predicate generation statement cannot turn a label into a functor
 */
public class InvalidFunctorGenerationError extends VisError {
    /** The string that could not be converted to a functor */
    private final String functor;

    /**
     * Create a new InvalidFunctorGenerationError
     *
     * @param row       The line number in which the error was encountered
     * @param column    The character position index at which the error was encountered
     * @param functor   The string that could not be converted to a functor
     */
    public InvalidFunctorGenerationError(int row, int column, String functor) {
        super(row, column);
        this.functor = functor;
        this.setMsg(String.format("Could not turn %s into a functor at line %d:%d", functor, row, column));
    }

    /** Get the invalid functor */
    public String getFunctor() {
        return functor;
    }

}
