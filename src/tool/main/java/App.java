import com.airhacks.afterburner.injection.Injector;
import general.files.DocumentModel;
import general.files.IOManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import root.RootView;

import java.util.Set;

public class App extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception {
        RootView rootView = new RootView();
        Scene scene = new Scene(rootView.getView());
        primaryStage.setTitle("Graphter Effects");
        final String uri = getClass().getResource("app.css").toExternalForm();
        primaryStage.getIcons().add(new Image(getClass().getResource("graphter_effects_logo.png").toExternalForm()));


        scene.getStylesheets().add(uri);
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (!DocumentModel.getInstance().graafvisChangesSaved()){
                    if(!IOManager.showGraafvisScriptSaveDialog(DocumentModel.getInstance().getGraafVisFilePath(), DocumentModel.getInstance().graafVisCode)){
                        event.consume();
                        return;
                    }
                }
                Set<String> svgNames = DocumentModel.getInstance().getAllGeneratedSVGS().keySet();
                for (String svgName: svgNames){
                    if(IOManager.showSVGSaveDialog(DocumentModel.getInstance().getGeneratedSVG(svgName))){
                        DocumentModel.getInstance().getAllGeneratedSVGS().remove(svgName);
                    } else {
                        event.consume();
                        return;
                    }
                }
                System.exit(0);
            }
        });
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
