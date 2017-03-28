package compiler.solver;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import utils.FileUtils;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Expr;
import za.co.wstoop.jatalog.Jatalog;
import za.co.wstoop.jatalog.Rule;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

    // TODO: Extendibility
    public List<VisElem> solve() throws DatalogException {
        attributesPredicate("shape", "type");
        attributesPredicate("pos", "x1", "y1");
        attributesPredicate("pos", "x1", "y1", "z");
        attributesPredicate("posX", "x1");
        attributesPredicate("posY", "y1");
        attributesPredicate("posZ", "z");
        attributesPredicate("width", "width");
        attributesPredicate("height", "height");
        attributesPredicate("colour", "colour");
        attributesPredicate("border-colour", "border-colour");

        // TODO: More than two keys
        varPredicate("alignX", vars -> vars.get(0).eq(vars.get(1)).post(), "x1", "x1");
        varPredicate("alignY", vars -> vars.get(0).eq(vars.get(1)).post(), "y1", "y1");

        varPredicate("above", vars -> vars.get(1).gt(vars.get(0)).post(), "y2", "y1");
        varValuePredicate("above", (vars, value) -> vars.get(1).sub(vars.get(0)).eq(value).post(), "y2", "y1");
        varPredicate("below", vars -> vars.get(0).gt(vars.get(1)).post(), "y1", "y2");
        varValuePredicate("below", (vars, value) -> vars.get(0).sub(vars.get(1)).eq(value).post(), "y1", "y2");
        varPredicate("right", vars -> vars.get(0).gt(vars.get(1)).post(), "x1", "x2");
        varValuePredicate("right", (vars, value) -> vars.get(0).sub(vars.get(1)).eq(value).post(), "x1", "x2");
        varPredicate("left", vars -> vars.get(1).gt(vars.get(0)).post(), "x2", "x1");
        varValuePredicate("left", (vars, value) -> vars.get(1).sub(vars.get(0)).eq(value).post(), "x2", "x1");

        varPredicate("before", vars -> vars.get(0).lt(vars.get(1)).post(), "z", "z");
        varPredicate("after", vars -> vars.get(0).gt(vars.get(1)).post(), "z", "z");

        // TODO: Temporary
        predicate("line", 0, (visElems, values) -> {
            visElems.get(0).set("type", "line");
            visElems.get(0).setVar("x1", visElems.get(1).getVar("centerX"));
            visElems.get(0).setVar("y1", visElems.get(1).getVar("centerY"));
            visElems.get(0).setVar("x2", visElems.get(2).getVar("centerX"));
            visElems.get(0).setVar("y2", visElems.get(2).getVar("centerY"));
        });

        // FIXME
        predicate("image", 1, ((visElems, values) -> {
            String image;
            try {
                image = FileUtils.ImageToBase64(new File(values.get(0)));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            visElems.forEach(visElem -> {
                visElem.set("type", "image");
                visElem.set("image", image);
            });
        }));

        // TODO: Temporary
        model.allDifferent(visMap.values().stream().filter(visElem -> visElem.getValue("type").equals("ellipse")).map(visElem -> visElem.getVar("centerX")).toArray(IntVar[]::new)).post();
        model.allDifferent(visMap.values().stream().filter(visElem -> visElem.getValue("type").equals("ellipse")).map(visElem -> visElem.getVar("centerY")).toArray(IntVar[]::new)).post();

        model.getSolver().solve();
        return visMap.values();
    }

    private void attributesPredicate(String name, String... attributes) {
        predicate(name, attributes.length, ((visElems, values) -> visElems.forEach(visElem -> {
            for (int i = 0; i < attributes.length; i++) {
                visElem.set(attributes[i], values.get(i));
            }
        })));
    }

    private void varPredicate(String name, Consumer<List<IntVar>> consumer, String... varNames) {
        varValuesPredicate(name, 0, ((vars, values) -> consumer.accept(vars)), varNames);
    }

    private void varValuePredicate(String name, BiConsumer<List<IntVar>, Integer> consumer, String... varNames) {
        varValuesPredicate(name, 1, ((vars, values) -> consumer.accept(vars, values.get(0))), varNames);
    }

    private void varValuesPredicate(String name, int valueArity, BiConsumer<List<IntVar>, List<Integer>> consumer, String... varNames) {
        predicate(name, valueArity, ((visElems, values) -> {
            List<IntVar> vars = getVars(visElems, varNames);
            List<Integer> intValues = values.stream().map(Integer::valueOf).collect(Collectors.toList());
            consumer.accept(vars, intValues);
        }));
    }

    private static List<IntVar> getVars(List<VisElem> visElems, String[] varNames) {
        List<IntVar> results = new ArrayList<>(varNames.length);
        for (int i = 0; i < varNames.length; i++) {
            results.add(visElems.get(i).getVar(varNames[i]));
        }
        return results;
    }

    private void predicate(String name, int valueArity, BiConsumer<List<VisElem>, List<String>> consumer) {
        jatalog.getIdb().stream()
                .map(Rule::getHead)
                .filter(expr -> expr.getPredicate().equals(name) || expr.getPredicate().startsWith("_" + name + "_"))
                .forEach(expr -> predicate(expr, valueArity, consumer));
    }

    private void predicate(Expr expr, int valueArity, BiConsumer<List<VisElem>, List<String>> consumer) {
        int[] keyArityArray = nameToArityArray(expr.getPredicate());
        if (!checkArity(keyArityArray, expr.arity(), valueArity)) {
            return;
        }
        String[] args = generateList(expr.arity());
        Collection<Map<String, String>> results = query(expr(expr.getPredicate(), args));
        for (Map<String, String> result : results) {
            List<VisElem> visElems = new ArrayList<>();
            List<String> values = new ArrayList<>();
            int position = 0;
            for (int keyArity : keyArityArray) {
                String[] key = new String[keyArity];
                for (int i = 0; i < keyArity; i++) {
                    key[i] = result.get(args[position++]);
                }
                visElems.add(visMap.get(key));
            }
            while (position < args.length) {
                values.add(result.get(args[position++]));
            }
            consumer.accept(visElems, values);
        }
    }

    private Collection<Map<String, String>> query(Expr goal) {
        Collection<Map<String, String>> results = null;
        try {
            results = jatalog.query(goal);
        } catch (DatalogException e) {
            e.printStackTrace();
        }
        return results;
    }

    private static boolean checkArity(int[] keyArityArray, int exprArity, int valueArity) {
        for (int keyArrity : keyArityArray) {
            exprArity -= keyArrity;
        }
        return exprArity == valueArity;
    }

    private static int[] nameToArityArray(String name) {
        int[] arity = Arrays.stream(name.split("_"))
                .skip(2)
                .mapToInt(Integer::parseInt)
                .toArray();

        // TODO: Temporary
        if (arity.length == 0) {
            arity = new int[]{1};
        }
        return arity;
    }

    private static String[] generateList(int length) {
        String[] result = new String[length];
        for (int i = 0; i < length; i++) {
            result[i] = "X" + i;
        }
        return result;
    }
}
