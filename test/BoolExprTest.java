//import org.antlr.v4.runtime.ParserRuleContext;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// *
// */
//public class BoolExprTest extends GrammarTest {
//
//    private static final List<String> BOOL_EXPR = Arrays.asList
//            (
//                    "%s %s %s",
//                    "(%s %s %s)",
//                    "not (%s %s %s)",
//                    "not (false %s true)"
//            );
//
//    public static final List<String> VALID_SAMPLES = new ArrayList<>();
//    static {
//        List<String> vals = new ArrayList<>();
//        vals.addAll(VariableTest.VALID_SAMPLES);
//        vals.add("true");
//        vals.add("false");
//        List<String> numExprs = NumExprTest.VALID_SAMPLES;
//        // Add expr 0
//        for (String sample : vals) {
//            VALID_SAMPLES.add(String.format(BOOL_EXPR.get(0), sample, VALID_BOOL_OP_SAMPLES.get(0), sample));
//        }
//        for (String op : VALID_BOOL_OP_SAMPLES) {
//            VALID_SAMPLES.add(String.format(BOOL_EXPR.get(0), vals.get(0), op, vals.get(0)));
//        }
//        for (String sample : numExprs) {
//            VALID_SAMPLES.add(String.format(BOOL_EXPR.get(0), sample, VALID_EQ_OP_SAMPLES.get(0), sample));
//        }
//        for (String op : VALID_EQ_OP_SAMPLES) {
//            VALID_SAMPLES.add(String.format(BOOL_EXPR.get(0), numExprs.get(0), op, numExprs.get(0)));
//        }
//        // Add expr 1
//        for (String sample : vals) {
//            VALID_SAMPLES.add(String.format(BOOL_EXPR.get(1), sample, VALID_BOOL_OP_SAMPLES.get(0), sample));
//        }
//        for (String op : VALID_BOOL_OP_SAMPLES) {
//            VALID_SAMPLES.add(String.format(BOOL_EXPR.get(1), vals.get(0), op, vals.get(0)));
//        }
//        for (String sample : numExprs) {
//            VALID_SAMPLES.add(String.format(BOOL_EXPR.get(1), sample, VALID_EQ_OP_SAMPLES.get(0), sample));
//        }
//        for (String op : VALID_EQ_OP_SAMPLES) {
//            VALID_SAMPLES.add(String.format(BOOL_EXPR.get(1), numExprs.get(0), op, numExprs.get(0)));
//        }
//        // Add expr 2
//        for (String sample : vals) {
//            VALID_SAMPLES.add(String.format(BOOL_EXPR.get(2), sample, VALID_BOOL_OP_SAMPLES.get(0), sample));
//        }
//        for (String op : VALID_BOOL_OP_SAMPLES) {
//            VALID_SAMPLES.add(String.format(BOOL_EXPR.get(2), vals.get(0), op, vals.get(0)));
//        }
//        for (String sample : numExprs) {
//            VALID_SAMPLES.add(String.format(BOOL_EXPR.get(2), sample, VALID_EQ_OP_SAMPLES.get(0), sample));
//        }
//        for (String op : VALID_EQ_OP_SAMPLES) {
//            VALID_SAMPLES.add(String.format(BOOL_EXPR.get(2), numExprs.get(0), op, numExprs.get(0)));
//        }
//        // Add expr 3
//        for (String op : VALID_BOOL_OP_SAMPLES) {
//            VALID_SAMPLES.add(String.format(BOOL_EXPR.get(3), op));
//        }
//    }
//
//    public static final List<String> INVALID_SAMPLES = Arrays.asList
//            (
//                    "",
//                    "()",
//                    "(==)",
//                    "==",
//                    "==true",
//                    "1 or 1"
//            );
//
//    @Override
//    protected List<String> getValidSamples() {
//        return VALID_SAMPLES;
//    }
//
//    @Override
//    protected List<String> getInvalidSamples() {
//        return INVALID_SAMPLES;
//    }
//
//    @Override
//    protected ParserRuleContext parse(GraafvisParser parser) {
//        return parser.bool_expr();
//    }
//
//    @Override
//    protected String getRuleName() {
//        return "bool expr";
//    }
//
//}
