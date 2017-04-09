package screens.idescreen.topbar.buttonbar;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import general.ViewModel;
import general.compiler.CompilerRunnable;
import general.files.DocumentModel;
import general.files.DocumentModelChange;
import general.files.LoaderUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import screens.idescreen.simpleviewer.SimpleViewerPresenter;
import screens.idescreen.simpleviewer.SimpleViewerView;
import utils.FileUtils;
import utils.Pair;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        graphComboBox.setCellFactory(lv ->
                new ListCell<String>() {
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

                                    System.out.println("Clicked cross on " + item);
                                    DocumentModel.getInstance().removeGraph(item);
                                    //if (isSelected()) {
                                        //graphComboBox.getSelectionModel().select(null);
                                    //}
                                }
                        );
                        // Arrange controls in a HBox, and set display to graphic only (the text is included in the graphic in this implementation).
                        graphic = new HBox(label, cross);
                        graphic.setHgrow(label, Priority.ALWAYS);
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
                });

        // We have to set a custom skin, otherwise the ComboBox disappears before the click on the Hyperlink is registered.
        graphComboBox.setSkin(new ComboBoxListViewSkin<String>(graphComboBox) {
            @Override
            protected boolean isHideOnClickEnabled() {
                return false;
            }
        });

        final ContextMenu contextMenu = new ContextMenu();
        MenuItem showAsImage = new MenuItem("Show as Image");
        MenuItem showAsText = new MenuItem("Show as Text");
        contextMenu.getItems().addAll(showAsImage, showAsText);
        showAsImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String selectedGraphName = graphComboBox.getSelectionModel().getSelectedItem().toString();
                Path selectedGraphPath = DocumentModel.getInstance().getGraphPathMap().get(selectedGraphName);
                CompilerRunnable compilerRunnable = new CompilerRunnable(Paths.get("defaultvisualization.vis"), selectedGraphPath);
                new Thread(compilerRunnable).start();
            }
        });
        showAsText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String selectedGraphName = graphComboBox.getSelectionModel().getSelectedItem().toString();
                Path selectedGraphPath = DocumentModel.getInstance().getGraphPathMap().get(selectedGraphName);
                String graphAsString = "";
                try {
                    graphAsString = FileUtils.readFromFile(selectedGraphPath.toFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TabPane centralTabPane = (TabPane) ((BorderPane) (((AnchorPane) splitPane.getParent()).getParent().getParent()).getParent()).getCenter();
                SimpleViewerView simpleViewerView = new SimpleViewerView();
                SimpleViewerPresenter simpleViewerPresenter = (SimpleViewerPresenter) simpleViewerView.getPresenter();
                simpleViewerPresenter.loadContent(selectedGraphName, graphAsString);
                centralTabPane.getTabs().add(new Tab(selectedGraphName, simpleViewerView.getView()));
            }
        });

        graphComboBox.setContextMenu(contextMenu);
        graphComboBox.setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            if (!choiceBoxFilled) {
                                LoaderUtils.showLoadGraphsPopup(false);
                            }
                        }
                    }
                }
        );
        graphComboBox.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    event.consume();
                }
            }
        });

        DocumentModel.getInstance().addObserver(this);
        bind();
    }

    public void bind(){
        splitPane.prefWidthProperty().bind( ((Pane) (viewModel.getMainView()).getParent()).widthProperty() );
    }

    public void compileButtonPressed(ActionEvent actionEvent) {
        Path graphFilePath = DocumentModel.getInstance().getGraphPathMap().get(graphComboBox.getSelectionModel().getSelectedItem());
        Path scriptFilePath = DocumentModel.getInstance().getGraafVisFilePath();

        TabPane tabPane = (TabPane) (((BorderPane) (viewModel.getMainView())).getCenter());
        String codeOnScreen = DocumentModel.getInstance().graafVisCode; //This way the user doesn't have to save it's code first

        new File("/temp/compiler").mkdirs();
        Path tempFilePath = Paths.get("/temp/compiler",scriptFilePath.getFileName().toString());
        try {
            LoaderUtils.saveVIS(tempFilePath,codeOnScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            new Thread(new CompilerRunnable(tempFilePath,graphFilePath)).start();
        } catch (Exception e){
            e.printStackTrace();
            //TODO: Handle exceptions by showing them in an error box
        }

    }



    @Override
    public void update(Observable o, Object arg) {
        Pair<DocumentModelChange, Object> arguments = (Pair) arg;
        DocumentModelChange documentModelChange = (DocumentModelChange) arguments.get(0);
        int indexSelected;
        switch (documentModelChange) {
            case GRAPHFILELOADED:
                indexSelected = graphComboBox.getSelectionModel().getSelectedIndex();
                String graphFileNameNew = (String) arguments.get(1);
                //graphComboBox.setItems(FXCollections.observableArrayList());
                choiceBoxFilled = true;

                graphComboBox.getItems().add(graphFileNameNew);
                //When nothing is selected, select the first item from the list.
                if (indexSelected == -1){
                    indexSelected = 0;
                }
                graphComboBox.getSelectionModel().select(indexSelected);
                //TODO: Make disctinction between all new files and one extra file
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
                    choiceBoxFilled = false;
                }
                break;

            case GRAAFVISFILELOADED:
                Path script = DocumentModel.getInstance().getGraafVisFilePath();
                graafVisScriptNameLabel.setText(script.getFileName().toString());
                break;
            default:
                break;
        }
    }

    public void debugButtonPressed(ActionEvent actionEvent) {
    }
}
