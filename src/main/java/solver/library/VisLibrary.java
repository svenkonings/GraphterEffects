package solver.library;

import alice.tuprolog.Term;
import solver.VisElem;
import solver.VisMap;
import utils.TriConsumer;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static prolog.TuProlog.clause;

public class VisLibrary {

    /** Terms to be added before querieing */
    protected final Set<Term> terms;

    /** Mapping from query to {@link QueryConsumer}. */
    protected final Map<String, QueryConsumer> queries;

    /** Used to set default values after all the consequences have been applied. */
    protected Consumer<VisElem> defaults;

    /**
     * Create a new VisLibrary with an empty set of terms, an empty mapping and a defaults consumer that does nothing.
     */
    public VisLibrary() {
        this.terms = new HashSet<>();
        this.queries = new LinkedHashMap<>();
        this.defaults = elem -> {
            // Do nothing.
        };
    }

    /**
     * Get the set of terms associated with this library.
     *
     * @return The set of terms.
     */
    public Set<Term> getTerms() {
        return terms;
    }

    /**
     * Get the set of terms associated with this library.
     *
     * @return The set of terms.
     */
    public Map<String, QueryConsumer> getQueries() {
        return queries;
    }

    public void applyDefaults(VisElem elem) {
        defaults.accept(elem);
    }

    public boolean addClause(Term head, Term body) {
        return addTerm(clause(head, body));
    }

    public boolean removeClause(Term head, Term body) {
        return removeTerm(clause(head, body));
    }

    public boolean addTerm(Term term) {
        return terms.add(term);
    }

    public boolean removeTerm(Term term) {
        return terms.remove(term);
    }

    /**
     * Puts the given query with the given {@link QueryConsumer}.
     *
     * @param query         The given query.
     * @param queryConsumer The given {@link QueryConsumer}.
     * @return The previous {@link QueryConsumer} associated with the query, or {@code null} if it didn't exist.
     */
    public QueryConsumer putQuery(String query, QueryConsumer queryConsumer) {
        return queries.put(query.replaceAll("\\s+", ""), queryConsumer);
    }

    /**
     * Get the {@link QueryConsumer} associated with the given query.
     *
     * @param query The given query.
     * @return The {@link QueryConsumer}, or {@code null} if it doesn't exist.
     */
    public QueryConsumer getQuery(String query) {
        return queries.get(query.replaceAll("\\s+", ""));
    }

    /**
     * Remove the given query.
     *
     * @param query The given query.
     * @return The {@link QueryConsumer} associated to this query, or {@code null} if it doesn't exist.
     */
    public QueryConsumer removeQuery(String query) {
        return queries.remove(query.replaceAll("\\s+", ""));
    }

    public Consumer<VisElem> getDefaults() {
        return defaults;
    }

    public void setDefaults(Consumer<VisElem> defaults) {
        this.defaults = defaults;
    }

    /**
     * Creates a {@link QueryConsumer} that calls the given {@link BiConsumer} for every result of the solved query. The
     * {@link BiConsumer} receives the mapping of visualization elements and a {@link Map} of the results.
     *
     * @param consumer The given consumer.
     * @return The {@link QueryConsumer}.
     */
    public static QueryConsumer forEach(BiConsumer<VisMap, Map<String, Term>> consumer) {
        return (visMap, values) -> values.forEach(value -> consumer.accept(visMap, value));
    }

    /**
     * Creates a {@link QueryConsumer} that calls the given {@link BiConsumer} for every result of the solved query. The
     * {@link BiConsumer} receives the visualization element that belongs to the "Elem" variable and a {@link Map} of
     * the results.
     *
     * @param consumer The given consumer.
     * @return The {@link QueryConsumer}.
     */
    public static QueryConsumer elementQuery(BiConsumer<VisElem, Map<String, Term>> consumer) {
        return forEach((visMap, values) -> {
            VisElem elem = visMap.get(values.get("Elem"));
            consumer.accept(elem, values);
        });
    }

    /**
     * Creates a {@link QueryConsumer} that calls the given {@link TriConsumer} for every result of the solved query.
     * The {@link TriConsumer} receives the visualization elements that belongs to "Elem1" and "Elem2" variables and a
     * {@link Map} of the results.
     *
     * @param consumer The given consumer.
     * @return The {@link QueryConsumer}.
     */
    public static QueryConsumer elementPairQuery(TriConsumer<VisElem, VisElem, Map<String, Term>> consumer) {
        return forEach((visMap, values) -> {
            VisElem elem1 = visMap.get(values.get("Elem1"));
            VisElem elem2 = visMap.get(values.get("Elem2"));
            if (elem1 == elem2) {
                return;
            }
            consumer.accept(elem1, elem2, values);
        });
    }
}
