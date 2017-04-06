package general.files;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LoaderUtils {

    private static FileChooser.ExtensionFilter allFilesFilter = new FileChooser.ExtensionFilter("All files", "*");;
    private static FileChooser.ExtensionFilter visFilesFilter = new FileChooser.ExtensionFilter("VIS files (*.vis)", "*.VIS", "*.vis");;
    private static FileChooser.ExtensionFilter graphFilesFilter = new FileChooser.ExtensionFilter("Graph files (*.dot, ... )", "*.DOT", "*.dot");;
    private static FileChooser.ExtensionFilter dotFilesFilter = new FileChooser.ExtensionFilter("DOT files (*.dot)", "*.DOT", "*.dot");

    public static void showLoadScriptPopup() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select GraafVis Script");
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        path += "/tests";
        fileChooser.setInitialDirectory(new File(path));

        fileChooser.getExtensionFilters().add(visFilesFilter);
        fileChooser.getExtensionFilters().add(allFilesFilter);
        //fileChooser.getExtensionFilters().add(FileChooser.ExtensionF)

        File script = fileChooser.showOpenDialog(new Stage());
        if (script != null) {
            DocumentModel.getInstance().loadGraafVisFile(script.toPath());
        }
    }

    public static void showLoadGraphsPopup(boolean replaceExistingFiles) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Graph(s)");
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        path += "/tests";
        fileChooser.setInitialDirectory(new File(path));

        fileChooser.getExtensionFilters().add(graphFilesFilter);
        fileChooser.getExtensionFilters().add(dotFilesFilter);
        fileChooser.getExtensionFilters().add(allFilesFilter);

        List<File> graphFileList = fileChooser.showOpenMultipleDialog(new Stage());
        List<Path> graphPathList = new ArrayList<>();

        for (File file: graphFileList){
            graphPathList.add(file.toPath());
        }

        if (graphFileList != null){
            if (replaceExistingFiles) {
                DocumentModel.getInstance().removeAllGraphs();
                DocumentModel.getInstance().loadAllGraph(graphPathList);
            }
            else {
                DocumentModel.getInstance().loadAllGraph(graphPathList);
            }
        }
    }

    public static void showSaveScriptPopup(String code) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save GraafVis Script");
        File fileLocation = fileChooser.showSaveDialog(new Stage());
        List<String> codeList = new ArrayList<>();
        codeList.add(code);

        Path path = fileLocation.toPath();
        try {
            Files.write(path, codeList, Charset.forName("UTF-8"));
            DocumentModel.getInstance().loadGraafVisFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
