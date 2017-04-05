package screens.idescreen.topbar.buttonbar;

import alice.tuprolog.InvalidTheoryException;
import exceptions.UnknownGraphTypeException;
import general.CompilerUtils;
import general.DocumentModel;
import general.LoaderUtils;
import general.ViewModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.dom4j.Document;
import screens.idescreen.svgviewer.SVGViewerPresenter;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ButtonBarPresenter implements Initializable, Observer {

    public SplitPane splitPane;
    @FXML private Label graafVisScriptNameLabel;
    @FXML private ComboBox graphComboBox;
    @Inject ViewModel viewModel;
    private boolean choiceBoxFilled;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File graafvisScript = DocumentModel.getInstance().getGraafVisFile();
        List<File> graafVisFileList = DocumentModel.getInstance().getGraphFileList();

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


        File graphFile = DocumentModel.getInstance().getGraphFileList().get(graphComboBox.getSelectionModel().getSelectedIndex());
        File scriptFile = DocumentModel.getInstance().getGraafVisFile();
        try {
            Document generatedSVG = CompilerUtils.compile(scriptFile, graphFile);
            DocumentModel.getInstance().setGeneratedSVG(generatedSVG);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnknownGraphTypeException e) {
            e.printStackTrace();
        } catch (InvalidTheoryException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }

        String svgxml = DocumentModel.getInstance().getGeneratedSVG().asXML();
        List<String> svgxmltext = new ArrayList<>();
        svgxmltext.add(svgxml);


        Path file = Paths.get("temp3.svg");
        try {
        Files.write(file, svgxmltext, Charset.forName("UTF-8"));
        } catch (IOException e) {
          e.printStackTrace();
        }


        SVGViewerPresenter.SVGViewerView svgViewerView = new SVGViewerPresenter.SVGViewerView();

        //Show the svg
        BorderPane borderPane = ((BorderPane) viewModel.getMainView());
        TabPane tabPane = (TabPane) borderPane.getCenter();
        tabPane.getTabs().add(new Tab("Name Visualization", svgViewerView.getView()));

    }


    public void menuButtonBarPushed(MouseEvent actionEvent) {
        List<File> graphFileList = DocumentModel.getInstance().getGraphFileList();
        if (graphFileList != null){
            LoaderUtils.showLoadGraphsPopup(false);
        }
        if (graphFileList != null) {
            choiceBoxFilled = true;
        }
        for (File file: graphFileList){
            graphComboBox.getItems().add(file.getName());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ( (String) arg) {
            case "graphFilesLoaded":
                List<File> graphFileList = DocumentModel.getInstance().getGraphFileList();
                int indexSelected = graphComboBox.getSelectionModel().getSelectedIndex();
                System.out.println("Index selected" + indexSelected);
                FXCollections.observableArrayList();
                if (graphFileList.size() > 0) {
                    choiceBoxFilled = true;
                }
                for (File file : graphFileList) {
                    graphComboBox.getItems().add(file.getName());
                }
                graphComboBox.getSelectionModel().select(indexSelected);
                //TODO: Make disctinction between all new files and one extra file
                break;
            case "scriptLoaded":
                File script = DocumentModel.getInstance().getGraafVisFile();
                graafVisScriptNameLabel.setText(script.getName());
                break;
            default:
                break;
        }
    }
}
