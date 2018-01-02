package com.github.meteoorkip.general.files;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IOManager {

    private static FileChooser.ExtensionFilter allFilesFilter = new FileChooser.ExtensionFilter("All files", "*");
    private static FileChooser.ExtensionFilter visFilesFilter = new FileChooser.ExtensionFilter("VIS files (*.vis)", "*.VIS", "*.vis");
    private static FileChooser.ExtensionFilter graphFilesFilter = new FileChooser.ExtensionFilter("Graph files (*.dot, ... )", "*.DOT", "*.dot","*.gxl","*.GXL");
    private static FileChooser.ExtensionFilter dotFilesFilter = new FileChooser.ExtensionFilter("DOT files (*.dot)", "*.DOT", "*.dot");
    private static FileChooser.ExtensionFilter svgFilesFilter = new FileChooser.ExtensionFilter("SVG files (*.svg)", "*.svg");

    public static void showLoadScriptPopup() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select GraafVis Script");

        fileChooser.setInitialDirectory(FileModel.getInstance().getDefaultDirectoryPath().toFile());
        //System.out.println((FileModel.getInstance().getLastSaveAndLoadPathGraafVis().toFile()));

        fileChooser.getExtensionFilters().add(visFilesFilter);
        fileChooser.getExtensionFilters().add(allFilesFilter);
        //fileChooser.getExtensionFilters().add(FileChooser.ExtensionF)

        File script = fileChooser.showOpenDialog(new Stage());
        if (script != null) {
            FileModel.getInstance().loadGraafVisFile(script.toPath());
            //Path subpath = script.toPath().subpath(0, script.toPath().getNameCount()-1);
            //FileModel.getInstance().setLastSaveAndLoadPathGraafVis(subpath);
        }
    }

    public static void showLoadGraphsPopup(boolean replaceExistingFiles) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Graph(s)");
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
//        path += "/tests";
        fileChooser.setInitialDirectory(new File(path));

        fileChooser.getExtensionFilters().add(graphFilesFilter);
        fileChooser.getExtensionFilters().add(dotFilesFilter);
        fileChooser.getExtensionFilters().add(allFilesFilter);

        List<File> graphFileList = fileChooser.showOpenMultipleDialog(new Stage());
        List<Path> graphPathList = new ArrayList<>();

        if (graphFileList != null){

            for (File file: graphFileList){
                graphPathList.add(file.toPath());
            }
            //FileModel.getInstance().setLastSaveAndLoadPathGraafVis(graphPathList.get(0).subpath(0,graphPathList.get(0).getNameCount()-1));

            if (replaceExistingFiles) {
                FileModel.getInstance().removeAllGraphs();
                FileModel.getInstance().loadAllGraph(graphPathList);
            }
            else {
                FileModel.getInstance().loadAllGraph(graphPathList);
            }
        }
    }

    public static void showSaveScriptPopup(Path path, String codefile) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save GraafVis Script");
        fileChooser.getExtensionFilters().add(visFilesFilter);
        fileChooser.setInitialDirectory(FileModel.getInstance().getDefaultDirectoryPath().toFile());
        fileChooser.setInitialFileName(path.getFileName().toString());
        File fileLocation = fileChooser.showSaveDialog(new Stage());
        List<String> codeList = new ArrayList<>();
        codeList.add(codefile);

        try {
            if (fileLocation != null) {
                Files.write(fileLocation.toPath(), codeList, Charset.forName("UTF-8"));
                FileModel.getInstance().loadGraafVisFile(fileLocation.toPath());///FileModel.getInstance().setLastSaveAndLoadPathGraafVis(path.subpath(0, path.getNameCount()-1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveSVG(Path path, String xml) throws IOException {
        List<String> codeList = new ArrayList<>();
        codeList.add(xml);
        Files.write(path, codeList, Charset.forName("UTF-8"));
    }

    public static void saveVIS(Path path, String code) throws IOException {
        List<String> codeList = new ArrayList<>();
        codeList.add(code);
        Files.write(path, codeList, Charset.forName("UTF-8"));
    }

    public static void showSaveSVGPopup(Path svgPath) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Generated Visualization");
        fileChooser.getExtensionFilters().add(svgFilesFilter);
        fileChooser.setInitialDirectory(FileModel.getInstance().getDefaultDirectoryPath().toFile());
        fileChooser.setInitialFileName(svgPath.getFileName().toString());
        File saveLocation = fileChooser.showSaveDialog(new Stage());
        List<String> codeList = new ArrayList<>();


        try {
            codeList.add(readFile(svgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (saveLocation != null) {
                Files.write(saveLocation.toPath(), codeList, Charset.forName("UTF-8"));
                //FileModel.getInstance().setLastSavePathVisualization(saveLocation.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(Path path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }

    public static boolean showSVGSaveDialog(Path path) {
        String filename = path.getFileName().toString();
        Optional<ButtonType> result = showSaveDialog(filename);
        final boolean[] returnValue = {false};
        result.ifPresent(buttonType -> {
            if (buttonType.getButtonData().equals(ButtonBar.ButtonData.YES)) {
                showSaveSVGPopup(path);
                returnValue[0] = true;
            } else returnValue[0] = buttonType.getButtonData().equals(ButtonBar.ButtonData.NO);
        });
        return returnValue[0];
    }

    public static boolean showGraafvisScriptSaveDialog(Path path, String codeFile) {
        String filename = path.getFileName().toString();
        Optional<ButtonType> result = showSaveDialog(filename);
        final boolean[] returnValue = {false};
        result.ifPresent(buttonType -> {
            if (buttonType.getButtonData().equals(ButtonBar.ButtonData.YES)) {
                showSaveScriptPopup(path, codeFile);
                returnValue[0] = true;
            } else returnValue[0] = buttonType.getButtonData().equals(ButtonBar.ButtonData.NO);
        });
        return returnValue[0];
    }

    private static Optional<ButtonType> showSaveDialog(String filename){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Graphter Effects");
        alert.setHeaderText(null);

        alert.setContentText("Save changes to " + filename + " before continuing?");

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        //noinspection ConstantConditions
        stage.getIcons().add(
                new Image(IOManager.class.getClassLoader().getResource("graphter_effects_logo.png").toExternalForm()));

        return alert.showAndWait();

    }
}
