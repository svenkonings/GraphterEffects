package utils;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by poesd_000 on 22/03/2017.
 */
public class FileUtils {

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
