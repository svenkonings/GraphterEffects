package start;

import general.files.DocumentModel;
import general.StageHistory;
import general.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import loader.LoaderView;
import screens.idescreen.IDEView;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class StartPresenter implements Initializable {

    @Inject private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageHistory.getInstance().setCurrentStage(StartPresenter.class.getSimpleName());
    }

    public void start() {

    }

    public void createNewGraafVisButtonPressed(ActionEvent actionEvent) {
        StageHistory.getInstance().setCurrentStage(this.getClass().getSimpleName());
        DocumentModel.getInstance().newGraafVisFile();
        IDEView IDEView = new IDEView();
        viewModel.setMainView(IDEView.getView());
    }

    public void loadGGButtonPressed(ActionEvent actionEvent) {
        LoaderView loaderView = new LoaderView();
        viewModel.setMainView(loaderView.getView());
    }
}
