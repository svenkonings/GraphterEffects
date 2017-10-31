package com.github.meteoorkip.solver;

import org.chocosolver.solver.Cause;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.github.meteoorkip.utils.StringUtils.parseInt;

/**
 * The {@code VisElem} class respresents a visualization element. A visualization element consists of name-value pairs,
 * with the values being {@link String} constants, and name-variable pairs, with the variables being {@link IntVar}
 * variables. The values of instantiated variables can also be retreiverd as {@link String} constants.
 */
public class VisElem {

    /** The key of this element. */
    private final String key;

    /** The model associated with this element. */
    private final Model model;

    /** The default lower bound of a variable. */
    private final int lowerBound;

    /** The default upper bound of a variable. */
    private final int upperBound;

    /** The values of this element. */
    private final Map<String, String> values;

    /** The variables of this element. */
    private final Map<String, IntVar> vars;

    /**
     * Constructs a new {@code VisElem} with the given key, model and default bounds.
     *
     * @param key        The given key.
     * @param model      The given model.
     * @param lowerBound The default lower bound of a variable.
     * @param upperBound The default upper bound of a variable.
     */
    public VisElem(String key, Model model, int lowerBound, int upperBound) {
        this.key = key;
        this.model = model;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.values = new LinkedHashMap<>();
        this.vars = new LinkedHashMap<>();
    }

    /**
     * If the value is parsable by {@link com.github.meteoorkip.utils.StringUtils#parseInt(String)} it will be treated
     * as the value of an {@link IntVar} variable and set the name-variable pair. Otherwise the value will be treated as
     * a {@link String} constant and set the name-value pair.
     *
     * @param name  The given name.
     * @param value The given value.
     * @throws ElementException If the name can't be assigned to the given value.
     */
    public void set(String name, String value) {
        try {
            int constant = parseInt(value);
            setVar(name, constant);
        } catch (NumberFormatException e) {
            setValue(name, value);
        }
    }

    /**
     * Set the given name-value pair.
     *
     * @param name  The given name.
     * @param value The given {@link String} constant.
     * @return The value.
     * @throws ElementException If the name is already assigned to a variable or to a different value.
     */
    public String setValue(String name, String value) {
        if (vars.containsKey(name)) {
            IntVar var = vars.get(name);
            throw new ElementException("%s.%s is already defined as the variable %s and" +
                    "can't be defined as the value %s", key, name, var, value);
        } else if (values.containsKey(name)) {
            String currentValue = values.get(name);
            if (!currentValue.equals(value)) {
                throw new ElementException("%s.%s already has the value %s instead of %s",
                        key, name, currentValue, value);
            }
            return value;
        } else {
            values.put(name, value);
            return value;
        }
    }

    /**
     * Sets the given name-variable pair. The value is treated as the constant of an {@link IntVar} variable.
     *
     * @param name     The given name.
     * @param constant The given value.
     * @return The variable.
     * @throws ElementException If the name is already assigned to a value or the variable can't be instantiated to the
     *                          given constant.
     */
    public IntVar setVar(String name, int constant) {
        if (values.containsKey(name)) {
            String value = values.get(name);
            throw new ElementException("%s.%s is already defined as the value %s and" +
                    "can't be defined as a variable with the constant %d", key, name, value, constant);
        } else if (vars.containsKey(name)) {
            IntVar var = vars.get(name);
            if (!var.isInstantiated()) {
                try {
                    var.instantiateTo(constant, Cause.Null);
                } catch (ContradictionException e) {
                    throw new ElementException("%s.%s with domain [%d, %d] can't be instantiated to %d",
                            key, name, var.getLB(), var.getUB(), constant);
                }
            } else if (!var.isInstantiatedTo(constant)) {
                throw new ElementException("%s.%s already has the value %d instead of %d",
                        key, name, var.getValue(), constant);
            }
            return var;
        } else {
            IntVar var = model.intVar(constant);
            vars.put(name, var);
            return var;
        }
    }

