package compiler.solver;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import compiler.prolog.TuProlog;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;
import utils.FileUtils;
import utils.QuadConsumer;
import utils.TriConsumer;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;

import static compiler.prolog.TuProlog.*;
import static utils.GraphUtils.ILLEGAL_PREFIX;
import static utils.StringUtils.parseInt;
import static utils.TermUtils.*;

/**
 * The constraint solver.
 */
public class Solver {

    private final Set<Term> clauses;

    /** Mapping from query to {@link QueryConsumer}. */
    private final Map<String, QueryConsumer> queries;

    /**
     * Creates a new solver.
     */
    public Solver() {
        clauses = new HashSet<>();
        setDefaultClauses();

        queries = new LinkedHashMap<>();
        setDefaultQueries();
    }

    /**
     * Solves the constraints and returns a {@link List} of visualization elements.
     *
     * @return The {@link List} of visualization elements.
     */
    public VisMap solve(TuProlog prolog) throws InvalidTheoryException {
        prolog.addTheory(clauses.toArray(new Term[0]));
        Model model = new Model();
        VisMap visMap = new VisMap(model);
        queries.forEach((query, queryConsumer) -> queryConsumer.accept(visMap, prolog.solve(query)));
        visMap.values().forEach(Solver::setDefaults);
        boolean succes = model.getSolver().solve();
        model.getSolver().printStatistics();
        if (!succes) {
            throw new RuntimeException("No solution found.");
        }
        return visMap;
    }

    public boolean addClause(Term head, Term body) {
        return clauses.add(clause(head, body));
    }

    public boolean removeClause(Term head, Term body) {
        return clauses.remove(clause(head, body));
    }

