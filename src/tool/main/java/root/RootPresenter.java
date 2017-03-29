package root;

import general.ViewModel;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import start.StartView;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class RootPresenter implements Initializable {

    public BorderPane rootPane;

    @Inject private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rootPane.centerProperty().bind(viewModel.mainViewProperty());
        StartView view = new StartView();
        viewModel.setMainView(view.getView());
    }
}
