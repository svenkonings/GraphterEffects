package utils;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by poesd_000 on 22/03/2017.
 */
public class FileUtils {


    public static List<File> recursiveInDirectory(String directory) {
        File file = new File(directory);
        return recursiveInDirectory(file);
    }


    private static List<File> recursiveInDirectory(File dir) {
        assert dir.isDirectory();
        List<File> res = new LinkedList<>();
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
