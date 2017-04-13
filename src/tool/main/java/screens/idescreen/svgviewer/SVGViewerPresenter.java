package screens.idescreen.svgviewer;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSException;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import utils.FileUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SVGViewerPresenter implements Initializable {

    public WebView webView;
    @FXML public StackPane svgViewerPane;
    private String svgName;
    private Path svgPath;
    private String svgAsString;

    final WebView webview = new WebView();
    final WebEngine webEngine = webview.getEngine();


    private static final Pattern XML_TAG = Pattern.compile("(?<ELEMENT>(</?\\h*)(\\w+)([^<>]*)(\\h*/?>))"
            +"|(?<COMMENT><!--[^<>]+-->)");

    private static final Pattern ATTRIBUTES = Pattern.compile("(\\w+\\h*)(=)(\\h*\"[^\"]+\")");

    private static final int GROUP_OPEN_BRACKET = 2;
    private static final int GROUP_ELEMENT_NAME = 3;
    private static final int GROUP_ATTRIBUTES_SECTION = 4;
    private static final int GROUP_CLOSE_BRACKET = 5;
    private static final int GROUP_ATTRIBUTE_NAME = 1;
    private static final int GROUP_EQUAL_SYMBOL = 2;
    private static final int GROUP_ATTRIBUTE_VALUE = 3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webView = new WebView();
        webview.setPrefHeight(5);

        //svgViewerPane.setPadding(new Insets(20,20,20,20));

        svgViewerPane.widthProperty().addListener( new ChangeListener<Object>() {
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue)
            {
                Double width = (Double)newValue;
                System.out.println("Region width changed: " + width);
                webview.setPrefWidth(width);
                adjustHeight();
            }
        });


        webview.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {

            @Override
            public void changed(ObservableValue<? extends State> arg0, State oldState, State newState) {
                if (newState.equals(Worker.State.SUCCEEDED)) {
                    adjustHeight();
                }
            }
        });
        Set<Node> scrolls = svgViewerPane.lookupAll(".scroll-bar");
        for (Node scroll : scrolls) {
            scroll.setVisible(false);
        }

        Set<Node> scrolls2 = webview.lookupAll(".scroll-bar");
        for (Node scroll : scrolls) {
            scroll.setVisible(false);
        }
        // http://stackoverflow.com/questions/11206942/how-to-hide-scrollbars-in-the-javafx-webview
        webview.getChildrenUnmodifiable().addListener(new ListChangeListener<Node>() {
            @Override public void onChanged(Change<? extends Node> change) {
                Set<Node> scrolls = webview.lookupAll(".scroll-bar");
                for (Node scroll : scrolls) {
                    scroll.setVisible(false);
                }
            }
        });

        try {
            setContent(FileUtils.readFromFile(
                    Paths.get("s0.svg")
                            .toFile()).replaceAll(">", ">\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        svgViewerPane.getChildren().add(webview);
    }

    public void setContent( final String content )
    {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                webEngine.loadContent(getHtml(content));
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        adjustHeight();
                    }
                });
            }
        });
    }


    private void adjustHeight() {

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                try {
                    //"document.getElementById('mydiv').offsetHeight"
                    Object result = webEngine.executeScript(
                            "document.getElementById('mydiv').offsetHeight");
                    if (result instanceof Integer) {
                        Integer i = (Integer) result;
                        double height = new Double(i);
                        height = height;
                        webview.setPrefHeight(height);
                        System.out.println("height on state: " + height + " prefh: " + webview.getPrefHeight());
                    }
                } catch (JSException e) {
                    // not important
                }
            }
        });

    }

    private String getHtml(String content) {
        return "<html><body>" +
                "<div id=\"mydiv\">" + content + "</div>" +
                "</body></html>";
    }

    public void loadContent(String svgName, Path content){
        this.svgName = svgName;
        this.svgPath = content;
        try {
            this.svgAsString = FileUtils.readFromFile(svgPath.toFile()).replaceAll(">", ">\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSVGAsImage() {
        if (svgViewerPane.getChildren().size() == 1){
            svgViewerPane.getChildren().remove(0);
        }
        svgViewerPane.getChildren().add(webView);
        //System.out.println(FileUtils.readFromFile(n));
        webView.getEngine().loadContent(svgAsString);
        //webView.getEngine().loadContent(FileUtils.readFromFile(new File("demo1.svg") ));
        webView.setContextMenuEnabled(false);
    }

    public void showSVGAsText() {
        if (svgViewerPane.getChildren().size() == 1){
            svgViewerPane.getChildren().remove(0);
        }

        CodeArea codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        codeArea.textProperty().addListener((obs, oldText, newText) -> {
            codeArea.setStyleSpans(0, computeHighlighting(newText));
        });
        codeArea.replaceText(0, 0, svgAsString);

        codeArea.prefWidthProperty().bind(svgViewerPane.widthProperty());
        codeArea.prefHeightProperty().bind(svgViewerPane.heightProperty());

        svgViewerPane.getChildren().add(new VirtualizedScrollPane<>(codeArea));
        svgViewerPane.getStylesheets().add(SVGViewerPresenter.class.getResource("xml-highlighting.css").toExternalForm());
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {

        Matcher matcher = XML_TAG.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while(matcher.find()) {

            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            if(matcher.group("COMMENT") != null) {
                spansBuilder.add(Collections.singleton("comment"), matcher.end() - matcher.start());
            }
            else {
                if(matcher.group("ELEMENT") != null) {
                    String attributesText = matcher.group(GROUP_ATTRIBUTES_SECTION);

                    spansBuilder.add(Collections.singleton("tagmark"), matcher.end(GROUP_OPEN_BRACKET) - matcher.start(GROUP_OPEN_BRACKET));
                    spansBuilder.add(Collections.singleton("anytag"), matcher.end(GROUP_ELEMENT_NAME) - matcher.end(GROUP_OPEN_BRACKET));

                    if(!attributesText.isEmpty()) {

                        lastKwEnd = 0;

                        Matcher amatcher = ATTRIBUTES.matcher(attributesText);
                        while(amatcher.find()) {
                            spansBuilder.add(Collections.emptyList(), amatcher.start() - lastKwEnd);
                            spansBuilder.add(Collections.singleton("attribute"), amatcher.end(GROUP_ATTRIBUTE_NAME) - amatcher.start(GROUP_ATTRIBUTE_NAME));
                            spansBuilder.add(Collections.singleton("tagmark"), amatcher.end(GROUP_EQUAL_SYMBOL) - amatcher.end(GROUP_ATTRIBUTE_NAME));
                            spansBuilder.add(Collections.singleton("avalue"), amatcher.end(GROUP_ATTRIBUTE_VALUE) - amatcher.end(GROUP_EQUAL_SYMBOL));
                            lastKwEnd = amatcher.end();
                        }
                        if(attributesText.length() > lastKwEnd)
                            spansBuilder.add(Collections.emptyList(), attributesText.length() - lastKwEnd);
                    }

                    lastKwEnd = matcher.end(GROUP_ATTRIBUTES_SECTION);

                    spansBuilder.add(Collections.singleton("tagmark"), matcher.end(GROUP_CLOSE_BRACKET) - lastKwEnd);
                }
            }
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}
