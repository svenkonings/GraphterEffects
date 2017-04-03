package compiler.solver;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import compiler.prolog.TuProlog;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import utils.FileUtils;
import utils.TriConsumer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static compiler.prolog.TuProlog.list;

/**
 * The constraint solver.
 */
public class Solver {
    /** The {@link TuProlog} engine. */
    private final TuProlog prolog;

    /** The constraint solver {@link Model} */
    private final Model model;

    /** The mapping of visualization elements. */
    private final VisMap visMap;

    /** Mapping from query to {@link QueryConsumer}. */
    private final Map<String, QueryConsumer> queries;

    /**
     * Creates a new solver with the given terms.
     *
     * @param terms The given terms.
     * @throws InvalidTheoryException If the {@link alice.tuprolog.Theory} is invalid.
     */
    public Solver(List<Term> terms) throws InvalidTheoryException {
        this(new TuProlog(terms));
    }

    /**
     * Creates a new solver with the given {@link TuProlog}.
     *
     * @param tuProlog The given {@link TuProlog} engine.
     */
    public Solver(TuProlog tuProlog) {
        prolog = tuProlog;
        model = new Model();
        visMap = new VisMap(model);
        queries = new LinkedHashMap<>();
        setDefaults();
    }

    /**
     * Set the default queries and their associated {@link QueryConsumer}.
     */
    private void setDefaults() {
        setQuery("pos(Key, X, Y, Z)", attrQuery("Key", "x1", "X", "y1", "Y", "z", "Z"));
        setQuery("pos(Key, X, Y)", attrQuery("Key", "x1", "X", "y1", "Y"));
        setQuery("posX(Key, X)", attrQuery("Key", "x1", "X"));
        setQuery("posY(Key, Y)", attrQuery("Key", "y1", "Y"));
        setQuery("posZ(Key, Z)", attrQuery("Key", "z", "Z"));
        setQuery("dimensions(Key, Width, Height)", attrQuery("Key", "width", "Width", "height", "Height"));
        setQuery("width(Key, Width)", attrQuery("Key", "width", "Width"));
        setQuery("height(Key, Height)", attrQuery("Key", "height", "Height"));
        setQuery("colour(Key, Colour)", attrQuery("Key", "colour", "Colour"));
        setQuery("borderColour(Key, Colour)", attrQuery("Key", "border-colour", "Colour"));

        setQuery("shape(Key, Shape)", elementQuery("Key", (elem, values) -> {
            elem.setValue("type", values.get("Shape").toString());
            defaultConstraints(elem);
        }));

        setQuery("line(From, To)", forEach((visMap, values) -> {
            Term fromKey = values.get("From");
            Term toKey = values.get("To");
            Term key = list(fromKey, toKey);
            lineConstraints(visMap, key, fromKey, toKey);
        }));

        setQuery("line(Key, From, To)", forEach((visMap, values) -> {
            Term key = values.get("Key");
            Term fromKey = values.get("From");
            Term toKey = values.get("To");
            lineConstraints(visMap, key, fromKey, toKey);
        }));

        setQuery("image(Key, Image)", imageQuery("Key", "Image"));

        setQuery("alignX(Key1, Key2)", alignQuery("Key1", "Key2", "minX"));
        setQuery("alignY(Key1, Key2)", alignQuery("Key1", "Key2", "minY"));

        setQuery("below(Key1, Key2)", relPosQuery("Key1", "Key2", "minY", "maxY", true));
        setQuery("above(Key1, Key2)", relPosQuery("Key1", "Key2", "maxY", "minY", false));
        setQuery("right(Key1, Key2)", relPosQuery("Key1", "Key2", "minX", "maxX", true));
        setQuery("left(Key1, Key2)", relPosQuery("Key1", "Key2", "maxX", "minX", false));
        setQuery("after(Key1, Key2)", relPosQuery("Key1", "Key2", "z", "z", true));
        setQuery("before(Key1, Key2)", relPosQuery("Key1", "Key2", "z", "z", false));

        setQuery("below(Key1, Key2, Value)", absPosQuery("Key1", "Key2", "Value", "minY", "maxY", true));
        setQuery("above(Key1, Key2, Value)", absPosQuery("Key1", "Key2", "Value", "maxY", "minY", false));
        setQuery("right(Key1, Key2, Value)", absPosQuery("Key1", "Key2", "Value", "minX", "maxX", true));
        setQuery("left(Key1, Key2, Value)", absPosQuery("Key1", "Key2", "Value", "maxX", "minX", false));
        setQuery("after(Key1, Key2, Value)", absPosQuery("Key1", "Key2", "Value", "z", "z", true));
        setQuery("before(Key1, Key2, Value)", absPosQuery("Key1", "Key2", "Value", "z", "z", false));

        setQuery("noOverlap(Key1, Key2)", noOverlapQuery("Key1", "Key2"));
    }

