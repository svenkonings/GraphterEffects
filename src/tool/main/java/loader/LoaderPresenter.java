package loader;

import general.Model;
import general.StageHistory;
import general.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import screens.compilescreen.CompileView;
import screens.idescreen.IDEView;

import javax.inject.Inject;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoaderPresenter implements Initializable{

    public Label scriptURLLabel;
    public Label graphUrlLabel;

    public static FileChooser.ExtensionFilter allFilesFilter = new FileChooser.ExtensionFilter("All files", "*");;
    public static FileChooser.ExtensionFilter visFilesFilter = new FileChooser.ExtensionFilter("VIS files (*.vis)", "*.VIS", "*.vis");;
    public static FileChooser.ExtensionFilter graphFilesFilter = new FileChooser.ExtensionFilter("Graph files (*.dot, ... )", "*.DOT", "*.dot");;
    public static FileChooser.ExtensionFilter dotFilesFilter = new FileChooser.ExtensionFilter("DOT files (*.dot)", "*.DOT", "*.dot");

    @Inject
    private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageHistory.getInstance().setCurrentStage(LoaderPresenter.class.getSimpleName());

        allFilesFilter = new FileChooser.ExtensionFilter("All files", "*");

        visFilesFilter = new FileChooser.ExtensionFilter("VIS files (*.vis)", "*.VIS", "*.vis");

        graphFilesFilter = new FileChooser.ExtensionFilter("Graph files (*.dot, ... )", "*.DOT", "*.dot");
        dotFilesFilter = new FileChooser.ExtensionFilter("Graph files (*.dot)", "*.DOT", "*.dot");
    }

    public void loadScriptButtonPushed(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select GraafVis Script");
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        path += "/tests";
        fileChooser.setInitialDirectory(new File(path));

        fileChooser.getExtensionFilters().add(visFilesFilter);
        fileChooser.getExtensionFilters().add(allFilesFilter);
        //fileChooser.getExtensionFilters().add(FileChooser.ExtensionF)

        File script = fileChooser.showOpenDialog(new Stage());
        if (script != null) {
            Model.getInstance().setGraafVisFile(script);
            scriptURLLabel.setText(script.getName());
        }
    }

    public void loadGraphButtonPushed(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Graph(s)");
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        path += "/tests";
        fileChooser.setInitialDirectory(new File(path));

        fileChooser.getExtensionFilters().add(graphFilesFilter);
        fileChooser.getExtensionFilters().add(dotFilesFilter);
        fileChooser.getExtensionFilters().add(allFilesFilter);

        List<File> graphFileList = fileChooser.showOpenMultipleDialog(new Stage());
        if (graphFileList != null) {
            Model.getInstance().setGraphFileList(graphFileList);

            if (graphFileList.size() == 1) {
                graphUrlLabel.setText(graphFileList.get(0).getName());
            } else {
                graphUrlLabel.setText(graphFileList.size() + " graphs selected");
            }
        }
    }

    public static FileChooser getGraphFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Graph(s)");
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        path += "/tests";
        fileChooser.setInitialDirectory(new File(path));

        fileChooser.getExtensionFilters().add(allFilesFilter);
        fileChooser.getExtensionFilters().add(graphFilesFilter);
        fileChooser.getExtensionFilters().add(dotFilesFilter);

        return fileChooser;
    }

    public static FileChooser getVisFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select GraafVis Script");
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        path += "/tests";
        fileChooser.setInitialDirectory(new File(path));

        fileChooser.getExtensionFilters().add(visFilesFilter);
        fileChooser.getExtensionFilters().add(allFilesFilter);

        return fileChooser;
    }

    public void newScriptButtonPushed(ActionEvent actionEvent) {
        IDEView IDEView = new IDEView();
        viewModel.setMainView(IDEView.getView());
    }

    public void newGraphButtonPushed(ActionEvent actionEvent) {
        //IDEView newGraafvisView = new IDEView();
        //viewModel.setMainView(newGraafvisView.getView());
    }

    public void compileButtonPushed(ActionEvent actionEvent) {
        CompileView compileView = new CompileView();
        viewModel.setMainView(compileView.getView());
    }
}
