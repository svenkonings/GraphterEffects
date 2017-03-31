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
    private final Map<String, QueryConsumer> queries;

    public Solver(List<Term> terms) throws InvalidTheoryException {
        this(new TuProlog(terms));
    }

    public Solver(TuProlog tuProlog) {
        prolog = tuProlog;
        model = new Model();
        visMap = new VisMap(model);
        queries = new HashMap<>();
        setDefaults();
    }

    private void setDefaults() {
        setQuery("shape(Key, Shape)", attrQuery("Key", "type", "Shape"));
        setQuery("pos(Key, X, Y, Z)", attrQuery("Key", "x1", "X", "y1", "Y", "z", "Z"));
        setQuery("pos(Key, X, Y)", attrQuery("Key", "x1", "X", "y1", "Y"));
        setQuery("posX(Key, X)", attrQuery("Key", "x1", "X"));
        setQuery("posY(Key, Y)", attrQuery("Key", "y1", "Y"));
        setQuery("posZ(Key, Z)", attrQuery("Key", "z", "Z"));
        setQuery("dimensions(Key, Width, Height)", attrQuery("Key", "width", "Width", "height", "Height"));
        setQuery("width(Key, Width)", attrQuery("Key", "width", "Width"));
        setQuery("height(Key, Height)", attrQuery("Key", "height", "Height"));
        setQuery("colour(Key, Colour)", attrQuery("Key", "colour", "Colour"));
        setQuery("border_colour(Key, Colour)", attrQuery("Key", "border-colour", "Colour"));

        setQuery("line(From, To)", forEach((visMap, values) -> {
            Term fromKey = values.get("From");
            Term toKey = values.get("To");
            Term key = list(fromKey, toKey);
            lineConstraint(visMap, key, fromKey, toKey);
        }));

        setQuery("line(Key, From, To)", forEach((visMap, values) -> {
            Term key = values.get("Key");
            Term fromKey = values.get("From");
            Term toKey = values.get("To");
            lineConstraint(visMap, key, fromKey, toKey);
        }));

        setQuery("image(Key, Image)", imageQuery("Key", "Image"));

        setQuery("alignX(Key1, Key2)", alignQuery("Key1", "Key2", "x1"));
        setQuery("alignY(Key1, Key2)", alignQuery("Key1", "Key2", "y1"));

        setQuery("below(Key1, Key2)", relPosQuery("Key1", "Key2", "y1", "y2", true));
        setQuery("above(Key1, Key2)", relPosQuery("Key1", "Key2", "y2", "y1", false));
        setQuery("right(Key1, Key2)", relPosQuery("Key1", "Key2", "x1", "x2", true));
        setQuery("left(Key1, Key2)", relPosQuery("Key1", "Key2", "x2", "x1", false));
        setQuery("after(Key1, Key2)", relPosQuery("Key1", "Key2", "z", "z", true));
        setQuery("before(Key1, Key2)", relPosQuery("Key1", "Key2", "z", "z", false));

        setQuery("below(Key1, Key2, Value)", absPosQuery("Key1", "Key2", "Value", "y1", "y2", true));
        setQuery("above(Key1, Key2, Value)", absPosQuery("Key1", "Key2", "Value", "y2", "y1", false));
        setQuery("right(Key1, Key2, Value)", absPosQuery("Key1", "Key2", "Value", "x1", "x2", true));
        setQuery("left(Key1, Key2, Value)", absPosQuery("Key1", "Key2", "Value", "x2", "x1", false));
        setQuery("after(Key1, Key2, Value)", absPosQuery("Key1", "Key2", "Value", "z", "z", true));
        setQuery("before(Key1, Key2, Value)", absPosQuery("Key1", "Key2", "Value", "z", "z", false));
    }

    public QueryConsumer setQuery(String query, QueryConsumer queryConsumer) {
        return queries.put(query.replaceAll("\\s+", ""), queryConsumer);
    }

    public QueryConsumer getQuery(String query) {
        return queries.get(query.replaceAll("\\s+", ""));
    }

    public QueryConsumer removeQuery(String query) {
        return queries.remove(query.replaceAll("\\s+", ""));
    }

    public List<VisElem> solve() {
        queries.forEach((query, queryConsumer) -> queryConsumer.accept(visMap, prolog.solve(query)));
        visMap.values().forEach(VisElem::setDefaults);
        model.getSolver().solve();
        return new ArrayList<>(visMap.values());
    }

    public static QueryConsumer forEach(BiConsumer<VisMap, Map<String, Term>> consumer) {
        return (visMap, values) -> values.forEach(value -> consumer.accept(visMap, value));
    }

    public static QueryConsumer attrQuery(String key, String... pairs) {
        assert pairs.length % 2 == 0;
        return forEach((visMap, values) -> {
            VisElem visElem = visMap.get(values.get(key));
            for (int i = 0; i < pairs.length; i += 2) {
                String attribute = pairs[i];
                String value = values.get(pairs[i + 1]).toString();
                visElem.set(attribute, value);
            }
        });
    }

    public static QueryConsumer alignQuery(String key1, String key2, String varName) {
        return forEach((visMap, values) -> {
            VisElem elem1 = visMap.get(values.get(key1));
            VisElem elem2 = visMap.get(values.get(key2));
            if (elem1.hasVar(varName)) {
                elem2.setVar(varName, elem1.getVar(varName));
            } else {
                elem1.setVar(varName, elem2.getVar(varName));
            }
        });
    }

    public static QueryConsumer relPosQuery(String key1, String key2, String varName1, String varName2, boolean greater) {
        return forEach((visMap, values) -> {
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

    public static QueryConsumer absPosQuery(String key1, String key2, String val, String varName1, String varName2, boolean greater) {
        return forEach((visMap, values) -> {
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

    public static QueryConsumer imageQuery(String key, String imagePath) {
        return forEach((visMap, values) -> {
            VisElem elem = visMap.get(values.get(key));
            String image;
            try {
                image = FileUtils.ImageToBase64(new File(values.get(imagePath).toString()));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            elem.set("type", "image");
            elem.set("image", image);
        });
    }

    public static void lineConstraint(VisMap visMap, Term key, Term fromKey, Term toKey) {
        VisElem line = visMap.get(key);
        VisElem fromElem = visMap.get(fromKey);
        VisElem toElem = visMap.get(toKey);
        line.set("type", "line");
        line.setVar("x1", fromElem.getVar("centerX"));
        line.setVar("y1", fromElem.getVar("centerY"));
        line.setVar("x2", toElem.getVar("centerX"));
        line.setVar("y2", toElem.getVar("centerY"));
    }
}