    /**
     * Set the given query with the given {@link QueryConsumer}.
     *
     * @param query         The given query.
     * @param queryConsumer The given {@link QueryConsumer}.
     * @return The previous {@link QueryConsumer} associated with the query, or {@code null} if it didn't exist.
     */
    public QueryConsumer setQuery(String query, QueryConsumer queryConsumer) {
        return queries.put(query.replaceAll("\\s+", ""), queryConsumer);
    }

    /**
     * Get the {@link QueryConsumer} associated with the given query.
     *
     * @param query The given query.
     * @return The {@link QueryConsumer}, or {@code null} if it doesn't exist.
     */
    public QueryConsumer getQuery(String query) {
        return queries.get(query.replaceAll("\\s+", ""));
    }

    /**
     * Remove the given query.
     *
     * @param query The given query.
     * @return The {@link QueryConsumer} associated to this query, or {@code null} if it doesn't exist.
     */
    public QueryConsumer removeQuery(String query) {
        return queries.remove(query.replaceAll("\\s+", ""));
    }

    /**
     * Solves the constraints and returns a {@link List} of visualization elements.
     *
     * @return The {@link List} of visualization elements.
     */
    public List<VisElem> solve() {
        queries.forEach((query, queryConsumer) -> queryConsumer.accept(visMap, prolog.solve(query)));
        visMap.values().forEach(Solver::defaultValues);
        if (!model.getSolver().solve()) {
            // TODO: Change exception type
            throw new RuntimeException("No solution found.");
        }
        return new ArrayList<>(visMap.values());
    }

    /**
     * Creates a {@link QueryConsumer} that calls the given {@link BiConsumer} for every result of the solved query. The
     * {@link BiConsumer} receives the mapping of visualization elements and a {@link Map} of the results.
     *
     * @param consumer The given consumer.
     * @return The {@link QueryConsumer}.
     */
    public static QueryConsumer forEach(BiConsumer<VisMap, Map<String, Term>> consumer) {
        return (visMap, values) -> values.forEach(value -> consumer.accept(visMap, value));
    }

    /**
     * Creates a {@link QueryConsumer} that calls the given {@link BiConsumer} for every result of the solved query. The
     * {@link BiConsumer} receives the visualization element that belongs to the given key and a {@link Map} of the
     * results.
     *
     * @param key      The given key.
     * @param consumer The given consumer.
     * @return The {@link QueryConsumer}.
     */
    public static QueryConsumer elementQuery(String key, BiConsumer<VisElem, Map<String, Term>> consumer) {
        return forEach((visMap, values) -> {
            VisElem elem = visMap.get(values.get(key));
            consumer.accept(elem, values);
        });
    }

    /**
     * Creates a {@link QueryConsumer} that calls the given {@link TriConsumer} for every result of the solved query.
     * The {@link TriConsumer} receives the two visualization elements that belongs to the given keys and a {@link Map}
     * of the results.
     *
     * @param key1     The first key.
     * @param key2     The second key.
     * @param consumer The given consumer.
     * @return The {@link QueryConsumer}.
     */
    public static QueryConsumer elementPairQuery(String key1, String key2, TriConsumer<VisElem, VisElem, Map<String, Term>> consumer) {
        return forEach((visMap, values) -> {
            VisElem elem1 = visMap.get(values.get(key1));
            VisElem elem2 = visMap.get(values.get(key2));
            if (elem1 == elem2) {
                return;
            }
            consumer.accept(elem1, elem2, values);
        });
    }

    /**
     * Creates a {@link QueryConsumer} that sets the given attribute-value pairs on the visualization element that
     * belongs to the given key. The values should be the original names of the variables specified in the query. The
     * values that will be set are the resulting terms associated with the variables.
     *
     * @param key   The given key.
     * @param pairs The given pairs.
     * @return The {@link QueryConsumer}.
     */
    public static QueryConsumer attrQuery(String key, String... pairs) {
        assert pairs.length % 2 == 0;
        return elementQuery(key, (elem, values) -> {
            for (int i = 0; i < pairs.length; i += 2) {
                String attribute = pairs[i];
                String value = values.get(pairs[i + 1]).toString();
                elem.set(attribute, value);
            }
        });
    }

    public static QueryConsumer alignQuery(String key1, String key2, String varName) {
        return elementPairQuery(key1, key2, (elem1, elem2, values) -> {
            if (elem1.hasVar(varName)) {
                elem2.setVar(varName, elem1.getVar(varName));
            } else {
                elem1.setVar(varName, elem2.getVar(varName));
            }
        });
    }

