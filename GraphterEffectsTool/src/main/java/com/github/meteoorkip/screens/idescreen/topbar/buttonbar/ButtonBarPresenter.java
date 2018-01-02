package com.github.meteoorkip.screens.idescreen.topbar.buttonbar;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import com.github.meteoorkip.general.ViewModel;
import com.github.meteoorkip.general.files.FileModel;
import com.github.meteoorkip.general.files.FileModelChange;
import com.github.meteoorkip.general.files.IOManager;
import com.github.meteoorkip.general.generation.GenerationProgress;
import com.github.meteoorkip.general.generation.GeneratorRunnable;
import com.github.meteoorkip.general.generation.GeneratorUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import com.github.meteoorkip.screens.idescreen.tab.simpleviewer.SimpleViewerPresenter;
import com.github.meteoorkip.screens.idescreen.tab.simpleviewer.SimpleViewerView;
import com.github.meteoorkip.utils.FileUtils;
import com.github.meteoorkip.utils.Pair;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class ButtonBarPresenter implements Initializable, Observer {

    public SplitPane splitPane;
    public Button compileButton;
    public MenuButton debugButton;
    @FXML private Label graafVisScriptNameLabel;
    @FXML private ComboBox graphComboBox;
    @Inject ViewModel viewModel;
    private boolean choiceBoxFilled;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path graafvisScriptPath = FileModel.getInstance().getGraafVisFilePath();
        Map<String,Path> graphPathMap = FileModel.getInstance().getGraphPathMap();

        if (graafvisScriptPath == null) {
            graafVisScriptNameLabel.setText("New Script");
        } else {
            graafVisScriptNameLabel.setText(graafvisScriptPath.getFileName().toString());
        }

        graphComboBox.setPromptText("Select graphs");

        graphComboBox.setCellFactory(lv -> new ListCell<String>() {
            // This is the node that will display the text and the cross.
            // I chose a hyperlink, but you can change to button, image, etc.
            private HBox graphic;

            // this is the constructor for the anonymous class.
            {
                Label label = new Label();
                // Bind the label text to the item property. If your ComboBox items are not Strings you should use a converter.
                label.textProperty().bind(itemProperty());
                // Set max width to infinity so the cross is all the way to the right.
                label.setMaxWidth(Double.POSITIVE_INFINITY);
                // We have to modify the hiding behavior of the ComboBox to allow clicking on the hyperlink,
                // so we need to hide the ComboBox when the label is clicked (item selected).
                label.setOnMouseClicked(event -> graphComboBox.hide());

                Hyperlink cross = new Hyperlink(" X ");
                cross.setVisited(true); // So it is black, and not blue.

                cross.setOnMouseClicked(event ->
                        {
                            // Since the ListView reuses cells, we need to get the item first, before making changes.
                            String item = getItem();
                            FileModel.getInstance().removeGraph(item);
                            if (graphComboBox.getSelectionModel().getSelectedItem()==null) {
                                System.out.println(1);
                            }
                            //if (isSelected()) {
                            //graphComboBox.getSelectionModel().select(null);
                            //}
                        }
                );
                // Arrange controls in a HBox, and set display to graphic only (the text is included in the graphic in this implementation).
                graphic = new HBox(label, cross);
                HBox.setHgrow(label, Priority.ALWAYS);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(graphic);
                }
            }
        }
                );
        // We have to set a custom skin, otherwise the ComboBox disappears before the click on the Hyperlink is registered.
        graphComboBox.setSkin(new ComboBoxListViewSkin<String>(graphComboBox) {
            @Override
            protected boolean isHideOnClickEnabled() {
                return false;
            }
        });

        graphComboBox.setContextMenu(generateGraphContextMenu());
        graphComboBox.setOnContextMenuRequested(event -> {
            if (graphComboBox.getSelectionModel().getSelectedItem()==null) {
                graphComboBox.getContextMenu().hide();
            }
        });
        graphComboBox.setOnMouseClicked(
                event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        if (!choiceBoxFilled) {
                            IOManager.showLoadGraphsPopup(false);
                        }
                    }
                }
        );
        graphComboBox.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                event.consume();
            }
        });

        graphComboBox.setOnAction(event -> {
            if (graphComboBox.getSelectionModel().getSelectedItem()!=null) {
                FileModel.getInstance().setSelectedGraph(graphComboBox.getSelectionModel().getSelectedItem().toString());
            }
        });


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

        compileButton.setDisable(!choiceBoxFilled);
        debugButton.setDisable(!choiceBoxFilled);

        FileModel.getInstance().addObserver(this);
        bind();
    }

    public void bind(){
        splitPane.prefWidthProperty().bind( viewModel.sceneWidthProperty() );
    }

    public void compileButtonPressed(ActionEvent actionEvent) {
        if (graphComboBox.getSelectionModel().getSelectedIndex() >= 0) {
            Path graphFilePath = FileModel.getInstance().getSelectedGraph();
            Path scriptFilePath = FileModel.getInstance().getGraafVisFilePath();

            String codeOnScreen = FileModel.getInstance().graafVisCode; //This way the user doesn't have to save it's code first
            Path tempFilePath = GeneratorUtils.saveAsTempScript(scriptFilePath.getFileName().toString(), codeOnScreen);
            try {
                new Thread(new GeneratorRunnable(tempFilePath, graphFilePath)).start();
            } catch (Exception e) {
                e.printStackTrace();
                //TODO: Handle exceptions by showing them in an error box
            }
        }
    }



    @Override
    public void update(Observable o, Object arg) {
        Pair<FileModelChange, Object> arguments = (Pair) arg;
        FileModelChange documentModelChange = (FileModelChange) arguments.get(0);
        int indexSelected;
        switch (documentModelChange) {
            case GRAPHFILELOADED:
                String graphFileNameNew = (String) arguments.get(1);
                choiceBoxFilled = true;
                compileButton.setDisable(false);
                debugButton.setDisable(false);

                graphComboBox.getItems().add(graphFileNameNew);
                graphComboBox.getSelectionModel().select(graphComboBox.getItems().size()-1);
                FileModel.getInstance().setSelectedGraph(graphComboBox.getSelectionModel().getSelectedItem().toString());

                               break;
            case GRAPHFILEREMOVED:
                indexSelected = graphComboBox.getSelectionModel().getSelectedIndex();
                String graphFileNameRemoved = (String) arguments.get(1);
                int indexGraphFileRemoved = graphComboBox.getItems().indexOf(graphFileNameRemoved);
                graphComboBox.getItems().remove(graphFileNameRemoved);

                if (indexSelected > indexGraphFileRemoved){
                    indexSelected--;
                } else if (indexSelected == indexGraphFileRemoved){
                    indexSelected = -1;
                }

                graphComboBox.getSelectionModel().select(indexSelected);

                if(graphComboBox.getItems().size() == 0){
                    FileModel.getInstance().setSelectedGraph(null);
                    choiceBoxFilled = false;
                } else {
                    FileModel.getInstance().setSelectedGraph(graphComboBox.getSelectionModel().getSelectedItem().toString());
                }
                compileButton.setDisable(!choiceBoxFilled);
                debugButton.setDisable(!choiceBoxFilled);
                break;

            case GRAAFVISFILELOADED:
                Path script = FileModel.getInstance().getGraafVisFilePath();
                graafVisScriptNameLabel.setText(script.getFileName().toString());
                break;
            default:
                break;
        }
    }

    private ContextMenu generateGraphContextMenu(){
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem showAsImage = new MenuItem("Show Default Visualization");
        MenuItem showAsText = new MenuItem("Show File");
        contextMenu.getItems().addAll(showAsImage, showAsText);
        showAsImage.setOnAction(event -> {
            Path selectedGraphPath = FileModel.getInstance().getSelectedGraph();
            GeneratorRunnable generatorRunnable = null;
            try {
                // TODO: Pim, maak van getResource een methode in FileUtils die de inputstream uitleest en een String returned
                generatorRunnable = new GeneratorRunnable(Paths.get(getClass().getClassLoader().getResource("defaultvisualization.vis").toURI()), selectedGraphPath);
            } catch (URISyntaxException e) {
                // TODO: Probably do something here
                e.printStackTrace();
            }
            new Thread(generatorRunnable).start();
        });
        showAsText.setOnAction(event -> {
            Path selectedGraphPath = FileModel.getInstance().getSelectedGraph();
            String graphAsString = "";
            try {
                graphAsString = FileUtils.readFromFile(selectedGraphPath.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            TabPane centralTabPane = (TabPane) ((BorderPane) (splitPane.getParent().getParent().getParent()).getParent()).getCenter();
            SimpleViewerView simpleViewerView = new SimpleViewerView();
            SimpleViewerPresenter simpleViewerPresenter = (SimpleViewerPresenter) simpleViewerView.getPresenter();
            simpleViewerPresenter.loadContent(selectedGraphPath.getFileName().toString(), graphAsString);
            centralTabPane.getTabs().add(new Tab(selectedGraphPath.getFileName().toString(), simpleViewerView.getView()));
            centralTabPane.getSelectionModel().select(centralTabPane.getTabs().size()-1);
        });
        return contextMenu;
    }

    public void generateVisElemsButtonPressed(ActionEvent actionEvent) {
        Path graphFilePath = FileModel.getInstance().getSelectedGraph();
        Path scriptFilePath = FileModel.getInstance().getGraafVisFilePath();

        String codeOnScreen = FileModel.getInstance().graafVisCode; //This way the user doesn't have to save it's code first
        Path tempFilePath = GeneratorUtils.saveAsTempScript(scriptFilePath.getFileName().toString(),codeOnScreen);
        try {
            new Thread(new GeneratorRunnable(tempFilePath,graphFilePath, GenerationProgress.SOLVED)).start();
        } catch (Exception e){
            e.printStackTrace();
            //TODO: Handle exceptions by showing them in an error box
        }
    }

    public void generateRulesButtonPressed(ActionEvent actionEvent) {
        Path graphFilePath = FileModel.getInstance().getSelectedGraph();
        Path scriptFilePath = FileModel.getInstance().getGraafVisFilePath();

        String codeOnScreen = FileModel.getInstance().graafVisCode; //This way the user doesn't have to save it's code first
        Path tempFilePath = GeneratorUtils.saveAsTempScript(scriptFilePath.getFileName().toString(),codeOnScreen);
        try {
            new Thread(new GeneratorRunnable(tempFilePath,graphFilePath, GenerationProgress.PROLOGLOADED)).start();
        } catch (Exception e){
            e.printStackTrace();
            //TODO: Handle exceptions by showing them in an error box
        }
    }

    public void graafVisScriptNameLabelClicked(MouseEvent mouseEvent) {
        TabPane centralTabPane = (TabPane) ((BorderPane) (splitPane.getParent().getParent().getParent()).getParent()).getCenter();
        centralTabPane.getSelectionModel().select(0);
    }
}
