package com.github.meteoorkip.screens.idescreen;

import com.github.meteoorkip.general.ViewModel;
import com.github.meteoorkip.general.generation.GenerationModel;
import com.github.meteoorkip.general.generation.GenerationProgress;
import com.github.meteoorkip.general.files.FileModel;
import com.github.meteoorkip.general.files.FileModelChange;
import com.github.meteoorkip.general.files.IOManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import com.github.meteoorkip.screens.idescreen.bottombar.BottomBarView;
import com.github.meteoorkip.screens.idescreen.tab.graafviseditor.GraafVisEditorPresenter;
import com.github.meteoorkip.screens.idescreen.tab.graafviseditor.GraafVisEditorView;
import com.github.meteoorkip.screens.idescreen.tab.ruleviewer.RuleViewerView;
import com.github.meteoorkip.screens.idescreen.tab.svgviewer.SVGViewerPresenter;
import com.github.meteoorkip.screens.idescreen.tab.svgviewer.SVGViewerView;
import com.github.meteoorkip.screens.idescreen.topbar.TopBarView;
import com.github.meteoorkip.screens.idescreen.tab.viselemviewer.VisElemViewerPresenter;
import com.github.meteoorkip.screens.idescreen.tab.viselemviewer.VisElemViewerView;
import com.github.meteoorkip.utils.Pair;

