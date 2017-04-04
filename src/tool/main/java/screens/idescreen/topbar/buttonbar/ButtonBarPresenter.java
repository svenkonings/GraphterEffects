package screens.idescreen.topbar.buttonbar;

import general.Model;
import general.ViewModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import loader.LoaderPresenter;
import screens.idescreen.svgviewer.SVGViewerPresenter;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ButtonBarPresenter implements Initializable {

    public SplitPane splitPane;
    @FXML private Label graafVisScriptNameLabel;
    @FXML private ComboBox graphComboBox;
    @Inject ViewModel viewModel;
    private boolean choiceBoxFilled;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File graafvisScript = general.Model.getInstance().getGraafVisFile();
        List<File> graafVisFileList = Model.getInstance().getGraphFileList();

        if (graafvisScript == null) {
            graafVisScriptNameLabel.setText("New Script");
        } else {
            graafVisScriptNameLabel.setText(graafvisScript.getName());
        }

        //graphComboBox.setTe
        for (int i = graphComboBox.getItems().size()-1; i >= 0; i++){
            graphComboBox.getItems().remove(i);
            choiceBoxFilled = false;
        }
        for (File file: graafVisFileList){
            choiceBoxFilled = true;
            graphComboBox.getItems().add(file);
        }
        if (graafVisFileList.size() > 0) {
            //TODO: Show the first option by default
        } else {
            graphComboBox.setItems(FXCollections.observableArrayList("Selecting Graph(s)"));
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

        bind();
    }

    public void bind(){
        splitPane.setStyle("-fx-background-color: #ff231a;");
        splitPane.prefWidthProperty().bind( ((Pane) (viewModel.getMainView()).getParent()).widthProperty() );
    }

    public void compileButtonPressed(ActionEvent actionEvent) {
        /*
        String code = Model.getInstance().getCodePaneTextArea().getText();
        System.out.println(code);
        List<Term> visRules = RuleGenerator.generate(code);
        System.out.println(visRules);

        int selectedIndex = graphComboBox.getSelectionModel().getSelectedIndex();
        System.out.println(graphComboBox.getItems().get(selectedIndex));
        */
        SVGViewerPresenter.SVGViewerView svgViewerView = new SVGViewerPresenter.SVGViewerView();

        //Show the svg
        ((BorderPane) viewModel.getMainView()).setCenter(svgViewerView.getView());
}


    public void menuButtonBarPushed(MouseEvent actionEvent) {
        FileChooser fileChooser = LoaderPresenter.getGraphFileChooser();
        List<File> graphFileList = fileChooser.showOpenMultipleDialog(new Stage());
        if (graphFileList != null) {
            Model.getInstance().getGraphFileList().addAll(graphFileList);
            choiceBoxFilled = true;
        }
        for (File file: graphFileList){
            graphComboBox.getItems().add(file.getName());
        }
    }

}
