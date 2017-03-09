package thelibraryv2.exceptions;


public class InvalidSizeException extends Exception {

    private InvalidSizeException(){}

    public InvalidSizeException(int expected, int actual) {}
}
