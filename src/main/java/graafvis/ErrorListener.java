package graafvis;

import graafvis.errors.SyntaxError;
import graafvis.errors.VisError;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Antlr error listener to collect graafvis.errors rather than send them to stderr.
 */
public class ErrorListener extends BaseErrorListener {
    /**
     * Errors collected by the listener.
     */
    private final List<VisError> errors;

    /** Create a new error listener */
    public ErrorListener() {
        this.errors = new ArrayList<>();
    }

    /** Captures and stores the syntax error */
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol, int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        this.errors.add(new SyntaxError(line, charPositionInLine, msg));
    }

    /**
     * Indicates if the listener has collected any graafvis.errors.
     */
    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    /**
     * Returns the (possibly empty) list of errors collected by the listener.
     */
    public List<VisError> getErrors() {
        return this.errors;
    }


}