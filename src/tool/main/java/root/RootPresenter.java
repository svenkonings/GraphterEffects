package root;

import general.ViewModel;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import start.StartView;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class RootPresenter implements Initializable {

    public BorderPane rootPane;
    public StackPane stackPane;

    @Inject private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rootPane.centerProperty().bind(viewModel.mainViewProperty());
        //rootPane.widthProperty().isEqualTo(1000,0);
        //rootPane.widthProperty().addListener((observable, oldValue, newValue) -> System.out.println(newValue));
        StartView view = new StartView();
        viewModel.setMainView(view.getView());

        //rootPane.widthProperty().isEqualTo(rootPane.getScene().getWindow().getWidth(), 0);
}
}
