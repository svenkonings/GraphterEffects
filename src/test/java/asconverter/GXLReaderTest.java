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
    public void createGraphstreamWithoutError() throws IOException, SAXException {
        for (File f : FileUtils.recursiveInDirectory("resources/testgxl")) {
            if (f.getName().endsWith(".properties") || f.getName().endsWith(".gcp") || f.getName().endsWith(".txt")) {
                continue;
            }
            System.out.println("Reading " + f.getAbsolutePath() + "...");
            GXLReader.read(f.getPath());
        }

    }
}



