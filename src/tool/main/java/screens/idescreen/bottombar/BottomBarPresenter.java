package screens.idescreen.bottombar;

import general.ViewModel;
import general.compiler.CompilationModel;
import general.compiler.CompilationProgress;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;

import javax.inject.Inject;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class BottomBarPresenter implements Initializable, Observer{

    @Inject ViewModel viewModel;
    public TitledPane compilationResultTitledPane;
    public TextArea compilationResultTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        compilationResultTitledPane.prefWidthProperty().bind(viewModel.getMainView().getScene().widthProperty());

        compilationResultTitledPane.setPrefHeight(25);
        compilationResultTextArea.setPrefHeight(100);

        compilationResultTitledPane.setExpanded(false);

        compilationResultTitledPane.expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    compilationResultTitledPane.setPrefHeight(100);
                } else {
                    compilationResultTitledPane.setPrefHeight(25);
                }
            }
        });


        CompilationModel.getInstance().addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        CompilationProgress compilationProgress = (CompilationProgress) arg;
        switch (compilationProgress){
            case COMPILATIONSTARTED:
                CompilationModel.getInstance().addObserverToCompilation(this);
                compilationResultTextArea.setText("Compilation started \n");
                compilationResultTitledPane.setExpanded(true);
                break;
            case DEBUGCOMPILATIONSTARTED:
                CompilationModel.getInstance().addObserverToCompilation(this);
                compilationResultTextArea.setText("Debug Compilation started \n");
                compilationResultTitledPane.setExpanded(true);
            case SOLVED:
                compilationResultTextArea.appendText("SVG generated\n");
                break;
            case ERROROCCURED:
                compilationResultTextArea.appendText(CompilationModel.getInstance().getCompilation().getException().toString());
                break;
        }
    }
}
