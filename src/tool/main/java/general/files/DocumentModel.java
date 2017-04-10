package general.files;

import utils.Pair;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DocumentModel extends Observable{


    private Path graafVisFilePath;
    private Map<String, Path> graphFileMap = new HashMap<>();
    private LinkedList<Pair<String,Path>> graphFileList = new LinkedList<Pair<String,Path>>(); //Serves as a FIFO Queue;
    private Map<String, Path> generatedSVGMap = new HashMap<>();
    private Map<String, Integer> generatedSVGCounterMap = new HashMap<>(); //To make sure 2 files don't have the same name.
    public String graafVisCode;

    private Path lastSaveAndLoadPathGraafVis;
    private Path lastSavePathVisualization;
    private Path lastLoadPathGraph;

    public Path getGraafVisFilePath() {
        return graafVisFilePath;
    }

    public void loadGraafVisFile(Path graafVisFilePath) {
        this.graafVisFilePath = graafVisFilePath;
        setChanged();
        notifyObservers(new Pair<>(DocumentModelChange.GRAAFVISFILELOADED, graafVisFilePath.getFileName().toString()));
    }

    public int generateSVGCounter(String name){
        if (generatedSVGCounterMap.containsKey(name)){
            int key = generatedSVGCounterMap.get(name);
            key++;
            generatedSVGCounterMap.put(name, key);
            return key;
        } else {
            generatedSVGCounterMap.put(name, 1);
            return 0;
        }
    }

    public void newGraafVisFile(){
        new File("temp/newfile.vis").mkdir();
        Path path = new File("temp/newfile.vis").toPath();
        DocumentModel.getInstance().loadGraafVisFile(path);
        setChanged();
        notifyObservers(new Pair<>(DocumentModelChange.GRAAFVISFILECREATED,null));
    }

    public Map<String,Path> getGraphPathMap() {
        return graphFileMap;
    }

    

    public void loadGraph(Path graphPath) {
        graphFileMap.put(graphPath.getFileName().toString(), graphPath);
        graphFileList.addFirst(new Pair<>(graphPath.getFileName().toString(), graphPath));
        if(graphFileList.size() == 11){
            Pair<String,Path> removedGraph = graphFileList.removeLast();
            setChanged();
            notifyObservers(new Pair<>(DocumentModelChange.GRAPHFILEREMOVED, removedGraph.getFirst()));
        }
        setChanged();
        notifyObservers(new Pair<>(DocumentModelChange.GRAPHFILELOADED, graphPath.getFileName().toString() ));
    }

    public void removeAllGraphs(){
        graphFileMap = new HashMap<>();
        setChanged();
        notifyObservers(new Pair<>(DocumentModelChange.GRAPHFILEREMOVEDALL,null));
    }

    public void loadAllGraph(List<Path> graphPaths) {
        for (Path graphPath: graphPaths) {
            loadGraph(graphPath);
        }
    }

    public void removeGraph(String name){
        graphFileMap.remove(name);
        setChanged();
        notifyObservers(new Pair<>(DocumentModelChange.GRAPHFILEREMOVED,name));
    }

    public void addGeneratedSVG(Path path){
        generatedSVGMap.put(path.getFileName().toString(), path);
        setChanged();
        notifyObservers(new Pair<>(DocumentModelChange.SVGGENERATED, path.getFileName().toString()));
    }

    public Path getGeneratedSVG(String name) {
        return generatedSVGMap.get(name);
    }

    public void removeGeneratedSVG(String name) {
        generatedSVGMap.remove(name);
        setChanged();
        notifyObservers(new Pair<>(DocumentModelChange.SVGREMOVED, name));
    }

    public Path getLastSaveAndLoadPathGraafVis() {
        return lastSaveAndLoadPathGraafVis;
    }

    public void setLastSaveAndLoadPathGraafVis(Path lastSaveAndLoadPathGraafVis) {
        this.lastSaveAndLoadPathGraafVis = lastSaveAndLoadPathGraafVis;
    }

    public Path getLastSavePathVisualization() {
        return lastSavePathVisualization;
    }

    public void setLastSavePathVisualization(Path lastSavePathVisualization) {
        this.lastSavePathVisualization = lastSavePathVisualization;
    }


    public Path getLastLoadPathGraph() {
        return lastLoadPathGraph;
    }

    public void setLastLoadPathGraph(Path lastLoadPathGraph) {
        this.lastLoadPathGraph = lastLoadPathGraph;
    }

    private static DocumentModel ourInstance = new DocumentModel();

    public static DocumentModel getInstance() {
        return ourInstance;
    }

    private DocumentModel() {
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        lastLoadPathGraph = Paths.get(path);
        lastSaveAndLoadPathGraafVis = Paths.get(path);
        lastSavePathVisualization = Paths.get(path);
    }
}
