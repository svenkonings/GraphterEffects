package com.github.meteoorkip.graafvis.errors;

/**
 * An error that occurs when a variable is encountered in the consequence of a clause that has not been defined in the
 * antecedent
 */
public class UndefinedVariableError extends VisError {
    /** The variable that was not defined */
    private final String variable;

    /**
     * Create a new UndefinedVariableError
     *
     * @param row      The line number in which the error was encountered
     * @param column   The character position index at which the error was encountered
     * @param variable The variable that was not defined
     */
    public UndefinedVariableError(int row, int column, String variable) {
        super(row, column);
        this.variable = variable;
        this.setMsg(String.format("Introduced a new variable in the consequence of a clause at line %d:%d.", row, column));
    }

    /** Get the variable that was not defined */
    public String getVariable() {
        return variable;
    }

}
