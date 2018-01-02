package com.github.meteoorkip.screens.idescreen.topbar.menubar;

import com.github.meteoorkip.general.ViewModel;
import com.github.meteoorkip.general.generation.GeneratorRunnable;
import com.github.meteoorkip.general.files.FileModel;
import com.github.meteoorkip.general.files.IOManager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import com.github.meteoorkip.screens.idescreen.about.AboutView;
import com.github.meteoorkip.screens.idescreen.bottombar.BottomBarPresenter;
import com.github.meteoorkip.screens.idescreen.tab.simpleviewer.SimpleViewerPresenter;
import com.github.meteoorkip.screens.idescreen.tab.simpleviewer.SimpleViewerView;
import com.github.meteoorkip.utils.FileUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuBarPresenter implements Initializable {

    public Pane borderPane;
    public MenuBar menuBar;
    @Inject
    private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bind();
    }

    public void bind(){
        borderPane.prefWidthProperty().bind( viewModel.sceneWidthProperty() );
        menuBar.prefWidthProperty().bind(borderPane.prefWidthProperty());
    }

    public void exitMenuItemPressed(ActionEvent actionEvent) {
        borderPane.fireEvent(
                new WindowEvent(
                        borderPane.getScene().getWindow(),
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );
    }

    public void loadScriptMenuItemPressed(ActionEvent actionEvent) {
        if (!FileModel.getInstance().graafvisChangesSaved()){
            if(IOManager.showGraafvisScriptSaveDialog(FileModel.getInstance().getGraafVisFilePath(), FileModel.getInstance().graafVisCode)){
                IOManager.showLoadScriptPopup();
            }
        } else {
            IOManager.showLoadScriptPopup();
        }


    }

    public void newScriptMenuItemPressed(ActionEvent actionEvent) {
        if (!FileModel.getInstance().graafvisChangesSaved()){
            if(IOManager.showGraafvisScriptSaveDialog(FileModel.getInstance().getGraafVisFilePath(), FileModel.getInstance().graafVisCode)){
                FileModel.getInstance().newGraafVisFile();
            }
        } else {
            FileModel.getInstance().newGraafVisFile();
        }
        FileModel.getInstance().newGraafVisFile();
    }

    public void saveMenuItemPressed(ActionEvent actionEvent) {
        //IOManager.showSaveScriptPopup(Model.getInstance().getCodePaneTextArea().getText());
        TabPane tabPane = (TabPane) (((BorderPane)((StackPane) (viewModel.getMainView())).getChildren().get(0)).getCenter());
        Tab viewerTab = tabPane.getSelectionModel().getSelectedItem();
        String viewerTabID = viewerTab.getText();
        if (viewerTabID.endsWith(" *")) {
            viewerTabID = viewerTabID.substring(0, viewerTabID.length()-2);
        }

        if (viewerTabID.endsWith(".vis")){
            Path codePath = FileModel.getInstance().getGraafVisFilePath();
            String code = FileModel.getInstance().graafVisCode;
            List<String> codeList = new ArrayList<>();
            codeList.add(code);
            try {
                Files.write(codePath, codeList, Charset.forName("UTF-8"));
                FileModel.getInstance().loadGraafVisFile(codePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            IOManager.showSaveSVGPopup(FileModel.getInstance().getGeneratedSVG(viewerTabID));
        }
    }

    public void loadGraphMenuItemPressed(ActionEvent actionEvent) {
        IOManager.showLoadGraphsPopup(false);
    }

    public void saveAsMenuItemPressed(ActionEvent actionEvent) {
        TabPane tabPane = (TabPane) (((BorderPane) ((StackPane)viewModel.getMainView()).getChildren().get(0)).getCenter());
        Tab viewerTab = tabPane.getSelectionModel().getSelectedItem();
        String viewerTabID = viewerTab.getText();

        if (tabPane.getSelectionModel().getSelectedIndex() == 0){
            Path codePath = FileModel.getInstance().getGraafVisFilePath();
            String code = FileModel.getInstance().graafVisCode;
            IOManager.showSaveScriptPopup(codePath, code);
        } else {
            IOManager.showSaveSVGPopup(FileModel.getInstance().getGeneratedSVG(viewerTabID));
        }
    }

    public void graphAsImageMenuItemPressed(ActionEvent actionEvent) {
        BorderPane centralBorder = ((BorderPane) (borderPane.getParent().getParent().getParent()).getParent());
        SplitPane topBar = (SplitPane) centralBorder.getTop();
        ButtonBar buttonBar = (ButtonBar) ((SplitPane) ((AnchorPane) topBar.getItems().get(1)).getChildren().get(0)).getItems().get(1);
        ComboBox comboBox = (ComboBox) buttonBar.getButtons().get(0);
        if (comboBox.getSelectionModel().getSelectedItem()==null) {
            BottomBarPresenter.addText("No graph selected.");
            return;
        }
        String selectedGraphName = comboBox.getSelectionModel().getSelectedItem().toString();
        Path selectedGraphPath = FileModel.getInstance().getGraphPathMap().get(selectedGraphName);
        GeneratorRunnable generatorRunnable = new GeneratorRunnable(Paths.get(getClass().getClassLoader().getResource("defaultvisualization.vis").getFile().substring(1)), selectedGraphPath);
        new Thread(generatorRunnable).start();
    }

    public void graphAsTextMenuItemPressed(ActionEvent actionEvent) {
        BorderPane centralBorder = ((BorderPane) (borderPane.getParent().getParent().getParent()).getParent());
        SplitPane topBar = (SplitPane) centralBorder.getTop();
        ButtonBar buttonBar = (ButtonBar) ((SplitPane) ((AnchorPane) topBar.getItems().get(1)).getChildren().get(0)).getItems().get(1);
        ComboBox comboBox = (ComboBox) buttonBar.getButtons().get(0);
        if (comboBox.getSelectionModel().getSelectedItem()==null) {
            BottomBarPresenter.addText("No graph selected.");
            return;
        }
        String selectedGraphName = comboBox.getSelectionModel().getSelectedItem().toString();

        Path selectedGraphPath = FileModel.getInstance().getGraphPathMap().get(selectedGraphName);
        String graphAsString = "";
        try {
            graphAsString = FileUtils.readFromFile(selectedGraphPath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        TabPane centralTabPane = (TabPane) centralBorder.getCenter();
        SimpleViewerView simpleViewerView = new SimpleViewerView();
        SimpleViewerPresenter simpleViewerPresenter = (SimpleViewerPresenter) simpleViewerView.getPresenter();
        simpleViewerPresenter.loadContent(selectedGraphName, graphAsString);
        centralTabPane.getTabs().add(new Tab(selectedGraphName, simpleViewerView.getView()));
        centralTabPane.getSelectionModel().select(centralTabPane.getTabs().size()-1);
    }

    public void undoChangesMenutemPressed(ActionEvent actionEvent) {
        Path graafvisScriptPath = FileModel.getInstance().getGraafVisFilePath();
        if (!FileModel.getInstance().graafvisChangesSaved()) {
            if (IOManager.showGraafvisScriptSaveDialog(graafvisScriptPath, FileModel.getInstance().graafVisCode)) {
                FileModel.getInstance().loadGraafVisFile(graafvisScriptPath);
            }
        }
    }

    public void helpMenuItemPressed(ActionEvent actionEvent) {
        AboutView aboutView = new AboutView();
        Scene scene = new Scene(aboutView.getView());
        Stage aboutDialog = new Stage();
        aboutDialog.setScene(scene);
        aboutDialog.show();
    }
}
