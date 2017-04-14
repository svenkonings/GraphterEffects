package root;

import general.ViewModel;
import general.files.DocumentModel;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import screens.idescreen.IDEView;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class RootPresenter implements Initializable {

    public StackPane rootPane;
    @Inject
    private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        viewModel.setMainView(rootPane);
        //rootPane.centerProperty().bind(viewModel.mainViewProperty());
        //rootPane.widthProperty().isEqualTo(1000,0);
        //rootPane.widthProperty().addListener((observable, oldValue, newValue) -> System.out.println(newValue));
        //StartView view = new StartView();
        //viewModel.setMainView(view.getView());

        //rootPane.widthProperty().isEqualTo(rootPane.getScene().getWindow().getWidth(), 0);
    }

    public void loadIDE() {
        DocumentModel.getInstance().newGraafVisFile();
        screens.idescreen.IDEView IDEView = new IDEView();
        rootPane.getChildren().add(IDEView.getView());
        //rootPane.wid
}
}
