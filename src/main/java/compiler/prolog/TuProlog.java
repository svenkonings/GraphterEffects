package compiler.prolog;

import alice.tuprolog.Double;
import alice.tuprolog.Float;
import alice.tuprolog.*;
import alice.tuprolog.Long;
import alice.tuprolog.Number;

import java.util.*;
import java.util.stream.Collectors;

public class TuProlog {
    // More generic version, parses the given String
    public static Term term(String term) {
        return Term.createTerm(term);
    }

    // More generic version, parses the given String
    public static Number number(String number) {
        return Number.createNumber(number);
    }

    public static Int intVal(int value) {
        return new Int(value);
    }

    public static Long longVal(long value) {
        return new Long(value);
    }

    public static Float floatVal(float value) {
        return new Float(value);
    }

    public static Double doubleVal(double value) {
        return new Double(value);
    }

    public static Var var() {
        return new Var();
    }

    public static Var var(String name) {
        return new Var(name);
    }

    public static Struct list(Term... terms) {
        if (terms.length == 0) {
            return new Struct();
        } else {
            return new Struct(terms);
        }
    }

    public static Struct struct(String name, Term... terms) {
        return new Struct(name, terms);
    }

    public static Struct clause(Term head, Term body) {
        return new Struct(":-", head, body);
    }

    public static Struct and(Term... terms) {
        return concatTerms(",", terms);
    }

    public static Struct or(Term... terms) {
        return concatTerms(";", terms);
    }

    public static Struct concatTerms(String name, Term... terms) {
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

    // Class start
    private final Prolog prolog;

    public TuProlog() {
        prolog = new Prolog();
    }

    public TuProlog(Term... terms) throws InvalidTheoryException {
        this();
        setTheory(terms);
    }

    public void addTheory(Term... terms) throws InvalidTheoryException {
        Theory theory = new Theory(list(terms));
        prolog.addTheory(theory);
    }

    public void setTheory(Term... terms) throws InvalidTheoryException {
        Theory theory = new Theory(list(terms));
        prolog.setTheory(theory);
    }

    public List<Map<String, String>> query(Term term) {
        List<Map<String, String>> results = new ArrayList<>();
        SolveInfo info = prolog.solve(term);
        while (info.isSuccess()) {
            List<Var> vars;
            try {
                vars = info.getBindingVars();
            } catch (NoSolutionException e) {
                break;
            }
            Map<String, String> result = vars.stream().collect(Collectors.toMap(Var::getOriginalName, var -> termToString(var.getTerm())));
            results.add(result);
            if (prolog.hasOpenAlternatives()) {
                try {
                    info = prolog.solveNext();
                } catch (NoMoreSolutionException e) {
                    break;
                }
            } else {
                break;
            }
        }
        prolog.solveEnd();
        return results;
    }

    private static String termToString(Term term) {
        String result;
        if (term instanceof Int) {
            result = Integer.toString(((Int) term).intValue());
        } else if (term instanceof Long) {
            result = java.lang.Long.toString(((Long) term).longValue());
        } else if (term instanceof Float) {
            result = java.lang.Float.toString(((Float) term).floatValue());
        } else if (term instanceof Double) {
            result = java.lang.Double.toString(((Double) term).doubleValue());
        } else {
            result = term.toString();
        }
        return result;
    }
}
