package com.github.meteoorkip.screens.idescreen.tab.graafviseditor;

import com.github.meteoorkip.general.ViewModel;
import com.github.meteoorkip.general.files.FileModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import com.github.meteoorkip.utils.FileUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraafVisEditorPresenter implements Initializable {

    @FXML public StackPane graafvisEditorPane;
    @Inject ViewModel viewModel;
    private CodeArea codeArea;

    private static final String[] KEYWORDS = new String[] {
            "as", "(edge|node)\\s*(labels)","eval","not","consult","println"
    };

    //Interesting for pre-defined things with a meaning
    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";


    private static final String PAREN_PATTERN = "[()]";
    private static final String BRACE_PATTERN = "[{}]";
    private static final String BRACKET_PATTERN = "[\\[\\]]";

    //Patterns concerning the logical flow
    private static final String DOT_PATTERN = "\\.";
    private static final String ARROW_PATTERN = "->";

    //Patterns for atoms/variables
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String VARIABLE_PATTERN = "(?<![a-zA-Z])[A-Z][a-zA-Z0-9]*";

    //

    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";
    private static final String PREDICATE_PATTERN = "([^\\W]*(?=[({]))|(`[^\\(]*`(?=[({]))";


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        codeArea.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .subscribe(change -> {
                    //System.out.println(change);
                    try {
                        codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
                    } catch (Exception e){
                        //e.printStackTrace();
                        //Error concerning RichTextFX
                        //TODO: Explain error in detail
                        //Intersting: not findable on google :o
                    }
                    FileModel.getInstance().graafVisCode = codeArea.getText();
                });

        String graafVisCode;
        try {
            graafVisCode = FileUtils.readFromFile(FileModel.getInstance().getGraafVisFilePath().toFile());
            codeArea.replaceText(0, 0, graafVisCode);
            FileModel.getInstance().graafVisCode = graafVisCode;
        } catch (IOException e) {
            e.printStackTrace();
        }

        graafvisEditorPane.getChildren().add(new VirtualizedScrollPane<>(codeArea));
        graafvisEditorPane.getStylesheets().add(GraafVisEditorPresenter.class.getResource("graafvis-highlighting.css").toExternalForm());

        codeArea.prefWidthProperty().bind(graafvisEditorPane.widthProperty());
        codeArea.prefHeightProperty().bind(graafvisEditorPane.heightProperty());
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

    public CodeArea getCodeArea(){
        return codeArea;
    }

    public void setCodeArea(CodeArea codeArea){
        this.codeArea = codeArea;
    }
}