    public static QueryConsumer relPosQuery(String key1, String key2, String varName1, String varName2, boolean greater) {
        return elementPairQuery(key1, key2, (elem1, elem2, values) -> {
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
        return elementPairQuery(key1, key2, (elem1, elem2, values) -> {
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

    public static QueryConsumer imageQuery(String key, String imageValue) {
        return elementQuery(key, (elem, values) -> {
            String imagePath = values.get(imageValue).toString().replaceAll("'", "");
            String image;
            try {
                image = FileUtils.getImageSVG(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            elem.set("type", "image");
            elem.set("image", image);
            defaultConstraints(elem);
        });
    }

    public static QueryConsumer noOverlapQuery(String key1, String key2) {
        return elementPairQuery(key1, key2, (elem1, elem2, values) -> {
            Model model = elem1.getVar("minX").getModel();
            model.or(
                    elem1.getVar("minX").gt(elem2.getVar("maxX")).decompose(),
                    elem1.getVar("maxX").lt(elem2.getVar("minX")).decompose()
            ).post();
            model.or(
                    elem1.getVar("minY").gt(elem2.getVar("maxY")).decompose(),
                    elem1.getVar("maxY").lt(elem2.getVar("minY")).decompose()
            ).post();
        });
    }

    /**
     * Sets the default values of the given visualization element. The defaults are:
     * <table summary="Defaults" border="1">
     * <tr><td><b>Name</b></td>     <td><b>Value</b></td></tr>
     * <tr><td>z</td>               <td>0</td></tr>
     * <tr><td>type</td>            <td>ellipse</td></tr>
     * <tr><td>colour</td>          <td>white</td></tr>
     * <tr><td>border-colour</td>   <td>black</td></tr>
     * </table>
     *
     * @param elem The given visualization element.
     */
    public static void defaultValues(VisElem elem) {
        if (!elem.hasVar("z")) elem.setVar("z", 0);

        if (!elem.hasValue("type")) {
            elem.setValue("type", "ellipse");
            defaultConstraints(elem);
        }

        if (!elem.hasValue("colour")) elem.setValue("colour", "white");
        if (!elem.hasValue("border-colour")) elem.setValue("border-colour", "black");
    }

    /**
     * Sets the default constraints of the given visualization element.
     *
     * @param elem The given visualization element.
     */
    public static void defaultConstraints(VisElem elem) {
        elem.setVar("radiusX", elem.getVar("width").div(2).intVar());
        elem.setVar("radiusY", elem.getVar("height").div(2).intVar());

        elem.setVar("minX", elem.getVar("x1"));
        elem.setVar("centerX", elem.getVar("x1").add(elem.getVar("radiusX")).intVar());
        elem.setVar("maxX", elem.getVar("x1").add(elem.getVar("width")).intVar());

        elem.setVar("minY", elem.getVar("y1"));
        elem.setVar("centerY", elem.getVar("y1").add(elem.getVar("radiusY")).intVar());
        elem.setVar("maxY", elem.getVar("y1").add(elem.getVar("height")).intVar());
    }

    /**
     * Retreives the visualization elements associated with the given terms from the given map and sets line constraints
     * for the given line element.
     *
     * @param visMap  The mapping of visualization elements.
     * @param key     The key of the line element.
     * @param fromKey The key of the visualization element at the start of this line.
     * @param toKey   The key of the visualization element at the end of this line.
     */
    public static void lineConstraints(VisMap visMap, Term key, Term fromKey, Term toKey) {
        VisElem line = visMap.get(key);
        VisElem fromElem = visMap.get(fromKey);
        VisElem toElem = visMap.get(toKey);

        if (fromElem == toElem) {
            return;
        }

        line.set("type", "line");
        if(!line.hasVar("z")) line.setVar("z", -1);
        line.setVar("x1", fromElem.getVar("centerX"));
        line.setVar("y1", fromElem.getVar("centerY"));
        line.setVar("x2", toElem.getVar("centerX"));
        line.setVar("y2", toElem.getVar("centerY"));

        line.setVar("width", line.getVar("x1").dist(line.getVar("x2")).intVar());
        line.setVar("radiusX", line.getVar("width").div(2).intVar());

        line.setVar("height", line.getVar("y1").dist(line.getVar("y2")).intVar());
        line.setVar("radiusY", line.getVar("height").div(2).intVar());

        line.setVar("minX", line.getVar("x1").min(line.getVar("x2")).intVar());
        line.setVar("centerX", line.getVar("minX").add(line.getVar("radiusX")).intVar());
        line.setVar("maxX", line.getVar("x1").max(line.getVar("x2")).intVar());

        line.setVar("minY", line.getVar("y1").min(line.getVar("y2")).intVar());
        line.setVar("centerY", line.getVar("minY").add(line.getVar("radiusY")).intVar());
        line.setVar("maxY", line.getVar("y1").max(line.getVar("y2")).intVar());
    }
}
