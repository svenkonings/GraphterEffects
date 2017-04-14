package screens.idescreen.topbar.menubar;

import general.ViewModel;
import general.files.DocumentModel;
import general.files.IOManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import java.util.Set;

public class MenuPresenter implements Initializable {

    public Pane borderPane;
    public MenuBar menuBar;
    @Inject
    private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bind();
        ((Pane) (viewModel.getMainView())).setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.F5) {
                    System.out.println("F5 pressed");
                    //Stop letting it do anything else
                    keyEvent.consume();
                }
            }
        });
    }

    public void bind(){
        borderPane.prefWidthProperty().bind( ((Pane) (viewModel.getMainView()).getParent()).widthProperty() );
        menuBar.prefWidthProperty().bind(borderPane.prefWidthProperty());
    }

    public void exitButtonPressed(ActionEvent actionEvent) {
        if (!DocumentModel.getInstance().graafvisChangesSaved()){
            if(!IOManager.showGraafvisScriptSaveDialog(DocumentModel.getInstance().getGraafVisFilePath(), DocumentModel.getInstance().graafVisCode)){
                actionEvent.consume();
                return;
            }
        }
        Set<String> svgNames = DocumentModel.getInstance().getAllGeneratedSVGS().keySet();
        for (String svgName: svgNames){
            if(IOManager.showSVGSaveDialog(DocumentModel.getInstance().getGeneratedSVG(svgName))){
                DocumentModel.getInstance().getAllGeneratedSVGS().remove(svgName);
            } else {
                actionEvent.consume();
                return;
            }
        }
        System.exit(0);
    }

    public void loadScriptButtonPressed(ActionEvent actionEvent) {
        if (!DocumentModel.getInstance().graafvisChangesSaved()){
            if(IOManager.showGraafvisScriptSaveDialog(DocumentModel.getInstance().getGraafVisFilePath(), DocumentModel.getInstance().graafVisCode)){
                IOManager.showLoadScriptPopup();
            }
        } else {
            IOManager.showLoadScriptPopup();
        }


    }

    public void newScriptButtonPressed(ActionEvent actionEvent) {
        if (!DocumentModel.getInstance().graafvisChangesSaved()){
            if(IOManager.showGraafvisScriptSaveDialog(DocumentModel.getInstance().getGraafVisFilePath(), DocumentModel.getInstance().graafVisCode)){
                DocumentModel.getInstance().newGraafVisFile();
            }
        } else {
            DocumentModel.getInstance().newGraafVisFile();
        }
        DocumentModel.getInstance().newGraafVisFile();
    }

    public void saveButtonPressed(ActionEvent actionEvent) {
        //IOManager.showSaveScriptPopup(Model.getInstance().getCodePaneTextArea().getText());
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
            IOManager.showSaveSVGPopup(DocumentModel.getInstance().getGeneratedSVG(viewerTabID));
        }
    }

    public void propertiesButtonPressed(ActionEvent actionEvent) {
    }

    public void loadGraphButtonPressed(ActionEvent actionEvent) {
        IOManager.showLoadGraphsPopup(false);
    }

    public void saveAsButtonPressed(ActionEvent actionEvent) {
        TabPane tabPane = (TabPane) (((BorderPane) (viewModel.getMainView())).getCenter());
        Tab viewerTab = tabPane.getSelectionModel().getSelectedItem();
        String viewerTabID = viewerTab.getText();

        if (tabPane.getSelectionModel().getSelectedIndex() == 0){
            Path codePath = DocumentModel.getInstance().getGraafVisFilePath();
            String code = DocumentModel.getInstance().graafVisCode;
            IOManager.showSaveScriptPopup(codePath, code);
        } else {
            IOManager.showSaveSVGPopup(DocumentModel.getInstance().getGeneratedSVG(viewerTabID));
        }
    }
}
