package graafvis.errors;

/**
 *
 */
public class ImportError extends VisError {

    private final String fileName;

    public ImportError(int row, int column, String filename) {
        super(row, column);
        this.fileName = filename;
        this.setMsg(String.format("Could not consult %s.", filename));
    }

    public String getFileName() {
        return fileName;
    }

}
