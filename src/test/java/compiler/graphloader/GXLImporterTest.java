package compiler.graphloader;

import org.junit.Test;
import org.xml.sax.SAXException;
import utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class GXLImporterTest {

    @Test
    public void GPLWithoutError() throws IOException, SAXException {
        testFromFolder("groovegraphs");
    }

    @Test
    public void GXLWithoutError() throws IOException, SAXException {
        testFromFolder("gxl");
    }


    private void testFromFolder(String folder) throws IOException, SAXException {
        Files.walk(Paths.get(folder)).forEach(path -> {
            try {
                if (GXLImporter.acceptsExtension(FileUtils.getExtension(path.toString()))) {
                    Importer.graphFromFile(path.toString());
                }
            } catch (SAXException | IOException e) {
                System.err.println("Error reading file " + path);
                throw new RuntimeException(e);
            }
        });
    }
}



