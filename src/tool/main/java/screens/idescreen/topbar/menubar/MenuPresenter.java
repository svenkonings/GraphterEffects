package screens.idescreen.topbar.menubar;

import general.files.DocumentModel;
import general.files.LoaderUtils;
import general.Model;
import general.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuPresenter implements Initializable {

    public Pane borderPane;
    @Inject
    private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bind();
    }

    public void bind(){
        borderPane.prefWidthProperty().bind( ((Pane) (viewModel.getMainView()).getParent()).widthProperty() );
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
        LoaderUtils.showSaveScriptPopup(Model.getInstance().getCodePaneTextArea().getText());
    }

    public void propertiesButtonPressed(ActionEvent actionEvent) {
    }

    public void loadGraphButtonPressed(ActionEvent actionEvent) {
        LoaderUtils.showLoadGraphsPopup(false);
    }
}
