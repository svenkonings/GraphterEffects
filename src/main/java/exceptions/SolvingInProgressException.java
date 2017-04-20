package exceptions;

public class SolvingInProgressException extends RuntimeException {

    public SolvingInProgressException() {
        super("Cannot change the graph of a library when solving is in progress!");
    }
}
