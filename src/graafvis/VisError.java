package graafvis;

public abstract class VisError {

    private final int row;
    private final int column;

    private String msg;

    VisError(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public VisError(int row, int column, String msg) {
        this.row = row;
        this.column = column;
        this.msg = msg;
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
