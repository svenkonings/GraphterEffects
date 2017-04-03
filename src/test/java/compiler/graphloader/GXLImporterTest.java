package compiler.graphloader;

import org.junit.Test;
import org.xml.sax.SAXException;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;

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
        for (File f : FileUtils.recursiveInDirectory(FileUtils.fromResources(folder))) {
            try {
                if (GXLImporter.acceptsExtension(FileUtils.getExtension(f.getName()))) {
                    Importer.graphFromFile(f);
                }
            } catch (Exception e) {
                System.err.println("Error reading file " + f.getPath());
                throw e;
            }
        }
    }
}



