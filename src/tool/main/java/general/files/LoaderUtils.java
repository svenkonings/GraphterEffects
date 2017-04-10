package general.files;

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

public class LoaderUtils {

    private static FileChooser.ExtensionFilter allFilesFilter = new FileChooser.ExtensionFilter("All files", "*");;
    private static FileChooser.ExtensionFilter visFilesFilter = new FileChooser.ExtensionFilter("VIS files (*.vis)", "*.VIS", "*.vis");;
    private static FileChooser.ExtensionFilter graphFilesFilter = new FileChooser.ExtensionFilter("Graph files (*.dot, ... )", "*.DOT", "*.dot");;
    private static FileChooser.ExtensionFilter dotFilesFilter = new FileChooser.ExtensionFilter("DOT files (*.dot)", "*.DOT", "*.dot");
    private static FileChooser.ExtensionFilter svgFilesFilter = new FileChooser.ExtensionFilter("SVG files (*.svg)", "*.svg");

    public static void showLoadScriptPopup() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select GraafVis Script");
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
//        path += "/tests";
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
//        path += "/tests";
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

    public static void showSaveScriptPopup(Path path, String codefile) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save GraafVis Script");
        fileChooser.getExtensionFilters().add(visFilesFilter);
        fileChooser.setInitialFileName(path.getFileName().toString());
        File fileLocation = fileChooser.showSaveDialog(new Stage());
        List<String> codeList = new ArrayList<>();
        codeList.add(codefile);

        try {
            Files.write(fileLocation.toPath(), codeList, Charset.forName("UTF-8"));
            DocumentModel.getInstance().loadGraafVisFile(path);
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
        fileChooser.setInitialFileName(svgPath.getFileName().toString());
        File saveLocation = fileChooser.showSaveDialog(new Stage());
        List<String> codeList = new ArrayList<>();


        try {
            codeList.add(readFile(svgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.write(saveLocation.toPath(), codeList, Charset.forName("UTF-8"));
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
}
