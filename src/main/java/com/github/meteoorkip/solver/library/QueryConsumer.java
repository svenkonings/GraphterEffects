package com.github.meteoorkip.solver.library;

import alice.tuprolog.Term;
import com.github.meteoorkip.solver.VisMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Represents a {@link BiConsumer} that receives a {@link VisMap} and the {@link com.github.meteoorkip.prolog.TuProlog}
 * result (see {@link com.github.meteoorkip.prolog.TuProlog#solve(Term)}. This consumer is ussually used to modify the
 * visualization elements in the {@link VisMap} based the results.
 */
@FunctionalInterface
public interface QueryConsumer extends BiConsumer<VisMap, List<Map<String, Term>>> {
    default QueryConsumer andThen(QueryConsumer after) {
        Objects.requireNonNull(after);

        return (l, r) -> {
            accept(l, r);
            after.accept(l, r);
        };
    }
}
