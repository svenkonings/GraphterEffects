package start;

import general.StageHistory;
import general.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import loader.LoaderView;
import newgraafvis.NewGraafvisView;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class StartPresenter implements Initializable {

    @Inject private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }

    public void start() {

    }

    public void createNewGraafVisButtonPressed(ActionEvent actionEvent) {
        StageHistory.getInstance().add(this.getClass().getSimpleName());
        NewGraafvisView newGraafvisView = new NewGraafvisView();
        viewModel.setMainView(newGraafvisView.getView());
    }

    public void loadGGButtonPressed(ActionEvent actionEvent) {
        LoaderView loaderView = new LoaderView();
        viewModel.setMainView(loaderView.getView());
    }
}
