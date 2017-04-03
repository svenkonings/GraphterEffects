package graafvis.deprecated;

import graafvis.ConstraintSet;
import graafvis.grammar.GraafvisBaseVisitor;
import graafvis.grammar.GraafvisLexer;
import graafvis.grammar.GraafvisParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Expr;
import za.co.wstoop.jatalog.Rule;

import java.util.ArrayList;
import java.util.List;

import static za.co.wstoop.jatalog.Expr.expr;

/**
 * Created by Lindsay on 28-Mar-17.
 */
public class RuleGeneratorDeprecated2<T> extends GraafvisBaseVisitor<T> {
    public static final String WILD_CARD_PREFIX = "X*"; // Cap letter to make it a var, * to make it illegal
    private ConstraintSet cs = new ConstraintSet();
    private ParseTreeProperty<Expr> exprPTP = new ParseTreeProperty<>();
    private ParseTreeProperty<List<Expr>> exprsPTP = new ParseTreeProperty<>();
    private ParseTreeProperty<String> stringPTP = new ParseTreeProperty<>();
    private ParseTreeProperty<Integer> cntPTP = new ParseTreeProperty<>();


    /**********************
     --- Calling  ---
     **********************/

    public static void main(String[] args) throws DatalogException {
        generate("node(a), label(a, \"wolf\").");
        generate("node(X), label(X, \"wolf\") -> wolf(X).");
        generate("node(X), label(X, \"wolf\") -> check(X), colour(X, red).");
        generate("node(X), label(X, _) -> shape(X, square).");
        generate("parent(X,Z), parent(Y,Z), edge(X, Y) -> family(X, Y, Z), child(Z), left(X, Y).");
        // Error: generate("node(X), label(X, _) -> check(X), colour(X, blue)");
        generate("shape((X,id1), square).");
    }

    public static ConstraintSet generate(String script) {
        System.out.println("\n" + script);
        RuleGeneratorDeprecated2 rg = new RuleGeneratorDeprecated2();
        Lexer lexer = new GraafvisLexer(new ANTLRInputStream(script));
        TokenStream tokens = new CommonTokenStream(lexer);
        GraafvisParser parser = new GraafvisParser(tokens);
        ParseTree tree = parser.program();
        tree.accept(rg);
//        new ParseTreeWalker().walk(rg, tree);
//        System.out.println("\tTree: " + tree);
        System.out.println("\tFacts: " + rg.getCs().getFacts());
        System.out.println("\tRules: " + rg.getCs().getRules());
        return rg.getCs();
    }


    /*************************
     --- Tree walker ---
     *************************/

    @Override public T visitProgram(GraafvisParser.ProgramContext ctx) {
        // TODO imports
        T t = visitChildren(ctx);
        return t;
    }

    // TODO Node label gen
    // TODO Edge label gen

    @Override public T visitClause(GraafvisParser.ClauseContext ctx) {
        T t = visitChildren(ctx);
        // Consequence
        GraafvisParser.ConsequenceContext consequence = ctx.consequence();
        List<Expr> consequence_exprs = new ArrayList<>();
        for (GraafvisParser.LiteralContext lit : consequence.literal()) {
            consequence_exprs.add(getExpr(lit));
        }

        // Antecedent
        GraafvisParser.AntecedentContext ante = ctx.antecedent();
        if (ante != null) {
            // The clause is a rule
            List<Expr> ante_exprs = getExprs(ctx.antecedent().propositional_formula());
            for (Expr e : consequence_exprs) {
                addRule(new Rule(e, ante_exprs));
            }
        } else {
            // The clause is a fact
            for (Expr e : consequence_exprs) {
                addFact(e);
            }
        }
        return t;
    }

    // TODO Out of scope: pfNot, pfOr, pfNest
    @Override public T visitPfLit(GraafvisParser.PfLitContext ctx) {
        T t = visitChildren(ctx);
        List<Expr> exprs = new ArrayList<>();
        exprs.add(getExpr(ctx.literal()));
        setExprs(ctx, exprs);
        return t;
    }

    @Override public T visitPfAnd(GraafvisParser.PfAndContext ctx) {
        T t = visitChildren(ctx);
        List<Expr> exprs = new ArrayList<>();
        // Ignoring ORs
        for (GraafvisParser.Propositional_formulaContext pf : ctx.propositional_formula()) {
            exprs.addAll(getExprs(pf));
        }
        setExprs(ctx, exprs);
        return t;
    }

    @Override public T visitAtomLiteral(GraafvisParser.AtomLiteralContext ctx) {
        T t = visitChildren(ctx);
        setExpr(ctx, getExpr(ctx.atom()));
        return t;
    }

    // --- ATOMS ---

    @Override public T visitAtom(GraafvisParser.AtomContext ctx) {
        T t = visitChildren(ctx);
        int n = ctx.term().size();
        String[] terms = new String[n];
        for (int i = 0; i < n; i++) {
            terms[i] = getStr(ctx.term(i));
        }
        setExpr(ctx, expr(ctx.predicate().getText(), terms));
        return t;
    }

    @Override public T visitMultiAtomLiteral(GraafvisParser.MultiAtomLiteralContext ctx) {
        // TODO
        return visitChildren(ctx);
    }

    // --- TERMS ----

    @Override public T visitTermGround(GraafvisParser.TermGroundContext ctx) {
        setStr(ctx, ctx.getText());
        return visitChildren(ctx);
    }

    @Override public T visitTermVar(GraafvisParser.TermVarContext ctx) {
        setStr(ctx, ctx.getText());
        return visitChildren(ctx);
    }

    @Override public T visitWildcard(GraafvisParser.WildcardContext ctx) {
        // TODO Use the upCnt function to keep a counter per encapsulating clause, but
        // that is a bit too complex for now. So for now, a very very very hacky random function.
        setStr(ctx, WILD_CARD_PREFIX + Math.random());
        return visitChildren(ctx);
    }

    @Override public T visitTuple(GraafvisParser.TupleContext ctx) {
        // TODO
        setStr(ctx, "<HI I AM TUPLE>");
        return visitChildren(ctx);
    }

    // ---------




    /*****************************
     --- Helper methods ---
     *****************************/

    // --- ParseTreeProperties ---

    private Expr getExpr(ParseTree node) {
        return this.exprPTP.get(node);
    }

    private void setExpr(ParseTree node, Expr expr) {
        this.exprPTP.put(node, expr);
    }

    private List<Expr> getExprs(ParseTree node) {
        return this.exprsPTP.get(node);
    }

    private void setExprs(ParseTree node, List<Expr> exprs) {
        this.exprsPTP.put(node, exprs);
    }

    private String getStr(ParseTree node) {
        return this.stringPTP.get(node);
    }

    private void setStr(ParseTree node, String s) {
        this.stringPTP.put(node, s);
    }

    // --- Wildcard counter ---
    // A counter for the generated variables for wildcard support.
    // Separate IDs per wildcard representing variable is needed to distinguish multiple wildcards in one clause.
    private Integer upCnt(ParseTree node) {
        Integer cnt = this.cntPTP.get(node) == null ? 0 : this.cntPTP.get(node);
        this.cntPTP.put(node, cnt + 1);
        return cnt;
    }

    // --- Update logic model ---

    private void addFact(Expr e) {
        cs.addFact(e);
    }

    private void addRule(Rule r) {
        cs.addRule(r);
    }

    // --- Getters & setters ---

    public ConstraintSet getCs() {
        return cs;
    }

    public void setCs(ConstraintSet cs) {
        this.cs = cs;
    }

}
