package screens.idescreen.topbar.menubar;

import general.StageHistory;
import general.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import loader.LoaderView;
import screens.compileerrorscreen.CompileErrorView;
import start.StartView;

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


    public void closeButtonPressed(ActionEvent actionEvent) {


        switch (StageHistory.getInstance().peek()){
            case "StartPresenter":
                StartView startView = new StartView();
                viewModel.setMainView(startView.getView());
                break;
            case "LoaderPresenter":
                LoaderView loaderView = new LoaderView();
                viewModel.setMainView(loaderView.getView());
                break;
            case "CompileErrorPresenter":
                CompileErrorView compileErrorView = new CompileErrorView();
                viewModel.setMainView(compileErrorView.getView());
                break;
            default:
                throw new IllegalStateException();
        }

    }

    public void exitButtonPressed(ActionEvent actionEvent) {
        System.exit(0);
    }
}
