package general.compiler;

/**
 * Enum of Compilation Progress stages
 */
public enum CompilationProgress {

    NORMALCOMPILATIONSTARTED(0),DEBUGCOMPILATIONSTARTED(0),GRAAFVISCOMPILED(1),GRAPHCONVERTED(2), SOLVED(3),SVGGENERATED(4),COMPILATIONFINISHED(5),  ERROROCCURED(6), COMPILEERROR(6), NOSOLUTION(6), ABORTED(5);

    private final int ordinal;

    CompilationProgress(int ordinal) {
        this.ordinal = ordinal;
    }

    /**
     * @return the ordinal
     */
    private int getOrdinal(){
        return ordinal;
    }
}
