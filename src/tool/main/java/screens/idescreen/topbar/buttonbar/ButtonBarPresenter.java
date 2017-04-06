package screens.idescreen.topbar.buttonbar;

import general.CompilerUtils;
import general.ViewModel;
import general.files.DocumentModel;
import general.files.DocumentModelChange;
import general.files.LoaderUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import utils.Pair;

import javax.inject.Inject;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class ButtonBarPresenter implements Initializable, Observer {

    public SplitPane splitPane;
    @FXML private Label graafVisScriptNameLabel;
    @FXML private ComboBox graphComboBox;
    @Inject ViewModel viewModel;
    private boolean choiceBoxFilled;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path graafvisScriptPath = DocumentModel.getInstance().getGraafVisFilePath();
        Map<String,Path> graphPathMap = DocumentModel.getInstance().getGraphPathMap();

        if (graafvisScriptPath == null) {
            graafVisScriptNameLabel.setText("New Script");
        } else {
            graafVisScriptNameLabel.setText(graafvisScriptPath.getFileName().toString());
        }

        //graphComboBox.setTe
        for (int i = graphComboBox.getItems().size()-1; i >= 0; i++){
            graphComboBox.getItems().remove(i);
            choiceBoxFilled = false;
        }
        for (String name: graphPathMap.keySet()){
            choiceBoxFilled = true;
            graphComboBox.getItems().add(name);
        }
        if (graphPathMap.keySet().size() > 0) {
            graphComboBox.getSelectionModel().select(0);
        } else {
            graphComboBox.setItems(FXCollections.observableArrayList());
        }

        graphComboBox.setPromptText("Select graphs");


        graphComboBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!choiceBoxFilled) {
                    menuButtonBarPushed(event);
                }
            }
        });

        DocumentModel.getInstance().addObserver(this);
        bind();
    }

    public void bind(){
        splitPane.setStyle("-fx-background-color: #ff231a;");
        splitPane.prefWidthProperty().bind( ((Pane) (viewModel.getMainView()).getParent()).widthProperty() );
    }

    public void compileButtonPressed(ActionEvent actionEvent) {


        Path graphFilePath = DocumentModel.getInstance().getGraphPathMap().get(graphComboBox.getSelectionModel().getSelectedItem());
        Path scriptFilePath = DocumentModel.getInstance().getGraafVisFilePath();


        try {
            CompilerUtils.compile(scriptFilePath, graphFilePath);
        } catch (Exception e){
            e.printStackTrace();
            //TODO: Handle exceptions by showing them in an error box
        }

    }


    public void menuButtonBarPushed(MouseEvent actionEvent) {
        if (!choiceBoxFilled){
            LoaderUtils.showLoadGraphsPopup(false);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Pair<DocumentModelChange, Object> arguments = (Pair) arg;
        DocumentModelChange documentModelChange = (DocumentModelChange) arguments.get(0);
        switch (documentModelChange) {
            case GRAPHFILELOADED:
                Map<String,Path> graphFileList = DocumentModel.getInstance().getGraphPathMap();
                int indexSelected = graphComboBox.getSelectionModel().getSelectedIndex();
                graphComboBox.setItems(FXCollections.observableArrayList());
                if (graphFileList.size() > 0) {
                    choiceBoxFilled = true;
                }
                for (String name : graphFileList.keySet()) {
                        graphComboBox.getItems().add(name);
                }
                //When nothing is selected, select the first item from the list.
                if (indexSelected == -1){
                    indexSelected = 0;
                }
                graphComboBox.getSelectionModel().select(indexSelected);
                //TODO: Make disctinction between all new files and one extra file
                break;
            case GRAAFVISFILELOADED:
                Path script = DocumentModel.getInstance().getGraafVisFilePath();
                graafVisScriptNameLabel.setText(script.getFileName().toString());
                break;
            default:
                break;
        }
    }
}
