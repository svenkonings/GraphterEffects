import com.airhacks.afterburner.injection.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import root.RootView;

public class App extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception {

        RootView rootView = new RootView();
        Scene scene = new Scene(rootView.getView());
        primaryStage.setTitle("Graphter Effects");
        final String uri = getClass().getResource("app.css").toExternalForm();


        scene.getStylesheets().add(uri);
        primaryStage.setScene(scene);
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
