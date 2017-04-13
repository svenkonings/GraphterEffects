package graafvis.checkers;
import graafvis.grammar.GraafvisBaseVisitor;
import graafvis.grammar.GraafvisParser;
import graafvis.warnings.SingletonVariableWarning;
import graafvis.warnings.Warning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Generates warnings for unnecessary variable usage
 */
public class SingletonVariablesCheck extends GraafvisBaseVisitor<Void> {

    /** Counter to keep track of variable usage in the antecedent of a clause */
    private final VariableCounter antecedentCounter;
    /** Counter to keep track of variable usage in the consequence of a clause */
    private final VariableCounter consequenceCounter;

    /** A list of warnings */
    private final ArrayList<Warning> warnings;

    /** Create a new singular variables check */
    SingletonVariablesCheck() {
        warnings = new ArrayList<>();
        antecedentCounter = new VariableCounter();
        consequenceCounter = new VariableCounter();
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

    /** Compare the variable occurrences in antecedent and consequence */
    @Override
    public Void visitClause(GraafvisParser.ClauseContext ctx) {
        /* Reset counters */
        antecedentCounter.reset();
        consequenceCounter.reset();
        /* Visit children */
        visitChildren(ctx);
        /* Compare antecedent and consequence */
        Set<String> antecedentVariables = antecedentCounter.getVariables();
        Set<String> consequenceVariables = consequenceCounter.getVariables();
        for (String variable : antecedentVariables) {
            /* Add a warning if a variable occurs only once in the antecedent and never in the consequence */
            if (antecedentCounter.count(variable) == 1 && !consequenceVariables.contains(variable)) {
                LocationInProgram location = antecedentCounter.getLatestOccurrence(variable);
                warnings.add(new SingletonVariableWarning(location.getLine(), location.getColumn(), variable));
            }
        }
        return null;
    }

    /** Count variable occurrence */
    @Override
    public Void visitVariableAntecedent(GraafvisParser.VariableAntecedentContext ctx) {
        int line = ctx.HID().getSymbol().getLine();
        int column = ctx.HID().getSymbol().getCharPositionInLine();
        antecedentCounter.add(ctx.HID().getText(), line, column);
        return null;
    }

    /** Count variable occurrence */
    @Override
    public Void visitVariableConsequence(GraafvisParser.VariableConsequenceContext ctx) {
        int line = ctx.HID().getSymbol().getLine();
        int column = ctx.HID().getSymbol().getCharPositionInLine();
        consequenceCounter.add(ctx.HID().getText(), line, column);
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

    /** Keeps count of how many times a variable occurred */
    private class VariableCounter extends HashMap<String, Integer> {

        /** Stores the latest occurrence of a variable while visiting context nodes */
        private final HashMap<String, LocationInProgram> latestOccurrence;

        /** Create a new variable counter */
        private VariableCounter() {
            latestOccurrence = new HashMap<>();
        }

        /** Add a variable occurrence */
        void add(String var, int line, int column) {
            if (this.keySet().contains(var)) {
                this.put(var, this.get(var) + 1);
            } else {
                this.put(var, 1);
            }
            this.latestOccurrence.put(var, new LocationInProgram(line, column));
        }

        /** Get the number of times that a variable occurred */
        int count(String var) {
            if (this.keySet().contains(var)) {
                return this.get(var);
            } else {
                return 0;
            }
        }

        /** Get the latest occurrence of a variable */
        LocationInProgram getLatestOccurrence(String var) {
            return latestOccurrence.get(var);
        }

        /** Reset the counter for usage in a new clause */
        void reset() {
            latestOccurrence.clear();
            this.clear();
        }

        Set<String> getVariables() {
            return keySet();
        }

    }

    /** A location in the program */
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