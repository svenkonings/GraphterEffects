package graafvis.deprecated;

import graafvis.ErrorListener;
import graafvis.errors.BlacklistedPredicateError;
import graafvis.errors.VisError;
import graafvis.grammar.GraafvisBaseListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Graafvis checker
 *
 * Responsible for:
 *  - Correct file names in import statements
 *  - Correct labels and IDs in label generation statements
 *  - Blacklisted predicates in the consequence of a clause
 *  - Correct usage of variables
 */
public class Checker extends GraafvisBaseListener {

    /**
     * Constants
     */

    /** Set of predicates that are RHS blacklisted by default */
    private static final HashSet<String> DEFAULT_BLACKLIST = new HashSet<>();
    static {
        DEFAULT_BLACKLIST.add("node"); // TODO -- make reference to constants
        DEFAULT_BLACKLIST.add("edge");
        DEFAULT_BLACKLIST.add("label");
        DEFAULT_BLACKLIST.add("attribute");
    }

    /**
     * Instance variables
     */

    /** Set of predicates that cannot be used in consequences */
    private final HashSet<String> consequenceBlackList;

    /** List of graafvis.errors obtained during the checking phase */
    private final ArrayList<VisError> errors;

    private final ParseTreeProperty<HashSet<String>> clauseScope; // TODO -- use this to check proper variable use?

    /**
     * Constructor
     */

    /** Create a new checker */
    public Checker() {
        consequenceBlackList = new HashSet<>();
        consequenceBlackList.addAll(DEFAULT_BLACKLIST);
        errors = new ArrayList<>();
        clauseScope = new ParseTreeProperty<>();

    }

    /**
     * Tree listener methods
     */

    @Override
    public void enterImport_vis(GraafvisParser.Import_visContext ctx) {
        // TODO -- check import regex
        String fileName = ctx.STRING().getText();
    }

    /** Creates an error when blacklisted predicates are used in this consequence */
    @Override
    public void enterConsequence(GraafvisParser.ConsequenceContext ctx) {
        List<GraafvisParser.LiteralContext> literals = ctx.literal();
        /* Iterate through all literals in the consequence */
        for (GraafvisParser.LiteralContext literal : literals) {
            ParseTree child = literal.getChild(0);
            /* Check literals of atom type */
            if (child instanceof GraafvisParser.AtomContext) {
                /* Obtain the predicate */
                GraafvisParser.PredicateContext predicateContext = ((GraafvisParser.AtomContext) child).predicate();
                String predicate = predicateContext.getText();
                /* Check if the predicate is blacklisted */
                if (this.consequenceBlackList.contains(predicate)) {
                    int line = predicateContext.ID().getSymbol().getLine();
                    int column = predicateContext.ID().getSymbol().getCharPositionInLine();
                    /* The predicate is blacklisted. Add an error */
                    this.errors.add(new BlacklistedPredicateError(line, column, predicate));
                }
            /* Check literals of multi atom types */
            } else if (child instanceof GraafvisParser.Multi_atomContext) {
                /* Obtain the predicate */
                GraafvisParser.PredicateContext predicateContext = ((GraafvisParser.Multi_atomContext) child).predicate();
                String predicate = predicateContext.getText();
                /* Check if the predicate is blacklisted */
                if (this.consequenceBlackList.contains(predicate)) {
                    int line = predicateContext.ID().getSymbol().getLine();
                    int column = predicateContext.ID().getSymbol().getCharPositionInLine();
                    /* The predicate is blacklisted. Add an error */
                    this.errors.add(new BlacklistedPredicateError(line, column, predicate));
                }
            }
        }
    }

    /** Makes sure the specified labels can be converted to proper predicates */
    @Override
    public void enterNode_label_gen(GraafvisParser.Node_label_genContext ctx) {
        List<GraafvisParser.LabelContext> labels = ctx.label();
        labels.forEach(this::addLabel);
    }

    /** Makes sure the specified labels can be converted to proper predicates */
    @Override
    public void enterEdge_label_gen(GraafvisParser.Edge_label_genContext ctx) {
        List<GraafvisParser.LabelContext> labels = ctx.label();
        labels.forEach(this::addLabel);
    }

    /**
     * Help methods
     */

    /** Checks if a label has been renamed to a proper ID. If so, it adds the ID to the blacklist.
     *  Otherwise it checks if the original label is a correct ID and adds this to the blacklist. */
    private void addLabel(GraafvisParser.LabelContext label) {
        if (label.RENAME_TOKEN() == null) {
            String labelString = label.STRING().getText();
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
            this.consequenceBlackList.add(label.ID().getText());
        }
    }

    /**
     * Getters
     */

    public HashSet<String> getConsequenceBlackList() {
        return consequenceBlackList;
    }

    public ArrayList<VisError> getErrors() {
        return errors;
    }


}
