package compiler.solver;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import compiler.prolog.TuProlog;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
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

        // Markup
        setQuery("colour(Elem, Colour)", attrQuery("colour", "Colour"));
        setQuery("stroke(Elem, Colour)", attrQuery("stroke", "Colour"));

        // Absolute positioning
        setQuery("pos(Elem, X, Y, Z)", attrQuery("x1", "X", "y1", "Y", "z", "Z"));
        setQuery("pos(Elem, X, Y)", attrQuery("x1", "X", "y1", "Y"));
        setQuery("xPos(Elem, X)", attrQuery("x1", "X"));
        setQuery("yPos(Elem, Y)", attrQuery("y1", "Y"));
        setQuery("zPos(Elem, Z)", attrQuery("z", "Z"));

        // Absolute dimensions
        setQuery("dimensions(Elem, Width, Height)", attrQuery("width", "Width", "height", "Height"));
        setQuery("width (Elem, Width) ", attrQuery("width", "Width"));
        setQuery("height(Elem, Height)", attrQuery("height", "Height"));

        // Alignment
        setQuery("alignLeft      (Elem1, Elem2)", equalsQuery("minX"));
        setQuery("alignHorizontal(Elem1, Elem2)", equalsQuery("centerX"));
        setQuery("alignRight     (Elem1, Elem2)", equalsQuery("maxX"));
        setQuery("alignTop       (Elem1, Elem2)", equalsQuery("minY"));
        setQuery("alignVertical  (Elem1, Elem2)", equalsQuery("centerY"));
        setQuery("alignBottom    (Elem1, Elem2)", equalsQuery("maxY"));

        // Relative dimensions
        setQuery("sameWidth (Elem1, Elem2)", equalsQuery("width"));
        setQuery("sameHeight(Elem1, Elem2)", equalsQuery("height"));

        // Relative positioning
        setQuery("below (Elem1, Elem2)", relPosQuery("minY", "maxY", true));
        setQuery("above (Elem1, Elem2)", relPosQuery("maxY", "minY", false));
        setQuery("right (Elem1, Elem2)", relPosQuery("minX", "maxX", true));
        setQuery("left  (Elem1, Elem2)", relPosQuery("maxX", "minX", false));
        setQuery("before(Elem1, Elem2)", relPosQuery("z", "z", true));
        setQuery("after (Elem1, Elem2)", relPosQuery("z", "z", false));

        // Absolute relative positioning
        setQuery("below (Elem1, Elem2, Value)", absPosQuery("minY", "maxY", true));
        setQuery("above (Elem1, Elem2, Value)", absPosQuery("maxY", "minY", false));
        setQuery("right (Elem1, Elem2, Value)", absPosQuery("minX", "maxX", true));
        setQuery("left  (Elem1, Elem2, Value)", absPosQuery("maxX", "minX", false));
        setQuery("before(Elem1, Elem2, Value)", absPosQuery("z", "z", true));
        setQuery("after (Elem1, Elem2, Value)", absPosQuery("z", "z", false));
        setQuery("below (Elem1, Elem2, Operator, Value)", absPosQuery("minY", "maxY", true));
        setQuery("above (Elem1, Elem2, Operator, Value)", absPosQuery("maxY", "minY", false));
        setQuery("right (Elem1, Elem2, Operator, Value)", absPosQuery("minX", "maxX", true));
        setQuery("left  (Elem1, Elem2, Operator, Value)", absPosQuery("maxX", "minX", false));
        setQuery("before(Elem1, Elem2, Operator, Value)", absPosQuery("z", "z", true));
        setQuery("after (Elem1, Elem2, Operator, Value)", absPosQuery("z", "z", false));

        // Overlapping
        setQuery("noOverlap          (Elem1, Elem2)", noOverlapQuery(true, true));
        setQuery("noHorizontalOverlap(Elem1, Elem2)", noOverlapQuery(true, false));
        setQuery("noVerticalOverlap  (Elem1, Elem2)", noOverlapQuery(false, true));

        // Distance
        setQuery("distance          (Elem1, Elem2, Value)", distanceQuery(true, true));
        setQuery("horizontalDistance(Elem1, Elem2, Value)", distanceQuery(true, false));
        setQuery("verticalDistance  (Elem1, Elem2, Value)", distanceQuery(false, true));
        setQuery("distance          (Elem1, Elem2, Operator, Value)", distanceQuery(true, true));
        setQuery("horizontalDistance(Elem1, Elem2, Operator, Value)", distanceQuery(true, false));
        setQuery("verticalDistance  (Elem1, Elem2, Operator, Value)", distanceQuery(false, true));

        // Enclosed
        setQuery("enclosed          (Elem1, Elem2)", enclosedQuery(true, true));
        setQuery("enclosedHorizontal(Elem1, Elem2)", enclosedQuery(true, false));
        setQuery("enclosedVertical  (Elem1, Elem2)", enclosedQuery(false, true));

        // Visualizations
        setQuery("shape(Elem, Shape)", elementQuery((elem, values) -> {
            elem.setValue("type", values.get("Shape").toString());
            defaultConstraints(elem);
        }));

        setQuery("line(Elem, FromElem, ToElem)", forEach((visMap, values) -> {
            Term key = values.get("Elem");
            Term fromKey = values.get("FromElem");
            Term toKey = values.get("ToElem");
            lineConstraints(visMap, key, fromKey, toKey);
        }));

        setQuery("line(FromElem, ToElem)", forEach((visMap, values) -> {
            Term fromKey = values.get("FromElem");
            Term toKey = values.get("ToElem");
            Term key = list(fromKey, toKey);
            lineConstraints(visMap, key, fromKey, toKey);
        }));

        setQuery("image(Elem, Image)", imageQuery());
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
     * {@link BiConsumer} receives the visualization element that belongs to the "Elem" variable and a {@link Map} of
     * the results.
     *
     * @param consumer The given consumer.
     * @return The {@link QueryConsumer}.
     */
    public static QueryConsumer elementQuery(BiConsumer<VisElem, Map<String, Term>> consumer) {
        return forEach((visMap, values) -> {
            VisElem elem = visMap.get(values.get("Elem"));
            consumer.accept(elem, values);
        });
    }

    /**
     * Creates a {@link QueryConsumer} that calls the given {@link TriConsumer} for every result of the solved query.
     * The {@link TriConsumer} receives the visualization elements that belongs to "Elem1" and "Elem2" variables and a
     * {@link Map} of the results.
     *
     * @param consumer The given consumer.
     * @return The {@link QueryConsumer}.
     */
    public static QueryConsumer elementPairQuery(TriConsumer<VisElem, VisElem, Map<String, Term>> consumer) {
        return forEach((visMap, values) -> {
            VisElem elem1 = visMap.get(values.get("Elem1"));
            VisElem elem2 = visMap.get(values.get("Elem2"));
            if (elem1 == elem2) {
                return;
            }
            consumer.accept(elem1, elem2, values);
        });
    }

    /**
     * Creates a {@link QueryConsumer} that sets the given attribute-value pairs on the visualization element that
     * belongs to the "Elem" variable. The values should be the original names of the variables specified in the query.
     * The values that will be set are the resulting terms associated with the variables.
     *
     * @param pairs The given pairs.
     * @return The {@link QueryConsumer}.
     */
    public static QueryConsumer attrQuery(String... pairs) {
        assert pairs.length % 2 == 0;
        return elementQuery((elem, values) -> {
            for (int i = 0; i < pairs.length; i += 2) {
                String attribute = pairs[i];
                String value = values.get(pairs[i + 1]).toString();
                elem.set(attribute, value);
            }
        });
    }

    public static QueryConsumer equalsQuery(String varName) {
        return elementPairQuery((elem1, elem2, values) -> {
            if (elem1.hasVar(varName)) {
                elem2.setVar(varName, elem1.getVar(varName));
            } else {
                elem1.setVar(varName, elem2.getVar(varName));
            }
        });
    }

    public static QueryConsumer relPosQuery(String varName1, String varName2, boolean swap) {
        return elementPairQuery((elem1, elem2, values) -> {
            IntVar var1 = elem1.getVar(varName1);
            IntVar var2 = elem2.getVar(varName2);
            if (swap) {
                var1.gt(var2).post();
            } else {
                var2.gt(var1).post();
            }
        });
    }

    public static QueryConsumer absPosQuery(String varName1, String varName2, boolean swap) {
        return elementPairQuery((elem1, elem2, values) -> {
            IntVar var1 = elem1.getVar(varName1);
            IntVar var2 = elem2.getVar(varName2);
            String op = getOp(values);
            int value = Integer.parseInt(values.get("Value").toString());
            Model model = var1.getModel();
            if (swap) {
                model.arithm(var1.sub(var2).intVar(), op, value).post();
            } else {
                model.arithm(var2.sub(var1).intVar(), op, value).post();
            }
        });
    }

    public static QueryConsumer imageQuery() {
        return elementQuery((elem, values) -> {
            String imagePath = termToString(values.get("Image"));
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

    public static QueryConsumer noOverlapQuery(boolean x, boolean y) {
        assert x || y;
        return elementPairQuery((elem1, elem2, values) -> {
            List<Constraint> constraints = new ArrayList<>();
            if (x) {
                constraints.add(elem1.getVar("minX").gt(elem2.getVar("maxX")).decompose());
                constraints.add(elem1.getVar("maxX").lt(elem2.getVar("minX")).decompose());
            }
            if (y) {
                constraints.add(elem1.getVar("minY").gt(elem2.getVar("maxY")).decompose());
                constraints.add(elem1.getVar("maxY").lt(elem2.getVar("minY")).decompose());
            }
            Model model = elem1.getVar("minX").getModel();
            model.or(constraints.toArray(new Constraint[0])).post();
        });
    }

    public static QueryConsumer distanceQuery(boolean x, boolean y) {
        assert x || y;
        return elementPairQuery((elem1, elem2, values) -> {
            String op = getOp(values);
            int value = Integer.parseInt(values.get("Value").toString());
            Model model = elem1.getVar("minX").getModel();
            if (x) model.or(
                    model.arithm(elem1.getVar("minX").dist(elem2.getVar("maxX")).intVar(), op, value),
                    model.arithm(elem1.getVar("maxX").dist(elem2.getVar("minX")).intVar(), op, value)
            ).post();
            if (y) model.or(
                    model.arithm(elem1.getVar("minY").dist(elem2.getVar("maxY")).intVar(), op, value),
                    model.arithm(elem1.getVar("maxY").dist(elem2.getVar("minY")).intVar(), op, value)
            ).post();
        });
    }

    public static QueryConsumer enclosedQuery(boolean x, boolean y) {
        assert x || y;
        return elementPairQuery((elem1, elem2, values) -> {
            if (x) {
                elem1.getVar("minX").ge(elem2.getVar("minX")).post();
                elem1.getVar("maxX").le(elem2.getVar("maxX")).post();
            }
            if (y) {
                elem1.getVar("minY").ge(elem2.getVar("minY")).post();
                elem1.getVar("maxY").le(elem2.getVar("maxY")).post();
            }
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
        if (!elem.hasVar("width")) elem.setVar("width", 10);
        if (!elem.hasVar("height")) elem.setVar("height", 10);

        if (!elem.hasValue("type")) {
            elem.setValue("type", "ellipse");
            defaultConstraints(elem);
        }

        if (!elem.hasValue("colour")) {
            elem.setValue("colour", "white");
            if (!elem.hasValue("stroke")) {
                elem.setValue("stroke", "black");
            }
        }
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
        if (!line.hasVar("z")) line.setVar("z", -1);
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

    public static String getOp(Map<String, Term> values) {
        Term operator = values.get("Operator");
        if (operator != null) {
            return termToString(operator);
        } else {
            return "=";
        }
    }

    public static String termToString(Term term) {
        return term.toString().replaceAll("[\"']", "");
    }
}
