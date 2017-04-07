package screens.idescreen.graafviseditor;

import general.files.DocumentModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import utils.FileUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraafVisEditorPresenter implements Initializable {

    @FXML public AnchorPane graafvisEditorPane;

    private static final String[] KEYWORDS = new String[] {
            "as", "(edge|graph|node)\\s*(labels)"
    };

    //Interesting for pre-defined things with a meaning
    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";


    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";

    //Patterns concerning the logical flow
    private static final String DOT_PATTERN = "\\.";
    private static final String ARROW_PATTERN = "->";

    //Patterns for atoms/variables
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String VARIABLE_PATTERN = "(?<![a-zA-Z])[A-Z][a-zA-Z0-9]*";

    //

    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";
    private static final String PREDICATE_PATTERN = "[^\\s]*(?=[(])";


    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<DOT>" + DOT_PATTERN + ")"
                    + "|(?<ARROW>" + ARROW_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
                    + "|(?<VARIABLE>" + VARIABLE_PATTERN + ")"
                    + "|(?<PREDICATE>" + PREDICATE_PATTERN + ")"
    );

    private static final String sampleCode = String.join("\n", new String[] {
            "edge labels:\n" +
                    "    \"wolf\",\n" +
                    "    \"type:Goat\" as goat,\n" +
                    "    \"type:Cabbage\" as cabbage,\n" +
                    "    \"type:Boat\" as boat,\n" +
                    "    \"type:Bank\" as bank,\n" +
                    "    \"on\",\n" +
                    "    \"in\",\n" +
                    "    \"likes\",\n" +
                    "    \"moored\",\n" +
                    "    \"go\",\n" +
                    "    \"flag:left\" as leftBank,\n" +
                    "    \"flag:right\" as rightBank.\n" +
                    "\n" +
                    "bounds(0, 32).\n" +
                    "backgroundImage(\"images/river.png\").\n" +
                    "\n" +
                    "shape(water, rectangle).\n" +
                    "colour(water, blue).\n" +
                    "dimensions(water, 20, 4).\n" +
                    "\n" +
                    "wolf(X, X); goat(X, X); cabbage(X, X) ->\n" +
                    "    passenger(X).\n" +
                    "\n" +
                    "passenger(X), passenger(Y) ->\n" +
                    "    noOverlap(X, Y).\n" +
                    "\n" +
                    "wolf(X, X) ->\n" +
                    "    image(X, \"images/wolf.png\"),\n" +
                    "    dimensions(X, 2, 2).\n" +
                    "\n" +
                    "goat(X, X) ->\n" +
                    "    image(X, \"images/sheep.png\"),\n" +
                    "    dimensions(X, 2, 2).\n" +
                    "\n" +
                    "cabbage(X, X) ->\n" +
                    "    image(X, \"images/cabbage.png\"),\n" +
                    "    dimensions(X, 2, 2).\n" +
                    "\n" +
                    "boat(X, X) ->\n" +
                    "    image(X, \"images/boat.png\"),\n" +
                    "    dimensions(X, 8, 3),\n" +
                    "    enclosedHorizontal(X, water),\n" +
                    "    above(X, water, -1),\n" +
                    "    before(X, water).\n" +
                    "\n" +
                    "bank(X, X) ->\n" +
                    "    shape(X, rectangle),\n" +
                    "    colour(X, green),\n" +
                    "    alignBottom(X, water).\n" +
                    "\n" +
                    "bank(X, X), bank(Y, Y) ->\n" +
                    "    sameWidth(X, Y).\n" +
                    "\n" +
                    "boat(X, X), bank(Y, Y) ->\n" +
                    "    alignTop(X, Y).\n" +
                    "\n" +
                    "leftBank(X, X) ->\n" +
                    "    left(X, water, 0).\n" +
                    "\n" +
                    "rightBank(X, X) ->\n" +
                    "    right(X, water, 0).\n" +
                    "\n" +
                    "on(Passenger, Bank) ->\n" +
                    "    above(Passenger, Bank, -1),\n" +
                    "    enclosedHorizontal(Passenger, Bank),\n" +
                    "    before(Passenger, Bank).\n" +
                    "\n" +
                    "in(Passenger, Boat) ->\n" +
                    "    above(Passenger, Boat, -1),\n" +
                    "    enclosedHorizontal(Passenger, Boat),\n" +
                    "    alignHorizontal(Passenger, Boat),\n" +
                    "    before(Passenger, Boat).\n" +
                    "\n" +
                    "moored(Boat, Bank); go(Boat, Bank) ->\n" +
                    "    horizontalDistance(Boat, Bank, 0).\n" +
                    "\n" +
                    "// --- Legend ---\n" +
                    "shape(legend, rectangle).\n" +
                    "colour(legend, lightpink).\n" +
                    "above(legend, water, 4).\n" +
                    "alignLeft(legend, water).\n" +
                    "zPos(legend, -1).\n" +
                    "\n" +
                    "passenger(X), image(X, Path) -> image([X, mini], Path), dimensions([X, mini], 1, 1).\n" +
                    "\n" +
                    "likes(X, Y, Z) ->\n" +
                    "    image(Z, \"images/heart.png\"),\n" +
                    "    dimensions(Z, 1, 1),\n" +
                    "    left([X, mini], Z, 0),\n" +
                    "    left(Z, [Y, mini], 0),\n" +
                    "    alignVertical([X, mini], Z),\n" +
                    "    alignVertical(Z, [Y, mini]),\n" +
                    "    enclosed([X, mini], legend),\n" +
                    "    enclosed([Y, mini], legend),\n" +
                    "    enclosed(Z, legend)."
    });


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CodeArea codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        codeArea.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .subscribe(change -> {
                    codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
                });

        String graafVisCode = null;
        try {
            graafVisCode = FileUtils.readFromFile(DocumentModel.getInstance().getGraafVisFilePath().toFile());
            codeArea.replaceText(0, 0, graafVisCode);
            DocumentModel.getInstance().graafVisCode = graafVisCode;
        } catch (IOException e) {
            e.printStackTrace();
        }


        graafvisEditorPane.getChildren().add(new VirtualizedScrollPane<>(codeArea));
        graafvisEditorPane.getStylesheets().add(GraafVisEditorPresenter.class.getResource("graafvis-keywords.css").toExternalForm());

        codeArea.prefWidthProperty().bind(graafvisEditorPane.widthProperty());
        codeArea.prefHeightProperty().bind(graafvisEditorPane.heightProperty());
        codeArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                DocumentModel.getInstance().graafVisCode = newValue;
            }
        });

        graafvisEditorPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("GVE:" + newValue);
            }
        });
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("DOT") != null ? "dot" :
                                                            matcher.group("ARROW") != null ? "arrow" :
                                                                    matcher.group("STRING") != null ? "string" :
                                                                            matcher.group("PREDICATE") != null ? "predicate" :
                                                                                    matcher.group("VARIABLE") != null ? "variable" :
                                                                                            matcher.group("COMMENT") != null ? "comment" :

                                                                                                    null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}
