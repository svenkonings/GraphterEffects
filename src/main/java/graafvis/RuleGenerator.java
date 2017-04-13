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

import java.util.ArrayList;
import java.util.List;

import static compiler.prolog.TuProlog.*;

/**
 * Created by Lindsay on 28-Mar-17.
 */
public class RuleGenerator extends GraafvisBaseVisitor<Term> {
    public static final String TUP_AND = ",";
    public static final String TUP_OR = ";";
    public static final String TUP_NOT = "not";
    public static final String TUP_LIST = ".";
    private List<Term> result = new ArrayList<>();


    /**********************
     --- Calling  ---
     **********************/

    // --- Testing TU Prolog ---

//    public static void tupTest() throws NoMoreSolutionException, InvalidTheoryException, NoSolutionException {
//        Struct[] clauses = new Struct[]{
//                new Struct("node", new Struct("a")),
//                new Struct("node", new Struct("b")),
//                new Struct("edge", new Struct("a"), new Struct("b")),
//                new Struct("edge", new Struct("b"), new Struct("a")),
//                new Struct("label", new Struct("a"), new Struct("\"wolf\""))
//        };
//        Struct prog = new Struct(clauses);
//        query(prog, new Struct("node", new Var("X")));
//        query(prog, new Struct("edge", new Var("X"), new Var("Y")));
//        query(prog, new Struct("label", new Var("Y"), new Struct("\"wolf\"")));
//        Struct q = new Struct(
//                ",",
//                new Struct("edge", new Var("X"), new Var("Y")),
//                new Struct("label", new Var("X"), new Struct("\"wolf\""))
//        );
//        query(prog, q);
//    }

    // TODO Hier een test van maken, of niet?
    public static void main(String[] args) throws NoMoreSolutionException, NoSolutionException, InvalidTheoryException {
        query("p(aap).", struct("p", var("X")));
        query("p(X), q(X) -> r(X). p(hond), q(hond), p(kat), q(konijn), r(muis).", struct("r", var("X")));
    }

    public static void query(String script, Struct query) throws InvalidTheoryException, NoSolutionException, NoMoreSolutionException {
        List<Term> clauses = generate(script);
        Struct prog = list(clauses.toArray(new Term[clauses.size()]));
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

    public static List<Term> generate(String script) {
//        System.out.println("\nParsing: " + script);
        RuleGenerator rg = new RuleGenerator();
        Lexer lexer = new GraafvisLexer(new ANTLRInputStream(script));
        TokenStream tokens = new CommonTokenStream(lexer);
        GraafvisParser parser = new GraafvisParser(tokens);
        ParseTree tree = parser.program();
        tree.accept(rg);
//        System.out.println(rg.getResult());
        return rg.getResult();
    }

    /*************************
     --- Tree walker ---
     *************************/

    @Override public Term visitNodeLabelGen(NodeLabelGenContext ctx) {
        for (LabelContext label : ctx.label()) {
            String asName = label.STRING().getText();
            String dslName = label.ID() == null ? removeOuterChars(asName) : label.ID().getText();
            addClause(clause(
                    struct(dslName, var("X")),
                    and(struct("node", var("X")), struct("label", var("X"), struct(asName)))
            ));
        }
        return null;
    }

    @Override public Term visitEdgeLabelGen(EdgeLabelGenContext ctx) {
        for (LabelContext label : ctx.label()) {
            String asName = label.STRING().getText();
            String dslName = label.ID() == null ? removeOuterChars(asName) : label.ID().getText();
            addClause(clause(
                    struct(dslName, var("X")),
                    and(struct("edge", var("X")), struct("label", var("X"), struct(asName)))
            ));
            // TODO is the edge object itself still the third argument?
            addClause(clause(
                    struct(dslName, var("X"), var("Y")),
                    and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct(asName)))
            ));
            // TODO is the edge object itself still the third argument?
            addClause(clause(
                    struct(dslName, var("X"), var("Y"), var("Z")),
                    and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct(asName)))
            ));
        }
        return null;
    }

//
//    @Override public Term visitClause(ClauseContext ctx) {
//        if (ctx.consequence instanceof AndConsequenceContext)
//        if (ctx.antecedent == null) {
//            // Fact
//            for (LiteralContext conseqLit : ctx.consequence().literal()) {
//                addClause(visit(conseqLit));
//            }
//        } else {
//            Term antecedent = visit(ctx.antecedent());
//            // Rule
//            for (LiteralContext conseqLit : ctx.consequence().literal()) {
//                addClause(clause(visit(conseqLit), antecedent));
//            }
//        }
//        return null;
//    }

