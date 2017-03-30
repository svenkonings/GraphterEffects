package graafvis.checkers;

import graafvis.errors.UndefinedVariableError;
import graafvis.errors.VisError;
import graafvis.grammar.GraafvisBaseVisitor;
import graafvis.grammar.GraafvisParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Checks if no variables are introduced in the consequence of a clause.
 */
class VariableUsageCheck extends GraafvisBaseVisitor<Void> {

    /** List of errors obtained during the checking phase */
    private final ArrayList<VisError> errors;

    /** Stores for each clause the variables that are used in the antecedent of that clause. */
    private final ParseTreeProperty<HashSet<String>> variables;

    /** Create a new variable usage check */
    VariableUsageCheck() {
        variables = new ParseTreeProperty<>();
        errors = new ArrayList<>();
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
        /* Create two sets used for storing used variables in antecedent and consequence */
        HashSet<String> antecedentSet = new HashSet<>(), consequenceSet = new HashSet<>();
        if (ctx.antecedent() != null) {
            variables.put(ctx.antecedent(), antecedentSet);
        }
        variables.put(ctx.consequence(), consequenceSet);
        /* Visit antecedent and consequence */
        visitChildren(ctx);
        /* Compare sets */
        for (String variable : consequenceSet) {
            if (!antecedentSet.contains(variable)) {
                int line = ctx.ARROW().getSymbol().getLine();
                int column = ctx.ARROW().getSymbol().getCharPositionInLine();
                errors.add(new UndefinedVariableError(line, column, variable));
            }
        }
        return null;
    }

    /** Pass on the set to its children */
    @Override
    public Void visitAntecedent(GraafvisParser.AntecedentContext ctx) {
        variables.put(ctx.propositionalFormula(), variables.get(ctx));
        visit(ctx.propositionalFormula());
        return null;
    }

    /** Pass on the set to its children */
    @Override
    public Void visitPfNot(GraafvisParser.PfNotContext ctx) {
        variables.put(ctx.propositionalFormula(), variables.get(ctx));
        visit(ctx.propositionalFormula());
        return null;
    }

    /** Pass on the set to its children */
    @Override
    public Void visitPfLit(GraafvisParser.PfLitContext ctx) {
        variables.put(ctx.literal(), variables.get(ctx));
        visit(ctx.literal());
        return null;
    }

    /** Pass on the set to its children */
    @Override
    public Void visitPfAnd(GraafvisParser.PfAndContext ctx) {
        for (GraafvisParser.PropositionalFormulaContext formula : ctx.propositionalFormula()) {
            variables.put(formula, variables.get(ctx));
            visit(formula);
        }
        return null;
    }

    /** Pass on the set to its children */
    @Override
    public Void visitPfOr(GraafvisParser.PfOrContext ctx) {
        for (GraafvisParser.PropositionalFormulaContext formula : ctx.propositionalFormula()) {
            variables.put(formula, variables.get(ctx));
            visit(formula);
        }
        return null;
    }

    /** Pass on the set to its children */
    @Override
    public Void visitPfNest(GraafvisParser.PfNestContext ctx) {
        variables.put(ctx.propositionalFormula(), variables.get(ctx));
        visit(ctx.propositionalFormula());
        return null;
    }

    /** Pass on the set to its children */
    @Override
    public Void visitConsequence(GraafvisParser.ConsequenceContext ctx) {
        for (GraafvisParser.LiteralContext literal : ctx.literal()) {
            variables.put(literal, variables.get(ctx));
            visit(literal);
        }
        return null;
    }

    /** Pass on the set to its children */
    @Override
    public Void visitAtomLiteral(GraafvisParser.AtomLiteralContext ctx) {
        variables.put(ctx.atom(), variables.get(ctx));
        return visitAtom(ctx.atom());
    }

    /** Pass on the set to its children */
    @Override
    public Void visitMultiAtomLiteral(GraafvisParser.MultiAtomLiteralContext ctx) {
        variables.put(ctx.multiAtom(), variables.get(ctx));
        visitMultiAtom(ctx.multiAtom());
        return null;
    }

    /** Pass on the set to its children */
    @Override
    public Void visitAtom(GraafvisParser.AtomContext ctx) {
        variables.put(ctx.termTuple(), variables.get(ctx));
        return visitTermTuple(ctx.termTuple());
    }

    /** Pass on the set to its children */
    @Override
    public Void visitMultiAtom(GraafvisParser.MultiAtomContext ctx) {
        for (GraafvisParser.TermContext term : ctx.term()) {
            variables.put(term, variables.get(ctx));
            visit(term);
        }
        for (GraafvisParser.TermTupleContext termTuple : ctx.termTuple()) {
            variables.put(termTuple, variables.get(ctx));
            visitTermTuple(termTuple);
        }
        return null;
    }

    /** Pass on the set to its children */
    @Override
    public Void visitTermTuple(GraafvisParser.TermTupleContext ctx) {
        for (GraafvisParser.TermContext term : ctx.term()) {
            variables.put(term, variables.get(ctx));
            visit(term);
        }
        return null;
    }

    /** Pass on the set to its children */
    @Override
    public Void visitTermVar(GraafvisParser.TermVarContext ctx) {
        variables.put(ctx.variable(), variables.get(ctx));
        visitVariable(ctx.variable());
        return null;
    }

    /** Pass on the set to its children */
    @Override
    public Void visitTermAtom(GraafvisParser.TermAtomContext ctx) {
        variables.put(ctx.atom(), variables.get(ctx));
        return visitAtom(ctx.atom());
    }

    /** Pass on the set to its children */
    @Override
    public Void visitTermList(GraafvisParser.TermListContext ctx) {
        for (GraafvisParser.TermContext term : ctx.term()) {
            variables.put(term, variables.get(ctx));
            visit(term);
        }
        return null;
    }

    /** Ignore wildcards */
    @Override
    public Void visitTermWildcard(GraafvisParser.TermWildcardContext ctx) {
        return null;
    }

    /** Ignore strings */
    @Override
    public Void visitTermString(GraafvisParser.TermStringContext ctx) {
        return null;
    }

    /** Ignore numbers */
    @Override
    public Void visitTermNumber(GraafvisParser.TermNumberContext ctx) {
        return null;
    }

    /** Ignore IDs */
    @Override
    public Void visitTermID(GraafvisParser.TermIDContext ctx) {
        return super.visitTermID(ctx);
    }

    /** Add the variable */
    @Override
    public Void visitVariable(GraafvisParser.VariableContext ctx) {
        variables.get(ctx).add(ctx.HID().getText());
        return null;
    }

    /*
     * Getters
     */

    public ArrayList<VisError> getErrors() {
        return errors;
    }

}
