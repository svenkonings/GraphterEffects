package errors;

/**
 *
 */
public class ImportError extends Error {

    private final String fileName;

    ImportError(int row, int column, String filename) {
        super(row, column);
        this.fileName = filename;
        this.setMsg(String.format("Could not consult %s.", filename));
    }

    public String getFileName() {
        return fileName;
    }

}
