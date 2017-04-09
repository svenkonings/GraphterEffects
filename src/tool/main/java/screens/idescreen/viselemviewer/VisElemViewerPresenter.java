package screens.idescreen.viselemviewer;

import compiler.solver.VisElem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VisElemViewerPresenter implements Initializable {

    @FXML public AnchorPane simpleViewerPane;
    @FXML public TreeView visElemsView;
    private List<VisElem> visElemList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void loadContent(List<VisElem> visElems){
        visElemList = visElems;
        showContentInTreeView();
    }

    public void showContentInTreeView() {
        TreeItem<String> rootItem = new TreeItem<String> ("Vis Elems");
        rootItem.setExpanded(true);

        int index = 0;
        for (VisElem visElem: visElemList){
            TreeItem visElemTreeItem = new TreeItem<String>("visElem " + index);
            for (String key : visElem.getValues().keySet()){
                visElemTreeItem.getChildren().add(new TreeItem<String>(key + ": " + visElem.getValues().get(key)));
            }
            rootItem.getChildren().add(visElemTreeItem);
            index++;
        }

        visElemsView.setRoot(rootItem);
    }

}
