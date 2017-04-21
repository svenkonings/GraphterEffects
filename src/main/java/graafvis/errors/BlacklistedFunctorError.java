package graafvis.errors;

public class BlacklistedFunctorError extends VisError {

    private final String functor;

    public BlacklistedFunctorError(int row, int column, String functor) {
        super(row, column);
        this.setMsg(String.format("Found blacklisted functor \"%s\" at line %d:%d", functor, row, column));
        this.functor = functor;
    }

    public String getFunctor() {
        return functor;
    }

}
