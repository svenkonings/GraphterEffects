package graafvis.checkers;

import graafvis.ErrorListener;
import graafvis.errors.InvalidPredicateGenerationError;
import graafvis.errors.VisError;
import graafvis.grammar.GraafvisBaseVisitor;
import graafvis.grammar.GraafvisLexer;
import graafvis.grammar.GraafvisParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import java.util.ArrayList;

/**
 * Checks whether the specified labels in label generation statements can actually be converted to predicates
 */
class LabelGenerationCheck extends GraafvisBaseVisitor<Void> {

    /** List of errors obtained during the checking phase */
    private final ArrayList<VisError> errors = new ArrayList<>();

    /** Reset checker for new usage */
    void reset() {
        errors.clear();
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

    /** Checks if a label can be generated */
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

        /* Make sure parse graafvis errors are captured */
            lexer.removeErrorListeners();
            lexer.addErrorListener(errorListener);
            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);

        /* Parse the predicate */
            GraafvisParser.CTermContext termContext = parser.cTerm();
            if (!(termContext instanceof GraafvisParser.AtomConsequenceContext)) {
                int line = termContext.getStart().getLine();
                int column = termContext.getStart().getCharPositionInLine();
                this.errors.add(new InvalidPredicateGenerationError(line, column, predicateToGenerate));
            } else if (((GraafvisParser.AtomConsequenceContext) termContext).PAR_OPEN() != null
                    || ((GraafvisParser.AtomConsequenceContext) termContext).PAR_CLOSE() != null
                    || ((GraafvisParser.AtomConsequenceContext) termContext).cTermSeries() != null) {
                int line = ((GraafvisParser.AtomConsequenceContext) termContext).predicate().getStart().getLine();
                int column = ((GraafvisParser.AtomConsequenceContext) termContext).predicate().getStart().getCharPositionInLine();
                this.errors.add(new InvalidPredicateGenerationError(line, column, predicateToGenerate));
            }
        /* Check for graafvis.errors */
            this.errors.addAll(errorListener.getErrors());
        }
        return null;
    }

    /*
     * Getters
     */

    ArrayList<VisError> getErrors() {
        return errors;
    }

}
