package loader;

import general.StageHistory;
import general.ViewModel;
import general.files.DocumentModel;
import general.files.LoaderUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import screens.compilescreen.CompileView;
import screens.idescreen.IDEView;

import javax.inject.Inject;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;
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

        Path script = DocumentModel.getInstance().getGraafVisFilePath();
        if (script != null) {
            scriptURLLabel.setText(script.getFileName().toString());
        }
    }

    public void loadGraphButtonPushed(ActionEvent actionEvent) {
        LoaderUtils.showLoadGraphsPopup(true);

        Map<String,Path> graphPathMap = DocumentModel.getInstance().getGraphPathMap();
        if (graphPathMap != null) {
            if (graphPathMap.size() == 1) {
                for (String key: graphPathMap.keySet()) {
                    graphUrlLabel.setText(graphPathMap.get(key).getFileName().toString());
                }
            } else {
                graphUrlLabel.setText(graphPathMap.size() + " graphs selected");
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
