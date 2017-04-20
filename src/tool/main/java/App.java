import com.airhacks.afterburner.injection.Injector;
import general.files.DocumentModel;
import general.files.IOManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import root.RootPresenter;
import root.RootView;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class App extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        RootView rootView = new RootView();
        Scene scene = new Scene(rootView.getView());
        //viewModel.setMainView(scene.getRoot());
        primaryStage.setTitle("Graphter Effects");
        final String uri = getClass().getResource("app.css").toExternalForm();
        primaryStage.getIcons().add(new Image(getClass().getResource("graphter_effects_logo.png").toExternalForm()));


        scene.getStylesheets().add(uri);
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(event -> {
            if (!DocumentModel.getInstance().graafvisChangesSaved()){
                if(!IOManager.showGraafvisScriptSaveDialog(DocumentModel.getInstance().getGraafVisFilePath(), DocumentModel.getInstance().graafVisCode)){
                    event.consume();
                    return;
                }
            }
            Set<String> svgNames = new HashSet<>(DocumentModel.getInstance().getAllGeneratedSVGS().keySet());
            for (String svgName: svgNames){
                if(IOManager.showSVGSaveDialog(DocumentModel.getInstance().getGeneratedSVG(svgName))){
                    DocumentModel.getInstance().getAllGeneratedSVGS().remove(svgName);
                } else {
                    event.consume();
                    return;
                }
            }
            try {
                FileUtils.deleteDirectory(new File("temp"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
        primaryStage.show();

        //Preparing the files.
        //noinspection ResultOfMethodCallIgnored
        new File("temp/compiled").mkdirs();
        DocumentModel.getInstance().newGraafVisFile();

        ((RootPresenter) rootView.getPresenter()).loadIDE();
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
