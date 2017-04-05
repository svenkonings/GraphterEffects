package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.stream.Collectors;

public class FileUtils {

    /**
     * Retrieves a file from the Resources folder of Graphter Effects.
     *
     * @param path Path that indicates the location of the file in question from the resource folder.
     * @return The file requested.
     * @throws IOException Thrown when the file could not be loaded.
     */
    public static String fromResources(String path) throws IOException {
        URL url = FileUtils.class.getClassLoader().getResource(path);
        if (url == null) {
            throw new IOException("Resource file " + path + " not found.");
        }
        try {
            return Paths.get(url.toURI()).toString();
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

    public static String readLines(String path) throws IOException {
        return Files.readAllLines(Paths.get(path)).stream().collect(Collectors.joining());
    }

    /**
     * Reads a file and returns a String containing its Base64 String representation that can be used in an SVG image.
     *
     * @param path The File to be read.
     * @return The String representation that can be used in SVG generation.
     * @throws IOException Thrown when the File could not be read.
     */
    public static String getImageSVG(String path) throws IOException {
        String extension = getExtension(path);
        String base64 = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(path)));
        return "data:image/" + extension + ";base64," + base64;
    }

    /**
     * Retrieves the extension of a file given its filename.
     *
     * @param path Filename of the file.
     * @return The extension of the file.
     */
    public static String getExtension(String path) {
        return path.substring(path.lastIndexOf(".") + 1).toLowerCase();
    }
}
