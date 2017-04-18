package screens.idescreen;

import general.ViewModel;
import general.compiler.CompilationModel;
import general.compiler.CompilationProgress;
import general.files.DocumentModel;
import general.files.DocumentModelChange;
import general.files.IOManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import screens.idescreen.bottombar.BottomBarView;
import screens.idescreen.tab.graafviseditor.GraafVisEditorPresenter;
import screens.idescreen.tab.graafviseditor.GraafVisEditorView;
import screens.idescreen.tab.ruleviewer.RuleViewerView;
import screens.idescreen.tab.svgviewer.SVGViewerPresenter;
import screens.idescreen.tab.svgviewer.SVGViewerView;
import screens.idescreen.topbar.TopBarView;
import screens.idescreen.viselemviewer.VisElemViewerPresenter;
import screens.idescreen.viselemviewer.VisElemViewerView;
import utils.Pair;

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

        DocumentModel.getInstance().addObserver(this);
        CompilationModel.getInstance().addObserver(this);

    }

    private void bind(){
        borderPane.prefWidthProperty().bind(viewModel.sceneWidthProperty());
        borderPane.prefHeightProperty().bind(viewModel.sceneHeigthProperty());
    }

    public static Tab createGraafvisEditorTab(){
        GraafVisEditorView graafVisEditorView = new GraafVisEditorView();
        String graafVisScriptName = DocumentModel.getInstance().getGraafVisFilePath().getFileName().toString();
        Tab codeTab = new Tab(graafVisScriptName, graafVisEditorView.getView());

        GraafVisEditorPresenter graafVisEditorPresenter = (GraafVisEditorPresenter) graafVisEditorView.getPresenter();
        graafVisEditorPresenter.getCodeArea().textProperty().addListener((observable, oldValue, newValue) -> {
            codeTab.setText(graafVisScriptName + " *");
            DocumentModel.getInstance().setGraafvisChangesSaved(false);
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
        String graphName = CompilationModel.getInstance().getCompilation().getGraphFile().getFileName().toString().split("\\.")[0];

        return new Tab("Rules " + graphName, ruleViewerView.getView());
    }

    public static Tab createVisElemViewerTab(){
        VisElemViewerView visElemViewerView = new VisElemViewerView();
        VisElemViewerPresenter visElemViewerPresenter = (VisElemViewerPresenter) visElemViewerView.getPresenter();
        visElemViewerPresenter.loadContent(CompilationModel.getInstance().getCompilation().getVisMap());
        //((StackPane) visElemViewerView.getView()).prefWidthProperty().bind(tabPane.widthProperty());
        //((StackPane) visElemViewerView.getView()).prefHeightProperty().bind(tabPane.heightProperty());
        String graphName = CompilationModel.getInstance().getCompilation().getGraphFile().getFileName().toString().split("\\.")[0];

        return new Tab("Vis Elems " + graphName,visElemViewerView.getView());
    }

    @Override
    public void update(Observable o, Object arg) {

        //TODO: ADD LOGGING
        if (arg instanceof Pair) {
            Pair arguments = (Pair) arg;
            if (arguments.get(0) instanceof DocumentModelChange) {
                DocumentModelChange documentModelChange = (DocumentModelChange) arguments.get(0);
                switch (documentModelChange) {
                    case GRAAFVISFILELOADED:
                        DocumentModel.getInstance().setGraafvisChangesSaved(true);
                        Tab codeTab = createGraafvisEditorTab();
                        tabPane.getTabs().set(0, codeTab);
                        break;

                    case SVGGENERATED:
                        Platform.runLater(() -> {
                            Tab svgViewerTab = createSVGViewerTab((String) arguments.get(1));
                            tabPane.getTabs().add(svgViewerTab);
                        });
                        break;
                    case GRAAFVISFILESAVED:
                }
            }
        }

        else if (arg instanceof CompilationProgress){
            CompilationProgress compilationProgress = (CompilationProgress) arg;
            switch (compilationProgress) {
                case COMPILATIONSTARTED:
                    //TODO LOGGING: System.out.println("compilation started");
                    CompilationModel.getInstance().addObserverToCompilation(this);
                    break;
                case DEBUGCOMPILATIONSTARTED:
                    //TODO LOGGING: System.out.println("debug compilation started");
                    CompilationModel.getInstance().addObserverToCompilation(this);
                    break;
                case GRAPHCONVERTED:
                    //TODO LOGGING: System.out.println("Graph converted");
                    if (CompilationModel.getInstance().getCompilation().isDebug() &&
                            CompilationModel.getInstance().getCompilation().getMaxProgress() == compilationProgress) {
                        Platform.runLater(() -> {
                            tabPane.getTabs().add(createRuleViewerTab());
                        });
                    }
                    break;
                case GRAAFVISCOMPILED:
                    //TODO: LOGGING System.out.println("Graafvis compiled");
                    break;
                case SOLVED:
                    //TODO: LOGGING System.out.println("Logic solved");
                    if (CompilationModel.getInstance().getCompilation().isDebug() &&
                            CompilationModel.getInstance().getCompilation().getMaxProgress() == compilationProgress){
                        Platform.runLater(() -> {
                            tabPane.getTabs().add(createVisElemViewerTab());
                        });
                    }
                    break;
                case SVGGENERATED:
                    //TODO: LOGGING System.out.println("SVG generated");
                    break;
                case COMPILATIONFINISHED:
                    //TODO: LOGGING System.out.println("Compilation complete");
                    break;
                case ERROROCCURED:
                    //TODO: Show error screen
                    System.out.println("Error occured");
                    break;
            }
        }
    }
}
