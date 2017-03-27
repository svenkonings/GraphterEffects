package solver.temp;

import org.chocosolver.solver.Model;
import compiler.solver.VisElem;
import compiler.solver.VisMap;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Jatalog;
import za.co.wstoop.jatalog.Rule;

import java.util.*;
import java.util.function.BiConsumer;

import static za.co.wstoop.jatalog.Expr.expr;

public class Solver {
    private final Jatalog jatalog;
    private final Model model;
    private final VisMap visMap;

    public Solver(Jatalog jatalog) {
        this.jatalog = jatalog;
        this.model = new Model();
        this.visMap = new VisMap(model);
    }

    public List<VisElem> solve() throws DatalogException {
        keyValuePredicate("shape", "type");
        keyValuePredicate("posX", "x1");
        keyValuePredicate("posY", "y1");
        keyValuePredicate("width", "width");
        keyValuePredicate("height", "height");
        keyValuePredicate("color", "color");
        predicate("left", ((visElemMap, resultMap) -> visElemMap.get("Key1").getVar("x2").le(visElemMap.get("Key2").getVar("x1")).post()), new Arg("Key1", 1), new Arg("Key2", 1));
        predicate("left", ((visElemMap, resultMap) -> visElemMap.get("Key2").getVar("x1").sub(visElemMap.get("Key1").getVar("x2")).eq(Integer.parseInt(resultMap.get("Value"))).post()), new Arg("Key1", 1), new Arg("Key2", 1), new Arg("Value"));
        model.getSolver().solve();
        return visMap.values();
    }

    private void keyValuePredicate(String name, String attr) throws DatalogException {
        jatalog.getIdb().stream()
                .map(Rule::getHead)
                .filter(expr -> expr.getPredicate().equals(name))
                .forEach(expr -> keyValuePredicate(name, attr, expr.arity() - 1));
    }

    private void keyValuePredicate(String name, String attr, int keyArity) {
        predicate(name, ((visElemMap, resultMap) -> visElemMap.get("Key").set(attr, resultMap.get("Value"))), new Arg("Key", keyArity), new Arg("Value"));
    }

    private void predicate(String name, BiConsumer<Map<String, VisElem>, Map<String, String>> consumer, Arg... args) {
        Collection<Map<String, String>> results;
        try {
            results = jatalog.query(expr(name, Arg.concatValues(args)));
        } catch (DatalogException e) {
            e.printStackTrace();
            return;
        }
        for (Map<String, String> result : results) {
            Map<String, VisElem> visElems = new HashMap<>();
            Map<String, String> argResults = new HashMap<>();
            for (Arg arg : args) {
                if (arg.isVarArg()) {
                    String[] key = new String[arg.valueNames.size()];
                    for (int i = 0; i < arg.valueNames.size(); i++) {
                        key[i] = result.get(arg.valueNames.get(i));
                    }
                    visElems.put(arg.argName, visMap.get(key));
                } else {
                    for (String valueName : arg.valueNames) {
                        argResults.put(valueName, result.get(valueName));
                    }
                }
            }
            consumer.accept(visElems, argResults);
        }
    }

    public static class Arg {
        public final String argName;
        public final List<String> valueNames;

        public Arg(String... names) {
            argName = null;
            valueNames = Collections.unmodifiableList(Arrays.asList(names));
        }

        public Arg(String name, int arity) {
            argName = name;
            List<String> names = new ArrayList<>(arity);
            for (int i = 0; i < arity; i++) {
                names.add(name + "_" + i);
            }
            valueNames = Collections.unmodifiableList(names);
        }

        public boolean isVarArg() {
            return argName != null;
        }

        public static String[] concatValues(Arg... args) {
            List<String> result = new ArrayList<>();
            for (Arg arg : args) {
                result.addAll(arg.valueNames);
            }
            return result.toArray(new String[0]);
        }
    }
}
