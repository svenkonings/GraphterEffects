package solver;

/**
 * An exception for consequences.
 */
public class ConsequenceException extends RuntimeException {

    /**
     * Creates the exception with the formatted message using the specified format {@link String} and arguments.
     *
     * @param message The format {@link String}.
     * @param args    The given arguments.
     */
    public ConsequenceException(String message, Object... args) {
        super(String.format(message, args));
    }
}
