package graafvis.checkers;

import graafvis.errors.BlacklistedPredicateError;
import graafvis.errors.VisError;
import graafvis.grammar.GraafvisBaseVisitor;
import graafvis.grammar.GraafvisParser;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Makes sure blacklisted predicates are not used in consequences. Blacklists predicates that are generated from labels.
 */
public class ConsequenceBlacklist extends GraafvisBaseVisitor<Void> {

    /** Set of predicates that are RHS blacklisted by default */
    private static final HashSet<String> DEFAULT_BLACKLIST = new HashSet<>();
    static {
        DEFAULT_BLACKLIST.add("node"); // TODO -- make reference to constants
        DEFAULT_BLACKLIST.add("edge");
        DEFAULT_BLACKLIST.add("label");
        DEFAULT_BLACKLIST.add("attribute");
        DEFAULT_BLACKLIST.add("not"); // TODO -- update list
    }

    /** Set of predicates that cannot be used in consequences */
    private final HashSet<String> consequenceBlackList;

    /** List of errors obtained during the checking phase */
    private final ArrayList<VisError> errors;

    /** Create a new blacklist */
    ConsequenceBlacklist() {
        consequenceBlackList = new HashSet<>();
        consequenceBlackList.addAll(DEFAULT_BLACKLIST);
        errors = new ArrayList<>();
    }

    /*
     * Visitor methods
     */

    /** Visit all children of the program, except imports */
    @Override
    public Void visitProgram(GraafvisParser.ProgramContext ctx) {
        if (ctx.nodeLabelGen() != null) {
            visitNodeLabelGen(ctx.nodeLabelGen()); // Add generated node labels to blacklist
        }
        if (ctx.edgeLabelGen() != null) {
            visitEdgeLabelGen(ctx.edgeLabelGen()); // Add generated edge labels to blacklist
        }
        ctx.clause().forEach(this::visitClause);
        return null;
    }

    /** Add generated label predicates to the blacklist */
    @Override
    public Void visitLabel(GraafvisParser.LabelContext ctx) {
        if (ctx.RENAME_TOKEN() == null) {
            /* The label string has not been renamed. Take string content as predicate and add to blacklist */
            String labelString = ctx.STRING().getText();
            String predicateToGenerate = labelString.substring(1, labelString.length() - 1); // Remove quotation marks
            this.consequenceBlackList.add(predicateToGenerate);
        } else {
            /* The label string has been renamed. Take renamed label as predicate and add to blacklist */
            this.consequenceBlackList.add(ctx.ID().getText());
        }
        return null;
    }

    /** Visit the consequence of a clause */
    @Override
    public Void visitClause(GraafvisParser.ClauseContext ctx) {
        return visit(ctx.cTerm());
    }

    /*
     * Terms
     */

    /** Check the predicate and visit term */
    @Override
    public Void visitAtomConsequence(GraafvisParser.AtomConsequenceContext ctx) {
        blacklistCheck(ctx.ID());
        if (ctx.cTermSeries() != null) {
            return visitCTermSeries(ctx.cTermSeries());
        } else {
            return null;
        }
    }

    /** Check the predicate and visit terms */
    @Override
    public Void visitMultiAtomConsequence(GraafvisParser.MultiAtomConsequenceContext ctx) {
        blacklistCheck(ctx.ID());
        for (GraafvisParser.CMultiTermContext term : ctx.terms) {
            visitCMultiTerm(term);
        }
        return null;
    }

    /*
     * Helper methods
     */

    /** Checks if the predicate has been blacklisted. If so, it adds an error to the error list */
    private void blacklistCheck(TerminalNode id) {
        String predicate = id.getText();
        if (consequenceBlackList.contains(predicate)) {
            int line = id.getSymbol().getLine();
            int column = id.getSymbol().getCharPositionInLine();
            errors.add(new BlacklistedPredicateError(line, column, predicate));
        }
    }

    /*
     * Getters
     */

    public ArrayList<VisError> getErrors() {
        return errors;
    }

}
