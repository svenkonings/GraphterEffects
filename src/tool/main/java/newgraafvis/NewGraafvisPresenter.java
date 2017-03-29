package newgraafvis;

import editgraafvis.EditGraafvisView;
import general.StageHistory;
import general.ViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import newgraafvis.newgraafvismenubar.MenuBarView;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class NewGraafvisPresenter implements Initializable {

    @FXML BorderPane borderPane;

    @Inject
    private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageHistory.getInstance().add(this.getClass().getSimpleName());
        MenuBarView menuBarView = new MenuBarView();
        borderPane.setTop(menuBarView.getView());

        EditGraafvisView editGraafvisView = new EditGraafvisView();
        borderPane.setCenter(editGraafvisView.getView());
    }
}
