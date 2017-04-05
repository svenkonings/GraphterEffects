package general;

import org.dom4j.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class DocumentModel extends Observable{


    private File graafVisFile;
    private List<File> graphFileList = new ArrayList<>();
    private Document generatedSVG;


    public File getGraafVisFile() {
        return graafVisFile;
    }

    public void setGraafVisFile(File graafVisFile) {
        this.graafVisFile = graafVisFile;
        setChanged();
        notifyObservers("scriptLoaded");
    }

    public void newGraafVisFile(){
        this.graafVisFile = null;
        setChanged();
        //notifyObservers("newScriptLoaded");
    }

    public List<File> getGraphFileList() {
        return graphFileList;
    }

    public void setGraphFileList(List<File> graphFileList) {
        this.graphFileList = graphFileList;
        setChanged();
        notifyObservers("graphFilesLoaded");
    }

    public Document getGeneratedSVG() {
        return generatedSVG;
    }

    public void setGeneratedSVG(Document generatedSVG) {
        this.generatedSVG = generatedSVG;
        setChanged();
        notifyObservers("svgGenerated");
    }

    private static DocumentModel ourInstance = new DocumentModel();

    public static DocumentModel getInstance() {
        return ourInstance;
    }

    private DocumentModel() {
    }
}
