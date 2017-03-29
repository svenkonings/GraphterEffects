package graafvis.checkers;

import graafvis.ErrorListener;
import graafvis.GraafvisBaseVisitor;
import graafvis.GraafvisLexer;
import graafvis.GraafvisParser;
import graafvis.errors.BlacklistedPredicateError;
import graafvis.errors.VisError;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 */
public class ConsequenceBlacklist extends GraafvisBaseVisitor<Void> {

    /** Set of predicates that are RHS blacklisted by default */
    private static final HashSet<String> DEFAULT_BLACKLIST = new HashSet<>();
    static {
        DEFAULT_BLACKLIST.add("node"); // TODO -- make reference to constants
        DEFAULT_BLACKLIST.add("edge");
        DEFAULT_BLACKLIST.add("label");
        DEFAULT_BLACKLIST.add("attribute");
    }

    /** Set of predicates that cannot be used in consequences */
    private final HashSet<String> consequenceBlackList;

    /** List of graafvis.errors obtained during the checking phase */
    private final ArrayList<VisError> errors;

    /** Create a new blacklist */
    public ConsequenceBlacklist() {
        consequenceBlackList = new HashSet<>();
        consequenceBlackList.addAll(DEFAULT_BLACKLIST);
        errors = new ArrayList<>();

    }

    /**
     * Visitor methods
     */

    @Override
    public Void visitProgram(GraafvisParser.ProgramContext ctx) {
        if (ctx.node_label_gen() != null) {
            visitNode_label_gen(ctx.node_label_gen());                                              // Add generated node labels to blacklist
        }
        if (ctx.edge_label_gen() != null) {
            visitEdge_label_gen(ctx.edge_label_gen());                                              // Add generated egde labels to blacklist
        }
        ctx.clause().forEach(this::visitClause);                                                    // Visit all clauses
        return null;
    }

    @Override
    public Void visitLabel(GraafvisParser.LabelContext ctx) {
        if (ctx.RENAME_TOKEN() == null) {
            String labelString = ctx.STRING().getText();
            /* Remove quotation marks */
            String predicateToGenerate = labelString.substring(1, labelString.length() - 1);
            /* Parse predicate for correctness */
            CharStream stream = new ANTLRInputStream(predicateToGenerate);
            GraafvisLexer lexer = new GraafvisLexer(stream);
            TokenStream tokenStream = new CommonTokenStream(lexer);
            GraafvisParser parser = new GraafvisParser(tokenStream);
            ErrorListener errorListener = new ErrorListener();
            /* Make sure parse graafvis.errors are captured */
            lexer.removeErrorListeners();
            lexer.addErrorListener(errorListener);
            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);
            /* Check for graafvis.errors */
            this.errors.addAll(errorListener.getErrors());
            /* Add the label to the blacklist */
            this.consequenceBlackList.add(predicateToGenerate);
        } else {
            /* Add the label to the blacklist */
            this.consequenceBlackList.add(ctx.ID().getText());
        }
        return null;
    }

    @Override
    public Void visitClause(GraafvisParser.ClauseContext ctx) {
        return visitConsequence(ctx.consequence());                                                 // Only the consequence needs to be checked
    }

    @Override
    public Void visitNumExprLiteral(GraafvisParser.NumExprLiteralContext ctx) {
        return null;                                                                                // Nothing to see here
    }

    @Override
    public Void visitAtomLiteral(GraafvisParser.AtomLiteralContext ctx) {
        blacklistCheck(ctx.atom().predicate());                                                     // Check the atom's predicate
        return null;
    }

    @Override
    public Void visitMultiAtomLiteral(GraafvisParser.MultiAtomLiteralContext ctx) {
        blacklistCheck(ctx.multi_atom().predicate());                                               // Check the atom's predicate
        return null;
    }
    
    /**
     * Helper methods
     */

    /** Checks if the predicate has been blacklisted. If so, it adds an error to the error list */
    private void blacklistCheck(GraafvisParser.PredicateContext ctx) {
        String predicate = ctx.getText();
        if (consequenceBlackList.contains(predicate)) {
            int line = ctx.ID().getSymbol().getLine();
            int column = ctx.ID().getSymbol().getCharPositionInLine();
            errors.add(new BlacklistedPredicateError(line, column, predicate));
        }
    }

    /**
     * Getters
     */

    public ArrayList<VisError> getErrors() {
        return errors;
    }


}
