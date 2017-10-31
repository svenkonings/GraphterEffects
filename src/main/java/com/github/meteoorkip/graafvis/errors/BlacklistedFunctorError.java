package com.github.meteoorkip.graafvis.errors;

/**
 * An error that occurs when a blacklisted predicate has been found in the consequence of a clause
 */
public class BlacklistedFunctorError extends VisError {

    /** The blacklisted functor */
    private final String functor;

    /**
     * Create a new BlacklistedFunctorError
     *
     * @param row     The line number in which the error was encountered
     * @param column  The character position index at which the error was encountered
     * @param functor The functor that is blacklisted
     */
    public BlacklistedFunctorError(int row, int column, String functor) {
        super(row, column);
        this.setMsg(String.format("Found blacklisted functor \"%s\" at line %d:%d", functor, row, column));
        this.functor = functor;
    }

    /** Get the blacklisted functor */
    public String getFunctor() {
        return functor;
    }

}
