package graafvis;

import alice.tuprolog.*;
import alice.tuprolog.Number;
import graafvis.grammar.GraafvisBaseVisitor;
import graafvis.grammar.GraafvisParser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

import static compiler.prolog.TuProlog.*;

/**
 *
 */
public class RuleGeneratorProposal extends GraafvisBaseVisitor<Term> {

    public static final String TUP_WILD_CARD = "_";
    public static final String TUP_AND = ",";
    public static final String TUP_OR = ";";
    public static final String TUP_NOT = "not";

    private final List<Term> result;

    public RuleGeneratorProposal(GraafvisParser.ProgramContext ctx) {
        result = new ArrayList<>();
        ctx.accept(this);
    }

    /*************************
     --- Tree walker ---
     *************************/

    @Override public Term visitNodeLabelGen(GraafvisParser.NodeLabelGenContext ctx) {
        for (GraafvisParser.LabelContext label : ctx.label()) {
            String asName = label.STRING().getText();
            String dslName = label.ID() == null ? asName.substring(1, asName.length() - 1) : label.ID().getText();
            addClause(clause(
                    struct(dslName, var("X")),
                    and(struct("node", var("X")), struct("label", var("X"), struct(asName)))
            ));
        }
        return null;
    }

    @Override public Term visitEdgeLabelGen(GraafvisParser.EdgeLabelGenContext ctx) {
        for (GraafvisParser.LabelContext label : ctx.label()) {
            String asName = label.STRING().getText();
            String dslName = label.ID() == null ? asName.substring(1, asName.length() - 1) : label.ID().getText();
            addClause(clause(
                    struct(dslName, var("X")),
                    and(struct("edge", var("X")), struct("label", var("X"), struct(asName)))
            ));
            addClause(clause(
                    struct(dslName, var("X"), var("Y")),
                    and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct(asName)))
            ));
            addClause(clause(
                    struct(dslName, var("X"), var("Y"), var("Z")),
                    and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct(asName)))
            ));
        }
        return null;
    }


    @Override public Term visitClause(GraafvisParser.ClauseContext ctx) {
        if (ctx.antecedent() == null) {
            // Fact
            for (GraafvisParser.LiteralContext conseqLit : ctx.consequence().literal()) {
                addClause(visit(conseqLit));
            }
        } else {
            Term antecedent = visit(ctx.antecedent());
            // Rule
            for (GraafvisParser.LiteralContext conseqLit : ctx.consequence().literal()) {
                addClause(clause(visit(conseqLit), antecedent));
            }
        }
        return null;
    }

    @Override public Term visitPfNest(GraafvisParser.PfNestContext ctx) {
        return visit(ctx.propositionalFormula()); // TODO is dit ok? want dat an deze eig weg
    }

    @Override public Term visitPfNot(GraafvisParser.PfNotContext ctx) {
        return struct(TUP_NOT, visit(ctx.propositionalFormula()));
    }

    @Override public Term visitPfAnd(GraafvisParser.PfAndContext ctx) {
        return new Struct(TUP_AND, visit(ctx.propositionalFormula(0)), visit(ctx.propositionalFormula(1)));
    }

    @Override public Term visitPfOr(GraafvisParser.PfOrContext ctx) {
        return new Struct(TUP_OR, visit(ctx.propositionalFormula(0)), visit(ctx.propositionalFormula(1)));
    }

    // --- ATOMS ---

    @Override public Term visitAtom(GraafvisParser.AtomContext ctx) {
        if (ctx.termTuple() == null) {
            // Constant, f.e. p
            return new Struct(ctx.predicate().getText());
        } else {
            // Regular predicate, f.e. p(X), p(X,Y,Z), p()
            return new Struct(ctx.predicate().getText(), aggregateVisit(ctx.termTuple().term())); // TODO geen agg in geval van enkele term?
        }
        // TODO mogelijk deel verplaatsen naar TermTuple
    }

    @Override public Term visitMultiAnd(GraafvisParser.MultiAndContext ctx) {
        return and(aggregateVisitMultiTerms(ctx.predicate().getText(), ctx.multiTerm()));
    }

    @Override public Term visitMultiOr(GraafvisParser.MultiOrContext ctx) {
        return or(aggregateVisitMultiTerms(ctx.predicate().getText(), ctx.multiTerm()));
    }

    // --- TERMS ----

    @Override public Term visitTermVar(GraafvisParser.TermVarContext ctx) {
        return new Var(ctx.getText());
    }

    @Override public Term visitTermWildcard(GraafvisParser.TermWildcardContext ctx) {
        return new Var(TUP_WILD_CARD);
    }

    @Override public Term visitTermString(GraafvisParser.TermStringContext ctx) {
        return new Struct(ctx.getText());
    }

    @Override public Term visitTermNumber(GraafvisParser.TermNumberContext ctx) {
        return Number.createNumber(ctx.getText());
    }

    @Override public Term visitTermID(GraafvisParser.TermIDContext ctx) {
        return new Struct(ctx.getText());
    }

    @Override public Term visitTermList(GraafvisParser.TermListContext ctx) {
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
            // No subtrees available
            return new Term[0];
        }
        int n = ctxs.size();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            if (ctxs.get(i).getChild(0) instanceof GraafvisParser.TermTupleContext) {
                // TermTuple
                System.out.println("AVMT Termtuple: " + ctxs.get(i).getText());
                terms[i] = new Struct(predicate, aggregateVisit(((GraafvisParser.TermTupleContext) ctxs.get(i).getChild(0)).term()));
            } else {
                // Term
                System.out.println("AVMT Term: " + ctxs.get(i).getText());
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

}
