package graafvis;

import alice.tuprolog.*;
import graafvis.grammar.GraafvisBaseVisitor;
import graafvis.grammar.GraafvisLexer;
import graafvis.grammar.GraafvisParser;
import graafvis.grammar.GraafvisParser.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.Arrays;
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


    /*****************
     --- Calling  ---
     *****************/

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

    /*******************
     --- Tree walker ---
     *******************/

    @Override public Term visitNodeLabelGen(NodeLabelGenContext ctx) {
        for (LabelContext label : ctx.label()) {
            String asName = label.STRING().getText();
            String dslName = label.ID() == null ? removeOuterChars(asName) : label.ID().getText();
            addClause(
                    struct(dslName, var("X")),
                    and(struct("node", var("X")), struct("label", var("X"), struct(asName)))
            );
        }
        return null;
    }

    @Override public Term visitEdgeLabelGen(EdgeLabelGenContext ctx) {
        for (LabelContext label : ctx.label()) {
            String asName = label.STRING().getText();
            String dslName = label.ID() == null ? removeOuterChars(asName) : label.ID().getText();
            addClause(
                    struct(dslName, var("X")),
                    and(struct("edge", var("X")), struct("label", var("X"), struct(asName)))
            );
            // TODO is the edge object itself still the third argument?
            addClause(
                    struct(dslName, var("X"), var("Y")),
                    and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct(asName)))
            );
            // TODO is the edge object itself still the third argument?
            addClause(
                    struct(dslName, var("X"), var("Y"), var("Z")),
                    and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct(asName)))
            );
        }
        return null;
    }

    @Override public Term visitClause(ClauseContext ctx) {
        Term antecedent = (ctx.antecedent == null) ? null : visit(ctx.antecedent);
        for (CTermContext cTerm : ctx.consequence.args) {
            if (cTerm instanceof MultiCompoundConsequenceContext) {
                // TODO werkt dit wel?
                aggregateVisitMultiTerms(((MultiCompoundConsequenceContext) cTerm).functor(), ((MultiCompoundConsequenceContext) cTerm).args);
            }
            addClause(visit(cTerm), antecedent);
        }
        return null;
    }

