package screens.idescreen;

import general.StageHistory;
import general.ViewModel;
import general.files.DocumentModel;
import general.files.DocumentModelChange;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import screens.idescreen.codepane.CodePaneView;
import screens.idescreen.svgviewer.SVGViewerPresenter;
import screens.idescreen.svgviewer.SVGViewerView;
import screens.idescreen.topbar.TopBarView;
import utils.Pair;

import javax.inject.Inject;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class IDEPresenter implements Initializable, Observer {

    @FXML public BorderPane borderPane;
    public Pane centerPane;
    public TabPane tabPane;

    @Inject private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageHistory.getInstance().setCurrentStage(this.getClass().getSimpleName());

        borderPane.setStyle("-fx-background-color: #a1fff3;");

        TopBarView topBarView = new TopBarView();
        borderPane.setTop(topBarView.getView());

        CodePaneView codePaneView = new CodePaneView();
        if (DocumentModel.getInstance().getGraafVisFilePath() == null){
            tabPane.getTabs().add(new Tab("New file", codePaneView.getView()));
        } else {
            tabPane.getTabs().add(new Tab(DocumentModel.getInstance().getGraafVisFilePath().getFileName().toString(), codePaneView.getView()));
        }

        DocumentModel.getInstance().addObserver(this);
        bind();


    }

    public void bind(){
        //borderPane.widthProperty().a
        borderPane.prefWidthProperty().bind( ((Pane) (viewModel.getMainView()).getParent()).widthProperty() );
        borderPane.prefHeightProperty().bind( ((Pane) (viewModel.getMainView()).getParent()).heightProperty() );
        borderPane.prefWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("IDE BorderPane Pref Width: " + newValue);
            }
        });
        //borderPane.heightProperty().isEqualTo(viewModel.getMainView().getLayoutBounds().getHeight(),0);
    }


    @Override
    public void update(Observable o, Object arg) {
        Pair<DocumentModelChange, Object> arguments = (Pair) arg;
        DocumentModelChange documentModelChange = (DocumentModelChange) arguments.get(0);
        switch (documentModelChange) {
            case GRAAFVISFILELOADED:
                CodePaneView codePaneView = new CodePaneView();
                tabPane.getTabs().set(0, new Tab(DocumentModel.getInstance().getGraafVisFilePath().getFileName().toString(), codePaneView.getView()));
                break;
            case SVGGENERATED:
                SVGViewerView svgViewerView = new SVGViewerView();
                SVGViewerPresenter svgViewerPresenter = (SVGViewerPresenter) svgViewerView.getPresenter();
                String svgName = (String) arguments.get(1);
                svgViewerPresenter.loadContent(svgName);

                //Show the svg
                BorderPane borderPane = ((BorderPane) viewModel.getMainView());
                TabPane tabPane = (TabPane) borderPane.getCenter();
                tabPane.getTabs().add(new Tab(svgName, svgViewerView.getView()));
                break;
        }
    }
}
