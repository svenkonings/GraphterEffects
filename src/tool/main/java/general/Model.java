package general;

import javafx.scene.control.TextArea;
import org.dom4j.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Model {


    private File graafVisFile;
    private List<File> graphFileList = new ArrayList<>();
    private TextArea codePaneTextArea;
    private Document generatedSVG;


    public File getGraafVisFile() {
        return graafVisFile;
    }

    public void setGraafVisFile(File graafVisFile) {
        this.graafVisFile = graafVisFile;
    }


    public List<File> getGraphFileList() {
        return graphFileList;
    }

    public void setGraphFileList(List<File> graphFileList) {
        this.graphFileList = graphFileList;
    }



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

    public Document getGeneratedSVG() {
        return generatedSVG;
    }

    public void setGeneratedSVG(Document generatedSVG) {
        this.generatedSVG = generatedSVG;
    }
}
