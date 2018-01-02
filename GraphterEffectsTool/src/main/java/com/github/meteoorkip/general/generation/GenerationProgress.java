package com.github.meteoorkip.general.generation;

/**
 * Enum of Generation Progress stages
 */
public enum GenerationProgress {

    NORMALGENERATIONSTARTED(0), DEBUGGENERATIONSTARTED(0), GRAAFVISCOMPILED(1), GRAPHLOADED(2), PROLOGLOADED(3),
    SOLVED(4), SVGGENERATED(5), GENERATIONFINISHED(6), ABORTED(6), ERROROCCURED(7), COMPILEERROR(7), NOSOLUTION(7);

    private final int ordinal;

    GenerationProgress(int ordinal) {
        this.ordinal = ordinal;
    }

    /**
     * @return the ordinal
     */
    private int getOrdinal() {
        return ordinal;
    }
}
