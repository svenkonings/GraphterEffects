package compiler.solver;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import alice.tuprolog.Var;
import compiler.prolog.TuProlog;
import org.chocosolver.solver.Model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static compiler.prolog.TuProlog.struct;

public class TuSolver {
    private final TuProlog prolog;
    private final Model model;
    private final TuMap visMap;

    public TuSolver(List<Term> terms) throws InvalidTheoryException {
        prolog = new TuProlog(terms.toArray(new Term[0]));
        model = new Model();
        visMap = new TuMap(model);
    }

    // TODO: Extendibility
    public List<VisElem> solve() {
        attributesPredicate("shape", "type");
        attributesPredicate("pos", "x1", "y1");
        attributesPredicate("pos", "x1", "y1", "z");
        attributesPredicate("posX", "x1");
        attributesPredicate("posY", "y1");
        attributesPredicate("posZ", "z");
        attributesPredicate("dimensions", "width", "height");
        attributesPredicate("width", "width");
        attributesPredicate("height", "height");
        attributesPredicate("colour", "colour");
        attributesPredicate("border-colour", "border-colour");

        List<VisElem> visElems = visMap.values();
        visElems.forEach(VisElem::setDefaults);
        return visElems;
    }

    // Shortcut for simple predicates that only set the attribute value
    private void attributesPredicate(String name, String... attributes) {
        String[] args = varNames(attributes.length + 1);
        predicate(name, (visMap, values) -> {
            // Assumes the first term is the key
            VisElem visElem = visMap.get(values.get(args[0]));
            for (int i = 1; i < args.length; i++) {
                // TODO: Check if value is a number?
                visElem.set(attributes[i - 1], values.get(args[i]).replaceAll("'", ""));
            }
        }, args);
    }

    private void predicate(String name, BiConsumer<TuMap, Map<String, String>> consumer, String... args) {
        query(name, args).forEach(resultMap -> consumer.accept(visMap, resultMap));
    }

    private List<Map<String, String>> query(String name, String... args) {
        Var[] vars = Arrays.stream(args).map(TuProlog::var).toArray(Var[]::new);
        return prolog.query(struct(name, vars));
    }

    private static String[] varNames(int amount) {
        String[] result = new String[amount];
        for (int i = 0; i < amount; i++) {
            result[i] = "X" + i;
        }
        return result;
    }
}