//    @Override public Term visitPfNest(PfNestContext ctx) {
//        return visit(ctx.propositionalFormula()); // TODO is dit ok? want dat an deze eig weg
//    }

    // --- ANTECEDENT ---

    @Override public Term visitNotAntecedent(NotAntecedentContext ctx) {
        return struct(TUP_NOT, visit(ctx.aTerm()));
    }

    @Override public Term visitCompoundAntecedent(CompoundAntecedentContext ctx) {
        return visitCompound(ctx.functor(), ctx.args);
    }

    @Override public Term visitMultiAndCompoundAntecedent(MultiAndCompoundAntecedentContext ctx) {
        return safeAnd(aggregateVisitMultiTerms(ctx.functor(), ctx.args));
    }

    @Override public Term visitMultiOrCompoundAntecedent(MultiOrCompoundAntecedentContext ctx) {
        return safeOr(aggregateVisitMultiTerms(ctx.functor(), ctx.args));
    }

    @Override public Term visitParAntecedent(ParAntecedentContext ctx) {
        return visit(ctx.aTermExpr());
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
        if (ctx.aArgSeries() == null) {
            System.out.println("ooh, aargseries null!");
            return new Struct();
        }
        return list(visitAggregate(ctx.aArgSeries().args));
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

    @Override public Term visitAArgSeries(AArgSeriesContext ctx) {
        return safeAnd(visitAggregate(ctx.args));
    }

    @Override public Term visitOrSeries(OrSeriesContext ctx) {
        return safeOr(visitAggregate(ctx.args));
    }

    @Override public Term visitAndExpressionAntecedent(AndExpressionAntecedentContext ctx) {
        return and(visit(ctx.aTermExpr(0)), visit(ctx.aTermExpr(1)));
    }

    @Override public Term visitOrExpressionAntecedent(OrExpressionAntecedentContext ctx) {
        return or(visit(ctx.aTermExpr(0)), visit(ctx.aTermExpr(1)));
    }

    @Override public Term visitParExpressionAntecedent(ParExpressionAntecedentContext ctx) {
        return visit(ctx.aTermExpr());
    }

    // --- CONSEQUENCE ---

    @Override public Term visitCompoundConsequence(CompoundConsequenceContext ctx) {
        return visitCompound(ctx.functor(), ctx.args);
    }

    @Override public Term visitMultiCompoundConsequence(MultiCompoundConsequenceContext ctx) {
        return safeAnd(aggregateVisitMultiTerms(ctx.functor(), ctx.args));
    }

    @Override public Term visitListConsequence(ListConsequenceContext ctx) {
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
        if (ctx.cArgSeries() == null) {
            System.out.println("ooh, cargseries null!");
            return new Struct();
        }
        return list(visitAggregate(ctx.cArgSeries().args));
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


    /**********************
     --- Helper methods ---
     **********************/

    // --- Return one term for multiple visits ---
    public Term[] visitAggregate(List<? extends ParseTree> ctxs) {
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

    // Not checked for terms == null, as grammar prohibits p{}
    public Term[] aggregateVisitMultiTerms(FunctorContext functor, List<? extends ParseTree> multiTerms) {
        int n = multiTerms.size();
        Term[] results = new Term[n];
        for (int i = 0; i < n; i++) {
            results[i] = safeStruct(getFunctor(functor), visitAggregate(getMultiArg(multiTerms.get(i))));
        }
        return results;
    }

    public Term visitCompound(FunctorContext functor, ParseTree termSeries) {
        if (termSeries == null) {
            // Constant, f.e. p
            return struct(getFunctor(functor));
        } else {
            // Regular predicate, f.e. p(X), p(X,Y,Z), p()
            List<? extends ParseTree> terms = getListFromTermSeries(termSeries);
            return struct(getFunctor(functor), visitAggregate(terms));
        }
    }

    // --- Casts to allow for generic methods ---

    public static List<? extends ParseTree> getListFromTermSeries(ParseTree termContainer) {
        if (termContainer instanceof AArgSeriesContext) {
            // Antecedent
            return ((AArgSeriesContext) termContainer).args;
        }
        // Consequence
        return ((CArgSeriesContext) termContainer).args;
    }

    public static List<? extends ParseTree> getMultiArg(ParseTree termContainer) {
        if (termContainer instanceof AMultiArgContext) {
            // MultiTerm in antecedent
            AMultiArgContext tc = (AMultiArgContext) termContainer;
            // Terms available
            // [Case 1] With parenthesis: contains multiple arguments for the to be generated compound term
            if (tc.aArgSeries() != null) {
                return tc.aArgSeries().args;
            }
            // [Case 2] Without parenthesis: contains one argument for the to be generated compound term
            if (tc.aTerm() != null) {
                return inList(tc.aTerm());
            }
            // [Case 3] No terms: empty parenthesis
            return null;
        }
        // MultiTerm in consequence
        CMultiArgContext tc = (CMultiArgContext) termContainer;
        // [Case 1] With parenthesis: contains multiple arguments for the to be generated compound term
        if (tc.cArgSeries() != null) {
            return tc.cArgSeries().args;
        }
        // [Case 2] Without parenthesis: contains one argument for the to be generated compound term
        if (tc.cTerm() != null) {
            return inList(tc.cTerm());
        }
        // [Case 3] No terms: empty parenthesis
        return null;
    }

    // --- String building ---

    private static String getFunctor(FunctorContext ctx) {
        String s = ctx.getText();
        return (ctx instanceof InfixFunctorContext) ? removeOuterChars(s) : s;
    }

    private static String removeOuterChars(String s) {
        return (s == null) ? s : s.substring(1, s.length() - 1);
    }

    // --- Update logic model ---

    private void addClause(Term head, Term body) {
//        Term term = (body == null) ? head : clause(head, body);
        result.add(safeClause(head, body)); // TODO check is safe is needed
    }

    // --- Getters & setters ---

    public List<Term> getResult() {
        return result;
    }

    public void setResult(List<Term> ts) {
        this.result = ts;
    }

    // --- Generic collection help ---

    public static <T> List<T> inList(T... ts) {
        return Arrays.asList(ts);
    }

}