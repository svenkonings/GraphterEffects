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

/**
 * The library consists of three parts:
 * <p>
 * First there are the terms. These are added to the prolog script before the solving process.
 * <p>
 * Second is the mapping from queries to {@link QueryConsumer}. During the solving process the given queries are queried
 * on the final prolog script. The result are then passed to the associated {@link QueryConsumer}. The {@link
 * QueryConsumer} can be implemented to do something with the results, like applying visualization consequences.
 * <p>
 * Last there is the elemConsumer. After all the queries have been queried this consumer is called for every
 * visualization element and has the ability to set default values.
 */
public class VisLibrary {

    /** Terms to be added before querieing */
    protected final Set<Term> terms;

    /** Mapping from query to {@link QueryConsumer}. */
    protected final Map<String, QueryConsumer> queries;

    /** Used to set default values after all the consequences have been applied. */
    protected Consumer<VisElem> elemConsumer;

    /**
     * Create a new VisLibrary with an empty set of terms, an empty mapping and a elemConsumer consumer that does
     * nothing.
     */
    public VisLibrary() {
        this.terms = new HashSet<>();
        this.queries = new LinkedHashMap<>();
        this.elemConsumer = elem -> {
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
     * Get the set mapping of queries associated with this library.
     *
     * @return The mapping.
     */
    public Map<String, QueryConsumer> getQueries() {
        return queries;
    }

    /**
     * Apply the elemConsumer consumer to the given element.
     *
     * @param elem The given element.
     */
    public void applyElemConsumer(VisElem elem) {
        elemConsumer.accept(elem);
    }

    /**
     * Add the given clause to the terms.
     *
     * @param head The clause head.
     * @param body The clause tail.
     * @return {@code true} if the terms did not already contain the specified element.
     */
    public boolean addClause(Term head, Term body) {
        return addTerm(clause(head, body));
    }

    /**
     * Remove the given clause from the terms.
     *
     * @param head The clause head.
     * @param body The clause tail.
     * @return {@code true} if the clause existed.
     */
    public boolean removeClause(Term head, Term body) {
        return removeTerm(clause(head, body));
    }

    /**
     * Add the given term to the terms.
     *
     * @param term The given term.
     * @return {@code true} if the terms did not already contain the specified element.
     */
    public boolean addTerm(Term term) {
        return terms.add(term);
    }

    /**
     * Remove the given term from terms.
     *
     * @param term The given term.
     * @return {@code true} if the term existed.
     */
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

    /**
     * Get the element consumer.
     *
     * @return The element consumer.
     */
    public Consumer<VisElem> getElemConsumer() {
        return elemConsumer;
    }

    /**
     * Set the given element consumer.
     *
     * @param elemConsumer The given element consumer.
     */
    public void setElemConsumer(Consumer<VisElem> elemConsumer) {
        this.elemConsumer = elemConsumer;
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
