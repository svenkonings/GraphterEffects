package screens.idescreen.codepane;

import general.Model;
import general.ViewModel;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import javax.inject.Inject;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CodePanePresenter implements Initializable {

   @Inject ViewModel viewModel;
    public TextArea codeTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File graafVisFile = Model.getInstance().getGraafVisFile();
        Model.getInstance().setCodePaneTextArea(codeTextArea);
        if (graafVisFile != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(graafVisFile))) {
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

        bind();
    }

    public void bind(){
        codeTextArea.prefWidthProperty().bind( ((Pane) (viewModel.getMainView()).getParent()).widthProperty() );
        codeTextArea.prefHeightProperty().bind( ((Pane) (viewModel.getMainView()).getParent()).heightProperty() );
    }

}
