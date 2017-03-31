package graafvis.checkers;

import graafvis.ErrorListener;
import graafvis.errors.VisError;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import java.util.ArrayList;

/**
 * Checks if the label strings in predicate generation statements are suitable for predicate generation
 * (so "node labels: "Wolf"." would fail this check, as predicates cannot be named "Wolf").
 */
class LabelGenerationCheck extends GraafvisBaseVisitor<Void> {

    /** List of errors obtained during the checking phase */
    private final ArrayList<VisError> errors;

    /** Create a new label generation check */
    LabelGenerationCheck() {
        errors = new ArrayList<>();
    }

    /*
     * Visitor methods
     */

    /** Only label generation nodes need visiting */
    @Override
    public Void visitProgram(GraafvisParser.ProgramContext ctx) {
        if (ctx.nodeLabelGen() != null) {
            visitNodeLabelGen(ctx.nodeLabelGen());
        }
        if (ctx.edgeLabelGen() != null) {
            visitEdgeLabelGen(ctx.edgeLabelGen());
        }
        return null;
    }

    /** Checks if a label can be  */
    @Override
    public Void visitLabel(GraafvisParser.LabelContext ctx) {

        if (ctx.RENAME_TOKEN() == null) {
            String labelString = ctx.STRING().getText();
        /* Remove quotation marks */
            String predicateToGenerate = labelString.substring(1, labelString.length() - 1);

        /* Parse predicate for correctness */
            CharStream stream = new ANTLRInputStream(predicateToGenerate);
            GraafvisLexer lexer = new GraafvisLexer(stream);
            TokenStream tokenStream = new CommonTokenStream(lexer);
            GraafvisParser parser = new GraafvisParser(tokenStream);
            ErrorListener errorListener = new ErrorListener();

        /* Make sure parse graafvis.errors are captured */
            lexer.removeErrorListeners();
            lexer.addErrorListener(errorListener);
            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);

        /* Parse the predicate */
            parser.predicate();

        /* Check for graafvis.errors */
            this.errors.addAll(errorListener.getErrors());
        }
        return null;
    }

    /*
     * Getters
     */

    public ArrayList<VisError> getErrors() {
        return errors;
    }

}
