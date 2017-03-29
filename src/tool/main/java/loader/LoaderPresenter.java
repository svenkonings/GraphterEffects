package loader;

import general.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import newgraafvis.NewGraafvisView;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class LoaderPresenter implements Initializable{

    public Label scriptURLLabel;
    public Label graphUrlLabel;

    FileChooser.ExtensionFilter allFilesFilter;
    FileChooser.ExtensionFilter visFilesFilter;
    FileChooser.ExtensionFilter graphFilesFilter;
    FileChooser.ExtensionFilter dotFilesFilter;

    @Inject
    private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allFilesFilter = new FileChooser.ExtensionFilter("All files", "*");

        visFilesFilter = new FileChooser.ExtensionFilter("VIS files (*.vis)", "*.VIS", "*.vis");

        graphFilesFilter = new FileChooser.ExtensionFilter("Graph files (*.dot", "*.DOT", "*.dot");
        dotFilesFilter = new FileChooser.ExtensionFilter("Graph files (*.dot", "*.DOT", "*.dot");
    }

    public void loadScriptButtonPushed(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select GraafVis Script");

        fileChooser.getExtensionFilters().add(visFilesFilter);
        fileChooser.getExtensionFilters().add(allFilesFilter);
        //fileChooser.getExtensionFilters().add(FileChooser.ExtensionF)

        File file = fileChooser.showOpenDialog(new Stage());
        scriptURLLabel.setText(file.getName());
    }

    public void loadGraphButtonPushed(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Graph");

        fileChooser.getExtensionFilters().add(graphFilesFilter);
        fileChooser.getExtensionFilters().add(dotFilesFilter);
        fileChooser.getExtensionFilters().add(allFilesFilter);

        File file = fileChooser.showOpenDialog(new Stage());
        graphUrlLabel.setText(file.getName());

    }

    public void newScriptButtonPushed(ActionEvent actionEvent) {
        NewGraafvisView newGraafvisView = new NewGraafvisView();
        viewModel.setMainView(newGraafvisView.getView());
    }

    public void newGraphButtonPushed(ActionEvent actionEvent) {
        //NewGraafvisView newGraafvisView = new NewGraafvisView();
        //viewModel.setMainView(newGraafvisView.getView());
    }
}
