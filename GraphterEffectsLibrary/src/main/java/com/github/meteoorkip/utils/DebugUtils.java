package com.github.meteoorkip.utils;

import it.unibo.tuprolog.solve.Solver;
import it.unibo.tuprolog.solve.classic.ClassicSolverFactory;
import it.unibo.tuprolog.solve.library.AliasedLibrary;
import it.unibo.tuprolog.solve.library.Libraries;

public class DebugUtils {


    public static void traceInvalidLibrary(AliasedLibrary l)  {
        Libraries libraries = Libraries.of(l);
        ClassicSolverFactory.INSTANCE.solverWithDefaultBuiltins(libraries);
    }


}
