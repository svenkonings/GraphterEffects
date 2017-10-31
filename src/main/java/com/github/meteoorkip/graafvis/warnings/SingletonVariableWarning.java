package com.github.meteoorkip.graafvis.warnings;

/**
 *
 */
public class SingletonVariableWarning extends Warning {

    private final String variable;

    public SingletonVariableWarning(int row, int column, String variable) {
        super(row, column);
        this.variable = variable;
        setMsg(String.format("Warning: Singleton variable %s found at line %d:%d.", variable, row, column));
    }

    public String getVariable() {
        return variable;
    }

}
