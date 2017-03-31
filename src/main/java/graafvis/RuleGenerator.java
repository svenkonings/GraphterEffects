package graafvis;

import alice.tuprolog.*;
import alice.tuprolog.Number;
import graafvis.grammar.GraafvisBaseVisitor;
import graafvis.grammar.GraafvisLexer;
import graafvis.grammar.GraafvisParser;
import graafvis.grammar.GraafvisParser.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import org.junit.Test;
import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Expr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static compiler.prolog.TuProlog.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by Lindsay on 28-Mar-17.
 */
public class RuleGenerator extends GraafvisBaseVisitor<Term> {
    public static final String TUP_WILD_CARD = "_";
    public static final String TUP_AND = ",";
    public static final String TUP_OR = ";";
    public static final String TUP_HORN = ":-";
    private List<Term> result = new ArrayList<>();
    private ParseTreeProperty<Expr> exprPTP = new ParseTreeProperty<>();
    private ParseTreeProperty<List<Expr>> exprsPTP = new ParseTreeProperty<>();
    private ParseTreeProperty<String> stringPTP = new ParseTreeProperty<>();
    private ParseTreeProperty<Integer> cntPTP = new ParseTreeProperty<>();


    /**********************
        --- Calling  ---
     **********************/

    // --- Testing TU Prolog ---

    public static void tupTest() throws NoMoreSolutionException, InvalidTheoryException, DatalogException, NoSolutionException {
        Struct[] clauses = new Struct[]{
                new Struct("node", new Struct("a")),
                new Struct("node", new Struct("b")),
                new Struct("edge", new Struct("a"), new Struct("b")),
                new Struct("edge", new Struct("b"), new Struct("a")),
                new Struct("label", new Struct("a"), new Struct("\"wolf\""))
        };
        Struct prog = new Struct(clauses);
        query(prog, new Struct("node", new Var("X")));
        query(prog, new Struct("edge", new Var("X"), new Var("Y")));
        query(prog, new Struct("label", new Var("Y"), new Struct("\"wolf\"")));
        Struct q = new Struct(
                ",",
                new Struct("edge", new Var("X"), new Var("Y")),
                new Struct("label", new Var("X"), new Struct("\"wolf\""))
        );
        query(prog, q);
    }

    public static void query(Struct prog, Struct query) throws DatalogException, InvalidTheoryException, NoSolutionException, NoMoreSolutionException {
        System.out.println("\n> ?- " + query + "\n");
        Prolog engine = new Prolog();
        Theory t = new Theory(prog);
        engine.addTheory(t);
        SolveInfo info = engine.solve(query);
        while (info.isSuccess()) { // taken from the previous example
            System.out.println("Solution: " + info.getSolution() + "\nBindings: " + info);
            if (engine.hasOpenAlternatives()) {
                info = engine.solveNext();
            } else {
                break;
            }
        }
    }

    // --- Rule generation ---

    public static void main(String[] args) throws NoMoreSolutionException, InvalidTheoryException, DatalogException, NoSolutionException {
//        tupTest();
//        generate("node(a), label(a, \"wolf\").");
//        generate("node(X), label(X, \"wolf\") -> wolf(X).");
//        generate("node(X), label(X, \"wolf\") -> check(X), colour(X, red).");
//        generate("node(X), label(X, _) -> shape(X, square).");
//        generate("parent(X,Z), parent(Y,Z), edge(X, Y) -> family(X, Y, Z), child(Z), left(X, Y).");
//        // Error: generate("node(X), label(X, _) -> check(X), colour(X, blue)");
//        generate("shape((X,id1), square).");
    }

    public static List<Term> generate(String script) {
        System.out.println("\nParsing: " + script);
        RuleGenerator rg = new RuleGenerator();
        Lexer lexer = new GraafvisLexer(new ANTLRInputStream(script));
        TokenStream tokens = new CommonTokenStream(lexer);
        GraafvisParser parser = new GraafvisParser(tokens);
        ParseTree tree = parser.program();
        tree.accept(rg);
        System.out.println(rg.getResult());
//        System.out.println("\tFacts: " + rg.getCs().getFacts());
//        System.out.println("\tRules: " + rg.getCs().getRules());
        return rg.getResult();
    }

    // --- Testing ---

