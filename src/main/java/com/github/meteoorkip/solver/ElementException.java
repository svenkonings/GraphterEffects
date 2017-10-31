package com.github.meteoorkip.solver;

/**
 * An exception for duplicate elements.
 */
public class ElementException extends RuntimeException {

    /**
     * Creates the exception with the formatted message using the specified format {@link String} and arguments.
     *
     * @param message The format {@link String}.
     * @param args    The given arguments.
     */
    public ElementException(String message, Object... args) {
        super(String.format(message, args));
    }
}
