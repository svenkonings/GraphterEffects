package start;

import general.StageHistory;
import general.ViewModel;
import general.files.DocumentModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import loader.LoaderView;
import screens.idescreen.IDEView;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class StartPresenter implements Initializable {

    @Inject private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageHistory.getInstance().setCurrentStage(StartPresenter.class.getSimpleName());
    }

    public void start() {

    }

    public void createNewGraafVisButtonPressed(ActionEvent actionEvent) {
        StageHistory.getInstance().setCurrentStage(this.getClass().getSimpleName());
        DocumentModel.getInstance().newGraafVisFile();
        IDEView IDEView = new IDEView();
        //addKeyboardShortCuts(IDEView.getView());
        viewModel.setMainView(IDEView.getView());

    }

    public void loadGGButtonPressed(ActionEvent actionEvent) {
        LoaderView loaderView = new LoaderView();
        viewModel.setMainView(loaderView.getView());
    }

    public void addKeyboardShortCuts(Parent view){
        /*
        BorderPane borderPane = (BorderPane) view;
        TabPane tabPane = (TabPane) borderPane.getCenter();
        //CRTL + S for 'save'
        final KeyCombination keyCombSave = new KeyCodeCombination(KeyCode.S,
                KeyCombination.CONTROL_ANY);
        view.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (keyCombSave.match(event)) {
                    try {
                        if (tabPane.getSelectionModel().getSelectedIndex() == 0) {
                            IOManager.saveVIS(DocumentModel.getInstance().getGraafVisFilePath(), DocumentModel.getInstance().graafVisCode);
                        } else if (tabPane.getSelectionModel().getSelectedIndex() > 0) {
                            if (tabPane.getSelectionModel().getSelectedItem().getText().split("\\.")[1].equals("svg")){
                                String filename = tabPane.getSelectionModel().getSelectedItem().getText().split("\\.")[0];
                                IOManager.showSaveSVGPopup(DocumentModel.getInstance().getGeneratedSVG(filename));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //CRTL + ALT + S for 'save as'
        final KeyCombination keyCombSaveAs = new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_ANY,
                KeyCombination.CONTROL_ANY);
        view.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (keyCombSaveAs.match(event)) {
                    if (tabPane.getSelectionModel().getSelectedIndex() == 0) {
                        IOManager.showSaveScriptPopup(DocumentModel.getInstance().getGraafVisFilePath(), DocumentModel.getInstance().graafVisCode);
                    } else if (tabPane.getSelectionModel().getSelectedIndex() > 0) {
                        if (tabPane.getSelectionModel().getSelectedItem().getText().split("\\.")[1].equals("svg")) {
                            String filename = tabPane.getSelectionModel().getSelectedItem().getText().split("\\.")[0];
                            IOManager.showSaveSVGPopup(DocumentModel.getInstance().getGeneratedSVG(filename));
                        }
                    }
                }
                }
            });
        */
    }
}
