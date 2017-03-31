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
        if (ctx.termTuple() == null) {
            // Constant, f.e. p
            return new Struct(ctx.predicate().getText());
        } else {
            // Regular predicate, f.e. p(X), p(X,Y,Z), p()
            return new Struct(ctx.predicate().getText(), aggregateVisit(ctx.termTuple().term())); // TODO geen agg in geval van enkele term?
        }
        // TODO mogelijk deel verplaatsen naar TermTuple
    }

    @Override public Term visitMultiAnd(MultiAndContext ctx) {
        // TODO werken met node{X,Y,X} ipv node{(X),(Y),(Z)}
        return and(aggregateVisitMultiTerms(ctx.predicate().getText(), ctx.multiTerm()));
    }

    @Override public Term visitMultiOr(MultiOrContext ctx) {
        return or(aggregateVisitMultiTerms(ctx.predicate().getText(), ctx.multiTerm()));
    }

//    @Override public Term visitMultiTerm(MultiTermContext ctx) {
//
//    }

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

    // --- Return one term for multiple visits ---
    public Term[] aggregateVisit(List<? extends ParseTree> ctxs) {
        // TODO check for null: [] en p()
        if (ctxs == null) {
            return new Term[0];
        }
        int n = ctxs.size();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            terms[i] = visit(ctxs.get(i));
        }
        return terms;
    }

    public Term[] aggregateVisitMultiTerms(String predicate, List<? extends ParseTree> ctxs) {
        // TODO check for null: [] en p()
        if (ctxs == null) {
            return new Term[0];
        }
        int n = ctxs.size();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            if (ctxs.get(i) instanceof TermTupleContext) {
                terms[i] = new Struct(predicate, aggregateVisit(((TermTupleContext) ctxs.get(i)).term()));
            } else {
                terms[i] = new Struct(predicate, visit(ctxs.get(i)));
            }
        }
        return terms;
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

}
