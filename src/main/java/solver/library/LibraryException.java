package solver.library;

/**
 * An exception for consequences.
 */
public class LibraryException extends RuntimeException {

    public LibraryException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates the exception with the formatted message using the specified format {@link String} and arguments.
     *
     * @param message The format {@link String}.
     * @param args    The given arguments.
     */
    public LibraryException(String message, Object... args) {
        super(String.format(message, args));
    }
}
