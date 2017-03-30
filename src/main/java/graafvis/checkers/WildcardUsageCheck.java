package graafvis.checkers;

import graafvis.errors.VisError;
import graafvis.errors.WildcardError;
import graafvis.grammar.GraafvisBaseVisitor;
import graafvis.grammar.GraafvisParser;

import java.util.ArrayList;

/**
 * Checks if no wildcards are used in the consequence of a clause.
 */
class WildcardUsageCheck extends GraafvisBaseVisitor<Void> {

    /** List of errors obtained during the checking phase */
    private final ArrayList<VisError> errors;

    /** Create a new wildcard usage check */
    WildcardUsageCheck() {
        errors = new ArrayList<>();
    }

    /*
     * Visitor methods
     */

    /** Encountered a wildcard. Add an error */
    @Override
    public Void visitWildcard(GraafvisParser.WildcardContext ctx) {
        int line = ctx.UNDERSCORE().getSymbol().getLine();
        int column = ctx.UNDERSCORE().getSymbol().getCharPositionInLine();
        errors.add(new WildcardError(line, column));
        return null;
    }

    /*
     * Dead ends
     */

    @Override
    public Void visitImport_vis(GraafvisParser.Import_visContext ctx) {
        return null;
    }

    @Override
    public Void visitNode_label_gen(GraafvisParser.Node_label_genContext ctx) {
        return null;
    }

    @Override
    public Void visitEdge_label_gen(GraafvisParser.Edge_label_genContext ctx) {
        return null;
    }

    @Override
    public Void visitAntecedent(GraafvisParser.AntecedentContext ctx) {
        return null;
    }

    @Override
    public Void visitNumExprLiteral(GraafvisParser.NumExprLiteralContext ctx) {
        return null;
    }

    @Override
    public Void visitPredicate(GraafvisParser.PredicateContext ctx) {
        return null;
    }

    @Override
    public Void visitTermGround(GraafvisParser.TermGroundContext ctx) {
        return null;
    }

    @Override
    public Void visitGround_term(GraafvisParser.Ground_termContext ctx) {
        return null;
    }

    @Override
    public Void visitVariable(GraafvisParser.VariableContext ctx) {
        return null;
    }

    /*
     * Getters
     */

    public ArrayList<VisError> getErrors() {
        return errors;
    }

}