//    @Override public Term visitPfNest(PfNestContext ctx) {
//        return visit(ctx.propositionalFormula()); // TODO is dit ok? want dat an deze eig weg
//    }

    // --- ANTECEDENT ---

    @Override public Term visitNotAntecedent(NotAntecedentContext ctx) {
        return struct(TUP_NOT, visit(ctx.aTerm()));
    }

    @Override public Term visitAndAntecedent(AndAntecedentContext ctx) {
        return new Struct(TUP_AND, visit(ctx.aTerm(0)), visit(ctx.aTerm(1)));
    }

    @Override public Term visitOrAntecedent(OrAntecedentContext ctx) {
        return new Struct(TUP_OR, visit(ctx.aTerm(0)), visit(ctx.aTerm(1)));
    }

    @Override public Term visitAtomAntecedent(AtomAntecedentContext ctx) {
        if (ctx.aTermSeries() == null) {
            // Constant, f.e. p
            return new Struct(ctx.predicate().getText());
        } else {
            // Regular predicate, f.e. p(X), p(X,Y,Z), p()
            return new Struct(ctx.predicate().getText(), aggregateVisit(ctx.aTermSeries().terms)); // TODO geen agg in geval van enkele term?
        }
        // TODO mogelijk deel verplaatsen naar TermTuple
    }

    @Override public Term visitMultiAndAtomAntecedent(MultiAndAtomAntecedentContext ctx) {
        return and(aggregateVisitMultiTerms(ctx.predicate().getText(), ctx.terms));
    }

    @Override public Term visitMultiOrAtomAntecedent(MultiOrAtomAntecedentContext ctx) {
        return or(aggregateVisitMultiTerms(ctx.predicate().getText(), ctx.terms));
    }

    @Override public Term visitListAntecedent(ListAntecedentContext ctx) {
        // HEAD & TAIL
        /*
        [a] = '.'(a,[])
        [a,b] = '.'(a,'.'(b,[]))
        [a,b|c] = '.'(a,'.'(b,c))
        There can be only one | in a list, and no commas after it.
         */
        // Struct
        // TODO null checks
        // TODO meerdere heads
        return struct(TUP_LIST, list(aggregateVisit(ctx.aTermSeries().aTerm())), visit(ctx.aTerm()));
    }

    @Override public Term visitVariableAntecedent(VariableAntecedentContext ctx) {
        return var(ctx.getText());
    }

    @Override public Term visitWildcardAntecedent(WildcardAntecedentContext ctx) {
        return var(ctx.getText());
    }

    @Override public Term visitStringAntecedent(StringAntecedentContext ctx) {
        return struct(ctx.getText());
    }

    @Override public Term visitNumberAntecedent(NumberAntecedentContext ctx) {
        return number(ctx.getText());
    }

    // --- CONSEQUENCE ---

    @Override public Term visitAndConsequence(AndConsequenceContext ctx) {
        return struct(TUP_AND, visit(ctx.cTerm(0)), visit(ctx.cTerm(1)));
    }

    @Override public Term visitVariableConsequence(VariableConsequenceContext ctx) {
        return var(ctx.getText());
    }

    @Override public Term visitStringConsequence(StringConsequenceContext ctx) {
        return struct(ctx.getText());
    }

    @Override public Term visitNumberConsequence(NumberConsequenceContext ctx) {
        return number(ctx.getText());
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
            // No subtrees available
            return new Term[0];
        }
        int n = ctxs.size();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            if (ctxs.get(i).getChild(0) instanceof ATermSeriesContext) {
                // TermTuple
                System.out.println("AVMT Termtuple: " + ctxs.get(i).getText());
                terms[i] = new Struct(predicate, aggregateVisit(((ATermSeriesContext) ctxs.get(i).getChild(0)).terms));
            } else {
                // Term
                System.out.println("AVMT Term: " + ctxs.get(i).getText());
                terms[i] = new Struct(predicate, visit(ctxs.get(i)));
            }
        }
        return terms;
    }

    private String getFunctor(PredicateContext ctx) {
        String s = ctx.getText();
        if (ctx instanceof InfixPredicateContext) {
            s = removeOuterChars(s);
        }
        return s;
    }

    private String removeOuterChars(String s) {
        return s == null ? s : s.substring(1, s.length() - 1);
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