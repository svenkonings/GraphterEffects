package compiler;

import za.co.wstoop.jatalog.Expr;
import za.co.wstoop.jatalog.Rule;

import java.util.*;


/**
 * Intermediate representation of a Graafvis script before it is delivered to a Solver.
 */
public class ConstraintSet {

    /**
     * Set of Strings indicating imports in the Graafvis String.
     */
    private final Set<String> imports;
    /**
     * List of Facts given through the Graafvis String.
     */
    private final List<Expr> facts;
    /**
     * List of Rules given through the Graafvis String.
     */
    private final List<Rule> rules;

    /**
     * Creates a new ConstraintSet.
     * @param imports Set of Strings indicating imports in the Graafvis String.
     * @param facts List of facts given through the Graafvis String.
     * @param rules List of rules given through the Graafvis String.
     */
    public ConstraintSet(Set<String> imports, List<Expr> facts, List<Rule> rules) {
        this.imports = imports;
        this.facts = facts;
        this.rules = rules;
    }

    /**
     * Returns the imports.
     * @return A Set of Strings indicating imports in the Graafvis String.
     */
    public Set<String> getImports() {
        return imports;
    }

    /**
     * Returns the facts.
     * @return A List of Facts given through the Graafvis String.
     */
    public List<Expr> getFacts() {
        return facts;
    }

    /**
     * Returns the rules.
     * @return A List of Rules given through the Graafvis String.
     */
    public List<Rule> getRules(){
        return rules;
    }


    /**
     * Merges a ConstraintSet into this ConstraintSet.
     * @param from ConstraintSet to merge with this one.
     */
    public void merge(ConstraintSet from) {
        imports.addAll(from.getImports());
        facts.addAll(from.getFacts());
        rules.addAll(from.getRules());
    }
}
