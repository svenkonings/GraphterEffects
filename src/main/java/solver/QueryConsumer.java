package solver;

import alice.tuprolog.Term;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Represents a {@link BiConsumer} that receives a {@link VisMap} and the {@link prolog.TuProlog} result (see
 * {@link prolog.TuProlog#solve(Term)}.
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
