package screens.idescreen.codepane;

import general.Model;
import general.ViewModel;
import general.files.DocumentModel;
import general.files.LoaderUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class CodePanePresenter implements Initializable {

   @Inject ViewModel viewModel;
    public TextArea codeTextArea;
    public RSyntaxTextArea codeTextArea2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Path graafVisFilePath = DocumentModel.getInstance().getGraafVisFilePath();
        Model.getInstance().setCodePaneTextArea(codeTextArea);
        if (graafVisFilePath != null) {
            setText(graafVisFilePath);
        }

        bind();
    }

    public void bind(){
        codeTextArea.prefWidthProperty().bind( ((Pane) (viewModel.getMainView()).getParent()).widthProperty() );
        codeTextArea.prefHeightProperty().bind( ((Pane) (viewModel.getMainView()).getParent()).heightProperty() );
    }

    public void setText(Path path) {
        try {
            codeTextArea.setText(LoaderUtils.readFile(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
