package screens.idescreen.codepane;

import general.Model;
import general.ViewModel;
import general.files.DocumentModel;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class CodePanePresenter implements Initializable {

   @Inject ViewModel viewModel;
    public TextArea codeTextArea;

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
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String code = sb.toString();
            codeTextArea.setText(code);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
