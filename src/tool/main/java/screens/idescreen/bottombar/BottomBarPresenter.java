package screens.idescreen.bottombar;

import general.ViewModel;
import general.compiler.Compilation;
import general.compiler.CompilationModel;
import general.compiler.CompilationProgress;
import graafvis.errors.VisError;
import graafvis.warnings.Warning;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import prolog.LogListener;
import prolog.TuProlog;

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


    private static BottomBarPresenter instance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        compilationResultTitledPane.prefWidthProperty().bind(viewModel.getMainView().getScene().widthProperty());

        compilationResultTitledPane.setPrefHeight(25);
        compilationResultTitledPane.setExpanded(false);

        expandButton.setDisable(true);

        compilationResultTitledPane.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                compilationResultTitledPane.setPrefHeight(viewModel.sceneHeigthProperty().multiply(0.2).doubleValue());
                expandButton.setDisable(false);
            } else {
                compilationResultTitledPane.setPrefHeight(25);
                expandButton.setText("Maximize");
                expandButton.setDisable(true);
            }
        });

        viewModel.sceneHeigthProperty().addListener((observable, oldValue, newValue) -> {
            if (compilationResultTitledPane.isExpanded()){
                if (maximized){
                    compilationResultTitledPane.setPrefHeight(viewModel.sceneHeigthProperty().subtract(65).doubleValue());
                } else {
                    compilationResultTitledPane.setPrefHeight(viewModel.sceneHeigthProperty().multiply(0.2).doubleValue());
                    System.out.println(viewModel.sceneHeigthProperty().multiply(0.2).intValue());
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
                Platform.runLater(() -> {
                    compilationResultTextArea.setText("Compilation started \n");
                    compilationResultTitledPane.setExpanded(true);
                });
                break;
            case DEBUGCOMPILATIONSTARTED:
                CompilationModel.getInstance().addObserverToCompilation(this);
                Platform.runLater(() -> {
                    compilationResultTextArea.setText("Debug Compilation started \n");
                    compilationResultTitledPane.setExpanded(true);
                });
                break;
            case SOLVED:
                String measures = CompilationModel.getInstance().getCompilation().getSolveResults().getModel().getSolver().getMeasures().toString();
                int nbVars = CompilationModel.getInstance().getCompilation().getSolveResults().getModel().getNbVars();
                int nbConstraints = CompilationModel.getInstance().getCompilation().getSolveResults().getModel().getNbVars();

                Platform.runLater(() -> {
                    compilationResultTextArea.appendText("Constraints solved:\n");
                    compilationResultTextArea.appendText("Variables : " + nbVars + "\n");
                    compilationResultTextArea.appendText("Constraints : " + nbConstraints + "\n");
                    compilationResultTextArea.appendText("Measures : \n");
                    compilationResultTextArea.appendText(measures + "\n");
                });
                break;
            case SVGGENERATED:
                Platform.runLater(() -> compilationResultTextArea.appendText("SVG generated\n"));
                break;
            case ERROROCCURED:
                Platform.runLater(() -> compilationResultTextArea.appendText(CompilationModel.getInstance().getCompilation().getException().toString() +
                        CompilationModel.getInstance().getCompilation().getException().getMessage() + "\n"));
                break;
            case NOSOLUTION:
                measures = CompilationModel.getInstance().getCompilation().getSolveResults().getModel().getSolver().getMeasures().toString();
                nbVars = CompilationModel.getInstance().getCompilation().getSolveResults().getModel().getNbVars();
                nbConstraints = CompilationModel.getInstance().getCompilation().getSolveResults().getModel().getNbVars();

                Platform.runLater(() -> {
                    compilationResultTextArea.appendText("No solution found:\n");
                    compilationResultTextArea.appendText("Constraints solved:\n");
                    compilationResultTextArea.appendText("Variables : " + nbVars + "\n");
                    compilationResultTextArea.appendText("Constraints : " + nbConstraints + "\n");
                    compilationResultTextArea.appendText("Measures : \n");
                    compilationResultTextArea.appendText(measures + "\n");
                });
                break;
            case COMPILEERROR:
                Platform.runLater(() -> {
                    compilationResultTextArea.appendText(CompilationModel.getInstance().getCompilation().getException().toString()
                            //+ CompilationModel.getInstance().getCompilation().getException().getMessage()
                            + "\n");
                    Compilation compilation = CompilationModel.getInstance().getCompilation();
                    for (VisError error : compilation.getErrors()) {
                        compilationResultTextArea.appendText(error.toString() + "\n");
                    }
                    for (Warning warning : compilation.getWarnings()) {
                        compilationResultTextArea.appendText(warning.toString() + "\n");
                    }

                });
                break;
        }
    }

    public static void addText(String added) {
        Platform.runLater(() -> Platform.runLater(() -> instance.compilationResultTextArea.appendText(added + "\n")));
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
    }

    @Override
    public void textAdded(String added) {
        addText(added);
    }
}
