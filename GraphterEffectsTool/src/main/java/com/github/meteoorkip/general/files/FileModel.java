package com.github.meteoorkip.general.files;

import com.github.meteoorkip.utils.Pair;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileModel extends Observable{



    private Path graafVisFilePath;
    private Map<String, Path> graphFileMap = new HashMap<>();
    private LinkedList<Pair<String,Path>> graphFileList = new LinkedList<>(); //Serves as a FIFO Queue;
    private Map<String, Path> generatedSVGMap = new HashMap<>();
    private Map<String, Integer> generatedSVGCounterMap = new HashMap<>(); //To make sure 2 files don't have the same name.
    public String graafVisCode;
    private boolean changesSaved = true;
    private Path selectedGraph;
    private final Path defaultDirectoryPath;

    public Path getGraafVisFilePath() {
        return graafVisFilePath;
    }

    public void loadGraafVisFile(Path graafVisFilePath) {
        this.graafVisFilePath = graafVisFilePath;
        setChanged();
        notifyObservers(new Pair<>(FileModelChange.GRAAFVISFILELOADED, graafVisFilePath.getFileName().toString()));
    }

    public synchronized int generateSVGCounter(String name){
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
        File newGraafvisFile = null;
        try {
            newGraafvisFile = new File("temp/newfile.vis");
            //noinspection ResultOfMethodCallIgnored
            newGraafvisFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path path = newGraafvisFile.toPath();
        FileModel.getInstance().loadGraafVisFile(path);
        setChanged();
        notifyObservers(new Pair<>(FileModelChange.GRAAFVISFILECREATED,null));
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
            notifyObservers(new Pair<>(FileModelChange.GRAPHFILEREMOVED, removedGraph.getFirst()));
        }
        setChanged();
        notifyObservers(new Pair<>(FileModelChange.GRAPHFILELOADED, graphPath.getFileName().toString() ));
    }

    public void removeAllGraphs(){
        graphFileMap = new HashMap<>();
        setChanged();
        notifyObservers(new Pair<>(FileModelChange.GRAPHFILEREMOVEDALL,null));
    }

    public void loadAllGraph(List<Path> graphPaths) {
        for (Path graphPath: graphPaths) {
            loadGraph(graphPath);
        }
    }

    public void removeGraph(String name){
        graphFileMap.remove(name);
        setChanged();
        notifyObservers(new Pair<>(FileModelChange.GRAPHFILEREMOVED,name));
    }

    public void addGeneratedSVG(Path path){
        generatedSVGMap.put(path.getFileName().toString(), path);
        setChanged();
        notifyObservers(new Pair<>(FileModelChange.SVGGENERATED, path.getFileName().toString()));
    }

    public Path getGeneratedSVG(String name) {
        return generatedSVGMap.get(name);
    }

    public void removeGeneratedSVG(String name) {
        generatedSVGMap.remove(name);
        setChanged();
        notifyObservers(new Pair<>(FileModelChange.SVGREMOVED, name));
    }

    public Map<String,Path> getAllGeneratedSVGS(){
        return generatedSVGMap;
    }

    public boolean graafvisChangesSaved(){
        return changesSaved;
    }

    public void setGraafvisChangesSaved(boolean changesSaved){
        this.changesSaved = changesSaved;
    }


    public void setSelectedGraph(String name){
        this.selectedGraph = graphFileMap.get(name);
    }

    public Path getSelectedGraph(){
        return selectedGraph;
    }

    public Path getDefaultDirectoryPath() {
        return defaultDirectoryPath;
    }

    private static FileModel ourInstance = new FileModel();

    public static FileModel getInstance() {
        return ourInstance;
    }

    private FileModel() {
        defaultDirectoryPath = Paths.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath());
    }


}