    private void setDefaultClauses() {
        addClause(struct("pos", var("Elem"), var("X"), var("Y")),
                struct("pos", var("Elem"), var("X"), var("Y"), var())
        );
        addClause(struct("zPos", var("Elem"), var("Z")),
                struct("zPos", var("Elem"), var(), var(), var("Z"))
        );
        addClause(struct("xPos", var("Elem"), var("X")),
                struct("pos", var("Elem"), var("X"), var())
        );
        addClause(struct("yPos", var("Elem"), var("Y")),
                struct("pos", var("Elem"), var(), var("Y"))
        );
        addClause(struct("dimensions", var("Elem"), var("Size"), var("Size")),
                struct("size", var("Elem"), var("Size"))
        );
        addClause(struct("width", var("Elem"), var("Width")),
                struct("dimensions", var("Elem"), var("Width"), var())
        );
        addClause(struct("height", var("Elem"), var("Height")),
                struct("dimensions", var("Elem"), var(), var("Height"))
        );
        addClause(struct("sameWidth", var("Elem1"), var("Elem2")),
                struct("sameDimensions", var("Elem1"), var("Elem2"))
        );
        addClause(struct("sameHeight", var("Elem1"), var("Elem2")),
                struct("sameDimensions", var("Elem1"), var("Elem2"))
        );
        addClause(struct("vis", var("Elem")), or(
                struct("shape", var("Elem"), var()),
                struct("line", var("Elem"), var(), var()),
                struct("image", var("Elem"), var()),
                struct("text", var("Elem"), var())
        ));
        addClause(struct("shape", var("Elem"), struct("rectangle")),
                struct("rectangle", var("Elem"))
        );
        addClause(struct("shape", var("Elem"), struct("ellipse")),
                struct("ellipse", var("Elem"))
        );
        addClause(struct("shape", var("Elem"), struct("square")),
                struct("square", var("Elem"))
        );
        addClause(struct("shape", var("Elem"), struct("circle")),
                struct("circle", var("Elem"))
        );
        addClause(struct("line", list(var("FromElem"), var("ToElem")), var("FromElem"), var("ToElem")),
                struct("line", var("FromElem"), var("ToElem"))
        );
        addClause(struct("line", var("Elem"), var("FromElem"), var("ToElem")),
                struct("arrow", var("Elem"), var("FromElem"), var("ToElem"))
        );
        addClause(struct("arrow", list(var("FromElem"), var("ToElem")), var("FromElem"), var("ToElem")),
                struct("arrow", var("FromElem"), var("ToElem"))
        );
        addClause(struct("arrow", var("Elem"), var("FromElem"), var("ToElem")),
                struct("doubleArrow", var("Elem"), var("FromElem"), var("ToElem"))
        );
        addClause(struct("doubleArrow", list(var("FromElem"), var("ToElem")), var("FromElem"), var("ToElem")),
                struct("doubleArrow", var("FromElem"), var("ToElem"))
        );
        addClause(struct("alignCenter", var("Elem1"), var("Elem2")),
                struct("align", var("Elem1"), var("Elem2"))
        );
        addClause(struct("below", var("Elem1"), var("Elem2"), struct("="), var("Value")),
                struct("below", var("Elem1"), var("Elem2"), var("Value"))
        );
        addClause(struct("above", var("Elem1"), var("Elem2"), struct("="), var("Value")),
                struct("above", var("Elem1"), var("Elem2"), var("Value"))
        );
        addClause(struct("right", var("Elem1"), var("Elem2"), struct("="), var("Value")),
                struct("right", var("Elem1"), var("Elem2"), var("Value"))
        );
        addClause(struct("left", var("Elem1"), var("Elem2"), struct("="), var("Value")),
                struct("left", var("Elem1"), var("Elem2"), var("Value"))
        );
        addClause(struct("before", var("Elem1"), var("Elem2"), struct("="), var("Value")),
                struct("before", var("Elem1"), var("Elem2"), var("Value"))
        );
        addClause(struct("behind", var("Elem1"), var("Elem2"), struct("="), var("Value")),
                struct("behind", var("Elem1"), var("Elem2"), var("Value"))
        );
        addClause(struct("distance", var("Elem1"), var("Elem2"), struct(">="), var("Value")),
                struct("distance", var("Elem1"), var("Elem2"), var("Value"))
        );
        addClause(struct("horizontalDistance", var("Elem1"), var("Elem2"), struct(">="), var("Value")),
                struct("horizontalDistance", var("Elem1"), var("Elem2"), var("Value"))
        );
        addClause(struct("verticalDistance", var("Elem1"), var("Elem2"), struct(">="), var("Value")),
                struct("verticalDistance", var("Elem1"), var("Elem2"), var("Value"))
        );
    }

