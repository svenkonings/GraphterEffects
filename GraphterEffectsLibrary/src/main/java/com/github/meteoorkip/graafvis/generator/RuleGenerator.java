package com.github.meteoorkip.graafvis.generator;


import com.github.meteoorkip.graafvis.grammar.GraafvisBaseVisitor;
import com.github.meteoorkip.graafvis.grammar.GraafvisLexer;
import com.github.meteoorkip.graafvis.grammar.GraafvisParser;
import com.github.meteoorkip.graafvis.grammar.GraafvisParser.*;
import com.github.meteoorkip.utils.StringUtils;
import it.unibo.tuprolog.core.Clause;
import it.unibo.tuprolog.core.Struct;
import it.unibo.tuprolog.core.Term;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.github.meteoorkip.prolog.TuProlog.*;

public class RuleGenerator extends GraafvisBaseVisitor<Term> {
    private List<Clause> result;

    public RuleGenerator(ProgramContext ctx) {
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
        ProgramContext programContext = parser.program();
        programContext.accept(this);
        return null;
    }

    /**
     * Generates shorthand predicates to address nodes with specific labels, and immediately adds the clauses enabling
     * these predicates. If no renaming is specified, the original label name from the input graph will be used as
     * functor.
     *
     * An example:
     *      Line in Graafvis script:        node labels: "Wolf" as wolf.
     *      Generates the hidden rule:      node(X), label(X, "Wolf") -> wolf(X).
     *
     * @param ctx   the parse tree node
     * @return      null
     */
    @Override public Term visitNodeLabelGen(NodeLabelGenContext ctx) {
        // Example: wolf
        for (LabelContext label : ctx.label()) {
            String inputName = label.STRING().getText();
            String scriptName = label.ID() == null ? removeOuterChars(inputName) : label.ID().getText();
            addClause(
                    struct(scriptName, var("X")),
                    and(struct("node", var("X")), struct("label", var("X"), struct(inputName)))
            );
        }
        return null;
    }

