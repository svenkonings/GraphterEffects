package loader;

import general.DocumentModel;
import general.LoaderUtils;
import general.StageHistory;
import general.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import screens.compilescreen.CompileView;
import screens.idescreen.IDEView;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoaderPresenter implements Initializable{

    public Label scriptURLLabel;
    public Label graphUrlLabel;



    @Inject
    private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageHistory.getInstance().setCurrentStage(LoaderPresenter.class.getSimpleName());;
    }

    public void loadScriptButtonPushed(ActionEvent actionEvent) {
        LoaderUtils.showLoadScriptPopup();

        File script = DocumentModel.getInstance().getGraafVisFile();
        if (script != null) {
            scriptURLLabel.setText(script.getName());
        }
    }

    public void loadGraphButtonPushed(ActionEvent actionEvent) {
        LoaderUtils.showLoadGraphsPopup(true);

        List<File> graphFileList = DocumentModel.getInstance().getGraphFileList();
        if (graphFileList != null) {
            if (graphFileList.size() == 1) {
                graphUrlLabel.setText(graphFileList.get(0).getName());
            } else {
                graphUrlLabel.setText(graphFileList.size() + " graphs selected");
            }
        }
    }

    public void newScriptButtonPushed(ActionEvent actionEvent) {
        IDEView IDEView = new IDEView();
        viewModel.setMainView(IDEView.getView());
    }

    public void newGraphButtonPushed(ActionEvent actionEvent) {

    }

    public void compileButtonPushed(ActionEvent actionEvent) {
        CompileView compileView = new CompileView();
        viewModel.setMainView(compileView.getView());
    }


}
