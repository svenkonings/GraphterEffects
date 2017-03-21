package solver.constraintlogic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.dom4j.Element;
import solver.constraintlogic.elements.Ellipse;
import solver.constraintlogic.elements.Rectangle;

import java.util.HashMap;
import java.util.Map;

import static org.chocosolver.solver.variables.IntVar.MAX_INT_BOUND;
import static solver.constraintlogic.Shape.*;

public class VisElem {

    private final Model model;
    private final Map<String, IntVar> values;

    public VisElem(Model model) {
        this.model = model;
        this.values = new HashMap<>();
    }

    public IntVar get(String name) {
        return values.computeIfAbsent(name, key -> {
            switch (key) {
                case "shape":
                    return model.intVar(SHAPES);
                default:
                    return model.intVar(0, MAX_INT_BOUND);
            }
        });
    }

    // TODO: Add support for non-shapes
    public Element generateElement() {
        IntVar shape = get("shape");
        if (shape.isInstantiated()) {
            switch (shape.getValue()) {
                case SHAPE_RECTANGLE:
                    return new Rectangle(get("x"), get("y"), get("width"), get("height"));
                case SHAPE_ELLIPSE:
                    return new Ellipse(get("x"), get("y"), get("width"), get("height"));
            }
        }
        return null;
    }
}
