package compiler.solver;

import alice.tuprolog.Term;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface QueryConsumer extends BiConsumer<VisMap, List<Map<String, Term>>> {
}
