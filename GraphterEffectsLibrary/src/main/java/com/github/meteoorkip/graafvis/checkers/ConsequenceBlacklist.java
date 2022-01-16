package com.github.meteoorkip.graafvis.checkers;

import com.github.meteoorkip.graafvis.errors.BlacklistedFunctorError;
import com.github.meteoorkip.graafvis.errors.VisError;
import com.github.meteoorkip.graafvis.grammar.GraafvisBaseVisitor;
import com.github.meteoorkip.graafvis.grammar.GraafvisParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Makes sure blacklisted functors are not used in consequences. Blacklists functors that are generated from labels.
 */
class ConsequenceBlacklist extends GraafvisBaseVisitor<Void> {

    /** Set of functors that are RHS blacklisted by default */
    private static final HashSet<String> DEFAULT_BLACKLIST = new HashSet<>();

    static {
        DEFAULT_BLACKLIST.add("node");
        DEFAULT_BLACKLIST.add("edge");
        DEFAULT_BLACKLIST.add("label");
        DEFAULT_BLACKLIST.add("attribute");
        DEFAULT_BLACKLIST.add("not");
        DEFAULT_BLACKLIST.add("inmst");
        DEFAULT_BLACKLIST.add("inshortestpath");
    }

    /** Set of functors that cannot be used in consequences */
    private final HashSet<String> consequenceBlackList = new HashSet<>();

    /** List of errors obtained during the checking phase */
    private final ArrayList<VisError> errors = new ArrayList<>();

    /** Create a new blacklist */
    ConsequenceBlacklist() {
        reset();
    }

    /** Reset the checker for new usage */
    void reset() {
        consequenceBlackList.clear();
        consequenceBlackList.addAll(DEFAULT_BLACKLIST);
    }

    /** Add a predicate to the blacklist */
    void blacklist(String functor) {
        this.consequenceBlackList.add(functor);
    }

    /** Add all predicates to the blacklist */
    void blacklistAll(Collection<String> predicates) {
        this.consequenceBlackList.addAll(predicates);
    }

    /*
     * Visitor methods
     */

    /** Visit all children of the program, except imports */
    @Override
    public Void visitProgram(GraafvisParser.ProgramContext ctx) {
        if (ctx.nodeLabelGen() != null) {
            visitNodeLabelGen(ctx.nodeLabelGen()); // Add generated node labels to blacklist
        }
        if (ctx.edgeLabelGen() != null) {
            visitEdgeLabelGen(ctx.edgeLabelGen()); // Add generated edge labels to blacklist
        }
        ctx.clause().forEach(this::visitClause);
        return null;
    }

    /** Add generated label functor to the blacklist */
    @Override
    public Void visitLabel(GraafvisParser.LabelContext ctx) {
        if (ctx.RENAME_TOKEN() == null) {
            /* The label string has not been renamed. Take string content as functor and add to blacklist */
            String labelString = ctx.STRING().getText();
            String functorToGenerate = labelString.substring(1, labelString.length() - 1); // Remove quotation marks
            this.consequenceBlackList.add(functorToGenerate);
        } else {
            /* The label string has been renamed. Take renamed label as functor and add to blacklist */
            this.consequenceBlackList.add(ctx.ID().getText());
        }
        return null;
    }

    /*
     * Terms
     */

    /** Check the functor and visit term */
    @Override
    public Void visitCompoundConsequence(GraafvisParser.CompoundConsequenceContext ctx) {
        blacklistCheck(ctx.functor());
        if (ctx.cArgSeries() != null) {
            return visitCArgSeries(ctx.cArgSeries());
        } else {
            return null;
        }
    }

    /** Check the functor and visit terms */
    @Override
    public Void visitMultiCompoundConsequence(GraafvisParser.MultiCompoundConsequenceContext ctx) {
        blacklistCheck(ctx.functor());
        for (GraafvisParser.CMultiArgContext arg : ctx.args) {
            visitCMultiArg(arg);
        }
        return null;
    }

    /*
     * Helper methods
     */

    /** Checks if the functor has been blacklisted. If so, it adds an error to the error list */
    private void blacklistCheck(GraafvisParser.FunctorContext functor) {
        String id = functor.getText();
        if (consequenceBlackList.contains(id)) {
            int line = functor.getStart().getLine();
            int column = functor.getStart().getCharPositionInLine();
            errors.add(new BlacklistedFunctorError(line, column, id));
        }
    }

    /*
     * Getters
     */

    ArrayList<VisError> getErrors() {
        return errors;
    }

}
