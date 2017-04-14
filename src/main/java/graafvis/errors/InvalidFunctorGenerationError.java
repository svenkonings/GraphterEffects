package graafvis.errors;

/**
 *
 */
public class InvalidFunctorGenerationError extends VisError {

    private final String functor;

    public InvalidFunctorGenerationError(int row, int column, String functor) {
        super(row, column);
        this.functor = functor;
        this.setMsg(String.format("Could not turn %s into a functor at line %d:%d", functor, row, column));
    }

    public String getFunctor() {
        return functor;
    }

}
