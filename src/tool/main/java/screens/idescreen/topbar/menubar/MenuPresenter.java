package screens.idescreen.topbar.menubar;

import general.ViewModel;
import general.files.DocumentModel;
import general.files.LoaderUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuPresenter implements Initializable {

    public Pane borderPane;
    public MenuBar menuBar;
    @Inject
    private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bind();
    }

    public void bind(){
        borderPane.prefWidthProperty().bind( ((Pane) (viewModel.getMainView()).getParent()).widthProperty() );
        menuBar.prefWidthProperty().bind(borderPane.prefWidthProperty());
    }

    public void exitButtonPressed(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void loadScriptButtonPressed(ActionEvent actionEvent) {
        LoaderUtils.showLoadScriptPopup();
    }

    public void newScriptButtonPressed(ActionEvent actionEvent) {
        DocumentModel.getInstance().newGraafVisFile();
    }

    public void saveButtonPressed(ActionEvent actionEvent) {
        //LoaderUtils.showSaveScriptPopup(Model.getInstance().getCodePaneTextArea().getText());
        TabPane tabPane = (TabPane) (((BorderPane) (viewModel.getMainView())).getCenter());
        Tab viewerTab = tabPane.getSelectionModel().getSelectedItem();
        String viewerTabID = viewerTab.getText();

        if (viewerTabID.split("\\.")[1].equals("vis")){
            Path codePath = DocumentModel.getInstance().getGraafVisFilePath();

            String code = DocumentModel.getInstance().graafVisCode;
            List<String> codeList = new ArrayList<>();
            codeList.add(code);
            try {
                Files.write(codePath, codeList, Charset.forName("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LoaderUtils.showSaveSVGPopup(DocumentModel.getInstance().getGeneratedSVG(viewerTabID));
        }
    }

    public void propertiesButtonPressed(ActionEvent actionEvent) {
    }

    public void loadGraphButtonPressed(ActionEvent actionEvent) {
        LoaderUtils.showLoadGraphsPopup(false);
    }

    public void saveAsButtonPressed(ActionEvent actionEvent) {
        TabPane tabPane = (TabPane) (((BorderPane) (viewModel.getMainView())).getCenter());
        Tab viewerTab = tabPane.getSelectionModel().getSelectedItem();
        String viewerTabID = viewerTab.getText();

        if (viewerTabID.split("\\.").length == 2 && viewerTabID.split("\\.")[1].equals("vis")){
            Path codePath = DocumentModel.getInstance().getGraafVisFilePath();
            String code = DocumentModel.getInstance().graafVisCode;
            LoaderUtils.showSaveScriptPopup(codePath, code);
        } else {
            LoaderUtils.showSaveSVGPopup(DocumentModel.getInstance().getGeneratedSVG(viewerTabID));
        }
    }
}
