package com.github.meteoorkip.graafvis.checkers;

import com.github.meteoorkip.graafvis.errors.VisError;
import com.github.meteoorkip.graafvis.warnings.Warning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Result of the checking phase
 */
public class CheckerResult {

    /** List of errors */
    private final List<VisError> errors;
    /** List of warnings */
    private final List<Warning> warnings;

    /** Create a new checker result */
    CheckerResult() {
        errors = new ArrayList<>();
        warnings = new ArrayList<>();
    }

    /** Add an error */
    void addError(VisError error) {
        errors.add(error);
    }

    /** Add multiple errors */
    void addErrors(Collection<VisError> errors) {
        this.errors.addAll(errors);
    }

    /** Add a warning */
    void addWarning(Warning warning) {
        warnings.add(warning);
    }

    /** Add multiple warnings */
    void addWarnings(Collection<Warning> warnings) {
        this.warnings.addAll(warnings);
    }

    /*
     * Getters
     */

    /** Get all errors */
    public List<VisError> getErrors() {
        return errors;
    }

    /** Get all warnings */
    public List<Warning> getWarnings() {
        return warnings;
    }


}
