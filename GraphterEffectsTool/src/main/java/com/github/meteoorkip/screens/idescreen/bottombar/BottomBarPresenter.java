package com.github.meteoorkip.screens.idescreen.bottombar;

import com.github.meteoorkip.general.ViewModel;
import com.github.meteoorkip.general.generation.Generation;
import com.github.meteoorkip.general.generation.GenerationModel;
import com.github.meteoorkip.general.generation.GenerationProgress;
import com.github.meteoorkip.graafvis.errors.VisError;
import com.github.meteoorkip.graafvis.warnings.Warning;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import com.github.meteoorkip.prolog.LogListener;
import com.github.meteoorkip.prolog.TuProlog;

import javax.inject.Inject;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class BottomBarPresenter implements Initializable, Observer, LogListener{

    public Button expandButton;
    @Inject ViewModel viewModel;
    public TitledPane generationResultTitledPane;
    public TextArea generationResultTextArea;
    public boolean maximized;


    private static BottomBarPresenter instance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        generationResultTitledPane.prefWidthProperty().bind(viewModel.getMainView().getScene().widthProperty());

        generationResultTitledPane.setPrefHeight(25);
        generationResultTitledPane.setExpanded(false);

        expandButton.setDisable(true);

        generationResultTitledPane.expandedProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue) {
                double relativeValue = viewModel.sceneHeigthProperty().multiply(0.2).doubleValue();
                int minimumValue = 100;
                if (relativeValue < minimumValue) {
                    generationResultTitledPane.setPrefHeight(minimumValue);
                } else {
                    generationResultTitledPane.setPrefHeight(relativeValue);
                }
                expandButton.setDisable(false);
            } else {
                generationResultTitledPane.setPrefHeight(25);
                expandButton.setText("Maximize");
                expandButton.setDisable(true);
            }
        });

        viewModel.sceneHeigthProperty().addListener((observable, oldValue, newValue) -> {
            if (generationResultTitledPane.isExpanded()){
                if (maximized){
                    generationResultTitledPane.setPrefHeight(viewModel.sceneHeigthProperty().subtract(65).doubleValue());
                } else {
                    double relativeValue = viewModel.sceneHeigthProperty().multiply(0.2).doubleValue();
                    int minimumValue = 100;
                    if (relativeValue < minimumValue) {
                        generationResultTitledPane.setPrefHeight(minimumValue);
                    } else {
                        generationResultTitledPane.setPrefHeight(relativeValue);
                    }
                }
            }
        });


        GenerationModel.getInstance().addObserver(this);
        TuProlog.addLogListener(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        GenerationProgress generationProgress = (GenerationProgress) arg;
        switch (generationProgress){
            case NORMALGENERATIONSTARTED:
                GenerationModel.getInstance().addObserverToGeneration(this);
                Platform.runLater(() -> {
                    generationResultTextArea.setText("Generation started \n");
                    generationResultTitledPane.setExpanded(true);
                });
                break;
            case DEBUGGENERATIONSTARTED:
                GenerationModel.getInstance().addObserverToGeneration(this);
                Platform.runLater(() -> {
                    generationResultTextArea.setText("Debug Generation started \n");
                    generationResultTitledPane.setExpanded(true);
                });
                break;
            case SOLVED:
                String measures = GenerationModel.getInstance().getGeneration().getSolveResults().getModel().getSolver().getMeasures().toString();
                int nbVars = GenerationModel.getInstance().getGeneration().getSolveResults().getModel().getNbVars();
                int nbConstraints = GenerationModel.getInstance().getGeneration().getSolveResults().getModel().getNbVars();

                Platform.runLater(() -> {
                    generationResultTextArea.appendText("Constraints solved:\n");
                    generationResultTextArea.appendText("Variables : " + nbVars + "\n");
                    generationResultTextArea.appendText("Constraints : " + nbConstraints + "\n");
                    generationResultTextArea.appendText("Measures : \n");
                    generationResultTextArea.appendText(measures + "\n");
                });
                break;
            case SVGGENERATED:
                Platform.runLater(() -> generationResultTextArea.appendText("SVG generated\n"));
                break;
            case ERROROCCURED:
                Platform.runLater(() -> generationResultTextArea.appendText(GenerationModel.getInstance().getGeneration().getException() + "\n"));
                GenerationModel.getInstance().getGeneration().getException().printStackTrace();
                break;
            case NOSOLUTION:
                measures = GenerationModel.getInstance().getGeneration().getSolveResults().getModel().getSolver().getMeasures().toString();
                nbVars = GenerationModel.getInstance().getGeneration().getSolveResults().getModel().getNbVars();
                nbConstraints = GenerationModel.getInstance().getGeneration().getSolveResults().getModel().getNbVars();

                Platform.runLater(() -> {
                    generationResultTextArea.appendText("No solution found:\n");
                    generationResultTextArea.appendText("Constraints solved:\n");
                    generationResultTextArea.appendText("Variables : " + nbVars + "\n");
                    generationResultTextArea.appendText("Constraints : " + nbConstraints + "\n");
                    generationResultTextArea.appendText("Measures : \n");
                    generationResultTextArea.appendText(measures + "\n");
                });
                break;
            case COMPILEERROR:
                Platform.runLater(() -> {
                    generationResultTextArea.appendText(GenerationModel.getInstance().getGeneration().getException().toString()
                            //+ GenerationModel.getInstance().getGeneration().getException().getMessage()
                            + "\n");
                    Generation generation = GenerationModel.getInstance().getGeneration();
                    for (VisError error : generation.getErrors()) {
                        generationResultTextArea.appendText(error.toString() + "\n");
                    }
                    for (Warning warning : generation.getWarnings()) {
                        generationResultTextArea.appendText(warning.toString() + "\n");
                    }

                });
                break;
        }
    }

    public static void addText(String added) {
        Platform.runLater(() -> Platform.runLater(() -> instance.generationResultTextArea.appendText(added + "\n")));
    }

    public void expandButtonPressed(ActionEvent actionEvent) {
        if (generationResultTitledPane.isExpanded()) {
            if (!maximized) {
                generationResultTitledPane.setPrefHeight(viewModel.sceneHeigthProperty().subtract(65).doubleValue());
                maximized = true;
                expandButton.setText("Reduce");
            } else {
                double relativeValue = viewModel.sceneHeigthProperty().multiply(0.2).doubleValue();
                int minimumValue = 100;
                if (relativeValue < minimumValue) {
                    generationResultTitledPane.setPrefHeight(minimumValue);
                } else {
                    generationResultTitledPane.setPrefHeight(relativeValue);
                }
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
