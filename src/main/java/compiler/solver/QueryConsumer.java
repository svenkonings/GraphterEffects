package compiler.solver;

import alice.tuprolog.Term;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Represents a {@link BiConsumer} that receives a {@link VisMap} and the {@link compiler.prolog.TuProlog} result (see
 * {@link compiler.prolog.TuProlog#solve(Term)}.
 */
@FunctionalInterface
public interface QueryConsumer extends BiConsumer<VisMap, List<Map<String, Term>>> {
}