    /**
     * Generates shorthand predicates to address edges with specific labels, and immediately adds these clauses enabling
     * these predicates. If no renaming is specified, the original label name from the input graph will be used as
     * functor.
     *
     * An example:
     *      Line in Graafvis script:        edge labels: "Is friends with" as friends.
     *      Generates the hidden rules:     edge(X), label(X, "Is friends with")        -> friends(X).
     *                                      edge(X, Y, Z), label(Z, "Is friends with")  -> friends(X, Y).
     *                                      edge(X, Y, Z), label(Z, "Is friends with")  -> friends(X, Y, Z).
     *
     * @param ctx   the parse tree node
     * @return      null
     */
    @Override public Term visitEdgeLabelGen(EdgeLabelGenContext ctx) {
        // Example: e
        for (LabelContext label : ctx.label()) {
            String inputName = label.STRING().getText();
            String scriptName = label.ID() == null ? removeOuterChars(inputName) : label.ID().getText();
            //
            addClause(
                    struct(scriptName, var("X")),
                    and(struct("edge", var("X")), struct("label", var("X"), struct(inputName)))
            );
            addClause(
                    struct(scriptName, var("X"), var("Y")),
                    and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct(inputName)))
            );
            addClause(
                    struct(scriptName, var("X"), var("Y"), var("Z")),
                    and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct(inputName)))
            );
        }
        return null;
    }

    @Override public Term visitClause(ClauseContext ctx) {
        if (ctx.antecedent == null) { //is a fact, not a clause
            for (CTermContext cTerm : ctx.consequence.args) {
                if (cTerm instanceof MultiCompoundConsequenceContext) {
                    visitMultiArgs(((MultiCompoundConsequenceContext) cTerm).functor(), ((MultiCompoundConsequenceContext) cTerm).args);
                }
                addFact((Struct) visit(cTerm));
            }
            return null;
        } else {
            Term antecedent = visit(ctx.antecedent);
            Objects.requireNonNull(antecedent);
            for (CTermContext cTerm : ctx.consequence.args) {
                if (cTerm instanceof MultiCompoundConsequenceContext) {
                    visitMultiArgs(((MultiCompoundConsequenceContext) cTerm).functor(), ((MultiCompoundConsequenceContext) cTerm).args);
                }
                addClause((Struct) visit(cTerm), antecedent);
            }
            return null;
        }
    }

    // --- ANTECEDENT ---

    @Override public Term visitNotAntecedent(NotAntecedentContext ctx) {
        return not(visit(ctx.aTerm()));
    }

    @Override public Term visitCompoundAntecedent(CompoundAntecedentContext ctx) {
        return visitCompound(ctx.functor(), ctx.args);
    }

    @Override public Term visitMultiAndCompoundAntecedent(MultiAndCompoundAntecedentContext ctx) {
        return and(visitMultiArgs(ctx.functor(), ctx.args));
    }

    @Override public Term visitMultiOrCompoundAntecedent(MultiOrCompoundAntecedentContext ctx) {
        return or(visitMultiArgs(ctx.functor(), ctx.args));
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
        return and(visitAggregate(ctx.args));
    }

    @Override public Term visitOrSeries(OrSeriesContext ctx) {
        return or(visitAggregate(ctx.args));
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
        return and(visitMultiArgs(ctx.functor(), ctx.args));
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


    /* *******************************
     --- Abstract visitor methods ---
     ******************************* */

    /**
     * Visits a compound term, returning one {@link Term} given a functor and an arbitrary amount of arguments.
     * Used for visiting {@link CompoundAntecedentContext} and {@link CompoundConsequenceContext}.
     *
     * @param functor   Given functor
     * @param arguments Given arguments
     * @return          Resulting {@link Term}
     */
    public Term visitCompound(FunctorContext functor, ParseTree arguments) {
        if (arguments == null) {
            // No parenthesis used: the compound term is an atom.
            // Examples: p, q, `special%chars/{`.
            return struct(getFunctor(functor));
        } else {
            // Parenthesis used: the compound term can still be an atom in case of empty parenthesis.
            // Example: p(X), p(X,Y,Z), p(), `,`(a, b).
            List<? extends ParseTree> terms;
            if (arguments instanceof AArgSeriesContext) {
                // Antecedent
                terms = ((AArgSeriesContext) arguments).args;
            } else {
                // Consequence
                terms = ((CArgSeriesContext) arguments).args;
            }
            return struct(getFunctor(functor), visitAggregate(terms));
        }
    }

    /**
     * Visits a list, returning one {@link Term} given a possible list tail and an arbitrary amount of list elements.
     * Used for visiting {@link ListAntecedentContext} and {@link ListConsequenceContext}.
     *
     * @param tail      Given list tail
     * @param elements  Given list elements
     * @return          Resulting {@link Term}
     */
    public Term visitList(ParseTree tail, ParseTree elements) {
        // Empty list
        if (elements == null) {
            return list();
        }
        List<? extends ParseTree> args;
        if (elements instanceof AArgSeriesContext) {
            args = ((AArgSeriesContext) elements).args;
        } else {
            args = ((CArgSeriesContext) elements).args;
        }
        // List without tail
        if (tail == null) {
            return list(visitAggregate(args));
        }
        // List with tail
        return listTail(list(visit(tail)), visitAggregate(args));
    }

    /**
     * Visits multiple parse tree nodes and returns a result list as a {@link Term} list.
     *
     * @param ctxs  Given parse tree nodes
     * @return      Resulting {@link Term} list
     */
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

    /**
     * Visits the {@link ParseTree} multi arguments of a multi compound term, returning a {@link List<Term>} containing
     * every generated (singular) compound term.
     * Used for visiting {@link MultiAndCompoundAntecedentContext}, {@link MultiOrCompoundAntecedentContext} and
     * {@link MultiCompoundConsequenceContext}.
     *
     * An example:
     *      Line in Graafvis script:        p{a, (X,Y), ((a,b)), (), []}.
     *      Generates the hidden rules:     p(a). p(X,Y). p((a,b)). p. p([]).
     *
     * N.B. using {@link com.github.meteoorkip.prolog.TuProlog ensures that a multi
     * argument which does not contain any arguments still generates an atom: the functor without arguments.
     * N.B. there is no check ensuring multiArgs is not null, as the grammar prohibits using a multi compound without
     * multi arguments. Example: p{} is not allowed.
     *
     *
     * @param functor   Given functor
     * @param multiArgs Given multi arguments
     * @return
     */
    public Term[] visitMultiArgs(FunctorContext functor, List<? extends ParseTree> multiArgs) {
        int n = multiArgs.size();
        Term[] results = new Term[n];
        for (int i = 0; i < n; i++) {
            ParseTree multiArg = multiArgs.get(i);

            // Casting
            List<? extends ParseTree> args;
            if (multiArg instanceof AMultiArgContext) {
                // MultiTerm in antecedent
                AMultiArgContext tc = (AMultiArgContext) multiArg;
                if (tc.aArgSeries() != null) {
                    // [Case 1] With parenthesis: contains multiple arguments for the to be generated compound term
                    args = tc.aArgSeries().args;
                } else if (tc.aTerm() != null) {
                    // [Case 2] Without parenthesis: contains one argument for the to be generated compound term
                    args = inList(tc.aTerm());
                } else {
                    // [Case 3] No terms: empty parenthesis
                    args = null;
                }
            } else {
                // MultiTerm in consequence
                CMultiArgContext tc = (CMultiArgContext) multiArg;
                if (tc.cArgSeries() != null) {
                    // [Case 1] With parenthesis: contains multiple arguments for the to be generated compound term
                    args = tc.cArgSeries().args;
                } else if (tc.cTerm() != null) {
                    // [Case 2] Without parenthesis: contains one argument for the to be generated compound term
                    args = inList(tc.cTerm());
                } else {
                    // [Case 3] No terms: empty parenthesis
                    args = null;
                }
            }
            Term[] arguments = visitAggregate(args);
            results[i] = arguments == null ? struct(getFunctor(functor)) : struct(getFunctor(functor), arguments);
        }
        return results;
    }


    /**********************
     --- Helper methods ---
     **********************/

    // --- Updating & reading the rule generation model ---

    private void addClause(Struct head, Term... body) {
        result.add(clause(head, body));
    }

    private void addFact(Struct head) {
        result.add(fact(head));
    }

    public List<Clause> getResult() {
        return result;
    }

    public void setResult(List<Clause> ts) {
        this.result = ts;
    }

    // --- String building ---

    private static String getFunctor(FunctorContext ctx) {
        String s = ctx.getText();
        return (ctx instanceof InfixFunctorContext) ? removeOuterChars(s) : s;
    }

    private static String removeOuterChars(String s) {
        return (s == null) ? s : s.substring(1, s.length() - 1);
    }

    // --- Generic collection help ---

    public static <T> List<T> inList(T... ts) {
        return Arrays.asList(ts);
    }

}
