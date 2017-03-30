package compiler.solver;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import compiler.prolog.TuProlog;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static compiler.prolog.TuProlog.list;

public class Solver {
    private final TuProlog prolog;
    private final Model model;
    private final VisMap visMap;
    private final Map<String, QueryConsumer> predicates;

    public Solver(List<Term> terms) throws InvalidTheoryException {
        this(new TuProlog(terms));
    }

    public Solver(TuProlog tuProlog) {
        prolog = tuProlog;
        model = new Model();
        visMap = new VisMap(model);
        predicates = new HashMap<>();
        setDefaults();
    }

    private void setDefaults() {
        addPredicate("shape(Key, Shape)", attrPredicate("Key", "type", "Shape"));
        addPredicate("pos(Key, X, Y, Z)", attrPredicate("Key", "x1", "X", "y1", "Y", "z", "Z"));
        addPredicate("pos(Key, X, Y)", attrPredicate("Key", "x1", "X", "y1", "Y"));
        addPredicate("posX(Key, X)", attrPredicate("Key", "x1", "X"));
        addPredicate("posY(Key, Y)", attrPredicate("Key", "y1", "Y"));
        addPredicate("posZ(Key, Z)", attrPredicate("Key", "z", "Z"));
        addPredicate("dimensions(Key, Width, Height)", attrPredicate("Key", "width", "Width", "height", "Height"));
        addPredicate("width(Key, Width)", attrPredicate("Key", "width", "Width"));
        addPredicate("height(Key, Height)", attrPredicate("Key", "height", "Height"));
        addPredicate("colour(Key, Colour)", attrPredicate("Key", "colour", "Colour"));
        addPredicate("border_colour(Key, Colour)", attrPredicate("Key", "border-colour", "Colour"));

        addPredicate("line(Key1, Key2)", predicate((visMap, values) -> {
            Term term1 = values.get("Key1");
            Term term2 = values.get("Key2");
            VisElem elem1 = visMap.get(term1);
            VisElem elem2 = visMap.get(term2);
            VisElem line = visMap.get(list(term1, term2));
            line.set("type", "line");
            line.setVar("x1", elem1.getVar("centerX"));
            line.setVar("y1", elem1.getVar("centerY"));
            line.setVar("x2", elem2.getVar("centerX"));
            line.setVar("y2", elem2.getVar("centerY"));
        }));

        addPredicate("image(Key, Image)", predicate((visMap, values) -> {
            VisElem elem = visMap.get(values.get("Key"));
            String image;
            try {
                image = FileUtils.ImageToBase64(new File(values.get("Image").toString()));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            elem.set("type", "image");
            elem.set("image", image);
        }));

        addPredicate("alignX(Key1, Key2)", alignPredicate("Key1", "Key2", "x1"));
        addPredicate("alignY(Key1, Key2)", alignPredicate("Key1", "Key2", "y1"));

        addPredicate("below(Key1, Key2)", relPosPredicate("Key1", "Key2", "y1", "y2", true));
        addPredicate("above(Key1, Key2)", relPosPredicate("Key1", "Key2", "y2", "y1", false));
        addPredicate("right(Key1, Key2)", relPosPredicate("Key1", "Key2", "x1", "x2", true));
        addPredicate("left(Key1, Key2)", relPosPredicate("Key1", "Key2", "x2", "x1", false));
        addPredicate("after(Key1, Key2)", relPosPredicate("Key1", "Key2", "z", "z", true));
        addPredicate("before(Key1, Key2)", relPosPredicate("Key1", "Key2", "z", "z", false));

        addPredicate("below(Key1, Key2, Value)", absPosPredicate("Key1", "Key2", "Value", "y1", "y2", true));
        addPredicate("above(Key1, Key2, Value)", absPosPredicate("Key1", "Key2", "Value", "y2", "y1", false));
        addPredicate("right(Key1, Key2, Value)", absPosPredicate("Key1", "Key2", "Value", "x1", "x2", true));
        addPredicate("left(Key1, Key2, Value)", absPosPredicate("Key1", "Key2", "Value", "x2", "x1", false));
        addPredicate("after(Key1, Key2, Value)", absPosPredicate("Key1", "Key2", "Value", "z", "z", true));
        addPredicate("before(Key1, Key2, Value)", absPosPredicate("Key1", "Key2", "Value", "z", "z", false));
    }

    public void addPredicate(String term, QueryConsumer consumer) {
        predicates.put(term.replaceAll("\\s+", ""), consumer);
    }

    public void removePredicate(String term) {
        predicates.remove(term.replaceAll("\\s+", ""));
    }

    public List<VisElem> solve() {
        predicates.forEach((term, consumer) -> consumer.accept(visMap, prolog.query(term)));
        visMap.values().forEach(VisElem::setDefaults);
        model.getSolver().solve();
        return new ArrayList<>(visMap.values());
    }

    public static QueryConsumer predicate(BiConsumer<VisMap, Map<String, Term>> consumer) {
        return (visMap, values) -> values.forEach(resultMap -> consumer.accept(visMap, resultMap));
    }

    public static QueryConsumer attrPredicate(String key, String... pairs) {
        assert pairs.length % 2 == 0;
        return predicate((visMap, values) -> {
            VisElem visElem = visMap.get(values.get(key));
            for (int i = 0; i < pairs.length; i += 2) {
                String attribute = pairs[i];
                String value = values.get(pairs[i + 1]).toString();
                visElem.set(attribute, value);
            }
        });
    }

    public static QueryConsumer alignPredicate(String key1, String key2, String varName) {
        return predicate((visMap, values) -> {
            VisElem elem1 = visMap.get(values.get(key1));
            VisElem elem2 = visMap.get(values.get(key2));
            if (elem1.hasVar(varName)) {
                elem2.setVar(varName, elem1.getVar(varName));
            } else {
                elem1.setVar(varName, elem2.getVar(varName));
            }
        });
    }

    public static QueryConsumer relPosPredicate(String key1, String key2, String varName1, String varName2, boolean greater) {
        return predicate((visMap, values) -> {
            VisElem elem1 = visMap.get(values.get(key1));
            VisElem elem2 = visMap.get(values.get(key2));
            IntVar var1 = elem1.getVar(varName1);
            IntVar var2 = elem2.getVar(varName2);
            if (greater) {
                var1.gt(var2).post();
            } else {
                var2.gt(var1).post();
            }
        });
    }

    public static QueryConsumer absPosPredicate(String key1, String key2, String val, String varName1, String varName2, boolean greater) {
        return predicate((visMap, values) -> {
            VisElem elem1 = visMap.get(values.get(key1));
            VisElem elem2 = visMap.get(values.get(key2));
            IntVar var1 = elem1.getVar(varName1);
            IntVar var2 = elem2.getVar(varName2);
            int value = Integer.parseInt(values.get(val).toString());
            if (greater) {
                var1.sub(var2).eq(value).post();
            } else {
                var2.sub(var1).eq(value).post();
            }
        });
    }
}
