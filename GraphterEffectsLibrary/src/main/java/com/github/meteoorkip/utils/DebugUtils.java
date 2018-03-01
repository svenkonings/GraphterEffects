package com.github.meteoorkip.utils;

import alice.tuprolog.*;
import alice.tuprolog.lib.BasicLibrary;
import alice.tuprolog.lib.IOLibrary;
import alice.tuprolog.lib.ISOLibrary;
import alice.tuprolog.lib.OOLibrary;
import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.prolog.TuProlog;
import com.github.meteoorkip.solver.Solver;
import org.graphstream.graph.Graph;

import java.util.LinkedList;
import java.util.List;

public class DebugUtils {


    public static void traceInvalidLibrary(Library l) throws InvalidLibraryException {
        List<Library> pre = new LinkedList<>();
        pre.add(new BasicLibrary());
        pre.add(new ISOLibrary());
        pre.add(new IOLibrary());
        pre.add(new OOLibrary());
        traceInvalidLibrary(pre,l);
    }



    public static void traceInvalidLibrary(List<Library> pre, Library l) throws InvalidLibraryException {
        Prolog prolog = new Prolog();
        for (Library lib : pre) {
            try {
                prolog.loadLibrary(lib);
            } catch (InvalidLibraryException e) {
               throw new InvalidLibraryException("InvalidLibraryException in pre-trace libraries!!", e.getLine(), e.getPos());
            }
        }
        TheoryManager theoryManager = prolog.getTheoryManager();
        try {
            theoryManager.consult(new Theory(l.getTheory()), false, l.getName());
        } catch (InvalidTheoryException e) {
            System.out.println(l.getTheory());
            e.printStackTrace();
        }
    }


    public static String getCompleteTheory(Graph g, String script) throws GraafvisCompiler.SyntaxException, GraafvisCompiler.CheckerException {
        GraafvisCompiler compiler = new GraafvisCompiler();
        List<Term> terms = compiler.compile(script);
        Solver solver = new Solver();
        TuProlog a = solver.loadProlog(g, terms);
        return a.getProlog().getTheoryManager().getTheory(false);
    }
}