import javax.inject.Inject;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class IDEPresenter implements Initializable, Observer {

    @FXML public BorderPane borderPane;
    public TabPane tabPane;

    @Inject private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TopBarView topBarView = new TopBarView();
        borderPane.setTop(topBarView.getView());

        BottomBarView bottomBarView = new BottomBarView();
        borderPane.setBottom(bottomBarView.getView());

        Tab codeTab = createGraafvisEditorTab();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tabPane.getTabs().add(codeTab);

        bind();

        FileModel.getInstance().addObserver(this);
        GenerationModel.getInstance().addObserver(this);

    }

    private void bind(){
        borderPane.prefWidthProperty().bind(viewModel.sceneWidthProperty());
        borderPane.prefHeightProperty().bind(viewModel.sceneHeigthProperty());
    }

    public static Tab createGraafvisEditorTab(){
        GraafVisEditorView graafVisEditorView = new GraafVisEditorView();
        String graafVisScriptName = FileModel.getInstance().getGraafVisFilePath().getFileName().toString();
        Tab codeTab = new Tab(graafVisScriptName, graafVisEditorView.getView());

        GraafVisEditorPresenter graafVisEditorPresenter = (GraafVisEditorPresenter) graafVisEditorView.getPresenter();
        graafVisEditorPresenter.getCodeArea().textProperty().addListener((observable, oldValue, newValue) -> {
            codeTab.setText(graafVisScriptName + " *");
            FileModel.getInstance().setGraafvisChangesSaved(false);
        });

        //TODO, zijn deze nodig??
        //((StackPane) graafVisEditorView.getView()).prefWidthProperty().bind(tabPane.widthProperty());
        //((StackPane) graafVisEditorView.getView()).prefHeightProperty().bind(tabPane.heightProperty());

        codeTab.setClosable(false);
        return codeTab;
    }

    public static Tab createSVGViewerTab(String svgName){
        SVGViewerView svgViewerView = new SVGViewerView();
        SVGViewerPresenter svgViewerPresenter = (SVGViewerPresenter) svgViewerView.getPresenter();

        svgViewerPresenter.loadContent(svgName, FileModel.getInstance().getGeneratedSVG(svgName));
        svgViewerPresenter.showSVGAsImage();

        Tab svgViewerTab = new Tab(svgName, svgViewerView.getView());
        svgViewerTab.setClosable(true);
        svgViewerTab.setOnCloseRequest(event -> {
            if (! IOManager.showSVGSaveDialog(FileModel.getInstance().getGeneratedSVG(svgName))){
                event.consume();
            }
        });
        svgViewerTab.setOnClosed(event -> FileModel.getInstance().removeGeneratedSVG(svgName));

        final ContextMenu contextMenu = new ContextMenu();
        MenuItem showAsImage = new MenuItem("Show as Image");
        MenuItem showAsText = new MenuItem("Show as Text");
        showAsImage.setOnAction(event -> svgViewerPresenter.showSVGAsImage());
        showAsText.setOnAction(event -> svgViewerPresenter.showSVGAsText());
        contextMenu.getItems().addAll(showAsImage, showAsText);

        //((StackPane) svgViewerView.getView()).prefWidthProperty().bind(tabPane.widthProperty());
        //((StackPane) svgViewerView.getView()).prefHeightProperty().bind(tabPane.heightProperty());

        svgViewerTab.setContextMenu(contextMenu);

        return svgViewerTab;
    }

    public static Tab createRuleViewerTab(){
        RuleViewerView ruleViewerView = new RuleViewerView();
        //((StackPane) ruleViewerView.getView()).prefWidthProperty().bind(tabPane.widthProperty());
        //((StackPane) ruleViewerView.getView()).prefHeightProperty().bind(tabPane.heightProperty());
        String graphName = GenerationModel.getInstance().getGeneration().getGraphFile().getFileName().toString().split("\\.")[0];

        return new Tab("Rules " + graphName, ruleViewerView.getView());
    }

    public static Tab createVisElemViewerTab(){
        VisElemViewerView visElemViewerView = new VisElemViewerView();
        VisElemViewerPresenter visElemViewerPresenter = (VisElemViewerPresenter) visElemViewerView.getPresenter();
        visElemViewerPresenter.loadContent(GenerationModel.getInstance().getGeneration().getSolveResults().getVisMap());
        //((StackPane) visElemViewerView.getView()).prefWidthProperty().bind(tabPane.widthProperty());
        //((StackPane) visElemViewerView.getView()).prefHeightProperty().bind(tabPane.heightProperty());
        String graphName = GenerationModel.getInstance().getGeneration().getGraphFile().getFileName().toString().split("\\.")[0];

        return new Tab("Vis Elems " + graphName,visElemViewerView.getView());
    }

    @Override
    public void update(Observable o, Object arg) {

        //TODO: ADD LOGGING
        if (arg instanceof Pair) {
            Pair arguments = (Pair) arg;
            if (arguments.get(0) instanceof FileModelChange) {
                FileModelChange documentModelChange = (FileModelChange) arguments.get(0);
                switch (documentModelChange) {
                    case GRAAFVISFILELOADED:
                        FileModel.getInstance().setGraafvisChangesSaved(true);
                        Tab codeTab = createGraafvisEditorTab();
                        tabPane.getTabs().set(0, codeTab);
                        tabPane.getSelectionModel().select(0);
                        break;

                    case SVGGENERATED:
                        Platform.runLater(() -> {
                            Tab svgViewerTab = createSVGViewerTab((String) arguments.get(1));
                            tabPane.getTabs().add(svgViewerTab);
                            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
                        });
                        break;
                }
            }
        }

        else if (arg instanceof GenerationProgress){
            GenerationProgress generationProgress = (GenerationProgress) arg;
            switch (generationProgress) {
                case NORMALGENERATIONSTARTED:
                    //TODO LOGGING: System.out.println("compilation started");
                    GenerationModel.getInstance().addObserverToGeneration(this);
                    break;
                case DEBUGGENERATIONSTARTED:
                    //TODO LOGGING: System.out.println("debug compilation started");
                    GenerationModel.getInstance().addObserverToGeneration(this);
                    break;
                case PROLOGLOADED:
                    //TODO LOGGING: System.out.println("Graph converted");
                    if (GenerationModel.getInstance().getGeneration().isDebug() &&
                            GenerationModel.getInstance().getGeneration().getTargetProgress() == generationProgress) {
                        Platform.runLater(() -> {tabPane.getTabs().add(createRuleViewerTab());
                            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);});
                    }
                    break;
                case GRAAFVISCOMPILED:
                    //TODO: LOGGING System.out.println("Graafvis compiled");
                    break;
                case SOLVED:
                    //TODO: LOGGING System.out.println("Logic solved");
                    if (GenerationModel.getInstance().getGeneration().isDebug() &&
                            GenerationModel.getInstance().getGeneration().getTargetProgress() == generationProgress){
                        Platform.runLater(() -> {tabPane.getTabs().add(createVisElemViewerTab());
                            tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);});
                    }
                    break;
                case SVGGENERATED:
                    //TODO: LOGGING System.out.println("SVG generated");
                    break;
                case GENERATIONFINISHED:
                    //TODO: LOGGING System.out.println("Generation complete");
                    break;
                case ERROROCCURED:
                    //TODO: Show error screen
                    break;
            }
        }
    }
}