    /**
     * Set the given name-variable pair. The {@link IntVar} varaible uses the given lower and upper bounds.
     *
     * @param name The given name.
     * @param lb   The given lower bound value.
     * @param ub   The given upper bound value.
     * @return The variable.
     * @throws ElementException If the name is already assigned to a value or the bounds of the variable can't be
     *                          updated to the given bounds.
     */
    public IntVar setVar(String name, int lb, int ub) {
        if (values.containsKey(name)) {
            String value = values.get(name);
            throw new ElementException("%s.%s is already defined as the value %s and" +
                    "can't be defined as a variable with the bounds [%d, %d]", key, name, value, lb, ub);
        } else if (vars.containsKey(name)) {
            IntVar var = vars.get(name);
            try {
                var.updateLowerBound(lb, Cause.Null);
            } catch (ContradictionException e) {
                throw new ElementException("%s.%s with domain [%d, %d] can't update the lower bound to %d",
                        key, name, var.getLB(), var.getUB(), lb);
            }
            try {
                var.updateUpperBound(ub, Cause.Null);
            } catch (ContradictionException e) {
                throw new ElementException("%s.%s with domain [%d, %d] can't update the upper bound to %d",
                        key, name, var.getLB(), var.getUB(), ub);
            }
            return var;
        } else {
            IntVar var = model.intVar(lb, ub);
            vars.put(name, var);
            return var;
        }
    }

    /**
     * Sets the given name-variable pair. The variable being an {@link IntVar}.
     *
     * @param name   The given name.
     * @param newVar The given {@link IntVar} variable.
     * @return The variable.
     * @throws ElementException If the name is already assigned to a value.
     */
    public IntVar setVar(String name, IntVar newVar) {
        if (values.containsKey(name)) {
            String value = values.get(name);
            throw new ElementException("%s.%s is already defined as the value %s and" +
                    "can't be defined as the variable %s", key, name, value, newVar);
        } else if (vars.containsKey(name)) {
            IntVar var = vars.get(name);
            if (!var.equals(newVar)) {
                var.eq(newVar).post();
            }
            return var;
        } else {
            vars.put(name, newVar);
            return newVar;
        }
    }

    /**
     * Sets or replaces the varaible associated to the given name with the given constant.
     *
     * @param name     The given name.
     * @param constant The given value.
     * @return The variable.
     */
    public IntVar replaceVar(String name, int constant) {
        IntVar var = model.intVar(constant);
        vars.put(name, var);
        return var;
    }

    /**
     * Get the key of this element.
     *
     * @return The key of this element.
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the model associated to this element.
     *
     * @return The model associated to this element.
     */
    public Model getModel() {
        return model;
    }

    /**
     * Get the default lower bound.
     *
     * @return The lower bound.
     */
    public int getLowerBound() {
        return lowerBound;
    }

    /**
     * Get the default upper bound.
     *
     * @return The upper bound.
     */
    public int getUpperBound() {
        return upperBound;
    }

    /**
     * Converts the value of an {@link IntVar} variable to a {@link String} value.
     *
     * @param var The variable to convert.
     * @return The value, or {@code null} if the {@link IntVar} isn't instantiated.
     */
    private static String varToValue(IntVar var) {
        if (var.isInstantiated()) {
            return Integer.toString(var.getValue());
        }
        return null;
    }

    /**
     * Returns whether there is a value belonging to the given name. Instatiated variables also count as values.
     *
     * @param name The given name.
     * @return {@code true} if there is a value, {@code false} otherwise.
     */
    public boolean hasValue(String name) {
        return values.containsKey(name) || vars.containsKey(name) && vars.get(name).isInstantiated();
    }

    /**
     * Get the {@link String} value belonging to the given name. The value of a variable is determined by {@link
     * VisElem#varToValue(IntVar)}.
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
     * Returns whether there is a variable belonging to the given name.
     *
     * @param name The given name.
     * @return {@code true} if there is a variable, {@code false} otherwise.
     */
    public boolean hasVar(String name) {
        return vars.containsKey(name);
    }

    /**
     * Get the {@link IntVar} variable belonging to the given name or create a new one wih the default lower and upper
     * bounds.
     *
     * @param name The given name.
     * @return The {@link IntVar} variable.
     */
    public IntVar getVar(String name) {
        if (vars.containsKey(name)) {
            return vars.get(name);
        } else {
            return setVar(name, lowerBound, upperBound);
        }
    }

    /**
     * Get a copy of the name-value pairs of this element. The value of a variable is determined by {@link
     * VisElem#varToValue(IntVar)}.
     *
     * @return A map containting the name-value pairs.
     */
    public Map<String, String> getValues() {
        Map<String, String> result = new LinkedHashMap<>(values);
        vars.forEach((name, var) -> {
            String value = varToValue(var);
            if (value != null) {
                result.put(name, value);
            }
        });
        return result;
    }

    /**
     * Get a copy of the name-variable pairs of this element.
     *
     * @return A map containing the name-variable pairs.
     */
    public Map<String, IntVar> getVars() {
        return new LinkedHashMap<>(vars);
    }
}
