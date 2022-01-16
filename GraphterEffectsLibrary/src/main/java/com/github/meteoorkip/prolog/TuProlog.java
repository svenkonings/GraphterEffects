package com.github.meteoorkip.prolog;

import it.unibo.tuprolog.core.Integer;
import it.unibo.tuprolog.core.*;
import it.unibo.tuprolog.core.parsing.TermParser;
import it.unibo.tuprolog.solve.Solution;
import it.unibo.tuprolog.solve.Solver;
import it.unibo.tuprolog.solve.channel.InputChannel;
import it.unibo.tuprolog.solve.channel.OutputChannel;
import it.unibo.tuprolog.solve.classic.ClassicSolverFactory;
import it.unibo.tuprolog.solve.exception.Warning;
import it.unibo.tuprolog.solve.flags.FlagStore;
import it.unibo.tuprolog.solve.library.AliasedLibrary;
import it.unibo.tuprolog.solve.library.Libraries;
import it.unibo.tuprolog.theory.Theory;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Wrapper for the TuProlog library.
 */
public class TuProlog {

    public static final List<LogListener> loglisteners = new ArrayList<>();
    private Libraries libraries = Libraries.empty();
    private Theory staticTheory;

    public TuProlog(Collection<Clause> clauses) {
        staticTheory = Theory.of(clauses);
    }

    public TuProlog(Clause... clauses) {
        staticTheory = Theory.of(clauses);
    }

    public static Atom atom(String atom) {
        return Atom.of(atom);
    }

    public static Numeric number(String number) {
        return Numeric.of(number);
    }

    public static Integer intVal(int value) {
        return Integer.of(value);
    }

    public static Var var() {
        return Var.anonymous();
    }

    public static Var var(String name) {
        return Var.of(name);
    }

    public static it.unibo.tuprolog.core.List list(Term... terms) {
        return it.unibo.tuprolog.core.List.of(terms);
    }

    public static Struct listTail(Term tail, Term... terms) {
        List<Term> ts = new ArrayList<>(Arrays.asList(terms));
        ts.add(tail);
        return nestTermsRight(".", ts.toArray(new Term[0]));
    }

    public static Struct struct(String name, Term... terms) {
        return Struct.of(name, terms);
    }

    public static Clause clause(@NotNull Struct head, Term... body) {
        return Clause.of(head, body);
    }

    public static Fact fact(Struct head) {
        return Fact.of(head);
    }

    public static Fact fact(String name, Term... terms) {
        return fact(struct(name, terms));
    }

    /**
     * Negates the given term.
     *
     * @param term The given term.
     * @return The resulting {@link Struct}.
     */
    public static Struct not(Term term) {
        return struct("not", term);
    }

    /**
     * Creates a concatenation of and clauses with the given terms.
     *
     * @param terms The given term.
     * @return The resulting {@link Struct}.
     */
    public static Struct and(Term... terms) {
        return nestTermsLeft(",", terms);
    }

    /**
     * Creates a concatenation of or clauses with the given terms.
     *
     * @param terms The given term.
     * @return The resulting {@link Struct}.
     */
    public static Term or(Term... terms) {
        if (terms.length == 1) {
            return terms[0];
        } else {
            return Struct.of(";", terms);
        }
    }

    /**
     * Creates a concatenation of clauses with the given name and the given terms.
     *
     * @param name  The given name.
     * @param terms The given terms.
     * @return The resulting {@link Struct}.
     */
    public static Struct nestTermsLeft(String name, Term... terms) {
        Deque<Term> deque = new ArrayDeque<>(Arrays.asList(terms));
        while (deque.size() < 2) {
            deque.addLast(list());
        }
        Term term1 = deque.removeFirst();
        Term term2 = deque.removeFirst();
        Struct struct = struct(name, term1, term2);
        while (!deque.isEmpty()) {
            Term term = deque.removeFirst();
            struct = struct(name, struct, term);
        }
        return struct;
    }

    /**
     * Creates a concatenation of clauses with the given name and the given terms.
     *
     * @param name  The given name.
     * @param terms The given terms.
     * @return The resulting {@link Struct}.
     */
    public static Struct nestTermsRight(String name, Term... terms) {
        Deque<Term> deque = new ArrayDeque<>(Arrays.asList(terms));
        while (deque.size() < 2) {
            deque.addLast(list());
        }
        Term term2 = deque.removeLast();
        Term term1 = deque.removeLast();
        Struct struct = struct(name, term1, term2);
        while (!deque.isEmpty()) {
            Term term = deque.removeLast();
            struct = struct(name, term, struct);
        }
        return struct;
    }

    public static void addLogListener(LogListener e) {
        loglisteners.add(e);
    }

    public static void log(String tolog) {
        for (LogListener ll : loglisteners) {
            ll.textAdded(tolog);
        }
    }

    public void addTheory(Clause... clauses) {
        staticTheory = staticTheory.plus(Theory.of(clauses));
    }

    public void addTheory(Collection<Clause> clauses) {
        staticTheory = staticTheory.plus(Theory.of(clauses));
    }

    public Theory getTheory() {
        return staticTheory;
    }

    public List<Map<String, Term>> solve(String query) {
        return solve(TermParser.getWithDefaultOperators().parseStruct(query));
    }

    public List<Map<String, Term>> solve(Struct... andQuery) {
        Set<Var> allvars = new HashSet<>();
        for (Struct struct : andQuery) {
            struct.getVariables().iterator().forEachRemaining(allvars::add);
        }
        staticTheory = staticTheory.plus(clause(Struct.of("myQuery", allvars), andQuery));
        return solve(Struct.of("myQuery", allvars));
    }

    public List<Map<String, Term>> solve(Struct query) {
        List<Warning> warnings = new ArrayList<>();
        OutputChannel<Warning> warningOutputChannel = OutputChannel.of(x -> {
            warnings.add(x);
            log(x.getMessage());
            return null;
        });
        Solver solver = ClassicSolverFactory.INSTANCE.solverWithDefaultBuiltins(libraries, FlagStore.DEFAULT, staticTheory, Theory.empty(), InputChannel.stdIn(), OutputChannel.of(x -> null), OutputChannel.of(x -> null), warningOutputChannel);
        List<Solution> solutions = solver.solveList(query);
        List<Map<String, Term>> res = new ArrayList<>();
        for (Solution solution : solutions) {
            if (solution.isYes()) {
                Set<String> keys = solution.getSubstitution().keySet().stream().map(Var::getName).collect(Collectors.toSet());
                Map<String, Term> toAdd = keys.stream().collect(Collectors.toMap(v -> v, s -> solution.getSubstitution().getByName(s)));
                if (!res.contains(toAdd)) {
                    res.add(toAdd);
                }
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return getTheory().toString();
    }

    public void loadLibrary(AliasedLibrary library) {
        libraries = libraries.plus(library);
    }
}
