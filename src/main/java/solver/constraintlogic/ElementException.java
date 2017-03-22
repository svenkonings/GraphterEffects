package solver.constraintlogic;

public class ElementException extends RuntimeException {
    public ElementException(String message, Object... args) {
        super(String.format(message, args));
    }
}
