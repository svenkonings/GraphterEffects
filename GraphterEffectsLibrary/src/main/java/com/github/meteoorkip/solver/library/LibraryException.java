package com.github.meteoorkip.solver.library;

/**
 * An exception thrown when something goes wrong in one of the libraries.
 */
public class LibraryException extends RuntimeException {

    /**
     * Creates the exception with the given cause.
     *
     * @param cause The given cause.
     */
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
