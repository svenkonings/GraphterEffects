package errors;


public class SyntaxError extends Error {

    public SyntaxError(int row, int column) {
        super(row, column);
        this.setMsg("Syntax Error");
    }

    public SyntaxError(int row, int column, String msg) {
        super(row, column);
        this.setMsg("Syntax Error: " + msg);
    }

}