import graafvis.GraafvisParser;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class ProgramTest extends GrammarTest {

    private static final List<String> PROGRAMS = Arrays.asList
            (
                    "%s %s %s %s",
                    "%s %s %s %s %s"
            );

    public static final List<String> VALID_SAMPLES = new ArrayList<>();
    static {
        List<String> imports = ImportVisTest.VALID_SAMPLES;
        List<String> nodeLabelGen = NodeLabelGenTest.VALID_SAMPLES;
        List<String> edgeLabelGen = EdgeLabelGenTest.VALID_SAMPLES;
        List<String> clauses = ClauseTest.VALID_SAMPLES;
        // Add program 0
        for (String sample : imports) {
            VALID_SAMPLES.add(String.format(PROGRAMS.get(0), sample, nodeLabelGen.get(0), edgeLabelGen.get(0), clauses.get(0)));
        }
        for (String sample : nodeLabelGen) {
            VALID_SAMPLES.add(String.format(PROGRAMS.get(0), imports.get(0), sample, edgeLabelGen.get(0), clauses.get(0)));
        }
        for (String sample : edgeLabelGen) {
            VALID_SAMPLES.add(String.format(PROGRAMS.get(0), imports.get(0), nodeLabelGen.get(0), sample, clauses.get(0)));
        }
        for (String sample : clauses) {
            VALID_SAMPLES.add(String.format(PROGRAMS.get(0), imports.get(0), nodeLabelGen.get(0), edgeLabelGen.get(0), sample));
        }
        // Add program 1
        for (String sample : imports) {
            VALID_SAMPLES.add(String.format(PROGRAMS.get(1), sample, nodeLabelGen.get(0), edgeLabelGen.get(0), clauses.get(0), clauses.get(0)));
        }
        for (String sample : nodeLabelGen) {
            VALID_SAMPLES.add(String.format(PROGRAMS.get(1), imports.get(0), sample, edgeLabelGen.get(0), clauses.get(0), clauses.get(0)));
        }
        for (String sample : edgeLabelGen) {
            VALID_SAMPLES.add(String.format(PROGRAMS.get(1), imports.get(0), nodeLabelGen.get(0), sample, clauses.get(0), clauses.get(0)));
        }
        for (String sample : clauses) {
            VALID_SAMPLES.add(String.format(PROGRAMS.get(1), imports.get(0), nodeLabelGen.get(0), edgeLabelGen.get(0), sample, sample));
        }
    }

    public static final List<String> INVALID_SAMPLES = Arrays.asList
            (

            );


    @Override
    protected List<String> getValidSamples() {
        return VALID_SAMPLES;
    }

    @Override
    protected List<String> getInvalidSamples() {
        return INVALID_SAMPLES;
    }

    @Override
    protected ParserRuleContext parse(GraafvisParser parser) {
        return parser.program();
    }

    @Override
    protected String getRuleName() {
        return "program";
    }
}
