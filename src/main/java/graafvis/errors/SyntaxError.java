package graafvis.errors;

/**
 * Contains a syntax error found by the ErrorListener
 */
public class SyntaxError extends VisError {

    /**
     * Create a new syntax error
     *
     * @param row       The line number in which the error was encountered
     * @param column    The character position index at which the error was encountered
     * @param msg       A message specifying what kind of syntax error occurred
     */
    public SyntaxError(int row, int column, String msg) {
        super(row, column);
        this.setMsg(String.format("Syntax error at line %d:%d: %s", row, column, msg));
    }

}
