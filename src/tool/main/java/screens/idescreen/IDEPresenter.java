package screens.idescreen;

import general.DocumentModel;
import general.StageHistory;
import general.ViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import screens.idescreen.codepane.CodePaneView;
import screens.idescreen.topbar.TopBarView;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class IDEPresenter implements Initializable {

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
        if (DocumentModel.getInstance().getGraafVisFile() == null){
            tabPane.getTabs().add(new Tab("New file", codePaneView.getView()));
        } else {
            tabPane.getTabs().add(new Tab(DocumentModel.getInstance().getGraafVisFile().getName(), codePaneView.getView()));
        }

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



}
