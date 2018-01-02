package com.github.meteoorkip.screens.idescreen.tab.simpleviewer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SimpleViewerPresenter implements Initializable {

    @FXML public AnchorPane simpleViewerPane;
    private String contentName;
    private String content;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void loadContent(String contentName, String content){
        this.contentName = contentName;
        this.content= content;
        showContentasText();
    }

    public void showContentasText() {
        if (simpleViewerPane.getChildren().size() == 1){
            simpleViewerPane.getChildren().remove(0);
        }

        CodeArea codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.replaceText(0, 0, content);

        codeArea.setEditable(false);

        codeArea.prefWidthProperty().bind(simpleViewerPane.widthProperty());
        codeArea.prefHeightProperty().bind(simpleViewerPane.heightProperty());

        simpleViewerPane.getChildren().add(new VirtualizedScrollPane<>(codeArea));
    }

}
