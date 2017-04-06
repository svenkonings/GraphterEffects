package general;

import javafx.scene.control.TextArea;

public class Model {

    private TextArea codePaneTextArea;



    private static Model ourInstance = new Model();

    public static Model getInstance() {
        return ourInstance;
    }

    private Model() {
    }

    public TextArea getCodePaneTextArea() {
        return codePaneTextArea;
    }

    public void setCodePaneTextArea(TextArea codePaneTextArea) {
        this.codePaneTextArea = codePaneTextArea;
    }
}
