package utils;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public final class FileUtils {


    public static String getExtension(String filename) {
        if (!filename.contains(".")) {
            return filename;
        }
        return getExtension(filename.substring(filename.indexOf(".")+1));
    }

    public static File fromResources(String path) {
        return new File(FileUtils.class.getClassLoader().getResource(path).getFile());
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




}
