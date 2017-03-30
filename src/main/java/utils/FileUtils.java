package utils;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Base64;

/**
 * Class used for methods to read from- save to- or manipulate File Objects and related tasks.
 */
public final class FileUtils {


    /**
     * Retrieves the extension of a file given its filename.
     * @param filename Filename of the file.
     * @return The extension of the file.
     */
    public static String getExtension(String filename) {
        if (!filename.contains(".")) {
            return filename;
        }
        return getExtension(filename.substring(filename.indexOf(".")+1));
    }

    /**
     * Retrieves a file from the Resources folder of Graphter Effects.
     * @param path Path that indicates the location of the file in question from the resource folder.
     * @return The file requested.
     * @throws IOException Thrown when the file could not be loaded.
     */
    public static File fromResources(String path) throws IOException {
        URL url = FileUtils.class.getClassLoader().getResource(path);
        if (url == null) {
            throw new IOException("Resource file " + path + " not found.");
        }
        return new File(url.getFile());
    }

    /**
     * Returns a list of ALL files in a directory and all its subdirectories, recursively.
     * @param dir Directory to search files in.
     * @return All files within this directory.
     */
    public static List<File> recursiveInDirectory(File dir) throws IOException {
        List<File> res = new LinkedList<>();
        if (dir.isFile()) {
            res.add(dir);
            return res;
        }
        File[] directoryListing = dir.listFiles();
        if (directoryListing == null) {
            throw new IOException();
        }
        for (File child : directoryListing) {
            if (child.isDirectory()) {
                res.addAll(recursiveInDirectory(child));
            } else {
                res.add(child);
            }
        }
        return res;
    }

    /**
     * Reads a file and returns its Base64 String representation.
     * @param file The file to be read.
     * @return The Base64 String representation.
     * @throws IOException Thrown when the file could not be read.
     */
    public static String ImageToBase64(File file) throws IOException {
        return Base64.getEncoder().encodeToString(org.apache.commons.io.FileUtils.readFileToByteArray(file));
    }

    public static String readFromFile(File file) throws IOException {
        return org.apache.commons.io.FileUtils.readFileToString(file, "UTF-8");
    }


    /**
     * Reads a file and returns a String containing its Base64 String representation that can be used in an SVG image.
     * @param file The File to be read.
     * @return The String representation that can be used in SVG generation.
     * @throws IOException Thrown when the File could not be read.
     */
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
