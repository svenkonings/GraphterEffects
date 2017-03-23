package solver.constraintlogic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static org.chocosolver.solver.variables.IntVar.MAX_INT_BOUND;

/**
 * The {@code VisElem} class respresents a visualization element.A visualization element has a single {@code VisType}
 * type with a combination of String values and Integer Variables.
 */
// TODO: Improve exception handling
// TODO: Add ability to replace existing values/vars?
public class VisElem {

    /** The type of this element. */
    private VisType type;

    /** The model associated with this element. */
    private final Model model;

    /** The values of this element. */
    private final Map<String, String> values;

    /** The variables of this element. */
    private final Map<String, IntVar> vars;

    /**
     * Constructs a new {@code VisElem} with the given model.
     *
     * @param model The given model.
     */
    public VisElem(Model model) {
        this.model = model;
        this.values = new HashMap<>();
        this.vars = new HashMap<>();
        setDefaultVars();
    }

    /**
     * Initializes the default variables. The defaults are:
     * <ul>
     * <li>x and y posistions</li>
     * <li>x1 and y1 positions (same as x and y)</li>
     * <li>width and heigth</li>
     * <li>x2 and y2 posistions (x + width and y + heigth)</li>
     * <li>cx and cy positions (center x and center y)</li>
     * </ul>
     */
    private void setDefaultVars() {
        setVar("x1", getVar("x"));
        setVar("y1", getVar("y"));
        setVar("x2", getVar("x").add(getVar("width")).intVar());
        setVar("y2", getVar("y").add(getVar("height")).intVar());
        setVar("cx", getVar("x").add(getVar("width").div(2)).intVar());
        setVar("cy", getVar("y").add(getVar("heigth").div(2)).intVar());
    }

    /**
     * @return The type of this element.
     */
    public VisType getType() {
        return type;
    }

    /**
     * Sets the type of this element, if not already set, and initializes the default values belonging to the given type
     *
     * @param newType The type to set.
     * @throws ElementException If the type of this element is already set.
     */
    public void setType(VisType newType) {
        if (type != null && !type.equals(newType)) {
            throw new ElementException("This element already has type %s instead of %s", type, newType);
        }
        type = newType;
        setTypeVars();
    }

    /**
     * Initializes the default values belonging to the type of this element.
     *
     * @throws ElementException If this element has no type.
     */
    private void setTypeVars() {
        if (type == null) {
            throw new ElementException("Type not set");
        }
        switch (type) {
            case ELLIPSE:
                setVar("rx", getVar("width").div(2).intVar());
                setVar("ry", getVar("height").div(2).intVar());
                break;
        }
    }

    /**
     * Set the given name-value pair. If the value is parsable by {@link Integer#parseInt(String)}, then it will be
     * treated as the value of an {@link IntVar} variable. Otherwise the value will be treated as a {@link String}
     * constant.
     *
     * @param name  The given name.
     * @param value The given value.
     * @throws ElementException If the name is already assigned to a different value.
     */
    public void set(String name, String value) {
        try {
            int varValue = Integer.parseInt(value);
            setVar(name, varValue);
        } catch (NumberFormatException e) {
            setValue(name, value);
        }
    }

    /**
     * Set the given name-value pair. The value is treated as a {@link String} constant.
     *
     * @param name  The given name.
     * @param value The given {@link String} constant.
     * @throws ElementException If the name is already assigned to a different value.
     */
    private void setValue(String name, String value) {
        if (vars.containsKey(name)) {
            throw new ElementException("%s is already defined as a variable", name);
        } else if (values.containsKey(name) && !Objects.equals(values.get(name), value)) {
            throw new ElementException("%s already has a different value", name);
        } else {
            values.put(name, value);
        }
    }

    /**
     * Sets the given name-value pair. The value is treated as the value of an {@link IntVar} variable.
     *
     * @param name     The given name.
     * @param varValue The given {@link IntVar} value.
     * @throws ElementException If the name is already assigned to a {@link String} constant.
     */
    private void setVar(String name, int varValue) {
        if (values.containsKey(name)) {
            throw new ElementException("%s is already defined as a value", name);
        } else if (vars.containsKey(name)) {
            IntVar var = vars.get(name);
            model.arithm(var, "=", varValue).post();
        } else {
            IntVar var = model.intVar(model.generateName(name), varValue);
            vars.put(name, var);
        }
    }

    /**
     * Sets the given name-variable pair. The variable being an {@link IntVar}.
     *
     * @param name The given name.
     * @param var  The given {@link IntVar} variable.
     * @throws ElementException If the name is already defined.
     */
    public void setVar(String name, IntVar var) {
        if (values.containsKey(name)) {
            throw new ElementException("%s is already defined as a value", name);
        } else if (vars.containsKey(name)) {
            throw new ElementException("%s is already defined as a variable", name);
        } else {
            vars.put(name, var);
        }
    }

    /**
     * Converts the value of an {@link IntVar} variable to a {@link String} value.
     *
     * @param var The variable to convert.
     * @return The String value, or {@code null} if the {@link IntVar} isn't instantiated.
     */
    private static String varToValue(IntVar var) {
        if (var.isInstantiated()) {
            return Integer.toString(var.getValue());
        }
        return null;
    }

    /**
     * Get the {@link String} value belonging to the given name.
     *
     * @param name The given name.
     * @return The value, or {@code null} if it doesn't exists.
     */
    public String getValue(String name) {
        if (values.containsKey(name)) {
            return values.get(name);
        } else if (vars.containsKey(name)) {
            return varToValue(vars.get(name));
        } else {
            return null;
        }
    }

    /**
     * Get the {@link IntVar} variable belonging to the given name.
     *
     * @param name The given name.
     * @return The {@link IntVar} variable.
     * @throws ElementException If the name belongs to a value instead of a variable.
     */
    public IntVar getVar(String name) {
        if (values.containsKey(name)) {
            throw new ElementException("%s is already defined as a value", name);
        }
        return vars.computeIfAbsent(name, key -> model.intVar(model.generateName(key), 0, MAX_INT_BOUND));
    }

    /**
     * Get a copy of the name-value pairs of this element. The value of a variable is determined by {@link
     * VisElem#varToValue(IntVar)}.
     *
     * @return A map containting the name-value pairs.
     */
    public Map<String, String> getValues() {
        Map<String, String> result = new HashMap<>(values);
        vars.forEach((name, var) -> result.put(name, varToValue(var)));
        return result;
    }

    /**
     * Get a copy of the name-variable pairs of this element.
     *
     * @return A map containing the name-variable pairs.
     */
    public Map<String, IntVar> getVars() {
        return new HashMap<>(vars);
    }

    /**
     * Transforms this visualization element to a SVG element and adds it to the given parent SVG element. The
     * name-value pairs are added as attributes to the SVG element if they are applicable to this type according to
     * {@link SvgAttributes#fromVisType(VisType)}.
     *
     * @param parent The parent SVG element.
     */
    public void addToElement(Element parent) {
        if (type == null) {
            return;
        }
        Element element = parent.addElement(VisType.toSvgElement(type));
        Set<String> attributes = SvgAttributes.fromVisType(type);
        getValues().entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .filter(entry -> attributes.contains(entry.getKey()))
                .forEach(entry -> element.addAttribute(entry.getKey(), entry.getValue()));
    }
}
