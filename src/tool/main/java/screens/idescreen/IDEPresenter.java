package screens.idescreen;

import general.StageHistory;
import general.ViewModel;
import general.compiler.CompilationModel;
import general.compiler.CompilationProgress;
import general.files.DocumentModel;
import general.files.DocumentModelChange;
import general.files.IOManager;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import screens.idescreen.bottombar.BottomBarView;
import screens.idescreen.tab.graafviseditor.GraafVisEditorPresenter;
import screens.idescreen.tab.graafviseditor.GraafVisEditorView;
import screens.idescreen.tab.ruleviewer.RuleViewerPresenter;
import screens.idescreen.tab.ruleviewer.RuleViewerView;
import screens.idescreen.tab.svgviewer.SVGViewerPresenter;
import screens.idescreen.tab.svgviewer.SVGViewerView;
import screens.idescreen.topbar.TopBarView;
import screens.idescreen.viselemviewer.VisElemViewerPresenter;
import screens.idescreen.viselemviewer.VisElemViewerView;
import utils.Pair;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
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

        TopBarView topBarView = new TopBarView();
        borderPane.setTop(topBarView.getView());

        BottomBarView bottomBarView = new BottomBarView();
        borderPane.setBottom(bottomBarView.getView());

        /*
        CodePaneView codePaneView = new CodePaneView();
        Tab codeTab;
        if (DocumentModel.getInstance().getGraafVisFilePath() == null){
            codeTab = new Tab("New file", codePaneView.getView());
        } else {
            codeTab = new Tab(DocumentModel.getInstance().getGraafVisFilePath().getFileName().toString(), codePaneView.getView());
        }
        */
        GraafVisEditorView graafVisEditorView = new GraafVisEditorView();
        Tab codeTab;
        if (DocumentModel.getInstance().getGraafVisFilePath() == null){
            try {
                new File("temp/compiled").mkdirs();
                IOManager.saveVIS(Paths.get("temp/newfile.vis"),"");
                DocumentModel.getInstance().loadGraafVisFile(Paths.get("temp/newfile.vis"));
            } catch (IOException e) {
                e.printStackTrace();
            };
            codeTab = new Tab("New file", graafVisEditorView.getView());
        } else {
            codeTab = new Tab(DocumentModel.getInstance().getGraafVisFilePath().getFileName().toString(), graafVisEditorView.getView());
        }

        GraafVisEditorPresenter graafVisEditorPresenter = (GraafVisEditorPresenter) graafVisEditorView.getPresenter();
        graafVisEditorPresenter.getCodeArea().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                codeTab.setText(DocumentModel.getInstance().getGraafVisFilePath().getFileName().toString() + " *");
                DocumentModel.getInstance().setGraafvisChangesSaved(false);
        }
        });

        codeTab.setClosable(false);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tabPane.getTabs().add(codeTab);

        DocumentModel.getInstance().addObserver(this);
        CompilationModel.getInstance().addObserver(this);
        bind();


    }

    public void bind(){
        borderPane.prefWidthProperty().bind(viewModel.sceneWidthProperty());
        borderPane.prefHeightProperty().bind(viewModel.sceneHeigthProperty());
    }


    @Override
    public void update(Observable o, Object arg) {

        if (arg instanceof Pair) {
            Pair arguments = (Pair) arg;
            if (arguments.get(0) instanceof DocumentModelChange) {
                DocumentModelChange documentModelChange = (DocumentModelChange) arguments.get(0);
                switch (documentModelChange) {
                    case GRAAFVISFILELOADED:

                        DocumentModel.getInstance().setGraafvisChangesSaved(true);

                        GraafVisEditorView graafVisEditorView = new GraafVisEditorView();
                        ((StackPane) graafVisEditorView.getView()).prefWidthProperty().bind(tabPane.widthProperty());
                        ((StackPane) graafVisEditorView.getView()).prefHeightProperty().bind(tabPane.heightProperty());
                        String scriptName = DocumentModel.getInstance().getGraafVisFilePath().getFileName().toString();

                        Tab codeTab = new Tab(scriptName, graafVisEditorView.getView());
                        codeTab.setClosable(false);

                        GraafVisEditorPresenter graafVisEditorPresenter = (GraafVisEditorPresenter) graafVisEditorView.getPresenter();
                        graafVisEditorPresenter.getCodeArea().textProperty().addListener(new ChangeListener<String>() {
                            @Override
                            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                codeTab.setText(scriptName + " *");
                                DocumentModel.getInstance().setGraafvisChangesSaved(false);
                            }
                        });

                        tabPane.getTabs().set(0, codeTab);
                        break;
                    case SVGGENERATED:
                        Platform.runLater(() -> {

                            SVGViewerView svgViewerView = new SVGViewerView();
                            ((StackPane) svgViewerView.getView()).prefWidthProperty().bind(tabPane.widthProperty());
                            ((StackPane) svgViewerView.getView()).prefHeightProperty().bind(tabPane.heightProperty());

                            SVGViewerPresenter svgViewerPresenter = (SVGViewerPresenter) svgViewerView.getPresenter();

                            String svgName = (String) arguments.get(1);
                            svgViewerPresenter.loadContent(svgName, DocumentModel.getInstance().getGeneratedSVG(svgName));
                            svgViewerPresenter.showSVGAsImage();

                            Tab svgViewerTab = new Tab(svgName, svgViewerView.getView());
                            svgViewerTab.setClosable(true);
                            svgViewerTab.setOnCloseRequest(event -> {
                                if (! IOManager.showSVGSaveDialog(DocumentModel.getInstance().getGeneratedSVG(svgName))){
                                    event.consume();
                                }
                            });
                            svgViewerTab.setOnClosed(event -> DocumentModel.getInstance().removeGeneratedSVG(svgName));

                            final ContextMenu contextMenu = new ContextMenu();
                            MenuItem showAsImage = new MenuItem("Show as Image");
                            MenuItem showAsText = new MenuItem("Show as Text");
                            contextMenu.getItems().addAll(showAsImage, showAsText);
                            showAsImage.setOnAction(event -> svgViewerPresenter.showSVGAsImage());
                            showAsText.setOnAction(event -> svgViewerPresenter.showSVGAsText());

                            svgViewerTab.setContextMenu(contextMenu);

                            tabPane.getTabs().add(svgViewerTab);
                        });
                        break;
                    case GRAAFVISFILESAVED:
                        //scriptName = DocumentModel.getInstance().getGraafVisFilePath().getFileName().toString();
                        //tabPane.getTabs().get(0).setText(scriptName);
                }
            }
        }

        else if (arg instanceof CompilationProgress){
            CompilationProgress compilationProgress = (CompilationProgress) arg;
            switch (compilationProgress) {
                case COMPILATIONSTARTED:
                    System.out.println("compilation started");
                    CompilationModel.getInstance().addObserverToCompilation(this);
                    break;
                case DEBUGCOMPILATIONSTARTED:
                    System.out.println("debug compilation started");
                    CompilationModel.getInstance().addObserverToCompilation(this);
                    break;
                case GRAPHCONVERTED:
                    System.out.println("Graph converted");
                    if (CompilationModel.getInstance().getCompilation().isDebug() &&
                            CompilationModel.getInstance().getCompilation().getMaxProgress() == compilationProgress) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                RuleViewerView ruleViewerView = new RuleViewerView();
                                RuleViewerPresenter ruleViewerPresenter = (RuleViewerPresenter) ruleViewerView.getPresenter();
                                //ruleViewerPresenter.loadContent(CompilationModel.getInstance().getCompilation().getVisMap());
                                ((StackPane) ruleViewerView.getView()).prefWidthProperty().bind(tabPane.widthProperty());
                                ((StackPane) ruleViewerView.getView()).prefHeightProperty().bind(tabPane.heightProperty());
                                String graphName = CompilationModel.getInstance().getCompilation().getGraphFile().getFileName().toString().split("\\.")[0];
                                tabPane.getTabs().add(new Tab("Rules " + graphName, ruleViewerView.getView()));
                                //tabPane.getTabs().add(new Tab("Vis Elems " + graphName,visElemViewerView.getView()));
                            }
                        });
                    }
                    break;
                case GRAAFVISCOMPILED:
                    System.out.println("Graafvis compiled");
                    break;
                case SOLVED:
                    System.out.println("Logic solved");
                    if (CompilationModel.getInstance().getCompilation().isDebug() &&
                            CompilationModel.getInstance().getCompilation().getMaxProgress() == compilationProgress){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                VisElemViewerView visElemViewerView = new VisElemViewerView();
                                VisElemViewerPresenter visElemViewerPresenter = (VisElemViewerPresenter) visElemViewerView.getPresenter();
                                visElemViewerPresenter.loadContent(CompilationModel.getInstance().getCompilation().getVisMap());
                                ((StackPane) visElemViewerView.getView()).prefWidthProperty().bind(tabPane.widthProperty());
                                ((StackPane) visElemViewerView.getView()).prefHeightProperty().bind(tabPane.heightProperty());
                                String graphName = CompilationModel.getInstance().getCompilation().getGraphFile().getFileName().toString().split("\\.")[0];
                                tabPane.getTabs().add(new Tab("Vis Elems " + graphName,visElemViewerView.getView()));
                            }
                        });
                    }
                    break;
                case SVGGENERATED:
                    System.out.println("SVG generated");
                    break;
                case COMPILATIONFINISHED:
                    System.out.println("Compilation complete");
                    break;
                case ERROROCCURED:
                    System.out.println("Error occured");
                    break;
            }
        }
    }
}
