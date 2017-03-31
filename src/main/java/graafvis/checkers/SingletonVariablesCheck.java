package graafvis.checkers;

import graafvis.grammar.GraafvisBaseVisitor;
import graafvis.grammar.GraafvisParser;
import graafvis.warnings.SingletonVariableWarning;
import graafvis.warnings.Warning;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class SingletonVariablesCheck extends GraafvisBaseVisitor<Void> {

    /** Property to keep track of variable counts on both sides of a clause */
    private final ParseTreeProperty<VariableCounter> variableCounters;
    /** Stores the latest encounter of a variable */
    private final HashMap<String, LocationInProgram> variableLocations;
    /** A list of warnings */
    private final ArrayList<Warning> warnings;

    /** Create a new singular variables check */
    public SingletonVariablesCheck() {
        variableCounters = new ParseTreeProperty<>();
        variableLocations = new HashMap<>();
        warnings = new ArrayList<>();
    }

    /*
     * Visitor methods
     */

    @Override
    public Void visitProgram(GraafvisParser.ProgramContext ctx) {
        for (GraafvisParser.ClauseContext clause : ctx.clause()) {
            visitClause(clause);
        }
        return null;
    }

    @Override
    public Void visitClause(GraafvisParser.ClauseContext ctx) {
        VariableCounter antecedentCounter = new VariableCounter();
        VariableCounter consequenceCounter = new VariableCounter();
        // Visit antecedent
        if (ctx.antecedent() != null) {
            variableCounters.put(ctx.antecedent(), antecedentCounter);
            visitAntecedent(ctx.antecedent());
        }
        // Visit consequence
        variableCounters.put(ctx.consequence(), consequenceCounter);
        visitConsequence(ctx.consequence());
        // Compare counters
        Set<String> antecedentVariables = antecedentCounter.getVariables();
        Set<String> consequenceVariables = consequenceCounter.getVariables();
        for (String variable : antecedentVariables) {
            /* Add a warning if a variable occurs only once on the LHS and never on the RHS */
            if (antecedentCounter.get(variable) == 1 && !consequenceVariables.contains(variable)) {
                LocationInProgram location = variableLocations.get(variable);
                warnings.add(new SingletonVariableWarning(location.getLine(), location.getColumn(), variable));
            }
        }
        return null;
    }

    /** Pass on the counter */
    @Override
    public Void visitAntecedent(GraafvisParser.AntecedentContext ctx) {
        variableCounters.put(ctx.propositionalFormula(), variableCounters.get(ctx));
        return visit(ctx.propositionalFormula());
    }

    /** Pass on the counter */
    @Override
    public Void visitPfNot(GraafvisParser.PfNotContext ctx) {
        variableCounters.put(ctx.propositionalFormula(), variableCounters.get(ctx));
        return visit(ctx.propositionalFormula());
    }

    /** Pass on the counter */
    @Override
    public Void visitPfLit(GraafvisParser.PfLitContext ctx) {
        variableCounters.put(ctx.literal(), variableCounters.get(ctx));
        return visit(ctx.literal());
    }

    /** Pass on the counter */
    @Override
    public Void visitPfAnd(GraafvisParser.PfAndContext ctx) {
        for (GraafvisParser.PropositionalFormulaContext pf : ctx.propositionalFormula()) {
            variableCounters.put(pf, variableCounters.get(ctx));
            visit(pf);
        }
        return null;
    }

    /** Pass on the counter */
    @Override
    public Void visitPfOr(GraafvisParser.PfOrContext ctx) {
        for (GraafvisParser.PropositionalFormulaContext pf : ctx.propositionalFormula()) {
            variableCounters.put(pf, variableCounters.get(ctx));
            visit(pf);
        }
        return null;
    }

    /** Pass on the counter */
    @Override
    public Void visitPfNest(GraafvisParser.PfNestContext ctx) {
        variableCounters.put(ctx.propositionalFormula(), variableCounters.get(ctx));
        return visit(ctx.propositionalFormula());
    }

    /** Pass on the counter */
    @Override
    public Void visitConsequence(GraafvisParser.ConsequenceContext ctx) {
        for (GraafvisParser.LiteralContext literal : ctx.literal()) {
            variableCounters.put(literal, variableCounters.get(ctx));
            visit(literal);
        }
        return null;
    }

    /** Pass on the counter */
    @Override
    public Void visitAtomLiteral(GraafvisParser.AtomLiteralContext ctx) {
        variableCounters.put(ctx.atom(), variableCounters.get(ctx));
        return visit(ctx.atom());
    }

    /** Pass on the counter */
    @Override
    public Void visitMultiAtomLiteral(GraafvisParser.MultiAtomLiteralContext ctx) {
        variableCounters.put(ctx.multiAtom(), variableCounters.get(ctx));
        return visit(ctx.multiAtom());
    }

    /** Pass on the counter */
    @Override
    public Void visitAtom(GraafvisParser.AtomContext ctx) {
        if (ctx.termTuple() != null) {
            variableCounters.put(ctx.termTuple(), variableCounters.get(ctx));
            return visitTermTuple(ctx.termTuple());
        } else {
            return null;
        }
    }

    /** Pass on the counter */
    @Override
    public Void visitMultiAtom(GraafvisParser.MultiAtomContext ctx) {
        for (GraafvisParser.TermContext term : ctx.term()) {
            variableCounters.put(term, variableCounters.get(ctx));
            visit(term);
        }
        for (GraafvisParser.TermTupleContext tuple : ctx.termTuple()) {
            variableCounters.put(tuple, variableCounters.get(ctx));
            visitTermTuple(tuple);
        }
        return null;
    }

    /** Pass on the counter */
    @Override
    public Void visitTermTuple(GraafvisParser.TermTupleContext ctx) {
        for (GraafvisParser.TermContext term : ctx.term()) {
            variableCounters.put(term, variableCounters.get(ctx));
            visit(term);
        }
        return null;
    }

    /** Pass on the counter */
    @Override
    public Void visitTermVar(GraafvisParser.TermVarContext ctx) {
        variableCounters.put(ctx.variable(), variableCounters.get(ctx));
        return visitVariable(ctx.variable());
    }

    /** Pass on the counter */
    @Override
    public Void visitTermAtom(GraafvisParser.TermAtomContext ctx) {
        variableCounters.put(ctx.atom(), variableCounters.get(ctx));
        return visitAtom(ctx.atom());
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
        return null;
    }

    /** Pass on the counter */
    @Override
    public Void visitTermList(GraafvisParser.TermListContext ctx) {
        for (GraafvisParser.TermContext term : ctx.term()) {
            variableCounters.put(term, variableCounters.get(ctx));
            visit(term);
        }
        return null;
    }

    /** Store variable occurrence */
    @Override
    public Void visitVariable(GraafvisParser.VariableContext ctx) {
        VariableCounter counter = variableCounters.get(ctx);
        String variable = ctx.HID().getText();
        counter.add(variable);
        int line = ctx.HID().getSymbol().getLine();
        int column = ctx.HID().getSymbol().getCharPositionInLine();
        variableLocations.put(variable, new LocationInProgram(line, column));
        return null;
    }

/*
     * Getters
     */

    public ArrayList<Warning> getWarnings() {
        return warnings;
    }

    /*
     * Helper classes
     */

    private class VariableCounter extends HashMap<String, Integer> {

        void add(String var) {
            if (this.keySet().contains(var)) {
                this.put(var, this.get(var) + 1);
            } else {
                this.put(var, 1);
            }
        }

        int count(String var) {
            if (this.keySet().contains(var)) {
                return this.get(var);
            } else {
                return 0;
            }
        }

        Set<String> getVariables() {
            return keySet();
        }

    }

    private final class LocationInProgram {

        private final int line;
        private final int column;

        private LocationInProgram(int line, int column) {
            this.line = line;
            this.column = column;
        }

        public int getLine() {
            return line;
        }

        public int getColumn() {
            return column;
        }

    }


}
