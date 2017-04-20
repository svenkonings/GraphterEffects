package general.compiler;

/**
 * Enum of Compilation Progress stages
 */
public enum CompilationProgress {

    NORMALCOMPILATIONSTARTED(0), DEBUGCOMPILATIONSTARTED(0), GRAAFVISCOMPILED(1), GRAPHLOADED(2), PROLOGLOADED(3),
    SOLVED(4), SVGGENERATED(5), COMPILATIONFINISHED(6), ABORTED(6), ERROROCCURED(7), COMPILEERROR(7), NOSOLUTION(7);

    private final int ordinal;

    CompilationProgress(int ordinal) {
        this.ordinal = ordinal;
    }

    /**
     * @return the ordinal
     */
    private int getOrdinal() {
        return ordinal;
    }
}
