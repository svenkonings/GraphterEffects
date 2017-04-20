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
        for (CTermContext cTerm : ctx.consequence.terms) {
            if (cTerm instanceof MultiCompoundConsequenceContext) {
                // TODO werkt dit wel?
                aggregateVisitMultiTerms(((MultiCompoundConsequenceContext) cTerm).functor(), ((MultiCompoundConsequenceContext) cTerm).terms);
            }
            addClause(visit(cTerm), antecedent);
        }
        return null;
    }

//    @Override public Term visitPfNest(PfNestContext ctx) {
//        return visit(ctx.propositionalFormula()); // TODO is dit ok? want dat an deze eig weg
//    }

    // --- ANTECEDENT ---

    @Override public Term visitOrAntecedent(OrAntecedentContext ctx) {
        return or(visit(ctx.aTerm(0)), visit(ctx.aTerm(1)));
    }

    @Override public Term visitNotAntecedent(NotAntecedentContext ctx) {
        return struct(TUP_NOT, visit(ctx.aTerm()));
    }

    @Override public Term visitCompoundAntecedent(CompoundAntecedentContext ctx) {
        return visitCompound(ctx.functor(), ctx.aTermSeries());
    }

    @Override public Term visitMultiAndCompoundAntecedent(MultiAndCompoundAntecedentContext ctx) {
        return and(aggregateVisitMultiTerms(ctx.functor(), ctx.terms));
    }

    @Override public Term visitMultiOrCompoundAntecedent(MultiOrCompoundAntecedentContext ctx) {
        return or(aggregateVisitMultiTerms(ctx.functor(), ctx.terms));
    }

    @Override public Term visitParAntecedent(ParAntecedentContext ctx) {
        return visit(ctx.aTermSeries());
    }

//    @Override public Term visitListAntecedent(ListAntecedentContext ctx) {
//        // HEAD & TAIL
//        /*
//        [a] = '.'(a,[])
//        [a,b] = '.'(a,'.'(b,[]))
//        [a,b|c] = '.'(a,'.'(b,c))
//        There can be only one | in a list, and no commas after it.
//         */
//        // Struct
//        // TODO null checks
//        // TODO meerdere heads
//        return struct(TUP_LIST, list(visitAggregate(ctx.aTermSeries().aTerm())), visit(ctx.aTerm()));
//    }

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

    @Override public Term visitATermSeries(ATermSeriesContext ctx) {
        return safeAnd(visitAggregate(ctx.terms));
    }

    // --- CONSEQUENCE ---

    @Override public Term visitCompoundConsequence(CompoundConsequenceContext ctx) {
        return visitCompound(ctx.functor(), ctx.cTermSeries());
    }

    @Override public Term visitMultiCompoundConsequence(MultiCompoundConsequenceContext ctx) {
        return and(aggregateVisitMultiTerms(ctx.functor(), ctx.terms));
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
        // TODO check for null: [] en p()
        // TODO terminology in comments
        // Casting to make this function usable for all compound contexts
//        List<? extends ParseTree> multiTerms;
//        FunctorContext functor;
//        if (ctx instanceof MultiAndCompoundAntecedentContext) {
//            multiTerms = ((MultiAndCompoundAntecedentContext) ctx).terms;
//            functor = ((MultiAndCompoundAntecedentContext) ctx).functor();
//        } else if (ctx instanceof MultiOrCompoundAntecedentContext) {
//            multiTerms = ((MultiOrCompoundAntecedentContext) ctx).terms;
//            functor = ((MultiOrCompoundAntecedentContext) ctx).functor();
//        } else {
//            multiTerms = ((MultiCompoundConsequenceContext) ctx).terms;
//            functor = ((MultiCompoundConsequenceContext) ctx).functor();
//        }
        int n = multiTerms.size();
        Term[] results = new Term[n];
        for (int i = 0; i < n; i++) {
            results[i] = safeStruct(getFunctor(functor), visitAggregate(getListFromMultiTerms(multiTerms.get(i))));
//            if (multiTerm instanceof ATermSeriesContext) {
//                // p{(X, Y), (A), (B, c, "hi")}
//                System.out.println("AVMT Termtuple: " + multiTerms.get(i).getText());
//                results[i] = struct(getFunctor(functor), visitAggregate(((ATermSeriesContext) multiTerm).terms));
//            } else {
//                // p{a, B, "hi", [a,b,c,d]}
//                System.out.println("AVMT Term: " + multiTerms.get(i).getText());
//                results[i] = struct(getFunctor(functor), visit(multiTerms.get(i)));
//            }
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
        if (termContainer instanceof ATermSeriesContext) {
            return ((ATermSeriesContext) termContainer).terms;
        }
        return ((CTermSeriesContext) termContainer).terms;
    }

    public static List<? extends ParseTree> getListFromMultiTerms(ParseTree termContainer) {
        System.out.println("TC " + termContainer.getText());
        if (termContainer instanceof AMultiTermContext) {
            // MultiTerm in antecedent
            AMultiTermContext tc = (AMultiTermContext) termContainer;
            ParseTree aTerm = tc.aTerm();
            // No terms: empty parenthesis
            if (aTerm == null) {
                return null;
            }
            // Terms available
            // [Case 1] With parenthesis: contains multiple arguments for the to be generated compound term
            if (aTerm instanceof ATermSeriesContext) {
                return ((ATermSeriesContext) aTerm).terms;
            }
            // [Case 2] Without parenthesis: contains one argument for the to be generated compound term
            return inList(tc.aTerm());
        }
        // MultiTerm in consequence
        CMultiTermContext tc = (CMultiTermContext) termContainer;
        // [Case 1] With parenthesis: contains multiple arguments for the to be generated compound term
        if (tc.cTermSeries() != null) {
            return tc.cTermSeries().terms;
        }
        // [Case 2] Without parenthesis: contains one argument for the to be generated compound term
        if (tc.cTerm() != null) {
            return inList(tc.cTerm());
        }
        // No terms: empty parenthesis
        return null;
//        CMultiTermContext tc = (CMultiTermContext) termContainer;
//        // Without parenthesis
//        if (tc.cTermTopLevel() != null) {
//            return inList(tc.cTermTopLevel());
//        }
//        // With parenthesis (probably contains multiple terms)
//        return tc.cTermSeries().terms;
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