package graafvis.errors;

public class UndefinedVariableError extends VisError {

    private final String variable;

    public UndefinedVariableError(int row, int column, String variable) {
        super(row, column);
        this.variable = variable;
        this.setMsg(String.format("Introduced a new variable in the consequence of a clause at line %d:%d.", row, column));
    }

    public String getVariable() {
        return variable;
    }

}
