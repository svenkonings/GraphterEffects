package compiler.prolog;

import alice.tuprolog.Double;
import alice.tuprolog.Float;
import alice.tuprolog.*;
import alice.tuprolog.Long;
import alice.tuprolog.Number;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wrapper for the TuProlog library.
 */
public class TuProlog {

    /**
     * Create a {@link Term} from a {@link String}.
     *
     * @param term The given {@link String}.
     * @return The parsed {@link Term}.
     */
    public static Term term(String term) {
        try {
            return Term.createTerm(term);
        } catch (InvalidTermException e) {
            // TODO: Attributes, labels and names should probably be Strings anyway
            return Term.createTerm(String.format("\"%s\"", term));
        }
    }

    /**
     * Create a {@link Number} from a {@link String}.
     *
     * @param number The given {@link String}.
     * @return The parsed {@link Number}.
     */
    public static Number number(String number) {
        return Number.createNumber(number);
    }

    /**
     * Create a {@link Int} from the given {@code int} value.
     *
     * @param value The given {@code int} value.
     * @return The {@link Int}.
     */
    public static Int intVal(int value) {
        return new Int(value);
    }

    /**
     * Create a {@link Long} from the given {@code long} value.
     *
     * @param value The given {@code long} value.
     * @return The {@link Long}.
     */
    public static Long longVal(long value) {
        return new Long(value);
    }

    /**
     * Create a {@link Float} from the given {@code float} value.
     *
     * @param value The given {@code float} value.
     * @return The {@link Float}.
     */
    public static Float floatVal(float value) {
        return new Float(value);
    }

    /**
     * Create a {@link Double} from the given {@code double} value.
     *
     * @param value The given {@code double} value.
     * @return The {@link Double}.
     */
    public static Double doubleVal(double value) {
        return new Double(value);
    }

    /**
     * Creates an anonymous {@link Var}.
     *
     * @return The {@link Var}.
     */
    public static Var var() {
        return new Var();
    }

    /**
     * Creates a {@link Var} with the given name.
     *
     * @param name The given name.
     * @return The {@link Var}.
     */
    public static Var var(String name) {
        return new Var(name);
    }

    /**
     * Creates a list {@link Struct} from the given terms. The list can be empty.
     *
     * @param terms The given terms.
     * @return The {@link Struct}.
     */
    public static Struct list(Term... terms) {
        if (terms.length == 0) {
            return new Struct();
        } else {
            return new Struct(terms);
        }
    }

    /**
     * Creates a {@link Struct} with the given name and terms. A {@link Struct} without terms is atomic.
     *
     * @param name  The given name
     * @param terms The given terms.
     * @return The {@link Struct}.
     */
    public static Struct struct(String name, Term... terms) {
        return new Struct(name, terms);
    }

    /**
     * Creates a clause {@link Struct} with the given head and body.
     *
     * @param head The given head.
     * @param body The given body.
     * @return The {@link Struct}.
     */
    public static Struct clause(Term head, Term body) {
        return new Struct(":-", head, body);
    }

    /**
     * Creates a concatenation of and clauses with the given terms.
     *
     * @param terms The given term.
     * @return The resulting {@link Struct}.
     */
    public static Struct and(Term... terms) {
        return concatTerms(",", terms);
    }

    /**
     * Creates a concatenation of or clauses with the given terms.
     *
     * @param terms The given term.
     * @return The resulting {@link Struct}.
     */
    public static Struct or(Term... terms) {
        return concatTerms(";", terms);
    }

    /**
     * Creates a concatenation of clauses with the given name and the given terms.
     *
     * @param name  The given name.
     * @param terms The given terms.
     * @return The resulting {@link Struct}.
     */
    public static Struct concatTerms(String name, Term... terms) {
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

    // Class start
    /** The internal {@link Prolog} engine. */
    private final Prolog prolog;

    /** Creates a new {@code TuProlog} engine. */
    public TuProlog() {
        prolog = new Prolog();
    }

    /**
     * Creates a new {@code TuProlog} engine with the given terms as {@link Theory}.
     *
     * @param terms The given terms.
     * @throws InvalidTheoryException If the {@link Theory} is invalid.
     */
    public TuProlog(Collection<Term> terms) throws InvalidTheoryException {
        this();
        setTheory(terms.toArray(new Term[0]));
    }

    /**
     * Add the {@link Theory} of the given terms.
     *
     * @param terms The given terms.
     * @throws InvalidTheoryException If the {@link Theory} is invalid.
     */
    public void addTheory(Term... terms) throws InvalidTheoryException {
        Theory theory = new Theory(list(terms));
        prolog.addTheory(theory);
    }

    /**
     * Set the {@link Theory} of the given terms.
     *
     * @param terms The given terms.
     * @throws InvalidTheoryException If the {@link Theory} is invalid.
     */
    public void setTheory(Term... terms) throws InvalidTheoryException {
        Theory theory = new Theory(list(terms));
        prolog.setTheory(theory);
    }

    /**
     * Get the current {@link Theory}.
     *
     * @return The {@link Theory}.
     */
    public Theory getTheory() {
        return prolog.getTheory();
    }

    /**
     * Parses the given {@link String} query to a {@link Term} and solves it. The result is a {@link List} of all the
     * results. Each result is a {@link Map} with the {@link String} representation of the original name as key and the
     * resulting {@link Term} as value.
     *
     * @param query The {@link String} query.
     * @return The resulting {@link List}.
     */
    public List<Map<String, Term>> solve(String query) {
        return solve(term(query));
    }

    /**
     * solves the given {@link Term}. The result is a {@link List} of all the results. Each result is a {@link Map} with
     * the {@link String} representation of the original name as key and the resulting {@link Term} as value.
     *
     * @param term The query {@link Term}.
     * @return The resulting {@link List}.
     */
    public List<Map<String, Term>> solve(Term term) {
        List<Map<String, Term>> results = new ArrayList<>();
        SolveInfo info = prolog.solve(term);
        while (info.isSuccess()) {
            List<Var> vars;
            try {
                vars = info.getBindingVars();
            } catch (NoSolutionException e) {
                break;
            }
            Map<String, Term> result = vars.stream().collect(Collectors.toMap(Var::getOriginalName, Var::getTerm));
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

    @Override
    public String toString() {
        return getTheory().toString();
    }

    public void loadLibrary(Library asrcLibrary) throws InvalidLibraryException {
        prolog.loadLibrary(asrcLibrary);
    }

    public Prolog getProlog() {
        return prolog;
    }
}
