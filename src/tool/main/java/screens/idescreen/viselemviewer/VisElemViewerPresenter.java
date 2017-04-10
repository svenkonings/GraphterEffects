package screens.idescreen.viselemviewer;

import compiler.solver.VisElem;
import compiler.solver.VisMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class VisElemViewerPresenter implements Initializable {

    @FXML
    public TreeView visElemsView;
    public Button filterButton;
    public TextField filterarguments;
    public StackPane visElemPane;
    public TitledPane titledPane;
    private VisMap visMap;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titledPane.prefWidthProperty().bind(visElemPane.widthProperty());
        titledPane.prefHeightProperty().bind(visElemPane.heightProperty());
        titledPane.maxWidthProperty().bind(visElemPane.widthProperty());

        visElemPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("Width property:" + newValue);
            }
        });
    }

    public void loadContent(VisMap visMap) {
        this.visMap = visMap;
        showContentInTreeView();
    }

    public void showContentInTreeView() {
        TreeItem<String> rootItem = new TreeItem<String>("Vis Elems");
        rootItem.setExpanded(true);
        Map<String, VisElem> visElemMap = visMap.getMapping();

        for (String atom : visElemMap.keySet()) {
            TreeItem visElemTreeItem = new TreeItem<String>("visElem " + atom);
            VisElem visElem = visElemMap.get(atom);
            for (String key : visElem.getValues().keySet())
                visElemTreeItem.getChildren().add(new TreeItem<String>(key + ": " + visElem.getValues().get(key)));
            rootItem.getChildren().add(visElemTreeItem);
        }
        visElemsView.setRoot(rootItem);
    }

}
