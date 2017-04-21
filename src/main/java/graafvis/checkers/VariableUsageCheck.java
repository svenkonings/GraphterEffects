package graafvis.checkers;

import graafvis.errors.UndefinedVariableError;
import graafvis.errors.VisError;
import graafvis.grammar.GraafvisBaseVisitor;
import graafvis.grammar.GraafvisParser;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Checks if no variables are introduced in the consequence of a clause.
 */
class VariableUsageCheck extends GraafvisBaseVisitor<Void> {

    /** List of errors obtained during the checking phase */
    private final ArrayList<VisError> errors = new ArrayList<>();

    /** Stores the variables used in a class */
    private final HashSet<String> variables = new HashSet<>();

    /** Reset checker for new usage */
    void reset() {
        errors.clear();
        variables.clear();
    }

    /*
     * Visitor methods
     */

    /** Visit all clauses */
    @Override
    public Void visitProgram(GraafvisParser.ProgramContext ctx) {
        for (GraafvisParser.ClauseContext clause : ctx.clause()) {
            visitClause(clause);
        }
        return null;
    }

    /** Visit antecedent and consequence and compare results */
    @Override
    public Void visitClause(GraafvisParser.ClauseContext ctx) {
        /* Arrived at a new clause, clear variables set */
        variables.clear();
        /* Visit antecedent and consequence */
        return visitChildren(ctx);
    }

    /** Found a variable in the antecedent. Add to set */
    @Override
    public Void visitVariableAntecedent(GraafvisParser.VariableAntecedentContext ctx) {
        variables.add(ctx.HID().getText());
        return null;
    }

    /** Found a variable in the consequence. Check if it is in the set */
    @Override
    public Void visitVariableConsequence(GraafvisParser.VariableConsequenceContext ctx) {
        TerminalNode variable = ctx.HID();
        if (!variables.contains(variable.getText())) {
            int line = variable.getSymbol().getLine();
            int column = variable.getSymbol().getCharPositionInLine();
            errors.add(new UndefinedVariableError(line, column, variable.getText()));
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
