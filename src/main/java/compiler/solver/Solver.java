package compiler.solver;

import org.chocosolver.solver.Model;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Jatalog;

import java.util.*;
import java.util.function.BiConsumer;

import static za.co.wstoop.jatalog.Expr.expr;

public class Solver {
    private final Jatalog jatalog;
    private final Model model;
    private final Map<String, VisElem> mapping;

    public Solver(Jatalog jatalog) {
        this.jatalog = jatalog;
        this.model = new Model();
        this.mapping = new HashMap<>();
    }

    // TODO: Predicate list
    // TODO: Implement mapping
    public List<VisElem> solve() throws DatalogException {
        valuePredicate("type", (visElem, result) -> visElem.setType(VisType.fromAtom(result)));
        valuePredicate("posX", "x");
        valuePredicate("posY", "y");
        valuePredicate("width", "width");
        valuePredicate("height", "height");
        model.getSolver().solve();
        return new ArrayList<>(mapping.values());
    }

    private void valuePredicate(String predicate, String name) throws DatalogException {
        valuePredicate(predicate, (visElem, result) -> visElem.set(name, result));
    }

    private void valuePredicate(String predicate, BiConsumer<VisElem, String> consumer) throws DatalogException {
        Collection<Map<String, String>> results = jatalog.query(expr(predicate, "Key", "Value"));
        for (Map<String, String> result : results) {
            VisElem visElem = mapping.computeIfAbsent(result.get("Key"), key -> new VisElem(model));
            consumer.accept(visElem, result.get("Value"));
        }
    }
}
