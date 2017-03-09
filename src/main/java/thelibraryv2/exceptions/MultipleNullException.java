package thelibraryv2.exceptions;


public class MultipleNullException extends Exception {

    @Override
    public String getMessage() {
        return "Too many null elements for this predicate.";
    }

}
