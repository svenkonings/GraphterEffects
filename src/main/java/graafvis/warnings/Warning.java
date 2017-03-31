package graafvis.warnings;

/**
 *
 */
public abstract class Warning {

    private final int row;
    private final int column;

    private String msg;

    protected Warning(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        if (msg == null) {
            return String.format("Unspecified warning at %d:%d.", row, column);
        } else {
            return msg;
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    protected void setMsg(String msg) {
        this.msg = msg;
    }


}
