package screens.compileerrorscreen;

import screens.compilescreen.CompileView;
import general.StageHistory;
import general.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import screens.idescreen.IDEView;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class CompileErrorPresenter implements Initializable{


    @Inject
    private ViewModel viewModel;

    @Inject
    private String error;

    public TextArea errorTextArea;
    public TextField errorTextField; //TODO: Change the size of this with more

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageHistory.getInstance().setCurrentStage(CompileErrorPresenter.class.getSimpleName());
        errorTextArea.setVisible(false);
        setError("Error while " + error);
    }

    public void setError(String errormessage ){
        errorTextArea.setText(errormessage);
        errorTextField.setText(errormessage);
    }

    public void moreButtonPushed(ActionEvent actionEvent) {
        errorTextField.setVisible(!errorTextField.isVisible());
        errorTextArea.setVisible(!errorTextArea.isVisible());
    }

    public void recompileButtonPressed(ActionEvent actionEvent) {
        CompileView compileView = new CompileView();
        viewModel.setMainView(compileView.getView());
    }

    public void editButtonPressed(ActionEvent actionEvent) {
        IDEView IDEView = new IDEView();
        viewModel.setMainView(IDEView.getView());
    }

    public void inspectButtonPressed(ActionEvent actionEvent) {
    }

}