    /**
     * Puts the given query with the given {@link QueryConsumer}.
     *
     * @param query         The given query.
     * @param queryConsumer The given {@link QueryConsumer}.
     * @return The previous {@link QueryConsumer} associated with the query, or {@code null} if it didn't exist.
     */
    public QueryConsumer putQuery(String query, QueryConsumer queryConsumer) {
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
     * Set the default queries and their associated {@link QueryConsumer}.
     */
    private void setDefaultQueries() {
        /*
          First, query the bounds
        */
        putQuery("bounds(MinBound, MaxBound)", (visMap, results) -> {
            if (results.size() == 0) {
                return;
            } else if (results.size() > 1) {
                throw new RuntimeException("Bounds can only be set once");
            }
            Map<String, Term> values = results.get(0);
            int minBound = parseInt(termToString(values.get("MinBound")));
            int maxBound = parseInt(termToString(values.get("MaxBound")));
            visMap.setBounds(minBound, maxBound);
        });

        /*
          Then query predicates that don't use relative positioning
        */
        // Markup
        putQuery("colour(Elem, Colour)", attrQuery("colour", "Colour"));
        putQuery("stroke(Elem, Colour)", attrQuery("stroke", "Colour"));

        // Absolute positioning
        putQuery("xPos(Elem, X)", attrQuery("x1", "X"));
        putQuery("yPos(Elem, Y)", attrQuery("y1", "Y"));
        putQuery("zPos(Elem, Z)", attrQuery("z", "Z"));

        // Absolute dimensions
        putQuery("width (Elem, Width) ", attrQuery("width", "Width"));
        putQuery("height(Elem, Height)", attrQuery("height", "Height"));

        // Relative dimensions
        putQuery("sameWidth (Elem1, Elem2)", equalsQuery("width"));
        putQuery("sameHeight(Elem1, Elem2)", equalsQuery("height"));

        /*
          Then query predicates that set the relative position constraints
        */
        // Visualizations
        putQuery("shape(Elem, Shape)", elementQuery((elem, values) -> {
            String shape = termToString(values.get("Shape"));
            switch (shape) {
                case "circle":
                case "square":
                    symmetricShapeConstraints(elem);
                    break;
                default:
                    shapeConstraints(elem);
                    break;
            }
            elem.setValue("type", shape);
            shapeConstraints(elem);
        }));
        putQuery("line(Elem, FromElem, ToElem)", lineQuery((line, fromElem, toElem, values) ->
                lineConstraints(line, fromElem, toElem)
        ));
        putQuery("arrow(Elem, FromElem, ToElem)", lineQuery((line, fromElem, toElem, values) ->
                line.setValue("markerEnd", "arrow")
        ));
        putQuery("doubleArrow(Elem, FromElem, ToElem)", lineQuery((line, fromElem, toElem, values) ->
                line.setValue("markerStart", "arrow")
        ));
        putQuery("image(Elem, Path)", elementQuery((elem, values) -> {
            setImage(elem, values);
            elem.set("type", "image");
            shapeConstraints(elem);
        }));
        putQuery("text(Elem, Text)", elementQuery((elem, values) -> {
            elem.setValue("type", "text");
            elem.setValue("text", stripQuotes(values.get("Text")));
            shapeConstraints(elem);
        }));

        // Global visualizations
        putQuery("backgroundImage(Path)", forEach((visMap, values) -> {
            VisElem elem = visMap.get(ILLEGAL_PREFIX + "background");
            setImage(elem, values);
            elem.setValue("type", "image");
            elem.setValue("global", "true");
        }));

        /*
          And then query predicates that use relavitve positioning
        */
        // Alignment
        putQuery("alignCenter    (Elem1, Elem2)", equalsQuery("centerX").andThen(equalsQuery("centerY")));
        putQuery("alignLeft      (Elem1, Elem2)", equalsQuery("minX"));
        putQuery("alignHorizontal(Elem1, Elem2)", equalsQuery("centerX"));
        putQuery("alignRight     (Elem1, Elem2)", equalsQuery("maxX"));
        putQuery("alignTop       (Elem1, Elem2)", equalsQuery("minY"));
        putQuery("alignVertical  (Elem1, Elem2)", equalsQuery("centerY"));
        putQuery("alignBottom    (Elem1, Elem2)", equalsQuery("maxY"));

        // Relative positioning
        putQuery("below (Elem1, Elem2)", relPosQuery("minY", "maxY", ">="));
        putQuery("above (Elem1, Elem2)", relPosQuery("maxY", "minY", "<="));
        putQuery("right (Elem1, Elem2)", relPosQuery("minX", "maxX", ">="));
        putQuery("left  (Elem1, Elem2)", relPosQuery("maxX", "minX", "<="));
        putQuery("before(Elem1, Elem2)", relPosQuery("z", "z", ">"));
        putQuery("behind(Elem1, Elem2)", relPosQuery("z", "z", "<"));

        // Absolute relative positioning
        putQuery("below (Elem1, Elem2, Operator, Value)", absPosQuery("minY", "maxY", true));
        putQuery("above (Elem1, Elem2, Operator, Value)", absPosQuery("maxY", "minY", false));
        putQuery("right (Elem1, Elem2, Operator, Value)", absPosQuery("minX", "maxX", true));
        putQuery("left  (Elem1, Elem2, Operator, Value)", absPosQuery("maxX", "minX", false));
        putQuery("before(Elem1, Elem2, Operator, Value)", absPosQuery("z", "z", true));
        putQuery("behind(Elem1, Elem2, Operator, Value)", absPosQuery("z", "z", false));

        // Overlapping
        putQuery("noOverlap          (Elem1, Elem2)", noOverlapQuery(true, true));
        putQuery("noHorizontalOverlap(Elem1, Elem2)", noOverlapQuery(true, false));
        putQuery("noVerticalOverlap  (Elem1, Elem2)", noOverlapQuery(false, true));

        // Distance
        putQuery("distance          (Elem1, Elem2, Operator, Value)", distanceQuery(true, true));
        putQuery("horizontalDistance(Elem1, Elem2, Operator, Value)", distanceQuery(true, false));
        putQuery("verticalDistance  (Elem1, Elem2, Operator, Value)", distanceQuery(false, true));

        // Enclosing
        putQuery("enclosed          (Elem1, Elem2)", enclosedQuery(true, true));
        putQuery("enclosedHorizontal(Elem1, Elem2)", enclosedQuery(true, false));
        putQuery("enclosedVertical  (Elem1, Elem2)", enclosedQuery(false, true));
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
     * Creates a {@link QueryConsumer} that calls the given {@link TriConsumer} for every result of the solved query.
     * The {@link TriConsumer} receives the visualization elements that belongs to "Elem1" and "Elem2" variables and a
     * {@link Map} of the results.
     *
     * @param consumer The given consumer.
     * @return The {@link QueryConsumer}.
     */
    public static QueryConsumer lineQuery(QuadConsumer<VisElem, VisElem, VisElem, Map<String, Term>> consumer) {
        return forEach((visMap, values) -> {
            VisElem fromElem = visMap.get(values.get("FromElem"));
            VisElem toElem = visMap.get(values.get("ToElem"));
            if (fromElem == toElem) {
                return;
            }
            VisElem line = visMap.get(values.get("Elem"));
            consumer.accept(line, fromElem, toElem, values);
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
                String value = termToString(values.get(pairs[i + 1]));
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

    public static QueryConsumer relPosQuery(String varName1, String varName2, String op) {
        return elementPairQuery((elem1, elem2, values) -> {
            IntVar var1 = elem1.getVar(varName1);
            IntVar var2 = elem2.getVar(varName2);
            Model model = var1.getModel();
            model.arithm(var1, op, var2).post();
        });
    }

    public static QueryConsumer absPosQuery(String varName1, String varName2, boolean swap) {
        return elementPairQuery((elem1, elem2, values) -> {
            IntVar var1 = elem1.getVar(varName1);
            IntVar var2 = elem2.getVar(varName2);
            String op = stripQuotes(values.get("Operator"));
            int value = termToInt(values.get("Value"));
            Model model = var1.getModel();
            if (swap) {
                model.arithm(var1.sub(var2).intVar(), op, value).post();
            } else {
                model.arithm(var2.sub(var1).intVar(), op, value).post();
            }
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

    public static QueryConsumer noOverlapQuery(boolean x, boolean y) {
        assert x || y;
        return elementPairQuery((elem1, elem2, values) -> {
            List<Constraint> constraints = new ArrayList<>();
            if (x) {
                constraints.add(elem1.getVar("minX").ge(elem2.getVar("maxX")).decompose());
                constraints.add(elem1.getVar("maxX").le(elem2.getVar("minX")).decompose());
            }
            if (y) {
                constraints.add(elem1.getVar("minY").ge(elem2.getVar("maxY")).decompose());
                constraints.add(elem1.getVar("maxY").le(elem2.getVar("minY")).decompose());
            }
            Model model = elem1.getModel();
            model.or(constraints.toArray(new Constraint[0])).post();
        });
    }

    public static QueryConsumer distanceQuery(boolean x, boolean y) {
        assert x || y;
        return elementPairQuery((elem1, elem2, values) -> {
            String op = stripQuotes(values.get("Operator"));
            int value = termToInt(values.get("Value"));
            Model model = elem1.getModel();
            if (x) model.or(
                    model.arithm(elem1.getVar("minX").sub(elem2.getVar("maxX")).intVar(), op, value),
                    model.arithm(elem2.getVar("minX").sub(elem1.getVar("maxX")).intVar(), op, value)
            ).post();
            if (y) model.or(
                    model.arithm(elem1.getVar("minY").sub(elem2.getVar("maxY")).intVar(), op, value),
                    model.arithm(elem2.getVar("minY").sub(elem1.getVar("maxY")).intVar(), op, value)
            ).post();
        });
    }

    public static void setImage(VisElem elem, Map<String, Term> values) {
        String imagePath = stripQuotes(values.get("Path"));
        String image;
        try {
            image = FileUtils.getImageSVG(new File(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        elem.set("image", image);
    }

    /**
     * Sets the default constraints of the given visualization element.
     *
     * @param elem The given visualization element.
     */
    public static void shapeConstraints(VisElem elem) {
        elem.getVar("width").ge(0).post();
        elem.getVar("height").ge(0).post();

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
     * Sets the default constraints of the given visualization element.
     *
     * @param elem The given visualization element.
     */
    public static void symmetricShapeConstraints(VisElem elem) {
        elem.getVar("size").ge(0).post();
        elem.setVar("width", elem.getVar("size"));
        elem.setVar("height", elem.getVar("size"));

        elem.setVar("radius", elem.getVar("size").div(2).intVar());
        elem.setVar("radiusX", elem.getVar("size"));
        elem.setVar("radiusY", elem.getVar("size"));

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
     * @param line     The line element.
     * @param fromElem The visualization element at the start of this line.
     * @param toElem   The visualization element at the end of this line.
     */
    public static void lineConstraints(VisElem line, VisElem fromElem, VisElem toElem) {
        line.set("type", "line");
        line.setVar("x1", fromElem.getVar("centerX"));
        line.setVar("y1", fromElem.getVar("centerY"));
        line.setVar("x2", toElem.getVar("centerX"));
        line.setVar("y2", toElem.getVar("centerY"));

        line.setVar("minX", line.getVar("x1").min(line.getVar("x2")).intVar());
        line.setVar("centerX", line.getVar("x1").add(line.getVar("x2")).div(2).intVar());
        line.setVar("maxX", line.getVar("x1").max(line.getVar("x2")).intVar());

        line.setVar("minY", line.getVar("y1").min(line.getVar("y2")).intVar());
        line.setVar("centerY", line.getVar("y1").add(line.getVar("y2")).div(2).intVar());
        line.setVar("maxY", line.getVar("y1").max(line.getVar("y2")).intVar());
    }

    /**
     * Should be called before {@link org.chocosolver.solver.Solver#solve}. Sets the default visualization element type
     * (including the associated constraints), provided it doesn't already exist.
     *
     * @param elem The given visualization element.
     */
    // TODO: Extendibility
    public static void setDefaults(VisElem elem) {
        if (!elem.hasValue("type")) {
            elem.setValue("type", "ellipse");
            shapeConstraints(elem);
        }
        if (!elem.hasVar("z")) {
            switch (elem.getValue("type")) {
                case "line":
                    elem.setVar("z", -1);
                    break;
                case "text":
                    elem.setVar("z", 1);
                    break;
                default:
                    elem.setVar("z", 0);
                    break;

            }
        }
        if (unPropagated(elem, "width", "minX", "centerX", "maxX")) {
            elem.setVar("width", 10);
        }
        if (unPropagated(elem, "height", "minY", "centerY", "maxY")) {
            elem.setVar("height", 10);
        }
    }

    public static boolean unPropagated(VisElem elem, String dimension, String min, String center, String max) {
        return elem.hasVar(dimension) &&
                !elem.getVar(dimension).isInstantiated() &&
                elem.getVar(min).getNbProps() == 2 &&
                elem.getVar(center).getNbProps() == 1 &&
                elem.getVar(max).getNbProps() == 1;
    }
}
