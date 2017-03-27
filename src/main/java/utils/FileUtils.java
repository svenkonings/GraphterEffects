package utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Base64;

public final class FileUtils {


    public static String getExtension(String filename) {
        if (!filename.contains(".")) {
            return filename;
        }
        return getExtension(filename.substring(filename.indexOf(".")+1));
    }

    public static File fromResources(String path) throws IOException {
        URL url = FileUtils.class.getClassLoader().getResource(path);
        if (url == null) {
            throw new IOException("Resource file " + path + " not found.");
        }
        return new File(url.getFile());
    }

    public static List<File> recursiveInDirectory(File dir) {
        List<File> res = new LinkedList<>();
        if (dir.isFile()) {
            res.add(dir);
            return res;
        }
        File[] directoryListing = dir.listFiles();
        for (File child : directoryListing) {
            if (child.isDirectory()) {
                res.addAll(recursiveInDirectory(child));
            } else {
                res.add(child);
            }
        }
        return res;
    }

    public static String ImageToBase64(File file) throws IOException {
        return Base64.getEncoder().encodeToString(org.apache.commons.io.FileUtils.readFileToByteArray(file));
    }

    //TODO: Refactor to somewhere else
    public static String getImageSVG(File file) throws IOException {
        String extension = FileUtils.getExtension(file.getName()).toLowerCase();
        switch (extension) {
            case "jpg":
            case "jpeg":
            case "jpe":
            case "jif":
            case "jfif":
            case "jfi":
                return "data:image/jpg;base64," + ImageToBase64(file);
            case "png":
                return "data:image/png;base64," + ImageToBase64(file);
            default:
                System.err.println("WARNING: Unknown image format: ." + extension);
                return "data:image/false;base64," + ImageToBase64(file);
        }
    }





}
