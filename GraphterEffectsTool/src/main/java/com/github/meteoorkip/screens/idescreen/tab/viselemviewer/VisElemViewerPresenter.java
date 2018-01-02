package com.github.meteoorkip.screens.idescreen.tab.viselemviewer;

import com.github.meteoorkip.solver.VisElem;
import com.github.meteoorkip.solver.VisMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.*;

public class VisElemViewerPresenter implements Initializable {

    @FXML
    public TreeView visElemsView;
    public Button filterButton;
    public TextField filterarguments;
    public StackPane visElemPane;
    public TitledPane titledPane;
    private VisMap visMap;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titledPane.prefWidthProperty().bind(visElemPane.widthProperty());
        titledPane.prefHeightProperty().bind(visElemPane.heightProperty());
        titledPane.maxWidthProperty().bind(visElemPane.widthProperty());
    }

    public void loadContent(VisMap visMap) {
        this.visMap = visMap;
        showContentInTreeView();
    }

    public void showContentInTreeView(){
        showContentInTreeView(new HashSet<>());
    }

    public void showContentInTreeView(Set<String> filterArgs) {
        TreeItem<String> rootItem = new TreeItem<>("Visualization Elements");
        rootItem.setExpanded(true);
        Map<String, VisElem> visElemMap = visMap.getMapping();

        for (String atom : visElemMap.keySet()) {
            TreeItem visElemTreeItem = new TreeItem<>("Element " + atom);
            VisElem visElem = visElemMap.get(atom);
            for (String key : visElem.getValues().keySet())
                if (filterArgs.size() != 0) {
                    for (String filterArg : filterArgs) {
                        if (key.toLowerCase().contains(filterArg.toLowerCase())) {
                            visElemTreeItem.getChildren().add(new TreeItem<>(key + ": " + visElem.getValues().get(key)));
                        }
                    }
                } else {
                    visElemTreeItem.getChildren().add(new TreeItem<>(key + ": " + visElem.getValues().get(key)));
                }
            rootItem.getChildren().add(visElemTreeItem);
        }
        visElemsView.setRoot(rootItem);
    }

    public void filterButtonPressed(ActionEvent actionEvent) {
        Set<String> keySet = new HashSet();
        Collections.addAll(keySet, filterarguments.getText().split(","));
        keySet.remove(""); //Because this will always be added.
        showContentInTreeView(keySet);
    }
}
