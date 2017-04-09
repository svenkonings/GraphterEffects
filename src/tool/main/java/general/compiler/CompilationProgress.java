package general.compiler;

public enum CompilationProgress {

    COMPILATIONSTARTED(0),DEBUGCOMPILATIONSTARTED(0),GRAAFVISCOMPILED(1),GRAPHCONVERTED(2), SOLVED(3),SVGGENERATED(4),COMPILATIONFINISHED(5),  ERROROCCURED(6);

    private final int ordinal;

    CompilationProgress(int ordinal) {
        this.ordinal = ordinal;
    }

    private int getOrdinal(){
        return ordinal;
    }
}
