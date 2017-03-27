package compiler;

import za.co.wstoop.jatalog.Expr;
import za.co.wstoop.jatalog.Rule;

import java.util.*;


public class Constraintset {

    private final Set<String> imports;
    private final List<Expr> facts;
    private final List<Rule> rules;

    public Constraintset(Set<String> imports, List<Expr> facts, List<Rule> rules) {
        this.imports = imports;
        this.facts = facts;
        this.rules = rules;
    }

    public Set<String> getImports() {
        return imports;
    }

    public List<Expr> getFacts() {
        return facts;
    }

    public List<Rule> getRules(){
        return rules;
    }
}