    @Test
    public void test() {
        Struct list = list(var("X"), var("Y"));
        Struct pX = struct("p", new Var("X"));
        Struct qX = struct("q", new Var("X"));


        List<Term> test1 = inList(struct("p", var("X")));
        assertEquals(test1, generate("p(X)."));

        List<Term> test2 = inList(struct("edge", var("X"), var("Y")));
        assertEquals(test2, generate("edge(X,Y)."));

        List<Term> test3 = inList(struct("p", var("X"), var("Y")));
        assertEquals(test3, generate("p(X,Y)."));

        List<Term> test4 = inList(struct("p", list(var("X"), var("Y"))));
        assertEquals(test4, generate("p([X,Y])."));

        List<Term> test5 = inList(struct("shape", list(var("X"), var("Y")), struct("square")));
        assertEquals(test5, generate("shape([X,Y], square)."));

        List<Term> test6 = inList(struct("shape", list(var("X"), list(number("3"), struct("\"wolf\""))), struct("square")));
        assertEquals(test6, generate("shape([X,[3, \"wolf\"]], square)."));


//        List<Term> test6 = Arrays.asList(new Struct());
//        List<Term> test6 = Arrays.asList(new Struct(TUP_OR, pX, ));

        // TODO Find way to test wildcards
    }

    /*************************
        --- Tree walker ---
     *************************/

//    @Override public Term visitProgram(ProgramContext ctx) {
//        // TODO imports, labels
//        return null;
//    }

    // TODO Node label gen
    // TODO Edge label gen

    @Override public Term visitClause(ClauseContext ctx) {
        if (ctx.antecedent() == null) {
            // Fact
            for (LiteralContext conseqLit : ctx.consequence().literal()) {
                addClause(visit(conseqLit));
            }
        } else {
            Term antecedent = visit(ctx.antecedent());
            // Rule
            for (LiteralContext conseqLit : ctx.consequence().literal()) {
                addClause(new Struct(TUP_HORN, visit(conseqLit), antecedent));
            }
        }
        return null;
    }

    // TODO Out of scope: pfNot, pfOr, pfNest
    @Override public Term visitPfLit(PfLitContext ctx) {
        return visitChildren(ctx); // TODO is dit ok? want dat an deze eig weg
    }

    @Override public Term visitPfAnd(PfAndContext ctx) {
        return new Struct(TUP_AND, visit(ctx.propositionalFormula(0)), visit(ctx.propositionalFormula(1)));
    }

    @Override public Term visitPfOr(PfOrContext ctx) {
        return new Struct(TUP_OR, visit(ctx.propositionalFormula(0)), visit(ctx.propositionalFormula(1)));
    }

    // TODO OR

    @Override public Term visitAtomLiteral(AtomLiteralContext ctx) {
        return visitChildren(ctx); // TODO Mogelijk visit(ctx.atom()) ofzo
    }

    // --- ATOMS ---

    @Override public Term visitAtom(AtomContext ctx) {
        return new Struct(ctx.predicate().getText(), aggregateVisit(ctx.termTuple().term())); // TODO geen agg in geval van enkele term?
        // TODO mogelijk deel verplaatsen naar TermTuple
    }

    @Override public Term visitMultiAtomLiteral(MultiAtomLiteralContext ctx) {
        // TODO
        return visitChildren(ctx);
    }

    // --- TERMS ----

    @Override public Term visitTermVar(TermVarContext ctx) {
        return new Var(ctx.getText());
    }

    @Override public Term visitTermWildcard(TermWildcardContext ctx) {
        return new Var(TUP_WILD_CARD);
    }

    @Override public Term visitTermString(TermStringContext ctx) {
        return new Struct(ctx.getText());
    }

    @Override public Term visitTermNumber(TermNumberContext ctx) {
        return Number.createNumber(ctx.getText());
    }

    @Override public Term visitTermID(TermIDContext ctx) {
        return new Struct(ctx.getText());
    }

    @Override public Term visitTermList(TermListContext ctx) {
        return new Struct(aggregateVisit(ctx.term()));
    }

    // ---------




    /*****************************
        --- Helper methods ---
     *****************************/

    // --- Change AbstractParseTreeVisitor behaviour ---
//    // TODO check for all aggregate cases
//    @Override public Term aggregateResult(Term aggregate, Term nextResult) {
//        return new Struct(new Term[]{aggregate, nextResult});
//    }

    // --- Return one term for multiple visits ---
    public Term[] aggregateVisit(List<? extends ParseTree> ctxs) {
        int n = ctxs.size();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            terms[i] = visit(ctxs.get(i));
        }
        return terms;
    }

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

    private void addClause(Term t) {
        result.add(t);
    }

    // --- Getters & setters ---

    public List<Term> getResult() {
        return result;
    }

    public void setResult(List<Term> ts) {
        this.result = ts;
    }

    // --- Little help for tests ---
    public static List<Term> inList(Term... ts) {
        return Arrays.asList(ts);
    }

}
