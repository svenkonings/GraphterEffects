package graafvis.errors;

public abstract class VisError {

    private final int row;
    private final int column;

    private String msg;

    protected VisError(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        if (msg == null) {
            return String.format("VisError at line %d:%d", row, column);
        }
        return msg;
    }

    protected void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }


}
