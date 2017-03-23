package solver.constraintlogic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static org.chocosolver.solver.variables.IntVar.MAX_INT_BOUND;

// TODO: Improve exception handling
public class VisElem {

    private VisType type;
    private final Model model;
    private final Map<String, String> values;
    private final Map<String, IntVar> vars;

    public VisElem(Model model) {
        this.model = model;
        this.values = new HashMap<>();
        this.vars = new HashMap<>();
        setDefaultVars();
    }

    private void setDefaultVars() {
        setVar("x1", getVar("x"));
        setVar("y1", getVar("y"));
        setVar("x2", getVar("x").add(getVar("width")).intVar());
        setVar("y2", getVar("y").add(getVar("height")).intVar());
        setVar("cx", getVar("x").add(getVar("width").div(2)).intVar());
        setVar("cy", getVar("y").add(getVar("heigth").div(2)).intVar());
    }

    public VisType getType() {
        return type;
    }

    public void setType(VisType newType) {
        if (type != null && !type.equals(newType)) {
            throw new ElementException("This element already has type %s instead of %s", type, newType);
        }
        type = newType;
        setTypeVars();
    }

    // TODO: Set default values for certain types
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

    public void set(String name, String value) {
        try {
            int varValue = Integer.parseInt(value);
            setVar(name, varValue);
        } catch (NumberFormatException e) {
            setValue(name, value);
        }
    }

    private void setValue(String name, String value) {
        if (vars.containsKey(name)) {
            throw new ElementException("%s is already defined as a variable", name);
        } else if (values.containsKey(name) && !Objects.equals(values.get(name), value)) {
            throw new ElementException("%s already has a different value", name);
        } else {
            values.put(name, value);
        }
    }

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

    public void setVar(String name, IntVar var) {
        if (values.containsKey(name)) {
            throw new ElementException("%s is already defined as a value", name);
        } else if (vars.containsKey(name)) {
            throw new ElementException("%s is already defined as a variable", name);
        } else {
            vars.put(name, var);
        }
    }

    private static String varToValue(IntVar var) {
        if (var.isInstantiated()) {
            return Integer.toString(var.getValue());
        }
        return null;
    }

    public String getValue(String name) {
        if (values.containsKey(name)) {
            return values.get(name);
        } else if (vars.containsKey(name)) {
            return varToValue(vars.get(name));
        } else {
            return null;
        }
    }

    public IntVar getVar(String name) {
        if (values.containsKey(name)) {
            throw new ElementException("%s is already defined as a value", name);
        }
        return vars.computeIfAbsent(name, key -> model.intVar(model.generateName(key), 0, MAX_INT_BOUND));
    }

    public Map<String, String> getValues() {
        Map<String, String> result = new HashMap<>(values);
        vars.forEach((name, var) -> result.put(name, varToValue(var)));
        return result;
    }

    public Map<String, IntVar> getVars() {
        return new HashMap<>(vars);
    }

    private void removeVars(String... names) {
        for (String name : names) {
            vars.remove(name);
        }
    }

    public void addElement(Element parent) {
        if (type == null) {
            return;
        }

        Element element = parent.addElement(VisType.toSvgElement(type));
        // TODO: Check if the given attributes belong to this type
        Set<String> attributes = SvgAttributes.fromVisType(type);
        getValues().entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .filter(entry -> attributes.contains(entry.getKey()))
                .forEach(entry -> element.addAttribute(entry.getKey(), entry.getValue()));
    }
}
