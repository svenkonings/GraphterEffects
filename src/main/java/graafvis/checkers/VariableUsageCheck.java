//package graafvis.checkers;
//
//import graafvis.errors.UndefinedVariableError;
//import graafvis.errors.VisError;
//import graafvis.grammar.GraafvisBaseVisitor;
//import graafvis.grammar.GraafvisParser;
//import org.antlr.v4.runtime.tree.ParseTree;
//import org.antlr.v4.runtime.tree.ParseTreeProperty;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//
///**
// * Checks if no variables are introduced in the consequence of a clause.
// */
//class VariableUsageCheck extends GraafvisBaseVisitor<Void> {
//
//    /** List of errors obtained during the checking phase */
//    private final ArrayList<VisError> errors;
//
//    /** Stores for each clause the variables that are used in the antecedent of that clause. */
//    private final ParseTreeProperty<HashSet<String>> variables;
//
//    /** Create a new variable usage check */
//    VariableUsageCheck() {
//        variables = new ParseTreeProperty<>();
//        errors = new ArrayList<>();
//    }
//
//    /*
//     * Visitor methods
//     */
//
//    /** Visit all clauses */
//    @Override
//    public Void visitProgram(GraafvisParser.ProgramContext ctx) {
//        for (GraafvisParser.ClauseContext clause : ctx.clause()) {
//            visitClause(clause);
//        }
//        return null;
//    }
//
//    /** Visit antecedent and consequence and compare results */
//    @Override
//    public Void visitClause(GraafvisParser.ClauseContext ctx) {
//        /* Create two sets used for storing used variables in antecedent and consequence */
//        HashSet<String> antecedentSet = new HashSet<>(), consequenceSet = new HashSet<>();
//        if (ctx.antecedent() != null) {
//            variables.put(ctx.antecedent(), antecedentSet);
//        }
//        variables.put(ctx.consequence(), consequenceSet);
//        /* Visit antecedent and consequence */
//        visitChildren(ctx);
//        /* Compare sets */
//        for (String variable : consequenceSet) {
//            if (!antecedentSet.contains(variable)) {
//                int line = ctx.ARROW().getSymbol().getLine();
//                int column = ctx.ARROW().getSymbol().getCharPositionInLine();
//                errors.add(new UndefinedVariableError(line, column, variable));
//            }
//        }
//        return null;
//    }
//
//    /** Pass on the set to its children */
//    @Override
//    public Void visitAntecedent(GraafvisParser.AntecedentContext ctx) {
//        variables.put(ctx.propositional_formula(), variables.get(ctx));
//        visit(ctx.propositional_formula());
//        return null;
//    }
//
//    /** Pass on the set to its children */
//    @Override
//    public Void visitPfNot(GraafvisParser.PfNotContext ctx) {
//        variables.put(ctx.propositional_formula(), variables.get(ctx));
//        visit(ctx.propositional_formula());
//        return null;
//    }
//
//    /** Pass on the set to its children */
//    @Override
//    public Void visitPfLit(GraafvisParser.PfLitContext ctx) {
//        variables.put(ctx.literal(), variables.get(ctx));
//        visit(ctx.literal());
//        return null;
//    }
//
//    /** Pass on the set to its children */
//    @Override
//    public Void visitPfAnd(GraafvisParser.PfAndContext ctx) {
//        for (GraafvisParser.Propositional_formulaContext formula : ctx.propositional_formula()) {
//            variables.put(formula, variables.get(ctx));
//            visit(formula);
//        }
//        return null;
//    }
//
//    /** Pass on the set to its children */
//    @Override
//    public Void visitPfOr(GraafvisParser.PfOrContext ctx) {
//        for (GraafvisParser.Propositional_formulaContext formula : ctx.propositional_formula()) {
//            variables.put(formula, variables.get(ctx));
//            visit(formula);
//        }
//        return null;
//    }
//
//    /** Pass on the set to its children */
//    @Override
//    public Void visitPfNest(GraafvisParser.PfNestContext ctx) {
//        variables.put(ctx.propositional_formula(), variables.get(ctx));
//        visit(ctx.propositional_formula());
//        return null;
//    }
//
//    /** Pass on the set to its children */
//    @Override
//    public Void visitConsequence(GraafvisParser.ConsequenceContext ctx) {
//        for (GraafvisParser.LiteralContext literal : ctx.literal()) {
//            variables.put(literal, variables.get(ctx));
//            visit(literal);
//        }
//        return null;
//    }
//
//    /** Pass on the set to its children */
//    @Override
//    public Void visitAtomLiteral(GraafvisParser.AtomLiteralContext ctx) {
//        variables.put(ctx.atom(), variables.get(ctx));
//        visitAtom(ctx.atom());
//        return null;
//    }
//
//    /** Pass on the set to its children */
//    @Override
//    public Void visitMultiAtomLiteral(GraafvisParser.MultiAtomLiteralContext ctx) {
//        variables.put(ctx.multi_atom(), variables.get(ctx));
//        visitMulti_atom(ctx.multi_atom());
//        return null;
//    }
//
//    /** Pass on the set to its children */
//    @Override
//    public Void visitNumExprLiteral(GraafvisParser.NumExprLiteralContext ctx) {
//        for (GraafvisParser.Num_exprContext numExpr : ctx.num_expr()) {
//            variables.put(numExpr, variables.get(ctx));
//            visitNum_expr(numExpr);
//        }
//        return null;
//    }
//
//    /** Pass on the set to its children */
//    @Override
//    public Void visitAtom(GraafvisParser.AtomContext ctx) {
//        for (GraafvisParser.TermContext term : ctx.term()) {
//            variables.put(term, variables.get(ctx));
//            visit(term);
//        }
//        return null;
//    }
//
//    /** Pass on the set to its children */
//    @Override
//    public Void visitMulti_atom(GraafvisParser.Multi_atomContext ctx) {
//        for (GraafvisParser.TermContext term : ctx.term()) {
//            variables.put(term, variables.get(ctx));
//            visit(term);
//        }
//        return null;
//    }
//
//    /** Pass on the set to its children */
//    @Override
//    public Void visitNum_expr(GraafvisParser.Num_exprContext ctx) {
//        for (ParseTree child : ctx.children) {
//            if (child instanceof GraafvisParser.VariableContext) {
//                variables.put(child, variables.get(ctx));
//                visitVariable((GraafvisParser.VariableContext) child);
//            } else if (child instanceof GraafvisParser.Num_exprContext) {
//                variables.put(child, variables.get(ctx));
//                visitNum_expr((GraafvisParser.Num_exprContext) child);
//            }
//        }
//        return null;
//    }
//
//    /** Pass on the set to its children */
//    @Override
//    public Void visitTermVar(GraafvisParser.TermVarContext ctx) {
//        variables.put(ctx.variable(), variables.get(ctx));
//        visitVariable(ctx.variable());
//        return null;
//    }
//
//    /** Pass on the set to its children */
//    @Override
//    public Void visitTuple(GraafvisParser.TupleContext ctx) {
//        for (GraafvisParser.TermContext term : ctx.term()) {
//            variables.put(term, variables.get(ctx));
//            visit(term);
//        }
//        return null;
//    }
//
//    /** Ignore ground terms */
//    @Override
//    public Void visitTermGround(GraafvisParser.TermGroundContext ctx) {
//        return null;
//    }
//
//    /** Ignore wildcards */
//    @Override
//    public Void visitWildcard(GraafvisParser.WildcardContext ctx) {
//        return null;
//    }
//
//    /** Add the variable */
//    @Override
//    public Void visitVariable(GraafvisParser.VariableContext ctx) {
//        variables.get(ctx).add(ctx.HID().getText());
//        return null;
//    }
//
//    /*
//     * Getters
//     */
//
//    public ArrayList<VisError> getErrors() {
//        return errors;
//    }
//
//}
