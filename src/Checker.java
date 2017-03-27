import java.util.ArrayList;
import java.util.HashSet;

/**
 * Graafvis checker
 *
 * Responsible for:
 *  - Correct file names in import statements
 *  - Correct labels and IDs in label generation statements
 *  - Blacklisted predicates in the consequence of a clause
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

    /** Regex of a .vis file */
    private static final String VIS_FILE_REGEX = ""; // TODO
    private static final String ID_REGEX = ""; // TODO?

    /**
     * Instance variables
     */

    /** Set of predicates that cannot be used in consequences */
    private final HashSet<String> consequenceBlackList;

    /** List of errors obtained during the checking phase */
    private final ArrayList<Error> errors;

    /**
     * Constructor
     */

    /** Create a new checker */
    public Checker() {
        consequenceBlackList = new HashSet<>();
        consequenceBlackList.addAll(DEFAULT_BLACKLIST);
        errors = new ArrayList<>();

    }

    /**
     * Tree listener methods
     */

    @Override
    public void enterImport_vis(GraafvisParser.Import_visContext ctx) {
        // TODO -- check import regex
        super.enterImport_vis(ctx);
    }

    @Override
    public void enterConsequence(GraafvisParser.ConsequenceContext ctx) {
        // TODO -- check blacklist
        super.enterConsequence(ctx);
    }

    @Override
    public void enterNode_label_gen(GraafvisParser.Node_label_genContext ctx) {
        // TODO -- check proper id and non empty string
        // TODO -- add label to blacklist
        super.enterNode_label_gen(ctx);
    }

    @Override
    public void enterEdge_label_gen(GraafvisParser.Edge_label_genContext ctx) {
        // TODO -- check proper id and non empty string
        // TODO -- add label to blacklist
        super.enterEdge_label_gen(ctx);
    }
}
