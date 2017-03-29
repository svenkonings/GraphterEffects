package newgraafvis.newgraafvismenubar;

import general.StageHistory;
import general.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import loader.LoaderView;
import start.StartView;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuPresenter implements Initializable {

    @Inject
    private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void closeButtonPressed(ActionEvent actionEvent) {


        switch (StageHistory.getInstance().peek()){
            case "StartPresenter":
                StartView startView = new StartView();
                viewModel.setMainView(startView.getView());
                break;
            case "LoaderPresenter":
                LoaderView loaderView = new LoaderView();
                viewModel.setMainView(loaderView.getView());
        }

    }

    public void exitButtonPressed(ActionEvent actionEvent) {
        System.exit(0);
    }
}
