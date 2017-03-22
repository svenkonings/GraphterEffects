package errors;

public abstract class Error {

    private final int row;
    private final int column;

    private String msg;

    Error(int row, int column) {
        this.row = row;
        this.column = column;
        this.msg = "D'OH";
    }

    public Error(int row, int column, String msg) {
        this.row = row;
        this.column = column;
        this.msg = msg;

    }

    @Override
    public String toString() {
        return String.format("D'OH at line %d:%d - %s", row, column, msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }


}
