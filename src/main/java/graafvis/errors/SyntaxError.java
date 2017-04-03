package graafvis.errors;

public class SyntaxError extends VisError {

    public SyntaxError(int row, int column) {
        super(row, column);
        this.setMsg(String.format("Syntax error at line %d:%d", this.getRow(), this.getColumn()));
    }

    public SyntaxError(int row, int column, String msg) {
        super(row, column);
        this.setMsg("Syntax VisError: " + msg);
    }

}
