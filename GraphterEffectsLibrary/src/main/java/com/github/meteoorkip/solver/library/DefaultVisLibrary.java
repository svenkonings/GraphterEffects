package com.github.meteoorkip.solver.library;

import com.github.meteoorkip.solver.VisElem;
import com.github.meteoorkip.utils.FileUtils;
import com.github.meteoorkip.utils.QuadConsumer;
import it.unibo.tuprolog.core.Term;
import nl.svenkonings.jacomo.elem.expressions.bool.BoolExpr;
import nl.svenkonings.jacomo.elem.expressions.bool.binary.BiBoolExpr;
import nl.svenkonings.jacomo.elem.expressions.bool.relational.ReBoolExpr;
import nl.svenkonings.jacomo.elem.expressions.integer.IntExpr;
import nl.svenkonings.jacomo.elem.variables.integer.IntVar;
import nl.svenkonings.jacomo.elem.variables.integer.UpdatableIntVar;
import nl.svenkonings.jacomo.exceptions.unchecked.ContradictionException;
import nl.svenkonings.jacomo.model.Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.meteoorkip.prolog.TuProlog.*;
import static com.github.meteoorkip.utils.GraphUtils.ILLEGAL_PREFIX;
import static com.github.meteoorkip.utils.StringUtils.parseInt;
import static com.github.meteoorkip.utils.TermUtils.*;

public class DefaultVisLibrary extends VisLibrary {

