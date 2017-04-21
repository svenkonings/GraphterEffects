package graafvis.generator;

import alice.tuprolog.*;
import graafvis.grammar.GraafvisBaseVisitor;
import graafvis.grammar.GraafvisLexer;
import graafvis.grammar.GraafvisParser;
import graafvis.grammar.GraafvisParser.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import utils.StringUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static prolog.TuProlog.*;

public class RuleGenerator extends GraafvisBaseVisitor<Term> {
    private List<Term> result;

    public RuleGenerator(GraafvisParser.ProgramContext ctx) {
        result = new ArrayList<>();
        ctx.accept(this);
    }


    /***************
     --- Visitor ---
     ***************/

    @Override public Term visitImportVis(ImportVisContext ctx) {
        String filename = StringUtils.removeQuotation(ctx.STRING().getText());
        Lexer lexer;
        try {
            lexer = new GraafvisLexer(new ANTLRFileStream(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TokenStream tokens = new CommonTokenStream(lexer);
        GraafvisParser parser = new GraafvisParser(tokens);
        GraafvisParser.ProgramContext programContext = parser.program();
        programContext.accept(this);
        return null;
    }

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
            addClause(
                    struct(dslName, var("X"), var("Y")),
                    and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct(asName)))
            );
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
                aggregateVisitMultiTerms(((MultiCompoundConsequenceContext) cTerm).functor(), ((MultiCompoundConsequenceContext) cTerm).args);
            }
            addClause(visit(cTerm), antecedent);
        }
        return null;
    }

    // --- ANTECEDENT ---

    @Override public Term visitNotAntecedent(NotAntecedentContext ctx) {
        return not(visit(ctx.aTerm()));
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
        return visitList(ctx.aTerm(), ctx.aArgSeries());
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
        return visitList(ctx.cTerm(), ctx.cArgSeries());
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


    /**********************
     --- Helper methods ---
     **********************/

    // --- Generic visits ---

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

    public Term visitList(ParseTree term, ParseTree series) {
        List<? extends ParseTree> args;
        // Empty list
        if (series == null) {
            return list();
        }
        if (series instanceof AArgSeriesContext) {
            args = ((AArgSeriesContext) series).args;
        } else {
            args = ((CArgSeriesContext) series).args;
        }
        // List without tail
        if (term == null) {
            return list(visitAggregate(args));
        }
        // List with tail
        return listTail(list(visit(term)), visitAggregate(args));
    }

    // --- Generic visits: Return one term for multiple visits ---

    public Term[] visitAggregate(List<? extends ParseTree> ctxs) {
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

    // --- Generic visits: casts ---

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
        result.add(safeClause(head, body));
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
