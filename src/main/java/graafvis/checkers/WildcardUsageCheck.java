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
    public Void visitTermWildcard(GraafvisParser.TermWildcardContext ctx) {
        int line = ctx.UNDERSCORE().getSymbol().getLine();
        int column = ctx.UNDERSCORE().getSymbol().getCharPositionInLine();
        errors.add(new WildcardError(line, column));
        return null;
    }

    /** Only visit clauses */
    @Override
    public Void visitProgram(GraafvisParser.ProgramContext ctx) {
        for (GraafvisParser.ClauseContext clause : ctx.clause()) {
            visitClause(clause);
        }
        return null;
    }

    /** Only the consequence needs visiting */
    @Override
    public Void visitClause(GraafvisParser.ClauseContext ctx) {
        return visitConsequence(ctx.consequence());
    }

    /** Visit the consequence's literals */
    @Override
    public Void visitConsequence(GraafvisParser.ConsequenceContext ctx) {
        for (GraafvisParser.LiteralContext literal : ctx.literal()) {
            visit(literal);
        }
        return null;
    }

    /** Visit the tuple of terms */
    @Override
    public Void visitAtom(GraafvisParser.AtomContext ctx) {
        return visitTermTuple(ctx.termTuple());
    }

    /** Visit the terms */
    @Override
    public Void visitMultiAtom(GraafvisParser.MultiAtomContext ctx) {
        for (GraafvisParser.TermContext term : ctx.term()) {
            visit(term);
        }
        for (GraafvisParser.TermTupleContext termTuple : ctx.termTuple()) {
            visitTermTuple(termTuple);
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