    public DefaultVisLibrary() {
        super();
        setDefaultClauses();
        setDefaultQueries();
        setElemConsumer(elem -> {
            if (!elem.hasVar("z")) {
                if (elem.getValue("type") == null) {
                    elem.setVar("z", 0);
                } else {
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
            }
        });
    }

    private void setDefaultClauses() {
        addClause(struct("pos", var("Elem"), var("X"), var("Y")),
                struct("pos", var("Elem"), var("X"), var("Y"), var())
        );
        addClause(struct("zPos", var("Elem"), var("Z")),
                struct("pos", var("Elem"), var(), var(), var("Z"))
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
     * Set the default queries and their associated {@link QueryConsumer}.
     */
    private void setDefaultQueries() {
        /*
          First, query the bounds
        */
        putQuery("eval", ((visMap, results) -> {
            // Do nothing.
        }));
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
        putQuery("strokeWidth(Elem, Width)", attrQuery("strokeWidth", "Width"));
        putQuery("strokeDashArray(Elem, Array)", attrQuery("strokeDashArray", "Array"));

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

        // Fonts
        putQuery("fontSize(Elem, Size)", attrQuery("fontSize", "Size"));

        /*
          Then query predicates that set the relative position constraints
        */
        // Visualizations
        putQuery("shape(Elem, Shape)", elementQuery((elem, values) -> {
            String shape = termToString(values.get("Shape"));
            switch (shape) {
                case "ellipse":
                    shapeConstraints(elem, 2);
                    break;
                case "rectangle":
                    shapeConstraints(elem, 1);
                    break;
                case "circle":
                    symmetricShapeConstraints(elem, 2);
                    break;
                case "square":
                    symmetricShapeConstraints(elem, 1);
                    break;
                default:
                    throw new LibraryException("Unknown shape: %s", shape);
            }
            elem.setValue("type", shape);
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
            shapeConstraints(elem, 1);
        }));
        putQuery("text(Elem, Text)", elementQuery((elem, values) -> {
            elem.setValue("type", "text");
            elem.setValue("text", stripQuotes(values.get("Text")));
            shapeConstraints(elem, 1);
        }));

        // Global visualizations
        putQuery("backgroundImage(Path)", forEach((visMap, values) -> {
            VisElem elem = visMap.get(ILLEGAL_PREFIX + "background");
            setImage(elem, values);
            elem.setValue("type", "image");
            elem.setValue("global", "true");
        }));

        /*
          And then query predicates that use relative positioning
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
     * Creates a {@link QueryConsumer} that calls the given {@link com.github.meteoorkip.utils.TriConsumer} for every result of the solved query.
     * The {@link com.github.meteoorkip.utils.TriConsumer} receives the visualization elements that belongs to "Elem1" and "Elem2" variables and a
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
        return elementPairQuery((elem1, elem2, values) -> setVar(elem1, varName, elem2, varName));
    }

    private static BoolExpr opToBoolExpr(IntExpr expr1, String op, IntExpr expr2) {
        switch (op) {
            case "=":
                return expr1.eq(expr2);
            case "!=":
                return expr1.ne(expr2);
            case "<":
                return expr1.lt(expr2);
            case "<=":
                return expr1.le(expr2);
            case ">":
                return expr1.gt(expr2);
            case ">=":
                return expr1.ge(expr2);
            default:
                throw new RuntimeException("Unknown op: " + op);
        }
    }

    public static QueryConsumer relPosQuery(String varName1, String varName2, String op) {
        return elementPairQuery((elem1, elem2, values) -> {
            IntVar var1 = elem1.getVar(varName1);
            IntVar var2 = elem2.getVar(varName2);
            Model model = elem1.getModel();
            model.constraint(opToBoolExpr(var1, op, var2));
        });
    }

    public static QueryConsumer absPosQuery(String varName1, String varName2, boolean swap) {
        return elementPairQuery((elem1, elem2, values) -> {
            IntVar var1 = elem1.getVar(varName1);
            IntVar var2 = elem2.getVar(varName2);
            String op = stripQuotes(values.get("Operator"));
            int value = termToInt(values.get("Value"));
            Model model = elem1.getModel();
            if (swap) {
                model.constraint(opToBoolExpr(var1.sub(var2), op, IntExpr.constant(value)));
            } else {
                model.constraint(opToBoolExpr(var2.sub(var1), op, IntExpr.constant(value)));
            }
        });
    }

    public static QueryConsumer enclosedQuery(boolean x, boolean y) {
        assert x || y;
        return elementPairQuery((elem1, elem2, values) -> {
            if (x) {
                elem1.getModel().constraint(elem1.getVar("minX").ge(elem2.getVar("minX")));
                elem1.getModel().constraint(elem1.getVar("maxX").le(elem2.getVar("maxX")));
            }
            if (y) {
                elem1.getModel().constraint(elem1.getVar("minY").ge(elem2.getVar("minY")));
                elem1.getModel().constraint(elem1.getVar("maxY").le(elem2.getVar("maxY")));
            }
        });
    }

    public static QueryConsumer noOverlapQuery(boolean x, boolean y) {
        assert x || y;
        return elementPairQuery((elem1, elem2, values) -> {
            Model model = elem1.getModel();
            List<ReBoolExpr> exprs = new ArrayList<>();
            if (x) {
                exprs.add(elem1.getVar("minX").ge(elem2.getVar("maxX")));
                exprs.add(elem1.getVar("maxX").le(elem2.getVar("minX")));
            }
            if (y) {
                exprs.add(elem1.getVar("minY").ge(elem2.getVar("maxY")));
                exprs.add(elem1.getVar("maxY").le(elem2.getVar("minY")));
            }
            model.constraint(BoolExpr.or(exprs.toArray(new ReBoolExpr[0])));
        });
    }

    public static QueryConsumer distanceQuery(boolean x, boolean y) {
        assert x || y;
        return elementPairQuery((elem1, elem2, values) -> {
            String op = stripQuotes(values.get("Operator"));
            int value = termToInt(values.get("Value"));
            Model model = elem1.getModel();
            List<BiBoolExpr> exprs = new ArrayList<>();
            if (x) {
                exprs.add(
                        elem1.getVar("minX").ge(elem2.getVar("maxX")).and(
                                opToBoolExpr(elem1.getVar("minX").sub(elem2.getVar("maxX")), op, IntExpr.constant(value))
                        )
                );
                exprs.add(
                        elem2.getVar("minX").ge(elem1.getVar("maxX")).and(
                                opToBoolExpr(elem2.getVar("minX").sub(elem1.getVar("maxX")), op, IntExpr.constant(value))
                        )
                );
            }
            if (y) {
                exprs.add(
                        elem1.getVar("minY").ge(elem2.getVar("maxY")).and(
                                opToBoolExpr(elem1.getVar("minY").sub(elem2.getVar("maxY")), op, IntExpr.constant(value))
                        )
                );
                exprs.add(
                        elem2.getVar("minY").ge(elem1.getVar("maxY")).and(
                                opToBoolExpr(elem2.getVar("minY").sub(elem1.getVar("maxY")), op, IntExpr.constant(value))
                        )
                );
            }
            model.constraint(BoolExpr.or(exprs.toArray(new BiBoolExpr[0])));
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
    public static void shapeConstraints(VisElem elem, int minSize) {
        updateLowerBound(elem, "width", minSize);
        updateLowerBound(elem, "height", minSize);

        Model model = elem.getModel();

        elem.setVar("radiusX", model.intVar(elem.getVar("width").div(IntExpr.constant(2))));
        elem.setVar("radiusY", model.intVar(elem.getVar("height").div(IntExpr.constant(2))));

        elem.setVar("minX", elem.getVar("x1"));
        elem.setVar("centerX", model.intVar(elem.getVar("x1").add(elem.getVar("radiusX"))));
        elem.setVar("maxX", model.intVar(elem.getVar("x1").add(elem.getVar("width"))));

        elem.setVar("minY", elem.getVar("y1"));
        elem.setVar("centerY", model.intVar(elem.getVar("y1").add(elem.getVar("radiusY"))));
        elem.setVar("maxY", model.intVar(elem.getVar("y1").add(elem.getVar("height"))));
    }

    /**
     * Sets the default constraints of the given visualization element.
     *
     * @param elem The given visualization element.
     */
    public static void symmetricShapeConstraints(VisElem elem, int minSize) {
        IntVar size = setVar(elem, "width", elem, "height");
        elem.setVar("size", size);
        updateLowerBound(elem, "size", minSize);

        Model model = elem.getModel();

        elem.setVar("radius", model.intVar(elem.getVar("size").div(IntExpr.constant(2))));
        elem.setVar("radiusX", elem.getVar("radius"));
        elem.setVar("radiusY", elem.getVar("radius"));

        elem.setVar("minX", elem.getVar("x1"));
        elem.setVar("centerX", model.intVar(elem.getVar("x1").add(elem.getVar("radiusX"))));
        elem.setVar("maxX", model.intVar(elem.getVar("x1").add(elem.getVar("width"))));

        elem.setVar("minY", elem.getVar("y1"));
        elem.setVar("centerY", model.intVar(elem.getVar("y1").add(elem.getVar("radiusY"))));
        elem.setVar("maxY", model.intVar(elem.getVar("y1").add(elem.getVar("height"))));
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

        Model model = line.getModel();

        line.setVar("minX", model.intVar(line.getVar("x1").min(line.getVar("x2"))));
        line.setVar("centerX", model.intVar(line.getVar("x1").add(line.getVar("x2")).div(IntExpr.constant(2))));
        line.setVar("maxX", model.intVar(line.getVar("x1").max(line.getVar("x2"))));

        line.setVar("minY", model.intVar(line.getVar("y1").min(line.getVar("y2"))));
        line.setVar("centerY", model.intVar(line.getVar("y1").add(line.getVar("y2")).div(IntExpr.constant(2))));
        line.setVar("maxY", model.intVar(line.getVar("y1").max(line.getVar("y2"))));
    }

    public static IntVar setVar(VisElem elem1, String varName1, VisElem elem2, String varName2) {
        if (elem1.hasVar(varName1)) {
            return elem2.setVar(varName2, elem1.getVar(varName1));
        } else {
            return elem1.setVar(varName1, elem2.getVar(varName2));
        }
    }

    public static void updateLowerBound(VisElem elem, String varName, int lb) {
        IntVar var = elem.getVar(varName);
        if (var instanceof UpdatableIntVar) {
            try {
                ((UpdatableIntVar) var).updateLowerBound(lb);
            } catch (ContradictionException e) {
                throw new LibraryException("Couldn't apply the minimum value %d to %s.%s with bounds[%d, %d]",
                        lb, elem.getKey(), varName, var.getLowerBound(), var.getUpperBound());
            }
        }
    }
}
