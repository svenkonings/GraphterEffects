import com.sun.org.omg.CORBA.ParDescriptionSeqHelper;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Expr;
import za.co.wstoop.jatalog.Jatalog;
import za.co.wstoop.jatalog.Rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static za.co.wstoop.jatalog.Expr.expr;

/**
 * Created by Lindsay on 27-Mar-17.
 */
public class RuleGenerator extends GraafvisBaseListener {
    public static final String WILD_CARD_PREFIX = "*";
    private ConstraintSet cs;
    private List<Expr> facts = new ArrayList<>();
    private List<Rule> rules = new ArrayList<>();
    private ParseTreeProperty<Expr> exprPTP = new ParseTreeProperty<>();
    private ParseTreeProperty<String> stringPTP = new ParseTreeProperty<>();
    private ParseTreeProperty<Integer> cntPTP = new ParseTreeProperty<>();


    /**********************
        --- Calling  ---
    **********************/

    public static void main(String[] args) throws DatalogException {
//        // Used for trying out JataLog
//        Jatalog jl = new Jatalog();
//        jl
//            .fact("node", "_a")
//            .fact("label", "_a", "\"Els\"")
//            .rule(expr("shape", "X", "square"), expr("node", "X"))
//            .rule(expr("shape", "X", "square"), expr("node", "X"), expr("label", "\"wolf\""))
//            .rule(expr("shape", "X", "square"), expr("node", "X"), expr("label", "\"wolf\""))
//            .rule(expr("shape", "X", "square"), expr("node", "X"), expr("label", "_"))
//            .rule(expr("shape", "X", "square"), expr("node", "X"), expr("label", "A**&"))
//        ;
//        System.out.println(jl);
//        System.out.println(jl.query(expr("shape", "X", "square")));
        generate(new RuleGenerator(), "p(X).");
    }

    public static ConstraintSet generate(RuleGenerator rg, String script) {
        Lexer lexer = new GraafvisLexer(new ANTLRInputStream(script));
        TokenStream tokens = new CommonTokenStream(lexer);
        GraafvisParser parser = new GraafvisParser(tokens);
        ParseTree tree = parser.program();
        System.out.println(rg.getCs().getFacts());
        System.out.println(rg.getCs().getRules());
        return rg.getCs();
    }


    /*************************
        --- Tree walker ---
    *************************/

    @Override public void exitProgram(GraafvisParser.ProgramContext ctx) {
        // TODO imports
        cs = new ConstraintSet(new HashSet<String>(), facts, rules);
    }

    // TODO Node label gen
    // TODO Edge label gen


    @Override public void enterClause(GraafvisParser.ClauseContext ctx) {
        // Consequence
        GraafvisParser.ConsequenceContext consequence = ctx.consequence();
        List<Expr> consequence_exprs = new ArrayList<>();
        for (GraafvisParser.LiteralContext lit : consequence.literal()) {
            consequence_exprs.add(getExpr(lit));
        }

        // Antecedent
        if (ctx.antecedent() != null) {
            // The clause is a rule
            GraafvisParser.Propositional_formulaContext formula = ctx.antecedent().propositional_formula();
            // TODO Wss moet ik de antecedent een List<Expr> door laten geven
        } else {
            // The clause is a fact
            for (Expr e : consequence_exprs) {
                addFact(e);
            }
        }
    }

    @Override public void exitClause(GraafvisParser.ClauseContext ctx) { }

    // TODO Out of scope: pfNot, pfBool, pfNest
    @Override public void exitPfLit(GraafvisParser.PfLitContext ctx) {
        setExpr(ctx, getExpr(ctx.literal()));
    }

    // --- ATOMS ---

    @Override public void exitAtom(GraafvisParser.AtomContext ctx) {
        List<String> terms = new ArrayList<>();
        for (GraafvisParser.TermContext trm : ctx.term()) {
            terms.add(getStr(trm)); // TODO terms goede String laten doorgeven
        }
        setExpr(ctx, expr(ctx.predicate().getText(), (String[]) terms.toArray()));
    }

    @Override public void exitMultiAtomLiteral(GraafvisParser.MultiAtomLiteralContext ctx) {
        // TODO
    }

    // --- TERMS ----

    @Override public void enterTermGround(GraafvisParser.TermGroundContext ctx) {
        setStr(ctx, ctx.getText());
    }

    @Override public void enterTermVar(GraafvisParser.TermVarContext ctx) {
        setStr(ctx, ctx.getText());
    }

    @Override public void enterWildcard(GraafvisParser.WildcardContext ctx) {
        // TODO Use the upCnt function to keep a counter per encapsulating clause, but
        // that is a bit too complex for now. So for now, a very very very hacky random function.
        setStr(ctx, WILD_CARD_PREFIX + Math.random());
    }

    @Override public void enterTuple(GraafvisParser.TupleContext ctx) {
        // TODO
        setStr(ctx, "<HI I AM TUPLE>");
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
        facts.add(e);
    }

    private void addRule(Rule r) {
        rules.add(r);
    }

    // --- Getters & setters ---

    public ConstraintSet getCs() {
        return cs;
    }

    public void setCs(ConstraintSet cs) {
        this.cs = cs;
    }
}
