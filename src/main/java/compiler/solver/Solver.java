package compiler.solver;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import alice.tuprolog.Var;
import compiler.prolog.TuProlog;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static compiler.prolog.TuProlog.list;
import static compiler.prolog.TuProlog.struct;

public class Solver {
    private final TuProlog prolog;
    private final Model model;
    private final VisMap visMap;

    public Solver(List<Term> terms) throws InvalidTheoryException {
        prolog = new TuProlog(terms.toArray(new Term[0]));
        model = new Model();
        visMap = new VisMap(model);
    }

    // TODO: Extendibility
    public List<VisElem> solve() {
        attributePredicate("shape", "type");
        attributePredicate("pos", "x1", "y1", "z");
        attributePredicate("pos", "x1", "y1");
        attributePredicate("posX", "x1");
        attributePredicate("posY", "y1");
        attributePredicate("posZ", "z");
        attributePredicate("dimensions", "width", "height");
        attributePredicate("width", "width");
        attributePredicate("height", "height");
        attributePredicate("colour", "colour");
        attributePredicate("border-colour", "border-colour");

        predicate("line", (visMap, values) -> {
            Term term1 = values.get("N1");
            Term term2 = values.get("N2");
            VisElem elem1 = visMap.get(term1);
            VisElem elem2 = visMap.get(term2);
            VisElem line = visMap.get(list(term1, term2));
            line.setVar("x1", elem1.getVar("centerX"));
            line.setVar("y1", elem1.getVar("centerY"));
            line.setVar("x2", elem2.getVar("centerX"));
            line.setVar("y2", elem2.getVar("centerY"));
        }, "N1", "N2");

        predicate("image", (visMap, values) -> {
            VisElem elem = visMap.get(values.get("N"));
            String image;
            try {
                image = FileUtils.ImageToBase64(new File(values.get("Image").toString()));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            elem.set("type", "image");
            elem.set("image", image);
        }, "N", "Image");

        alignPredicate("alignX", "x1");
        alignPredicate("alignY", "y1");

        positionPredicate("below", "y1", "y2", true);
        positionPredicate("above", "y2", "y1", false);
        positionPredicate("right", "x1", "x2", true);
        positionPredicate("left", "x2", "x1", false);
        positionPredicate("after", "z", "z", true);
        positionPredicate("before", "z", "z", false);

        visMap.values().forEach(VisElem::setDefaults);
        model.getSolver().solve();
        return new ArrayList<>(visMap.values());
    }

    private void predicate(String name, BiConsumer<VisMap, Map<String, Term>> consumer, String... args) {
        query(name, args).forEach(resultMap -> consumer.accept(visMap, resultMap));
    }

    private void attributePredicate(String name, String... attributes) {
        String[] varNames = varNames(attributes.length + 1);
        predicate(name, (visMap, values) -> {
            // Assume the first term is the key
            VisElem visElem = visMap.get(values.get(varNames[0]));
            for (int i = 1; i < varNames.length; i++) {
                visElem.set(attributes[i - 1], values.get(varNames[i]).toString());
            }
        }, varNames);
    }

    private void alignPredicate(String name, String varName) {
        predicate(name, (visMap, values) -> {
            VisElem elem1 = visMap.get(values.get("N1"));
            VisElem elem2 = visMap.get(values.get("N2"));
            if (elem1.hasVar(varName)) {
                elem2.setVar(varName, elem1.getVar(varName));
            } else {
                elem1.setVar(varName, elem2.getVar(varName));
            }
        }, "N1", "N2");
    }

    private void positionPredicate(String name, String varName1, String varName2, boolean greater) {
        // Relative
        predicate(name, (visMap, values) -> {
            VisElem elem1 = visMap.get(values.get("N1"));
            VisElem elem2 = visMap.get(values.get("N2"));
            IntVar var1 = elem1.getVar(varName1);
            IntVar var2 = elem2.getVar(varName2);
            if (greater) {
                var1.gt(var2).post();
            } else {
                var2.gt(var1).post();
            }
        }, "N1", "N2");

        // Absolute
        predicate(name, (visMap, values) -> {
            VisElem elem1 = visMap.get(values.get("N1"));
            VisElem elem2 = visMap.get(values.get("N2"));
            IntVar var1 = elem1.getVar(varName1);
            IntVar var2 = elem2.getVar(varName2);
            int value = Integer.parseInt(values.get("V").toString());
            if (greater) {
                var1.sub(var2).eq(value).post();
            } else {
                var2.sub(var1).eq(value).post();
            }
        }, "N1", "N2", "V");
    }

    private List<Map<String, Term>> query(String name, String... args) {
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
