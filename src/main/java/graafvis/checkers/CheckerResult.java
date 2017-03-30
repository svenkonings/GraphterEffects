package graafvis.checkers;

import graafvis.errors.VisError;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CheckerResult {

    private final List<VisError> errors;

    public CheckerResult() {
        errors = new ArrayList<>();
    }

    public void addError(VisError error) {
        errors.add(error);
    }

    public void addErrors(List<VisError> errors) {
        this.errors.addAll(errors);
    }

    public List<VisError> getErrors() {
        return errors;
    }
}
