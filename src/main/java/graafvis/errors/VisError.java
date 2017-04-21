package graafvis.errors;

/**
 * An error encountered by the Graafvis compiler
 */
public abstract class VisError {

    /** The line index of where the error was encountered */
    private final int row;
    /** The char position index at which the error was encountered */
    private final int column;
    /** A message clarifying the error */
    private String msg;

    /**
     * Create a new VisError
     *
     * @param row    The line index of where the error was encountered
     * @param column The char position index at which the error was encountered
     */
    protected VisError(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Get a string representation of this error
     */
    @Override
    public String toString() {
        if (msg == null) {
            return String.format("VisError at line %d:%d", row, column);
        }
        return msg;
    }

    /** Set the message clarifying this error */
    protected void setMsg(String msg) {
        this.msg = msg;
    }
    /** Get the line index of where the error was encountered */
    public int getRow() {
        return row;
    }
    /** Get the char position index at which the error was encountered */
    public int getColumn() {
        return column;
    }

}
