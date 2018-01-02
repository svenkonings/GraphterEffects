package com.github.meteoorkip.screens.idescreen.topbar;

import com.github.meteoorkip.general.ViewModel;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import com.github.meteoorkip.screens.idescreen.topbar.buttonbar.ButtonBarView;
import com.github.meteoorkip.screens.idescreen.topbar.menubar.MenuBarView;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class TopBarPresenter implements Initializable{

    @Inject ViewModel viewModel;
    public SplitPane splitPane;
    public AnchorPane topAnchorPane;
    public AnchorPane bottomAnchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MenuBarView menuBarView = new MenuBarView();
        ButtonBarView buttonBarView = new ButtonBarView();

        splitPane.setDividerPosition(1,splitPane.getHeight() - splitPane.getInsets().getBottom());
        SplitPane.setResizableWithParent(topAnchorPane, Boolean.FALSE);
        SplitPane.setResizableWithParent(bottomAnchorPane, Boolean.FALSE);

        splitPane.setPrefHeight(65);
        splitPane.setMinHeight(65);
        topAnchorPane.setPrefHeight(25);
        topAnchorPane.setMinHeight(25);
        bottomAnchorPane.setPrefHeight(35);
        bottomAnchorPane.setMinHeight(35);

        topAnchorPane.getChildren().add(menuBarView.getView());
        bottomAnchorPane.getChildren().add(buttonBarView.getView());

        bind();
    }

    public void bind() {
        splitPane.prefWidthProperty().bind(viewModel.sceneWidthProperty());
    }
}
