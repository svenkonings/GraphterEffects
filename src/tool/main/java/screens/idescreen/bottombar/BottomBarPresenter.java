package screens.idescreen.bottombar;

import compiler.prolog.LogListener;
import compiler.prolog.TuProlog;
import general.ViewModel;
import general.compiler.Compilation;
import general.compiler.CompilationModel;
import general.compiler.CompilationProgress;
import graafvis.errors.VisError;
import graafvis.warnings.Warning;
import javafx.application.Platform;
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

public class BottomBarPresenter implements Initializable, Observer, LogListener{

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
        TuProlog.addLogListener(this);
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
//                compilationResultTextArea.appendText(CompilationModel.getInstance().getCompilation().getException().toString());
                Compilation compilation = CompilationModel.getInstance().getCompilation();
                for (VisError error : compilation.getErrors()) {
                    compilationResultTextArea.appendText(error.toString() + "\n");
                }
                for (Warning warning : compilation.getWarnings()) {
                    compilationResultTextArea.appendText(warning.toString() + "\n");
                }
                break;
        }
    }

    @Override
    public void textAdded(String added) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                compilationResultTextArea.appendText(added + "\n");
            }
        });

    }
}
