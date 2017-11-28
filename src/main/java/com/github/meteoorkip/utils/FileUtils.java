package com.github.meteoorkip.utils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class used for methods to read from- save to- or manipulate File Objects and related tasks.
 */
public final class FileUtils {


    /**
     * Retrieves the extension of a file given its filename.
     *
     * @param filename Filename of the file.
     * @return The extension of the file.
     */
    public static String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    /**
     * Retrieves a file from the Resources folder of Graphter Effects.
     *
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
     * Retrieves a file from the Resources folder of Graphter Effects. Also works from jar file.
     * @param path Path that indicates the location of the file in question from the resource folder.
     * @return The string contents of the file requested.

     */
    public static String fromResourcesAsString(String path) throws IOException {
        InputStream stream = FileUtils.class.getClassLoader().getResourceAsStream(path);
        try {
            return new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining("\n"));
        } catch (NullPointerException e) {
            throw new IOException("Resource not found: " + path);
        }
    }

    /**
     * Returns a list of ALL files in a directory and all its subdirectories, recursively.
     *
     * @param dir Directory to search files in.
     * @return All files within this directory.
     * @throws IOException Thrown when access was denied.
     */
    public static List<File> recursiveInDirectory(File dir) throws IOException {
        return Files.walk(dir.toPath()).map(Path::toFile).filter(File::isFile).collect(Collectors.toList());
    }

    /**
     * Reads a file and returns its Base64 String representation.
     *
     * @param file The file to be read.
     * @return The Base64 String representation.
     * @throws IOException Thrown when the file could not be read.
     */
    public static String ImageToBase64(File file) throws IOException {
        return Base64.getEncoder().encodeToString(Files.readAllBytes(file.toPath()));
    }

    /**
     * Returns a String read from a file.
     *
     * @param file A given file.
     * @return The String contents of that file.
     * @throws IOException Throw when the file could not be read.
     */
    public static String readFromFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }

    /**
     * Reads a file and returns a String containing its Base64 String representation that can be used in an SVG image.
     *
     * @param file The File to be read.
     * @return The String representation that can be used in SVG generation.
     * @throws IOException Thrown when the File could not be read.
     */
    public static String getImageSVG(File file) throws IOException {
        String extension = FileUtils.getExtension(file.getName());
        String base64 = ImageToBase64(file);
        return "data:image/" + extension + ";base64," + base64;
    }
}
