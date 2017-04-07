package screens.idescreen;

import general.StageHistory;
import general.ViewModel;
import general.compiler.CompilationModel;
import general.compiler.CompilationProgress;
import general.files.DocumentModel;
import general.files.DocumentModelChange;
import general.files.LoaderUtils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import screens.idescreen.bottombar.BottomBarView;
import screens.idescreen.graafviseditor.GraafVisEditorView;
import screens.idescreen.newsvgviewer.SVGViewerView;
import screens.idescreen.topbar.TopBarView;
import utils.Pair;

import javax.inject.Inject;
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
                LoaderUtils.saveVIS(Paths.get("/temp/newfile.vis"),"");
                DocumentModel.getInstance().loadGraafVisFile(Paths.get("/temp/newfile.vis"));
            } catch (IOException e) {
                e.printStackTrace();
            };
            codeTab = new Tab("New file", graafVisEditorView.getView());
        } else {
            codeTab = new Tab(DocumentModel.getInstance().getGraafVisFilePath().getFileName().toString(), graafVisEditorView.getView());
        }

        codeTab.setClosable(false);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tabPane.getTabs().add(codeTab);

        DocumentModel.getInstance().addObserver(this);
        CompilationModel.getInstance().addObserver(this);

        bind();


    }

    public void bind(){
        //borderPane.widthProperty().a
        borderPane.prefWidthProperty().bind( ((Pane) (viewModel.getMainView()).getParent()).widthProperty() );
        borderPane.prefHeightProperty().bind( ((Pane) (viewModel.getMainView()).getParent()).heightProperty() );
        borderPane.prefWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("IDE BorderPane Pref Width: " + newValue);
            }
        });
        //borderPane.heightProperty().isEqualTo(viewModel.getMainView().getLayoutBounds().getHeight(),0);
    }


    @Override
    public void update(Observable o, Object arg) {

        if (arg instanceof Pair) {
            Pair arguments = (Pair) arg;
            if (arguments.get(0) instanceof DocumentModelChange) {
                DocumentModelChange documentModelChange = (DocumentModelChange) arguments.get(0);
                switch (documentModelChange) {
                    case GRAAFVISFILELOADED:
                        GraafVisEditorView graafVisEditorView = new GraafVisEditorView();
                        Tab codeTab = new Tab(DocumentModel.getInstance().getGraafVisFilePath().getFileName().toString(), graafVisEditorView.getView());
                        codeTab.setClosable(false);
                        tabPane.getTabs().set(0, codeTab);
                        break;
                    case SVGGENERATED:
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                //Generate and load the content in the SVGViewerView2
                                SVGViewerView svgViewerView = new SVGViewerView();

                                String svgName = (String) arguments.get(1);
                                System.out.println("TEST:" + svgName);
                                //svgViewerPresenter2.loadContent(svgName);

                                BorderPane borderPane = ((BorderPane) viewModel.getMainView());
                                TabPane tabPane = (TabPane) borderPane.getCenter();

                                //Create the tabPane
                                Tab svgViewerTab = new Tab(svgName, svgViewerView.getView());
                                svgViewerTab.setClosable(true);
                                svgViewerTab.setOnClosed(new EventHandler<Event>() {
                                    @Override
                                    public void handle(Event event) {
                                        DocumentModel.getInstance().removeGeneratedSVG(svgName);
                                    }
                                });

                                tabPane.getTabs().add(svgViewerTab);
                            }
                        });
                        break;
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
                case GRAPHCONVERTED:
                    System.out.println("Graph converted");
                    break;
                case GRAAFVISCOMPILED:
                    System.out.println("Graafvis compiled");
                    break;
                case SOLVED:
                    System.out.println("Logic solved");
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