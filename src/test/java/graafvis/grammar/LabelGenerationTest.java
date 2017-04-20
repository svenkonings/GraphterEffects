package graafvis.grammar;

import org.junit.Test;

import java.util.ArrayList;

import static graafvis.grammar.TestUtil.*;

/**
 *
 */
public class LabelGenerationTest {

    private static final String NODE_LABEL_GEN_LINE = "node labels: %s.";
    private static final String EDGE_LABEL_GEN_LINE = "edge labels: %s.";

    private static final ArrayList<String> VALID_LABELING = new ArrayList<>();
    static {
        VALID_LABELING.add("\"wolf\"");
        VALID_LABELING.add("\"Wolf\" as wolf");
        VALID_LABELING.add("\"wolf\", \"Goat\" as goat");
        VALID_LABELING.add("\"@n1thin9_y0&_|1k3\" as functor");
        VALID_LABELING.add("\"goat\",\"cabbage\"");
    }

    private static final ArrayList<String> INVALID_LABELING = new ArrayList<>();
    static {
        INVALID_LABELING.add("wolf");
        INVALID_LABELING.add("\"wolf");
        INVALID_LABELING.add("\"wolf\" as _wolf");
        INVALID_LABELING.add("\"Wolf\" as Wolf");
    }

    static final ArrayList<String> VALID_NODE_GEN_SAMPLES = new ArrayList<>();
    static final ArrayList<String> INVALID_NODE_GEN_SAMPLES = new ArrayList<>();
    static final ArrayList<String> VALID_EDGE_GEN_SAMPLES = new ArrayList<>();
    static final ArrayList<String> INVALID_EDGE_GEN_SAMPLES = new ArrayList<>();

    static {
        for (String labeling : VALID_LABELING) {
            VALID_NODE_GEN_SAMPLES.add(String.format(NODE_LABEL_GEN_LINE, labeling));
            VALID_EDGE_GEN_SAMPLES.add(String.format(EDGE_LABEL_GEN_LINE, labeling));
        }
        for (String labeling : INVALID_LABELING) {
            INVALID_NODE_GEN_SAMPLES.add(String.format(NODE_LABEL_GEN_LINE, labeling));
            INVALID_EDGE_GEN_SAMPLES.add(String.format(EDGE_LABEL_GEN_LINE, labeling));
        }
        INVALID_NODE_GEN_SAMPLES.add(String.format("node laebel:%s.", "wolf"));
        INVALID_NODE_GEN_SAMPLES.add(String.format("node label:%s", "wolf"));
        INVALID_NODE_GEN_SAMPLES.add(String.format("node label %s.", "wolf"));

        INVALID_EDGE_GEN_SAMPLES.add(String.format("wedge lable:%s.", "wolf"));
        INVALID_EDGE_GEN_SAMPLES.add(String.format("edge label:%s", "wolf"));
        INVALID_EDGE_GEN_SAMPLES.add(String.format("edge label%s.", "wolf"));
    }

    @Test
    public void runTest() {
        writeTitleInLog("Label Generation Test");
        for (String sample : VALID_NODE_GEN_SAMPLES) {
            testValidNodeLabelGen(sample);
        }
        for (String sample : INVALID_NODE_GEN_SAMPLES) {
            testInvalidNodeLabelGen(sample);
        }
        for (String sample : VALID_EDGE_GEN_SAMPLES) {
            testValidEdgeLabelGen(sample);
        }
        for (String sample : INVALID_EDGE_GEN_SAMPLES) {
            testInvalidEdgeLabelGen(sample);
        }
    }

    private static void testValidNodeLabelGen(String sample) {
        GraafvisParser parser = getGraafvisParserFor(sample);
        parser.nodeLabelGen();
        assertHasNoErrors(parser);
    }

    private static void testInvalidNodeLabelGen(String sample) {
        GraafvisParser parser = getGraafvisParserFor(sample);
        parser.nodeLabelGen();
        assertHasErrors(parser);
    }

    private static void testValidEdgeLabelGen(String sample) {
        GraafvisParser parser = getGraafvisParserFor(sample);
        parser.edgeLabelGen();
        assertHasNoErrors(parser);
    }

    private static void testInvalidEdgeLabelGen(String sample) {
        GraafvisParser parser = getGraafvisParserFor(sample);
        parser.edgeLabelGen();
        assertHasErrors(parser);
    }


}
