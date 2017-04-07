package screens.idescreen.newsvgviewer;

import javafx.fxml.Initializable;
import javafx.scene.web.WebView;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SVGViewerPresenter implements Initializable {
    public WebView webView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            System.out.println(FileUtils.readFromFile(new File("demo1.svg")));
            webView.getEngine().loadContent(FileUtils.readFromFile(new File("demo1.svg") ));
            webView.setContextMenuEnabled(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
