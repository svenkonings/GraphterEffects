package solver;

/**
 * An exception for when an error occurs during the solving process.
 */
public class SolveException extends Exception {

    /** The {@link VisMap} used during the solving process. */
    private final VisMap visMap;

    /**
     * Creates the exception with the formatted message using the specified format {@link String} and arguments.
     *
     * @param message The format {@link String}.
     * @param args    The given arguments.
     */
    public SolveException(VisMap visMap, String message, Object... args) {
        super(String.format(message, args));
        this.visMap = visMap;
    }

    /**
     * Get the {@link VisMap} used during the solving process.
     *
     * @return The {@link VisMap} used during the solving process.
     */
    public VisMap getVisMap() {
        return visMap;
    }
}
