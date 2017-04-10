package screens.idescreen.viselemviewer;

import compiler.solver.VisElem;
import compiler.solver.VisMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class VisElemViewerPresenter implements Initializable {

    @FXML
    public AnchorPane simpleViewerPane;
    @FXML
    public TreeView visElemsView;
    private VisMap visMap;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void loadContent(VisMap visMap) {
        this.visMap = visMap;
        showContentInTreeView();
    }

    public void showContentInTreeView() {
        TreeItem<String> rootItem = new TreeItem<String>("Vis Elems");
        rootItem.setExpanded(true);

        int index = 0;
        for (VisElem visElem : visMap.values()) {
            TreeItem visElemTreeItem = new TreeItem<String>("visElem " + index);
            for (String key : visElem.getValues().keySet()) {
                visElemTreeItem.getChildren().add(new TreeItem<String>(key + ": " + visElem.getValues().get(key)));
            }
            rootItem.getChildren().add(visElemTreeItem);
            index++;
        }

        visElemsView.setRoot(rootItem);
    }

}
