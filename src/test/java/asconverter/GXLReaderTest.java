package asconverter;

import abstractsyntaxconverter.GXLReader;
import org.junit.Test;
import org.xml.sax.SAXException;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by poesd_000 on 22/03/2017.
 */
public class GXLReaderTest {

    @Test
    public void GPLWithoutError() throws IOException, SAXException {
        for (File f : FileUtils.recursiveInDirectory(FileUtils.fromResources("gpl_and_gst"))) {
            try {
                if (f.getName().endsWith(".properties") || f.getName().endsWith(".gcp") || f.getName().endsWith(".txt")) {
                    continue;
                }
                GXLReader.read(f.getPath());
            } catch (Exception e) {
                System.err.println("Error reading file " + f.getPath());
                throw e;
            }
        }
    }

    @Test
    public void GXLWithoutError() throws IOException, SAXException {
        for (File f : FileUtils.recursiveInDirectory(FileUtils.fromResources("gxl/test.gxl"))) {
            try {
                if (f.getName().endsWith(".properties") || f.getName().endsWith(".gcp") || f.getName().endsWith(".txt")) {
                    continue;
                }
                GXLReader.read(f.getPath());
            } catch (Exception e) {
                System.err.println("Error reading file " + f.getPath());
                throw e;
            }
        }
    }
}



