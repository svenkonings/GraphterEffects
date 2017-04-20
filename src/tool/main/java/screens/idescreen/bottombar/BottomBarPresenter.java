package screens.idescreen.bottombar;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import prolog.LogListener;
import prolog.TuProlog;
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

    public Button expandButton;
    @Inject ViewModel viewModel;
    public TitledPane compilationResultTitledPane;
    public TextArea compilationResultTextArea;
    public boolean maximized;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        compilationResultTitledPane.prefWidthProperty().bind(viewModel.getMainView().getScene().widthProperty());

        compilationResultTitledPane.setPrefHeight(25);
        compilationResultTitledPane.setExpanded(false);
        expandButton.setDisable(true);

        compilationResultTitledPane.expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    compilationResultTitledPane.setPrefHeight(viewModel.sceneHeigthProperty().multiply(0.2).doubleValue());
                    expandButton.setDisable(false);
                } else {
                    compilationResultTitledPane.setPrefHeight(25);
                    expandButton.setText("Maximize");
                    expandButton.setDisable(true);
                }
            }
        });

        viewModel.sceneHeigthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (compilationResultTitledPane.isExpanded()){
                    if (maximized){
                        compilationResultTitledPane.setPrefHeight(viewModel.sceneHeigthProperty().subtract(65).doubleValue());
                    } else {
                        compilationResultTitledPane.setPrefHeight(viewModel.sceneHeigthProperty().multiply(0.2).doubleValue());
                        System.out.println(viewModel.sceneHeigthProperty().multiply(0.2).intValue());
                    }
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
            case NORMALCOMPILATIONSTARTED:
                CompilationModel.getInstance().addObserverToCompilation(this);
                compilationResultTextArea.setText("Compilation started \n");
                compilationResultTitledPane.setExpanded(true);
                break;
            case DEBUGCOMPILATIONSTARTED:
                CompilationModel.getInstance().addObserverToCompilation(this);
                compilationResultTextArea.setText("Debug Compilation started \n");
                compilationResultTitledPane.setExpanded(true);
                break;
            case SOLVED:
                String measures = CompilationModel.getInstance().getCompilation().getSolveResults().getModel().getSolver().getMeasures().toString();
                int nbVars = CompilationModel.getInstance().getCompilation().getSolveResults().getModel().getNbVars();
                int nbConstraints = CompilationModel.getInstance().getCompilation().getSolveResults().getModel().getNbVars();

                compilationResultTextArea.appendText("Constraints solved:\n");
                compilationResultTextArea.appendText("Variables : " + nbVars + "\n");
                compilationResultTextArea.appendText("Constraints : " + nbConstraints + "\n");
                compilationResultTextArea.appendText("Measures : \n");
                compilationResultTextArea.appendText(measures + "\n");
                break;
            case SVGGENERATED:
                compilationResultTextArea.appendText("SVG generated\n");
                break;
            case ERROROCCURED:
                compilationResultTextArea.appendText(CompilationModel.getInstance().getCompilation().getException().toString());
                break;
            case NOSOLUTION:
                measures = CompilationModel.getInstance().getCompilation().getSolveResults().getModel().getSolver().getMeasures().toString();
                nbVars = CompilationModel.getInstance().getCompilation().getSolveResults().getModel().getNbVars();
                nbConstraints = CompilationModel.getInstance().getCompilation().getSolveResults().getModel().getNbVars();

                compilationResultTextArea.appendText("No solution found:\n");
                compilationResultTextArea.appendText("Constraints solved:\n");
                compilationResultTextArea.appendText("Variables : " + nbVars + "\n");
                compilationResultTextArea.appendText("Constraints : " + nbConstraints + "\n");
                compilationResultTextArea.appendText("Measures : \n");
                compilationResultTextArea.appendText(measures + "\n");
                break;
            case COMPILEERROR:
                compilationResultTextArea.appendText("A compile error occured:");
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

    public void expandButtonPressed(ActionEvent actionEvent) {
        if (compilationResultTitledPane.isExpanded()) {
            if (!maximized) {
                compilationResultTitledPane.setPrefHeight(viewModel.sceneHeigthProperty().subtract(65).doubleValue());
                maximized = true;
                expandButton.setText("Reduce");
            } else {
                compilationResultTitledPane.setPrefHeight(viewModel.sceneHeigthProperty().multiply(0.2).doubleValue());
                maximized = false;
                expandButton.setText("Maximize");
            }
        }
        //System.out.println(viewModel.sceneHeigthProperty().multiply(0.2).intValue());
    }
}
